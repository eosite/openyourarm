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

@Service("com.glaf.report.core.service.reportTmpDataSetService")
@Transactional(readOnly = true) 
public class ReportTmpDataSetServiceImpl implements ReportTmpDataSetService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportTmpDataSetMapper reportTmpDataSetMapper;

	public ReportTmpDataSetServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<ReportTmpDataSet> list) {
		for (ReportTmpDataSet reportTmpDataSet : list) {
		   if (StringUtils.isEmpty(reportTmpDataSet.getId())) {
			reportTmpDataSet.setId(idGenerator.getNextId("REPORT_TMP_DATASET"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
	//		reportTmpDataSetMapper.bulkInsertReportTmpDataSet_oracle(list);
		} else {
	//		reportTmpDataSetMapper.bulkInsertReportTmpDataSet(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		reportTmpDataSetMapper.deleteReportTmpDataSetById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    reportTmpDataSetMapper.deleteReportTmpDataSetById(id);
		}
	    }
	}

	public int count(ReportTmpDataSetQuery query) {
		return reportTmpDataSetMapper.getReportTmpDataSetCount(query);
	}

	public List<ReportTmpDataSet> list(ReportTmpDataSetQuery query) {
		List<ReportTmpDataSet> list = reportTmpDataSetMapper.getReportTmpDataSets(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getReportTmpDataSetCountByQueryCriteria(ReportTmpDataSetQuery query) {
		return reportTmpDataSetMapper.getReportTmpDataSetCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportTmpDataSet> getReportTmpDataSetsByQueryCriteria(int start, int pageSize,
			ReportTmpDataSetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportTmpDataSet> rows = sqlSessionTemplate.selectList(
				"getReportTmpDataSets", query, rowBounds);
		return rows;
	}


	public ReportTmpDataSet getReportTmpDataSet(String id) {
	        if(id == null){
		    return null;
		}
		ReportTmpDataSet reportTmpDataSet = reportTmpDataSetMapper.getReportTmpDataSetById(id);
		return reportTmpDataSet;
	}

	@Transactional
	public void save(ReportTmpDataSet reportTmpDataSet) {
           if (StringUtils.isEmpty(reportTmpDataSet.getId())) {
	        reportTmpDataSet.setId(idGenerator.getNextId("REPORT_TMP_DATASET"));
		//reportTmpDataSet.setCreateDate(new Date());
		//reportTmpDataSet.setDeleteFlag(0);
		reportTmpDataSetMapper.insertReportTmpDataSet(reportTmpDataSet);
	       } else {
		reportTmpDataSetMapper.updateReportTmpDataSet(reportTmpDataSet);
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

	@javax.annotation.Resource(name = "com.glaf.report.core.mapper.ReportTmpDataSetMapper")
	public void setReportTmpDataSetMapper(ReportTmpDataSetMapper reportTmpDataSetMapper) {
		this.reportTmpDataSetMapper = reportTmpDataSetMapper;
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
