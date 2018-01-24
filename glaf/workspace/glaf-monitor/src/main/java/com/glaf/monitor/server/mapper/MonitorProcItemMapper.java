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

@Component("com.glaf.monitor.server.mapper.MonitorProcItemMapper")
public interface MonitorProcItemMapper {

	void deleteMonitorProcItems(MonitorProcItemQuery query);

	void deleteMonitorProcItemById(String id);

	MonitorProcItem getMonitorProcItemById(String id);

	int getMonitorProcItemCount(MonitorProcItemQuery query);

	List<MonitorProcItem> getMonitorProcItems(MonitorProcItemQuery query);

	void insertMonitorProcItem(MonitorProcItem model);

	void updateMonitorProcItem(MonitorProcItem model);

}
