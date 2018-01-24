package com.glaf.base.modules.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.base.modules.sys.model.LoginUser;
import com.glaf.base.modules.sys.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface LoginUserMapper {

	void bulkInsertLoginUser(List<LoginUser> list);

	void bulkInsertLoginUser_oracle(List<LoginUser> list);

	void deleteLoginUsers(LoginUserQuery query);

	void deleteLoginUserById(String id);

	LoginUser getLoginUserById(String id);

	int getLoginUserCount(LoginUserQuery query);

	List<LoginUser> getLoginUsers(LoginUserQuery query);

	void insertLoginUser(LoginUser model);

	void updateLoginUser(LoginUser model);

}
