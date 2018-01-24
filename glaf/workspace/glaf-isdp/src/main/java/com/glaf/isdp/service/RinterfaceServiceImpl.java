package com.glaf.isdp.service;

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
import com.glaf.isdp.domain.Rinterface;
import com.glaf.isdp.mapper.RinterfaceMapper;
import com.glaf.isdp.query.RinterfaceQuery;

@Service("com.glaf.isdp.service.rinterfaceService")
@Transactional(readOnly = true)
public class RinterfaceServiceImpl implements RinterfaceService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected RinterfaceMapper rinterfaceMapper;

	public RinterfaceServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<Rinterface> list) {
		for (Rinterface rinterface : list) {
			if (rinterface.getListId() == null) {

				rinterface.setListId(idGenerator.getNextId("RINTERFACE", "LISTID", rinterface.getCreateBy()));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// rinterfaceMapper.bulkInsertRinterface_oracle(list);
		} else {
			// rinterfaceMapper.bulkInsertRinterface(list);
		}
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			rinterfaceMapper.deleteRinterfaceById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				rinterfaceMapper.deleteRinterfaceById(id);
			}
		}
	}

	public int count(RinterfaceQuery query) {
		return rinterfaceMapper.getRinterfaceCount(query);
	}

	public List<Rinterface> list(RinterfaceQuery query) {
		List<Rinterface> list = rinterfaceMapper.getRinterfaces(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getRinterfaceCountByQueryCriteria(RinterfaceQuery query) {
		return rinterfaceMapper.getRinterfaceCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Rinterface> getRinterfacesByQueryCriteria(int start, int pageSize, RinterfaceQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Rinterface> rows = sqlSessionTemplate.selectList("getRinterfaces", query, rowBounds);
		return rows;
	}

	public Rinterface getRinterface(String id) {
		if (id == null) {
			return null;
		}
		Rinterface rinterface = rinterfaceMapper.getRinterfaceById(id);
		return rinterface;
	}

	@Transactional
	public void save(Rinterface rinterface) {
		if (rinterface.getListId() == null) {
			if (StringUtils.isEmpty(rinterface.getCreateBy())) {
				LoginContext loginContext = Authentication.getLoginContext();
				rinterface.setCreateBy(loginContext != null ? loginContext.getActorId() : "admin");
			}
			rinterface.setListId(idGenerator.getNextId("R_INTERFACE", "LISTID", rinterface.getCreateBy()));
			rinterfaceMapper.insertRinterface(rinterface);
		} else {
			rinterfaceMapper.updateRinterface(rinterface);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.RinterfaceMapper")
	public void setRinterfaceMapper(RinterfaceMapper rinterfaceMapper) {
		this.rinterfaceMapper = rinterfaceMapper;
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
