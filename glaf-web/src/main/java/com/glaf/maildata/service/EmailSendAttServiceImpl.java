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

import com.glaf.maildata.mapper.*;
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

@Service("com.glaf.maildata.service.emailSendAttService")
@Transactional(readOnly = true) 
public class EmailSendAttServiceImpl implements EmailSendAttService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EmailSendAttMapper emailSendAttMapper;

	public EmailSendAttServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		emailSendAttMapper.deleteEmailSendAttById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> fileIds) {
	    if(fileIds != null && !fileIds.isEmpty()){
		for(String id : fileIds){
		    emailSendAttMapper.deleteEmailSendAttById(id);
		}
	    }
	}

	public int count(EmailSendAttQuery query) {
		return emailSendAttMapper.getEmailSendAttCount(query);
	}

	public List<EmailSendAtt> list(EmailSendAttQuery query) {
		List<EmailSendAtt> list = emailSendAttMapper.getEmailSendAtts(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getEmailSendAttCountByQueryCriteria(EmailSendAttQuery query) {
		return emailSendAttMapper.getEmailSendAttCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EmailSendAtt> getEmailSendAttsByQueryCriteria(int start, int pageSize,
			EmailSendAttQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EmailSendAtt> rows = sqlSessionTemplate.selectList(
				"getEmailSendAtts", query, rowBounds);
		return rows;
	}


	public EmailSendAtt getEmailSendAtt(String id) {
	        if(id == null){
		    return null;
		}
		EmailSendAtt emailSendAtt = emailSendAttMapper.getEmailSendAttById(id);
		return emailSendAtt;
	}

	@Transactional
	public void save(EmailSendAtt emailSendAtt) {
           if (StringUtils.isEmpty(emailSendAtt.getFileId())) {
	        emailSendAtt.setFileId(idGenerator.getNextId("EMAIL_SENDATT"));
		//emailSendAtt.setCreateDate(new Date());
		//emailSendAtt.setDeleteFlag(0);
		emailSendAttMapper.insertEmailSendAtt(emailSendAtt);
	       } else {
		emailSendAttMapper.updateEmailSendAtt(emailSendAtt);
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

	@javax.annotation.Resource(name = "com.glaf.maildata.mapper.EmailSendAttMapper")
	public void setEmailSendAttMapper(EmailSendAttMapper emailSendAttMapper) {
		this.emailSendAttMapper = emailSendAttMapper;
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
		public List<EmailSendAtt> getEmailSendAttsByMailId(String mailId) {
			return  emailSendAttMapper.getEmailSendAttsByMailId(mailId);
		}

}
