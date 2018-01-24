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
import com.glaf.isdp.domain.FlowActivD;
import com.glaf.isdp.mapper.FlowActivDMapper;
import com.glaf.isdp.query.FlowActivDQuery;
import com.glaf.isdp.service.FlowActivDService;

@Service("com.glaf.isdp.service.flowActivDService")
@Transactional(readOnly = true)
public class FlowActivDServiceImpl implements FlowActivDService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FlowActivDMapper flowActivDMapper;

	public FlowActivDServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			flowActivDMapper.deleteFlowActivDById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				flowActivDMapper.deleteFlowActivDById(id);
			}
		}
	}

	public int count(FlowActivDQuery query) {
		query.ensureInitialized();
		return flowActivDMapper.getFlowActivDCount(query);
	}

	public List<FlowActivD> list(FlowActivDQuery query) {
		query.ensureInitialized();
		List<FlowActivD> list = flowActivDMapper.getFlowActivDs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFlowActivDCountByQueryCriteria(FlowActivDQuery query) {
		return flowActivDMapper.getFlowActivDCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FlowActivD> getFlowActivDsByQueryCriteria(int start,
			int pageSize, FlowActivDQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FlowActivD> rows = sqlSessionTemplate.selectList("getFlowActivDs",
				query, rowBounds);
		return rows;
	}

	public FlowActivD getFlowActivD(String id) {
		if (id == null) {
			return null;
		}
		FlowActivD flowActivD = flowActivDMapper.getFlowActivDById(id);
		return flowActivD;
	}

	@Transactional
	public void save(FlowActivD flowActivD) {
		if (StringUtils.isEmpty(flowActivD.getId())) {
			flowActivD.setId(idGenerator.getNextId("FLOW_ACTIV_D"));
			// flowActivD.setCreateDate(new Date());
			// flowActivD.setDeleteFlag(0);
			flowActivDMapper.insertFlowActivD(flowActivD);
		} else {
			flowActivDMapper.updateFlowActivD(flowActivD);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FlowActivDMapper")
	public void setFlowActivDMapper(FlowActivDMapper flowActivDMapper) {
		this.flowActivDMapper = flowActivDMapper;
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
	public FlowActivD getLastFlowActivDByProcessId(String processId) {
		return this.flowActivDMapper.getLastFlowActivDByProcessId(processId);
	}
}
