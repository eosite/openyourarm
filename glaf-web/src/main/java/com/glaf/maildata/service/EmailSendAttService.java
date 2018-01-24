package com.glaf.maildata.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

 
@Transactional(readOnly = true)
public interface EmailSendAttService {
	 
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
	 void deleteByIds(List<String> fileIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<EmailSendAtt> list(EmailSendAttQuery query);

	 /**
	  * 获取邮件附件
	  * @param mailId
	  * @return
	  */
	 List<EmailSendAtt> getEmailSendAttsByMailId(String mailId);
         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getEmailSendAttCountByQueryCriteria(EmailSendAttQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<EmailSendAtt> getEmailSendAttsByQueryCriteria(int start, int pageSize,
			EmailSendAttQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 EmailSendAtt getEmailSendAtt(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(EmailSendAtt emailSendAtt);

}
