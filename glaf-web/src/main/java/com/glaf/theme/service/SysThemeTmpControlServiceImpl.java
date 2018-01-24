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

@Service("com.glaf.theme.service.sysThemeTmpControlService")
@Transactional(readOnly = true) 
public class SysThemeTmpControlServiceImpl implements SysThemeTmpControlService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysThemeTmpControlMapper sysThemeTmpControlMapper;

	public SysThemeTmpControlServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SysThemeTmpControl> list) {
		for (SysThemeTmpControl sysThemeTmpControl : list) {
		   if (StringUtils.isEmpty(sysThemeTmpControl.getControlId())) {
			sysThemeTmpControl.setControlId(idGenerator.getNextId("SYS_THEME_TMP_CONTROL_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			sysThemeTmpControlMapper.bulkInsertSysThemeTmpControl_oracle(list);
		} else {
//			sysThemeTmpControlMapper.bulkInsertSysThemeTmpControl(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		sysThemeTmpControlMapper.deleteSysThemeTmpControlById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> controlIds) {
	    if(controlIds != null && !controlIds.isEmpty()){
		for(String id : controlIds){
		    sysThemeTmpControlMapper.deleteSysThemeTmpControlById(id);
		}
	    }
	}

	public int count(SysThemeTmpControlQuery query) {
		return sysThemeTmpControlMapper.getSysThemeTmpControlCount(query);
	}

	public List<SysThemeTmpControl> list(SysThemeTmpControlQuery query) {
		List<SysThemeTmpControl> list = sysThemeTmpControlMapper.getSysThemeTmpControls(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSysThemeTmpControlCountByQueryCriteria(SysThemeTmpControlQuery query) {
		return sysThemeTmpControlMapper.getSysThemeTmpControlCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysThemeTmpControl> getSysThemeTmpControlsByQueryCriteria(int start, int pageSize,
			SysThemeTmpControlQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysThemeTmpControl> rows = sqlSessionTemplate.selectList(
				"getSysThemeTmpControls", query, rowBounds);
		return rows;
	}


	public SysThemeTmpControl getSysThemeTmpControl(String id) {
	        if(id == null){
		    return null;
		}
		SysThemeTmpControl sysThemeTmpControl = sysThemeTmpControlMapper.getSysThemeTmpControlById(id);
		return sysThemeTmpControl;
	}

	@Transactional
	public void save(SysThemeTmpControl sysThemeTmpControl) {
           if (StringUtils.isEmpty(sysThemeTmpControl.getControlId())) {
	        sysThemeTmpControl.setControlId(idGenerator.getNextId("SYS_THEME_TMP_CONTROL_"));
		//sysThemeTmpControl.setCreateDate(new Date());
		//sysThemeTmpControl.setDeleteFlag(0);
		sysThemeTmpControlMapper.insertSysThemeTmpControl(sysThemeTmpControl);
	       } else {
		sysThemeTmpControlMapper.updateSysThemeTmpControl(sysThemeTmpControl);
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

	@javax.annotation.Resource(name = "com.glaf.theme.mapper.SysThemeTmpControlMapper")
	public void setSysThemeTmpControlMapper(SysThemeTmpControlMapper sysThemeTmpControlMapper) {
		this.sysThemeTmpControlMapper = sysThemeTmpControlMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

        @javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}


		@Override
		public SysThemeTmpControl getThumbnailById(String id) {
			if(id == null){
			    return null;
			}
			return sysThemeTmpControlMapper.getThumbnailById(id);
		}


		@Override
		public List<SysThemeTmpControl> listAndCssText(SysThemeTmpControlQuery query) {
			List<SysThemeTmpControl> list = sysThemeTmpControlMapper.getSysThemeTmpControlsAndCssText(query);
			return list;
		}


		@Override
		public List<SysThemeTmpControl> getSysThemeTmpControlsAndCssText(int start, int pageSize,
				SysThemeTmpControlQuery query) {
			RowBounds rowBounds = new RowBounds(start, pageSize);
			List<SysThemeTmpControl> rows = sqlSessionTemplate.selectList(
					"getSysThemeTmpControlsAndCssText", query, rowBounds);
			return rows;
		}

}
