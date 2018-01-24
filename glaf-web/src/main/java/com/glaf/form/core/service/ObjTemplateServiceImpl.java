package com.glaf.form.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

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
import com.glaf.form.core.domain.ObjTemplate;
import com.glaf.form.core.domain.ObjTemplateCategory;
import com.glaf.form.core.mapper.ObjTemplateCategoryMapper;
import com.glaf.form.core.mapper.ObjTemplateMapper;
import com.glaf.form.core.query.ObjTemplateQuery;

@Service("com.glaf.form.core.service.objTemplateService")
@Transactional(readOnly = true) 
public class ObjTemplateServiceImpl implements ObjTemplateService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ObjTemplateMapper objTemplateMapper;
	
	protected ObjTemplateCategoryMapper objTemplateCategoryMapper;

	public ObjTemplateServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		objTemplateMapper.deleteObjTemplateById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> templateIds) {
	    if(templateIds != null && !templateIds.isEmpty()){
		for(Long id : templateIds){
		    objTemplateMapper.deleteObjTemplateById(id);
		}
	    }
	}

	public int count(ObjTemplateQuery query) {
		return objTemplateMapper.getObjTemplateCount(query);
	}

	public List<ObjTemplate> list(ObjTemplateQuery query) {
		List<ObjTemplate> list = objTemplateMapper.getObjTemplates(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getObjTemplateCountByQueryCriteria(ObjTemplateQuery query) {
		return objTemplateMapper.getObjTemplateCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ObjTemplate> getObjTemplatesByQueryCriteria(int start, int pageSize,
			ObjTemplateQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ObjTemplate> rows = sqlSessionTemplate.selectList(
				"getObjTemplates", query, rowBounds);
		return rows;
	}

	public List<ObjTemplate> getCategoryTemplates(Long category){
		return objTemplateMapper.getCategoryTemplates(category);
	}
	
	public ObjTemplate getObjTemplate(Long id) {
	        if(id == null){
		    return null;
		}
		ObjTemplate objTemplate = objTemplateMapper.getObjTemplateById(id);
		return objTemplate;
	}

	@Transactional
	public void save(ObjTemplate objTemplate) {
            if ( objTemplate.getTemplateId()  == null||objTemplate.getTemplateId()==0L) {
	        objTemplate.setTemplateId(idGenerator.nextId("OBJ_TEMPLATE"));
		//objTemplate.setCreateDate(new Date());
		//objTemplate.setDeleteFlag(0);
		objTemplateMapper.insertObjTemplate(objTemplate);
	       } else {
		objTemplateMapper.updateObjTemplate(objTemplate);
	      }
	}
	@Transactional
    public void saveTemplateToCategory(ObjTemplate objTemplate,Long categoryId){
    	save(objTemplate);
    	ObjTemplateCategory  objTemplateCategory=new ObjTemplateCategory();
    	objTemplateCategory.setCategoryId(categoryId);
    	objTemplateCategory.setTemplateId(objTemplate.getTemplateId());
    	objTemplateCategory.setCreateTime(new Date());
    	objTemplateCategory.setCreator(objTemplate.getCreator());
    	objTemplateCategory.setModifier(objTemplate.getCreator());
    	objTemplateCategory.setUpdateTime(new Date());
    	objTemplateCategoryMapper.insertObjTemplateCategory(objTemplateCategory);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.ObjTemplateMapper")
	public void setObjTemplateMapper(ObjTemplateMapper objTemplateMapper) {
		this.objTemplateMapper = objTemplateMapper;
	}
	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.ObjTemplateCategoryMapper")
	public void setObjTemplateCategoryMapper(ObjTemplateCategoryMapper objTemplateCategoryMapper) {
		this.objTemplateCategoryMapper = objTemplateCategoryMapper;
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
