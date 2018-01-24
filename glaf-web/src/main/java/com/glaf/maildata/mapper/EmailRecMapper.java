package com.glaf.maildata.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.glaf.maildata.domain.*;
import com.glaf.maildata.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.maildata.mapper.EmailRecMapper")
public interface EmailRecMapper {

	void deleteEmailRecs(EmailRecQuery query);

	void deleteEmailRecById(String id);

	EmailRec getEmailRecById(String id);

	int getEmailRecCount(EmailRecQuery query);

	List<EmailRec> getEmailRecs(EmailRecQuery query);

	void insertEmailRec(EmailRec model);

	void updateEmailRec(EmailRec model);

}
