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

import com.glaf.convert.domain.CvtRunHis;
import com.glaf.convert.mapper.CvtRunHisMapper;
import com.glaf.convert.query.CvtRunHisQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.cvtRunHisService")
@Transactional(readOnly = true) 
public class CvtRunHisServiceImpl implements CvtRunHisService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CvtRunHisMapper cvtRunHisMapper;

	public CvtRunHisServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		cvtRunHisMapper.deleteCvtRunHisById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> taskCodes) {
	    if(taskCodes != null && !taskCodes.isEmpty()){
		for(String id : taskCodes){
		    cvtRunHisMapper.deleteCvtRunHisById(id);
		}
	    }
	}

	public int count(CvtRunHisQuery query) {
		return cvtRunHisMapper.getCvtRunHisCount(query);
	}

	public List<CvtRunHis> list(CvtRunHisQuery query) {
		List<CvtRunHis> list = cvtRunHisMapper.getCvtRunHiss(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getCvtRunHisCountByQueryCriteria(CvtRunHisQuery query) {
		return cvtRunHisMapper.getCvtRunHisCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CvtRunHis> getCvtRunHissByQueryCriteria(int start, int pageSize,
			CvtRunHisQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CvtRunHis> rows = sqlSessionTemplate.selectList(
				"getCvtRunHiss", query, rowBounds);
		return rows;
	}


	public CvtRunHis getCvtRunHis(String id) {
	        if(id == null){
		    return null;
		}
		CvtRunHis cvtRunHis = cvtRunHisMapper.getCvtRunHisById(id);
		return cvtRunHis;
	}

	@Transactional
	public void save(CvtRunHis cvtRunHis) {
           if (StringUtils.isEmpty(cvtRunHis.getTaskCode())) {
	        cvtRunHis.setTaskCode(idGenerator.getNextId("CVT_RUN_HIS"));
		//cvtRunHis.setCreateDate(new Date());
		//cvtRunHis.setDeleteFlag(0);
		cvtRunHisMapper.insertCvtRunHis(cvtRunHis);
	       } else {
		cvtRunHisMapper.updateCvtRunHis(cvtRunHis);
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.CvtRunHisMapper")
	public void setCvtRunHisMapper(CvtRunHisMapper cvtRunHisMapper) {
		this.cvtRunHisMapper = cvtRunHisMapper;
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
