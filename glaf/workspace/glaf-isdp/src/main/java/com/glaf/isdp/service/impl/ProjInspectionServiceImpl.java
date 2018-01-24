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
import com.glaf.isdp.domain.ProjInspection;
import com.glaf.isdp.mapper.ProjInspectionMapper;
import com.glaf.isdp.query.ProjInspectionQuery;
import com.glaf.isdp.service.ProjInspectionService;

@Service("com.glaf.isdp.service.projInspectionService")
@Transactional(readOnly = true)
public class ProjInspectionServiceImpl implements ProjInspectionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ProjInspectionMapper projInspectionMapper;

	public ProjInspectionServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			projInspectionMapper.deleteProjInspectionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				projInspectionMapper.deleteProjInspectionById(id);
			}
		}
	}

	public int count(ProjInspectionQuery query) {
		query.ensureInitialized();
		return projInspectionMapper.getProjInspectionCount(query);
	}

	public List<ProjInspection> list(ProjInspectionQuery query) {
		query.ensureInitialized();
		List<ProjInspection> list = projInspectionMapper
				.getProjInspections(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getProjInspectionCountByQueryCriteria(ProjInspectionQuery query) {
		return projInspectionMapper.getProjInspectionCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ProjInspection> getProjInspectionsByQueryCriteria(int start,
			int pageSize, ProjInspectionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ProjInspection> rows = sqlSessionTemplate.selectList(
				"getProjInspections", query, rowBounds);
		return rows;
	}

	public ProjInspection getProjInspection(String id) {
		if (id == null) {
			return null;
		}
		ProjInspection projInspection = projInspectionMapper
				.getProjInspectionById(id);
		return projInspection;
	}

	@Transactional
	public void save(ProjInspection projInspection) {
		if (StringUtils.isEmpty(projInspection.getId())) {
			projInspection.setId(idGenerator.getNextId("PROJ_INSPECTION"));
			// projInspection.setCreateDate(new Date());
			// projInspection.setDeleteFlag(0);
			projInspectionMapper.insertProjInspection(projInspection);
		} else {
			projInspectionMapper.updateProjInspection(projInspection);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.ProjInspectionMapper")
	public void setProjInspectionMapper(
			ProjInspectionMapper projInspectionMapper) {
		this.projInspectionMapper = projInspectionMapper;
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
	public Integer getProjInspectionCountByTreepInfoIdLike(
			String treepInfoIdLike, String startDate, String endDate) {
		return projInspectionMapper.getProjInspectionCountByTreepInfoIdLike(treepInfoIdLike, startDate, endDate);
	}

	@Override
	public Integer countIntCheck(Integer intCheck) {
		return projInspectionMapper.countIntCheck(intCheck);
	}
}
