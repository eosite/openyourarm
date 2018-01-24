package com.glaf.monitor.server.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;

 
@Transactional(readOnly = true)
public interface MonitorTerminalBusCategoryService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(String id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<String> terminalIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<MonitorTerminalBusCategory> list(MonitorTerminalBusCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getMonitorTerminalBusCategoryCountByQueryCriteria(MonitorTerminalBusCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<MonitorTerminalBusCategory> getMonitorTerminalBusCategorysByQueryCriteria(int start, int pageSize,
			MonitorTerminalBusCategoryQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 MonitorTerminalBusCategory getMonitorTerminalBusCategory(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(MonitorTerminalBusCategory monitorTerminalBusCategory);

    /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(String terminalId,String categoryIds);
}
