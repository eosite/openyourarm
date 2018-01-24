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
public interface ParameterLogMapper {

	void bulkInsertParameterLog(List<ParameterLog> list);

	void bulkInsertParameterLog_oracle(List<ParameterLog> list);

	void deleteParameterLogById(Long id);
	
	void deleteParameterLogs(ParameterLogQuery query);
	
	void deleteOverdueParameterLogs(Date dateBefore);

	ParameterLog getParameterLogById(Long id);

	int getParameterLogCount(ParameterLogQuery query);

	List<ParameterLog> getParameterLogs(ParameterLogQuery query);

	void insertParameterLog(ParameterLog model);

	void updateParameterLog(ParameterLog model);

}
