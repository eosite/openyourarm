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
import com.glaf.isdp.domain.FlowStation;
import com.glaf.isdp.mapper.FlowStationMapper;
import com.glaf.isdp.query.FlowStationQuery;
import com.glaf.isdp.service.FlowStationService;

@Service("com.glaf.isdp.service.flowStationService")
@Transactional(readOnly = true)
public class FlowStationServiceImpl implements FlowStationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FlowStationMapper flowStationMapper;

	public FlowStationServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			flowStationMapper.deleteFlowStationById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				flowStationMapper.deleteFlowStationById(id);
			}
		}
	}

	public int count(FlowStationQuery query) {
		query.ensureInitialized();
		return flowStationMapper.getFlowStationCount(query);
	}

	public List<FlowStation> list(FlowStationQuery query) {
		query.ensureInitialized();
		List<FlowStation> list = flowStationMapper.getFlowStations(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFlowStationCountByQueryCriteria(FlowStationQuery query) {
		return flowStationMapper.getFlowStationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FlowStation> getFlowStationsByQueryCriteria(int start,
			int pageSize, FlowStationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FlowStation> rows = sqlSessionTemplate.selectList(
				"getFlowStations", query, rowBounds);
		return rows;
	}

	public FlowStation getFlowStation(String id) {
		if (id == null) {
			return null;
		}
		FlowStation flowStation = flowStationMapper.getFlowStationById(id);
		return flowStation;
	}

	@Transactional
	public void save(FlowStation flowStation) {
		if (StringUtils.isEmpty(flowStation.getId())) {
			flowStation.setId(idGenerator.getNextId("FLOW_STATION"));
			// flowStation.setCreateDate(new Date());
			// flowStation.setDeleteFlag(0);
			flowStationMapper.insertFlowStation(flowStation);
		} else {
			flowStationMapper.updateFlowStation(flowStation);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FlowStationMapper")
	public void setFlowStationMapper(FlowStationMapper flowStationMapper) {
		this.flowStationMapper = flowStationMapper;
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
