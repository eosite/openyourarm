package com.glaf.form.core.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.form.core.domain.FormEventComplex;
import com.glaf.form.core.mapper.FormEventComplexMapper;
import com.glaf.form.core.query.FormEventComplexQuery;
import com.glaf.form.core.service.FormEventComplexService;



@Service("com.glaf.form.core.service.formEventComplexService")
@Transactional(readOnly = true) 
public class FormEventComplexServiceImpl implements FormEventComplexService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormEventComplexMapper formEventComplexMapper;

	public FormEventComplexServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormEventComplex> list) {
		for (FormEventComplex formEventComplex : list) {
		   if (StringUtils.isEmpty(formEventComplex.getId())) {
			formEventComplex.setId(idGenerator.getNextId("FORM_EVENT_COMPLEX"));
		   }
		}
		
		int batch_size = 100;
                List<FormEventComplex> rows = new ArrayList<FormEventComplex>(batch_size);

		for (FormEventComplex bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					//formEventComplexMapper.bulkInsertFormEventComplex_oracle(list);
				} else {
					//formEventComplexMapper.bulkInsertFormEventComplex(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//formEventComplexMapper.bulkInsertFormEventComplex_oracle(list);
			} else {
				//formEventComplexMapper.bulkInsertFormEventComplex(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formEventComplexMapper.deleteFormEventComplexById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formEventComplexMapper.deleteFormEventComplexById(id);
		}
	    }
	}

	public int count(FormEventComplexQuery query) {
		return formEventComplexMapper.getFormEventComplexCount(query);
	}

	public List<FormEventComplex> list(FormEventComplexQuery query) {
		List<FormEventComplex> list = formEventComplexMapper.getFormEventComplexs(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormEventComplexCountByQueryCriteria(FormEventComplexQuery query) {
		return formEventComplexMapper.getFormEventComplexCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormEventComplex> getFormEventComplexsByQueryCriteria(int start, int pageSize,
			FormEventComplexQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormEventComplex> rows = sqlSessionTemplate.selectList(
				"getFormEventComplexs", query, rowBounds);
		return rows;
	}


	public FormEventComplex getFormEventComplex(String id) {
	        if(id == null){
		    return null;
		}
		FormEventComplex formEventComplex = formEventComplexMapper.getFormEventComplexById(id);
		return formEventComplex;
	}

	@Transactional
	public void save(FormEventComplex formEventComplex) {
           if (StringUtils.isEmpty(formEventComplex.getId())) {
	        formEventComplex.setId(idGenerator.getNextId("FORM_EVENT_COMPLEX"));
		//formEventComplex.setCreateDate(new Date());
		//formEventComplex.setDeleteFlag(0);
		formEventComplexMapper.insertFormEventComplex(formEventComplex);
	       } else {
		formEventComplexMapper.updateFormEventComplex(formEventComplex);
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

	@javax.annotation.Resource(name = "com.glaf.form.mapper.FormEventComplexMapper")
	public void setFormEventComplexMapper(FormEventComplexMapper formEventComplexMapper) {
		this.formEventComplexMapper = formEventComplexMapper;
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
	public List<FormEventComplex> queryComplexByPageId(FormEventComplexQuery query) {
		return formEventComplexMapper.queryComplexByPageId(query);
	}

}
