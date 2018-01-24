package com.glaf.report.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

 
@Transactional(readOnly = true)
public interface ReportTmpMappingService {
	 
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
	 List<ReportTmpMapping> list(ReportTmpMappingQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getReportTmpMappingCountByQueryCriteria(ReportTmpMappingQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ReportTmpMapping> getReportTmpMappingsByQueryCriteria(int start, int pageSize,
			ReportTmpMappingQuery query) ;
	 /**
	  * 获取报表数据
	  * @param tmpMappingId
	  * @param paramJson
	  * @return
	  */
	 JSONObject getReportData(String tmpMappingId, JSONObject paramJson);
	 /**
	  * 获取报表模板数据集映射以及数据集列映射
	  * @param tmpMappingId
	  * @return
	  */
	 ReportTmpMappingEntity getReportTmpDatasetMapping(String tmpMappingId);
	 /**
	  * 获取报表数据集数据
	  * @param tmpMappingId
	  * @param paramJson
	  * @return
	  */
	 JSONObject getReportDatasetData(String tmpMappingId, JSONObject paramJson);
	 
	 /**
	  * 获取转换后的报表数据集数据
	  * @param reportTmpMappingEntity
	  * @param data
	  * @return
	  */
	 JSONObject transReportDatasetData(ReportTmpMappingEntity reportTmpMappingEntity, JSONObject data);

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ReportTmpMapping getReportTmpMapping(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ReportTmpMapping reportTmpMapping);

}
