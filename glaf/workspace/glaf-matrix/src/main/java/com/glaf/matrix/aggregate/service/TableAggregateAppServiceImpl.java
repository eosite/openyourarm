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

package com.glaf.matrix.aggregate.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.glaf.matrix.aggregate.domain.TableAggregateApp;
import com.glaf.matrix.aggregate.domain.TableAggregateItem;
import com.glaf.matrix.aggregate.mapper.TableAggregateAppMapper;
import com.glaf.matrix.aggregate.mapper.TableAggregateItemMapper;
import com.glaf.matrix.aggregate.query.TableAggregateAppQuery;
import com.glaf.matrix.aggregate.query.TableAggregateItemQuery;

@Service("com.glaf.matrix.aggregate.service.tableAggregateAppService")
@Transactional(readOnly = true)
public class TableAggregateAppServiceImpl implements TableAggregateAppService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableAggregateAppMapper tableAggregateAppMapper;

	protected TableAggregateItemMapper tableAggregateItemMapper;

	public TableAggregateAppServiceImpl() {

	}

	public int count(TableAggregateAppQuery query) {
		return tableAggregateAppMapper.getTableAggregateAppCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			tableAggregateAppMapper.deleteTableAggregateAppById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				tableAggregateAppMapper.deleteTableAggregateAppById(id);
			}
		}
	}

	public TableAggregateApp getTableAggregateApp(Long syncId) {
		if (syncId == null) {
			return null;
		}
		TableAggregateApp tableAggregateApp = tableAggregateAppMapper.getTableAggregateAppById(syncId);
		if (tableAggregateApp != null) {
			TableAggregateItemQuery query = new TableAggregateItemQuery();
			query.syncId(syncId);
			List<TableAggregateItem> items = tableAggregateItemMapper.getTableAggregateItems(query);
			tableAggregateApp.setItems(items);
		}
		return tableAggregateApp;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableAggregateAppCountByQueryCriteria(TableAggregateAppQuery query) {
		return tableAggregateAppMapper.getTableAggregateAppCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableAggregateApp> getTableAggregateAppsByQueryCriteria(int start, int pageSize, TableAggregateAppQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableAggregateApp> rows = sqlSessionTemplate.selectList("getTableAggregateApps", query, rowBounds);
		return rows;
	}

	public List<TableAggregateApp> list(TableAggregateAppQuery query) {
		List<TableAggregateApp> list = tableAggregateAppMapper.getTableAggregateApps(query);
		return list;
	}

	@Transactional
	public void save(TableAggregateApp tableAggregateApp) {
		if (tableAggregateApp.getId() == 0) {
			tableAggregateApp.setId(idGenerator.nextId("SYS_TABLE_AGGR_APP"));
			tableAggregateApp.setCreateTime(new Date());
			if (StringUtils.isEmpty(tableAggregateApp.getDeploymentId())) {
				tableAggregateApp.setDeploymentId(UUID32.getUUID());
			}
			tableAggregateAppMapper.insertTableAggregateApp(tableAggregateApp);
		} else {
			tableAggregateAppMapper.updateTableAggregateApp(tableAggregateApp);
		}
	}

	@Transactional
	public long saveAs(long syncId, String createBy) {
		TableAggregateApp model = this.getTableAggregateApp(syncId);
		if (model != null) {
			model.setId(idGenerator.nextId("SYS_TABLE_AGGR_APP"));
			model.setCreateTime(new Date());
			model.setCreateBy(createBy);
			tableAggregateAppMapper.insertTableAggregateApp(model);

			if (model.getItems() != null && !model.getItems().isEmpty()) {
				for (TableAggregateItem item : model.getItems()) {
					item.setId(idGenerator.nextId("SYS_TABLE_AGGR_ITEM"));
					item.setCreateTime(new Date());
					item.setCreateBy(createBy);
					item.setSyncId(model.getId());
					tableAggregateItemMapper.insertTableAggregateItem(item);
				}
			}

			return model.getId();
		}
		return -1;
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

	@javax.annotation.Resource(name = "com.glaf.matrix.aggregate.mapper.TableAggregateAppMapper")
	public void setTableAggregateAppMapper(TableAggregateAppMapper tableAggregateAppMapper) {
		this.tableAggregateAppMapper = tableAggregateAppMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.aggregate.mapper.TableAggregateItemMapper")
	public void setTableAggregateItemMapper(TableAggregateItemMapper tableAggregateItemMapper) {
		this.tableAggregateItemMapper = tableAggregateItemMapper;
	}

}
