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

@Service("com.glaf.monitor.server.service.monitorTerminalItemService")
@Transactional(readOnly = true) 
public class MonitorTerminalItemServiceImpl implements MonitorTerminalItemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorTerminalItemMapper monitorTerminalItemMapper;

	public MonitorTerminalItemServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorTerminalItem> list) {
		for (MonitorTerminalItem monitorTerminalItem : list) {
		   if (StringUtils.isEmpty(monitorTerminalItem.getId())) {
			monitorTerminalItem.setId(idGenerator.getNextId("MONITOR_TERMINAL_ITEM"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		monitorTerminalItemMapper.deleteMonitorTerminalItemById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    monitorTerminalItemMapper.deleteMonitorTerminalItemById(id);
		}
	    }
	}

	public int count(MonitorTerminalItemQuery query) {
		return monitorTerminalItemMapper.getMonitorTerminalItemCount(query);
	}

	public List<MonitorTerminalItem> list(MonitorTerminalItemQuery query) {
		List<MonitorTerminalItem> list = monitorTerminalItemMapper.getMonitorTerminalItems(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorTerminalItemCountByQueryCriteria(MonitorTerminalItemQuery query) {
		return monitorTerminalItemMapper.getMonitorTerminalItemCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorTerminalItem> getMonitorTerminalItemsByQueryCriteria(int start, int pageSize,
			MonitorTerminalItemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorTerminalItem> rows = sqlSessionTemplate.selectList(
				"getMonitorTerminalItems", query, rowBounds);
		return rows;
	}


	public MonitorTerminalItem getMonitorTerminalItem(String id) {
	        if(id == null){
		    return null;
		}
		MonitorTerminalItem monitorTerminalItem = monitorTerminalItemMapper.getMonitorTerminalItemById(id);
		return monitorTerminalItem;
	}

	@Transactional
	public void save(MonitorTerminalItem monitorTerminalItem) {
           if (StringUtils.isEmpty(monitorTerminalItem.getId())) {
	        monitorTerminalItem.setId(idGenerator.getNextId("MONITOR_TERMINAL_ITEM"));
		//monitorTerminalItem.setCreateDate(new Date());
		//monitorTerminalItem.setDeleteFlag(0);
		monitorTerminalItemMapper.insertMonitorTerminalItem(monitorTerminalItem);
	       } else {
		monitorTerminalItemMapper.updateMonitorTerminalItem(monitorTerminalItem);
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

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorTerminalItemMapper")
	public void setMonitorTerminalItemMapper(MonitorTerminalItemMapper monitorTerminalItemMapper) {
		this.monitorTerminalItemMapper = monitorTerminalItemMapper;
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
