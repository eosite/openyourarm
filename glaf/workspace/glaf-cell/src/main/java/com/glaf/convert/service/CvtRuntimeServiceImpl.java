package com.glaf.convert.service;

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

import com.glaf.convert.domain.CvtRuntime;
import com.glaf.convert.mapper.CvtRuntimeMapper;
import com.glaf.convert.query.CvtRuntimeQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.cvtRuntimeService")
@Transactional(readOnly = true) 
public class CvtRuntimeServiceImpl implements CvtRuntimeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CvtRuntimeMapper cvtRuntimeMapper;

	public CvtRuntimeServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		cvtRuntimeMapper.deleteCvtRuntimeById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> taskCodes) {
	    if(taskCodes != null && !taskCodes.isEmpty()){
		for(String id : taskCodes){
		    cvtRuntimeMapper.deleteCvtRuntimeById(id);
		}
	    }
	}

	public int count(CvtRuntimeQuery query) {
		return cvtRuntimeMapper.getCvtRuntimeCount(query);
	}

	public List<CvtRuntime> list(CvtRuntimeQuery query) {
		List<CvtRuntime> list = cvtRuntimeMapper.getCvtRuntimes(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getCvtRuntimeCountByQueryCriteria(CvtRuntimeQuery query) {
		return cvtRuntimeMapper.getCvtRuntimeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CvtRuntime> getCvtRuntimesByQueryCriteria(int start, int pageSize,
			CvtRuntimeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CvtRuntime> rows = sqlSessionTemplate.selectList(
				"getCvtRuntimes", query, rowBounds);
		return rows;
	}


	public CvtRuntime getCvtRuntime(String id) {
	        if(id == null){
		    return null;
		}
		CvtRuntime cvtRuntime = cvtRuntimeMapper.getCvtRuntimeById(id);
		return cvtRuntime;
	}

	@Transactional
	public void save(CvtRuntime cvtRuntime) {
           if (StringUtils.isEmpty(cvtRuntime.getTaskCode())) {
	        cvtRuntime.setTaskCode(idGenerator.getNextId("CVT_RUNTIME"));
		//cvtRuntime.setCreateDate(new Date());
		//cvtRuntime.setDeleteFlag(0);
		cvtRuntimeMapper.insertCvtRuntime(cvtRuntime);
	       } else {
		cvtRuntimeMapper.updateCvtRuntime(cvtRuntime);
	      }
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.CvtRuntimeMapper")
	public void setCvtRuntimeMapper(CvtRuntimeMapper cvtRuntimeMapper) {
		this.cvtRuntimeMapper = cvtRuntimeMapper;
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
