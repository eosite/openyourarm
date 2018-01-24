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

@Service("com.glaf.monitor.server.service.monitorProcUserService")
@Transactional(readOnly = true) 
public class MonitorProcUserServiceImpl implements MonitorProcUserService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorProcUserMapper monitorProcUserMapper;

	public MonitorProcUserServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorProcUser> list) {
		for (MonitorProcUser monitorProcUser : list) {
		   if (StringUtils.isEmpty(monitorProcUser.getId())) {
			monitorProcUser.setId(idGenerator.getNextId("MONITOR_PROC_USER"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		monitorProcUserMapper.deleteMonitorProcUserById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    monitorProcUserMapper.deleteMonitorProcUserById(id);
		}
	    }
	}

	public int count(MonitorProcUserQuery query) {
		return monitorProcUserMapper.getMonitorProcUserCount(query);
	}

	public List<MonitorProcUser> list(MonitorProcUserQuery query) {
		List<MonitorProcUser> list = monitorProcUserMapper.getMonitorProcUsers(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorProcUserCountByQueryCriteria(MonitorProcUserQuery query) {
		return monitorProcUserMapper.getMonitorProcUserCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorProcUser> getMonitorProcUsersByQueryCriteria(int start, int pageSize,
			MonitorProcUserQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorProcUser> rows = sqlSessionTemplate.selectList(
				"getMonitorProcUsers", query, rowBounds);
		return rows;
	}


	public MonitorProcUser getMonitorProcUser(String id) {
	        if(id == null){
		    return null;
		}
		MonitorProcUser monitorProcUser = monitorProcUserMapper.getMonitorProcUserById(id);
		return monitorProcUser;
	}

	@Transactional
	public void save(MonitorProcUser monitorProcUser) {
           if (StringUtils.isEmpty(monitorProcUser.getId())) {
	        monitorProcUser.setId(idGenerator.getNextId("MONITOR_PROC_USER"));
		//monitorProcUser.setCreateDate(new Date());
		//monitorProcUser.setDeleteFlag(0);
		monitorProcUserMapper.insertMonitorProcUser(monitorProcUser);
	       } else {
		monitorProcUserMapper.updateMonitorProcUser(monitorProcUser);
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

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorProcUserMapper")
	public void setMonitorProcUserMapper(MonitorProcUserMapper monitorProcUserMapper) {
		this.monitorProcUserMapper = monitorProcUserMapper;
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
