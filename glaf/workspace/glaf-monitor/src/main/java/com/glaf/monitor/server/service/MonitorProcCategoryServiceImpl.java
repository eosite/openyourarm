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

@Service("com.glaf.monitor.server.service.monitorProcCategoryService")
@Transactional(readOnly = true) 
public class MonitorProcCategoryServiceImpl implements MonitorProcCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorProcCategoryMapper monitorProcCategoryMapper;

	public MonitorProcCategoryServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorProcCategory> list) {
		for (MonitorProcCategory monitorProcCategory : list) {
		    if ( monitorProcCategory.getId()  == null) {
			monitorProcCategory.setId(idGenerator.nextId("MONITOR_PROC_CATEGORY").intValue());
		    }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}


	@Transactional
	public void deleteById(Integer id) {
	     if(id != null ){
		monitorProcCategoryMapper.deleteMonitorProcCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Integer> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Integer id : ids){
		    monitorProcCategoryMapper.deleteMonitorProcCategoryById(id);
		}
	    }
	}

	public int count(MonitorProcCategoryQuery query) {
		return monitorProcCategoryMapper.getMonitorProcCategoryCount(query);
	}

	public List<MonitorProcCategory> list(MonitorProcCategoryQuery query) {
		List<MonitorProcCategory> list = monitorProcCategoryMapper.getMonitorProcCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorProcCategoryCountByQueryCriteria(MonitorProcCategoryQuery query) {
		return monitorProcCategoryMapper.getMonitorProcCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorProcCategory> getMonitorProcCategorysByQueryCriteria(int start, int pageSize,
			MonitorProcCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorProcCategory> rows = sqlSessionTemplate.selectList(
				"getMonitorProcCategorys", query, rowBounds);
		return rows;
	}


	public MonitorProcCategory getMonitorProcCategory(Integer id) {
	        if(id == null){
		    return null;
		}
		MonitorProcCategory monitorProcCategory = monitorProcCategoryMapper.getMonitorProcCategoryById(id);
		return monitorProcCategory;
	}

	@Transactional
	public void save(MonitorProcCategory monitorProcCategory) {
            if ( monitorProcCategory.getId()  == null) {
	        monitorProcCategory.setId(idGenerator.nextId("MONITOR_PROC_CATEGORY").intValue());
		//monitorProcCategory.setCreateDate(new Date());
		//monitorProcCategory.setDeleteFlag(0);
		monitorProcCategoryMapper.insertMonitorProcCategory(monitorProcCategory);
	       } else {
		monitorProcCategoryMapper.updateMonitorProcCategory(monitorProcCategory);
	      }
	}
	
	@Transactional
	public void add(MonitorProcCategory monitorProcCategory,Integer parentIndexId, String parentTreeId) {
		if ( monitorProcCategory.getId()  == null) {
        	int id = idGenerator.nextId("MONITOR_PROC_CATEGORY").intValue();
	        monitorProcCategory.setId(id);
	        if(parentIndexId == null){
	        	monitorProcCategory.setPid(-1);
	        	monitorProcCategory.setTreeid(String.valueOf(id) + "|");
	        }else{
	        	monitorProcCategory.setPid(parentIndexId);
	        	monitorProcCategory.setTreeid(parentTreeId + String.valueOf(id) + "|");
	        }
	        monitorProcCategoryMapper.insertMonitorProcCategory(monitorProcCategory);
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

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorProcCategoryMapper")
	public void setMonitorProcCategoryMapper(MonitorProcCategoryMapper monitorProcCategoryMapper) {
		this.monitorProcCategoryMapper = monitorProcCategoryMapper;
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
