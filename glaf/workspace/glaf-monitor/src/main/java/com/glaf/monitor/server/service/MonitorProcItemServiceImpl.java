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

@Service("com.glaf.monitor.server.service.monitorProcItemService")
@Transactional(readOnly = true) 
public class MonitorProcItemServiceImpl implements MonitorProcItemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorProcItemMapper monitorProcItemMapper;

	public MonitorProcItemServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorProcItem> list) {
		for (MonitorProcItem monitorProcItem : list) {
		   if (StringUtils.isEmpty(monitorProcItem.getId())) {
			monitorProcItem.setId(idGenerator.getNextId("MONITOR_PROC_ITEM"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		monitorProcItemMapper.deleteMonitorProcItemById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    monitorProcItemMapper.deleteMonitorProcItemById(id);
		}
	    }
	}

	public int count(MonitorProcItemQuery query) {
		return monitorProcItemMapper.getMonitorProcItemCount(query);
	}

	public List<MonitorProcItem> list(MonitorProcItemQuery query) {
		List<MonitorProcItem> list = monitorProcItemMapper.getMonitorProcItems(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorProcItemCountByQueryCriteria(MonitorProcItemQuery query) {
		return monitorProcItemMapper.getMonitorProcItemCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorProcItem> getMonitorProcItemsByQueryCriteria(int start, int pageSize,
			MonitorProcItemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorProcItem> rows = sqlSessionTemplate.selectList(
				"getMonitorProcItems", query, rowBounds);
		return rows;
	}


	public MonitorProcItem getMonitorProcItem(String id) {
	        if(id == null){
		    return null;
		}
		MonitorProcItem monitorProcItem = monitorProcItemMapper.getMonitorProcItemById(id);
		return monitorProcItem;
	}

	@Transactional
	public void save(MonitorProcItem monitorProcItem) {
           if (StringUtils.isEmpty(monitorProcItem.getId())) {
	        monitorProcItem.setId(idGenerator.getNextId("MONITOR_PROC_ITEM"));
		//monitorProcItem.setCreateDate(new Date());
		//monitorProcItem.setDeleteFlag(0);
		monitorProcItemMapper.insertMonitorProcItem(monitorProcItem);
	       } else {
		monitorProcItemMapper.updateMonitorProcItem(monitorProcItem);
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

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorProcItemMapper")
	public void setMonitorProcItemMapper(MonitorProcItemMapper monitorProcItemMapper) {
		this.monitorProcItemMapper = monitorProcItemMapper;
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
