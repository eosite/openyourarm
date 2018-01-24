package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.mapper.FieldInterfaceMapper;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.mapper.CellDataTableMapper;
import com.glaf.isdp.query.CellDataTableQuery;
import com.glaf.isdp.service.CellDataTableService;

@Service("com.glaf.isdp.service.cellDataTableService")
@Transactional(readOnly = true)
public class CellDataTableServiceImpl implements CellDataTableService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellDataTableMapper cellDataTableMapper;
	
	protected FieldInterfaceMapper fileInterfaceMapper;
	
	public CellDataTableServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			cellDataTableMapper.deleteCellDataTableById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				cellDataTableMapper.deleteCellDataTableById(id);
			}
		}
	}

	public int count(CellDataTableQuery query) {
		query.ensureInitialized();
		return cellDataTableMapper.getCellDataTableCount(query);
	}

	public List<CellDataTable> list(CellDataTableQuery query) {
		query.ensureInitialized();
		List<CellDataTable> list = cellDataTableMapper.getCellDataTables(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellDataTableCountByQueryCriteria(CellDataTableQuery query) {
		return cellDataTableMapper.getCellDataTableCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellDataTable> getCellDataTablesByQueryCriteria(int start,
			int pageSize, CellDataTableQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellDataTable> rows = sqlSessionTemplate.selectList(
				"getCellDataTables", query, rowBounds);
		return rows;
	}
	
	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	@Override
	public List<CellDataTable> getCellDataTablesAndChildTablesByQueryCriteria(int start,
			int pageSize, CellDataTableQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellDataTable> rows = sqlSessionTemplate.selectList(
				"getCellDataTableAndChildTables", query, rowBounds);
		return rows;
	}
	
	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	@Override
	public int getCellDataTableCountAndChildTablesByQueryCriteria(CellDataTableQuery query) {
		return cellDataTableMapper.getCellDataTableAndChildTablesCount(query);
	}
	

	@Override
	public List<CellDataTable> getCellDataTablesByTreedotIndexId(int start, int pageSize, CellDataTableQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellDataTable> rows = sqlSessionTemplate.selectList(
				"getCellDataTablesByTreedotIndexId", query, rowBounds);
		return rows;
	}

	@Override
	public int getCellDataTablesCountByTreedotIndexId(CellDataTableQuery query) {
		return cellDataTableMapper.getCellDataTablesCountByTreedotIndexId(query);
	}

	public CellDataTable getCellDataTable(String id) {
		if (id == null) {
			return null;
		}
		CellDataTable cellDataTable = cellDataTableMapper
				.getCellDataTableById(id);
		return cellDataTable;
	}

	@Transactional
	public void save(CellDataTable cellDataTable) {
		if (StringUtils.isEmpty(cellDataTable.getId())) {
			String id = idGenerator.getNextId("cell_data_table", "id", cellDataTable.getUserId());
			cellDataTable.setId(id);
			// cellDataTable.setCreateDate(new Date());
			// cellDataTable.setDeleteFlag(0);
			cellDataTableMapper.insertCellDataTable(cellDataTable);
		} else {
			cellDataTableMapper.updateCellDataTable(cellDataTable);
		}
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate
					.getDataSource());
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
	public CellDataTable getCellDataTableByTableName(String tablename) {
		return cellDataTableMapper.getCellDataTableByTableName(tablename);
	}

	@Override
	public int getNextMaxUser() {
		return cellDataTableMapper.getNextMaxUser();
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellDataTableMapper")
	public void setCellDataTableMapper(CellDataTableMapper cellDataTableMapper) {
		this.cellDataTableMapper = cellDataTableMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public void setFileInterfaceMapper(FieldInterfaceMapper fileInterfaceMapper) {
		this.fileInterfaceMapper = fileInterfaceMapper;
	}

}
