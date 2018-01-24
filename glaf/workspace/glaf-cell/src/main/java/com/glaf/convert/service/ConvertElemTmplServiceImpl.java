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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertElemTmpl;
import com.glaf.convert.mapper.ConvertElemTmplMapper;
import com.glaf.convert.query.ConvertElemTmplQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.convertElemTmplService")
@Transactional(readOnly = true) 
public class ConvertElemTmplServiceImpl implements ConvertElemTmplService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertElemTmplMapper convertElemTmplMapper;

	public ConvertElemTmplServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		convertElemTmplMapper.deleteConvertElemTmplById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> cvtElemIds) {
	    if(cvtElemIds != null && !cvtElemIds.isEmpty()){
		for(Long id : cvtElemIds){
		    convertElemTmplMapper.deleteConvertElemTmplById(id);
		}
	    }
	}

	public int count(ConvertElemTmplQuery query) {
		return convertElemTmplMapper.getConvertElemTmplCount(query);
	}

	public List<ConvertElemTmpl> list(ConvertElemTmplQuery query) {
		List<ConvertElemTmpl> list = convertElemTmplMapper.getConvertElemTmpls(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getConvertElemTmplCountByQueryCriteria(ConvertElemTmplQuery query) {
		return convertElemTmplMapper.getConvertElemTmplCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ConvertElemTmpl> getConvertElemTmplsByQueryCriteria(int start, int pageSize,
			ConvertElemTmplQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ConvertElemTmpl> rows = sqlSessionTemplate.selectList(
				"getConvertElemTmpls", query, rowBounds);
		return rows;
	}


	public ConvertElemTmpl getConvertElemTmpl(Long id) {
	        if(id == null){
		    return null;
		}
		ConvertElemTmpl convertElemTmpl = convertElemTmplMapper.getConvertElemTmplById(id);
		return convertElemTmpl;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void save(ConvertElemTmpl convertElemTmpl) {
            if ( convertElemTmpl.getCvtElemId()  == null) {
	        convertElemTmpl.setCvtElemId(idGenerator.nextId("CVT_ELEM_TMPL"));
		//convertElemTmpl.setCreateDate(new Date());
		//convertElemTmpl.setDeleteFlag(0);
		convertElemTmplMapper.insertConvertElemTmpl(convertElemTmpl);
	       } else {
		convertElemTmplMapper.updateConvertElemTmpl(convertElemTmpl);
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.ConvertElemTmplMapper")
	public void setConvertElemTmplMapper(ConvertElemTmplMapper convertElemTmplMapper) {
		this.convertElemTmplMapper = convertElemTmplMapper;
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
