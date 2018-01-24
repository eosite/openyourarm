package com.glaf.maildata.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.maildata.mapper.EmailSendAttMapper")
public interface EmailSendAttMapper {

	void deleteEmailSendAtts(EmailSendAttQuery query);

	void deleteEmailSendAttById(String id);

	EmailSendAtt getEmailSendAttById(String id);

	int getEmailSendAttCount(EmailSendAttQuery query);

	List<EmailSendAtt> getEmailSendAtts(EmailSendAttQuery query);

	void insertEmailSendAtt(EmailSendAtt model);

	void updateEmailSendAtt(EmailSendAtt model);
	
	List<EmailSendAtt> getEmailSendAttsByMailId(String mailId);

}
