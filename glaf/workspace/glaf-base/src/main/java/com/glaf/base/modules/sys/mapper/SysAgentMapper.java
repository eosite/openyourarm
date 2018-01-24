package com.glaf.base.modules.sys.mapper;

import java.util.List;
import org.springframework.stereotype.Component;

import com.glaf.base.modules.sys.model.SysAgent;
import com.glaf.base.modules.sys.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface SysAgentMapper {

	void bulkInsertSysAgent(List<SysAgent> list);

	void bulkInsertSysAgent_oracle(List<SysAgent> list);

	void deleteSysAgents(SysAgentQuery query);

	void deleteSysAgentById(String id);

	SysAgent getSysAgentById(String id);

	int getSysAgentCount(SysAgentQuery query);

	List<SysAgent> getSysAgents(SysAgentQuery query);

	void insertSysAgent(SysAgent model);

	void updateSysAgent(SysAgent model);

}
