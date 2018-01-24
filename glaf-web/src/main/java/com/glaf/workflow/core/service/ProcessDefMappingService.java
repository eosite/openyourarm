package com.glaf.workflow.core.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.workflow.core.domain.ProcessDefMapping;
import com.glaf.workflow.core.query.ProcessDefMappingQuery;

 
@Transactional(readOnly = true)
public interface ProcessDefMappingService {
	 
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
	 void deleteByIds(List<String> iDs);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ProcessDefMapping> list(ProcessDefMappingQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getProcessDefMappingCountByQueryCriteria(ProcessDefMappingQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ProcessDefMapping> getProcessDefMappingsByQueryCriteria(int start, int pageSize,
			ProcessDefMappingQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ProcessDefMapping getProcessDefMapping(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ProcessDefMapping processDefMapping);
	
	ProcessDefMapping getProcessDefMappingByImpProcessDefAndImpSysId(@Param("impProcessDef")String impProcessDef,@Param("impSysId")String impSysId,@Param("sysId")String sysId);

}
