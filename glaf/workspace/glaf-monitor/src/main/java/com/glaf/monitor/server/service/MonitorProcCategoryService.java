package com.glaf.monitor.server.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;

 
@Transactional(readOnly = true)
public interface MonitorProcCategoryService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(Integer id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<Integer> ids);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<MonitorProcCategory> list(MonitorProcCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getMonitorProcCategoryCountByQueryCriteria(MonitorProcCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<MonitorProcCategory> getMonitorProcCategorysByQueryCriteria(int start, int pageSize,
			MonitorProcCategoryQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 MonitorProcCategory getMonitorProcCategory(Integer id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(MonitorProcCategory monitorProcCategory);
	
	/**
	 * 增加分类树节点
	 * @param monitorCategory	
	 * @param parentIndexId	父节点id
	 * @param parentTreeId	父节点treeid
	 */
	@Transactional
	 void add(MonitorProcCategory monitorProcCategory,Integer parentIndexId, String parentTreeId);

}
