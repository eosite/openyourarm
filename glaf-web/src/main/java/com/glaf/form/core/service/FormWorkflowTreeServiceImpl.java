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

@Service("com.glaf.form.core.service.formWorkflowTreeService")
@Transactional(readOnly = true) 
public class FormWorkflowTreeServiceImpl implements FormWorkflowTreeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormWorkflowTreeMapper formWorkflowTreeMapper;

	public FormWorkflowTreeServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormWorkflowTree> list) {
		for (FormWorkflowTree formWorkflowTree : list) {
		    if ( formWorkflowTree.getId()  == null) {
			formWorkflowTree.setId(idGenerator.nextId("FORM_WORKFLOW_TREE"));
		    }
		}
		
		int batch_size = 100;
                List<FormWorkflowTree> rows = new ArrayList<FormWorkflowTree>(batch_size);

		for (FormWorkflowTree bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//	formWorkflowTreeMapper.bulkInsertFormWorkflowTree_oracle(list);
				} else {
				//	formWorkflowTreeMapper.bulkInsertFormWorkflowTree(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//formWorkflowTreeMapper.bulkInsertFormWorkflowTree_oracle(list);
			} else {
				//formWorkflowTreeMapper.bulkInsertFormWorkflowTree(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		formWorkflowTreeMapper.deleteFormWorkflowTreeById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    formWorkflowTreeMapper.deleteFormWorkflowTreeById(id);
		}
	    }
	}

	public int count(FormWorkflowTreeQuery query) {
		return formWorkflowTreeMapper.getFormWorkflowTreeCount(query);
	}

	public List<FormWorkflowTree> list(FormWorkflowTreeQuery query) {
		List<FormWorkflowTree> list = formWorkflowTreeMapper.getFormWorkflowTrees(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormWorkflowTreeCountByQueryCriteria(FormWorkflowTreeQuery query) {
		return formWorkflowTreeMapper.getFormWorkflowTreeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormWorkflowTree> getFormWorkflowTreesByQueryCriteria(int start, int pageSize,
			FormWorkflowTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormWorkflowTree> rows = sqlSessionTemplate.selectList(
				"getFormWorkflowTrees", query, rowBounds);
		return rows;
	}


	public FormWorkflowTree getFormWorkflowTree(Long id) {
	        if(id == null){
		    return null;
		}
		FormWorkflowTree formWorkflowTree = formWorkflowTreeMapper.getFormWorkflowTreeById(id);
		return formWorkflowTree;
	}

	@Transactional
	public void save(FormWorkflowTree formWorkflowTree) {
            if ( formWorkflowTree.getId()  == null) {
	        formWorkflowTree.setId(idGenerator.nextId("FORM_WORKFLOW_TREE"));
		//formWorkflowTree.setCreateDate(new Date());
		//formWorkflowTree.setDeleteFlag(0);
		formWorkflowTreeMapper.insertFormWorkflowTree(formWorkflowTree);
	       } else {
		formWorkflowTreeMapper.updateFormWorkflowTree(formWorkflowTree);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormWorkflowTreeMapper")
	public void setFormWorkflowTreeMapper(FormWorkflowTreeMapper formWorkflowTreeMapper) {
		this.formWorkflowTreeMapper = formWorkflowTreeMapper;
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
		public void deleteByPdefId(String defId) {
			this.formWorkflowTreeMapper.deleteFormWorkflowTreesByPdefId(defId);
		}

}
