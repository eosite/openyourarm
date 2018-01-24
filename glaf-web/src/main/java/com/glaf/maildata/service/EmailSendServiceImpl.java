package com.glaf.maildata.service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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

import sun.misc.BASE64Encoder;

@Service("com.glaf.maildata.service.emailSendService")
@Transactional(readOnly = true) 
public class EmailSendServiceImpl implements EmailSendService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EmailSendMapper emailSendMapper;

	public EmailSendServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		emailSendMapper.deleteEmailSendById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    emailSendMapper.deleteEmailSendById(id);
		}
	    }
	}

	public int count(EmailSendQuery query) {
		return emailSendMapper.getEmailSendCount(query);
	}

	public List<EmailSend> list(EmailSendQuery query) {
		List<EmailSend> list = emailSendMapper.getEmailSends(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getEmailSendCountByQueryCriteria(EmailSendQuery query) {
		return emailSendMapper.getEmailSendCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EmailSend> getEmailSendsByQueryCriteria(int start, int pageSize,
			EmailSendQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EmailSend> rows = sqlSessionTemplate.selectList(
				"getEmailSends", query, rowBounds);
		return rows;
	}


	public EmailSend getEmailSend(String id) {
	        if(id == null){
		    return null;
		}
		EmailSend emailSend = emailSendMapper.getEmailSendById(id);
		return emailSend;
	}

	@Transactional
	public void save(EmailSend emailSend) {
           if (StringUtils.isEmpty(emailSend.getId())) {
	        emailSend.setId(idGenerator.getNextId("EMAIL_SEND"));
		//emailSend.setCreateDate(new Date());
		//emailSend.setDeleteFlag(0);
		emailSendMapper.insertEmailSend(emailSend);
	       } else {
		emailSendMapper.updateEmailSend(emailSend);
	      }
	}
    
	public List<EmailSend> getNeedSendEmails(){
		return  emailSendMapper.getNeedSendEmails();
	}
	
	public void updateEmailOperationStatus(String emailId,int intOperat){
		emailSendMapper.updateEmailOperationStatus(emailId, intOperat);
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
	/**
	 * 生成发送数据包
	 * 
	 * @param xmlDesc
	 * @param emailSendAttrs
	 * @return
	 * @throws Exception
	 */
	public byte[] generateSendDataPackage(String subject,String xmlDesc, List<EmailSendAtt> emailSendAttrs) throws Exception {
		byte[] dataPackage = null;
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(new String(xmlDesc.getBytes("UTF-8")));
			Element root = doc.getRootElement();
			Element subjectEle = root.addElement("主题");
			subjectEle.addText(subject);
			Element attrs = root.addElement("附件列表");
			byte[] attrContent = null;
			String dataPackageStr = null;
			for (EmailSendAtt emailSendAtt : emailSendAttrs) {
				Element attr = attrs.addElement("附件");
				attrContent = emailSendAtt.getFileContent();
				// 附件内容通过base64
				dataPackageStr = new BASE64Encoder().encode(attrContent);
				attr.addText(dataPackageStr);
			}
			doc.setXMLEncoding("GB2312");
			dataPackage = doc.asXML().getBytes();
		} catch (UnsupportedEncodingException | DocumentException e) {
			// TODO Auto-generated catch block
			logger.error("封装数据包出错：" + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		return dataPackage;
	}
	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.maildata.mapper.EmailSendMapper")
	public void setEmailSendMapper(EmailSendMapper emailSendMapper) {
		this.emailSendMapper = emailSendMapper;
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
