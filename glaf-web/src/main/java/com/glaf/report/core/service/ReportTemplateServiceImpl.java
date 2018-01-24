package com.glaf.report.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.report.core.domain.ReportTemplate;
import com.glaf.report.core.mapper.ReportTemplateMapper;
import com.glaf.report.core.query.ReportTemplateQuery;

@Service("com.glaf.report.core.service.reportTemplateService")
@Transactional(readOnly = true) 
public class ReportTemplateServiceImpl implements ReportTemplateService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportTemplateMapper reportTemplateMapper;

	public ReportTemplateServiceImpl() {

	}

	@Override
	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		reportTemplateMapper.deleteReportTemplateById(id);
	     }
	}

	@Override
	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    reportTemplateMapper.deleteReportTemplateById(id);
		}
	    }
	}

	public int count(ReportTemplateQuery query) {
		return reportTemplateMapper.getReportTemplateCount(query);
	}

	@Override
	public List<ReportTemplate> list(ReportTemplateQuery query) {
		List<ReportTemplate> list = reportTemplateMapper.getReportTemplates(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	@Override
	public int getReportTemplateCountByQueryCriteria(ReportTemplateQuery query) {
		return reportTemplateMapper.getReportTemplateCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	@Override
	public List<ReportTemplate> getReportTemplatesByQueryCriteria(int start, int pageSize,
			ReportTemplateQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportTemplate> rows = sqlSessionTemplate.selectList(
				"getReportTemplates", query, rowBounds);
		return rows;
	}


	@Override
	public ReportTemplate getReportTemplate(String id) {
	        if(id == null){
		    return null;
		}
		ReportTemplate reportTemplate = reportTemplateMapper.getReportTemplateById(id);
		return reportTemplate;
	}

	@Override
	@Transactional
	public void save(ReportTemplate reportTemplate) {
           if (StringUtils.isEmpty(reportTemplate.getId())) {
	        reportTemplate.setId(idGenerator.getNextId("REPORT_TEMPLATE"));
		//reportTemplate.setCreateDate(new Date());
		//reportTemplate.setDeleteFlag(0);
		reportTemplateMapper.insertReportTemplate(reportTemplate);
	       } else {
		reportTemplateMapper.updateReportTemplate(reportTemplate);
	      }
	}


	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
 		String sql = "  ";//要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
			    psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.report.mapper.ReportTemplateMapper")
	public void setReportTemplateMapper(ReportTemplateMapper reportTemplateMapper) {
		this.reportTemplateMapper = reportTemplateMapper;
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
