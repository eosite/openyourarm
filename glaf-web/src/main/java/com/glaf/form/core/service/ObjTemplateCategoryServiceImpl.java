package com.glaf.form.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.glaf.form.core.domain.ObjTemplateCategory;
import com.glaf.form.core.mapper.ObjTemplateCategoryMapper;
import com.glaf.form.core.query.ObjTemplateCategoryQuery;

@Service("com.glaf.form.core.service.objTemplateCategoryService")
@Transactional(readOnly = true) 
public class ObjTemplateCategoryServiceImpl implements ObjTemplateCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ObjTemplateCategoryMapper objTemplateCategoryMapper;

	public ObjTemplateCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		objTemplateCategoryMapper.deleteObjTemplateCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> categoryIds) {
	    if(categoryIds != null && !categoryIds.isEmpty()){
		for(Long id : categoryIds){
		    objTemplateCategoryMapper.deleteObjTemplateCategoryById(id);
		}
	    }
	}

	public int count(ObjTemplateCategoryQuery query) {
		return objTemplateCategoryMapper.getObjTemplateCategoryCount(query);
	}

	public List<ObjTemplateCategory> list(ObjTemplateCategoryQuery query) {
		List<ObjTemplateCategory> list = objTemplateCategoryMapper.getObjTemplateCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getObjTemplateCategoryCountByQueryCriteria(ObjTemplateCategoryQuery query) {
		return objTemplateCategoryMapper.getObjTemplateCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ObjTemplateCategory> getObjTemplateCategorysByQueryCriteria(int start, int pageSize,
			ObjTemplateCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ObjTemplateCategory> rows = sqlSessionTemplate.selectList(
				"getObjTemplateCategorys", query, rowBounds);
		return rows;
	}


	public ObjTemplateCategory getObjTemplateCategory(Long id) {
	        if(id == null){
		    return null;
		}
		ObjTemplateCategory objTemplateCategory = objTemplateCategoryMapper.getObjTemplateCategoryById(id);
		return objTemplateCategory;
	}
	public ObjTemplateCategory getObjTemplateCategoryByTemplateId(Long id) {
        if(id == null){
	    return null;
	}
	ObjTemplateCategory objTemplateCategory = objTemplateCategoryMapper.getObjTemplateCategoryByTemplateId(id);
	return objTemplateCategory;
   }
	@Transactional
	public void save(ObjTemplateCategory objTemplateCategory) {
            if ( objTemplateCategory.getCategoryId()  == null) {
	        objTemplateCategory.setCategoryId(idGenerator.nextId("OBJ_TEMPLATE_CATEGORY"));
		//objTemplateCategory.setCreateDate(new Date());
		//objTemplateCategory.setDeleteFlag(0);
		objTemplateCategoryMapper.insertObjTemplateCategory(objTemplateCategory);
	       } else {
		objTemplateCategoryMapper.updateObjTemplateCategory(objTemplateCategory);
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
