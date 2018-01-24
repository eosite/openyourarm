package com.glaf.chinaiss.data.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.chinaiss.data.domain.DataModel;
import com.glaf.chinaiss.data.domain.DataModelColumns;
import com.glaf.chinaiss.data.domain.DataModelMetadata;
import com.glaf.chinaiss.data.domain.DataModelRelation;
import com.glaf.chinaiss.data.domain.DataModelTables;
import com.glaf.chinaiss.data.mapper.DataModelMapper;
import com.glaf.chinaiss.data.query.DataModelQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.ThreadFactory;
import com.glaf.datamgr.jdbc.DBAM;
import com.glaf.isdp.domain.RdataField;
import com.glaf.isdp.domain.RdataTable;
import com.glaf.isdp.domain.Rinterface;
import com.glaf.isdp.service.RdataTableService;

@Service("com.glaf.chinaiss.data.service.dataModelService")
@Transactional(readOnly = true)
public class DataModelServiceImpl implements DataModelService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataModelMapper dataModelMapper;

	protected DataModelTablesService dataModelTablesService;

	protected DataModelColumnsService dataModelColumnsService;

	protected DataModelRelationService dataModelRelationService;

	protected RdataTableService rdataTableService;

	public DataModelServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataModel> list) {
		for (DataModel dataModel : list) {
			if (StringUtils.isEmpty(dataModel.getId())) {
				dataModel.setId(idGenerator.getNextId("DATA_MODEL"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// dataModelMapper.bulkInsertDataModel_oracle(list);
		} else {
			// dataModelMapper.bulkInsertDataModel(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			dataModelMapper.deleteDataModelById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				dataModelMapper.deleteDataModelById(id);
			}
		}
	}

	public int count(DataModelQuery query) {
		return dataModelMapper.getDataModelCount(query);
	}

	public List<DataModel> list(DataModelQuery query) {
		List<DataModel> list = dataModelMapper.getDataModels(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataModelCountByQueryCriteria(DataModelQuery query) {
		return dataModelMapper.getDataModelCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataModel> getDataModelsByQueryCriteria(int start, int pageSize, DataModelQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataModel> rows = sqlSessionTemplate.selectList("getDataModels", query, rowBounds);
		return rows;
	}

	public DataModel getDataModel(String id) {
		if (id == null) {
			return null;
		}
		DataModel dataModel = dataModelMapper.getDataModelById(id);
		return dataModel;
	}

	@Transactional
	public void save(DataModel dataModel) {
		if (StringUtils.isEmpty(dataModel.getId())) {
			dataModel.setId(idGenerator.getNextId("DATA_MODEL"));
			dataModel.setCreateDate(new Date());
			// dataModel.setDeleteFlag(0);
			dataModelMapper.insertDataModel(dataModel);
		} else {
			dataModel.setUpdateDate(new Date());
			dataModelMapper.updateDataModel(dataModel);
		}
	}

	@Override
	public List getDataModelsWithTree(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataModelMapper.getDataModelsWithTree(params);
	}

	@Override
	public List<Map<String, Object>> getDataModelTablesByTreeId(String treeId) {
		List<Map<String, Object>> list = //
				dataModelMapper.getDataModelTablesByTreeId("%" + treeId + "%");
		if (CollectionUtils.isNotEmpty(list)) {
			for (Map<String, Object> map : list) {
				map.put("tableName", map.get("TABLENAME_"));
				map.put("name", map.get("NAME_"));
				map.put("table", true);
			}
		}
		return list;
	}

	@Override
	public List getDataModelColumns(String treeId, String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public void deleteDetails(String id, String sql) {
		logger.debug("-------------------start run-------------------");
		Connection connection = null;
		Statement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.createStatement();

			if (StringUtils.isEmpty(sql)) {
				String tables[] = new String[] { "DATA_MODEL_TABLES", "DATA_MODEL_RELATION" };
				for (String tableName : tables) {
					psmt.addBatch(String.format("DELETE FROM %s WHERE PARENTID_ = '%s'", tableName, id));
				}
			} else {
				psmt.addBatch(sql);
			}

			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	@Override
	public void saveOrUpdateDetails(DataModel dataModel) {
		this.save(dataModel);
		if (StringUtils.isNotEmpty(dataModel.getJson())) {
			this.deleteDetails(dataModel.getId(), null);
			JSONObject json = JSON.parseObject//
			(dataModel.getJson()), to, co;

			/**
			 * 表
			 */
			int i, len, $i;
			String code, id;
			DataModelTables dmt;
			DataModelColumns column;
			List<DataModelColumns> dmcs = null;
			Map<String, JSONObject> map = new HashMap<String, JSONObject>();
			JSONArray arrays = json.getJSONArray("nodes"), columns;
			if (CollectionUtils.isNotEmpty(arrays)) {
				len = arrays.size();
				for (i = 0; i < len; i++) {
					to = arrays.getJSONObject(i);
					code = to.getString("code");
					code = "";
					id = to.getString("id");
					if (StringUtils.isEmpty(code)) {
						code = id;
					}
					to.put("code", code);
					dmt = new DataModelTables();
					dmt.setParentId(dataModel.getId());
					dmt.setTableName(code);
					dmt.setName(to.getString("name"));
					dmt.setDescription(to.getString("describe"));
					dmt.setType(to.getString("type"));
					dmt.setCreateDate(new Date());

					this.dataModelTablesService.save(dmt);
					map.put(id, to);
					columns = to.getJSONArray("columns");

					if (CollectionUtils.isNotEmpty(columns)) {
						dmcs = new ArrayList<DataModelColumns>();
						this.deleteDetails(dataModel.getId(),
								String.format(//
										"DELETE FROM DATA_MODEL_COLUMNS WHERE TABLENAME_ = '%s' AND TOPID_ LIKE '%s'", //
										dmt.getTableName(), "%" + dataModel.getParentId() + "%"));
						for ($i = 0; $i < columns.size(); $i++) {
							co = columns.getJSONObject($i);
							code = co.getString("code");
							id = co.getString("id");
							if (StringUtils.isEmpty(code)) {
								code = id;
							}

							column = new DataModelColumns();
							column.setColumnName(code);
							column.setName(co.getString("name"));
							column.setDescription(co.getString("describe"));
							column.setLength(co.getInteger("length"));
							column.setType(co.getString("datatype"));
							column.setParentId(dmt.getId());
							column.setTableName(dmt.getTableName());
							column.setCreateDate(new Date());
							column.setTopId(dataModel.getTreeId());
							// this.dataModelColumnsService.save(column);
							dmcs.add(column);
						}
						/**
						 * 列太多...
						 */
						ThreadFactory.run(new SaveDetails(dmcs, dataModelColumnsService));
					}
				}
			}

			/**
			 * 表关系
			 */
			arrays = json.getJSONArray("edges");
			if (CollectionUtils.isNotEmpty(arrays)) {
				JSONObject relation;
				DataModelRelation dmr;
				String type;
				String[] sourceMsg, targetMsg;
				len = arrays.size();
				for (i = 0; i < len; i++) {
					relation = arrays.getJSONObject(i);
					dmr = new DataModelRelation();
					dmr.setRelateType(type = //
							relation.getJSONObject("data").getString("type"));

					sourceMsg = StringUtils.split(relation.getString("source"), ".");
					if (!map.containsKey(sourceMsg[0])) {
						continue;
					}
					dmr.setColumnName(sourceMsg[1]);

					targetMsg = StringUtils.split(relation.getString("target"), ".");
					if (!map.containsKey(targetMsg[0])) {
						continue;
					}
					dmr.setRelateTable(map.get(targetMsg[0]).getString("code"));
					dmr.setRelateColumn(targetMsg[1]);

					dmr.setParentId(dataModel.getId());
					dmr.setCreateDate(new Date());

					this.dataModelRelationService.save(dmr);
				}
			}
		}
	}

	class SaveDetails implements Runnable {

		protected List<DataModelColumns> dmcs;

		protected DataModelColumnsService dataModelColumnsService;

		public SaveDetails(List<DataModelColumns> dmcs, DataModelColumnsService dataModelColumnsService) {
			this.dmcs = dmcs;
			this.dataModelColumnsService = dataModelColumnsService;
		}

		@Override
		public void run() {
			if (CollectionUtils.isNotEmpty(dmcs))
				for (DataModelColumns dmc : dmcs)
					dataModelColumnsService.save(dmc);
		}

	}

	public List<Map<String, Object>> getList(String sql, List<Object> parameters) {
		List<Map<String, Object>> list = null;
		try {
			list = DBAM.executeQuery(DataSourceUtils//
					.getConnection(jdbcTemplate.getDataSource()), sql, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return list;
	}

	/**
	 * 获取模型与物理库的表、字段映射
	 * 
	 * @param connection
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public Map<String, DataModelMetadata> getMappingByTableName(String tableName) {
		Map<String, DataModelMetadata> mapping = new HashMap<String, DataModelMetadata>();
		String sql = "SELECT CODE_ 'code', MAPPING_ 'mapping', MAPPINGID_ 'mappingId' FROM DATA_MODEL_METADATA WHERE CODE_ LIKE ?";
		List<Map<String, Object>> list;
		try {
			list = DBAM.executeQuery(
					DataSourceUtils//
							.getConnection(jdbcTemplate.getDataSource()), //
					sql, Arrays.asList(tableName + "%"));
			if (CollectionUtils.isNotEmpty(list)) {
				DataModelMetadata m = null;
				for (Map<String, Object> map : list) {
					m = new DataModelMetadata();
					BeanUtils.populate(m, map);
					mapping.put(m.getCode().toLowerCase(), //
							m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return mapping;
	}

	public void insertMapping(String code, String mapping, String type, String mapingId) {
		try {

			Connection connection = DataSourceUtils//
					.getConnection(jdbcTemplate.getDataSource());

			DBAM.execute(connection, "DELETE FROM DATA_MODEL_METADATA WHERE CODE_ = ?", Arrays.asList(code));

			DBAM.execute(connection,
					"INSERT INTO DATA_MODEL_METADATA (CODE_, MAPPING_, TYPE_, MAPPINGID_, ID_) VALUES (?, ?, ?, ?, ?)",
					Arrays.asList(code, mapping, type, mapingId, idGenerator.nextId("DATA_MODEL_METADATA_SEQ")));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.chinaiss.data.mapper.DataModelMapper")
	public void setDataModelMapper(DataModelMapper dataModelMapper) {
		this.dataModelMapper = dataModelMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setDataModelColumnsService(DataModelColumnsService dataModelColumnsService) {
		this.dataModelColumnsService = dataModelColumnsService;
	}

	@javax.annotation.Resource
	public void setDataModelRelationService(DataModelRelationService dataModelRelationService) {
		this.dataModelRelationService = dataModelRelationService;
	}

	@javax.annotation.Resource
	public void setDataModelTablesService(DataModelTablesService dataModelTablesService) {
		this.dataModelTablesService = dataModelTablesService;
	}

	@javax.annotation.Resource
	public void setRdataTableService(RdataTableService rdataTableService) {
		this.rdataTableService = rdataTableService;
	}

}
