package com.glaf.form.core.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.form.core.domain.FormPageEventProcDef;
import com.glaf.form.core.mapper.FormPageEventProcDefMapper;
import com.glaf.form.core.query.FormPageEventProcDefQuery;
import com.glaf.form.core.service.FormPageEventProcDefService;


@Service("com.glaf.form.core.service.formPageEventProcDefService")
@Transactional(readOnly = true)
public class FormPageEventProcDefServiceImpl implements FormPageEventProcDefService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormPageEventProcDefMapper formPageEventProcDefMapper;

	public FormPageEventProcDefServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formPageEventProcDefMapper.deleteFormPageEventProcDefById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formPageEventProcDefMapper.deleteFormPageEventProcDefById(id);
		}
	    }
	}

	@Override
	public void updateFormPageEventProcDefById(String PROCDEF_KEY_,
			String PROCDEF_ID_, String PROCDEPLOY_ID_, String EVENTPRO_ID) {
		formPageEventProcDefMapper.updateFormPageEventProcDefById( PROCDEF_KEY_,
				 PROCDEF_ID_,  PROCDEPLOY_ID_,  EVENTPRO_ID);
		
	}
	public int count(FormPageEventProcDefQuery query) {
		return formPageEventProcDefMapper.getFormPageEventProcDefCount(query);
	}

	public List<FormPageEventProcDef> list(FormPageEventProcDefQuery query) {
		List<FormPageEventProcDef> list = formPageEventProcDefMapper.getFormPageEventProcDefs(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormPageEventProcDefCountByQueryCriteria(FormPageEventProcDefQuery query) {
		return formPageEventProcDefMapper.getFormPageEventProcDefCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormPageEventProcDef> getFormPageEventProcDefsByQueryCriteria(int start, int pageSize,
			FormPageEventProcDefQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormPageEventProcDef> rows = sqlSessionTemplate.selectList(
				"getFormPageEventProcDefs", query, rowBounds);
		return rows;
	}
    /**
     * 获取页面事件流程定义
     * @param pageId
     * @return
     */
	public List<FormPageEventProcDef> getFormPageEventProcDefsByPageId(String pageId){
		FormPageEventProcDefQuery query=new FormPageEventProcDefQuery();
		query.setPageId(pageId);
		List<FormPageEventProcDef> rows=formPageEventProcDefMapper.getFormPageEventProcDefs(query);
		return rows;
	}
	public Map<String,FormPageEventProcDef> getFormPageEventProcDefMapByPageId(String pageId){
		List<FormPageEventProcDef> formPageEventProcDefList= getFormPageEventProcDefsByPageId(pageId);
		String key=null;
		Map<String,FormPageEventProcDef> procDefMap=new HashMap<String,FormPageEventProcDef>();
		for (FormPageEventProcDef formPageEventProcDef:formPageEventProcDefList)
		{
			key=formPageEventProcDef.getPageId()+"_"+formPageEventProcDef.getCompId()+"_"+formPageEventProcDef.getEvent_();
			procDefMap.put(key, formPageEventProcDef);
		}
		return procDefMap;
	}
	public FormPageEventProcDef getFormPageEventProcDef(String id) {
	        if(id == null){
		    return null;
		}
		FormPageEventProcDef formPageEventProcDef = formPageEventProcDefMapper.getFormPageEventProcDefById(id);
		return formPageEventProcDef;
	}

	@Transactional
	public void save(FormPageEventProcDef formPageEventProcDef) {
           if (StringUtils.isEmpty(formPageEventProcDef.getId())) {
	        formPageEventProcDef.setId(idGenerator.getNextId("FORM_PAGE_EVENT_PROCDEF"));
		//formPageEventProcDef.setCreateDate(new Date());
		//formPageEventProcDef.setDeleteFlag(0);
		formPageEventProcDefMapper.insertFormPageEventProcDef(formPageEventProcDef);
	       } else {
		formPageEventProcDefMapper.updateFormPageEventProcDef(formPageEventProcDef);
	      }
	}
	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
 		String sql = "  ";//要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
			    psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormPageEventProcDefMapper")
	public void setFormPageEventProcDefMapper(FormPageEventProcDefMapper formPageEventProcDefMapper) {
		this.formPageEventProcDefMapper = formPageEventProcDefMapper;
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
		public void updateDeployStatus(String eventProcId) {
			
			formPageEventProcDefMapper.updateDeployStatus(eventProcId);
		}


}
