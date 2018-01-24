package com.glaf.form.core.service;

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

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Service("com.glaf.form.core.service.actReBusinessService")
@Transactional(readOnly = true) 
public class ActReBusinessServiceImpl implements ActReBusinessService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ActReBusinessMapper actReBusinessMapper;

	public ActReBusinessServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		actReBusinessMapper.deleteActReBusinessById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    actReBusinessMapper.deleteActReBusinessById(id);
		}
	    }
	}

	public int count(ActReBusinessQuery query) {
		return actReBusinessMapper.getActReBusinessCount(query);
	}

	public List<ActReBusiness> list(ActReBusinessQuery query) {
		List<ActReBusiness> list = actReBusinessMapper.getActReBusinesss(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getActReBusinessCountByQueryCriteria(ActReBusinessQuery query) {
		return actReBusinessMapper.getActReBusinessCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ActReBusiness> getActReBusinesssByQueryCriteria(int start, int pageSize,
			ActReBusinessQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ActReBusiness> rows = sqlSessionTemplate.selectList(
				"getActReBusinesss", query, rowBounds);
		return rows;
	}


	public ActReBusiness getActReBusiness(Long id) {
	        if(id == null){
		    return null;
		}
		ActReBusiness actReBusiness = actReBusinessMapper.getActReBusinessById(id);
		return actReBusiness;
	}

	@Transactional
	public void save(ActReBusiness actReBusiness) {
            if ( actReBusiness.getId()  == null) {
	        actReBusiness.setId(idGenerator.nextId("ACT_RE_BUSINESS"));
		//actReBusiness.setCreateDate(new Date());
		//actReBusiness.setDeleteFlag(0);
		actReBusinessMapper.insertActReBusiness(actReBusiness);
	       } else {
		actReBusinessMapper.updateActReBusiness(actReBusiness);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.ActReBusinessMapper")
	public void setActReBusinessMapper(ActReBusinessMapper actReBusinessMapper) {
		this.actReBusinessMapper = actReBusinessMapper;
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
