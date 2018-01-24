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

package com.glaf.form.core.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.SysDataItem;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.ISysDataItemService;
import com.glaf.core.util.PageResult;
import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.mapper.FormDictoryMapper;
import com.glaf.form.core.query.FormDictTreeQuery;
import com.glaf.form.core.query.FormDictoryQuery;
import com.glaf.form.core.service.IFormDictTreeService;
import com.glaf.form.core.service.IFormDictoryService;
import com.glaf.form.core.util.FormDictoryJsonFactory;

@Service("formDictoryService")
@Transactional(readOnly = true)
public class FormDictoryServiceImpl implements IFormDictoryService {
	protected final static Log logger = LogFactory.getLog(FormDictoryServiceImpl.class);

	protected IdGenerator idGenerator;

	protected FormDictoryMapper formDictoryMapper;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected IFormDictTreeService formDictTreeService;

	protected ISysDataItemService sysDataItemService;

	public FormDictoryServiceImpl() {

	}

	public int count(FormDictoryQuery query) {
		return formDictoryMapper.getFormDictoryCount(query);
	}

	@Transactional
	public boolean create(FormDictory bean) {
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(FormDictory bean) {
		this.deleteById(bean.getId());
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean deleteAll(long[] ids) {
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				this.deleteById(id);
			}
		}
		return true;
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			formDictoryMapper.deleteFormDictoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			FormDictoryQuery query = new FormDictoryQuery();
			query.rowIds(rowIds);
			formDictoryMapper.deleteFormDictories(query);
		}
	}

	public FormDictory find(long id) {
		return this.getFormDictory(id);
	}

	/**
	 * 获取全部基础数据的分类树
	 * 
	 * @return
	 */
	public List<FormDictTree> getAllCategories() {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.locked(0);
		List<FormDictTree> trees = formDictTreeService.getDictoryFormDictTrees(query);
		return trees;
	}

	public List<FormDictory> getAvailableDictoryList(long nodeId) {
		String cacheKey = "form_dicts_" + nodeId;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("form_dict", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONArray array = JSON.parseArray(text);
					return FormDictoryJsonFactory.arrayToList(array);
				} catch (Exception ex) {
				}
			}
		}
		FormDictoryQuery query = new FormDictoryQuery();
		query.nodeId(nodeId);
		query.blocked(0);
		query.setOrderBy(" E.SORT desc");
		List<FormDictory> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONArray array = FormDictoryJsonFactory.listToArray(list);
				CacheFactory.put("form_dict", cacheKey, array.toJSONString());
			}
		}
		return list;
	}

	public String getCodeById(long id) {
		FormDictory dic = find(id);
		return dic.getCode();
	}

	public FormDictory getFormDictory(Long id) {
		if (id == null) {
			return null;
		}
		String cacheKey = "form_dict_" + id;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("form_dict", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject json = JSON.parseObject(text);
					return FormDictoryJsonFactory.jsonToObject(json);
				} catch (Exception ex) {
				}
			}
		}

		FormDictory dictory = formDictoryMapper.getFormDictoryById(id);

		if (dictory != null && SystemConfig.getBoolean("use_query_cache")) {
			JSONObject json = dictory.toJsonObject();
			CacheFactory.put("form_dict", cacheKey, json.toJSONString());
		}
		return dictory;
	}

	@Override
	public FormDictory getFormDictoryByCode(String code) {
		if (code == null) {
			return null;
		}
		String cacheKey = "form_dict_" + code;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("form_dict", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject json = JSON.parseObject(text);
					return FormDictoryJsonFactory.jsonToObject(json);
				} catch (Exception ex) {
				}
			}
		}

		FormDictory dict = formDictoryMapper.getFormDictoryByCode(code);
		if (dict != null && SystemConfig.getBoolean("use_query_cache")) {
			JSONObject json = dict.toJsonObject();
			CacheFactory.put("form_dict", cacheKey, json.toJSONString());
		}

		return dict;
	}

	public int getFormDictoryCountByQueryCriteria(FormDictoryQuery query) {
		return formDictoryMapper.getFormDictoryCount(query);
	}

	public PageResult getFormDictoryList(int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		FormDictoryQuery query = new FormDictoryQuery();
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<FormDictory> list = this.getFormDictorysByQueryCriteria(start, pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<FormDictory> getFormDictoryList(long nodeId) {
		FormDictoryQuery query = new FormDictoryQuery();
		query.nodeId(nodeId);
		query.setOrderBy(" E.SORT desc");
		return this.list(query);
	}

	public PageResult getFormDictoryList(long nodeId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		FormDictoryQuery query = new FormDictoryQuery();
		query.nodeId(nodeId);
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT desc");

		int start = pageSize * (pageNo - 1);
		List<FormDictory> list = this.getFormDictorysByQueryCriteria(start, pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	/**
	 * 返回某分类下的所有字典列表
	 * 
	 * @param nodeCode
	 * @return
	 */
	public List<FormDictory> getFormDictoryList(String nodeCode) {
		FormDictTree tree = formDictTreeService.getFormDictTreeByCode(nodeCode);
		if (tree == null) {
			return null;
		}
		return this.getAvailableDictoryList(tree.getId());
	}

	public List<FormDictory> getFormDictorysByQueryCriteria(int start, int pageSize, FormDictoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormDictory> rows = sqlSessionTemplate.selectList("getFormDictories", query, rowBounds);
		return rows;
	}

	public List<FormDictory> list(FormDictoryQuery query) {
		List<FormDictory> list = formDictoryMapper.getFormDictories(query);
		return list;
	}

	@Transactional
	public void save(FormDictory dictory) {
		if (dictory.getId() == 0) {
			dictory.setId(idGenerator.nextId());
			dictory.setCreateDate(new Date());
			dictory.setSort(1);
			formDictoryMapper.insertFormDictory(dictory);

			long nodeId = dictory.getNodeId();
			FormDictTree tree = formDictTreeService.findById(nodeId);
			if (tree != null && tree.getCode() != null) {
				if (sysDataItemService.getSysDataItemByName(tree.getCode()) == null) {
					SysDataItem dataItem = new SysDataItem();
					dataItem.setName(tree.getCode());
					dataItem.setQuerySQL(
							"select NAME as name, CODE as value from form_dictory where TYPEID = " + tree.getId());
					dataItem.setTextField("name");
					dataItem.setValueField("value");
					dataItem.setTitle(tree.getName());
					dataItem.setCreateBy(dictory.getCreateBy());
					dataItem.setLocked(0);
					dataItem.setType("SYS");
					sysDataItemService.save(dataItem);
				}
			}
		} else {
			dictory.setUpdateDate(new Date());
			formDictoryMapper.updateFormDictory(dictory);
			if (SystemConfig.getBoolean("use_query_cache")) {
				String cacheKey = "form_dict_" + dictory.getId();
				CacheFactory.remove("form_dict", cacheKey);
			}
		}
		if (SystemConfig.getBoolean("use_query_cache")) {
			String cacheKey = "form_dicts_" + dictory.getNodeId();
			CacheFactory.remove("form_dict", cacheKey);
		}
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setFormDictoryMapper(FormDictoryMapper formDictoryMapper) {
		this.formDictoryMapper = formDictoryMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysDataItemService(ISysDataItemService sysDataItemService) {
		this.sysDataItemService = sysDataItemService;
	}

	@javax.annotation.Resource
	public void setFormDictTreeService(IFormDictTreeService formDictTreeService) {
		this.formDictTreeService = formDictTreeService;
	}

	@Transactional
	public void sort(long parent, FormDictory bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			logger.debug("前移:" + bean.getName());
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(parent, bean);
			logger.debug("后移:" + bean.getName());
		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(long nodeId, FormDictory bean) {
		FormDictoryQuery query = new FormDictoryQuery();
		query.nodeId(nodeId);
		query.setSortLessThanOrEqual(bean.getSort());
		query.setIdNotEqual(bean.getId());
		query.setOrderBy(" E.SORT desc ");

		List<?> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			FormDictory temp = (FormDictory) list.get(0);
			int sort = bean.getSort();
			bean.setSort(temp.getSort() - 1);
			if (sort != temp.getSort()) {
				bean.setSort(temp.getSort());
			}
			this.update(bean);// 更新bean

			temp.setSort(sort + 1);
			this.update(temp);// 更新temp
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long nodeId, FormDictory bean) {
		FormDictoryQuery query = new FormDictoryQuery();
		query.nodeId(nodeId);
		query.setSortGreaterThanOrEqual(bean.getSort());
		query.setIdNotEqual(bean.getId());
		query.setOrderBy(" E.SORT asc ");

		List<?> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			FormDictory temp = (FormDictory) list.get(0);
			int sort = bean.getSort();
			bean.setSort(temp.getSort() + 1);
			if (sort != temp.getSort()) {
				bean.setSort(temp.getSort());
			}
			this.update(bean);// 更新bean

			temp.setSort(sort - 1);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public boolean update(FormDictory bean) {
		this.save(bean);
		return true;
	}

}
