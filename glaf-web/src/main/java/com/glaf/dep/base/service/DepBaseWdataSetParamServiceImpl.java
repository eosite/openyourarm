package com.glaf.dep.base.service;

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

import com.glaf.dep.base.mapper.*;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

@Service("com.glaf.dep.base.service.depBaseWdataSetParamService")
@Transactional(readOnly = true) 
public class DepBaseWdataSetParamServiceImpl implements DepBaseWdataSetParamService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBaseWdataSetParamMapper depBaseWdataSetParamMapper;

	public DepBaseWdataSetParamServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		depBaseWdataSetParamMapper.deleteDepBaseWdataSetParamById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    depBaseWdataSetParamMapper.deleteDepBaseWdataSetParamById(id);
		}
	    }
	}

	public int count(DepBaseWdataSetParamQuery query) {
		return depBaseWdataSetParamMapper.getDepBaseWdataSetParamCount(query);
	}

	public List<DepBaseWdataSetParam> list(DepBaseWdataSetParamQuery query) {
		List<DepBaseWdataSetParam> list = depBaseWdataSetParamMapper.getDepBaseWdataSetParams(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDepBaseWdataSetParamCountByQueryCriteria(DepBaseWdataSetParamQuery query) {
		return depBaseWdataSetParamMapper.getDepBaseWdataSetParamCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseWdataSetParam> getDepBaseWdataSetParamsByQueryCriteria(int start, int pageSize,
			DepBaseWdataSetParamQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseWdataSetParam> rows = sqlSessionTemplate.selectList(
				"getDepBaseWdataSetParams", query, rowBounds);
		return rows;
	}


	public DepBaseWdataSetParam getDepBaseWdataSetParam(Long id) {
	        if(id == null){
		    return null;
		}
		DepBaseWdataSetParam depBaseWdataSetParam = depBaseWdataSetParamMapper.getDepBaseWdataSetParamById(id);
		return depBaseWdataSetParam;
	}

	@Transactional
	public void save(DepBaseWdataSetParam depBaseWdataSetParam) {
            if ( depBaseWdataSetParam.getId()  == null) {
	        depBaseWdataSetParam.setId(idGenerator.nextId("DEP_BASE_WDATASET_PARAM_"));
		//depBaseWdataSetParam.setCreateDate(new Date());
		//depBaseWdataSetParam.setDeleteFlag(0);
		depBaseWdataSetParamMapper.insertDepBaseWdataSetParam(depBaseWdataSetParam);
	       } else {
		depBaseWdataSetParamMapper.updateDepBaseWdataSetParam(depBaseWdataSetParam);
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseWdataSetParamMapper")
	public void setDepBaseWdataSetParamMapper(DepBaseWdataSetParamMapper depBaseWdataSetParamMapper) {
		this.depBaseWdataSetParamMapper = depBaseWdataSetParamMapper;
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
