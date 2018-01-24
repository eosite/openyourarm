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

@Component("com.glaf.monitor.server.mapper.MonitorTerminalUserMapper")
public interface MonitorTerminalUserMapper {

	void deleteMonitorTerminalUsers(MonitorTerminalUserQuery query);

	void deleteMonitorTerminalUserById(String id);

	MonitorTerminalUser getMonitorTerminalUserById(String id);

	int getMonitorTerminalUserCount(MonitorTerminalUserQuery query);

	List<MonitorTerminalUser> getMonitorTerminalUsers(MonitorTerminalUserQuery query);

	void insertMonitorTerminalUser(MonitorTerminalUser model);

	void updateMonitorTerminalUser(MonitorTerminalUser model);

}
