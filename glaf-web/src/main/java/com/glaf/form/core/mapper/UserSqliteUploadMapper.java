package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.UserSqliteUpload;
import com.glaf.form.core.query.UserSqliteUploadQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.UserSqliteUploadMapper")
public interface UserSqliteUploadMapper {

	void deleteUserSqliteUploads(UserSqliteUploadQuery query);

	void deleteUserSqliteUploadById(String id);

	UserSqliteUpload getUserSqliteUploadById(String id);

	int getUserSqliteUploadCount(UserSqliteUploadQuery query);

	List<UserSqliteUpload> getUserSqliteUploads(UserSqliteUploadQuery query);

	void insertUserSqliteUpload(UserSqliteUpload model);

	void updateUserSqliteUpload(UserSqliteUpload model);

}
