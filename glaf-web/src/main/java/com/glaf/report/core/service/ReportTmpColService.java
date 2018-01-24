package com.glaf.report.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

 
@Transactional(readOnly = true)
public interface ReportTmpColService {
	 
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
	 List<ReportTmpCol> list(ReportTmpColQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getReportTmpColCountByQueryCriteria(ReportTmpColQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ReportTmpCol> getReportTmpColsByQueryCriteria(int start, int pageSize,
			ReportTmpColQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ReportTmpCol getReportTmpCol(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ReportTmpCol reportTmpCol);

}
