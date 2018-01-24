package com.glaf.isdp.web.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.RequestUtils;
import com.glaf.isdp.util.JSONConvertUtil;

@Controller
@Path("/rs/isdp/collect")
public class CollectResource {

	@Resource
	protected ITablePageService tablePageService;

	@Resource
	protected IFieldInterfaceService fieldInterfaceService;

	/**
	 * 获取图表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/charts")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] charts(@Context HttpServletRequest request) throws Exception {
		String tableName = RequestUtils.getString(request, "tableName");
		String dataType = RequestUtils.getString(request, "dataType");
		String whereCondition = RequestUtils.getString(request, "whereCondition");

		tableName = tableName == null ? "" : tableName.toUpperCase();

		JSONObject whereJSON = null;
		if (whereCondition != null && StringUtils.isNotEmpty(whereCondition)) {
			whereJSON = JSONObject.parseObject(whereCondition);
		}
		
		JSONArray array = new JSONArray();
		if ("list".equalsIgnoreCase(dataType)) {
			array = getDataList(tableName, whereJSON);
		} else if ("sum".equalsIgnoreCase(dataType)) {
			array = getDataSum(tableName, whereJSON);
		} else if ("aggregatesum".equalsIgnoreCase(dataType)) {
			array = getDataAggregateSum(tableName, whereJSON);
		}

		return array.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取图表数据，合计也在图表中显示
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private JSONArray getDataAggregateSum(String tableName, JSONObject whereJSON) throws Exception {
		String groupBy = getGroupByFileName(whereJSON);

		StringBuffer sb = new StringBuffer(" FROM ").append(tableName).append(" WHERE 1=1 ");
		sb.append(getWhereCondition(whereJSON));// 添加where条件

		Map<String, String> fieldMap = this.getFieldString(tableName);

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append(groupBy == null ? "" : (groupBy + ","));
		sql.append(fieldMap.get("fieldsum")).append(sb.toString());
		sql.append(groupBy == null ? "" : (" GROUP BY " + groupBy));
		List<Map<String, Object>> rslist = tablePageService.getListData(sql.toString(), null);

		sql = new StringBuffer();
		sql.append(" SELECT ").append(fieldMap.get("fieldsum")).append(sb.toString());
		List<Map<String, Object>> sum = tablePageService.getListData(sql.toString(), null);
		if(rslist!=null && rslist.size()>0 && sum!=null && sum.size()>0){
			Map<String, Object> tmp = sum.get(0);
			tmp.put(groupBy, "合计");
			rslist.add(tmp);
		}

		JSONArray array = JSONConvertUtil.mapListToJSONArray(rslist);
		this.customData(tableName, array);// 处理自定义数据

		return array;
	}

	/**
	 * 获取图表数据，图表只显示求和结果
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private JSONArray getDataSum(String tableName, JSONObject whereJSON) throws Exception {
		StringBuffer sb = new StringBuffer();
		List<FieldInterface> list = fieldInterfaceService.getInterfacesByTableId(tableName);
		for (FieldInterface model : list) {
			if (model.getShowtype() != null && model.getShowtype().equals("1")) {
				sb.append(",SUM(").append(model.getDname()).append(") AS ").append(model.getDname());
			}
		}
		sb = new StringBuffer("SELECT ").append(sb.substring(1)).append(" FROM ").append(tableName)
				.append(" WHERE 1=1 ");
		sb.append(getWhereCondition(whereJSON));// 添加where条件

		List<Map<String, Object>> rslist = tablePageService.getListData(sb.toString(), null);
		JSONArray array = JSONConvertUtil.mapListToJSONArray(rslist);

		return array;
	}

	/**
	 * 获取图表数据，图表中只显示列表数据
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private JSONArray getDataList(String tableName, JSONObject whereJSON) throws Exception {
		StringBuffer sb = new StringBuffer("SELECT * FROM ").append(tableName).append(" WHERE 1=1");
		sb.append(getWhereCondition(whereJSON));// 添加where条件
		sb.append(" ORDER BY LISTNO ASC");

		List<Map<String, Object>> list = tablePageService.getListData(sb.toString(), null);
		JSONArray array = JSONConvertUtil.mapListToJSONArray(list);

		this.customData(tableName, array);// 处理自定义数据

		return array;
	}

	/**
	 * 获取列表数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/datalist")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] datalist(@Context HttpServletRequest request) throws Exception {
		String tableName = RequestUtils.getString(request, "tableName");
		String whereCondition = RequestUtils.getString(request, "whereCondition");
		tableName = tableName == null ? "" : tableName.toUpperCase();

		JSONObject whereJSON = null;
		if (whereCondition != null && StringUtils.isNotEmpty(whereCondition)) {
			whereJSON = JSONObject.parseObject(whereCondition);
		}

		JSONObject results = new JSONObject();
		StringBuffer sb = new StringBuffer(" FROM ").append(tableName).append(" WHERE 1=1 ");
		sb.append(getWhereCondition(whereJSON));// 添加where条件

		int total = tablePageService.getQueryCount("select count(*) " + sb.toString(), new HashMap<String, Object>());
		results.put("total", total);
		if (total > 0) {
			List<Map<String, Object>> list = tablePageService.getListData("SELECT * " + sb.toString()
					+ " ORDER BY LISTNO ASC", null);
			JSONArray array = JSONConvertUtil.mapListToJSONArray(list);

			this.customData(tableName, array);// 处理自定义数据
			results.put("rows", array);
		}
		return results.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取列表数据，并求和。将求和结果插入到列表记录中
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/dataAggregatelist")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] dataAggregatelist(@Context HttpServletRequest request) throws Exception {
		String tableName = RequestUtils.getString(request, "tableName");
		String whereCondition = RequestUtils.getString(request, "whereCondition");
		tableName = tableName == null ? "" : tableName.toUpperCase();
		JSONObject whereJSON = null;
		if (whereCondition != null && StringUtils.isNotEmpty(whereCondition)) {
			whereJSON = JSONObject.parseObject(whereCondition);
		}

		String groupBy = getGroupByFileName(whereJSON);

		JSONObject results = new JSONObject();
		StringBuffer sb = new StringBuffer(" FROM ").append(tableName).append(" WHERE 1=1 ");
		sb.append(getWhereCondition(whereJSON));// 添加where条件
		int total = tablePageService.getQueryCount("select count(*) " + sb.toString(), new HashMap<String, Object>());
		results.put("total", total);

		// 处理字段
		Map<String, String> fieldMap = this.getFieldString(tableName);


		if (total > 0) {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ").append(groupBy == null ? "" : (groupBy + ","));
			sql.append(fieldMap.get("fieldsum")).append(sb.toString());
			sql.append(groupBy == null ? "" : (" GROUP BY " + groupBy));
			List<Map<String, Object>> list = tablePageService.getListData(sql.toString(), null);

			sql = new StringBuffer();
			sql.append(" SELECT ").append(fieldMap.get("fieldsum")).append(sb.toString());
			List<Map<String, Object>> sum = tablePageService.getListData(sql.toString(), null);
			if(sum!=null){
				Map<String, Object> tmp = sum.get(0);
				tmp.put(groupBy, "合计");
				list.add(tmp);
			}
			
			JSONArray array = JSONConvertUtil.mapListToJSONArray(list);

			this.customData(tableName, array);// 处理自定义数据
			results.put("rows", array);
		}
		return results.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 根据表名查询需要显示的字段及求和字段
	 * 
	 * @param tableName
	 * @return
	 */
	private Map<String, String> getFieldString(String tableName) {
		StringBuffer fieldsb = new StringBuffer();
		StringBuffer fieldsumsb = new StringBuffer();
		List<FieldInterface> fieldList = fieldInterfaceService.getInterfacesByTableId(tableName);
		for (FieldInterface model : fieldList) {
			if (model.getIsListShow() != null && model.getIsListShow().equals("1")) {
				// 如果字段显示标记设置为1时，在列表中显示
				fieldsb.append(",").append(model.getDname());
				if (model.getDtype().equalsIgnoreCase("i4") || model.getDtype().equalsIgnoreCase("r8")
						|| model.getDtype().equalsIgnoreCase("int")) {
					// 如果是数值类型，加上求和字段中
					fieldsumsb.append(",").append("SUM(").append(model.getDname()).append(") AS ")
							.append(model.getDname());
				}
			}
		}

		Map<String, String> fieldMap = new HashMap<String, String>();
		fieldMap.put("field", fieldsb.substring(1));
		fieldMap.put("fieldsum", fieldsumsb.substring(1));

		return fieldMap;
	}

	/**
	 * 处理Where条件，request参数是JSON格式，通过encordURI编码
	 * 
	 * @param whereCondition
	 * @return
	 */
	private String getWhereCondition(JSONObject whereJSON) {
		StringBuffer sb = new StringBuffer();
		if (whereJSON != null) {
			// 增加查询条件
			// 循环遍历参数，拼接成Where条件
			Set<Entry<String, Object>> set = whereJSON.entrySet();
			Iterator<Entry<String, Object>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				if (entry.getValue() != null && !"null".equalsIgnoreCase(entry.getValue().toString())) {
					sb.append(" AND ").append(entry.getKey()).append("=").append(entry.getValue());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 自定义的数据 不同图表分别有不同的数据，在这个方法里面根据tableName进行处理
	 * 
	 * @param tableName
	 * @param array
	 */
	private void customData(String tableName, JSONArray array) {
		if ("KEY_POINT_SET".equalsIgnoreCase(tableName)) {
			// 如果是关键点设置，计算百分比
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				obj.put("KEY_POINT_PERCENT",
						obj.getDoubleValue("KEY_POINT_QUANTITY") / obj.getDoubleValue("PARTITION_QUANTITY"));
			}
		} else if ("COMPLETION_TREEPINFO".equalsIgnoreCase(tableName)) {
			// 如果是开工报告等完成情况统计，计算百分比
			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = array.getJSONObject(i);
				obj.put("NODE_FINISH_PERCENT",
						obj.getDoubleValue("NODE_FINISH_COUNT") / obj.getDoubleValue("NODE_COUNT"));
				obj.put("NODE_FIRST_INSPECTION_PASS_PERCENT", obj.getDoubleValue("NODE_FIRST_INSPECTION_PASS_COUNT")
						/ obj.getDoubleValue("NODE_FINISH_COUNT"));
			}
		}
	}

	/**
	 * 从request参数中获取分组字段
	 * 
	 * @param whereCondition
	 * @return
	 */
	private String getGroupByFileName(JSONObject whereJSON) {
		String groupBy = null;// 分组字段
		if (whereJSON != null && whereJSON.containsKey("groupBy")) {
			groupBy = whereJSON.getString("groupBy");
			whereJSON.remove("groupBy");
		}
		return groupBy;
	}

	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	public void setFieldInterfaceService(IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

}
