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

@Service("com.glaf.monitor.server.service.monitorProcBusCategoryService")
@Transactional(readOnly = true) 
public class MonitorProcBusCategoryServiceImpl implements MonitorProcBusCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorProcBusCategoryMapper monitorProcBusCategoryMapper;

	public MonitorProcBusCategoryServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorProcBusCategory> list) {
		for (MonitorProcBusCategory monitorProcBusCategory : list) {
		   if (StringUtils.isEmpty(monitorProcBusCategory.getId())) {
			monitorProcBusCategory.setId(idGenerator.getNextId("MONITOR_PROC_BUS_CATEGORY"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		monitorProcBusCategoryMapper.deleteMonitorProcBusCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    monitorProcBusCategoryMapper.deleteMonitorProcBusCategoryById(id);
		}
	    }
	}

	public int count(MonitorProcBusCategoryQuery query) {
		return monitorProcBusCategoryMapper.getMonitorProcBusCategoryCount(query);
	}

	public List<MonitorProcBusCategory> list(MonitorProcBusCategoryQuery query) {
		List<MonitorProcBusCategory> list = monitorProcBusCategoryMapper.getMonitorProcBusCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorProcBusCategoryCountByQueryCriteria(MonitorProcBusCategoryQuery query) {
		return monitorProcBusCategoryMapper.getMonitorProcBusCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorProcBusCategory> getMonitorProcBusCategorysByQueryCriteria(int start, int pageSize,
			MonitorProcBusCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorProcBusCategory> rows = sqlSessionTemplate.selectList(
				"getMonitorProcBusCategorys", query, rowBounds);
		return rows;
	}


	public MonitorProcBusCategory getMonitorProcBusCategory(String id) {
	        if(id == null){
		    return null;
		}
		MonitorProcBusCategory monitorProcBusCategory = monitorProcBusCategoryMapper.getMonitorProcBusCategoryById(id);
		return monitorProcBusCategory;
	}

	@Transactional
	public void save(MonitorProcBusCategory monitorProcBusCategory) {
           if (StringUtils.isEmpty(monitorProcBusCategory.getId())) {
	        monitorProcBusCategory.setId(idGenerator.getNextId("MONITOR_PROC_BUS_CATEGORY"));
		//monitorProcBusCategory.setCreateDate(new Date());
		//monitorProcBusCategory.setDeleteFlag(0);
		monitorProcBusCategoryMapper.insertMonitorProcBusCategory(monitorProcBusCategory);
	       } else {
		monitorProcBusCategoryMapper.updateMonitorProcBusCategory(monitorProcBusCategory);
	      }
	}

	@Transactional
	public void save(String id,String categoryIds) {
		//先删除后添加
		monitorProcBusCategoryMapper.deleteMonitorProcBusCategoryById(id);
		
		StringTokenizer token = new StringTokenizer(categoryIds, ",");
		List<Integer> categoryIdsAry = new ArrayList();
		while (token.hasMoreTokens()) {
			String x = token.nextToken();
			if (StringUtils.isNotEmpty(x)) {
				categoryIdsAry.add(Integer.parseInt(x));
			}
		}
		
		monitorProcBusCategoryMapper.blukInsertMonitorProcBusCategory(id,categoryIdsAry);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorProcBusCategoryMapper")
	public void setMonitorProcBusCategoryMapper(MonitorProcBusCategoryMapper monitorProcBusCategoryMapper) {
		this.monitorProcBusCategoryMapper = monitorProcBusCategoryMapper;
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
