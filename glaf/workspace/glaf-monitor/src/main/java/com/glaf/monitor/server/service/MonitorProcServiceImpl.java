package com.glaf.monitor.server.service;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service("com.glaf.monitor.server.service.monitorProcService")
@Transactional(readOnly = true)
public class MonitorProcServiceImpl implements MonitorProcService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	protected MonitorProcBusCategoryMapper monitorProcBusCategoryMapper;

	protected MonitorProcMapper monitorProcMapper;

	public MonitorProcServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<MonitorProc> list) {
		for (MonitorProc monitorProc : list) {
			if (StringUtils.isEmpty(monitorProc.getId())) {
				monitorProc.setId(idGenerator.getNextId("MONITOR_PROC"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			monitorProcMapper.deleteMonitorProcById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				monitorProcMapper.deleteMonitorProcById(id);
			}
		}
	}

	public int count(MonitorProcQuery query) {
		return monitorProcMapper.getMonitorProcCount(query);
	}

	public List<MonitorProc> list(MonitorProcQuery query) {
		List<MonitorProc> list = monitorProcMapper.getMonitorProcs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getMonitorProcCountByQueryCriteria(MonitorProcQuery query) {
		return monitorProcMapper.getMonitorProcCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorProc> getMonitorProcsByQueryCriteria(int start, int pageSize, MonitorProcQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorProc> rows = sqlSessionTemplate.selectList("getMonitorProcs", query, rowBounds);
		return rows;
	}

	public MonitorProc getMonitorProc(String id) {
		if (id == null) {
			return null;
		}
		MonitorProc monitorProc = monitorProcMapper.getMonitorProcById(id);
		return monitorProc;
	}

	@Transactional
	public void save(MonitorProc monitorProc) {
		if (StringUtils.isEmpty(monitorProc.getId())) {
			monitorProc.setId(idGenerator.getNextId("MONITOR_PROC"));
			// monitorProc.setCreateDate(new Date());
			// monitorProc.setDeleteFlag(0);
			monitorProcMapper.insertMonitorProc(monitorProc);
		} else {
			monitorProcMapper.updateMonitorProc(monitorProc);
		}
	}

	@Transactional
	public void save(MonitorProc monitorProc, Integer categoryId) {
		if (StringUtils.isEmpty(monitorProc.getId())) {
			String id = idGenerator.getNextId("MONITOR_PROC");
			monitorProc.setId(id);
			monitorProcMapper.insertMonitorProc(monitorProc);
			
			if(categoryId != null){
	        	MonitorProcBusCategory category = new MonitorProcBusCategory();
	        	category.setProcCategoryId(categoryId);
	        	category.setId(id);
	        	category.setCreateby(monitorProc.getCreateby());
	        	category.setCreatetime(monitorProc.getCreatetime());
	        	this.monitorProcBusCategoryMapper.insertMonitorProcBusCategory(category);
	        }
		} else {
			monitorProcMapper.updateMonitorProc(monitorProc);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorProcMapper")
	public void setMonitorProcMapper(MonitorProcMapper monitorProcMapper) {
		this.monitorProcMapper = monitorProcMapper;
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
