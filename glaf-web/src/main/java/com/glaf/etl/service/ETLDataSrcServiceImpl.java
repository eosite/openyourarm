package com.glaf.etl.service;


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
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.etl.mapper.*;
import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

@Service("com.glaf.etl.service.eTLDataSrcService")
@Transactional(readOnly = true) 
public class ETLDataSrcServiceImpl implements ETLDataSrcService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ETLDataSrcMapper eTLDataSrcMapper;

	public ETLDataSrcServiceImpl() {

	}


//        @Transactional
//	public void bulkInsert(List<ETLDataSrc> list) {
//		for (ETLDataSrc eTLDataSrc : list) {
//		   if (StringUtils.isEmpty(eTLDataSrc.getId())) {
//			eTLDataSrc.setId(idGenerator.getNextId("ETL_DATASRC"));
//		   }
//		}
//		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			eTLDataSrcMapper.bulkInsertETLDataSrc_oracle(list);
//		} else {
//			eTLDataSrcMapper.bulkInsertETLDataSrc(list);
//		}
//	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		eTLDataSrcMapper.deleteETLDataSrcById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    eTLDataSrcMapper.deleteETLDataSrcById(id);
		}
	    }
	}

	public int count(ETLDataSrcQuery query) {
		return eTLDataSrcMapper.getETLDataSrcCount(query);
	}

	public List<ETLDataSrc> list(ETLDataSrcQuery query) {
		List<ETLDataSrc> list = eTLDataSrcMapper.getETLDataSrcs(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getETLDataSrcCountByQueryCriteria(ETLDataSrcQuery query) {
		return eTLDataSrcMapper.getETLDataSrcCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ETLDataSrc> getETLDataSrcsByQueryCriteria(int start, int pageSize,
			ETLDataSrcQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ETLDataSrc> rows = sqlSessionTemplate.selectList(
				"getETLDataSrcs", query, rowBounds);
		return rows;
	}


	public ETLDataSrc getETLDataSrc(String id) {
	        if(id == null){
		    return null;
		}
		ETLDataSrc eTLDataSrc = eTLDataSrcMapper.getETLDataSrcById(id);
		return eTLDataSrc;
	}

	@Transactional
	public void save(ETLDataSrc eTLDataSrc) {
           if (StringUtils.isEmpty(eTLDataSrc.getId())) {
	        eTLDataSrc.setId(UUID32.getUUID());
		//eTLDataSrc.setCreateDate(new Date());
		//eTLDataSrc.setDeleteFlag(0);
		eTLDataSrcMapper.insertETLDataSrc(eTLDataSrc);
	       } else {
		eTLDataSrcMapper.updateETLDataSrc(eTLDataSrc);
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

	@javax.annotation.Resource(name = "com.glaf.etl.mapper.ETLDataSrcMapper")
	public void setETLDataSrcMapper(ETLDataSrcMapper eTLDataSrcMapper) {
		this.eTLDataSrcMapper = eTLDataSrcMapper;
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
