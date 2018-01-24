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

@Component("com.glaf.monitor.server.mapper.MonitorCategoryMapper")
public interface MonitorCategoryMapper {

	void deleteMonitorCategorys(MonitorCategoryQuery query);

	void deleteMonitorCategoryById(Integer id);

	MonitorCategory getMonitorCategoryById(Integer id);

	int getMonitorCategoryCount(MonitorCategoryQuery query);

	List<MonitorCategory> getMonitorCategorys(MonitorCategoryQuery query);

	void insertMonitorCategory(MonitorCategory model);

	void updateMonitorCategory(MonitorCategory model);

}
