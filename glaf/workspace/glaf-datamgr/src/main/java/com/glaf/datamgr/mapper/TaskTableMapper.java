package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.datamgr.mapper.TaskTableMapper")
public interface TaskTableMapper {

	void bulkInsertTaskTable(List<TaskTable> list);

	void bulkInsertTaskTable_oracle(List<TaskTable> list);

	void deleteTaskTables(TaskTableQuery query);

	void deleteTaskTableById(Long id);

	TaskTable getTaskTableById(Long id);

	int getTaskTableCount(TaskTableQuery query);

	List<TaskTable> getTaskTables(TaskTableQuery query);

	void insertTaskTable(TaskTable model);

	void updateTaskTable(TaskTable model);

}
