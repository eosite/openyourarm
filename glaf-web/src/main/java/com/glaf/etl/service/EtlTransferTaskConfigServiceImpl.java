package com.glaf.etl.service;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service("com.glaf.etl.service.etlTransferTaskConfigService")
@Transactional(readOnly = true)
public class EtlTransferTaskConfigServiceImpl implements EtlTransferTaskConfigService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	protected EtlTransferTaskMapper etlTransferTaskMapper;
	@Autowired
	protected EtlTransferTaskSrcMapper etlTransferTaskSrcMapper;
	@Autowired
	protected EtlTransferTaskTargetMapper etlTransferTaskTargetMapper;
	protected EtlTransferTaskConfigMapper etlTransferTaskConfigMapper;
	@Autowired
	protected EtlTransferTaskService etlTransferTaskService;
	@Autowired
	protected EtlTransferTaskSrcService etlTransferTaskSrcService;
	@Autowired
	protected EtlTransferTaskTargetService etlTransferTaskTargetService;
	

	public EtlTransferTaskConfigServiceImpl() {

	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEtlTransferTaskCount() {
		return etlTransferTaskConfigMapper.getTaskConfigCount();
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EtlTransferTaskConfig> getEtlTransferTasks(int start, int pageSize,EtlTransferTaskConfigQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EtlTransferTaskConfig> rows = sqlSessionTemplate.selectList("listAllTasks", query, rowBounds);
		return rows;
	}
	
	
	@Override
	public void deleteById(String id) {
		if (id != null) {
			etlTransferTaskSrcMapper.deleteByTaskId(id);
			etlTransferTaskTargetMapper.deleteByTaskId(id);
			etlTransferTaskMapper.deleteEtlTransferTaskById(id);
		}
	}

	@Override
	public void save(EtlTransferTask etlTransferTask,EtlTransferTaskSrc etlTransferTaskSrc,EtlTransferTaskTarget etlTransferTaskTarget) {
		etlTransferTaskService.save(etlTransferTask);
		etlTransferTaskSrc.setTaskId_(etlTransferTask.getId_()); //设置任务id
		etlTransferTaskSrcService.save(etlTransferTaskSrc);
		etlTransferTaskTarget.setTaskId_(etlTransferTask.getId_());//设置任务id
		etlTransferTaskTargetService.save(etlTransferTaskTarget);
	}
	
	
	@javax.annotation.Resource(name = "com.glaf.etl.mapper.etlTransferTaskConfigMapper")
	public void setEtlTransferTaskMapper(EtlTransferTaskConfigMapper etlTransferTaskConfigMapper) {
		this.etlTransferTaskConfigMapper = etlTransferTaskConfigMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}



	@Override
	public void deleteByIds(List<String> id_s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EtlTransferTask> list(EtlTransferTaskQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, String>> getTaskSrcTargetMap(
			EtlTransferTaskQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EtlTransferTask getEtlTransferTask(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void updateTransferTaskStartStatus(EtlTransferTask etlTransferTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTransferTaskStatus(EtlTransferTask etlTransferTask) {
		// TODO Auto-generated method stub
		
	}


}
