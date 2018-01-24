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
import com.glaf.isdp.domain.CellMyTaskMain;
import com.glaf.isdp.mapper.CellMyTaskMainMapper;
import com.glaf.isdp.query.CellMyTaskMainQuery;
import com.glaf.isdp.service.CellMyTaskMainService;

@Service("com.glaf.isdp.service.cellMyTaskMainService")
@Transactional(readOnly = true)
public class CellMyTaskMainServiceImpl implements CellMyTaskMainService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellMyTaskMainMapper cellMyTaskMainMapper;

	public CellMyTaskMainServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			cellMyTaskMainMapper.deleteCellMyTaskMainById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				cellMyTaskMainMapper.deleteCellMyTaskMainById(id);
			}
		}
	}

	public int count(CellMyTaskMainQuery query) {
		query.ensureInitialized();
		return cellMyTaskMainMapper.getCellMyTaskMainCount(query);
	}

	public List<CellMyTaskMain> list(CellMyTaskMainQuery query) {
		query.ensureInitialized();
		List<CellMyTaskMain> list = cellMyTaskMainMapper
				.getCellMyTaskMains(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellMyTaskMainCountByQueryCriteria(CellMyTaskMainQuery query) {
		return cellMyTaskMainMapper.getCellMyTaskMainCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellMyTaskMain> getCellMyTaskMainsByQueryCriteria(int start,
			int pageSize, CellMyTaskMainQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellMyTaskMain> rows = sqlSessionTemplate.selectList(
				"getCellMyTaskMains", query, rowBounds);
		return rows;
	}

	public CellMyTaskMain getCellMyTaskMain(String id) {
		if (id == null) {
			return null;
		}
		CellMyTaskMain cellMyTaskMain = cellMyTaskMainMapper
				.getCellMyTaskMainById(id);
		return cellMyTaskMain;
	}

	@Transactional
	public void save(CellMyTaskMain cellMyTaskMain) {
		if (StringUtils.isEmpty(cellMyTaskMain.getId())) {
			cellMyTaskMain.setId(idGenerator.getNextId("CELL_MYTASKMAIN"));
			// cellMyTaskMain.setCreateDate(new Date());
			// cellMyTaskMain.setDeleteFlag(0);
			cellMyTaskMainMapper.insertCellMyTaskMain(cellMyTaskMain);
		} else {
			cellMyTaskMainMapper.updateCellMyTaskMain(cellMyTaskMain);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellMyTaskMainMapper")
	public void setCellMyTaskMainMapper(
			CellMyTaskMainMapper cellMyTaskMainMapper) {
		this.cellMyTaskMainMapper = cellMyTaskMainMapper;
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
	public List<CellMyTaskMain> selectCellMyTaskMainExtProcess(
			CellMyTaskMainQuery query) {
		return cellMyTaskMainMapper.selectCellMyTaskMainExtProcess(query);
	}

	@Override
	public CellMyTaskMain selectCellMyTaskMainByProcessId(String processId) {
		return cellMyTaskMainMapper.selectCellMyTaskMainByProcessId(processId);
	}
}
