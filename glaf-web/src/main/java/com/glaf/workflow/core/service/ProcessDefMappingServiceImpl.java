package com.glaf.workflow.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

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
import com.glaf.workflow.core.domain.ProcessDefMapping;
import com.glaf.workflow.core.mapper.ProcessDefMappingMapper;
import com.glaf.workflow.core.query.ProcessDefMappingQuery;

@Service("com.glaf.workflow.core.service.processDefMappingService")
@Transactional(readOnly = true) 
public class ProcessDefMappingServiceImpl implements ProcessDefMappingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ProcessDefMappingMapper processDefMappingMapper;

	public ProcessDefMappingServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		processDefMappingMapper.deleteProcessDefMappingById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> iDs) {
	    if(iDs != null && !iDs.isEmpty()){
		for(String id : iDs){
		    processDefMappingMapper.deleteProcessDefMappingById(id);
		}
	    }
	}

	public int count(ProcessDefMappingQuery query) {
		return processDefMappingMapper.getProcessDefMappingCount(query);
	}

	public List<ProcessDefMapping> list(ProcessDefMappingQuery query) {
		List<ProcessDefMapping> list = processDefMappingMapper.getProcessDefMappings(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getProcessDefMappingCountByQueryCriteria(ProcessDefMappingQuery query) {
		return processDefMappingMapper.getProcessDefMappingCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ProcessDefMapping> getProcessDefMappingsByQueryCriteria(int start, int pageSize,
			ProcessDefMappingQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ProcessDefMapping> rows = sqlSessionTemplate.selectList(
				"getProcessDefMappings", query, rowBounds);
		return rows;
	}


	public ProcessDefMapping getProcessDefMapping(String id) {
	        if(id == null){
		    return null;
		}
		ProcessDefMapping processDefMapping = processDefMappingMapper.getProcessDefMappingById(id);
		return processDefMapping;
	}

	@Transactional
	public void save(ProcessDefMapping processDefMapping) {
           if (StringUtils.isEmpty(processDefMapping.getID())) {
	        processDefMapping.setID(UUID32.getUUID());
		//processDefMapping.setCreateDate(new Date());
		//processDefMapping.setDeleteFlag(0);
		processDefMappingMapper.insertProcessDefMapping(processDefMapping);
	       } else {
		processDefMappingMapper.updateProcessDefMapping(processDefMapping);
	      }
	}

	public ProcessDefMapping getProcessDefMappingByImpProcessDefAndImpSysId(String impProcessDef,String impSysId,String sysId){
		ProcessDefMapping processDefMapping=processDefMappingMapper.getProcessDefMappingByImpProcessDefAndImpSysId(impProcessDef,impSysId,sysId);
		return processDefMapping;
	}
	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
 		String sql = "  ";//要运行的SQL语句
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

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.workflow.core.mapper.ProcessDefMappingMapper")
	public void setProcessDefMappingMapper(ProcessDefMappingMapper processDefMappingMapper) {
		this.processDefMappingMapper = processDefMappingMapper;
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
