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
import com.glaf.isdp.domain.ProjCellAndFiles;
import com.glaf.isdp.mapper.ProjCellAndFilesMapper;
import com.glaf.isdp.query.ProjCellAndFilesQuery;
import com.glaf.isdp.service.ProjCellAndFilesService;

@Service("com.glaf.isdp.service.projCellAndFilesService")
@Transactional(readOnly = true)
public class ProjCellAndFilesServiceImpl implements ProjCellAndFilesService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ProjCellAndFilesMapper projCellAndFilesMapper;

	public ProjCellAndFilesServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			projCellAndFilesMapper.deleteProjCellAndFilesById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				projCellAndFilesMapper.deleteProjCellAndFilesById(id);
			}
		}
	}

	public int count(ProjCellAndFilesQuery query) {
		query.ensureInitialized();
		return projCellAndFilesMapper.getProjCellAndFilesCount(query);
	}

	public List<ProjCellAndFiles> list(ProjCellAndFilesQuery query) {
		query.ensureInitialized();
		List<ProjCellAndFiles> list = projCellAndFilesMapper
				.getProjCellAndFiless(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getProjCellAndFilesCountByQueryCriteria(
			ProjCellAndFilesQuery query) {
		return projCellAndFilesMapper.getProjCellAndFilesCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ProjCellAndFiles> getProjCellAndFilessByQueryCriteria(
			int start, int pageSize, ProjCellAndFilesQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ProjCellAndFiles> rows = sqlSessionTemplate.selectList(
				"getProjCellAndFiless", query, rowBounds);
		return rows;
	}

	public ProjCellAndFiles getProjCellAndFiles(String id) {
		if (id == null) {
			return null;
		}
		ProjCellAndFiles projCellAndFiles = projCellAndFilesMapper
				.getProjCellAndFilesById(id);
		return projCellAndFiles;
	}

	@Transactional
	public void save(ProjCellAndFiles projCellAndFiles) {
		if (StringUtils.isEmpty(projCellAndFiles.getId())) {
			projCellAndFiles.setId(idGenerator.getNextId("PROJ_CELLANDFILES"));
			// projCellAndFiles.setCreateDate(new Date());
			// projCellAndFiles.setDeleteFlag(0);
			projCellAndFilesMapper.insertProjCellAndFiles(projCellAndFiles);
		} else {
			projCellAndFilesMapper.updateProjCellAndFiles(projCellAndFiles);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.ProjCellAndFilesMapper")
	public void setProjCellAndFilesMapper(
			ProjCellAndFilesMapper projCellAndFilesMapper) {
		this.projCellAndFilesMapper = projCellAndFilesMapper;
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
	public List<ProjCellAndFiles> getProjCellList(Integer indexId) {
		return projCellAndFilesMapper.getProjCellList(indexId);
	}

}
