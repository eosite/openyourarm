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

@Component("com.glaf.maildata.mapper.EmailRecAttMapper")
public interface EmailRecAttMapper {

	void deleteEmailRecAtts(EmailRecAttQuery query);

	void deleteEmailRecAttById(String id);

	EmailRecAtt getEmailRecAttById(String id);

	int getEmailRecAttCount(EmailRecAttQuery query);

	List<EmailRecAtt> getEmailRecAtts(EmailRecAttQuery query);
	
	List<EmailRecAtt> getEmailRecAttsByMailId (String mailId);

	void insertEmailRecAtt(EmailRecAtt model);

	void updateEmailRecAtt(EmailRecAtt model);

}
