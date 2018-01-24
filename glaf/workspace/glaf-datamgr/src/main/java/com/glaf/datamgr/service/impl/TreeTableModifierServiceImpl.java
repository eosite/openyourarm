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
import com.glaf.datamgr.service.TreeTableModifierService;

@Service("com.glaf.datamgr.service.treeTableModifierService")
@Transactional(readOnly = true)
public class TreeTableModifierServiceImpl implements TreeTableModifierService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TreeTableModifierMapper treeTableModifierMapper;

	public TreeTableModifierServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<TreeTableModifier> list) {
		for (TreeTableModifier treeTableModifier : list) {
			if (treeTableModifier.getId() == 0) {
				treeTableModifier.setId(idGenerator.nextId("SYS_TREETABLE_MODIFIER"));
				treeTableModifier.setCreateTime(new Date());
			}
		}

		int batch_size = 100;
		List<TreeTableModifier> rows = new ArrayList<TreeTableModifier>(batch_size);

		for (TreeTableModifier bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					treeTableModifierMapper.bulkInsertTreeTableModifier_oracle(rows);
				} else {
					treeTableModifierMapper.bulkInsertTreeTableModifier(rows);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				treeTableModifierMapper.bulkInsertTreeTableModifier_oracle(rows);
			} else {
				treeTableModifierMapper.bulkInsertTreeTableModifier(rows);
			}
			rows.clear();
		}
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			treeTableModifierMapper.deleteTreeTableModifierById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				treeTableModifierMapper.deleteTreeTableModifierById(id);
			}
		}
	}

	public int count(TreeTableModifierQuery query) {
		return treeTableModifierMapper.getTreeTableModifierCount(query);
	}

	public List<TreeTableModifier> list(TreeTableModifierQuery query) {
		List<TreeTableModifier> list = treeTableModifierMapper.getTreeTableModifiers(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTreeTableModifierCountByQueryCriteria(TreeTableModifierQuery query) {
		return treeTableModifierMapper.getTreeTableModifierCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TreeTableModifier> getTreeTableModifiersByQueryCriteria(int start, int pageSize,
			TreeTableModifierQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TreeTableModifier> rows = sqlSessionTemplate.selectList("getTreeTableModifiers", query, rowBounds);
		return rows;
	}

	public TreeTableModifier getTreeTableModifier(Long id) {
		if (id == null) {
			return null;
		}
		TreeTableModifier treeTableModifier = treeTableModifierMapper.getTreeTableModifierById(id);
		return treeTableModifier;
	}

	@Transactional
	public void save(TreeTableModifier treeTableModifier) {
		if (treeTableModifier.getId() == 0) {
			treeTableModifier.setId(idGenerator.nextId("SYS_TREETABLE_MODIFIER"));
			 treeTableModifier.setCreateTime(new Date());
			// treeTableModifier.setDeleteFlag(0);
			treeTableModifierMapper.insertTreeTableModifier(treeTableModifier);
		} else {
			treeTableModifierMapper.updateTreeTableModifier(treeTableModifier);
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

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.TreeTableModifierMapper")
	public void setTreeTableModifierMapper(TreeTableModifierMapper treeTableModifierMapper) {
		this.treeTableModifierMapper = treeTableModifierMapper;
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
