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

@Service("com.glaf.dep.report.service.depReportCellDataService")
@Transactional(readOnly = true) 
public class DepReportCellDataServiceImpl implements DepReportCellDataService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepReportCellDataMapper depReportCellDataMapper;

	public DepReportCellDataServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		depReportCellDataMapper.deleteDepReportCellDataById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    depReportCellDataMapper.deleteDepReportCellDataById(id);
		}
	    }
	}

	public int count(DepReportCellDataQuery query) {
		return depReportCellDataMapper.getDepReportCellDataCount(query);
	}

	public List<DepReportCellData> list(DepReportCellDataQuery query) {
		List<DepReportCellData> list = depReportCellDataMapper.getDepReportCellDatas(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDepReportCellDataCountByQueryCriteria(DepReportCellDataQuery query) {
		return depReportCellDataMapper.getDepReportCellDataCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepReportCellData> getDepReportCellDatasByQueryCriteria(int start, int pageSize,
			DepReportCellDataQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepReportCellData> rows = sqlSessionTemplate.selectList(
				"getDepReportCellDatas", query, rowBounds);
		return rows;
	}


	public DepReportCellData getDepReportCellData(Long id) {
	        if(id == null){
		    return null;
		}
		DepReportCellData depReportCellData = depReportCellDataMapper.getDepReportCellDataById(id);
		return depReportCellData;
	}

	@Transactional
	public void save(DepReportCellData depReportCellData) {
            if ( depReportCellData.getId()  == null) {
	        depReportCellData.setId(idGenerator.nextId("DEP_REPORT_CELLDATA"));
		//depReportCellData.setCreateDate(new Date());
		//depReportCellData.setDeleteFlag(0);
		depReportCellDataMapper.insertDepReportCellData(depReportCellData);
	       } else {
		depReportCellDataMapper.updateDepReportCellData(depReportCellData);
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

	@javax.annotation.Resource(name = "com.glaf.dep.report.mapper.DepReportCellDataMapper")
	public void setDepReportCellDataMapper(DepReportCellDataMapper depReportCellDataMapper) {
		this.depReportCellDataMapper = depReportCellDataMapper;
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
