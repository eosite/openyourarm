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

@Service("com.glaf.dep.report.service.depReportParamService")
@Transactional(readOnly = true) 
public class DepReportParamServiceImpl implements DepReportParamService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepReportParamMapper depReportParamMapper;

	public DepReportParamServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		depReportParamMapper.deleteDepReportParamById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    depReportParamMapper.deleteDepReportParamById(id);
		}
	    }
	}

	public int count(DepReportParamQuery query) {
		return depReportParamMapper.getDepReportParamCount(query);
	}

	public List<DepReportParam> list(DepReportParamQuery query) {
		List<DepReportParam> list = depReportParamMapper.getDepReportParams(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDepReportParamCountByQueryCriteria(DepReportParamQuery query) {
		return depReportParamMapper.getDepReportParamCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepReportParam> getDepReportParamsByQueryCriteria(int start, int pageSize,
			DepReportParamQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepReportParam> rows = sqlSessionTemplate.selectList(
				"getDepReportParams", query, rowBounds);
		return rows;
	}


	public DepReportParam getDepReportParam(Long id) {
	        if(id == null){
		    return null;
		}
		DepReportParam depReportParam = depReportParamMapper.getDepReportParamById(id);
		return depReportParam;
	}

	@Transactional
	public void save(DepReportParam depReportParam) {
            if ( depReportParam.getId()  == null) {
	        depReportParam.setId(idGenerator.nextId("DEP_REPORT_PARAM"));
		//depReportParam.setCreateDate(new Date());
		//depReportParam.setDeleteFlag(0);
		depReportParamMapper.insertDepReportParam(depReportParam);
	       } else {
		depReportParamMapper.updateDepReportParam(depReportParam);
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

	@javax.annotation.Resource(name = "com.glaf.dep.report.mapper.DepReportParamMapper")
	public void setDepReportParamMapper(DepReportParamMapper depReportParamMapper) {
		this.depReportParamMapper = depReportParamMapper;
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
