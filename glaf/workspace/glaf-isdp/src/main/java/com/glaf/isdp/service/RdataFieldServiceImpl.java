package com.glaf.isdp.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.Authentication;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DBUtils;
import com.glaf.isdp.domain.RdataField;
import com.glaf.isdp.mapper.RdataFieldMapper;
import com.glaf.isdp.query.RdataFieldQuery;

@Service("com.glaf.isdp.service.rdataFieldService")
@Transactional(readOnly = true)
public class RdataFieldServiceImpl implements RdataFieldService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected RdataFieldMapper rdataFieldMapper;

	public RdataFieldServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<RdataField> list) {
		for (RdataField rdataField : list) {
			if (StringUtils.isEmpty(rdataField.getId())) {
				rdataField.setId(idGenerator.getNextId("R_DATA_FIELD"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// rdataFieldMapper.bulkInsertRdataField_oracle(list);
		} else {
			// rdataFieldMapper.bulkInsertRdataField(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			rdataFieldMapper.deleteRdataFieldById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				rdataFieldMapper.deleteRdataFieldById(id);
			}
		}
	}

	public int count(RdataFieldQuery query) {
		return rdataFieldMapper.getRdataFieldCount(query);
	}

	public List<RdataField> list(RdataFieldQuery query) {
		List<RdataField> list = rdataFieldMapper.getRdataFields(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getRdataFieldCountByQueryCriteria(RdataFieldQuery query) {
		return rdataFieldMapper.getRdataFieldCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<RdataField> getRdataFieldsByQueryCriteria(int start, int pageSize, RdataFieldQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<RdataField> rows = sqlSessionTemplate.selectList("getRdataFields", query, rowBounds);
		return rows;
	}

	public RdataField getRdataField(String id) {
		if (id == null) {
			return null;
		}
		RdataField rdataField = rdataFieldMapper.getRdataFieldById(id);
		return rdataField;
	}

	@Transactional
	public void save(RdataField rdataField) {
		if (StringUtils.isEmpty(rdataField.getId())) {
			if (StringUtils.isEmpty(rdataField.getUserid())) {
				LoginContext loginContext = Authentication.getLoginContext();
				rdataField.setUserid(loginContext != null ? loginContext.getActorId() : "admin");
			}
			String id = idGenerator.getNextId("R_DATA_FIELD", "id", rdataField.getUserid());
			rdataField.setId(id);
			rdataField.setCtime(new Date());
			rdataFieldMapper.insertRdataField(rdataField);
		} else {
			rdataFieldMapper.updateRdataField(rdataField);
		}
	}

	public int getNextMaxUser(String tableId) {
		return rdataFieldMapper.getNextMaxUser(tableId);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.RdataFieldMapper")
	public void setRdataFieldMapper(RdataFieldMapper rdataFieldMapper) {
		this.rdataFieldMapper = rdataFieldMapper;
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
