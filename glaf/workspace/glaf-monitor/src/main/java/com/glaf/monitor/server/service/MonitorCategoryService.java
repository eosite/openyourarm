package com.glaf.monitor.server.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;

 
@Transactional(readOnly = true)
public interface MonitorCategoryService {
	 
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
	 List<MonitorCategory> list(MonitorCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getMonitorCategoryCountByQueryCriteria(MonitorCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<MonitorCategory> getMonitorCategorysByQueryCriteria(int start, int pageSize,
			MonitorCategoryQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 MonitorCategory getMonitorCategory(Integer id);

        /**
	 * 修改分类树节点
	 * 
	 * @return
	 */
	@Transactional
	 void save(MonitorCategory monitorCategory);
	
	/**
	 * 增加分类树节点
	 * @param monitorCategory	
	 * @param parentIndexId	父节点id
	 * @param parentTreeId	父节点treeid
	 */
	@Transactional
	 void add(MonitorCategory monitorCategory,Integer parentIndexId, String parentTreeId);

}
