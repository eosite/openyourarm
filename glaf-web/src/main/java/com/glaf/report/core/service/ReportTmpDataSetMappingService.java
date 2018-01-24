package com.glaf.report.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

@Transactional(readOnly = true)
public interface ReportTmpDataSetMappingService {

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
	List<ReportTmpDataSetMapping> list(ReportTmpDataSetMappingQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getReportTmpDataSetMappingCountByQueryCriteria(ReportTmpDataSetMappingQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ReportTmpDataSetMapping> getReportTmpDataSetMappingsByQueryCriteria(int start, int pageSize,
			ReportTmpDataSetMappingQuery query);

	Map<String, String> getReportTmpDataSetMappingMap(String tmpMappingId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	ReportTmpDataSetMapping getReportTmpDataSetMapping(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ReportTmpDataSetMapping reportTmpDataSetMapping);

	/**
	 * 保存多条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(List<ReportTmpDataSetMapping> reportTmpDataSetMappings);

	@Transactional
	void deleteByParentId(String id);

}
