package com.glaf.report.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

 
@Transactional(readOnly = true)
public interface ReportTmpCategoryService {
	 
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
	 List<ReportTmpCategory> list(ReportTmpCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getReportTmpCategoryCountByQueryCriteria(ReportTmpCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ReportTmpCategory> getReportTmpCategorysByQueryCriteria(int start, int pageSize,
			ReportTmpCategoryQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ReportTmpCategory getReportTmpCategory(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ReportTmpCategory reportTmpCategory);

}
