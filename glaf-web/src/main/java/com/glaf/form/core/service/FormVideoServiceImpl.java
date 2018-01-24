package com.glaf.form.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

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
import com.glaf.form.core.domain.FormVideo;
import com.glaf.form.core.mapper.FormVideoMapper;
import com.glaf.form.core.query.FormVideoQuery;

@Service("com.glaf.form.core.service.formVideoService")
@Transactional(readOnly = true)
public class FormVideoServiceImpl implements FormVideoService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormVideoMapper formVideoMapper;

	public FormVideoServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			formVideoMapper.deleteFormVideoById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				formVideoMapper.deleteFormVideoById(id);
			}
		}
	}

	public int count(FormVideoQuery query) {
		return formVideoMapper.getFormVideoCount(query);
	}

	public List<FormVideo> list(FormVideoQuery query) {
		List<FormVideo> list = formVideoMapper.getFormVideos(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormVideoCountByQueryCriteria(FormVideoQuery query) {
		return formVideoMapper.getFormVideoCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormVideo> getFormVideosByQueryCriteria(int start,
			int pageSize, FormVideoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormVideo> rows = sqlSessionTemplate.selectList("getFormVideos",
				query, rowBounds);
		return rows;
	}

	public FormVideo getFormVideo(Long id) {
		if (id == null) {
			return null;
		}
		FormVideo formVideo = formVideoMapper.getFormVideoById(id);
		return formVideo;
	}

	@Transactional
	public void save(FormVideo formVideo) {
		if (formVideo.getId() == null) {
			formVideo.setId(idGenerator.nextId("FORM_VIDEO"));
			formVideo.setCreateDate(new Date());
			formVideoMapper.insertFormVideo(formVideo);
		} else {
			formVideo.setUpdateDate(new Date());
			formVideoMapper.updateFormVideo(formVideo);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormVideoMapper")
	public void setFormVideoMapper(FormVideoMapper formVideoMapper) {
		this.formVideoMapper = formVideoMapper;
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
