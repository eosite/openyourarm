package com.glaf.dep.report.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.util.JdbcUtils;

import com.glaf.dep.report.mapper.*;
import com.glaf.dep.report.domain.*;
import com.glaf.dep.report.query.*;

@Service("com.glaf.dep.report.service.depReportValidationService")
@Transactional(readOnly = true) 
public class DepReportValidationServiceImpl implements DepReportValidationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepReportValidationMapper depReportValidationMapper;

	public DepReportValidationServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		depReportValidationMapper.deleteDepReportValidationById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    depReportValidationMapper.deleteDepReportValidationById(id);
		}
	    }
	}

	public int count(DepReportValidationQuery query) {
		return depReportValidationMapper.getDepReportValidationCount(query);
	}

	public List<DepReportValidation> list(DepReportValidationQuery query) {
		List<DepReportValidation> list = depReportValidationMapper.getDepReportValidations(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDepReportValidationCountByQueryCriteria(DepReportValidationQuery query) {
		return depReportValidationMapper.getDepReportValidationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepReportValidation> getDepReportValidationsByQueryCriteria(int start, int pageSize,
			DepReportValidationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepReportValidation> rows = sqlSessionTemplate.selectList(
				"getDepReportValidations", query, rowBounds);
		return rows;
	}


	public DepReportValidation getDepReportValidation(Long id) {
	        if(id == null){
		    return null;
		}
		DepReportValidation depReportValidation = depReportValidationMapper.getDepReportValidationById(id);
		return depReportValidation;
	}

	@Transactional
	public void save(DepReportValidation depReportValidation) {
            if ( depReportValidation.getId()  == null) {
	        depReportValidation.setId(idGenerator.nextId("DEP_REPORT_VALIDATION"));
		//depReportValidation.setCreateDate(new Date());
		//depReportValidation.setDeleteFlag(0);
		depReportValidationMapper.insertDepReportValidation(depReportValidation);
	       } else {
		depReportValidationMapper.updateDepReportValidation(depReportValidation);
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

	@javax.annotation.Resource(name = "com.glaf.dep.report.mapper.DepReportValidationMapper")
	public void setDepReportValidationMapper(DepReportValidationMapper depReportValidationMapper) {
		this.depReportValidationMapper = depReportValidationMapper;
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
