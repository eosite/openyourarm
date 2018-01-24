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

@Service("com.glaf.dep.base.service.depBaseWdataSetColumnService")
@Transactional(readOnly = true) 
public class DepBaseWdataSetColumnServiceImpl implements DepBaseWdataSetColumnService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBaseWdataSetColumnMapper depBaseWdataSetColumnMapper;

	public DepBaseWdataSetColumnServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		depBaseWdataSetColumnMapper.deleteDepBaseWdataSetColumnById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    depBaseWdataSetColumnMapper.deleteDepBaseWdataSetColumnById(id);
		}
	    }
	}

	public int count(DepBaseWdataSetColumnQuery query) {
		return depBaseWdataSetColumnMapper.getDepBaseWdataSetColumnCount(query);
	}

	public List<DepBaseWdataSetColumn> list(DepBaseWdataSetColumnQuery query) {
		List<DepBaseWdataSetColumn> list = depBaseWdataSetColumnMapper.getDepBaseWdataSetColumns(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDepBaseWdataSetColumnCountByQueryCriteria(DepBaseWdataSetColumnQuery query) {
		return depBaseWdataSetColumnMapper.getDepBaseWdataSetColumnCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseWdataSetColumn> getDepBaseWdataSetColumnsByQueryCriteria(int start, int pageSize,
			DepBaseWdataSetColumnQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseWdataSetColumn> rows = sqlSessionTemplate.selectList(
				"getDepBaseWdataSetColumns", query, rowBounds);
		return rows;
	}


	public DepBaseWdataSetColumn getDepBaseWdataSetColumn(Long id) {
	        if(id == null){
		    return null;
		}
		DepBaseWdataSetColumn depBaseWdataSetColumn = depBaseWdataSetColumnMapper.getDepBaseWdataSetColumnById(id);
		return depBaseWdataSetColumn;
	}

	@Transactional
	public void save(DepBaseWdataSetColumn depBaseWdataSetColumn) {
            if ( depBaseWdataSetColumn.getId()  == null) {
	        depBaseWdataSetColumn.setId(idGenerator.nextId("DEP_BASE_WDATASET_COLUMN_"));
		//depBaseWdataSetColumn.setCreateDate(new Date());
		//depBaseWdataSetColumn.setDeleteFlag(0);
		depBaseWdataSetColumnMapper.insertDepBaseWdataSetColumn(depBaseWdataSetColumn);
	       } else {
		depBaseWdataSetColumnMapper.updateDepBaseWdataSetColumn(depBaseWdataSetColumn);
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseWdataSetColumnMapper")
	public void setDepBaseWdataSetColumnMapper(DepBaseWdataSetColumnMapper depBaseWdataSetColumnMapper) {
		this.depBaseWdataSetColumnMapper = depBaseWdataSetColumnMapper;
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
