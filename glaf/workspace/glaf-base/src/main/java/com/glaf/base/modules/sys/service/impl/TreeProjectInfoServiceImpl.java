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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.mapper.MyTreeProjectInfoMapper;
import com.glaf.base.modules.sys.model.TreeProjectInfo;
import com.glaf.base.modules.sys.query.TreeProjectInfoQuery;
import com.glaf.base.modules.sys.service.ITreeProjectInfoService;

@Service("treeProjectInfoService")
@Transactional(readOnly = true)
public class TreeProjectInfoServiceImpl implements ITreeProjectInfoService {
	protected final Logger logger = LoggerFactory.getLogger(super.getClass());

	protected MyTreeProjectInfoMapper myTreeProjectInfoMapper;

	protected SqlSessionTemplate sqlSessionTemplate;

	@Transactional
	public void deleteById(Integer id) {
		if (id != null)
			this.myTreeProjectInfoMapper.deleteTreeProjectInfoById(id);
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
		if ((indexIds != null) && (!(indexIds.isEmpty())))
			for (Integer id : indexIds)
				this.myTreeProjectInfoMapper.deleteTreeProjectInfoById(id);
	}

	public TreeProjectInfo getRootTreeProjectInfo() {
		return this.myTreeProjectInfoMapper.getRootTreeProjectInfo();
	}

	/**
	 * 获取所有子节点记录数
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Map<String, Integer>> getSubTreeProjectInfoCountByParentId(
			Integer parentId) {
		return this.myTreeProjectInfoMapper
				.getSubTreeProjectInfoCountByParentId(parentId);// 查询所有子节点记录数
	}

	public List<Map<String, Integer>> getSubTreeProjectInfoCountByQuery(
			TreeProjectInfoQuery query) {
		return myTreeProjectInfoMapper.getSubTreeProjectInfoCountByQuery(query);
	}

	public TreeProjectInfo getTreeProjectInfo(Integer id) {
		if (id == null) {
			return null;
		}
		TreeProjectInfo treepinfo = this.myTreeProjectInfoMapper
				.getTreeProjectInfoById(id);
		return treepinfo;
	}

	public int getTreeProjectInfoCountByQueryCriteria(TreeProjectInfoQuery query) {
		return this.myTreeProjectInfoMapper.getTreeProjectInfoCount(query);
	}

	public String getTreeProjectInfoId(String strfuntion) {
		return myTreeProjectInfoMapper.getTreeProjectInfoId(strfuntion);
	}

	public List<TreeProjectInfo> getTreeProjectInfoListByParentId(
			Integer parentId) {
		return this.myTreeProjectInfoMapper.getTreeProjectInfoListByParentId(parentId);
	}

	public List<TreeProjectInfo> getTreeProjectInfos(String strfuntion) {
		String id = this.getTreeProjectInfoId(strfuntion);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("treeId", id);
		return this.getTreeProjectInfosByCondition(params);
	}

	public List<TreeProjectInfo> getTreeProjectInfosByCondition(
			Map<String, Object> params) {
		return myTreeProjectInfoMapper.getTreeProjectInfosByCondition(params);
	}

	public List<TreeProjectInfo> getTreeProjectInfosByQueryCriteria(int start,
			int pageSize, TreeProjectInfoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TreeProjectInfo> rows = sqlSessionTemplate.selectList(
				"getTreeProjectInfos", query, rowBounds);
		return rows;
	}

	public List<TreeProjectInfo> list(TreeProjectInfoQuery query) {
		
		List<TreeProjectInfo> list = this.myTreeProjectInfoMapper
				.getTreeProjectInfos(query);
		return list;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource 
	public void setMyTreeProjectInfoMapper(MyTreeProjectInfoMapper myTreeProjectInfoMapper) {
		this.myTreeProjectInfoMapper = myTreeProjectInfoMapper;
	}

}
