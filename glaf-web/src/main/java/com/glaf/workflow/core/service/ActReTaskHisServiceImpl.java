package com.glaf.workflow.core.service;

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

import com.glaf.workflow.core.mapper.*;
import com.glaf.workflow.core.domain.*;
import com.glaf.workflow.core.query.*;

@Service("com.glaf.workflow.core.service.actReTaskHisService")
@Transactional(readOnly = true)
public class ActReTaskHisServiceImpl implements ActReTaskHisService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ActReTaskHisMapper actReTaskHisMapper;

	public ActReTaskHisServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<ActReTaskHis> list) {
		for (ActReTaskHis actReTaskHis : list) {
			if (actReTaskHis.getId() == null) {
				actReTaskHis.setId(idGenerator.nextId("ACT_RE_TASK_HIS").intValue());
			}
		}

		int batch_size = 100;
		List<ActReTaskHis> rows = new ArrayList<ActReTaskHis>(batch_size);

		for (ActReTaskHis bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					// actReTaskHisMapper.bulkInsertActReTaskHis_oracle(list);
				} else {
					// actReTaskHisMapper.bulkInsertActReTaskHis(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				// actReTaskHisMapper.bulkInsertActReTaskHis_oracle(list);
			} else {
				// actReTaskHisMapper.bulkInsertActReTaskHis(list);
			}
			rows.clear();
		}
	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			actReTaskHisMapper.deleteActReTaskHisById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Integer id : ids) {
				actReTaskHisMapper.deleteActReTaskHisById(id);
			}
		}
	}

	public int count(ActReTaskHisQuery query) {
		return actReTaskHisMapper.getActReTaskHisCount(query);
	}

	public List<ActReTaskHis> list(ActReTaskHisQuery query) {
		List<ActReTaskHis> list = actReTaskHisMapper.getActReTaskHiss(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getActReTaskHisCountByQueryCriteria(ActReTaskHisQuery query) {
		return actReTaskHisMapper.getActReTaskHisCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ActReTaskHis> getActReTaskHissByQueryCriteria(int start, int pageSize, ActReTaskHisQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ActReTaskHis> rows = sqlSessionTemplate.selectList("getActReTaskHiss", query, rowBounds);
		return rows;
	}

	public ActReTaskHis getActReTaskHis(Integer id) {
		if (id == null) {
			return null;
		}
		ActReTaskHis actReTaskHis = actReTaskHisMapper.getActReTaskHisById(id);
		return actReTaskHis;
	}

	@Transactional
	public void save(ActReTaskHis actReTaskHis) {
		if (actReTaskHis.getId() == null) {
			actReTaskHis.setId(idGenerator.nextId("ACT_RE_TASK_HIS").intValue());
			// actReTaskHis.setCreateDate(new Date());
			// actReTaskHis.setDeleteFlag(0);
			actReTaskHisMapper.insertActReTaskHis(actReTaskHis);
		} else {
			actReTaskHisMapper.updateActReTaskHis(actReTaskHis);
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

	@javax.annotation.Resource(name = "com.glaf.workflow.core.mapper.ActReTaskHisMapper")
	public void setActReTaskHisMapper(ActReTaskHisMapper actReTaskHisMapper) {
		this.actReTaskHisMapper = actReTaskHisMapper;
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
