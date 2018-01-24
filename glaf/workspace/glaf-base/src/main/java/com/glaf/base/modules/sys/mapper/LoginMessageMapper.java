package com.glaf.base.modules.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.base.modules.sys.model.LoginMessage;
import com.glaf.base.modules.sys.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface LoginMessageMapper {

	void deleteLoginMessageByToken(String token);
	
	LoginMessage getLoginMessageByToken(String token);
	
	int getLoginCountByUserId(LoginMessageQuery query);

	int getLoginMessageCount(LoginMessageQuery query);

	List<LoginMessage> getLoginMessages(LoginMessageQuery query);

	void insertLoginMessage(LoginMessage model);

}
