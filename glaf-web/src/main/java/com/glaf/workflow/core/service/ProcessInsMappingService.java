
package com.glaf.workflow.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.workflow.core.domain.ProcessInsMapping;
import com.glaf.workflow.core.domain.ProcessMainTableName;
import com.glaf.workflow.core.query.ProcessInsMappingQuery;

 
@Transactional(readOnly = true)
public interface ProcessInsMappingService {
	 
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
	 List<ProcessInsMapping> list(ProcessInsMappingQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getProcessInsMappingCountByQueryCriteria(ProcessInsMappingQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ProcessInsMapping> getProcessInsMappingsByQueryCriteria(int start, int pageSize,
			ProcessInsMappingQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ProcessInsMapping getProcessInsMapping(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ProcessInsMapping processInsMapping);
	
     /**
      * 获取待启动流程列表
      * @return
      */
	 List<ProcessInsMapping> getNeedStartImpProcess();
	 /**
	  * 获取流程主数据表
	  * @param processId
	  * @return
	  */
	 List<ProcessMainTableName> getProcessMainTableNames(String processId);
	 /**
	  * 获取流程对应表单主数据表主键值
	  * @param sysId
	  * @param processId
	  * @return
	  */
	 Map<String,String> getTablePrimaryKeyValBySysId(String sysId,String processId);
	 /**
	  * 获取报表流程对应表单主数据表主键值
	  * @param sysId
	  * @param processId
	  * @return
	  */
	 Map<String,String> getReportTablePrimaryKeyValBySysId(String sysId,String processId);
     /**
      * 获取WPF传递流程表单主数据表主键值
      * @param sysId
      * @param processId
      * @return
      */
	 Map<String,String> getWpfTablePrimaryKeyValBySysId(String sysId,String processId);
	 /**
	  * 修改映射状态
	  */
	 void updateMapping(ProcessInsMapping processInsMapping);
}
