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
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.util.*;

import com.glaf.teim.mapper.*;
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

@Service("com.glaf.teim.service.eimBaseInfoService")
@Transactional(readOnly = true)
public class EimBaseInfoServiceImpl implements EimBaseInfoService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EimBaseInfoMapper eimBaseInfoMapper;

	public EimBaseInfoServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			eimBaseInfoMapper.deleteEimBaseInfoById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				eimBaseInfoMapper.deleteEimBaseInfoById(id);
			}
		}
	}

	public int count(EimBaseInfoQuery query) {
		return eimBaseInfoMapper.getEimBaseInfoCount(query);
	}

	public List<EimBaseInfo> list(EimBaseInfoQuery query) {
		List<EimBaseInfo> list = eimBaseInfoMapper.getEimBaseInfos(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEimBaseInfoCountByQueryCriteria(EimBaseInfoQuery query) {
		return eimBaseInfoMapper.getEimBaseInfoCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EimBaseInfo> getEimBaseInfosByQueryCriteria(int start, int pageSize, EimBaseInfoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EimBaseInfo> rows = sqlSessionTemplate.selectList("getEimBaseInfos", query, rowBounds);
		return rows;
	}

	public EimBaseInfo getEimBaseInfo(String id) {
		if (id == null) {
			return null;
		}
		EimBaseInfo eimBaseInfo = eimBaseInfoMapper.getEimBaseInfoById(id);
		return eimBaseInfo;
	}

	@Transactional
	public void save(EimBaseInfo eimBaseInfo) {
		if (StringUtils.isEmpty(eimBaseInfo.getId())) {
			eimBaseInfo.setId(UUID32.getUUID());
			eimBaseInfoMapper.insertEimBaseInfo(eimBaseInfo);
		} else {
			eimBaseInfoMapper.updateEimBaseInfo(eimBaseInfo);
		}
	}

	/**
	 * 获取所有应用信息
	 * 
	 * @return
	 */
	public List<Map> getAllEimBaseInfo() {
		List<Map> rows = eimBaseInfoMapper.getAllEimBaseInfo();
		return rows;
	}
	
	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimBaseInfoMapper")
	public void setEimBaseInfoMapper(EimBaseInfoMapper eimBaseInfoMapper) {
		this.eimBaseInfoMapper = eimBaseInfoMapper;
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
