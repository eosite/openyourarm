package com.glaf.etl.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

@Transactional(readOnly = true)
public interface EtlTransferTaskConfigService {

	
	
	
	
	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<EtlTransferTaskConfig> getEtlTransferTasks(int start, int pageSize,EtlTransferTaskConfigQuery query);
	
	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getEtlTransferTaskCount();

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(EtlTransferTask etlTransferTask,EtlTransferTaskSrc etlTransferTaskSrc,EtlTransferTaskTarget etlTransferTaskTarget);
	
	
	
	
	
	
	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> id_s);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<EtlTransferTask> list(EtlTransferTaskQuery query);

	/**
	 * 获取任务源和目标信息
	 * 
	 * @param query
	 * @return
	 */
	Map<String, Map<String, String>> getTaskSrcTargetMap(EtlTransferTaskQuery query);

	



	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	EtlTransferTask getEtlTransferTask(String id);

	/**
	 * 更新任务启动状态
	 * @param etlTransferTask
	 */
	@Transactional
	void updateTransferTaskStartStatus(EtlTransferTask etlTransferTask);
	/**
	 * 更新任务执行状态
	 * 
	 * @param etlTransferTask
	 */
	@Transactional
	void updateTransferTaskStatus(EtlTransferTask etlTransferTask);


}
