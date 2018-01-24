package com.glaf.dep.base.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.DBUtils;
import com.glaf.dep.base.domain.DepBaseWdataSet;
import com.glaf.dep.base.mapper.DepBaseWdataSetMapper;
import com.glaf.dep.base.query.DepBaseWdataSetQuery;
import com.glaf.dep.base.util.WdataSetBiluder;
import com.glaf.isdp.util.TableActionUtils;

@Service("com.glaf.dep.base.service.depBaseWdataSetService")
@Transactional(readOnly = true)
public class DepBaseWdataSetServiceImpl implements DepBaseWdataSetService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBaseWdataSetMapper depBaseWdataSetMapper;

	public DepBaseWdataSetServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			depBaseWdataSetMapper.deleteDepBaseWdataSetById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				depBaseWdataSetMapper.deleteDepBaseWdataSetById(id);
			}
		}
	}

	public int count(DepBaseWdataSetQuery query) {
		return depBaseWdataSetMapper.getDepBaseWdataSetCount(query);
	}

	public List<DepBaseWdataSet> list(DepBaseWdataSetQuery query) {
		List<DepBaseWdataSet> list = depBaseWdataSetMapper.getDepBaseWdataSets(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBaseWdataSetCountByQueryCriteria(DepBaseWdataSetQuery query) {
		return depBaseWdataSetMapper.getDepBaseWdataSetCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseWdataSet> getDepBaseWdataSetsByQueryCriteria(int start, int pageSize,
			DepBaseWdataSetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseWdataSet> rows = sqlSessionTemplate.selectList("getDepBaseWdataSets", query, rowBounds);
		return rows;
	}

	public DepBaseWdataSet getDepBaseWdataSet(Long id) {
		if (id == null) {
			return null;
		}
		DepBaseWdataSet depBaseWdataSet = depBaseWdataSetMapper.getDepBaseWdataSetById(id);
		return depBaseWdataSet;
	}

	@Transactional
	public void save(DepBaseWdataSet depBaseWdataSet) {
		if (depBaseWdataSet.getId() == null) {
			depBaseWdataSet.setId(idGenerator.nextId("DEP_BASE_WDATASET"));
			this.initSql(depBaseWdataSet);
			if (depBaseWdataSet.getDataSetCode() == null) {
				depBaseWdataSet.setDataSetCode("wds-code-" + depBaseWdataSet.getId());
			}
			depBaseWdataSetMapper.insertDepBaseWdataSet(depBaseWdataSet);
		} else {
			depBaseWdataSet.setModifyDatetime(new Date());
			this.initSql(depBaseWdataSet);
			depBaseWdataSetMapper.updateDepBaseWdataSet(depBaseWdataSet);
		}
	}

	private void initSql(DepBaseWdataSet depBaseWdataSet) {
		if (depBaseWdataSet.getRuleJson() != null) {
			JSONObject ruleJsonObject = JSON.parseObject(depBaseWdataSet.getRuleJson());
			ruleJsonObject.put("insertSql", new WdataSetBiluder(ruleJsonObject).getInsertDynamicSql());
			ruleJsonObject.put("updateSql", new WdataSetBiluder(ruleJsonObject).getUpdateDynamicSql());
			ruleJsonObject.put("deleteSql", new WdataSetBiluder(ruleJsonObject).getDeleteSql());
			ruleJsonObject.put("isNew", true);
			ruleJsonObject.put("isNewViews", true);
			depBaseWdataSet.setRuleJson(ruleJsonObject.toJSONString());
		}
	}

	private JSONObject getRuleJson(Long id) {
		DepBaseWdataSet wdataSet = this.getDepBaseWdataSet(id);
		return JSON.parseObject(wdataSet.getRuleJson());
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseWdataSetMapper")
	public void setDepBaseWdataSetMapper(DepBaseWdataSetMapper depBaseWdataSetMapper) {
		this.depBaseWdataSetMapper = depBaseWdataSetMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public JSONArray getColumnsBySelected(Long id) {
		JSONObject rule = this.getRuleJson(id), json;
		JSONArray jar = new JSONArray();
		if (rule != null && rule.containsKey("columns")) {
			JSONArray columns = rule.getJSONArray("columns");
			if (org.apache.commons.collections.CollectionUtils.isNotEmpty(columns)) {
				for (int i = 0; i < columns.size(); i++) {
					json = columns.getJSONObject(i);
					if (json.getBooleanValue("selected")) {
						jar.add(json);
					}
				}
			}
		}
		return jar;
	}

	@Override
	public JSONArray getColumns(Long id) {
		JSONObject rule = this.getRuleJson(id);
		JSONArray jar = new JSONArray();
		if (rule != null && rule.containsKey("columns")) {
			return rule.getJSONArray("columns");
		}
		return jar;
	}

	@Override
	public JSONObject getWdataSetCud(Long id) {
		JSONObject json = new JSONObject();
		JSONObject rule = this.getRuleJson(id), j;
		JSONArray jar = new JSONArray();
		if (rule != null && rule.containsKey("columns")) {
			JSONArray columns = rule.getJSONArray("columns");
			if (org.apache.commons.collections.CollectionUtils.isNotEmpty(columns)) {
				for (int i = 0; i < columns.size(); i++) {
					j = columns.getJSONObject(i);
					if (j.getBooleanValue("selected")) {
						jar.add(j);
					}
				}
			}
		}
		json.put("selectedColumns", jar);

		if (rule != null) {
			for (String key : new String[] { "whereParams", "columns" }) {
				if (rule.containsKey(key))
					json.put(key, rule.getJSONArray(key));
			}
		}

		return json;
	}

	@Override
	public void refreshWDataSets() {
		List<DepBaseWdataSet> list = this.list(new DepBaseWdataSetQuery());

		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		 this.refreshDataBase();

		for (DepBaseWdataSet depBaseWdataSet : list) {
			this.updateWdataSet(depBaseWdataSet);
		}

	}

	/**
	 * 刷新表结构
	 */
	private void refreshDataBase() {
		List<Map<String, Object>> list = jdbcTemplate.queryForList(//
				"SELECT DISTINCT DATATABLE_NAME_ FROM DEP_BASE_WDATASET WHERE DATATABLE_NAME_ IS NOT NULL");
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<ColumnDefinition> columns = TableActionUtils.getDefaultTableDefinition(//
				"DEP_BASE_WDATASET").getColumns();

		TableDefinition tableDefinition = new TableDefinition();

		for (ColumnDefinition cd : columns) {
			String columnName = cd.getColumnName().toLowerCase();
			if (!TableActionUtils.getDefaultExpValue().containsKey(columnName) || //
					"id".equals(columnName) || "ctime".equals(columnName)) {
				continue;
			}

			tableDefinition.addColumn(cd);

		}

		for (Map<String, Object> map : list) {
			String tableName = MapUtils.getString(map, "DATATABLE_NAME_");

			if (StringUtils.isBlank(tableName) || !DBUtils.tableExists(tableName)) {
				continue;
			}

			tableDefinition.setTableName(tableName);

			DBUtils.alterTable(tableDefinition);

		}
	}

	/**
	 * 修复更新集默认字段(updatedate, createdate, updater, creater...)
	 * @param depBaseWdataSet
	 */
	protected void updateWdataSet(DepBaseWdataSet depBaseWdataSet) {

		if (StringUtils.isBlank(depBaseWdataSet.getRuleJson())) {
			return;
		}

		String columnsKey = "columns", columnKey = "dataColumnName";

		JSONObject ruleJson = JSON.parseObject(depBaseWdataSet.getRuleJson());

		if (!ruleJson.containsKey(columnsKey))
			return;

		JSONArray columns = new JSONArray(), columns_ = ruleJson.getJSONArray(columnsKey);

		if (CollectionUtils.isEmpty(columns_)) {
			return;
		}

		JSONObject j = null, json = null;
		Map<String, JSONObject> columnMap = new HashMap<>();
		for (int i = 0; i < columns_.size(); i++) {
			j = columns_.getJSONObject(i);
			String dataColumnName = j.getString(columnKey);
			if (StringUtils.isEmpty(dataColumnName) || columnMap.containsKey(dataColumnName //
					= dataColumnName.toLowerCase())) {
				continue;
			}
			columnMap.put(dataColumnName, j);
			columns.add(j);
		}

		Map<String, JSONObject> exps = TableActionUtils.getDefaultExpValue();

		JSONObject exp, column;
		long time = System.currentTimeMillis();
		for (String key : exps.keySet()) {

			column = columnMap.get(key);

		//	if (column == null) {
			//	continue;
		//	}

			//boolean rst = column.getString("param").equals(column.getString("dname"));

			if ((column != null || "id".equals(key))/* && !rst*/) {
				continue;
			} 
			exp = exps.get(key);
			json = /*rst ? column :*/ new JSONObject();
			json.putAll(j);

			json.put("fname", key);
			json.put("conExp", "");
			json.put("prepared", true);
			json.put("isKey", false);
			json.put("dtype", StringUtils.endsWith(key, "date") ? "date" : "string");
			json.put("update", StringUtils.startsWith(key, "update"));
			json.put("paramName", j.getString("tableNameCN") + " " + key + " | 参数");
			// json.put("strlen", 50);
			json.put("input", StringUtils.startsWith(key, "create"));
			json.put("defaultVal", exp.getString("expActVal"));
			json.put(columnKey, key);
			json.put("param", "col" + time++);
			// json.put("wdataSetId", );
			json.put("nullAble", true);
			json.put("dname", key);
			// json.put("tableNameCN", );
			json.put("text", key);
			json.put("selected", false);
			json.put("defVal", exp.getString("expActVal"));
			json.put("dataItem", exp.toJSONString());

			//if (!rst)
				columns.add(json);
		}

		ruleJson.put(columnsKey, columns);
		depBaseWdataSet.setRuleJson(ruleJson.toJSONString());

		this.save(depBaseWdataSet);

	}

}
