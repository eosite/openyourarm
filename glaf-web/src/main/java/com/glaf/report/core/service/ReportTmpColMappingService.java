package com.glaf.report.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

@Transactional(readOnly = true)
public interface ReportTmpColMappingService {

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
	List<ReportTmpColMapping> list(ReportTmpColMappingQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getReportTmpColMappingCountByQueryCriteria(ReportTmpColMappingQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ReportTmpColMapping> getReportTmpColMappingsByQueryCriteria(int start, int pageSize,
			ReportTmpColMappingQuery query);

	/**
	 * 获取报表模板数据集列映射
	 * 
	 * @param tmpMappingId
	 * @return
	 */
	Map<String, Map<String, String>> getReportTmpColMappingMap(String tmpMappingId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	ReportTmpColMapping getReportTmpColMapping(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ReportTmpColMapping reportTmpColMapping);

	@Transactional
	void deleteByParentId(String id);

	@Transactional
	void deleteIfDataSetNotExists();

}
