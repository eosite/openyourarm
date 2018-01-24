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
import com.glaf.isdp.domain.FlowActiv;
import com.glaf.isdp.mapper.FlowActivMapper;
import com.glaf.isdp.query.FlowActivQuery;
import com.glaf.isdp.service.FlowActivService;

@Service("com.glaf.isdp.service.flowActivService")
@Transactional(readOnly = true)
public class FlowActivServiceImpl implements FlowActivService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FlowActivMapper flowActivMapper;

	public FlowActivServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			flowActivMapper.deleteFlowActivById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				flowActivMapper.deleteFlowActivById(id);
			}
		}
	}

	public int count(FlowActivQuery query) {
		query.ensureInitialized();
		return flowActivMapper.getFlowActivCount(query);
	}

	public List<FlowActiv> list(FlowActivQuery query) {
		query.ensureInitialized();
		List<FlowActiv> list = flowActivMapper.getFlowActivs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFlowActivCountByQueryCriteria(FlowActivQuery query) {
		return flowActivMapper.getFlowActivCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FlowActiv> getFlowActivsByQueryCriteria(int start,
			int pageSize, FlowActivQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FlowActiv> rows = sqlSessionTemplate.selectList("getFlowActivs",
				query, rowBounds);
		return rows;
	}

	public FlowActiv getFlowActiv(String id) {
		if (id == null) {
			return null;
		}
		FlowActiv flowActiv = flowActivMapper.getFlowActivById(id);
		return flowActiv;
	}

	@Transactional
	public void save(FlowActiv flowActiv) {
		if (StringUtils.isEmpty(flowActiv.getId())) {
			flowActiv.setId(idGenerator.getNextId("FLOW_ACTIV"));
			// flowActiv.setCreateDate(new Date());
			// flowActiv.setDeleteFlag(0);
			flowActivMapper.insertFlowActiv(flowActiv);
		} else {
			flowActivMapper.updateFlowActiv(flowActiv);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FlowActivMapper")
	public void setFlowActivMapper(FlowActivMapper flowActivMapper) {
		this.flowActivMapper = flowActivMapper;
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
