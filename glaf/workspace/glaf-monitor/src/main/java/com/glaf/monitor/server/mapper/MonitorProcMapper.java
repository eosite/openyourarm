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

@Component("com.glaf.monitor.server.mapper.MonitorProcMapper")
public interface MonitorProcMapper {

	void deleteMonitorProcs(MonitorProcQuery query);

	void deleteMonitorProcById(String id);

	MonitorProc getMonitorProcById(String id);

	int getMonitorProcCount(MonitorProcQuery query);

	List<MonitorProc> getMonitorProcs(MonitorProcQuery query);

	void insertMonitorProc(MonitorProc model);

	void updateMonitorProc(MonitorProc model);

}
