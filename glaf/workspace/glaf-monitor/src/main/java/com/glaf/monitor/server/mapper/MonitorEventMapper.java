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

@Component("com.glaf.monitor.server.mapper.MonitorEventMapper")
public interface MonitorEventMapper {

	void deleteMonitorEvents(MonitorEventQuery query);

	void deleteMonitorEventById(String id);

	MonitorEvent getMonitorEventById(String id);

	int getMonitorEventCount(MonitorEventQuery query);

	List<MonitorEvent> getMonitorEvents(MonitorEventQuery query);

	void insertMonitorEvent(MonitorEvent model);

	void updateMonitorEvent(MonitorEvent model);

}
