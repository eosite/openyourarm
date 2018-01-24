package com.glaf.maildata.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

 
@Transactional(readOnly = true)
public interface EmailRecAttService {
	 
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
	 List<EmailRecAtt> list(EmailRecAttQuery query);
    /**
     * 获取邮件附件
     * @param mailId
     * @return
     */
	 List<EmailRecAtt> getEmailRecAttsByMailId(String mailId);
         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getEmailRecAttCountByQueryCriteria(EmailRecAttQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<EmailRecAtt> getEmailRecAttsByQueryCriteria(int start, int pageSize,
			EmailRecAttQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 EmailRecAtt getEmailRecAtt(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(EmailRecAtt emailRecAtt);

}
