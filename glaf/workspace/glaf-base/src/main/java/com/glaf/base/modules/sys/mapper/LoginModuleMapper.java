package com.glaf.base.modules.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface LoginModuleMapper {

	void deleteLoginModuleById(String id);

	LoginModule getLoginModuleByAppId(String appId);

	LoginModule getLoginModuleById(String id);
	
	LoginModule getLoginModuleBySysCode(String sysCode);

	LoginModule getLoginModuleByToken(String token);

	int getLoginModuleCount(LoginModuleQuery query);

	List<LoginModule> getLoginModules(LoginModuleQuery query);

	String getPrivateKey(String id);

	void insertLoginModule(LoginModule model);

	void resetLoginAppSecret(LoginModule model);

	void resetLoginAppToken(LoginModule model);

	void resetLoginPublicKey(LoginModule model);

	void updateLoginModule(LoginModule model);

}
