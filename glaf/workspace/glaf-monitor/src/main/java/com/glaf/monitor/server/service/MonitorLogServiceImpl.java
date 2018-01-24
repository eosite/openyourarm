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

@Service("com.glaf.monitor.server.service.monitorLogService")
@Transactional(readOnly = true) 
public class MonitorLogServiceImpl implements MonitorLogService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorLogMapper monitorLogMapper;

	public MonitorLogServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorLog> list) {
		for (MonitorLog monitorLog : list) {
		   if (StringUtils.isEmpty(monitorLog.getId())) {
			monitorLog.setId(idGenerator.getNextId("MONITOR_LOG_"));
		   }
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		monitorLogMapper.deleteMonitorLogById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    monitorLogMapper.deleteMonitorLogById(id);
		}
	    }
	}

	public int count(MonitorLogQuery query) {
		return monitorLogMapper.getMonitorLogCount(query);
	}

	public List<MonitorLog> list(MonitorLogQuery query) {
		List<MonitorLog> list = monitorLogMapper.getMonitorLogs(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorLogCountByQueryCriteria(MonitorLogQuery query) {
		return monitorLogMapper.getMonitorLogCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorLog> getMonitorLogsByQueryCriteria(int start, int pageSize,
			MonitorLogQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorLog> rows = sqlSessionTemplate.selectList(
				"getMonitorLogs", query, rowBounds);
		return rows;
	}


	public MonitorLog getMonitorLog(String id) {
	        if(id == null){
		    return null;
		}
		MonitorLog monitorLog = monitorLogMapper.getMonitorLogById(id);
		return monitorLog;
	}

	@Transactional
	public void save(MonitorLog monitorLog) {
           if (StringUtils.isEmpty(monitorLog.getId())) {
	        monitorLog.setId(idGenerator.getNextId("MONITOR_LOG_"));
		//monitorLog.setCreateDate(new Date());
		//monitorLog.setDeleteFlag(0);
		monitorLogMapper.insertMonitorLog(monitorLog);
	       } else {
		monitorLogMapper.updateMonitorLog(monitorLog);
	      }
	}
	@Transactional
	public String createId() {
		return idGenerator.getNextId("MONITOR_LOG_");
	}
	@Transactional
	public void add(MonitorLog monitorLog) {
		monitorLogMapper.insertMonitorLog(monitorLog);
	}
	
	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorLogMapper")
	public void setMonitorLogMapper(MonitorLogMapper monitorLogMapper) {
		this.monitorLogMapper = monitorLogMapper;
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
