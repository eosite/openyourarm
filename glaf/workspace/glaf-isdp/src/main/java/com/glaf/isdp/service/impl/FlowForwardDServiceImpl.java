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
import com.glaf.isdp.domain.FlowForwardD;
import com.glaf.isdp.mapper.FlowForwardDMapper;
import com.glaf.isdp.query.FlowForwardDQuery;
import com.glaf.isdp.service.FlowForwardDService;

@Service("com.glaf.isdp.service.flowForwardDService")
@Transactional(readOnly = true)
public class FlowForwardDServiceImpl implements FlowForwardDService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FlowForwardDMapper flowForwardDMapper;

	public FlowForwardDServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			flowForwardDMapper.deleteFlowForwardDById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				flowForwardDMapper.deleteFlowForwardDById(id);
			}
		}
	}

	public int count(FlowForwardDQuery query) {
		query.ensureInitialized();
		return flowForwardDMapper.getFlowForwardDCount(query);
	}

	public List<FlowForwardD> list(FlowForwardDQuery query) {
		query.ensureInitialized();
		List<FlowForwardD> list = flowForwardDMapper.getFlowForwardDs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFlowForwardDCountByQueryCriteria(FlowForwardDQuery query) {
		return flowForwardDMapper.getFlowForwardDCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FlowForwardD> getFlowForwardDsByQueryCriteria(int start,
			int pageSize, FlowForwardDQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FlowForwardD> rows = sqlSessionTemplate.selectList(
				"getFlowForwardDs", query, rowBounds);
		return rows;
	}

	public FlowForwardD getFlowForwardD(String id) {
		if (id == null) {
			return null;
		}
		FlowForwardD flowForwardD = flowForwardDMapper.getFlowForwardDById(id);
		return flowForwardD;
	}

	@Transactional
	public void save(FlowForwardD flowForwardD) {
		if (StringUtils.isEmpty(flowForwardD.getId())) {
			flowForwardD.setId(idGenerator.getNextId("FLOW_FORWARD_D"));
			// flowForwardD.setCreateDate(new Date());
			// flowForwardD.setDeleteFlag(0);
			flowForwardDMapper.insertFlowForwardD(flowForwardD);
		} else {
			flowForwardDMapper.updateFlowForwardD(flowForwardD);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FlowForwardDMapper")
	public void setFlowForwardDMapper(FlowForwardDMapper flowForwardDMapper) {
		this.flowForwardDMapper = flowForwardDMapper;
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
