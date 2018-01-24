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

@Service("com.glaf.report.core.service.reportCategoryService")
@Transactional(readOnly = true) 
public class ReportCategoryServiceImpl implements ReportCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportCategoryMapper reportCategoryMapper;

	public ReportCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
	     if(id != null ){
		reportCategoryMapper.deleteReportCategoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Integer> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Integer id : ids){
		    reportCategoryMapper.deleteReportCategoryById(id);
		}
	    }
	}

	public int count(ReportCategoryQuery query) {
		return reportCategoryMapper.getReportCategoryCount(query);
	}

	public List<ReportCategory> list(ReportCategoryQuery query) {
		List<ReportCategory> list = reportCategoryMapper.getReportCategorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getReportCategoryCountByQueryCriteria(ReportCategoryQuery query) {
		return reportCategoryMapper.getReportCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportCategory> getReportCategorysByQueryCriteria(int start, int pageSize,
			ReportCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportCategory> rows = sqlSessionTemplate.selectList(
				"getReportCategorys", query, rowBounds);
		return rows;
	}


	public ReportCategory getReportCategory(Integer id) {
	        if(id == null){
		    return null;
		}
		ReportCategory reportCategory = reportCategoryMapper.getReportCategoryById(id);
		return reportCategory;
	}

	@Transactional
	public void save(ReportCategory reportCategory) {
            if ( reportCategory.getId()  == null) {
	        reportCategory.setId(idGenerator.nextId("REPORT_CATEGORY").longValue());
	        reportCategory.setTreeId(reportCategory.getTreeId()+reportCategory.getId()+"|");
		//reportCategory.setCreateDate(new Date());
		//reportCategory.setDeleteFlag(0);
		reportCategoryMapper.insertReportCategory(reportCategory);
	       } else {
		reportCategoryMapper.updateReportCategory(reportCategory);
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

	@javax.annotation.Resource(name = "com.glaf.report.core.mapper.ReportCategoryMapper")
	public void setReportCategoryMapper(ReportCategoryMapper reportCategoryMapper) {
		this.reportCategoryMapper = reportCategoryMapper;
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
		public void rename(Long categoryId, String name,String actorId,Date modifyDatatime) throws Exception {
			// TODO Auto-generated method stub
			try{
				reportCategoryMapper.rename(categoryId,name,actorId,modifyDatatime);
				}catch(Exception e){
					throw e;
				}
		}

		@Override
		public void move(String moveType,Long categoryId, Long pId,String treeId,String actorId,Date modifyDatatime) throws Exception {
			// TODO Auto-generated method stub
			try{
				if(moveType.equals("inner"))
				reportCategoryMapper.move(categoryId,pId,treeId,actorId,modifyDatatime);
				}catch(Exception e){
					throw e;
				}
		}

}
