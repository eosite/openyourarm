package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.isdp.config.RConstant;
import com.glaf.isdp.domain.CellUTableTree;
import com.glaf.isdp.mapper.CellUTableTreeMapper;
import com.glaf.isdp.query.CellUTableTreeQuery;
import com.glaf.isdp.service.CellUTableTreeService;

@Service("com.glaf.isdp.service.cellUTableTreeService")
@Transactional(readOnly = true)
public class CellUTableTreeServiceImpl implements CellUTableTreeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellUTableTreeMapper cellUTableTreeMapper;

	public CellUTableTreeServiceImpl() {

	}

	@Transactional
	public void deleteByIndexId(Integer indexId) {
		if (indexId != null) {
			cellUTableTreeMapper.deleteCellUTableTreeByIndexId(indexId);
		}
	}

	@Transactional
	public void deleteByIndexIds(List<Integer> indexIds) {
		if (indexIds != null && !indexIds.isEmpty()) {
			for (Integer id : indexIds) {
				cellUTableTreeMapper.deleteCellUTableTreeByIndexId(id);
			}
		}
	}

	public int count(CellUTableTreeQuery query) {
		query.ensureInitialized();
		return cellUTableTreeMapper.getCellUTableTreeCount(query);
	}

	public List<CellUTableTree> list(CellUTableTreeQuery query) {
		query.ensureInitialized();
		List<CellUTableTree> list = cellUTableTreeMapper.getCellUTableTrees(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellUTableTreeCountByQueryCriteria(CellUTableTreeQuery query) {
		return cellUTableTreeMapper.getCellUTableTreeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellUTableTree> getCellUTableTreesByQueryCriteria(int start, int pageSize, CellUTableTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellUTableTree> rows = sqlSessionTemplate.selectList("getCellUTableTrees", query, rowBounds);
		return rows;
	}

	public CellUTableTree getCellUTableTree(Integer indexId) {
		if (indexId == null) {
			return null;
		}
		CellUTableTree cellUTableTree = cellUTableTreeMapper.getCellUTableTreeByIndexId(indexId);
		return cellUTableTree;
	}

	@Transactional
	public void save(CellUTableTree cellUTableTree) {
		if (cellUTableTree.getIndexId() == null) {
			// cellUTableTree.setIndexId(idGenerator.nextId("CELL_UTABLETREE").intValue());
			cellUTableTree.setIndexId(this.getMaxId());
			// cellUTableTree.setCreateDate(new Date());
			// cellUTableTree.setDeleteFlag(0);
			if (StringUtils.isEmpty(cellUTableTree.getId())) {
				cellUTableTree.setId(cellUTableTree.getIndexId() + "|");
			}
			cellUTableTreeMapper.insertCellUTableTree(cellUTableTree);
		} else {
			cellUTableTreeMapper.updateCellUTableTree(cellUTableTree);
		}
	}

	@Transactional
	private Integer getMaxId() {

		String sql = "SELECT MAX(INDEX_ID) MID FROM CELL_UTABLETREE";
		Connection connection = null;
		Statement psmt = null;
		ResultSet rs = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.createStatement();
			rs = psmt.executeQuery(sql);
			if (rs != null && rs.next()) {
				return rs.getInt(1) + 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("getMaxId error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rs);
			JdbcUtils.close(psmt);
		}
		return 1;
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

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellUTableTreeMapper")
	public void setCellUTableTreeMapper(CellUTableTreeMapper cellUTableTreeMapper) {
		this.cellUTableTreeMapper = cellUTableTreeMapper;
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
	public List<CellUTableTree> getAllChildCellUTableTrees(Integer indexId) {
		
		
		
	//	if(StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())){
		if(RConstant.isDM() || RConstant.isOracle()){
			return cellUTableTreeMapper.getAllChildCellUTableTrees_oracle(indexId);
		}
		return cellUTableTreeMapper.getAllChildCellUTableTrees(indexId);
	}

	@Override
	public List<CellUTableTree> getCellUTableTreesAndChildCountByTableType(Integer tableType, Integer level,
			Integer parentId) {
		return this.cellUTableTreeMapper.getCellUTableTreesAndChildCountByTableType(tableType, level, parentId);
	}
}
