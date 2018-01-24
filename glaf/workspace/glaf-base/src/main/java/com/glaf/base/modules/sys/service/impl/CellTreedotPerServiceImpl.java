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
import com.glaf.base.modules.sys.model.CellTreedotPer;
import com.glaf.base.modules.sys.mapper.MyCellTreedotPerMapper;
import com.glaf.base.modules.sys.query.CellTreedotPerQuery;
import com.glaf.base.modules.sys.service.ICellTreedotPerService;

@Service("cellTreedotPerService")
@Transactional(readOnly = true)
public class CellTreedotPerServiceImpl implements ICellTreedotPerService {
	protected static final Log logger = LogFactory
			.getLog(CellTreedotPerServiceImpl.class);

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSession sqlSession;

	protected MyCellTreedotPerMapper myCellTreedotPerMapper;

	public CellTreedotPerServiceImpl() {

	}

	public int count(CellTreedotPerQuery query) {
		
		return myCellTreedotPerMapper
				.getCellTreedotPerCountByQueryCriteria(query);
	}

	@Transactional
	public void deleteById(String id) {
		myCellTreedotPerMapper.deleteCellTreedotPerById(id);
	}

	public CellTreedotPer getCellTreedotPer(String id) {
		CellTreedotPer cellTreedotPer = myCellTreedotPerMapper
				.getCellTreedotPerById(id);
		return cellTreedotPer;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellTreedotPerCount(Map<String, Object> parameter) {
		return myCellTreedotPerMapper.getCellTreedotPerCount(parameter);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellTreedotPerCountByQueryCriteria(CellTreedotPerQuery query) {
		return myCellTreedotPerMapper
				.getCellTreedotPerCountByQueryCriteria(query);
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<CellTreedotPer> getCellTreedotPers(Map<String, Object> parameter) {
		return myCellTreedotPerMapper.getCellTreedotPers(parameter);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellTreedotPer> getCellTreedotPersByQueryCriteria(int start,
			int pageSize, CellTreedotPerQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellTreedotPer> rows = sqlSession.selectList(
				"getCellTreedotPersByQueryCriteria", query, rowBounds);
		return rows;
	}

	public List<CellTreedotPer> list(CellTreedotPerQuery query) {
		
		List<CellTreedotPer> list = myCellTreedotPerMapper
				.getCellTreedotPersByQueryCriteria(query);
		return list;
	}

	@Transactional
	public void save(CellTreedotPer cellTreedotPer) {
		if (StringUtils.isEmpty(cellTreedotPer.getId())) {
			cellTreedotPer.setId(idGenerator.getNextId());
			// cellTreedotPer.setId(idGenerator.getNextId());
			// cellTreedotPer.setCreateDate(new Date());
			myCellTreedotPerMapper.insertCellTreedotPer(cellTreedotPer);
		} else {
			CellTreedotPer model = this.getCellTreedotPer(cellTreedotPer
					.getId());
			if (model != null) {
				model.setIndexId(cellTreedotPer.getIndexId());
				if (cellTreedotPer.getIndexName() != null) {
					model.setIndexName(cellTreedotPer.getIndexName());
				}
				model.setNlevel(cellTreedotPer.getNlevel());
				model.setNodeico(cellTreedotPer.getNodeico());
				if (cellTreedotPer.getFromid() != null) {
					model.setFromid(cellTreedotPer.getFromid());
				}
				model.setListno(cellTreedotPer.getListno());
				model.setIntuserper(cellTreedotPer.getIntuserper());
				model.setMenuIndex(cellTreedotPer.getMenuIndex());
				myCellTreedotPerMapper.updateCellTreedotPer(model);
			}
		}
	}

	@javax.annotation.Resource 
	public void setMyCellTreedotPerMapper(
			MyCellTreedotPerMapper myCellTreedotPerMapper) {
		this.myCellTreedotPerMapper = myCellTreedotPerMapper;
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
