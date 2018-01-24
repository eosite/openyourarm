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

@Service("com.glaf.teim.service.eimServerCategoryService")
@Transactional(readOnly = true)
public class EimServerCategoryServiceImpl implements EimServerCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EimServerCategoryMapper eimServerCategoryMapper;

	public EimServerCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			eimServerCategoryMapper.deleteEimServerCategoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				eimServerCategoryMapper.deleteEimServerCategoryById(id);
			}
		}
	}

	public int count(EimServerCategoryQuery query) {
		return eimServerCategoryMapper.getEimServerCategoryCount(query);
	}

	public List<EimServerCategory> list(EimServerCategoryQuery query) {
		List<EimServerCategory> list = eimServerCategoryMapper.getEimServerCategorys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEimServerCategoryCountByQueryCriteria(EimServerCategoryQuery query) {
		return eimServerCategoryMapper.getEimServerCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EimServerCategory> getEimServerCategorysByQueryCriteria(int start, int pageSize,
			EimServerCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EimServerCategory> rows = sqlSessionTemplate.selectList("getEimServerCategorys", query, rowBounds);
		return rows;
	}

	public EimServerCategory getEimServerCategory(Long id) {
		if (id == null) {
			return null;
		}
		EimServerCategory eimServerCategory = eimServerCategoryMapper.getEimServerCategoryById(id);
		return eimServerCategory;
	}

	@Transactional
	public void save(EimServerCategory eimServerCategory) {
		if (eimServerCategory.getId() == null) {
			eimServerCategory.setId(idGenerator.nextId("EIM_SERVER_CATEGORY"));
			if (eimServerCategory.getTreeId() != null) {
				eimServerCategory.setTreeId(eimServerCategory.getTreeId() + eimServerCategory.getId() + "|");
			} else {
				eimServerCategory.setTreeId(eimServerCategory.getId() + "|");
			}
			// eimServerCategory.setCreateDate(new Date());
			// eimServerCategory.setDeleteFlag(0);
			eimServerCategoryMapper.insertEimServerCategory(eimServerCategory);
		} else {
			eimServerCategory.setTreeId(eimServerCategory.getTreeId() + eimServerCategory.getId() + "|");
			eimServerCategoryMapper.updateEimServerCategory(eimServerCategory);
		}
	}

	@Override
	@Transactional
	public void move(String moveType, Long categoryId, Long pId, String treeId, String actorId, Date modifyDatatime) {
		// TODO Auto-generated method stub
		try {
			if (moveType.equals("inner"))
				eimServerCategoryMapper.move(categoryId, pId, treeId, actorId, modifyDatatime);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void rename(Long categoryId, String name, String actorId, Date modifyDatatime) {
		// TODO Auto-generated method stub
		try {
			eimServerCategoryMapper.rename(categoryId, name, actorId, modifyDatatime);
		} catch (Exception e) {
			throw e;
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

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimServerCategoryMapper")
	public void setEimServerCategoryMapper(EimServerCategoryMapper eimServerCategoryMapper) {
		this.eimServerCategoryMapper = eimServerCategoryMapper;
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
