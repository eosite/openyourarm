package com.glaf.report.core.service;


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

import com.glaf.report.core.mapper.*;
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

@Service("com.glaf.report.core.service.reportTmpCategoryService")
@Transactional(readOnly = true) 
public class ReportTmpCategoryServiceImpl implements ReportTmpCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportTmpCategoryMapper reportTmpCategoryMapper;

	public ReportTmpCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		reportTmpCategoryMapper.deleteReportTmpCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    reportTmpCategoryMapper.deleteReportTmpCategoryById(id);
		}
	    }
	}

	public int count(ReportTmpCategoryQuery query) {
		return reportTmpCategoryMapper.getReportTmpCategoryCount(query);
	}

	public List<ReportTmpCategory> list(ReportTmpCategoryQuery query) {
		List<ReportTmpCategory> list = reportTmpCategoryMapper.getReportTmpCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getReportTmpCategoryCountByQueryCriteria(ReportTmpCategoryQuery query) {
		return reportTmpCategoryMapper.getReportTmpCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportTmpCategory> getReportTmpCategorysByQueryCriteria(int start, int pageSize,
			ReportTmpCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportTmpCategory> rows = sqlSessionTemplate.selectList(
				"getReportTmpCategorys", query, rowBounds);
		return rows;
	}


	public ReportTmpCategory getReportTmpCategory(Long id) {
	        if(id == null){
		    return null;
		}
		ReportTmpCategory reportTmpCategory = reportTmpCategoryMapper.getReportTmpCategoryById(id);
		return reportTmpCategory;
	}

	@Transactional
	public void save(ReportTmpCategory reportTmpCategory) {
            if ( reportTmpCategory.getId()  == null) {
	        reportTmpCategory.setId(idGenerator.nextId("REPORT_TMP_CATEGORY"));
		//reportTmpCategory.setCreateDate(new Date());
		//reportTmpCategory.setDeleteFlag(0);
		reportTmpCategoryMapper.insertReportTmpCategory(reportTmpCategory);
	       } else {
		reportTmpCategoryMapper.updateReportTmpCategory(reportTmpCategory);
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

	@javax.annotation.Resource(name = "com.glaf.report.core.mapper.ReportTmpCategoryMapper")
	public void setReportTmpCategoryMapper(ReportTmpCategoryMapper reportTmpCategoryMapper) {
		this.reportTmpCategoryMapper = reportTmpCategoryMapper;
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
