package com.glaf.model.service;


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

import com.glaf.model.mapper.*;
import com.glaf.model.domain.*;
import com.glaf.model.query.*;

@Service("com.glaf.model.service.systemFuncDataObjService")
@Transactional(readOnly = true) 
public class SystemFuncDataObjServiceImpl implements SystemFuncDataObjService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SystemFuncDataObjMapper systemFuncDataObjMapper;

	public SystemFuncDataObjServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		systemFuncDataObjMapper.deleteSystemFuncDataObjById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> sysDataObjIds) {
	    if(sysDataObjIds != null && !sysDataObjIds.isEmpty()){
		for(String id : sysDataObjIds){
		    systemFuncDataObjMapper.deleteSystemFuncDataObjById(id);
		}
	    }
	}

	public int count(SystemFuncDataObjQuery query) {
		return systemFuncDataObjMapper.getSystemFuncDataObjCount(query);
	}

	public List<SystemFuncDataObj> list(SystemFuncDataObjQuery query) {
		List<SystemFuncDataObj> list = systemFuncDataObjMapper.getSystemFuncDataObjs(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSystemFuncDataObjCountByQueryCriteria(SystemFuncDataObjQuery query) {
		return systemFuncDataObjMapper.getSystemFuncDataObjCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SystemFuncDataObj> getSystemFuncDataObjsByQueryCriteria(int start, int pageSize,
			SystemFuncDataObjQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SystemFuncDataObj> rows = sqlSessionTemplate.selectList(
				"getSystemFuncDataObjs", query, rowBounds);
		return rows;
	}


	public SystemFuncDataObj getSystemFuncDataObj(String id) {
	        if(id == null){
		    return null;
		}
		SystemFuncDataObj systemFuncDataObj = systemFuncDataObjMapper.getSystemFuncDataObjById(id);
		return systemFuncDataObj;
	}

	@Transactional
	public void save(SystemFuncDataObj systemFuncDataObj) {
            if ( systemFuncDataObj.getSysDataObjId()  == null) {
	        systemFuncDataObj.setSysDataObjId(UUID32.getUUID());
	    systemFuncDataObj.setCreateTime(new Date());
		systemFuncDataObj.setDeleteFlag(0);
		systemFuncDataObjMapper.insertSystemFuncDataObj(systemFuncDataObj);
	       } else {
		systemFuncDataObjMapper.updateSystemFuncDataObj(systemFuncDataObj);
	      }
	}

	@Transactional
	public void saveFuncDataObj(String user,String funcId,String dataObjFuncId,int dataObjType){
		systemFuncDataObjMapper.deleteFuncDataObj(funcId,dataObjFuncId,dataObjType);
		SystemFuncDataObj systemFuncDataObj=new SystemFuncDataObj();
		systemFuncDataObj.setType(dataObjType);
		systemFuncDataObj.setFuncId(funcId);
		systemFuncDataObj.setDataObjId(dataObjFuncId);
		systemFuncDataObj.setCreateBy(user);
		save(systemFuncDataObj);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.model.mapper.SystemFuncDataObjMapper")
	public void setSystemFuncDataObjMapper(SystemFuncDataObjMapper systemFuncDataObjMapper) {
		this.systemFuncDataObjMapper = systemFuncDataObjMapper;
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
