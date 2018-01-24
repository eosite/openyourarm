package com.glaf.form.core.service;


import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Service("com.glaf.form.core.service.wdatasetSqlliteService")
@Transactional(readOnly = true) 
public class WdatasetSqlliteServiceImpl implements WdatasetSqlliteService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected WdatasetSqlliteMapper wdatasetSqlliteMapper;

	public WdatasetSqlliteServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<WdatasetSqllite> list) {
		for (WdatasetSqllite wdatasetSqllite : list) {
		   if (wdatasetSqllite.getId() == null) {
			wdatasetSqllite.setId(idGenerator.nextId("DEP_BASE_WDATASET_SQLLITE_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			wdatasetSqlliteMapper.bulkInsertWdatasetSqllite_oracle(list);
		} else {
//			wdatasetSqlliteMapper.bulkInsertWdatasetSqllite(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		wdatasetSqlliteMapper.deleteWdatasetSqlliteById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    wdatasetSqlliteMapper.deleteWdatasetSqlliteById(id);
		}
	    }
	}

	public int count(WdatasetSqlliteQuery query) {
		return wdatasetSqlliteMapper.getWdatasetSqlliteCount(query);
	}

	public List<WdatasetSqllite> list(WdatasetSqlliteQuery query) {
		List<WdatasetSqllite> list = wdatasetSqlliteMapper.getWdatasetSqllites(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getWdatasetSqlliteCountByQueryCriteria(WdatasetSqlliteQuery query) {
		return wdatasetSqlliteMapper.getWdatasetSqlliteCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<WdatasetSqllite> getWdatasetSqllitesByQueryCriteria(int start, int pageSize,
			WdatasetSqlliteQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<WdatasetSqllite> rows = sqlSessionTemplate.selectList(
				"getWdatasetSqllites", query, rowBounds);
		return rows;
	}


	public WdatasetSqllite getWdatasetSqllite(String id) {
	        if(id == null){
		    return null;
		}
		WdatasetSqllite wdatasetSqllite = wdatasetSqlliteMapper.getWdatasetSqlliteById(id);
		return wdatasetSqllite;
	}

	@Transactional
	public void save(WdatasetSqllite wdatasetSqllite) {
           if (wdatasetSqllite.getId() == null) {
	        wdatasetSqllite.setId(idGenerator.nextId("DEP_BASE_WDATASET_SQLLITE_"));
	        wdatasetSqllite.setSqlliteRuleCode("sqllite" + idGenerator.getNextId("DEP_BASE_WDATASET_SQLLITE_Code"));
		//wdatasetSqllite.setCreateDate(new Date());
		//wdatasetSqllite.setDeleteFlag(0);
		wdatasetSqlliteMapper.insertWdatasetSqllite(wdatasetSqllite);
	       } else {
		wdatasetSqlliteMapper.updateWdatasetSqllite(wdatasetSqllite);
	      }
	}


	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.WdatasetSqlliteMapper")
	public void setWdatasetSqlliteMapper(WdatasetSqlliteMapper wdatasetSqlliteMapper) {
		this.wdatasetSqlliteMapper = wdatasetSqlliteMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

        @javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}


		@Override
		public List<WdatasetSqllite> getWdatasetSqllitesByQueryCriteria2(int start, int pageSize,
				WdatasetSqlliteQuery query) {
			RowBounds rowBounds = new RowBounds(start, pageSize);
			List<WdatasetSqllite> rows = sqlSessionTemplate.selectList(
					"getWdatasetSqllites2", query, rowBounds);
			return rows;
		}


		@Override
		public JSONObject getSqlliteRuleInParam(Long id) {
			// TODO Auto-generated method stub
			return null;
		}

}
