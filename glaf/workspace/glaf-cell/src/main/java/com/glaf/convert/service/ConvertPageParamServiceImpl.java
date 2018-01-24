package com.glaf.convert.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertPageParam;
import com.glaf.convert.mapper.ConvertPageParamMapper;
import com.glaf.convert.query.ConvertPageParamQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.convertPageParamService")
@Transactional(readOnly = true) 
public class ConvertPageParamServiceImpl implements ConvertPageParamService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertPageParamMapper convertPageParamMapper;

	public ConvertPageParamServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		convertPageParamMapper.deleteConvertPageParamById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> cvtParamIds) {
	    if(cvtParamIds != null && !cvtParamIds.isEmpty()){
		for(Long id : cvtParamIds){
		    convertPageParamMapper.deleteConvertPageParamById(id);
		}
	    }
	}

	public int count(ConvertPageParamQuery query) {
		return convertPageParamMapper.getConvertPageParamCount(query);
	}

	public List<ConvertPageParam> list(ConvertPageParamQuery query) {
		List<ConvertPageParam> list = convertPageParamMapper.getConvertPageParams(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getConvertPageParamCountByQueryCriteria(ConvertPageParamQuery query) {
		return convertPageParamMapper.getConvertPageParamCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ConvertPageParam> getConvertPageParamsByQueryCriteria(int start, int pageSize,
			ConvertPageParamQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ConvertPageParam> rows = sqlSessionTemplate.selectList(
				"getConvertPageParams", query, rowBounds);
		return rows;
	}


	public ConvertPageParam getConvertPageParam(Long id) {
	        if(id == null){
		    return null;
		}
		ConvertPageParam convertPageParam = convertPageParamMapper.getConvertPageParamById(id);
		return convertPageParam;
	}

	@Transactional
	public void save(ConvertPageParam convertPageParam) {
            if ( convertPageParam.getCvtParamId()  == null) {
	        convertPageParam.setCvtParamId(idGenerator.nextId("CVT_PAGE_PARAM"));
		//convertPageParam.setCreateDate(new Date());
		//convertPageParam.setDeleteFlag(0);
		convertPageParamMapper.insertConvertPageParam(convertPageParam);
	       } else {
		convertPageParamMapper.updateConvertPageParam(convertPageParam);
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.ConvertPageParamMapper")
	public void setConvertPageParamMapper(ConvertPageParamMapper convertPageParamMapper) {
		this.convertPageParamMapper = convertPageParamMapper;
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
