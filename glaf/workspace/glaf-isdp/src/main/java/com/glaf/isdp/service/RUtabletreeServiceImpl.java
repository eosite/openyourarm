package com.glaf.isdp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.CollectionUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.isdp.domain.RUtabletree;
import com.glaf.isdp.domain.RdataTable;
import com.glaf.isdp.mapper.RUtabletreeMapper;
import com.glaf.isdp.query.RUtabletreeQuery;
import com.glaf.isdp.query.RdataTableQuery;

@Service("com.glaf.isdp.service.rUtabletreeService")
@Transactional(readOnly = true)
public class RUtabletreeServiceImpl implements RUtabletreeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected RUtabletreeMapper rUtabletreeMapper;

	@Autowired
	protected RdataTableService rdataTableService;

	public RUtabletreeServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<RUtabletree> list) {
		for (RUtabletree rUtabletree : list) {
			if (rUtabletree.getIndexId() == null) {
				rUtabletree.setIndexId(idGenerator.nextId("R_UTABLETREE").intValue());
				if (StringUtils.isEmpty(rUtabletree.getId())) {
					rUtabletree.setId(rUtabletree.getIndexId() + "|");
				}
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// rUtabletreeMapper.bulkInsertRUtabletree_oracle(list);
		} else {
			// rUtabletreeMapper.bulkInsertRUtabletree(list);
		}
	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			rUtabletreeMapper.deleteRUtabletreeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
		if (indexIds != null && !indexIds.isEmpty()) {
			for (Integer id : indexIds) {
				rUtabletreeMapper.deleteRUtabletreeById(id);
			}
		}
	}

	public int count(RUtabletreeQuery query) {
		return rUtabletreeMapper.getRUtabletreeCount(query);
	}

	public List<RUtabletree> list(RUtabletreeQuery query) {
		List<RUtabletree> list = rUtabletreeMapper.getRUtabletrees(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getRUtabletreeCountByQueryCriteria(RUtabletreeQuery query) {
		return rUtabletreeMapper.getRUtabletreeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<RUtabletree> getRUtabletreesByQueryCriteria(int start, int pageSize, RUtabletreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<RUtabletree> rows = sqlSessionTemplate.selectList("getRUtabletrees", query, rowBounds);
		return rows;
	}

	public RUtabletree getRUtabletree(Integer id) {
		if (id == null) {
			return null;
		}
		RUtabletree rUtabletree = rUtabletreeMapper.getRUtabletreeById(id);
		return rUtabletree;
	}

	@Transactional
	public void save(RUtabletree rUtabletree) {
		if (rUtabletree.getIndexId() == null) {
			rUtabletree.setIndexId(idGenerator.nextId("R_UTABLETREE").intValue() + 1000000);
			if (StringUtils.isEmpty(rUtabletree.getId())) {
				rUtabletree.setId(rUtabletree.getIndexId() + "|");
			}
			rUtabletreeMapper.insertRUtabletree(rUtabletree);
		} else {
			rUtabletreeMapper.updateRUtabletree(rUtabletree);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.RUtabletreeMapper")
	public void setRUtabletreeMapper(RUtabletreeMapper rUtabletreeMapper) {
		this.rUtabletreeMapper = rUtabletreeMapper;
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
	public JSONArray getTreeAndChildrenByType(Integer tableType) {

		JSONArray jsonArray = new JSONArray();

		RUtabletreeQuery query = new RUtabletreeQuery();
		query.setTabletype(tableType);
		List<RUtabletree> list = this.list(query);
		if (CollectionUtils.isNotEmpty(list)) {

			JSONObject json, json0;
			Map<Integer, JSONObject> map = new HashMap<Integer, JSONObject>(list.size());
			for (RUtabletree tree : list) {
				json = tree.toJsonObject();
				json.put("R", true);
				map.put(tree.getIndexId(), json);
				jsonArray.add(json);
			}
			RdataTableQuery query0 = new RdataTableQuery();
			List<RdataTable> list0 = rdataTableService.list(query0);
			String childrenKey = "children";
			if (CollectionUtils.isNotEmpty(list)) {
				for (RdataTable dt : list0) {
					if (dt.getIndexId() != null && ((json = map.get(dt.getIndexId())) != null)) {
						json0 = dt.toJsonObject();

						json0.put("tableId", dt.getId());
						json0.put("tableName", dt.getTablename());
						json0.put("tableNameCN", dt.getName());
						json0.put("isSubTable", dt.getIssubtable());
						json0.put("indexName", dt.getName());
						json0.put("R", true);

						if (!json.containsKey(childrenKey)) {
							json.put(childrenKey, new JSONArray());
						}
						json.getJSONArray(childrenKey).add(json0);
					}
				}
			}
		}

		return jsonArray;
	}

}
