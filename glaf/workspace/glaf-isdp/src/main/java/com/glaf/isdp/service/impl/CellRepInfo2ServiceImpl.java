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

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.isdp.domain.CellRepInfo2;
import com.glaf.isdp.mapper.CellRepInfo2Mapper;
import com.glaf.isdp.query.CellRepInfo2Query;
import com.glaf.isdp.service.CellRepInfo2Service;

@Service("com.glaf.isdp.service.cellRepInfo2Service")
@Transactional(readOnly = true)
public class CellRepInfo2ServiceImpl implements CellRepInfo2Service {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellRepInfo2Mapper cellRepInfo2Mapper;

	public CellRepInfo2ServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			cellRepInfo2Mapper.deleteCellRepInfo2ById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				cellRepInfo2Mapper.deleteCellRepInfo2ById(id);
			}
		}
	}

	public int count(CellRepInfo2Query query) {
		query.ensureInitialized();
		return cellRepInfo2Mapper.getCellRepInfo2Count(query);
	}

	public List<CellRepInfo2> list(CellRepInfo2Query query) {
		query.ensureInitialized();
		List<CellRepInfo2> list = cellRepInfo2Mapper.getCellRepInfo2s(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellRepInfo2CountByQueryCriteria(CellRepInfo2Query query) {
		return cellRepInfo2Mapper.getCellRepInfo2Count(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellRepInfo2> getCellRepInfo2sByQueryCriteria(int start,
			int pageSize, CellRepInfo2Query query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellRepInfo2> rows = sqlSessionTemplate.selectList(
				"getCellRepInfo2s", query, rowBounds);
		return rows;
	}

	public CellRepInfo2 getCellRepInfo2(String id) {
		if (id == null) {
			return null;
		}
		CellRepInfo2 cellRepInfo2 = cellRepInfo2Mapper.getCellRepInfo2ById(id);
		return cellRepInfo2;
	}

	@Transactional
	public void save(CellRepInfo2 cellRepInfo2) {
		if (StringUtils.isEmpty(cellRepInfo2.getId())) {
			cellRepInfo2.setId(idGenerator.getNextId("CELL_REPINFO2"));
			// cellRepInfo2.setCreateDate(new Date());
			// cellRepInfo2.setDeleteFlag(0);
			cellRepInfo2Mapper.insertCellRepInfo2(cellRepInfo2);
		} else {
			cellRepInfo2Mapper.updateCellRepInfo2(cellRepInfo2);
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

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellRepInfo2Mapper")
	public void setCellRepInfo2Mapper(CellRepInfo2Mapper cellRepInfo2Mapper) {
		this.cellRepInfo2Mapper = cellRepInfo2Mapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
