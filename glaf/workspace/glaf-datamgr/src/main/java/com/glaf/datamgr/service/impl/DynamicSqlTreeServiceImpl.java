package com.glaf.datamgr.service.impl;

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

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.DynamicSqlTreeService;

@Service("com.glaf.datamgr.service.dynamicSqlTreeService")
@Transactional(readOnly = true)
public class DynamicSqlTreeServiceImpl implements DynamicSqlTreeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DynamicSqlTreeMapper dynamicSqlTreeMapper;

	public DynamicSqlTreeServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DynamicSqlTree> list) {
		for (DynamicSqlTree dynamicSqlTree : list) {
			if (dynamicSqlTree.getId() == 0) {
				dynamicSqlTree.setId(idGenerator.nextId("SYS_DYNAMIC_SQL_TREE"));
				dynamicSqlTree.setCreateTime(new Date());
			}
		}

		int batch_size = 200;
		List<DynamicSqlTree> rows = new ArrayList<DynamicSqlTree>(batch_size);

		for (DynamicSqlTree bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % 100 == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					dynamicSqlTreeMapper.bulkInsertDynamicSqlTree_oracle(rows);
				} else {
					dynamicSqlTreeMapper.bulkInsertDynamicSqlTree(rows);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				dynamicSqlTreeMapper.bulkInsertDynamicSqlTree_oracle(rows);
			} else {
				dynamicSqlTreeMapper.bulkInsertDynamicSqlTree(rows);
			}
			rows.clear();
		}
	}

	public int count(DynamicSqlTreeQuery query) {
		return dynamicSqlTreeMapper.getDynamicSqlTreeCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			dynamicSqlTreeMapper.deleteDynamicSqlTreeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				dynamicSqlTreeMapper.deleteDynamicSqlTreeById(id);
			}
		}
	}

	public DynamicSqlTree getDynamicSqlTree(Long id) {
		if (id == null) {
			return null;
		}
		DynamicSqlTree dynamicSqlTree = dynamicSqlTreeMapper.getDynamicSqlTreeById(id);
		return dynamicSqlTree;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDynamicSqlTreeCountByQueryCriteria(DynamicSqlTreeQuery query) {
		return dynamicSqlTreeMapper.getDynamicSqlTreeCount(query);
	}

	public List<DynamicSqlTree> getDynamicSqlTreesByTableName(String tableName) {
		DynamicSqlTreeQuery query = new DynamicSqlTreeQuery();
		query.setTableName(tableName);
		return this.list(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DynamicSqlTree> getDynamicSqlTreesByQueryCriteria(int start, int pageSize, DynamicSqlTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DynamicSqlTree> rows = sqlSessionTemplate.selectList("getDynamicSqlTrees", query, rowBounds);
		return rows;
	}

	public List<DynamicSqlTree> list(DynamicSqlTreeQuery query) {
		List<DynamicSqlTree> list = dynamicSqlTreeMapper.getDynamicSqlTrees(query);
		return list;
	}

	@Transactional
	public void save(DynamicSqlTree dynamicSqlTree) {
		if (dynamicSqlTree.getId() == 0) {
			dynamicSqlTree.setId(idGenerator.nextId("SYS_DYNAMIC_SQL_TREE"));
			dynamicSqlTree.setCreateTime(new Date());
			long parentId = dynamicSqlTree.getParentId();
			if (parentId > 0) {
				DynamicSqlTree parent = this.getDynamicSqlTree(parentId);
				if (parent != null) {
					dynamicSqlTree.setLevel(parent.getLevel() + 1);
					if (parent.getTreeId() != null) {
						dynamicSqlTree.setTreeId(parent.getTreeId() + dynamicSqlTree.getId() + "|");
					}
				}
			} else {
				dynamicSqlTree.setLevel(0);
				dynamicSqlTree.setTreeId(dynamicSqlTree.getId() + "|");
			}
			dynamicSqlTreeMapper.insertDynamicSqlTree(dynamicSqlTree);
		} else {
			long parentId = dynamicSqlTree.getParentId();
			if (parentId > 0 && parentId != dynamicSqlTree.getId()) {
				DynamicSqlTree parent = this.getDynamicSqlTree(parentId);
				if (parent != null) {
					dynamicSqlTree.setLevel(parent.getLevel() + 1);
					if (parent.getTreeId() != null) {
						dynamicSqlTree.setTreeId(parent.getTreeId() + dynamicSqlTree.getId() + "|");
					}
				}
			} else {
				dynamicSqlTree.setLevel(0);
				dynamicSqlTree.setTreeId(dynamicSqlTree.getId() + "|");
			}
			dynamicSqlTree.setUpdateTime(new Date());
			dynamicSqlTreeMapper.updateDynamicSqlTree(dynamicSqlTree);
		}
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.DynamicSqlTreeMapper")
	public void setDynamicSqlTreeMapper(DynamicSqlTreeMapper dynamicSqlTreeMapper) {
		this.dynamicSqlTreeMapper = dynamicSqlTreeMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
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
