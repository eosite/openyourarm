package com.glaf.base.usertask.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.base.usertask.domain.*;
import com.glaf.base.usertask.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.base.usertask.mapper.UserTaskTotalMapper")
public interface UserTaskTotalMapper {

	void bulkInsertUserTaskTotal(List<UserTaskTotal> list);

	void bulkInsertUserTaskTotal_oracle(List<UserTaskTotal> list);

	void deleteUserTaskTotalById(String id);

	void deleteByDatabaseId(long databaseId);

	UserTaskTotal getUserTaskTotalById(String id);

	int getUserTaskTotalCount(UserTaskTotalQuery query);

	List<UserTaskTotal> getUserTaskTotals(UserTaskTotalQuery query);

	void insertUserTaskTotal(UserTaskTotal model);

	void updateUserTaskTotal(UserTaskTotal model);

}
