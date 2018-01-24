package com.glaf.workflow.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;
import com.glaf.workflow.core.domain.ProcessInsMapping;
import com.glaf.workflow.core.domain.ProcessMainTableName;
import com.glaf.workflow.core.mapper.ProcessInsMappingMapper;
import com.glaf.workflow.core.query.ProcessInsMappingQuery;

@Service("com.glaf.workflow.core.service.processInsMappingService")
@Transactional(readOnly = true)
public class ProcessInsMappingServiceImpl implements ProcessInsMappingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ProcessInsMappingMapper processInsMappingMapper;

	public ProcessInsMappingServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			processInsMappingMapper.deleteProcessInsMappingById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> iDs) {
		if (iDs != null && !iDs.isEmpty()) {
			for (String id : iDs) {
				processInsMappingMapper.deleteProcessInsMappingById(id);
			}
		}
	}

	public int count(ProcessInsMappingQuery query) {
		return processInsMappingMapper.getProcessInsMappingCount(query);
	}

	public List<ProcessInsMapping> list(ProcessInsMappingQuery query) {
		List<ProcessInsMapping> list = processInsMappingMapper.getProcessInsMappings(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getProcessInsMappingCountByQueryCriteria(ProcessInsMappingQuery query) {
		return processInsMappingMapper.getProcessInsMappingCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ProcessInsMapping> getProcessInsMappingsByQueryCriteria(int start, int pageSize,
			ProcessInsMappingQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ProcessInsMapping> rows = sqlSessionTemplate.selectList("getProcessInsMappings", query, rowBounds);
		return rows;
	}

	/**
	 * 获取待启动流程
	 * 
	 * @return
	 */
	public List<ProcessInsMapping> getNeedStartImpProcess() {
		ProcessInsMappingQuery query = new ProcessInsMappingQuery();
		query.setProcStatus(0);
		return list(query);
	}

	public ProcessInsMapping getProcessInsMapping(String id) {
		if (id == null) {
			return null;
		}
		ProcessInsMapping processInsMapping = processInsMappingMapper.getProcessInsMappingById(id);
		return processInsMapping;
	}

	/**
	 * 获取流程对应表单主数据表主键值
	 * 
	 * @param sysId
	 * @param processId
	 * @return
	 */
	public Map<String, String> getTablePrimaryKeyValBySysId(String sysId, String processId) {
		Map<String, String> tablePrimaryKeyVal = null;
		List<ProcessMainTableName> processMainTableNames = getProcessMainTableNames(processId);
		if (processMainTableNames != null && processMainTableNames.size() > 0) {
			tablePrimaryKeyVal = getTablePrimaryKeyVal(processMainTableNames);
		}
		return tablePrimaryKeyVal;
	}
	/**
	 * 获取wpf流程数据业务主表主键值
	 */
	public Map<String,String> getWpfTablePrimaryKeyValBySysId(String sysId,String processId){
		Map<String, String> tablePrimaryKeyVal = null;
		List<ProcessMainTableName> processMainTableNames = getWpfProcessMainTableNames(processId);
		if (processMainTableNames != null && processMainTableNames.size() > 0) {
			tablePrimaryKeyVal = getWpfTablePrimaryKeyVal(processMainTableNames);
		}
		return tablePrimaryKeyVal;
	}
	/**
	 * 
	 */
	public Map<String,String> getReportTablePrimaryKeyValBySysId(String sysId,String processId){
		Map<String, String> tablePrimaryKeyVal = null;
		List<ProcessMainTableName> processMainTableNames = getReportProcessMainTableNames(processId);
			if (processMainTableNames != null && processMainTableNames.size() > 0) {
				tablePrimaryKeyVal = getReportTablePrimaryKeyVal(processMainTableNames,processId);
			}	
		return tablePrimaryKeyVal;
	}
	/**
	 * 获取流程主数据表
	 */
	public List<ProcessMainTableName> getProcessMainTableNames(String processId) {
		return processInsMappingMapper.getProcessMainTableNames(processId);
	}
	/**
	 * 获取流程主数据表
	 */
	public List<ProcessMainTableName> getReportProcessMainTableNames(String processId) {
		return processInsMappingMapper.getReportProcessMainTableNames(processId);
	}
	/**
	 * 获取流程主数据表
	 */
	public List<ProcessMainTableName> getWpfProcessMainTableNames(String processId) {
		return processInsMappingMapper.getWpfProcessMainTableNames(processId);
	}
	/**
	 * 获取报表主数据表主键值
	 * 
	 * @param processInsId
	 * @return
	 */
	public Map<String, String> getTablePrimaryKeyVal(List<ProcessMainTableName> processMainTableNames) {
		Map<String, String> tablePrimaryKeyVal = new HashMap<String, String>();
		for (ProcessMainTableName processMainTableName : processMainTableNames) {
			String keyVal = processInsMappingMapper.getProcessMainTablePrimaryKeyVal(
					processMainTableName.getTableName(), processMainTableName.getFillFormId());
			tablePrimaryKeyVal.put(processMainTableName.getTableName(), keyVal);
		}
		return tablePrimaryKeyVal;
	}
	/**
	 * 获取报表主数据表主键值
	 * 
	 * @param processInsId
	 * @return
	 */
	public Map<String, String> getReportTablePrimaryKeyVal(List<ProcessMainTableName> processMainTableNames,String processId) {
		Map<String, String> tablePrimaryKeyVal = new HashMap<String, String>();
		for (ProcessMainTableName processMainTableName : processMainTableNames) {
			String keyVal = processInsMappingMapper.getReportProcessMainTablePrimaryKeyVal(
					processMainTableName.getTableName(), processId);
			tablePrimaryKeyVal.put(processMainTableName.getTableName(), keyVal);
		}
		return tablePrimaryKeyVal;
	}
	/**
	 * 获取主表主键值
	 * @param processMainTableNames
	 * @return
	 */
	public Map<String, String> getWpfTablePrimaryKeyVal(List<ProcessMainTableName> processMainTableNames) {
		Map<String, String> tablePrimaryKeyVal = new HashMap<String, String>();
		for (ProcessMainTableName processMainTableName : processMainTableNames) {
			String keyVal = processInsMappingMapper.getProcessMainTablePrimaryKeyVal(
					processMainTableName.getTableName(), processMainTableName.getFillFormId());
			tablePrimaryKeyVal.put(processMainTableName.getTableName(), keyVal);
			tablePrimaryKeyVal.put("cell_fillform_wpf", processMainTableName.getFillFormId());
		}
		
		return tablePrimaryKeyVal;
	}
	@Transactional
	public void save(ProcessInsMapping processInsMapping) {
		if (StringUtils.isEmpty(processInsMapping.getID())) {
			processInsMapping.setID(UUID32.getUUID());
			// processInsMapping.setCreateDate(new Date());
			// processInsMapping.setDeleteFlag(0);
			processInsMappingMapper.insertProcessInsMapping(processInsMapping);
		} else {
			processInsMappingMapper.updateProcessInsMapping(processInsMapping);
		}
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
				psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	@Override
	public void updateMapping(ProcessInsMapping processInsMapping) {
		logger.debug("-------------------start updateMapping-------------------");
		Connection connection = null;
		PreparedStatement psmt = null;
		final ProcessInsMapping mapping = processInsMapping;
		try {
			String tableName = processInsMapping.getClass().getDeclaredAnnotation(Table.class).name();
			String sql = String.format("UPDATE %s SET PROC_STATUS_ = ?,  PROC_RESULT_ = ? WHERE DES_PROC_INS_ID_ = ?",
					tableName);// 要运行的SQL语句
		//	connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			
			connection = sqlSessionTemplate.getConnection();//当前session 不用关闭
			psmt = connection.prepareStatement(sql);

			JdbcUtils.fillStatement(psmt, new ArrayList<Object>() {
				/**
				* 
				*/
				private static final long serialVersionUID = 1L;

				{
					add(mapping.getProcStatus());
					add(mapping.getProcResult());
					add(mapping.getDesProcInsId());
				}
			});
			psmt.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("updateMapping error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end updateMapping-------------------");
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.workflow.core.mapper.ProcessInsMappingMapper")
	public void setProcessInsMappingMapper(ProcessInsMappingMapper processInsMappingMapper) {
		this.processInsMappingMapper = processInsMappingMapper;
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
