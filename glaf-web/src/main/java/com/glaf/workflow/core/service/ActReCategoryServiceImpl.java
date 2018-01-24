package com.glaf.workflow.core.service;


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

import com.glaf.workflow.core.mapper.*;
import com.glaf.workflow.core.domain.*;
import com.glaf.workflow.core.query.*;

@Service("com.glaf.workflow.core.service.actReCategoryService")
@Transactional(readOnly = true) 
public class ActReCategoryServiceImpl implements ActReCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ActReCategoryMapper actReCategoryMapper;

	public ActReCategoryServiceImpl() {

	}


	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		actReCategoryMapper.deleteActReCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    actReCategoryMapper.deleteActReCategoryById(id);
		}
	    }
	}

	public int count(ActReCategoryQuery query) {
		return actReCategoryMapper.getActReCategoryCount(query);
	}

	public List<ActReCategory> list(ActReCategoryQuery query) {
		List<ActReCategory> list = actReCategoryMapper.getActReCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getActReCategoryCountByQueryCriteria(ActReCategoryQuery query) {
		return actReCategoryMapper.getActReCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ActReCategory> getActReCategorysByQueryCriteria(int start, int pageSize,
			ActReCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ActReCategory> rows = sqlSessionTemplate.selectList(
				"getActReCategorys", query, rowBounds);
		return rows;
	}


	public ActReCategory getActReCategory(Long id) {
	        if(id == null){
		    return null;
		}
		ActReCategory actReCategory = actReCategoryMapper.getActReCategoryById(id);
		return actReCategory;
	}

	@Transactional
	public void save(ActReCategory actReCategory) {
            if ( actReCategory.getId()  == null) {
	        actReCategory.setId(idGenerator.nextId("ACT_RE_CATEGORY"));
		//actReCategory.setCreateDate(new Date());
		//actReCategory.setDeleteFlag(0);
		actReCategoryMapper.insertActReCategory(actReCategory);
	       } else {
		actReCategoryMapper.updateActReCategory(actReCategory);
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

	@javax.annotation.Resource(name = "com.glaf.workflow.core.mapper.ActReCategoryMapper")
	public void setActReCategoryMapper(ActReCategoryMapper actReCategoryMapper) {
		this.actReCategoryMapper = actReCategoryMapper;
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
		public void rename(Long categoryId, String name, String actorId, Date modifyDatatime) throws Exception {
			// TODO Auto-generated method stub
			try{
				actReCategoryMapper.rename(categoryId,name,actorId,modifyDatatime);
				}catch(Exception e){
					throw e;
				}
		}


		@Override
		public void move(String moveType, Long categoryId, Long pId, String treeId, String actorId, Date modifyDatatime)
				throws Exception {
			// TODO Auto-generated method stub
			try{
				if(moveType.equals("inner"))
					actReCategoryMapper.move(categoryId,pId,treeId,actorId,modifyDatatime);
				}catch(Exception e){
					throw e;
				}
		}


		@Override
		public void workFlowMoveToCategory(String modelId, String categoryId) {
			// TODO Auto-generated method stub
			actReCategoryMapper.workFlowMoveToCategory( modelId, categoryId);
		}

}
