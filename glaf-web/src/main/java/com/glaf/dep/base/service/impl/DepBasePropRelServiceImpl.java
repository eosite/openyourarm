package com.glaf.dep.base.service.impl;

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
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.dep.base.domain.DepBasePropRel;
import com.glaf.dep.base.mapper.DepBasePropRelMapper;
import com.glaf.dep.base.query.DepBasePropRelQuery;
import com.glaf.dep.base.service.DepBasePropRelService;

@Service("com.glaf.dep.base.service.depBasePropRelService")
@Transactional(readOnly = true)
public class DepBasePropRelServiceImpl implements DepBasePropRelService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBasePropRelMapper depBasePropRelMapper;

	public DepBasePropRelServiceImpl() {

	}

	@Transactional
	public void deleteByRuleId(String id) {
		if (id != null) {
			depBasePropRelMapper.deleteByRuleId(id);
		}
	}

	@Transactional
	@Override
	public void deleteByPrimaryKey(String ruleId, String relRuleId) {
		if (ruleId != null && relRuleId != null) {
			depBasePropRelMapper.deleteByPrimaryKey(ruleId, relRuleId);
		}
	}

	public int count(DepBasePropRelQuery query) {
		return depBasePropRelMapper.getDepBasePropRelCount(query);
	}

	public List<DepBasePropRel> list(DepBasePropRelQuery query) {
		List<DepBasePropRel> list = depBasePropRelMapper
				.getDepBasePropRels(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBasePropRelCountByQueryCriteria(DepBasePropRelQuery query) {
		return depBasePropRelMapper.getDepBasePropRelCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBasePropRel> getDepBasePropRelsByQueryCriteria(int start,
			int pageSize, DepBasePropRelQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBasePropRel> rows = sqlSessionTemplate.selectList(
				"getDepBasePropRels", query, rowBounds);
		return rows;
	}

	public DepBasePropRel getDepBasePropRel(String id) {
		if (id == null) {
			return null;
		}
		DepBasePropRel depBasePropRel = depBasePropRelMapper
				.getDepBasePropRelById(id);
		return depBasePropRel;
	}

	@Transactional
	public void save(DepBasePropRel depBasePropRel) {
		// 先清除数据
		depBasePropRelMapper.deleteByRuleId(depBasePropRel.getRuleId());
		// 保存
		depBasePropRelMapper.insertDepBasePropRel(depBasePropRel);
	}

	@Override
	public void save(String ruleId, List<DepBasePropRel> saveList) {
		// 先清除数据
		depBasePropRelMapper.deleteByRuleId(ruleId);
		// 保存
		for (DepBasePropRel depBasePropRel : saveList) {
			depBasePropRelMapper.insertDepBasePropRel(depBasePropRel);
		}
	}

	@Override
	public void insertDepBasePropRel(DepBasePropRel depBasePropRel) {
		depBasePropRelMapper.insertDepBasePropRel(depBasePropRel);
	}

	@Override
	public void updateDepBasePropRel(DepBasePropRel depBasePropRel) {
		depBasePropRelMapper.updateDepBasePropRel(depBasePropRel);
	}

	@Override
	public DepBasePropRel getDepBasePropRelByPrimaryKey(String ruleId,
			String relRuleId) {
		return depBasePropRelMapper.getDepBasePropRelByPrimaryKey(ruleId,relRuleId);
	}
	
	@Override
	public List<DepBasePropRel> getDepBasePropRelsByRuleId(String ruleId,int start,int pageSize){
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBasePropRel> rows = sqlSessionTemplate.selectList(
				"selectDepBasePropRelByRuleId", ruleId, rowBounds);
		return rows;
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBasePropRelMapper")
	public void setDepBasePropRelMapper(
			DepBasePropRelMapper depBasePropRelMapper) {
		this.depBasePropRelMapper = depBasePropRelMapper;
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
