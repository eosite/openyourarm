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
import com.glaf.isdp.domain.FlowProcess;
import com.glaf.isdp.mapper.FlowProcessMapper;
import com.glaf.isdp.query.FlowProcessQuery;
import com.glaf.isdp.service.FlowProcessService;

@Service("com.glaf.isdp.service.flowProcessService")
@Transactional(readOnly = true)
public class FlowProcessServiceImpl implements FlowProcessService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FlowProcessMapper flowProcessMapper;

	public FlowProcessServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			flowProcessMapper.deleteFlowProcessById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				flowProcessMapper.deleteFlowProcessById(id);
			}
		}
	}

	public int count(FlowProcessQuery query) {
		query.ensureInitialized();
		return flowProcessMapper.getFlowProcessCount(query);
	}

	public List<FlowProcess> list(FlowProcessQuery query) {
		query.ensureInitialized();
		List<FlowProcess> list = flowProcessMapper.getFlowProcesss(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFlowProcessCountByQueryCriteria(FlowProcessQuery query) {
		return flowProcessMapper.getFlowProcessCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FlowProcess> getFlowProcesssByQueryCriteria(int start,
			int pageSize, FlowProcessQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FlowProcess> rows = sqlSessionTemplate.selectList(
				"getFlowProcesss", query, rowBounds);
		return rows;
	}

	public FlowProcess getFlowProcess(String id) {
		if (id == null) {
			return null;
		}
		FlowProcess flowProcess = flowProcessMapper.getFlowProcessById(id);
		return flowProcess;
	}

	@Transactional
	public void save(FlowProcess flowProcess) {
		if (StringUtils.isEmpty(flowProcess.getId())) {
			flowProcess.setId(idGenerator.getNextId("FLOW_PROCESS"));
			// flowProcess.setCreateDate(new Date());
			// flowProcess.setDeleteFlag(0);
			flowProcessMapper.insertFlowProcess(flowProcess);
		} else {
			flowProcessMapper.updateFlowProcess(flowProcess);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FlowProcessMapper")
	public void setFlowProcessMapper(FlowProcessMapper flowProcessMapper) {
		this.flowProcessMapper = flowProcessMapper;
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
