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
public interface AuthorityUserMapper {

	void bulkInsertAuthorityUser(List<AuthorityUser> list);

	void bulkInsertAuthorityUser_oracle(List<AuthorityUser> list);

	void deleteAuthorityUserByActorId(String actorId);

	void deleteAuthorityUserById(Long id);

	void deleteAuthorityUserByRoleId(long roleId);

	AuthorityUser getAuthorityUserById(Long id);

	int getAuthorityUserCount(AuthorityUserQuery query);

	List<AuthorityUser> getAuthorityUsers(AuthorityUserQuery query);

	void insertAuthorityUser(AuthorityUser model);

}
