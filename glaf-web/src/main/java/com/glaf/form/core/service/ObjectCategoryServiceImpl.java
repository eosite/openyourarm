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
import com.glaf.form.core.domain.ObjectCategory;
import com.glaf.form.core.mapper.ObjectCategoryMapper;
import com.glaf.form.core.query.ObjectCategoryQuery;

@Service("com.glaf.form.core.service.objectCategoryService")
@Transactional(readOnly = true) 
public class ObjectCategoryServiceImpl implements ObjectCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ObjectCategoryMapper objectCategoryMapper;

	public ObjectCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		objectCategoryMapper.deleteObjectCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> categoryIds) {
	    if(categoryIds != null && !categoryIds.isEmpty()){
		for(Long id : categoryIds){
		    objectCategoryMapper.deleteObjectCategoryById(id);
		}
	    }
	}

	public int count(ObjectCategoryQuery query) {
		return objectCategoryMapper.getObjectCategoryCount(query);
	}

	public List<ObjectCategory> list(ObjectCategoryQuery query) {
		List<ObjectCategory> list = objectCategoryMapper.getObjectCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getObjectCategoryCountByQueryCriteria(ObjectCategoryQuery query) {
		return objectCategoryMapper.getObjectCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ObjectCategory> getObjectCategorysByQueryCriteria(int start, int pageSize,
			ObjectCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ObjectCategory> rows = sqlSessionTemplate.selectList(
				"getObjectCategorys", query, rowBounds);
		return rows;
	}
    /**
     * 获取模板分类
     */
	public List<ObjectCategory> getPageCompTemplateObjectCategorys(String actorId,String treeId){
		List<ObjectCategory> objectCategorys=objectCategoryMapper.getPageCompTemplateObjectCategorys(actorId,treeId);
		return objectCategorys;
	}
	public ObjectCategory getObjectCategory(Long id) {
	        if(id == null){
		    return null;
		}
		ObjectCategory objectCategory = objectCategoryMapper.getObjectCategoryById(id);
		return objectCategory;
	}

	@Transactional
	public void save(ObjectCategory objectCategory) {
            if ( objectCategory.getCategoryId()  == null) {
	        objectCategory.setCategoryId(idGenerator.nextId("OBJ_CATEGORY"));
	        objectCategory.setTreeID(objectCategory.getTreeID()+objectCategory.getCategoryId()+"|");
			int orderNo=getMaxOrder(objectCategory.getParentId())+1;
			objectCategory.setOrderNo(orderNo);
		//objectCategory.setCreateDate(new Date());
		//objectCategory.setDeleteFlag(0);
		objectCategoryMapper.insertObjectCategory(objectCategory);
	       } else {
		objectCategoryMapper.updateObjectCategory(objectCategory);
	      }
	}
	@Transactional
	public void rename(Long categoryId,String name) throws Exception{
		try{
		 objectCategoryMapper.rename(categoryId,name);
		}catch(Exception e){
			throw e;
		}
	}
	public synchronized int getMaxOrder(Long categoryId){
		
		return 1;
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.ObjectCategoryMapper")
	public void setObjectCategoryMapper(ObjectCategoryMapper objectCategoryMapper) {
		this.objectCategoryMapper = objectCategoryMapper;
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
