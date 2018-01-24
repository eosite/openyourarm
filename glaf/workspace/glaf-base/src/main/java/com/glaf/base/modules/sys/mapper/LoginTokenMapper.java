package com.glaf.base.modules.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface LoginTokenMapper {

	void bulkInsertLoginToken(List<LoginToken> list);

	void bulkInsertLoginToken_oracle(List<LoginToken> list);
	
	void deleteLoginTokenById(String id);

	void deleteLoginTokenByUserId(String userId);

	void deleteLoginTokens(LoginTokenQuery query);

	LoginToken getLoginTokenById(String id);
	
	LoginToken getLoginTokenBySignature(String signature);
	
	LoginToken getLoginTokenByToken(String token);

	int getLoginTokenCount(LoginTokenQuery query);

	List<LoginToken> getLoginTokens(LoginTokenQuery query);

	void insertLoginToken(LoginToken model);

	void updateLoginToken(LoginToken model);
}
