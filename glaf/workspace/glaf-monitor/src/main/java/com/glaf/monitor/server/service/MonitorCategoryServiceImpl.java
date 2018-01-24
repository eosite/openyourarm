package com.glaf.monitor.server.service;


import java.util.*;
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

@Service("com.glaf.monitor.server.service.monitorCategoryService")
@Transactional(readOnly = true) 
public class MonitorCategoryServiceImpl implements MonitorCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MonitorCategoryMapper monitorCategoryMapper;

	public MonitorCategoryServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MonitorCategory> list) {
		for (MonitorCategory monitorCategory : list) {
		    if ( monitorCategory.getId()  == null) {
			monitorCategory.setId(idGenerator.nextId("MONITOR_CATEGORY").intValue());
		    }
		}
	}


	@Transactional
	public void deleteById(Integer id) {
	     if(id != null ){
		monitorCategoryMapper.deleteMonitorCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Integer> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Integer id : ids){
		    monitorCategoryMapper.deleteMonitorCategoryById(id);
		}
	    }
	}

	public int count(MonitorCategoryQuery query) {
		return monitorCategoryMapper.getMonitorCategoryCount(query);
	}

	public List<MonitorCategory> list(MonitorCategoryQuery query) {
		List<MonitorCategory> list = monitorCategoryMapper.getMonitorCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMonitorCategoryCountByQueryCriteria(MonitorCategoryQuery query) {
		return monitorCategoryMapper.getMonitorCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MonitorCategory> getMonitorCategorysByQueryCriteria(int start, int pageSize,
			MonitorCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MonitorCategory> rows = sqlSessionTemplate.selectList(
				"getMonitorCategorys", query, rowBounds);
		return rows;
	}


	public MonitorCategory getMonitorCategory(Integer id) {
	        if(id == null){
		    return null;
		}
		MonitorCategory monitorCategory = monitorCategoryMapper.getMonitorCategoryById(id);
		return monitorCategory;
	}

	@Transactional
	public void save(MonitorCategory monitorCategory) {
            if ( monitorCategory.getId()  == null) {
	        monitorCategory.setId(idGenerator.nextId("MONITOR_CATEGORY").intValue());
		//monitorCategory.setCreateDate(new Date());
		//monitorCategory.setDeleteFlag(0);
		monitorCategoryMapper.insertMonitorCategory(monitorCategory);
	       } else {
		monitorCategoryMapper.updateMonitorCategory(monitorCategory);
	      }
	}
	
	@Transactional
	public void add(MonitorCategory monitorCategory,Integer parentIndexId, String parentTreeId) {
        if ( monitorCategory.getId()  == null) {
        	int id = idGenerator.nextId("MONITOR_CATEGORY").intValue();
	        monitorCategory.setId(id);
	        if(parentIndexId == null){
	        	monitorCategory.setPid(-1);
	        	monitorCategory.setTreeid(String.valueOf(id) + "|");
	        }else{
	        	monitorCategory.setPid(parentIndexId);
	        	monitorCategory.setTreeid(parentTreeId + String.valueOf(id) + "|");
	        }
	        monitorCategoryMapper.insertMonitorCategory(monitorCategory);
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

	@javax.annotation.Resource(name = "com.glaf.monitor.server.mapper.MonitorCategoryMapper")
	public void setMonitorCategoryMapper(MonitorCategoryMapper monitorCategoryMapper) {
		this.monitorCategoryMapper = monitorCategoryMapper;
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
