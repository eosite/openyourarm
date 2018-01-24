package com.glaf.dep.report.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.dep.report.domain.*;
import com.glaf.dep.report.query.*;

 
@Transactional(readOnly = true)
public interface DepReportTmpCategoryService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(Long id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<Long> ids);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<DepReportTmpCategory> list(DepReportTmpCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getDepReportTmpCategoryCountByQueryCriteria(DepReportTmpCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<DepReportTmpCategory> getDepReportTmpCategorysByQueryCriteria(int start, int pageSize,
			DepReportTmpCategoryQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 DepReportTmpCategory getDepReportTmpCategory(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(DepReportTmpCategory depReportTmpCategory);

}
