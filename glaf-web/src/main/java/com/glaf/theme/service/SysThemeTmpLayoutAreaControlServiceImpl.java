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

@Service("com.glaf.theme.service.sysThemeTmpLayoutAreaControlService")
@Transactional(readOnly = true) 
public class SysThemeTmpLayoutAreaControlServiceImpl implements SysThemeTmpLayoutAreaControlService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysThemeTmpLayoutAreaControlMapper sysThemeTmpLayoutAreaControlMapper;

	public SysThemeTmpLayoutAreaControlServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SysThemeTmpLayoutAreaControl> list) {
		for (SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl : list) {
		   if (StringUtils.isEmpty(sysThemeTmpLayoutAreaControl.getControlId())) {
			sysThemeTmpLayoutAreaControl.setControlId(idGenerator.getNextId("SYS_THEME_TMP_LAYOUT_AREA_CONTROL_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			sysThemeTmpLayoutAreaControlMapper.bulkInsertSysThemeTmpLayoutAreaControl_oracle(list);
		} else {
//			sysThemeTmpLayoutAreaControlMapper.bulkInsertSysThemeTmpLayoutAreaControl(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		sysThemeTmpLayoutAreaControlMapper.deleteSysThemeTmpLayoutAreaControlById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> controlIds) {
	    if(controlIds != null && !controlIds.isEmpty()){
		for(String id : controlIds){
		    sysThemeTmpLayoutAreaControlMapper.deleteSysThemeTmpLayoutAreaControlById(id);
		}
	    }
	}

	public int count(SysThemeTmpLayoutAreaControlQuery query) {
		return sysThemeTmpLayoutAreaControlMapper.getSysThemeTmpLayoutAreaControlCount(query);
	}

	public List<SysThemeTmpLayoutAreaControl> list(SysThemeTmpLayoutAreaControlQuery query) {
		List<SysThemeTmpLayoutAreaControl> list = sysThemeTmpLayoutAreaControlMapper.getSysThemeTmpLayoutAreaControls(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSysThemeTmpLayoutAreaControlCountByQueryCriteria(SysThemeTmpLayoutAreaControlQuery query) {
		return sysThemeTmpLayoutAreaControlMapper.getSysThemeTmpLayoutAreaControlCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysThemeTmpLayoutAreaControl> getSysThemeTmpLayoutAreaControlsByQueryCriteria(int start, int pageSize,
			SysThemeTmpLayoutAreaControlQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysThemeTmpLayoutAreaControl> rows = sqlSessionTemplate.selectList(
				"getSysThemeTmpLayoutAreaControls", query, rowBounds);
		return rows;
	}


	public SysThemeTmpLayoutAreaControl getSysThemeTmpLayoutAreaControl(String id) {
	        if(id == null){
		    return null;
		}
		SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl = sysThemeTmpLayoutAreaControlMapper.getSysThemeTmpLayoutAreaControlById(id);
		return sysThemeTmpLayoutAreaControl;
	}

	@Transactional
	public void save(SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl) {
           if (StringUtils.isEmpty(sysThemeTmpLayoutAreaControl.getControlId())) {
	        sysThemeTmpLayoutAreaControl.setControlId(idGenerator.getNextId("SYS_THEME_TMP_LAYOUT_AREA_CONTROL_"));
		//sysThemeTmpLayoutAreaControl.setCreateDate(new Date());
		//sysThemeTmpLayoutAreaControl.setDeleteFlag(0);
		sysThemeTmpLayoutAreaControlMapper.insertSysThemeTmpLayoutAreaControl(sysThemeTmpLayoutAreaControl);
	       } else {
		sysThemeTmpLayoutAreaControlMapper.updateSysThemeTmpLayoutAreaControl(sysThemeTmpLayoutAreaControl);
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

	@javax.annotation.Resource(name = "com.glaf.theme.mapper.SysThemeTmpLayoutAreaControlMapper")
	public void setSysThemeTmpLayoutAreaControlMapper(SysThemeTmpLayoutAreaControlMapper sysThemeTmpLayoutAreaControlMapper) {
		this.sysThemeTmpLayoutAreaControlMapper = sysThemeTmpLayoutAreaControlMapper;
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
		public SysThemeTmpLayoutAreaControl getThumbnailById(String id) {
			if(id == null){
			    return null;
			}
			return sysThemeTmpLayoutAreaControlMapper.getThumbnailById(id);
		}

}
