package com.glaf.teim.service;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.teim.mapper.*;
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

@Service("com.glaf.teim.service.eimServerDataImpService")
@Transactional(readOnly = true)
public class EimServerDataImpServiceImpl implements EimServerDataImpService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EimServerDataImpMapper eimServerDataImpMapper;

	public EimServerDataImpServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			eimServerDataImpMapper.deleteEimServerDataImpById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				eimServerDataImpMapper.deleteEimServerDataImpById(id);
			}
		}
	}

	public int count(EimServerDataImpQuery query) {
		return eimServerDataImpMapper.getEimServerDataImpCount(query);
	}

	public List<EimServerDataImp> list(EimServerDataImpQuery query) {
		List<EimServerDataImp> list = eimServerDataImpMapper.getEimServerDataImps(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEimServerDataImpCountByQueryCriteria(EimServerDataImpQuery query) {
		return eimServerDataImpMapper.getEimServerDataImpCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EimServerDataImp> getEimServerDataImpsByQueryCriteria(int start, int pageSize,
			EimServerDataImpQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EimServerDataImp> rows = sqlSessionTemplate.selectList("getEimServerDataImps", query, rowBounds);
		return rows;
	}

	public EimServerDataImp getEimServerDataImp(String id) {
		if (id == null) {
			return null;
		}
		EimServerDataImp eimServerDataImp = eimServerDataImpMapper.getEimServerDataImpById(id);
		return eimServerDataImp;
	}

	@Transactional
	public void save(EimServerDataImp eimServerDataImp) {
		if (StringUtils.isEmpty(eimServerDataImp.getId())) {
			eimServerDataImp.setId(idGenerator.getNextId("EIM_SERVER_DATA_IMP"));
			// eimServerDataImp.setCreateDate(new Date());
			// eimServerDataImp.setDeleteFlag(0);
			eimServerDataImpMapper.insertEimServerDataImp(eimServerDataImp);
		} else {
			eimServerDataImpMapper.updateEimServerDataImp(eimServerDataImp);
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

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimServerDataImpMapper")
	public void setEimServerDataImpMapper(EimServerDataImpMapper eimServerDataImpMapper) {
		this.eimServerDataImpMapper = eimServerDataImpMapper;
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
	public List<Map> getEimServerDataImpData() {
		// TODO Auto-generated method stub
		return eimServerDataImpMapper.getEimServerDataImpData();
	}

}
