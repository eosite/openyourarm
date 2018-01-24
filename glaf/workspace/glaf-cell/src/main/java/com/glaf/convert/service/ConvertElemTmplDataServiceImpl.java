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

import com.glaf.convert.domain.ConvertElemTmplData;
import com.glaf.convert.mapper.ConvertElemTmplDataMapper;
import com.glaf.convert.query.ConvertElemTmplDataQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.convertElemTmplDataService")
@Transactional(readOnly = true) 
public class ConvertElemTmplDataServiceImpl implements ConvertElemTmplDataService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertElemTmplDataMapper convertElemTmplDataMapper;

	public ConvertElemTmplDataServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		convertElemTmplDataMapper.deleteConvertElemTmplDataById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> dataRuleIds) {
	    if(dataRuleIds != null && !dataRuleIds.isEmpty()){
		for(Long id : dataRuleIds){
		    convertElemTmplDataMapper.deleteConvertElemTmplDataById(id);
		}
	    }
	}

	public int count(ConvertElemTmplDataQuery query) {
		return convertElemTmplDataMapper.getConvertElemTmplDataCount(query);
	}

	public List<ConvertElemTmplData> list(ConvertElemTmplDataQuery query) {
		List<ConvertElemTmplData> list = convertElemTmplDataMapper.getConvertElemTmplDatas(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getConvertElemTmplDataCountByQueryCriteria(ConvertElemTmplDataQuery query) {
		return convertElemTmplDataMapper.getConvertElemTmplDataCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ConvertElemTmplData> getConvertElemTmplDatasByQueryCriteria(int start, int pageSize,
			ConvertElemTmplDataQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ConvertElemTmplData> rows = sqlSessionTemplate.selectList(
				"getConvertElemTmplDatas", query, rowBounds);
		return rows;
	}


	public ConvertElemTmplData getConvertElemTmplData(Long id) {
	        if(id == null){
		    return null;
		}
		ConvertElemTmplData convertElemTmplData = convertElemTmplDataMapper.getConvertElemTmplDataById(id);
		return convertElemTmplData;
	}

	@Transactional
	public void save(ConvertElemTmplData convertElemTmplData) {
            if ( convertElemTmplData.getDataRuleId()  == null) {
	        convertElemTmplData.setDataRuleId(idGenerator.nextId("CVT_ELEM_TMPL_DATA"));
		//convertElemTmplData.setCreateDate(new Date());
		//convertElemTmplData.setDeleteFlag(0);
		convertElemTmplDataMapper.insertConvertElemTmplData(convertElemTmplData);
	       } else {
		convertElemTmplDataMapper.updateConvertElemTmplData(convertElemTmplData);
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.ConvertElemTmplDataMapper")
	public void setConvertElemTmplDataMapper(ConvertElemTmplDataMapper convertElemTmplDataMapper) {
		this.convertElemTmplDataMapper = convertElemTmplDataMapper;
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
