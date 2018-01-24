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
import com.glaf.isdp.domain.FlowProcessD;
import com.glaf.isdp.mapper.FlowProcessDMapper;
import com.glaf.isdp.query.FlowProcessDQuery;
import com.glaf.isdp.service.FlowProcessDService;

@Service("com.glaf.isdp.service.flowProcessDService")
@Transactional(readOnly = true)
public class FlowProcessDServiceImpl implements FlowProcessDService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FlowProcessDMapper flowProcessDMapper;

	public FlowProcessDServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			flowProcessDMapper.deleteFlowProcessDById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				flowProcessDMapper.deleteFlowProcessDById(id);
			}
		}
	}

	public int count(FlowProcessDQuery query) {
		query.ensureInitialized();
		return flowProcessDMapper.getFlowProcessDCount(query);
	}

	public List<FlowProcessD> list(FlowProcessDQuery query) {
		query.ensureInitialized();
		List<FlowProcessD> list = flowProcessDMapper.getFlowProcessDs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFlowProcessDCountByQueryCriteria(FlowProcessDQuery query) {
		return flowProcessDMapper.getFlowProcessDCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FlowProcessD> getFlowProcessDsByQueryCriteria(int start,
			int pageSize, FlowProcessDQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FlowProcessD> rows = sqlSessionTemplate.selectList(
				"getFlowProcessDs", query, rowBounds);
		return rows;
	}

	public FlowProcessD getFlowProcessD(String id) {
		if (id == null) {
			return null;
		}
		FlowProcessD flowProcessD = flowProcessDMapper.getFlowProcessDById(id);
		return flowProcessD;
	}

	@Transactional
	public void save(FlowProcessD flowProcessD) {
		if (StringUtils.isEmpty(flowProcessD.getId())) {
			flowProcessD.setId(idGenerator.getNextId("FLOW_PROCESS_D"));
			// flowProcessD.setCreateDate(new Date());
			// flowProcessD.setDeleteFlag(0);
			flowProcessDMapper.insertFlowProcessD(flowProcessD);
		} else {
			flowProcessDMapper.updateFlowProcessD(flowProcessD);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FlowProcessDMapper")
	public void setFlowProcessDMapper(FlowProcessDMapper flowProcessDMapper) {
		this.flowProcessDMapper = flowProcessDMapper;
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
