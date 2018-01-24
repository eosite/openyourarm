package com.glaf.form.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.UserSqliteMapper")
public interface UserSqliteMapper {

	void deleteUserSqlites(UserSqliteQuery query);

	void deleteUserSqliteById(String id);

	UserSqlite getUserSqliteById(String id);

	int getUserSqliteCount(UserSqliteQuery query);

	List<UserSqlite> getUserSqlites(UserSqliteQuery query);

	void insertUserSqlite(UserSqlite model);

	void updateUserSqlite(UserSqlite model);

}
