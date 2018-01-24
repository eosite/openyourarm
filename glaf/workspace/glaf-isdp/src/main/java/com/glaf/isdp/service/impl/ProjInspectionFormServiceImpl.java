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
import com.glaf.isdp.domain.ProjInspectionForm;
import com.glaf.isdp.mapper.ProjInspectionFormMapper;
import com.glaf.isdp.query.ProjInspectionFormQuery;
import com.glaf.isdp.service.ProjInspectionFormService;

@Service("com.glaf.isdp.service.projInspectionFormService")
@Transactional(readOnly = true)
public class ProjInspectionFormServiceImpl implements ProjInspectionFormService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ProjInspectionFormMapper projInspectionFormMapper;

	public ProjInspectionFormServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			projInspectionFormMapper.deleteProjInspectionFormById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				projInspectionFormMapper.deleteProjInspectionFormById(id);
			}
		}
	}

	public int count(ProjInspectionFormQuery query) {
		query.ensureInitialized();
		return projInspectionFormMapper.getProjInspectionFormCount(query);
	}

	public List<ProjInspectionForm> list(ProjInspectionFormQuery query) {
		query.ensureInitialized();
		List<ProjInspectionForm> list = projInspectionFormMapper
				.getProjInspectionForms(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getProjInspectionFormCountByQueryCriteria(
			ProjInspectionFormQuery query) {
		return projInspectionFormMapper.getProjInspectionFormCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ProjInspectionForm> getProjInspectionFormsByQueryCriteria(
			int start, int pageSize, ProjInspectionFormQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ProjInspectionForm> rows = sqlSessionTemplate.selectList(
				"getProjInspectionForms", query, rowBounds);
		return rows;
	}

	public ProjInspectionForm getProjInspectionForm(String id) {
		if (id == null) {
			return null;
		}
		ProjInspectionForm projInspectionForm = projInspectionFormMapper
				.getProjInspectionFormById(id);
		return projInspectionForm;
	}

	@Transactional
	public void save(ProjInspectionForm projInspectionForm) {
		if (StringUtils.isEmpty(projInspectionForm.getId())) {
			projInspectionForm.setId(idGenerator
					.getNextId("PROJ_INSPECTION_FORM"));
			// projInspectionForm.setCreateDate(new Date());
			// projInspectionForm.setDeleteFlag(0);
			projInspectionFormMapper
					.insertProjInspectionForm(projInspectionForm);
		} else {
			projInspectionFormMapper
					.updateProjInspectionForm(projInspectionForm);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.ProjInspectionFormMapper")
	public void setProjInspectionFormMapper(
			ProjInspectionFormMapper projInspectionFormMapper) {
		this.projInspectionFormMapper = projInspectionFormMapper;
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
