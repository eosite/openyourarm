package com.glaf.convert.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.convert.domain.ConvertElemPropTmpl;
import com.glaf.convert.mapper.ConvertElemPropTmplMapper;
import com.glaf.convert.query.ConvertElemPropTmplQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.convertElemPropTmplService")
@Transactional(readOnly = true) 
public class ConvertElemPropTmplServiceImpl implements ConvertElemPropTmplService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertElemPropTmplMapper convertElemPropTmplMapper;

	public ConvertElemPropTmplServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		convertElemPropTmplMapper.deleteConvertElemPropTmplById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> elemPropIds) {
	    if(elemPropIds != null && !elemPropIds.isEmpty()){
		for(Long id : elemPropIds){
		    convertElemPropTmplMapper.deleteConvertElemPropTmplById(id);
		}
	    }
	}

	public int count(ConvertElemPropTmplQuery query) {
		return convertElemPropTmplMapper.getConvertElemPropTmplCount(query);
	}

	public List<ConvertElemPropTmpl> list(ConvertElemPropTmplQuery query) {
		List<ConvertElemPropTmpl> list = convertElemPropTmplMapper.getConvertElemPropTmpls(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getConvertElemPropTmplCountByQueryCriteria(ConvertElemPropTmplQuery query) {
		return convertElemPropTmplMapper.getConvertElemPropTmplCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ConvertElemPropTmpl> getConvertElemPropTmplsByQueryCriteria(int start, int pageSize,
			ConvertElemPropTmplQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ConvertElemPropTmpl> rows = sqlSessionTemplate.selectList(
				"getConvertElemPropTmpls", query, rowBounds);
		return rows;
	}


	public ConvertElemPropTmpl getConvertElemPropTmpl(Long id) {
	        if(id == null){
		    return null;
		}
		ConvertElemPropTmpl convertElemPropTmpl = convertElemPropTmplMapper.getConvertElemPropTmplById(id);
		return convertElemPropTmpl;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void save(ConvertElemPropTmpl convertElemPropTmpl) {
            if ( convertElemPropTmpl.getElemPropId()  == null) {
	        convertElemPropTmpl.setElemPropId(idGenerator.nextId("CVT_ELEMPROP_TMPL"));
		//convertElemPropTmpl.setCreateDate(new Date());
		//convertElemPropTmpl.setDeleteFlag(0);
		convertElemPropTmplMapper.insertConvertElemPropTmpl(convertElemPropTmpl);
	       } else {
		convertElemPropTmplMapper.updateConvertElemPropTmpl(convertElemPropTmpl);
	      }
	}
	@Transactional
	public void batchSave(Map<String,String> propMap,Long cvtId,int rowIndex,int colIndex) {
		    ConvertElemPropTmpl convertElemPropTmpl=null;
			convertElemPropTmpl=new ConvertElemPropTmpl();
			convertElemPropTmpl.setCvtId(cvtId);
			convertElemPropTmpl.setColIndex(rowIndex);
			convertElemPropTmpl.setRowIndex(colIndex);
			convertElemPropTmpl.setCellProp("ALL");
			convertElemPropTmpl.setCellPropVal(JSONObject.toJSONString(propMap));
			convertElemPropTmpl.setCreateDatetime(new Date());
			convertElemPropTmpl.setModifyDatetime(new Date());
			save(convertElemPropTmpl);
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

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.ConvertElemPropTmplMapper")
	public void setConvertElemPropTmplMapper(ConvertElemPropTmplMapper convertElemPropTmplMapper) {
		this.convertElemPropTmplMapper = convertElemPropTmplMapper;
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
