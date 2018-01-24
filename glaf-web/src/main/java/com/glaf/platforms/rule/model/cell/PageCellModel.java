package com.glaf.platforms.rule.model.cell;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.scope.context.SynchronizationManagerSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.dep.base.domain.DepBaseWdataSet;
import com.glaf.dep.base.service.DepBaseWdataSetService;

/**
 * 解析页面级规则
 * 
 * @author klaus.wang
 * 
 */
public class PageCellModel extends CommonCellModel implements ICellModel {

	public static final String data_save_area = "data_save_area";

	public static final String dynamic_area = "dynamic_area";

	public static final String dynamic_data = "dynamic_data";

	public static final String DATA_PAGESET = "data_pageset";

	/**
	 * 打印设置
	 */
	public static final String DATA_PRINT = "data_print";
	/**
	 * 打印尺寸
	 */
	public static final String PRINT_SIZE = "A000-2-1-001";
	/**
	 * 打印对齐
	 */
	public static final String PRINT_ALIGN = "A000-2-1-002";

	/**
	 * 自动行高列宽
	 */
	public static final String DATA_AUTO_SIZE = "data_autosize";
	/**
	 * 单元格合并
	 */
	public static final String DATA_MERGE = "data_merge";

	public static final String col1 = "col-1";

	public static final String col2 = "col-2";

	public static final String col3 = "col-3";

	public static final String col8 = "col-8";

	private DepBaseWdataSetService depBaseWdataSetService = ContextFactory
			.getBean("com.glaf.dep.base.service.depBaseWdataSetService");

	public JSONObject convert(JSONObject rule) {

		JSONArray jsonArray;
		JSONObject ret = new JSONObject();

		JSONArray dynamic_areaArray = null;
		if (rule.containsKey(dynamic_area)) { // 变长区
			dynamic_areaArray = new JSONArray();
			jsonArray = JSONArray.parseArray(rule.getString(dynamic_area));
			int i = 0, length = jsonArray.size();
			for (; i < length; i++) {
				JSONObject area = new JSONObject();
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				area.put("type", jsonObj.getJSONObject(col1).getString("name"));
				JSONObject datas = JSONArray.parseArray(jsonObj.getJSONObject(col2).getString("datas"))
						.getJSONObject(0);
				area.put("area", datas);

				area.put("dynamicElongate",
						jsonObj.containsKey(col3) ? jsonObj.getJSONObject(col3).getString("name") : null);
				dynamic_areaArray.add(area);
			}
			// ret.put(dynamic_area, dynamic_areaArray);
		}

		if (rule.containsKey(DATA_PAGESET)) {
			jsonArray = JSONArray.parseArray(rule.getString(DATA_PAGESET));
			JSONArray data_pagesetAry = new JSONArray();
			int i = 0, length = jsonArray.size();
			for (; i < length; i++) {
				JSONObject area = new JSONObject();
				area.put("type", jsonArray.getJSONObject(i).getJSONObject(col1).getString("name"));
				JSONObject datas = JSONArray
						.parseArray(jsonArray.getJSONObject(i).getJSONObject(col2).getString("datas")).getJSONObject(0);
				area.put("area", datas);
				data_pagesetAry.add(area);
			}
			ret.put(DATA_PAGESET, data_pagesetAry);
		}

		/**
		 * 设置打印区域
		 */
		if (rule.containsKey(DATA_PRINT)) {
			jsonArray = JSONArray.parseArray(rule.getString(DATA_PRINT));
			JSONArray data_pagesetAry = new JSONArray();
			int i = 0, length = jsonArray.size();
			List<String> exitsList = new ArrayList<>();
			for (; i < length; i++) {
				JSONObject area = new JSONObject();
				String type = jsonArray.getJSONObject(i).getJSONObject(col1).getString("name");
				if (exitsList.contains(type)) {
					continue;
				}
				exitsList.add(type);
				area.put("type", type);
				JSONObject datas = JSONArray
						.parseArray(jsonArray.getJSONObject(i).getJSONObject(col2).getString("datas")).getJSONObject(0);
				area.put("area", datas);
				data_pagesetAry.add(area);
			}
			ret.put(DATA_PRINT, data_pagesetAry);
		}
		/**
		 * 打印设置
		 */
		if (rule.containsKey(PRINT_SIZE)) {
			String printSize = rule.getString(PRINT_SIZE);
			if (StringUtils.isNotEmpty(printSize)) {
				String keyStr = "printSet";
				JSONObject printSetObj = new JSONObject();
				if (ret.containsKey(keyStr)) {
					printSetObj = ret.getJSONObject(keyStr);
				}
				printSetObj.put("printSize", printSize);
				ret.put(keyStr, printSetObj);
			}
		}
		/**
		 * 打印对齐
		 */
		if (rule.containsKey(PRINT_ALIGN)) {
			String ruleStr = rule.getString(PRINT_ALIGN);
			if (StringUtils.isNotEmpty(ruleStr)) {
				ret.put("printAlign", ruleStr);
			}
		}

		/**
		 * 自动行高列宽
		 */
		commonRuleSet(rule, ret, DATA_AUTO_SIZE, "autoFit");
		/**
		 * 自动合并
		 */
		commonRuleSet(rule, ret, DATA_MERGE, "data_merge");

		if (rule.containsKey(data_save_area)) { // 保存集
			jsonArray = JSONArray.parseArray(rule.getString(data_save_area));
			int i = 0, length = jsonArray.size();
			List<JSONObject> list = new ArrayList<JSONObject>();
			List<JSONObject> subTables = new ArrayList<JSONObject>(); // 保存子表table信息
																		// 用于迭代sortSubTable时候用
			for (; i < length; i++) {
				JSONObject data = jsonArray.getJSONObject(i).getJSONObject(col2).getJSONObject("data");

				JSONObject wdataSet = data.getJSONObject("wdataSet");

				this.initWhereClaus(wdataSet);

				if (data.get("tableMsg") == null || StringUtils.isEmpty(data.getString("tableMsg"))) {
					continue;
				}

				JSONObject tableMsg = JSONArray.parseArray(data.getString("tableMsg")).getJSONObject(0);

				this.a2b(wdataSet, tableMsg);

				if (dynamic_areaArray != null && dynamic_areaArray.size() > 0) {
					String id = tableMsg.getJSONArray("columns") // 获取第一个格子的信息
							.getJSONObject(0).getString("id");
					if (StringUtils.isNotBlank(id)) {
						final String $id[] = id.split("-");

						tableMsg.put("row", $id[0]);
						tableMsg.put("col", $id[1]);

						this.initDynamicArea(dynamic_areaArray, tableMsg);
					}
				}

				this.initTableMsg(tableMsg, subTables);
				tableMsg.put("forcePage", jsonArray.getJSONObject(i).getString(col8));
				tableMsg.put("fenLan", jsonArray.getJSONObject(i).getString("col-9"));
				list.add(tableMsg);
			}
			if (subTables.size() > 0) {
				list = sortSubTable(list, subTables);
			}
			ret.put(data_save_area, list);
		}

		JSONArray array = rule.getJSONArray(dynamic_data);

		if (CollectionUtils.isNotEmpty(array)) {
			array.forEach(j -> {
				JSONObject json = (JSONObject) j;
				System.out.println(json);
			});
			ret.put(dynamic_data, array);
		}

		return ret;
	}

	private List<JSONObject> sortSubTable(List<JSONObject> list, List<JSONObject> subTables) {
		JSONObject table;
		List<JSONObject> retList = new ArrayList<JSONObject>();
		List<JSONObject> lordList = new ArrayList<JSONObject>(); // 主表
		List<JSONObject> subList = new ArrayList<JSONObject>(); // 从表
		boolean isSubOrLord = false;
		// 筛选出有配置主从表关系的表
		for (JSONObject jsonObject : list) {
			table = jsonObject.getJSONObject("table");
			isSubOrLord = false;
			for (JSONObject subJsonObject : subTables) {
				if (subJsonObject.getString("id").equals(table.getString("id"))) {
					isSubOrLord = true;
					subList.add(jsonObject);
					continue;
				} else if (subJsonObject.getString("topId").equals(table.getString("id"))) {
					if (!lordList.contains(jsonObject)) {
						isSubOrLord = true;
						lordList.add(jsonObject);
						continue;
					}
				}
			}
			if (!isSubOrLord) {
				retList.add(jsonObject);
			}
		}
		JSONArray childrens;
		JSONObject subTable;
		for (JSONObject lordObj : lordList) {
			childrens = new JSONArray();
			table = lordObj.getJSONObject("table");
			for (JSONObject subObj : subList) {
				subTable = subObj.getJSONObject("table");
				if (table.getString("id").equals(subTable.getString("topId"))) {
					childrens.add(JSONObject.parse(subObj.toJSONString()));
				}
			}
			lordObj.put("children", childrens);
			retList.add(lordObj);
		}
		retList.removeAll(subList);
		return retList;
	}

	private void initWhereClaus(JSONObject wdataSet) {
		if (wdataSet != null) {
			DepBaseWdataSet depBaseWdataSet = depBaseWdataSetService.getDepBaseWdataSet(wdataSet.getLong("id"));
			if (depBaseWdataSet == null) {
				return;
			}
			String ruleJsoString = depBaseWdataSet.getRuleJson();
			if (StringUtils.isNotBlank(ruleJsoString)) {
				JSONObject ruleJsonObject = JSON.parseObject(ruleJsoString);
				String whereClausKey = "whereClaus";
				if (ruleJsonObject.containsKey(whereClausKey)) {
					JSONArray ruleJsonArray = ruleJsonObject.getJSONArray(whereClausKey),
							ruleJsonArray2 = new JSONArray();
					for (int x = 0; x < ruleJsonArray.size(); x++) {
						JSONObject rJsonObject = ruleJsonArray.getJSONObject(x), rJsonObject2 = new JSONObject();
						this.a2b(rJsonObject, rJsonObject2, "param", "dname", "tablename");
						ruleJsonArray2.add(rJsonObject2);
					}
					wdataSet.put(whereClausKey, ruleJsonArray2);
				}
			}
		}
	}

	private void initTableMsg(JSONObject tableMsg, List<JSONObject> subTables) {

		String key = "columns";
		JSONObject tmpJsonObject;
		JSONArray tmpJsonArray;
		if (tableMsg.containsKey(key)) {
			JSONArray columnsArray = tableMsg.getJSONArray(key);
			tmpJsonArray = new JSONArray();
			int i = 0, size = columnsArray.size();
			for (; i < size; i++) {
				JSONObject jsonObject = columnsArray.getJSONObject(i);
				tmpJsonObject = new JSONObject();
				this.a2b(jsonObject, tmpJsonObject, "id", "fieldName");
				tmpJsonArray.add(tmpJsonObject);
			}
			tableMsg.put(key, tmpJsonArray);
		}

		key = "table";
		if (tableMsg.containsKey(key)) {
			JSONObject table = tableMsg.getJSONObject(key);
			tmpJsonObject = new JSONObject();
			this.a2b(table, tmpJsonObject, "tableName", "isSubTable", "id", "topId");
			if ("1".equals(table.getString("isSubTable"))) {
				subTables.add(table);
			}
			tableMsg.put(key, tmpJsonObject);
		}
	}

	private void initDynamicArea(JSONArray dynamic_areaArray, JSONObject cell) {
		for (int i = 0; i < dynamic_areaArray.size(); i++) {
			JSONObject area = dynamic_areaArray.getJSONObject(i);
			if (this.cellInArea(cell, area.getJSONObject("area"))) {
				this.a2b(area, cell);
				break;
			}
		}
	}

	/**
	 * 判断单元格是否在区域里面
	 * 
	 * @param cell
	 * @param area
	 * @return
	 */
	private boolean cellInArea(JSONObject cell, JSONObject area) {
		if (cell != null && area != null) {
			Integer cr = cell.getInteger("row");
			Integer cc = cell.getInteger("col");
			Integer ar = area.getInteger("row");
			Integer ac = area.getInteger("col");
			if (ar <= cr && ac <= cc && (ar + area.getInteger("rowCount") - 1 >= cr)
					&& (ac + area.getInteger("colCount") - 1 >= cc)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 通用规则转换设置
	 * 
	 * @param rule
	 *            原始规则
	 * @param ret
	 *            转换规则对象
	 * @param key
	 *            原始key
	 * @param retKey
	 *            转换后的key
	 */
	private void commonRuleSet(JSONObject rule, JSONObject ret, String key, String retKey) {
		if (rule.containsKey(key)) {
			// [{"col-1":{"name":"autoRow"},"col-2":{"name":"G10:G12","datas":"[{\"row\":9,\"rowCount\":3,\"col\":6,\"colCount\":1}]"}}]
			String str = rule.getString(key);
			if (StringUtils.isNotEmpty(str)) {
				JSONArray ary = new JSONArray();
				JSONObject obj = new JSONObject();
				JSONArray strAry = JSONArray.parseArray(str);
				JSONObject strObj;
				for (Object object : strAry) {
					obj = new JSONObject();
					strObj = (JSONObject) object;
					obj.put("type", strObj.getJSONObject(col1).getString("name"));
					obj.put("area", JSON.parseArray(strObj.getJSONObject(col2).getString("datas")).getJSONObject(0));
					ary.add(obj);
				}
				ret.put(retKey, ary);
			}
		}
	}
}
