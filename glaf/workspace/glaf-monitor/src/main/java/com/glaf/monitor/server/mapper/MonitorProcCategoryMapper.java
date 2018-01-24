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

@Component("com.glaf.monitor.server.mapper.MonitorProcCategoryMapper")
public interface MonitorProcCategoryMapper {

	void deleteMonitorProcCategorys(MonitorProcCategoryQuery query);

	void deleteMonitorProcCategoryById(Integer id);

	MonitorProcCategory getMonitorProcCategoryById(Integer id);

	int getMonitorProcCategoryCount(MonitorProcCategoryQuery query);

	List<MonitorProcCategory> getMonitorProcCategorys(MonitorProcCategoryQuery query);

	void insertMonitorProcCategory(MonitorProcCategory model);

	void updateMonitorProcCategory(MonitorProcCategory model);

}
