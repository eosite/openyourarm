package com.glaf.convert.service;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertPageTmpl;
import com.glaf.convert.mapper.ConvertPageTmplMapper;
import com.glaf.convert.query.ConvertPageTmplQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.convertPageTmplService")
@Transactional(readOnly = true)
public class ConvertPageTmplServiceImpl implements ConvertPageTmplService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertPageTmplMapper convertPageTmplMapper;

	public ConvertPageTmplServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			convertPageTmplMapper.deleteConvertPageTmplById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> cvtIds) {
		if (cvtIds != null && !cvtIds.isEmpty()) {
			for (Long id : cvtIds) {
				convertPageTmplMapper.deleteConvertPageTmplById(id);
			}
		}
	}

	public int count(ConvertPageTmplQuery query) {
		return convertPageTmplMapper.getConvertPageTmplCount(query);
	}

	public List<ConvertPageTmpl> list(ConvertPageTmplQuery query) {
		List<ConvertPageTmpl> list = convertPageTmplMapper
				.getConvertPageTmpls(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getConvertPageTmplCountByQueryCriteria(ConvertPageTmplQuery query) {
		return convertPageTmplMapper.getConvertPageTmplCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ConvertPageTmpl> getConvertPageTmplsByQueryCriteria(int start,
			int pageSize, ConvertPageTmplQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ConvertPageTmpl> rows = sqlSessionTemplate.selectList(
				"getConvertPageTmpls", query, rowBounds);
		return rows;
	}

	public ConvertPageTmpl getConvertPageTmpl(Long id) {
		if (id == null) {
			return null;
		}
		ConvertPageTmpl convertPageTmpl = convertPageTmplMapper
				.getConvertPageTmplById(id);
		return convertPageTmpl;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void save(ConvertPageTmpl convertPageTmpl) {
		if (convertPageTmpl.getCvtId() == null) {
			convertPageTmpl.setCvtId(idGenerator.nextId("CVT_PAGE_TMPL"));
			convertPageTmpl.setCreateDatetime(new Date());
			convertPageTmpl.setModifyDatetime(new Date());
			// convertPageTmpl.setCreateDate(new Date());
			// convertPageTmpl.setDeleteFlag(0);
			convertPageTmplMapper.insertConvertPageTmpl(convertPageTmpl);
		} else {
			convertPageTmpl.setModifyDatetime(new Date());
			convertPageTmplMapper.updateConvertPageTmpl(convertPageTmpl);
		}
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public void updatePageTemplate(long cvtId,byte[] pageTemplate){
		convertPageTmplMapper.updatePageTemplate(cvtId,pageTemplate);
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.ConvertPageTmplMapper")
	public void setConvertPageTmplMapper(
			ConvertPageTmplMapper convertPageTmplMapper) {
		this.convertPageTmplMapper = convertPageTmplMapper;
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
