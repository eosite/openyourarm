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

@Component("com.glaf.monitor.server.mapper.MonitorLogMapper")
public interface MonitorLogMapper {

	void deleteMonitorLogs(MonitorLogQuery query);

	void deleteMonitorLogById(String id);

	MonitorLog getMonitorLogById(String id);

	int getMonitorLogCount(MonitorLogQuery query);

	List<MonitorLog> getMonitorLogs(MonitorLogQuery query);

	void insertMonitorLog(MonitorLog model);

	void updateMonitorLog(MonitorLog model);

}
