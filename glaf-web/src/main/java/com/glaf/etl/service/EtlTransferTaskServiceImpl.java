package com.glaf.etl.service;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.etl.mapper.*;
import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

@Service("com.glaf.etl.service.etlTransferTaskService")
@Transactional(readOnly = true)
public class EtlTransferTaskServiceImpl implements EtlTransferTaskService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EtlTransferTaskMapper etlTransferTaskMapper;

	public EtlTransferTaskServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			etlTransferTaskMapper.deleteEtlTransferTaskById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> id_s) {
		if (id_s != null && !id_s.isEmpty()) {
			for (String id : id_s) {
				etlTransferTaskMapper.deleteEtlTransferTaskById(id);
			}
		}
	}

	public int count(EtlTransferTaskQuery query) {
		return etlTransferTaskMapper.getEtlTransferTaskCount(query);
	}

	public List<EtlTransferTask> list(EtlTransferTaskQuery query) {
		List<EtlTransferTask> list = etlTransferTaskMapper.getEtlTransferTasks(query);
		return list;
	}
	/**
	 * 获取任务源和目标信息关键字值集合
	 */
	public Map<String,Map<String,String>> getTaskSrcTargetMap(EtlTransferTaskQuery query){
		Map<String,Map<String,String>> taskSrcTargetMap=new HashMap<String,Map<String,String>>();
		List<TaskSrcTarget> taskSrcTargetList=etlTransferTaskMapper.getTaskSrcTargetList(query);
		String taskId=null;
		String taskSrcId=null;
		String taskTargetId=null;
		String taskSrcIdTmp=null;
		Map<String,String> targetSrcMap=null;
		for(TaskSrcTarget taskSrcTarget:taskSrcTargetList){
			taskId=taskSrcTarget.getTaskId();
			taskSrcId=taskSrcTarget.getSrcId();
			taskTargetId=taskSrcTarget.getTargetId();
			if(taskSrcTargetMap.containsKey(taskId)){
				targetSrcMap=taskSrcTargetMap.get(taskId);
				if(targetSrcMap.containsKey(taskTargetId)){
					taskSrcIdTmp=targetSrcMap.get(taskTargetId);
					taskSrcIdTmp=taskSrcIdTmp+","+taskSrcId;
				}else{
					taskSrcIdTmp=taskSrcId;
				}
				targetSrcMap.put(taskTargetId, taskSrcIdTmp);
			}else{
				targetSrcMap=new HashMap<String,String>();
				targetSrcMap.put(taskTargetId, taskSrcId);
			}
			taskSrcTargetMap.put(taskId, targetSrcMap);
		}
		return taskSrcTargetMap;
	}
	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEtlTransferTaskCountByQueryCriteria(EtlTransferTaskQuery query) {
		return etlTransferTaskMapper.getEtlTransferTaskCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EtlTransferTask> getEtlTransferTasksByQueryCriteria(int start, int pageSize,
			EtlTransferTaskQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EtlTransferTask> rows = sqlSessionTemplate.selectList("getEtlTransferTasks", query, rowBounds);
		return rows;
	}

	public EtlTransferTask getEtlTransferTask(String id) {
		if (id == null) {
			return null;
		}
		EtlTransferTask etlTransferTask = etlTransferTaskMapper.getEtlTransferTaskById(id);
		return etlTransferTask;
	}
	
	
	@Transactional
	public void save(EtlTransferTask etlTransferTask) {
		if (StringUtils.isEmpty(etlTransferTask.getId_())) {
			etlTransferTask.setId_(UUID32.getUUID());
			// etlTransferTask.setCreateDate(new Date());
			// etlTransferTask.setDeleteFlag(0);
			etlTransferTaskMapper.insertEtlTransferTask(etlTransferTask);
		} else {
			etlTransferTaskMapper.updateEtlTransferTask(etlTransferTask);
		}
	}
	@Transactional
	public void updateTransferTaskStatus(EtlTransferTask etlTransferTask){
		etlTransferTask.setLastEndTime_(new Date());
		etlTransferTask.setLocked_(0);
		etlTransferTaskMapper.updateTransferTaskStatus(etlTransferTask);
	}
	@Transactional
	public void updateTransferTaskStartStatus(EtlTransferTask etlTransferTask){
		etlTransferTask.setLastStartTime_(new Date());
		etlTransferTask.setLocked_(1);
		etlTransferTaskMapper.updateTransferTaskStartStatus(etlTransferTask);
	}
	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.etl.mapper.EtlTransferTaskMapper")
	public void setEtlTransferTaskMapper(EtlTransferTaskMapper etlTransferTaskMapper) {
		this.etlTransferTaskMapper = etlTransferTaskMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
