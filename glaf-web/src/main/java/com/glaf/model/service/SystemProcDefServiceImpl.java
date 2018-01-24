package com.glaf.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.UUID32;
import com.glaf.model.domain.SystemProcDef;
import com.glaf.model.mapper.SystemProcDefMapper;
import com.glaf.model.query.SystemProcDefQuery;

@Service("com.glaf.model.service.systemProcDefService")
@Transactional(readOnly = true)
public class SystemProcDefServiceImpl implements SystemProcDefService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SystemProcDefMapper systemProcDefMapper;

	public SystemProcDefServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			systemProcDefMapper.deleteSystemProcDefById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				systemProcDefMapper.deleteSystemProcDefById(id);
			}
		}
	}

	public int count(SystemProcDefQuery query) {
		return systemProcDefMapper.getSystemProcDefCount(query);
	}

	public List<SystemProcDef> list(SystemProcDefQuery query) {
		List<SystemProcDef> list = systemProcDefMapper.getSystemProcDefs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSystemProcDefCountByQueryCriteria(SystemProcDefQuery query) {
		return systemProcDefMapper.getSystemProcDefCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SystemProcDef> getSystemProcDefsByQueryCriteria(int start, int pageSize, SystemProcDefQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SystemProcDef> rows = sqlSessionTemplate.selectList("getSystemProcDefs", query, rowBounds);
		return rows;
	}

	/**
	 * 获取流程元素集合
	 */
	public Map<String, SystemProcDef> getSystemProcDefMap(String modelId) {
		SystemProcDefQuery query = new SystemProcDefQuery();
		query.setProcModelId(modelId);
		List<SystemProcDef> systemProcDefs = systemProcDefMapper.getSystemProcDefs(query);
		Map<String, SystemProcDef> systemProcDefMap = new HashMap<String, SystemProcDef>();
		for (SystemProcDef systemProcDef : systemProcDefs) {
			systemProcDefMap.put(systemProcDef.getEleResourceId(), systemProcDef);
		}
		return systemProcDefMap;
	}

	public SystemProcDef getSystemProcDef(String id) {
		if (id == null) {
			return null;
		}
		SystemProcDef systemProcDef = systemProcDefMapper.getSystemProcDefById(id);
		return systemProcDef;
	}

	@Transactional
	public void save(SystemProcDef systemProcDef) {
		if (systemProcDef.getId() == null) {
			systemProcDef.setId(UUID32.getUUID());
			// systemProcDef.setCreateDate(new Date());
			// systemProcDef.setDeleteFlag(0);
			systemProcDefMapper.insertSystemProcDef(systemProcDef);
		} else {
			systemProcDefMapper.updateSystemProcDef(systemProcDef);
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

	@javax.annotation.Resource(name = "com.glaf.model.mapper.SystemProcDefMapper")
	public void setSystemProcDefMapper(SystemProcDefMapper systemProcDefMapper) {
		this.systemProcDefMapper = systemProcDefMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Transactional
	@Override
	public void updateProcDefNameType(SystemProcDef systemProcDef) {
		// TODO Auto-generated method stub
		systemProcDefMapper.updateProcDefNameType(systemProcDef);
	}

}
