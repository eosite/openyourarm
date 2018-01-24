package com.glaf.maildata.service;

import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.dom4j.DocumentException;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.domain.Database;
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

@Transactional(readOnly = true)
public interface EmailRecService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> iDs);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<EmailRec> list(EmailRecQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getEmailRecCountByQueryCriteria(EmailRecQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<EmailRec> getEmailRecsByQueryCriteria(int start, int pageSize, EmailRecQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	EmailRec getEmailRec(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(EmailRec emailRec);

	void readMail();

	/**
	 * 处理复杂邮件
	 * 
	 * @param multipart
	 * @throws MessagingException
	 * @throws IOException
	 */
	@Transactional
	void parseMultipart(Multipart multipart) throws MessagingException, IOException;
	/**
	 * 保存MQ数据包xm数据到物理表 
	 * @param database
	 * @param xmlData
	 * @throws DocumentException
	 * @throws IOException
	 */
	@Transactional
	void saveDataToDb(byte[] xmlData) throws DocumentException, IOException ;

}
