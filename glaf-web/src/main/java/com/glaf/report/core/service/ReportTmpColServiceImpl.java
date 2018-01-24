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

@Service("com.glaf.report.core.service.reportTmpColService")
@Transactional(readOnly = true) 
public class ReportTmpColServiceImpl implements ReportTmpColService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportTmpColMapper reportTmpColMapper;

	public ReportTmpColServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<ReportTmpCol> list) {
		for (ReportTmpCol reportTmpCol : list) {
		   if (StringUtils.isEmpty(reportTmpCol.getId())) {
			reportTmpCol.setId(idGenerator.getNextId("REPORT_TMP_COL"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//reportTmpColMapper.bulkInsertReportTmpCol_oracle(list);
		} else {
			//reportTmpColMapper.bulkInsertReportTmpCol(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		reportTmpColMapper.deleteReportTmpColById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    reportTmpColMapper.deleteReportTmpColById(id);
		}
	    }
	}

	public int count(ReportTmpColQuery query) {
		return reportTmpColMapper.getReportTmpColCount(query);
	}

	public List<ReportTmpCol> list(ReportTmpColQuery query) {
		List<ReportTmpCol> list = reportTmpColMapper.getReportTmpCols(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getReportTmpColCountByQueryCriteria(ReportTmpColQuery query) {
		return reportTmpColMapper.getReportTmpColCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportTmpCol> getReportTmpColsByQueryCriteria(int start, int pageSize,
			ReportTmpColQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportTmpCol> rows = sqlSessionTemplate.selectList(
				"getReportTmpCols", query, rowBounds);
		return rows;
	}


	public ReportTmpCol getReportTmpCol(String id) {
	        if(id == null){
		    return null;
		}
		ReportTmpCol reportTmpCol = reportTmpColMapper.getReportTmpColById(id);
		return reportTmpCol;
	}

	@Transactional
	public void save(ReportTmpCol reportTmpCol) {
           if (StringUtils.isEmpty(reportTmpCol.getId())) {
	        reportTmpCol.setId(idGenerator.getNextId("REPORT_TMP_COL"));
		//reportTmpCol.setCreateDate(new Date());
		//reportTmpCol.setDeleteFlag(0);
		reportTmpColMapper.insertReportTmpCol(reportTmpCol);
	       } else {
		reportTmpColMapper.updateReportTmpCol(reportTmpCol);
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

	@javax.annotation.Resource(name = "com.glaf.report.core.mapper.ReportTmpColMapper")
	public void setReportTmpColMapper(ReportTmpColMapper reportTmpColMapper) {
		this.reportTmpColMapper = reportTmpColMapper;
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
