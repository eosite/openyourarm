package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.isdp.config.RConstant;
import com.glaf.isdp.domain.CellDataField;
import com.glaf.isdp.mapper.CellDataFieldMapper;
import com.glaf.isdp.query.CellDataFieldQuery;
import com.glaf.isdp.service.CellDataFieldService;

@Service("com.glaf.isdp.service.cellDataFieldService")
@Transactional(readOnly = true)
public class CellDataFieldServiceImpl implements CellDataFieldService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellDataFieldMapper cellDataFieldMapper;

	public CellDataFieldServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			cellDataFieldMapper.deleteCellDataFieldById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				cellDataFieldMapper.deleteCellDataFieldById(id);
			}
		}
	}

	public int count(CellDataFieldQuery query) {
		query.ensureInitialized();
		return cellDataFieldMapper.getCellDataFieldCount(query);
	}

	public List<CellDataField> list(CellDataFieldQuery query) {
		query.ensureInitialized();
		List<CellDataField> list = cellDataFieldMapper.getCellDataFields(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellDataFieldCountByQueryCriteria(CellDataFieldQuery query) {
		return cellDataFieldMapper.getCellDataFieldCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellDataField> getCellDataFieldsByQueryCriteria(int start, int pageSize, CellDataFieldQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellDataField> rows = sqlSessionTemplate.selectList("getCellDataFields", query, rowBounds);
		return rows;
	}

	public CellDataField getCellDataField(String id) {
		if (id == null) {
			return null;
		}
		CellDataField cellDataField = cellDataFieldMapper.getCellDataFieldById(id);
		return cellDataField;
	}

	@Transactional
	public void save(CellDataField cellDataField) {
		if (StringUtils.isEmpty(cellDataField.getId())) {
			String id = idGenerator.getNextId("cell_data_field", "id", cellDataField.getUserId());
			cellDataField.setId(id);
			// cellDataField.setCreateDate(new Date());
			// cellDataField.setDeleteFlag(0);
			cellDataFieldMapper.insertCellDataField(cellDataField);
		} else {
			cellDataFieldMapper.updateCellDataField(cellDataField);
		}
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
				psmt.addBatch();
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
	public int getNextMaxUser(String tableId) {
		Integer maxUser = cellDataFieldMapper.getNextMaxUser(tableId);
		return maxUser == null ? 1 : maxUser;
	}

	@Override
	public CellDataField getCellDataFieldByFieldName(String fieldname) {
		return cellDataFieldMapper.getCellDataFieldByFieldName(fieldname);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellDataFieldMapper")
	public void setCellDataFieldMapper(CellDataFieldMapper cellDataFieldMapper) {
		this.cellDataFieldMapper = cellDataFieldMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public JSONArray getInterfaceColumnDefinitions(String systemName, String tableName, String tableId, Boolean R) {
		Map<String, Object> o;
		JSONObject jsonObject;
		String columnName;
		List<ColumnDefinition> columns = null;
		if (StringUtils.isNotEmpty(tableName)) {
			columns = DBUtils.getColumnDefinitions(systemName, tableName);
			R = R && DBUtils.tableExists(tableName);
		}
		JSONArray jsonArray = new JSONArray();
		if (CollectionUtils.isNotEmpty(columns)) {
			Map<String, JSONObject> tmpJson = new HashMap<String, JSONObject>();
			Map<String, Map<String, Object>> tmp = new HashMap<String, Map<String, Object>>();

			/**
			 * 没有Interface是直接拿元数据
			 */
			if (DBUtils.tableExists(systemName, "interface")) {
				for (ColumnDefinition column : columns) {
					columnName = column.getColumnName().toLowerCase();
					tmpJson.put(columnName, column.toJsonObject());
				}
				
				String sql = "select fname as \"fname\",dname as \"dname\",strlen as \"strlen\",dtype as \"dtype\" from "
						+ (R ? "r_interface" : "interface") + " where frmtype='" + tableId + "'";
				
				StringBuffer sb = new StringBuffer();
				sb.append("select fname as \"fname\",dname as \"dname\",strlen as \"strlen\",dtype as \"dtype\" from ")//
				.append(R ? "r_interface" : "interface").append(" where frmtype = ");
				if (StringUtils.isNotBlank(tableId)) {
					sb.append("'").append(tableId).append("'");
					
				} else {
					sb.append("(").append("SELECT id as \"id\" FROM ").append(R ? "r_data_table" : "cell_data_table")
					.append(" where tablename ='").append(tableName).append("' )");
				}
				sql = sb.toString();
				
				// if (!RConstant.isOracle()) {
				List<Map<String, Object>> list = new QueryHelper().getResultList(systemName, sql,
						new HashMap<String, Object>());
				if (CollectionUtils.isNotEmpty(list)) {
					for (Map<String, Object> map : list) {
						columnName = map.get("dname").toString().toLowerCase();
						if (tmpJson.containsKey(columnName))
							tmp.put(columnName, map);
						map.put("sys", false);
					}
				}
				// }
			}


			for (ColumnDefinition column : columns) {
				columnName = column.getColumnName().toLowerCase();
				o = tmp.get(columnName);
				if (o != null) {
					jsonObject = new JSONObject(o);
				} else {
					jsonObject = new JSONObject(column.toJsonObject());
					jsonObject.put("fname", columnName);
					jsonObject.put("dname", columnName);
					jsonObject.put("strlen", column.getLength());
					jsonObject.put("dtype",
							column.getJavaType() == null ? "string" : column.getJavaType().toLowerCase());
				}
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray;
	}

}
