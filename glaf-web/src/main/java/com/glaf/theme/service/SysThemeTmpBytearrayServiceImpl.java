package com.glaf.theme.service;


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

import com.glaf.theme.mapper.*;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

@Service("com.glaf.theme.service.sysThemeTmpBytearrayService")
@Transactional(readOnly = true) 
public class SysThemeTmpBytearrayServiceImpl implements SysThemeTmpBytearrayService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysThemeTmpBytearrayMapper sysThemeTmpBytearrayMapper;

	public SysThemeTmpBytearrayServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SysThemeTmpBytearray> list) {
		for (SysThemeTmpBytearray sysThemeTmpBytearray : list) {
		   if (StringUtils.isEmpty(sysThemeTmpBytearray.getId())) {
			sysThemeTmpBytearray.setId(idGenerator.getNextId("SYS_THEME_TMP_BYTEARRAY_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			sysThemeTmpBytearrayMapper.bulkInsertSysThemeTmpBytearray_oracle(list);
		} else {
//			sysThemeTmpBytearrayMapper.bulkInsertSysThemeTmpBytearray(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		sysThemeTmpBytearrayMapper.deleteSysThemeTmpBytearrayById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    sysThemeTmpBytearrayMapper.deleteSysThemeTmpBytearrayById(id);
		}
	    }
	}
	
	@Transactional
	public void deleteByBuss(String bussType, String bussKey, String type) {
		sysThemeTmpBytearrayMapper.deleteByBuss(bussType,bussKey,type);
	}

	public int count(SysThemeTmpBytearrayQuery query) {
		return sysThemeTmpBytearrayMapper.getSysThemeTmpBytearrayCount(query);
	}

	public List<SysThemeTmpBytearray> list(SysThemeTmpBytearrayQuery query) {
		List<SysThemeTmpBytearray> list = sysThemeTmpBytearrayMapper.getSysThemeTmpBytearrays(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSysThemeTmpBytearrayCountByQueryCriteria(SysThemeTmpBytearrayQuery query) {
		return sysThemeTmpBytearrayMapper.getSysThemeTmpBytearrayCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysThemeTmpBytearray> getSysThemeTmpBytearraysByQueryCriteria(int start, int pageSize,
			SysThemeTmpBytearrayQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysThemeTmpBytearray> rows = sqlSessionTemplate.selectList(
				"getSysThemeTmpBytearrays", query, rowBounds);
		return rows;
	}


	public SysThemeTmpBytearray getSysThemeTmpBytearray(String id) {
	        if(id == null){
		    return null;
		}
		SysThemeTmpBytearray sysThemeTmpBytearray = sysThemeTmpBytearrayMapper.getSysThemeTmpBytearrayById(id);
		return sysThemeTmpBytearray;
	}

	@Transactional
	public void save(SysThemeTmpBytearray sysThemeTmpBytearray) {
           if (StringUtils.isEmpty(sysThemeTmpBytearray.getId())) {
	        sysThemeTmpBytearray.setId(idGenerator.getNextId("SYS_THEME_TMP_BYTEARRAY_"));
		//sysThemeTmpBytearray.setCreateDate(new Date());
		//sysThemeTmpBytearray.setDeleteFlag(0);
		sysThemeTmpBytearrayMapper.insertSysThemeTmpBytearray(sysThemeTmpBytearray);
	       } else {
		sysThemeTmpBytearrayMapper.updateSysThemeTmpBytearray(sysThemeTmpBytearray);
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

	@javax.annotation.Resource(name = "com.glaf.theme.mapper.SysThemeTmpBytearrayMapper")
	public void setSysThemeTmpBytearrayMapper(SysThemeTmpBytearrayMapper sysThemeTmpBytearrayMapper) {
		this.sysThemeTmpBytearrayMapper = sysThemeTmpBytearrayMapper;
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
