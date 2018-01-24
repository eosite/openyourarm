package com.glaf.maildata.service;

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
import com.glaf.core.util.UUID32;
import com.glaf.maildata.mapper.*;
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

@Service("com.glaf.maildata.service.emailRecAttService")
@Transactional(readOnly = true) 
public class EmailRecAttServiceImpl implements EmailRecAttService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EmailRecAttMapper emailRecAttMapper;

	public EmailRecAttServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		emailRecAttMapper.deleteEmailRecAttById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> fileIds) {
	    if(fileIds != null && !fileIds.isEmpty()){
		for(String id : fileIds){
		    emailRecAttMapper.deleteEmailRecAttById(id);
		}
	    }
	}

	public int count(EmailRecAttQuery query) {
		return emailRecAttMapper.getEmailRecAttCount(query);
	}

	public List<EmailRecAtt> list(EmailRecAttQuery query) {
		List<EmailRecAtt> list = emailRecAttMapper.getEmailRecAtts(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getEmailRecAttCountByQueryCriteria(EmailRecAttQuery query) {
		return emailRecAttMapper.getEmailRecAttCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EmailRecAtt> getEmailRecAttsByQueryCriteria(int start, int pageSize,
			EmailRecAttQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EmailRecAtt> rows = sqlSessionTemplate.selectList(
				"getEmailRecAtts", query, rowBounds);
		return rows;
	}


	@Override
	public List<EmailRecAtt> getEmailRecAttsByMailId(String mailId) {
		List<EmailRecAtt> list = emailRecAttMapper.getEmailRecAttsByMailId(mailId);
		return list;
	}

	public EmailRecAtt getEmailRecAtt(String id) {
	        if(id == null){
		    return null;
		}
		EmailRecAtt emailRecAtt = emailRecAttMapper.getEmailRecAttById(id);
		return emailRecAtt;
	}

	@Transactional
	public void save(EmailRecAtt emailRecAtt) {
           if (StringUtils.isEmpty(emailRecAtt.getFileId())) {
	        emailRecAtt.setFileId(UUID32.getUUID());
		//emailRecAtt.setCreateDate(new Date());
		//emailRecAtt.setDeleteFlag(0);
		emailRecAttMapper.insertEmailRecAtt(emailRecAtt);
	       } else {
		emailRecAttMapper.updateEmailRecAtt(emailRecAtt);
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

	@javax.annotation.Resource(name = "com.glaf.maildata.mapper.EmailRecAttMapper")
	public void setEmailRecAttMapper(EmailRecAttMapper emailRecAttMapper) {
		this.emailRecAttMapper = emailRecAttMapper;
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
