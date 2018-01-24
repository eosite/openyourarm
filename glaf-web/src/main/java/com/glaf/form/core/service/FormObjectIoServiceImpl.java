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

@Service("com.glaf.form.core.service.formObjectIoService")
@Transactional(readOnly = true) 
public class FormObjectIoServiceImpl implements FormObjectIoService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormObjectIoMapper formObjectIoMapper;

	public FormObjectIoServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormObjectIo> list) {
		for (FormObjectIo formObjectIo : list) {
		   if (StringUtils.isEmpty(formObjectIo.getId())) {
			formObjectIo.setId(idGenerator.getNextId("FORM_OBJECT_IO"));
		   }
		}
		
		int batch_size = 100;
                List<FormObjectIo> rows = new ArrayList<FormObjectIo>(batch_size);

		for (FormObjectIo bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//	formObjectIoMapper.bulkInsertFormObjectIo_oracle(list);
				} else {
				//	formObjectIoMapper.bulkInsertFormObjectIo(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//	formObjectIoMapper.bulkInsertFormObjectIo_oracle(list);
			} else {
			//	formObjectIoMapper.bulkInsertFormObjectIo(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formObjectIoMapper.deleteFormObjectIoById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formObjectIoMapper.deleteFormObjectIoById(id);
		}
	    }
	}

	public int count(FormObjectIoQuery query) {
		return formObjectIoMapper.getFormObjectIoCount(query);
	}

	public List<FormObjectIo> list(FormObjectIoQuery query) {
		List<FormObjectIo> list = formObjectIoMapper.getFormObjectIos(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormObjectIoCountByQueryCriteria(FormObjectIoQuery query) {
		return formObjectIoMapper.getFormObjectIoCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormObjectIo> getFormObjectIosByQueryCriteria(int start, int pageSize,
			FormObjectIoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormObjectIo> rows = sqlSessionTemplate.selectList(
				"getFormObjectIos", query, rowBounds);
		return rows;
	}


	public FormObjectIo getFormObjectIo(String id) {
	        if(id == null){
		    return null;
		}
		FormObjectIo formObjectIo = formObjectIoMapper.getFormObjectIoById(id);
		return formObjectIo;
	}

	@Transactional
	public void save(FormObjectIo formObjectIo) {
           if (StringUtils.isEmpty(formObjectIo.getId())) {
	        formObjectIo.setId(idGenerator.getNextId("FORM_OBJECT_IO"));
		//formObjectIo.setCreateDate(new Date());
		//formObjectIo.setDeleteFlag(0);
		formObjectIoMapper.insertFormObjectIo(formObjectIo);
	       } else {
		formObjectIoMapper.updateFormObjectIo(formObjectIo);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormObjectIoMapper")
	public void setFormObjectIoMapper(FormObjectIoMapper formObjectIoMapper) {
		this.formObjectIoMapper = formObjectIoMapper;
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
