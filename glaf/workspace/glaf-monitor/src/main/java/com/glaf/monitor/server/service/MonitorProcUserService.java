package com.glaf.monitor.server.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;

 
@Transactional(readOnly = true)
public interface MonitorProcUserService {
	 
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
	 List<MonitorProcUser> list(MonitorProcUserQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getMonitorProcUserCountByQueryCriteria(MonitorProcUserQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<MonitorProcUser> getMonitorProcUsersByQueryCriteria(int start, int pageSize,
			MonitorProcUserQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 MonitorProcUser getMonitorProcUser(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(MonitorProcUser monitorProcUser);

}
