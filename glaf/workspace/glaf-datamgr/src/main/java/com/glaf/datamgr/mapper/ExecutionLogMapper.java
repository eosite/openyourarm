package com.glaf.datamgr.mapper;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface ExecutionLogMapper {

	void bulkInsertExecutionLog(List<ExecutionLog> list);

	void bulkInsertExecutionLog_oracle(List<ExecutionLog> list);

	void deleteExecutionLogById(Long id);
	
	void deleteExecutionLogs(ExecutionLogQuery query);
	
	void deleteOverdueExecutionLogs(Date dateBefore);

	ExecutionLog getExecutionLogById(Long id);

	int getExecutionLogCount(ExecutionLogQuery query);

	List<ExecutionLog> getExecutionLogs(ExecutionLogQuery query);

	void insertExecutionLog(ExecutionLog model);

	void updateExecutionLog(ExecutionLog model);

}
