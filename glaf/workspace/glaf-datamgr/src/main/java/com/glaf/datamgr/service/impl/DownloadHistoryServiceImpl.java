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

import com.glaf.datamgr.mapper.DownloadHistoryMapper;
import com.glaf.datamgr.domain.DownloadHistory;
import com.glaf.datamgr.query.DownloadHistoryQuery;
import com.glaf.datamgr.service.DownloadHistoryService;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("downloadHistoryService")
@Transactional(readOnly = true)
public class DownloadHistoryServiceImpl implements DownloadHistoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DownloadHistoryMapper downloadHistoryMapper;

	public DownloadHistoryServiceImpl() {

	}

	public int count(DownloadHistoryQuery query) {
		return downloadHistoryMapper.getDownloadHistoryCount(query);
	}

	public List<DownloadHistory> list(DownloadHistoryQuery query) {
		List<DownloadHistory> list = downloadHistoryMapper.getDownloadHistorys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDownloadHistoryCountByQueryCriteria(DownloadHistoryQuery query) {
		return downloadHistoryMapper.getDownloadHistoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DownloadHistory> getDownloadHistorysByQueryCriteria(int start, int pageSize,
			DownloadHistoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DownloadHistory> rows = sqlSessionTemplate.selectList("getDownloadHistorys", query, rowBounds);
		return rows;
	}

	public DownloadHistory getDownloadHistory(String id) {
		if (id == null) {
			return null;
		}
		DownloadHistory downloadHistory = downloadHistoryMapper.getDownloadHistoryById(id);
		return downloadHistory;
	}

	@Transactional
	public void save(DownloadHistory downloadHistory) {
		if (StringUtils.isEmpty(downloadHistory.getId())) {
			downloadHistory.setId(idGenerator.getNextId("DOWNLOAD_HISTORY"));
			downloadHistory.setDownloadTime(new Date());
			downloadHistoryMapper.insertDownloadHistory(downloadHistory);
		}
	}

	@Transactional
	public void updateDownloadHistory(DownloadHistory model) {
		downloadHistoryMapper.updateDownloadHistory(model);
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
	public void setDownloadHistoryMapper(DownloadHistoryMapper downloadHistoryMapper) {
		this.downloadHistoryMapper = downloadHistoryMapper;
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
