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

@Service("com.glaf.monitor.server.service.monitorTerminalUserService")
@Transactional(readOnly = true) 
public class MonitorTerminalUserServiceImpl implements MonitorTerminalUserService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorTerminalUserMapper monitorTerminalUserMapper;

	public MonitorTerminalUserServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorTerminalUser> list) {
		for (MonitorTerminalUser monitorTerminalUser : list) {
		   if (StringUtils.isEmpty(monitorTerminalUser.getId())) {
			monitorTerminalUser.setId(idGenerator.getNextId("MONITOR_TERMINAL_USER"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		monitorTerminalUserMapper.deleteMonitorTerminalUserById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    monitorTerminalUserMapper.deleteMonitorTerminalUserById(id);
		}
	    }
	}

	public int count(MonitorTerminalUserQuery query) {
		return monitorTerminalUserMapper.getMonitorTerminalUserCount(query);
	}

	public List<MonitorTerminalUser> list(MonitorTerminalUserQuery query) {
		List<MonitorTerminalUser> list = monitorTerminalUserMapper.getMonitorTerminalUsers(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorTerminalUserCountByQueryCriteria(MonitorTerminalUserQuery query) {
		return monitorTerminalUserMapper.getMonitorTerminalUserCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorTerminalUser> getMonitorTerminalUsersByQueryCriteria(int start, int pageSize,
			MonitorTerminalUserQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorTerminalUser> rows = sqlSessionTemplate.selectList(
				"getMonitorTerminalUsers", query, rowBounds);
		return rows;
	}


	public MonitorTerminalUser getMonitorTerminalUser(String id) {
	        if(id == null){
		    return null;
		}
		MonitorTerminalUser monitorTerminalUser = monitorTerminalUserMapper.getMonitorTerminalUserById(id);
		return monitorTerminalUser;
	}

	@Transactional
	public void save(MonitorTerminalUser monitorTerminalUser) {
           if (StringUtils.isEmpty(monitorTerminalUser.getId())) {
	        monitorTerminalUser.setId(idGenerator.getNextId("MONITOR_TERMINAL_USER"));
		//monitorTerminalUser.setCreateDate(new Date());
		//monitorTerminalUser.setDeleteFlag(0);
		monitorTerminalUserMapper.insertMonitorTerminalUser(monitorTerminalUser);
	       } else {
		monitorTerminalUserMapper.updateMonitorTerminalUser(monitorTerminalUser);
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

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorTerminalUserMapper")
	public void setMonitorTerminalUserMapper(MonitorTerminalUserMapper monitorTerminalUserMapper) {
		this.monitorTerminalUserMapper = monitorTerminalUserMapper;
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
