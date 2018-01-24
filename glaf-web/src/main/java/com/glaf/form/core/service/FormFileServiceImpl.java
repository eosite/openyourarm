package com.glaf.form.core.service;


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

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Service("com.glaf.form.core.service.formFileService")
@Transactional(readOnly = true) 
public class FormFileServiceImpl implements FormFileService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormFileMapper formFileMapper;

	public FormFileServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormFile> list) {
		for (FormFile formFile : list) {
		   if (StringUtils.isEmpty(formFile.getId())) {
			formFile.setId(idGenerator.getNextId("FORM_FILE_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			formFileMapper.bulkInsertFormFile_oracle(list);
		} else {
//			formFileMapper.bulkInsertFormFile(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formFileMapper.deleteFormFileById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formFileMapper.deleteFormFileById(id);
		}
	    }
	}

	public int count(FormFileQuery query) {
		return formFileMapper.getFormFileCount(query);
	}

	public List<FormFile> list(FormFileQuery query) {
		List<FormFile> list = formFileMapper.getFormFiles(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormFileCountByQueryCriteria(FormFileQuery query) {
		return formFileMapper.getFormFileCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormFile> getFormFilesByQueryCriteria(int start, int pageSize,
			FormFileQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormFile> rows = sqlSessionTemplate.selectList(
				"getFormFiles", query, rowBounds);
		return rows;
	}


	public FormFile getFormFile(String id) {
	        if(id == null){
		    return null;
		}
		FormFile formFile = formFileMapper.getFormFileById(id);
		return formFile;
	}

	@Transactional
	public FormFile save(FormFile formFile) {
           if (StringUtils.isEmpty(formFile.getId())) {
        	   formFile.setId(UUID32.getUUID());
		//formFile.setCreateDate(new Date());
		//formFile.setDeleteFlag(0);
		formFileMapper.insertFormFile(formFile);
	       } else {
		formFileMapper.updateFormFile(formFile);
	      }
           return formFile;
	}


	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormFileMapper")
	public void setFormFileMapper(FormFileMapper formFileMapper) {
		this.formFileMapper = formFileMapper;
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
