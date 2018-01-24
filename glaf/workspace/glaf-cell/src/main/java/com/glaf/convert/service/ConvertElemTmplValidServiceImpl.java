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
import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertElemTmplValid;
import com.glaf.convert.mapper.ConvertElemTmplValidMapper;
import com.glaf.convert.query.ConvertElemTmplValidQuery;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;

@Service("com.glaf.convert.service.convertElemTmplValidService")
@Transactional(readOnly = true) 
public class ConvertElemTmplValidServiceImpl implements ConvertElemTmplValidService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertElemTmplValidMapper convertElemTmplValidMapper;

	public ConvertElemTmplValidServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		convertElemTmplValidMapper.deleteConvertElemTmplValidById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> validRuleIds) {
	    if(validRuleIds != null && !validRuleIds.isEmpty()){
		for(Long id : validRuleIds){
		    convertElemTmplValidMapper.deleteConvertElemTmplValidById(id);
		}
	    }
	}

	public int count(ConvertElemTmplValidQuery query) {
		return convertElemTmplValidMapper.getConvertElemTmplValidCount(query);
	}

	public List<ConvertElemTmplValid> list(ConvertElemTmplValidQuery query) {
		List<ConvertElemTmplValid> list = convertElemTmplValidMapper.getConvertElemTmplValids(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getConvertElemTmplValidCountByQueryCriteria(ConvertElemTmplValidQuery query) {
		return convertElemTmplValidMapper.getConvertElemTmplValidCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ConvertElemTmplValid> getConvertElemTmplValidsByQueryCriteria(int start, int pageSize,
			ConvertElemTmplValidQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ConvertElemTmplValid> rows = sqlSessionTemplate.selectList(
				"getConvertElemTmplValids", query, rowBounds);
		return rows;
	}


	public ConvertElemTmplValid getConvertElemTmplValid(Long id) {
	        if(id == null){
		    return null;
		}
		ConvertElemTmplValid convertElemTmplValid = convertElemTmplValidMapper.getConvertElemTmplValidById(id);
		return convertElemTmplValid;
	}

	@Transactional
	public void save(ConvertElemTmplValid convertElemTmplValid) {
            if ( convertElemTmplValid.getValidRuleId()  == null) {
	        convertElemTmplValid.setValidRuleId(idGenerator.nextId("CVT_ELEM_TMPL_VALID"));
	        convertElemTmplValid.setTreeId(convertElemTmplValid.getValidRuleId()+convertElemTmplValid.getValidRuleId()+"|");
		//convertElemTmplValid.setCreateDate(new Date());
		//convertElemTmplValid.setDeleteFlag(0);
		convertElemTmplValidMapper.insertConvertElemTmplValid(convertElemTmplValid);
	       } else {
		convertElemTmplValidMapper.updateConvertElemTmplValid(convertElemTmplValid);
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
	@Override
	public ConvertElemTmplValid createVaildRule(ConvertElemTmplValid pConvertElemTmplValid,Long cvtElemId, String type,Map<String,String> params) {
		// TODO Auto-generated method stub
		// 可扩展为字典配置
		ConvertElemTmplValid convertElemTmplValid = new ConvertElemTmplValid();
		convertElemTmplValid.setCvtElemId(cvtElemId);
		if(pConvertElemTmplValid!=null){
			convertElemTmplValid.setParentRuleId(convertElemTmplValid.getCvtElemId());
		}
		switch (type) {
		// 是否必填
		case "require":
			convertElemTmplValid.setExpression("isNotNull");
			convertElemTmplValid.setValidType(type);
			convertElemTmplValid.setUseCondition("true");
			convertElemTmplValid.setSeq(1);
			break;
		// 数据类型
		case "datatype":
			convertElemTmplValid.setExpression("validDataType");
			convertElemTmplValid.setValidType(type);
			convertElemTmplValid.setCreateDatetime(new Date());
			convertElemTmplValid.setUseCondition("true");
			convertElemTmplValid.setDType(params.get("dType"));
			convertElemTmplValid.setSeq(2);
			break;
			// 数值长度
		case "length":
			convertElemTmplValid.setExpression("validLength");
			convertElemTmplValid.setValidType(type);
			if(params.containsKey("len")&&params.get("len")!=null&&params.get("len").trim().length()>0)
			{
				convertElemTmplValid.setLen(Integer.parseInt(params.get("len")));
			}
			convertElemTmplValid.setCreateDatetime(new Date());
			convertElemTmplValid.setUseCondition("true");
			convertElemTmplValid.setSeq(2);
			break;
		case "decimaldigits":
            convertElemTmplValid.setExpression("validDecimalDigits");
			convertElemTmplValid.setValidType(type);
			convertElemTmplValid.setCreateDatetime(new Date());
			convertElemTmplValid.setUseCondition("true");
			convertElemTmplValid.setSeq(2);
			break;
			// 输入范围
		case "range":
			convertElemTmplValid.setExpression("validRange");
			convertElemTmplValid.setValidType(type);
			if(params.containsKey("rangeLower"))
			convertElemTmplValid.setRangeLower(params.get("rangeLower"));
			if(params.containsKey("rangeUpper"))
			convertElemTmplValid.setRangeUpper(params.get("rangeUpper"));
			convertElemTmplValid.setUseCondition("true");
			convertElemTmplValid.setSeq(3);
			break;
		// 唯一性
		case "unique":
			convertElemTmplValid.setExpression("valDataType");
			convertElemTmplValid.setValidType(type);
			convertElemTmplValid.setSeq(4);
			break;
		default:
			break;
		}
		if(convertElemTmplValid.getValidType()!=null&&convertElemTmplValid.getValidType().trim().length()>0){
			convertElemTmplValid.setCreateDatetime(new Date());
			convertElemTmplValid.setModifyDatetime(new Date());
			if(pConvertElemTmplValid!=null)
			{
				convertElemTmplValid.setTreeId(pConvertElemTmplValid.getTreeId());
			}
			save(convertElemTmplValid);
		}
		return convertElemTmplValid;
	}
	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.convert.mapper.ConvertElemTmplValidMapper")
	public void setConvertElemTmplValidMapper(ConvertElemTmplValidMapper convertElemTmplValidMapper) {
		this.convertElemTmplValidMapper = convertElemTmplValidMapper;
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
