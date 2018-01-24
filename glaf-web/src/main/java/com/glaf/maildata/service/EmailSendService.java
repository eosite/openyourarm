package com.glaf.maildata.service;

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

 
@Transactional(readOnly = true)
public interface EmailSendService {
	 
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
	 void deleteByIds(List<String> ids);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<EmailSend> list(EmailSendQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getEmailSendCountByQueryCriteria(EmailSendQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<EmailSend> getEmailSendsByQueryCriteria(int start, int pageSize,
			EmailSendQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 EmailSend getEmailSend(String id);
	 
	 /**
	  * 获取待发送的邮件列表
	  * @return
	  */
	 List<EmailSend> getNeedSendEmails();

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(EmailSend emailSend);
	/**
	 * 更新邮件发送状态
	 * @param emailId
	 * @param intOperat
	 */
	@Transactional
	void updateEmailOperationStatus(String emailId,int intOperat);
	/**
	 * 生成MQ数据包
	 * @param xmlDesc
	 * @param emailSendAttrs
	 * @return
	 * @throws Exception
	 */
	public byte[] generateSendDataPackage(String subject,String xmlDesc, List<EmailSendAtt> emailSendAttrs) throws Exception ;

}
