package com.glaf.monitor.server.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.monitor.server.mapper.MonitorTerminalBusCategoryMapper")
public interface MonitorTerminalBusCategoryMapper {

	void deleteMonitorTerminalBusCategorys(MonitorTerminalBusCategoryQuery query);

	void deleteMonitorTerminalBusCategoryById(String id);
	
	void deleteMonitorTerminalBusCategoryByTerminalId(String terminalId);

	MonitorTerminalBusCategory getMonitorTerminalBusCategoryById(String id);

	int getMonitorTerminalBusCategoryCount(MonitorTerminalBusCategoryQuery query);

	List<MonitorTerminalBusCategory> getMonitorTerminalBusCategorys(MonitorTerminalBusCategoryQuery query);

	void insertMonitorTerminalBusCategory(MonitorTerminalBusCategory model);
	
	void blukInsertMonitorTerminalBusCategory(@Param("terminalId") String terminalId,@Param("categoryIds") List<Integer> categoryIds);

	void updateMonitorTerminalBusCategory(MonitorTerminalBusCategory model);

}
