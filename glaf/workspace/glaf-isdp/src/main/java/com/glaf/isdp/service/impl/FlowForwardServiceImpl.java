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
import com.glaf.isdp.domain.FlowForward;
import com.glaf.isdp.mapper.FlowForwardMapper;
import com.glaf.isdp.query.FlowForwardQuery;
import com.glaf.isdp.service.FlowForwardService;

@Service("com.glaf.isdp.service.flowForwardService")
@Transactional(readOnly = true)
public class FlowForwardServiceImpl implements FlowForwardService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FlowForwardMapper flowForwardMapper;

	public FlowForwardServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			flowForwardMapper.deleteFlowForwardById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				flowForwardMapper.deleteFlowForwardById(id);
			}
		}
	}

	public int count(FlowForwardQuery query) {
		query.ensureInitialized();
		return flowForwardMapper.getFlowForwardCount(query);
	}

	public List<FlowForward> list(FlowForwardQuery query) {
		query.ensureInitialized();
		List<FlowForward> list = flowForwardMapper.getFlowForwards(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFlowForwardCountByQueryCriteria(FlowForwardQuery query) {
		return flowForwardMapper.getFlowForwardCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FlowForward> getFlowForwardsByQueryCriteria(int start,
			int pageSize, FlowForwardQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FlowForward> rows = sqlSessionTemplate.selectList(
				"getFlowForwards", query, rowBounds);
		return rows;
	}

	public FlowForward getFlowForward(String id) {
		if (id == null) {
			return null;
		}
		FlowForward flowForward = flowForwardMapper.getFlowForwardById(id);
		return flowForward;
	}

	@Transactional
	public void save(FlowForward flowForward) {
		if (StringUtils.isEmpty(flowForward.getId())) {
			flowForward.setId(idGenerator.getNextId("FLOW_FORWARD"));
			// flowForward.setCreateDate(new Date());
			// flowForward.setDeleteFlag(0);
			flowForwardMapper.insertFlowForward(flowForward);
		} else {
			flowForwardMapper.updateFlowForward(flowForward);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FlowForwardMapper")
	public void setFlowForwardMapper(FlowForwardMapper flowForwardMapper) {
		this.flowForwardMapper = flowForwardMapper;
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