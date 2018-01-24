package com.glaf.monitor.server.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.monitor.server.mapper.MonitorTerminalMapper")
public interface MonitorTerminalMapper {

	void deleteMonitorTerminals(MonitorTerminalQuery query);

	void deleteMonitorTerminalById(String id);

	MonitorTerminal getMonitorTerminalById(String id);

	int getMonitorTerminalCount(MonitorTerminalQuery query);

	List<MonitorTerminal> getMonitorTerminals(MonitorTerminalQuery query);

	void insertMonitorTerminal(MonitorTerminal model);

	void updateMonitorTerminal(MonitorTerminal model);

}
