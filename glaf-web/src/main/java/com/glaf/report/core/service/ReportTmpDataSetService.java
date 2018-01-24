package com.glaf.report.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

 
@Transactional(readOnly = true)
public interface ReportTmpDataSetService {
	 
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
	 List<ReportTmpDataSet> list(ReportTmpDataSetQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getReportTmpDataSetCountByQueryCriteria(ReportTmpDataSetQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ReportTmpDataSet> getReportTmpDataSetsByQueryCriteria(int start, int pageSize,
			ReportTmpDataSetQuery query) ;
	 

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ReportTmpDataSet getReportTmpDataSet(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ReportTmpDataSet reportTmpDataSet);

}
