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

package com.glaf.base.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.base.modules.sys.model.CellTreedotView;
import com.glaf.base.modules.sys.mapper.MyCellTreedotViewMapper;
import com.glaf.base.modules.sys.query.CellTreedotViewQuery;
import com.glaf.base.modules.sys.service.ICellTreedotViewService;

@Service("cellTreedotViewService")
@Transactional(readOnly = true)
public class CellTreedotViewServiceImpl implements ICellTreedotViewService {
	protected static final Log logger = LogFactory
			.getLog(CellTreedotViewServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MyCellTreedotViewMapper myCellTreedotViewMapper;

	public CellTreedotViewServiceImpl() {

	}

	public int count(CellTreedotViewQuery query) {
		
		return myCellTreedotViewMapper
				.getCellTreedotViewCountByQueryCriteria(query);
	}

	@Transactional
	public void deleteById(String id) {
		myCellTreedotViewMapper.deleteCellTreedotViewById(id);
	}

	public CellTreedotView getCellTreedotView(String id) {
		CellTreedotView cellTreedotView = myCellTreedotViewMapper
				.getCellTreedotViewById(id);
		return cellTreedotView;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellTreedotViewCount(Map<String, Object> parameter) {
		return myCellTreedotViewMapper.getCellTreedotViewCount(parameter);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellTreedotViewCountByQueryCriteria(CellTreedotViewQuery query) {
		return myCellTreedotViewMapper
				.getCellTreedotViewCountByQueryCriteria(query);
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<CellTreedotView> getCellTreedotViews(
			Map<String, Object> parameter) {
		return myCellTreedotViewMapper.getCellTreedotViews(parameter);
	}

	public List<CellTreedotView> getCellTreedotViews(int indexId) {
		CellTreedotViewQuery query = new CellTreedotViewQuery();
		query.indexId(indexId);
		return this.list(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellTreedotView> getCellTreedotViewsByQueryCriteria(int start,
			int pageSize, CellTreedotViewQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellTreedotView> rows = sqlSession.selectList(
				"getCellTreedotViewsByQueryCriteria", query, rowBounds);
		return rows;
	}

	public List<CellTreedotView> list(CellTreedotViewQuery query) {
		
		List<CellTreedotView> list = myCellTreedotViewMapper
				.getCellTreedotViewsByQueryCriteria(query);
		return list;
	}

	@Transactional
	public void save(CellTreedotView cellTreedotView) {
		if (StringUtils.isEmpty(cellTreedotView.getId())) {
			cellTreedotView.setId(idGenerator.getNextId());
			// cellTreedotView.setId(idGenerator.getNextId());
			// cellTreedotView.setCreateDate(new Date());
			myCellTreedotViewMapper.insertCellTreedotView(cellTreedotView);
		} else {
			CellTreedotView model = this.getCellTreedotView(cellTreedotView
					.getId());
			if (model != null) {
				if (cellTreedotView.getTablename() != null) {
					model.setTablename(cellTreedotView.getTablename());
				}
				if (cellTreedotView.getTablenameS() != null) {
					model.setTablenameS(cellTreedotView.getTablenameS());
				}
				if (cellTreedotView.getAlldname() != null) {
					model.setAlldname(cellTreedotView.getAlldname());
				}
				if (cellTreedotView.getAllfname() != null) {
					model.setAllfname(cellTreedotView.getAllfname());
				}
				model.setIndexId(cellTreedotView.getIndexId());
				model.setListno(cellTreedotView.getListno());
				model.setIntrad(cellTreedotView.getIntrad());
				model.setIntwbslevel(cellTreedotView.getIntwbslevel());
				myCellTreedotViewMapper.updateCellTreedotView(model);
			}
		}
	}

	@javax.annotation.Resource 
	public void setMyCellTreedotViewMapper(
			MyCellTreedotViewMapper myCellTreedotViewMapper) {
		this.myCellTreedotViewMapper = myCellTreedotViewMapper;
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
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}
