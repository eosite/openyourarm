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

@Service("com.glaf.dep.report.service.depReportCellPosService")
@Transactional(readOnly = true) 
public class DepReportCellPosServiceImpl implements DepReportCellPosService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepReportCellPosMapper depReportCellPosMapper;

	public DepReportCellPosServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		depReportCellPosMapper.deleteDepReportCellPosById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    depReportCellPosMapper.deleteDepReportCellPosById(id);
		}
	    }
	}

	public int count(DepReportCellPosQuery query) {
		return depReportCellPosMapper.getDepReportCellPosCount(query);
	}

	public List<DepReportCellPos> list(DepReportCellPosQuery query) {
		List<DepReportCellPos> list = depReportCellPosMapper.getDepReportCellPoss(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDepReportCellPosCountByQueryCriteria(DepReportCellPosQuery query) {
		return depReportCellPosMapper.getDepReportCellPosCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepReportCellPos> getDepReportCellPossByQueryCriteria(int start, int pageSize,
			DepReportCellPosQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepReportCellPos> rows = sqlSessionTemplate.selectList(
				"getDepReportCellPoss", query, rowBounds);
		return rows;
	}


	public DepReportCellPos getDepReportCellPos(Long id) {
	        if(id == null){
		    return null;
		}
		DepReportCellPos depReportCellPos = depReportCellPosMapper.getDepReportCellPosById(id);
		return depReportCellPos;
	}

	@Transactional
	public void save(DepReportCellPos depReportCellPos) {
            if ( depReportCellPos.getId()  == null) {
	        depReportCellPos.setId(idGenerator.nextId("DEP_REPORT_CELL_POS"));
		//depReportCellPos.setCreateDate(new Date());
		//depReportCellPos.setDeleteFlag(0);
		depReportCellPosMapper.insertDepReportCellPos(depReportCellPos);
	       } else {
		depReportCellPosMapper.updateDepReportCellPos(depReportCellPos);
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

	@javax.annotation.Resource(name = "com.glaf.dep.report.mapper.DepReportCellPosMapper")
	public void setDepReportCellPosMapper(DepReportCellPosMapper depReportCellPosMapper) {
		this.depReportCellPosMapper = depReportCellPosMapper;
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
