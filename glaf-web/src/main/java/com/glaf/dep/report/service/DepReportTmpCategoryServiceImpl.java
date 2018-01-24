package com.glaf.dep.report.service;


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

import com.glaf.dep.report.mapper.*;
import com.glaf.dep.report.domain.*;
import com.glaf.dep.report.query.*;

@Service("com.glaf.dep.report.service.depReportTmpCategoryService")
@Transactional(readOnly = true) 
public class DepReportTmpCategoryServiceImpl implements DepReportTmpCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepReportTmpCategoryMapper depReportTmpCategoryMapper;

	public DepReportTmpCategoryServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<DepReportTmpCategory> list) {
		for (DepReportTmpCategory depReportTmpCategory : list) {
		    if ( depReportTmpCategory.getId()  == null) {
			depReportTmpCategory.setId(idGenerator.nextId("DEP_REPORT_TMPCATEGORY"));
		    }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		//	depReportTmpCategoryMapper.bulkInsertDepReportTmpCategory_oracle(list);
		} else {
		//	depReportTmpCategoryMapper.bulkInsertDepReportTmpCategory(list);
		}
	}


	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		depReportTmpCategoryMapper.deleteDepReportTmpCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    depReportTmpCategoryMapper.deleteDepReportTmpCategoryById(id);
		}
	    }
	}

	public int count(DepReportTmpCategoryQuery query) {
		return depReportTmpCategoryMapper.getDepReportTmpCategoryCount(query);
	}

	public List<DepReportTmpCategory> list(DepReportTmpCategoryQuery query) {
		List<DepReportTmpCategory> list = depReportTmpCategoryMapper.getDepReportTmpCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDepReportTmpCategoryCountByQueryCriteria(DepReportTmpCategoryQuery query) {
		return depReportTmpCategoryMapper.getDepReportTmpCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepReportTmpCategory> getDepReportTmpCategorysByQueryCriteria(int start, int pageSize,
			DepReportTmpCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepReportTmpCategory> rows = sqlSessionTemplate.selectList(
				"getDepReportTmpCategorys", query, rowBounds);
		return rows;
	}


	public DepReportTmpCategory getDepReportTmpCategory(Long id) {
	        if(id == null){
		    return null;
		}
		DepReportTmpCategory depReportTmpCategory = depReportTmpCategoryMapper.getDepReportTmpCategoryById(id);
		return depReportTmpCategory;
	}

	@Transactional
	public void save(DepReportTmpCategory depReportTmpCategory) {
       //     if ( depReportTmpCategory.getId()  == null) {
	        depReportTmpCategory.setId(idGenerator.nextId("DEP_REPORT_TMPCATEGORY"));
		//depReportTmpCategory.setCreateDate(new Date());
		//depReportTmpCategory.setDeleteFlag(0);
		depReportTmpCategoryMapper.insertDepReportTmpCategory(depReportTmpCategory);
	 //      } else {
	//	depReportTmpCategoryMapper.updateDepReportTmpCategory(depReportTmpCategory);
	 //     }
	}


	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.dep.report.mapper.DepReportTmpCategoryMapper")
	public void setDepReportTmpCategoryMapper(DepReportTmpCategoryMapper depReportTmpCategoryMapper) {
		this.depReportTmpCategoryMapper = depReportTmpCategoryMapper;
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
