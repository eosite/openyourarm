package com.glaf.etl.service;

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

import com.glaf.etl.mapper.*;
import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

@Service("com.glaf.etl.service.etlDataTargetService")
@Transactional(readOnly = true) 
public class EtlDataTargetServiceImpl implements EtlDataTargetService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EtlDataTargetMapper etlDataTargetMapper;

	public EtlDataTargetServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		etlDataTargetMapper.deleteEtlDataTargetById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> targetId_s) {
	    if(targetId_s != null && !targetId_s.isEmpty()){
		for(String id : targetId_s){
		    etlDataTargetMapper.deleteEtlDataTargetById(id);
		}
	    }
	}

	public int count(EtlDataTargetQuery query) {
		return etlDataTargetMapper.getEtlDataTargetCount(query);
	}

	public List<EtlDataTarget> list(EtlDataTargetQuery query) {
		List<EtlDataTarget> list = etlDataTargetMapper.getEtlDataTargets(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getEtlDataTargetCountByQueryCriteria(EtlDataTargetQuery query) {
		return etlDataTargetMapper.getEtlDataTargetCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EtlDataTarget> getEtlDataTargetsByQueryCriteria(int start, int pageSize,
			EtlDataTargetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EtlDataTarget> rows = sqlSessionTemplate.selectList(
				"getEtlDataTargets", query, rowBounds);
		return rows;
	}


	public EtlDataTarget getEtlDataTarget(String id) {
	        if(id == null){
		    return null;
		}
		EtlDataTarget etlDataTarget = etlDataTargetMapper.getEtlDataTargetById(id);
		return etlDataTarget;
	}

	@Transactional
	public void save(EtlDataTarget etlDataTarget) {
           if (StringUtils.isEmpty(etlDataTarget.getTargetId_())) {
	        etlDataTarget.setTargetId_(idGenerator.getNextId("ETL_DATATARGET"));
		//etlDataTarget.setCreateDate(new Date());
		//etlDataTarget.setDeleteFlag(0);
		etlDataTargetMapper.insertEtlDataTarget(etlDataTarget);
	       } else {
		etlDataTargetMapper.updateEtlDataTarget(etlDataTarget);
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

	@javax.annotation.Resource(name = "com.glaf.etl.mapper.EtlDataTargetMapper")
	public void setEtlDataTargetMapper(EtlDataTargetMapper etlDataTargetMapper) {
		this.etlDataTargetMapper = etlDataTargetMapper;
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
