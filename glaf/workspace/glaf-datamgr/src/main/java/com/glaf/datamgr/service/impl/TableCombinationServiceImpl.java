/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.datamgr.service.impl;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;

@Service("tableCombinationService")
@Transactional(readOnly = true)
public class TableCombinationServiceImpl implements TableCombinationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableCombinationMapper tableCombinationMapper;

	protected TableCombinationColumnMapper tableCombinationColumnMapper;

	protected TableCombinationRuleMapper tableCombinationRuleMapper;

	public TableCombinationServiceImpl() {

	}

	public int count(TableCombinationQuery query) {
		return tableCombinationMapper.getTableCombinationCount(query);
	}

	@Transactional
	public void deleteById(long combinationId) {
		if (combinationId != 0) {
			tableCombinationColumnMapper.deleteTableCombinationColumns(combinationId);
			tableCombinationRuleMapper.deleteTableCombinationRules(combinationId);
			tableCombinationMapper.deleteTableCombinationById(combinationId);
		}
	}

	public TableCombination getTableCombination(long combinationId) {
		if (combinationId == 0) {
			return null;
		}
		TableCombination tableCombination = tableCombinationMapper.getTableCombinationById(combinationId);
		if (tableCombination != null) {
			List<TableCombinationColumn> columns = tableCombinationColumnMapper
					.getTableCombinationColumns(combinationId);
			List<TableCombinationRule> rules = tableCombinationRuleMapper.getTableCombinationRules(combinationId);
			tableCombination.setColumns(columns);
			tableCombination.setRules(rules);
		}
		return tableCombination;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableCombinationCountByQueryCriteria(TableCombinationQuery query) {
		return tableCombinationMapper.getTableCombinationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableCombination> getTableCombinationsByQueryCriteria(int start, int pageSize,
			TableCombinationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableCombination> rows = sqlSessionTemplate.selectList("getTableCombinations", query, rowBounds);
		return rows;
	}

	public List<TableCombination> list(TableCombinationQuery query) {
		List<TableCombination> list = tableCombinationMapper.getTableCombinations(query);
		return list;
	}

	@Transactional
	public void resetAllTableCombinationStatus() {
		tableCombinationMapper.resetAllTableCombinationStatus();
	}

	@Transactional
	public void save(TableCombination tableCombination) {
		if (tableCombination.getId() == 0) {
			tableCombination.setId(idGenerator.nextId("TABLE_COMBINATION"));
			tableCombination.setCreateTime(new Date());
			tableCombinationMapper.insertTableCombination(tableCombination);
		} else {
			tableCombinationMapper.updateTableCombination(tableCombination);
		}
	}

	@Transactional
	public void saveAs(TableCombination tableCombination) {
		long combinationId = tableCombination.getId();
		List<TableCombinationColumn> columns = tableCombinationColumnMapper.getTableCombinationColumns(combinationId);
		List<TableCombinationRule> rules = tableCombinationRuleMapper.getTableCombinationRules(combinationId);
		tableCombination.setColumns(columns);
		tableCombination.setRules(rules);
		tableCombination.setId(idGenerator.nextId("TABLE_COMBINATION"));
		tableCombination.setCreateTime(new Date());
		tableCombinationMapper.insertTableCombination(tableCombination);

		if (rules != null && !rules.isEmpty()) {
			for (TableCombinationRule rule : rules) {
				rule.setId(idGenerator.nextId("TABLE_COMBINATION_RULE"));
				rule.setCombinationId(tableCombination.getId());
				tableCombinationRuleMapper.insertTableCombinationRule(rule);
			}
		}

		if (columns != null && !columns.isEmpty()) {
			for (TableCombinationColumn column : columns) {
				if (StringUtils.isNotEmpty(column.getSourceColumnName())) {
					column.setId(idGenerator.nextId("TABLE_COMBINATION_COLUMN"));
					column.setCombinationId(tableCombination.getId());
					tableCombinationColumnMapper.insertTableCombinationColumn(column);
				}
			}
		}
	}

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	public void saveRules(TableCombination tableCombination) {
		long combinationId = tableCombination.getId();
		tableCombinationColumnMapper.deleteTableCombinationColumns(combinationId);
		tableCombinationRuleMapper.deleteTableCombinationRules(combinationId);

		if (tableCombination.getRules() != null && !tableCombination.getRules().isEmpty()) {
			List<TableCombinationRule> rules = tableCombination.getRules();
			for (TableCombinationRule rule : rules) {
				rule.setId(idGenerator.nextId("TABLE_COMBINATION_RULE"));
				rule.setCombinationId(tableCombination.getId());
				tableCombinationRuleMapper.insertTableCombinationRule(rule);
			}
		}

		if (tableCombination.getColumns() != null && !tableCombination.getColumns().isEmpty()) {
			List<TableCombinationColumn> columns = tableCombination.getColumns();
			for (TableCombinationColumn column : columns) {
				if (StringUtils.isNotEmpty(column.getSourceColumnName())) {
					column.setId(idGenerator.nextId("TABLE_COMBINATION_COLUMN"));
					column.setCombinationId(tableCombination.getId());
					tableCombinationColumnMapper.insertTableCombinationColumn(column);
				}
			}
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

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setTableCombinationColumnMapper(TableCombinationColumnMapper tableCombinationColumnMapper) {
		this.tableCombinationColumnMapper = tableCombinationColumnMapper;
	}

	@javax.annotation.Resource
	public void setTableCombinationMapper(TableCombinationMapper tableCombinationMapper) {
		this.tableCombinationMapper = tableCombinationMapper;
	}

	@javax.annotation.Resource
	public void setTableCombinationRuleMapper(TableCombinationRuleMapper tableCombinationRuleMapper) {
		this.tableCombinationRuleMapper = tableCombinationRuleMapper;
	}

	@Transactional
	public void updateTableCombinationStatus(TableCombination tableCombination) {
		tableCombination.setSyncTime(new java.util.Date());
		tableCombinationMapper.updateTableCombinationStatus(tableCombination);
	}

}
