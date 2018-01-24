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
import com.glaf.isdp.domain.CellMyTask;
import com.glaf.isdp.mapper.CellMyTaskMapper;
import com.glaf.isdp.query.CellMyTaskQuery;
import com.glaf.isdp.service.CellMyTaskService;

@Service("com.glaf.isdp.service.cellMyTaskService")
@Transactional(readOnly = true)
public class CellMyTaskServiceImpl implements CellMyTaskService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellMyTaskMapper cellMyTaskMapper;

	public CellMyTaskServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			cellMyTaskMapper.deleteCellMyTaskById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				cellMyTaskMapper.deleteCellMyTaskById(id);
			}
		}
	}

	public int count(CellMyTaskQuery query) {
		query.ensureInitialized();
		return cellMyTaskMapper.getCellMyTaskCount(query);
	}

	public List<CellMyTask> list(CellMyTaskQuery query) {
		query.ensureInitialized();
		List<CellMyTask> list = cellMyTaskMapper.getCellMyTasks(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellMyTaskCountByQueryCriteria(CellMyTaskQuery query) {
		return cellMyTaskMapper.getCellMyTaskCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellMyTask> getCellMyTasksByQueryCriteria(int start,
			int pageSize, CellMyTaskQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellMyTask> rows = sqlSessionTemplate.selectList("getCellMyTasks",
				query, rowBounds);
		return rows;
	}

	public CellMyTask getCellMyTask(String id) {
		if (id == null) {
			return null;
		}
		CellMyTask cellMyTask = cellMyTaskMapper.getCellMyTaskById(id);
		return cellMyTask;
	}

	@Transactional
	public void save(CellMyTask cellMyTask) {
		if (StringUtils.isEmpty(cellMyTask.getId())) {
			cellMyTask.setId(idGenerator.getNextId("CELL_MYTASK"));
			// cellMyTask.setCreateDate(new Date());
			// cellMyTask.setDeleteFlag(0);
			cellMyTaskMapper.insertCellMyTask(cellMyTask);
		} else {
			cellMyTaskMapper.updateCellMyTask(cellMyTask);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellMyTaskMapper")
	public void setCellMyTaskMapper(CellMyTaskMapper cellMyTaskMapper) {
		this.cellMyTaskMapper = cellMyTaskMapper;
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
