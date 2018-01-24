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

@Component("com.glaf.monitor.server.mapper.MonitorProcUserMapper")
public interface MonitorProcUserMapper {

	void deleteMonitorProcUsers(MonitorProcUserQuery query);

	void deleteMonitorProcUserById(String id);

	MonitorProcUser getMonitorProcUserById(String id);

	int getMonitorProcUserCount(MonitorProcUserQuery query);

	List<MonitorProcUser> getMonitorProcUsers(MonitorProcUserQuery query);

	void insertMonitorProcUser(MonitorProcUser model);

	void updateMonitorProcUser(MonitorProcUser model);

}
