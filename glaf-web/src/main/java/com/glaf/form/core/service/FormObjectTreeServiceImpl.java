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

@Service("com.glaf.form.core.service.formObjectTreeService")
@Transactional(readOnly = true) 
public class FormObjectTreeServiceImpl implements FormObjectTreeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormObjectTreeMapper formObjectTreeMapper;

	public FormObjectTreeServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormObjectTree> list) {
		for (FormObjectTree formObjectTree : list) {
		   if (StringUtils.isEmpty(formObjectTree.getId())) {
			formObjectTree.setId(idGenerator.getNextId("FORM_OBJECT_TREE"));
		   }
		}
		
		int batch_size = 100;
                List<FormObjectTree> rows = new ArrayList<FormObjectTree>(batch_size);

		for (FormObjectTree bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//	formObjectTreeMapper.bulkInsertFormObjectTree_oracle(list);
				} else {
				//	formObjectTreeMapper.bulkInsertFormObjectTree(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//	formObjectTreeMapper.bulkInsertFormObjectTree_oracle(list);
			} else {
			//	formObjectTreeMapper.bulkInsertFormObjectTree(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formObjectTreeMapper.deleteFormObjectTreeById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formObjectTreeMapper.deleteFormObjectTreeById(id);
		}
	    }
	}

	public int count(FormObjectTreeQuery query) {
		return formObjectTreeMapper.getFormObjectTreeCount(query);
	}

	public List<FormObjectTree> list(FormObjectTreeQuery query) {
		List<FormObjectTree> list = formObjectTreeMapper.getFormObjectTrees(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormObjectTreeCountByQueryCriteria(FormObjectTreeQuery query) {
		return formObjectTreeMapper.getFormObjectTreeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormObjectTree> getFormObjectTreesByQueryCriteria(int start, int pageSize,
			FormObjectTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormObjectTree> rows = sqlSessionTemplate.selectList(
				"getFormObjectTrees", query, rowBounds);
		return rows;
	}


	public FormObjectTree getFormObjectTree(String id) {
	        if(id == null){
		    return null;
		}
		FormObjectTree formObjectTree = formObjectTreeMapper.getFormObjectTreeById(id);
		return formObjectTree;
	}

	@Transactional
	public void save(FormObjectTree formObjectTree) {
           if (StringUtils.isEmpty(formObjectTree.getId())) {
	        formObjectTree.setId(idGenerator.getNextId("FORM_OBJECT_TREE"));
		//formObjectTree.setCreateDate(new Date());
		//formObjectTree.setDeleteFlag(0);
		formObjectTreeMapper.insertFormObjectTree(formObjectTree);
	       } else {
		formObjectTreeMapper.updateFormObjectTree(formObjectTree);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormObjectTreeMapper")
	public void setFormObjectTreeMapper(FormObjectTreeMapper formObjectTreeMapper) {
		this.formObjectTreeMapper = formObjectTreeMapper;
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
