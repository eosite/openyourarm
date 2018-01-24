package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

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
import com.glaf.isdp.domain.CellFillForm;
import com.glaf.isdp.mapper.CellFillFormMapper;
import com.glaf.isdp.query.CellFillFormQuery;
import com.glaf.isdp.service.CellFillFormService;

@Service("com.glaf.isdp.service.cellFillFormService")
@Transactional(readOnly = true)
public class CellFillFormServiceImpl implements CellFillFormService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellFillFormMapper cellFillFormMapper;

	public CellFillFormServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			cellFillFormMapper.deleteCellFillFormById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				cellFillFormMapper.deleteCellFillFormById(id);
			}
		}
	}

	public int count(CellFillFormQuery query) {
		query.ensureInitialized();
		return cellFillFormMapper.getCellFillFormCount(query);
	}

	public List<CellFillForm> list(CellFillFormQuery query) {
		query.ensureInitialized();
		List<CellFillForm> list = cellFillFormMapper.getCellFillForms(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellFillFormCountByQueryCriteria(CellFillFormQuery query) {
		return cellFillFormMapper.getCellFillFormCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellFillForm> getCellFillFormsByQueryCriteria(int start,
			int pageSize, CellFillFormQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellFillForm> rows = sqlSessionTemplate.selectList(
				"getCellFillForms", query, rowBounds);
		return rows;
	}

	public CellFillForm getCellFillForm(String id) {
		if (id == null) {
			return null;
		}
		CellFillForm cellFillForm = cellFillFormMapper.getCellFillFormById(id);
		return cellFillForm;
	}

	@Transactional
	public void save(CellFillForm cellFillForm) {
		if (StringUtils.isEmpty(cellFillForm.getId())) {
			cellFillForm.setId(idGenerator.getNextId("CELL_FILLFORM"));
			// cellFillForm.setCreateDate(new Date());
			// cellFillForm.setDeleteFlag(0);
			cellFillFormMapper.insertCellFillForm(cellFillForm);
		} else {
			cellFillFormMapper.updateCellFillForm(cellFillForm);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellFillFormMapper")
	public void setCellFillFormMapper(CellFillFormMapper cellFillFormMapper) {
		this.cellFillFormMapper = cellFillFormMapper;
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
	public List<Map<String, Integer>> selectCellSum(CellFillFormQuery query) {
		return cellFillFormMapper.selectCellSum(query);
	}
}
