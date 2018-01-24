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
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Service("com.glaf.form.core.service.msgValidHistoryService")
@Transactional(readOnly = true) 
public class MsgValidHistoryServiceImpl implements MsgValidHistoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MsgValidHistoryMapper msgValidHistoryMapper;

	public MsgValidHistoryServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<MsgValidHistory> list) {
		for (MsgValidHistory msgValidHistory : list) {
		   if (StringUtils.isEmpty(msgValidHistory.getId())) {
			msgValidHistory.setId(idGenerator.getNextId("MSGVALIDHISTORY_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		} else {
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		msgValidHistoryMapper.deleteMsgValidHistoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    msgValidHistoryMapper.deleteMsgValidHistoryById(id);
		}
	    }
	}

	public int count(MsgValidHistoryQuery query) {
		return msgValidHistoryMapper.getMsgValidHistoryCount(query);
	}

	public List<MsgValidHistory> list(MsgValidHistoryQuery query) {
		List<MsgValidHistory> list = msgValidHistoryMapper.getMsgValidHistorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getMsgValidHistoryCountByQueryCriteria(MsgValidHistoryQuery query) {
		return msgValidHistoryMapper.getMsgValidHistoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MsgValidHistory> getMsgValidHistorysByQueryCriteria(int start, int pageSize,
			MsgValidHistoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MsgValidHistory> rows = sqlSessionTemplate.selectList(
				"getMsgValidHistorys", query, rowBounds);
		return rows;
	}


	public MsgValidHistory getMsgValidHistory(String id) {
	        if(id == null){
		    return null;
		}
		MsgValidHistory msgValidHistory = msgValidHistoryMapper.getMsgValidHistoryById(id);
		return msgValidHistory;
	}

	@Transactional
	public void save(MsgValidHistory msgValidHistory) {
           if (StringUtils.isEmpty(msgValidHistory.getId())) {
	        msgValidHistory.setId(idGenerator.getNextId("MSGVALIDHISTORY_"));
		//msgValidHistory.setCreateDate(new Date());
		//msgValidHistory.setDeleteFlag(0);
		msgValidHistoryMapper.insertMsgValidHistory(msgValidHistory);
	       } else {
		msgValidHistoryMapper.updateMsgValidHistory(msgValidHistory);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.MsgValidHistoryMapper")
	public void setMsgValidHistoryMapper(MsgValidHistoryMapper msgValidHistoryMapper) {
		this.msgValidHistoryMapper = msgValidHistoryMapper;
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
