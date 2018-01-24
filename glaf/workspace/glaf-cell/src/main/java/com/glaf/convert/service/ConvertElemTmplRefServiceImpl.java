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

import com.glaf.convert.domain.ConvertElemTmplRef;
import com.glaf.convert.mapper.ConvertElemTmplRefMapper;
import com.glaf.convert.query.ConvertElemTmplRefQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.convertElemTmplRefService")
@Transactional(readOnly = true) 
public class ConvertElemTmplRefServiceImpl implements ConvertElemTmplRefService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertElemTmplRefMapper convertElemTmplRefMapper;

	public ConvertElemTmplRefServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		convertElemTmplRefMapper.deleteConvertElemTmplRefById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> refRuleIds) {
	    if(refRuleIds != null && !refRuleIds.isEmpty()){
		for(Long id : refRuleIds){
		    convertElemTmplRefMapper.deleteConvertElemTmplRefById(id);
		}
	    }
	}

	public int count(ConvertElemTmplRefQuery query) {
		return convertElemTmplRefMapper.getConvertElemTmplRefCount(query);
	}

	public List<ConvertElemTmplRef> list(ConvertElemTmplRefQuery query) {
		List<ConvertElemTmplRef> list = convertElemTmplRefMapper.getConvertElemTmplRefs(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getConvertElemTmplRefCountByQueryCriteria(ConvertElemTmplRefQuery query) {
		return convertElemTmplRefMapper.getConvertElemTmplRefCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ConvertElemTmplRef> getConvertElemTmplRefsByQueryCriteria(int start, int pageSize,
			ConvertElemTmplRefQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ConvertElemTmplRef> rows = sqlSessionTemplate.selectList(
				"getConvertElemTmplRefs", query, rowBounds);
		return rows;
	}


	public ConvertElemTmplRef getConvertElemTmplRef(Long id) {
	        if(id == null){
		    return null;
		}
		ConvertElemTmplRef convertElemTmplRef = convertElemTmplRefMapper.getConvertElemTmplRefById(id);
		return convertElemTmplRef;
	}

	@Transactional
	public void save(ConvertElemTmplRef convertElemTmplRef) {
            if ( convertElemTmplRef.getRefRuleId()  == null) {
	        convertElemTmplRef.setRefRuleId(idGenerator.nextId("CVT_ELEM_TMPL_REF"));
		//convertElemTmplRef.setCreateDate(new Date());
		//convertElemTmplRef.setDeleteFlag(0);
		convertElemTmplRefMapper.insertConvertElemTmplRef(convertElemTmplRef);
	       } else {
		convertElemTmplRefMapper.updateConvertElemTmplRef(convertElemTmplRef);
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.ConvertElemTmplRefMapper")
	public void setConvertElemTmplRefMapper(ConvertElemTmplRefMapper convertElemTmplRefMapper) {
		this.convertElemTmplRefMapper = convertElemTmplRefMapper;
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
	public List<ConvertElemTmplRef> getConvertElemTmplRefsByCvtId(Long cvtId) {
		return convertElemTmplRefMapper.getConvertElemTmplRefsByCvtId(cvtId,null);
	}

}
