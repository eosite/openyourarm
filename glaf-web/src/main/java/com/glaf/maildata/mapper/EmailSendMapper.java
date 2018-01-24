package com.glaf.maildata.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.maildata.mapper.EmailSendMapper")
public interface EmailSendMapper {

	void deleteEmailSends(EmailSendQuery query);

	void deleteEmailSendById(String id);

	EmailSend getEmailSendById(String id);

	int getEmailSendCount(EmailSendQuery query);

	List<EmailSend> getEmailSends(EmailSendQuery query);

	void insertEmailSend(EmailSend model);

	void updateEmailSend(EmailSend model);
	
	List<EmailSend> getNeedSendEmails();
	
	void updateEmailOperationStatus(@Param("id")String emailId,@Param("intOperat")int intOperat);

}
