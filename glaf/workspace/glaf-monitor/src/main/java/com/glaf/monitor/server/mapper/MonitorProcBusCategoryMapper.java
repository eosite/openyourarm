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

@Component("com.glaf.monitor.server.mapper.MonitorProcBusCategoryMapper")
public interface MonitorProcBusCategoryMapper {

	void deleteMonitorProcBusCategorys(MonitorProcBusCategoryQuery query);

	void deleteMonitorProcBusCategoryById(String id);
	
	MonitorProcBusCategory getMonitorProcBusCategoryById(String id);

	int getMonitorProcBusCategoryCount(MonitorProcBusCategoryQuery query);

	List<MonitorProcBusCategory> getMonitorProcBusCategorys(MonitorProcBusCategoryQuery query);

	void insertMonitorProcBusCategory(MonitorProcBusCategory model);

	void updateMonitorProcBusCategory(MonitorProcBusCategory model);
	
	void blukInsertMonitorProcBusCategory(@Param("id") String id,@Param("categoryIds") List<Integer> categoryIds);

}
