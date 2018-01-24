package com.glaf.monitor.server.service;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.monitor.server.mapper.*;
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;

@Service("com.glaf.monitor.server.service.monitorTerminalBusCategoryService")
@Transactional(readOnly = true)
public class MonitorTerminalBusCategoryServiceImpl implements MonitorTerminalBusCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorTerminalBusCategoryMapper monitorTerminalBusCategoryMapper;

	public MonitorTerminalBusCategoryServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<MonitorTerminalBusCategory> list) {
		for (MonitorTerminalBusCategory monitorTerminalBusCategory : list) {
			if (StringUtils.isEmpty(monitorTerminalBusCategory.getTerminalId())) {
				monitorTerminalBusCategory.setTerminalId(idGenerator.getNextId("MONITOR_TERMINAL_BUS_CATEGORY"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			monitorTerminalBusCategoryMapper.deleteMonitorTerminalBusCategoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> terminalIds) {
		if (terminalIds != null && !terminalIds.isEmpty()) {
			for (String id : terminalIds) {
				monitorTerminalBusCategoryMapper.deleteMonitorTerminalBusCategoryById(id);
			}
		}
	}

	public int count(MonitorTerminalBusCategoryQuery query) {
		return monitorTerminalBusCategoryMapper.getMonitorTerminalBusCategoryCount(query);
	}

	public List<MonitorTerminalBusCategory> list(MonitorTerminalBusCategoryQuery query) {
		List<MonitorTerminalBusCategory> list = monitorTerminalBusCategoryMapper.getMonitorTerminalBusCategorys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getMonitorTerminalBusCategoryCountByQueryCriteria(MonitorTerminalBusCategoryQuery query) {
		return monitorTerminalBusCategoryMapper.getMonitorTerminalBusCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorTerminalBusCategory> getMonitorTerminalBusCategorysByQueryCriteria(int start, int pageSize,
			MonitorTerminalBusCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorTerminalBusCategory> rows = sqlSessionTemplate.selectList("getMonitorTerminalBusCategorys", query,
				rowBounds);
		return rows;
	}

	public MonitorTerminalBusCategory getMonitorTerminalBusCategory(String id) {
		if (id == null) {
			return null;
		}
		MonitorTerminalBusCategory monitorTerminalBusCategory = monitorTerminalBusCategoryMapper
				.getMonitorTerminalBusCategoryById(id);
		return monitorTerminalBusCategory;
	}

	@Transactional
	public void save(MonitorTerminalBusCategory monitorTerminalBusCategory) {
		if (StringUtils.isEmpty(monitorTerminalBusCategory.getTerminalId())) {
			monitorTerminalBusCategory.setTerminalId(idGenerator.getNextId("MONITOR_TERMINAL_BUS_CATEGORY"));
			// monitorTerminalBusCategory.setCreateDate(new Date());
			// monitorTerminalBusCategory.setDeleteFlag(0);
			monitorTerminalBusCategoryMapper.insertMonitorTerminalBusCategory(monitorTerminalBusCategory);
		} else {
			monitorTerminalBusCategoryMapper.updateMonitorTerminalBusCategory(monitorTerminalBusCategory);
		}
	}
	
	@Transactional
	public void save(String terminalId,String categoryIds) {
		//先删除后添加
		monitorTerminalBusCategoryMapper.deleteMonitorTerminalBusCategoryByTerminalId(terminalId);
		StringTokenizer token = new StringTokenizer(categoryIds, ",");
		List<Integer> categoryIdsAry = new ArrayList();
		while (token.hasMoreTokens()) {
			String x = token.nextToken();
			if (StringUtils.isNotEmpty(x)) {
				categoryIdsAry.add(Integer.parseInt(x));
			}
		}
		
		monitorTerminalBusCategoryMapper.blukInsertMonitorTerminalBusCategory(terminalId,categoryIdsAry);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorTerminalBusCategoryMapper")
	public void setMonitorTerminalBusCategoryMapper(MonitorTerminalBusCategoryMapper monitorTerminalBusCategoryMapper) {
		this.monitorTerminalBusCategoryMapper = monitorTerminalBusCategoryMapper;
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
