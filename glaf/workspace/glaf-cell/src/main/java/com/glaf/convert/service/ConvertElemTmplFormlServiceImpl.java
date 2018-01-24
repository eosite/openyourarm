package com.glaf.convert.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

import com.glaf.convert.domain.ConvertElemTmplForml;
import com.glaf.convert.domain.ConvertElemTmplFormlExt;
import com.glaf.convert.mapper.ConvertElemTmplFormlMapper;
import com.glaf.convert.query.ConvertElemTmplFormlQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.convertElemTmplFormlService")
@Transactional(readOnly = true)
public class ConvertElemTmplFormlServiceImpl implements
		ConvertElemTmplFormlService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertElemTmplFormlMapper convertElemTmplFormlMapper;

	public ConvertElemTmplFormlServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			convertElemTmplFormlMapper.deleteConvertElemTmplFormlById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> formlRuleIds) {
		if (formlRuleIds != null && !formlRuleIds.isEmpty()) {
			for (Long id : formlRuleIds) {
				convertElemTmplFormlMapper.deleteConvertElemTmplFormlById(id);
			}
		}
	}

	public int count(ConvertElemTmplFormlQuery query) {
		return convertElemTmplFormlMapper.getConvertElemTmplFormlCount(query);
	}

	public List<ConvertElemTmplForml> list(ConvertElemTmplFormlQuery query) {
		List<ConvertElemTmplForml> list = convertElemTmplFormlMapper
				.getConvertElemTmplFormls(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getConvertElemTmplFormlCountByQueryCriteria(
			ConvertElemTmplFormlQuery query) {
		return convertElemTmplFormlMapper.getConvertElemTmplFormlCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ConvertElemTmplForml> getConvertElemTmplFormlsByQueryCriteria(
			int start, int pageSize, ConvertElemTmplFormlQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ConvertElemTmplForml> rows = sqlSessionTemplate.selectList(
				"getConvertElemTmplFormls", query, rowBounds);
		return rows;
	}

	public ConvertElemTmplForml getConvertElemTmplForml(Long id) {
		if (id == null) {
			return null;
		}
		ConvertElemTmplForml convertElemTmplForml = convertElemTmplFormlMapper
				.getConvertElemTmplFormlById(id);
		return convertElemTmplForml;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void save(ConvertElemTmplForml convertElemTmplForml) {
		if (convertElemTmplForml.getFormlRuleId() == null) {
			convertElemTmplForml.setFormlRuleId(idGenerator
					.nextId("CVT_ELEM_TMPL_FORML"));
			// convertElemTmplForml.setCreateDate(new Date());
			// convertElemTmplForml.setDeleteFlag(0);
			convertElemTmplFormlMapper
					.insertConvertElemTmplForml(convertElemTmplForml);
		} else {
			convertElemTmplFormlMapper
					.updateConvertElemTmplForml(convertElemTmplForml);
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.ConvertElemTmplFormlMapper")
	public void setConvertElemTmplFormlMapper(
			ConvertElemTmplFormlMapper convertElemTmplFormlMapper) {
		this.convertElemTmplFormlMapper = convertElemTmplFormlMapper;
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
	public List<ConvertElemTmplForml> getConvertElemTmplFormlsByCvtId(Long cvtId) {
		return convertElemTmplFormlMapper
				.getConvertElemTmplFormlsByCvtId(cvtId);
	}

	@Override
	public List<ConvertElemTmplFormlExt> getAllConvertElemFormlExts() {
		return convertElemTmplFormlMapper.getAllConvertElemFormlExts();
	}

}
