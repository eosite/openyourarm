package com.glaf.form.core.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.util.JdbcUtils;
import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormPageCategoryService;

@Service("com.glaf.form.core.service.formPageCategoryService")
@Transactional(readOnly = true) 
public class FormPageCategoryServiceImpl implements FormPageCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormPageCategoryMapper formPageCategoryMapper;

	public FormPageCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
	     if(id != null ){
		formPageCategoryMapper.deleteFormPageCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Integer> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Integer id : ids){
		    formPageCategoryMapper.deleteFormPageCategoryById(id);
		}
	    }
	}

	public int count(FormPageCategoryQuery query) {
		return formPageCategoryMapper.getFormPageCategoryCount(query);
	}

	public List<FormPageCategory> list(FormPageCategoryQuery query) {
		List<FormPageCategory> list = formPageCategoryMapper.getFormPageCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormPageCategoryCountByQueryCriteria(FormPageCategoryQuery query) {
		return formPageCategoryMapper.getFormPageCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormPageCategory> getFormPageCategorysByQueryCriteria(int start, int pageSize,
			FormPageCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormPageCategory> rows = sqlSessionTemplate.selectList(
				"getFormPageCategorys", query, rowBounds);
		return rows;
	}


	public FormPageCategory getFormPageCategory(Integer id) {
	        if(id == null){
		    return null;
		}
		FormPageCategory formPageCategory = formPageCategoryMapper.getFormPageCategoryById(id);
		return formPageCategory;
	}

	@Transactional
	public void save(FormPageCategory formPageCategory) {
            if ( formPageCategory.getId()  == null) {
	        formPageCategory.setId(idGenerator.nextId("FORM_PAGE_CATEGORY").intValue());
		//formPageCategory.setCreateDate(new Date());
		//formPageCategory.setDeleteFlag(0);
		formPageCategoryMapper.insertFormPageCategory(formPageCategory);
	       } else {
		formPageCategoryMapper.updateFormPageCategory(formPageCategory);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormPageCategoryMapper")
	public void setFormPageCategoryMapper(FormPageCategoryMapper formPageCategoryMapper) {
		this.formPageCategoryMapper = formPageCategoryMapper;
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
