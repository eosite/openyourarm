package com.glaf.chinaiss.data.service;

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

import com.glaf.chinaiss.data.mapper.*;
import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;

@Service("com.glaf.chinaiss.data.service.dataModelTreeService")
@Transactional(readOnly = true)
public class DataModelTreeServiceImpl implements DataModelTreeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataModelTreeMapper dataModelTreeMapper;

	public DataModelTreeServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataModelTree> list) {
		for (DataModelTree dataModelTree : list) {
			if (StringUtils.isEmpty(dataModelTree.getId())) {
				dataModelTree.setId("TREE_" + idGenerator.getNextId("DATA_MODEL_TREE"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// dataModelTreeMapper.bulkInsertDataModelTree_oracle(list);
		} else {
			// dataModelTreeMapper.bulkInsertDataModelTree(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			dataModelTreeMapper.deleteDataModelTreeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				dataModelTreeMapper.deleteDataModelTreeById(id);
			}
		}
	}

	public int count(DataModelTreeQuery query) {
		return dataModelTreeMapper.getDataModelTreeCount(query);
	}

	public List<DataModelTree> list(DataModelTreeQuery query) {
		List<DataModelTree> list = dataModelTreeMapper.getDataModelTrees(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataModelTreeCountByQueryCriteria(DataModelTreeQuery query) {
		return dataModelTreeMapper.getDataModelTreeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataModelTree> getDataModelTreesByQueryCriteria(int start, int pageSize, DataModelTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataModelTree> rows = sqlSessionTemplate.selectList("getDataModelTrees", query, rowBounds);
		return rows;
	}

	public DataModelTree getDataModelTree(String id) {
		if (id == null) {
			return null;
		}
		DataModelTree dataModelTree = dataModelTreeMapper.getDataModelTreeById(id);
		return dataModelTree;
	}

	@Transactional
	public void save(DataModelTree dataModelTree) {
		if (StringUtils.isEmpty(dataModelTree.getId())) {
			dataModelTree.setId("TREE_" + idGenerator.getNextId("DATA_MODEL_TREE"));
			dataModelTree.setCreateDate(new Date());
			String treeId = dataModelTree.getTreeId();
			if (StringUtils.isBlank(treeId)) {
				treeId = "";
			}
			dataModelTree.setTreeId(treeId + dataModelTree.getId() + "|");
			dataModelTreeMapper.insertDataModelTree(dataModelTree);
		} else {
			dataModelTree.setUpdateDate(new Date());
			dataModelTreeMapper.updateDataModelTree(dataModelTree);
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

	@javax.annotation.Resource(name = "com.glaf.chinaiss.data.mapper.DataModelTreeMapper")
	public void setDataModelTreeMapper(DataModelTreeMapper dataModelTreeMapper) {
		this.dataModelTreeMapper = dataModelTreeMapper;
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
