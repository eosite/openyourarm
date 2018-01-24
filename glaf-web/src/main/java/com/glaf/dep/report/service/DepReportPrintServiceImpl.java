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

@Service("com.glaf.dep.report.service.depReportPrintService")
@Transactional(readOnly = true) 
public class DepReportPrintServiceImpl implements DepReportPrintService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepReportPrintMapper depReportPrintMapper;

	public DepReportPrintServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		depReportPrintMapper.deleteDepReportPrintById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    depReportPrintMapper.deleteDepReportPrintById(id);
		}
	    }
	}

	public int count(DepReportPrintQuery query) {
		return depReportPrintMapper.getDepReportPrintCount(query);
	}

	public List<DepReportPrint> list(DepReportPrintQuery query) {
		List<DepReportPrint> list = depReportPrintMapper.getDepReportPrints(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDepReportPrintCountByQueryCriteria(DepReportPrintQuery query) {
		return depReportPrintMapper.getDepReportPrintCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepReportPrint> getDepReportPrintsByQueryCriteria(int start, int pageSize,
			DepReportPrintQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepReportPrint> rows = sqlSessionTemplate.selectList(
				"getDepReportPrints", query, rowBounds);
		return rows;
	}


	public DepReportPrint getDepReportPrint(Long id) {
	        if(id == null){
		    return null;
		}
		DepReportPrint depReportPrint = depReportPrintMapper.getDepReportPrintById(id);
		return depReportPrint;
	}

	@Transactional
	public void save(DepReportPrint depReportPrint) {
            if ( depReportPrint.getId()  == null) {
	        depReportPrint.setId(idGenerator.nextId("DEP_REPORT_PRINT"));
		//depReportPrint.setCreateDate(new Date());
		//depReportPrint.setDeleteFlag(0);
		depReportPrintMapper.insertDepReportPrint(depReportPrint);
	       } else {
		depReportPrintMapper.updateDepReportPrint(depReportPrint);
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

	@javax.annotation.Resource(name = "com.glaf.dep.report.mapper.DepReportPrintMapper")
	public void setDepReportPrintMapper(DepReportPrintMapper depReportPrintMapper) {
		this.depReportPrintMapper = depReportPrintMapper;
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
