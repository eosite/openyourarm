package com.glaf.monitor.server.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;

 
@Transactional(readOnly = true)
public interface MonitorProcItemService {
	 
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
	 void deleteByIds(List<String> ids);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<MonitorProcItem> list(MonitorProcItemQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getMonitorProcItemCountByQueryCriteria(MonitorProcItemQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<MonitorProcItem> getMonitorProcItemsByQueryCriteria(int start, int pageSize,
			MonitorProcItemQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 MonitorProcItem getMonitorProcItem(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(MonitorProcItem monitorProcItem);

}
