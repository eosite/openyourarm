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

@Component("com.glaf.monitor.server.mapper.MonitorTerminalItemMapper")
public interface MonitorTerminalItemMapper {

	void deleteMonitorTerminalItems(MonitorTerminalItemQuery query);

	void deleteMonitorTerminalItemById(String id);

	MonitorTerminalItem getMonitorTerminalItemById(String id);

	int getMonitorTerminalItemCount(MonitorTerminalItemQuery query);

	List<MonitorTerminalItem> getMonitorTerminalItems(MonitorTerminalItemQuery query);

	void insertMonitorTerminalItem(MonitorTerminalItem model);

	void updateMonitorTerminalItem(MonitorTerminalItem model);

}
