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

@Service("com.glaf.monitor.server.service.monitorEventService")
@Transactional(readOnly = true) 
public class MonitorEventServiceImpl implements MonitorEventService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorEventMapper monitorEventMapper;

	public MonitorEventServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorEvent> list) {
		for (MonitorEvent monitorEvent : list) {
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		monitorEventMapper.deleteMonitorEventById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> eventIds) {
	    if(eventIds != null && !eventIds.isEmpty()){
		for(String id : eventIds){
		    monitorEventMapper.deleteMonitorEventById(id);
		}
	    }
	}

	public int count(MonitorEventQuery query) {
		return monitorEventMapper.getMonitorEventCount(query);
	}

	public List<MonitorEvent> list(MonitorEventQuery query) {
		List<MonitorEvent> list = monitorEventMapper.getMonitorEvents(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorEventCountByQueryCriteria(MonitorEventQuery query) {
		return monitorEventMapper.getMonitorEventCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorEvent> getMonitorEventsByQueryCriteria(int start, int pageSize,
			MonitorEventQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorEvent> rows = sqlSessionTemplate.selectList(
				"getMonitorEvents", query, rowBounds);
		return rows;
	}


	public MonitorEvent getMonitorEvent(String id) {
	        if(id == null){
		    return null;
		}
		MonitorEvent monitorEvent = monitorEventMapper.getMonitorEventById(id);
		return monitorEvent;
	}

	@Transactional
	public void save(MonitorEvent monitorEvent) {
           if (StringUtils.isEmpty(monitorEvent.getEventId())) {
	        monitorEvent.setEventId(idGenerator.getNextId("MONITOR_EVENT_"));
		//monitorEvent.setCreateDate(new Date());
		//monitorEvent.setDeleteFlag(0);
		monitorEventMapper.insertMonitorEvent(monitorEvent);
	       } else {
		monitorEventMapper.updateMonitorEvent(monitorEvent);
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

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorEventMapper")
	public void setMonitorEventMapper(MonitorEventMapper monitorEventMapper) {
		this.monitorEventMapper = monitorEventMapper;
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
