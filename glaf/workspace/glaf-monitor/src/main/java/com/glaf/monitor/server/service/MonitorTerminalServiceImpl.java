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

@Service("com.glaf.monitor.server.service.monitorTerminalService")
@Transactional(readOnly = true) 
public class MonitorTerminalServiceImpl implements MonitorTerminalService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorTerminalMapper monitorTerminalMapper;
	@Autowired
	protected MonitorTerminalBusCategoryMapper monitorTerminalBusCategoryMapper;

	public MonitorTerminalServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorTerminal> list) {
		for (MonitorTerminal monitorTerminal : list) {
		   if (StringUtils.isEmpty(monitorTerminal.getId())) {
			monitorTerminal.setId(idGenerator.getNextId("MONITOR_TERMINAL"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		monitorTerminalMapper.deleteMonitorTerminalById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    monitorTerminalMapper.deleteMonitorTerminalById(id);
		}
	    }
	}

	public int count(MonitorTerminalQuery query) {
		return monitorTerminalMapper.getMonitorTerminalCount(query);
	}

	public List<MonitorTerminal> list(MonitorTerminalQuery query) {
		List<MonitorTerminal> list = monitorTerminalMapper.getMonitorTerminals(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorTerminalCountByQueryCriteria(MonitorTerminalQuery query) {
		return monitorTerminalMapper.getMonitorTerminalCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorTerminal> getMonitorTerminalsByQueryCriteria(int start, int pageSize,
			MonitorTerminalQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorTerminal> rows = sqlSessionTemplate.selectList(
				"getMonitorTerminals", query, rowBounds);
		return rows;
	}


	public MonitorTerminal getMonitorTerminal(String id) {
	        if(id == null){
		    return null;
		}
		MonitorTerminal monitorTerminal = monitorTerminalMapper.getMonitorTerminalById(id);
		return monitorTerminal;
	}

	@Transactional
	public void save(MonitorTerminal monitorTerminal) {
           if (StringUtils.isEmpty(monitorTerminal.getId())) {
	        monitorTerminal.setId(idGenerator.getNextId("MONITOR_TERMINAL"));
		//monitorTerminal.setCreateDate(new Date());
		//monitorTerminal.setDeleteFlag(0);
		monitorTerminalMapper.insertMonitorTerminal(monitorTerminal);
	       } else {
		monitorTerminalMapper.updateMonitorTerminal(monitorTerminal);
	      }
	}
	
	@Transactional
	public void save(MonitorTerminal monitorTerminal,Integer categoryId) {
       if (StringUtils.isEmpty(monitorTerminal.getId())) {
    	   String id = idGenerator.getNextId("MONITOR_TERMINAL");
	        monitorTerminal.setId(id);
		//monitorTerminal.setCreateDate(new Date());
		//monitorTerminal.setDeleteFlag(0);
	        monitorTerminalMapper.insertMonitorTerminal(monitorTerminal);
	        if(categoryId != null){
	        	MonitorTerminalBusCategory category = new MonitorTerminalBusCategory();
	        	category.setCategoryId(categoryId);
	        	category.setTerminalId(id);
	        	category.setCreateby(monitorTerminal.getCreateby());
	        	category.setCreatetime(monitorTerminal.getCreatetime());
	        	this.monitorTerminalBusCategoryMapper.insertMonitorTerminalBusCategory(category);
	        }
       } else {
    	   monitorTerminalMapper.updateMonitorTerminal(monitorTerminal);
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

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorTerminalMapper")
	public void setMonitorTerminalMapper(MonitorTerminalMapper monitorTerminalMapper) {
		this.monitorTerminalMapper = monitorTerminalMapper;
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
