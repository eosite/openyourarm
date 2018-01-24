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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.SysApplicationMapper;
import com.glaf.base.modules.sys.mapper.SysDepartmentMapper;
import com.glaf.base.modules.sys.mapper.SysTreeMapper;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysApplicationQuery;
import com.glaf.base.modules.sys.query.SysDepartmentQuery;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.util.SysTreeJsonFactory;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.SysDataItem;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.ISysDataItemService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.StringTools;

@Service("sysTreeService")
@Transactional(readOnly = true)
public class SysTreeServiceImpl implements SysTreeService {
	protected final static Log logger = LogFactory.getLog(SysTreeServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysDepartmentMapper sysDepartmentMapper;

	protected SysTreeMapper sysTreeMapper;

	protected ITableDataService tableDataService;

	protected ISysDataItemService sysDataItemService;

	public SysTreeServiceImpl() {

	}

	public int count(SysTreeQuery query) {
		return sysTreeMapper.getSysTreeCount(query);
	}

	@Transactional
	public boolean create(SysTree bean) {
		String parentTreeId = null;
		if (bean.getParentId() != 0) {
			SysTree parent = this.findById(bean.getParentId());
			if (parent != null) {
				parentTreeId = parent.getTreeId();
				if (bean.getDiscriminator() == null) {
					bean.setDiscriminator(parent.getDiscriminator());
				}
				if (bean.getCacheFlag() == null) {
					bean.setCacheFlag(parent.getCacheFlag());
				}
				if (SystemConfig.getBoolean("use_query_cache")) {
					String cacheKey = "cache_treemodel_" + parent.getId();
					CacheFactory.remove("tree", cacheKey);
					cacheKey = "sys_tree_" + parent.getId();
					CacheFactory.remove("tree", cacheKey);
					CacheFactory.clear("tree");
				}
			}
		}
		if (bean.getId() == 0) {
			bean.setId(idGenerator.nextId());
		}
		bean.setCreateDate(new Date());
		bean.setSort(1);
		if (parentTreeId != null) {
			bean.setTreeId(parentTreeId + bean.getId() + "|");
		} else {
			bean.setTreeId(bean.getId() + "|");
		}
		sysTreeMapper.insertSysTree(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(SysTree bean) {
		this.deleteById(bean.getId());
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
			List<SysTree> treeList = this.getSysTreeList(id);
			if (treeList != null && !treeList.isEmpty()) {
				throw new RuntimeException("tree node exist children ");
			}
			sysTreeMapper.deleteSysTreeById(id);
			if (SystemConfig.getBoolean("use_query_cache")) {
				String cacheKey = "cache_treemodel_" + id;
				CacheFactory.remove("tree", cacheKey);
				cacheKey = "sys_tree_" + id;
				CacheFactory.remove("tree", cacheKey);
				CacheFactory.clear("tree");
			}
		}
	}

	public SysTree findById(long id) {
		return this.getSysTree(id);
	}

	public SysTree findByName(String name) {
		SysTreeQuery query = new SysTreeQuery();
		query.name(name);
		query.setDeleteFlag(0);

		List<SysTree> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public List<SysTree> getAllSysTreeList() {
		SysTreeQuery query = new SysTreeQuery();
		query.setDeleteFlag(0);
		List<SysTree> list = this.list(query);
		List<SysTree> nodes = this.getAvailableSysTrees(list);
		Collections.sort(nodes);
		return nodes;
	}

	public List<SysTree> getAllSysTreeListForDept(long parentId, int status) {
		List<SysTree> list = new ArrayList<SysTree>();
		this.loadChildrenTreeListForDept(list, parentId, status);
		List<SysTree> nodes = this.getAvailableSysTrees(list);
		Collections.sort(nodes);
		this.initDepartments(nodes);
		return nodes;
	}

	public List<SysTree> getApplicationSysTrees(SysTreeQuery query) {
		return sysTreeMapper.getApplicationSysTrees(query);
	}

	public List<SysTree> getAvailableSysTrees(List<SysTree> list) {
		List<SysTree> nodes = new ArrayList<SysTree>();
		if (list != null && !list.isEmpty()) {
			Map<Long, SysTree> disableMap = new HashMap<Long, SysTree>();
			for (int i = 0, len = list.size(); i < len / 2; i++) {
				for (SysTree tree : list) {
					if (tree.getLocked() == 1 || tree.getDeleteFlag() == 1) {
						disableMap.put(tree.getId(), tree);
						continue;
					}
					if (disableMap.get(tree.getParentId()) != null) {
						disableMap.put(tree.getId(), tree);
						continue;
					}
				}
			}
			for (SysTree tree : list) {
				if (disableMap.get(tree.getId()) == null) {
					nodes.add(tree);
				}
			}
			disableMap.clear();
			disableMap = null;
		}
		return nodes;
	}

	public List<SysTree> getDepartmentSysTrees(SysTreeQuery query) {
		List<SysTree> list = sysTreeMapper.getDepartmentSysTrees(query);
		this.initDepartments(list);
		return list;
	}

	public List<SysTree> getDictorySysTrees(SysTreeQuery query) {
		return sysTreeMapper.getDictorySysTrees(query);
	}

	/**
	 * 获取关联表树型结构
	 * 
	 * @param relationTable
	 *            表名
	 * @param relationColumn
	 *            关联字段名
	 * @param query
	 * @return
	 */
	public List<SysTree> getRelationSysTrees(String relationTable, String relationColumn, SysTreeQuery query) {
		query.setRelationTable(relationTable);
		query.setRelationColumn(relationColumn);
		return sysTreeMapper.getRelationSysTrees(query);
	}

	/**
	 * 获取某个用户某个角色的树节点
	 * 
	 * @param query
	 * @return
	 */
	public List<SysTree> getRoleUserTrees(SysUserQuery query) {
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return sysTreeMapper.getRoleUserTrees_oracle(query);
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			return sysTreeMapper.getRoleUserTrees_postgresql(query);
		}
		return sysTreeMapper.getRoleUserTrees(query);
	}

	public void getSysTree(List<SysTree> treeList, long parentId, int deep) {
		this.loadSysTrees(treeList, parentId, deep);
	}

	public SysTree getSysTree(Long id) {
		if (id == null) {
			return null;
		}
		SysTree sysTree = null;
		String cacheKey = "sys_tree_" + id;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("tree", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					com.alibaba.fastjson.JSONObject json = JSON.parseObject(text);
					sysTree = SysTreeJsonFactory.jsonToObject(json);
					if (sysTree != null) {
						return sysTree;
					}
				} catch (Exception ex) {
				}
			}
		}

		sysTree = sysTreeMapper.getSysTreeById(id);
		if (sysTree != null) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				CacheFactory.put("tree", cacheKey, sysTree.toJsonObject().toJSONString());
			}
		}
		return sysTree;
	}

	public SysTree getSysTreeByCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		SysTreeQuery query = new SysTreeQuery();
		query.code(code);
		query.setDeleteFlag(0);

		List<SysTree> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * 获取某个节点及其祖先
	 * 
	 * @param id
	 * @return
	 */
	public SysTree getSysTreeByIdWithAncestor(long id) {
		SysTree sysTree = this.getSysTree(id);
		if (sysTree != null) {
			if (sysTree.getParentId() > 0) {
				SysTree parent = this.getSysTreeByIdWithAncestor(sysTree.getParentId());
				if (parent != null) {
					sysTree.setParent(parent);
					sysTree.setParentTree(parent);
				}
			}
		}
		return sysTree;
	}

	public int getSysTreeCountByQueryCriteria(SysTreeQuery query) {
		return sysTreeMapper.getSysTreeCount(query);
	}

	public List<SysTree> getSysTreeList(long parentId) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(parentId);
		query.setOrderBy("  E.SORT asc ");
		List<SysTree> list = this.list(query);
		List<SysTree> trees = this.getAvailableSysTrees(list);
		if (trees != null && !trees.isEmpty()) {
			Collections.sort(trees);
		}
		return trees;
	}

	public PageResult getSysTreeList(long parentId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysTreeQuery query = new SysTreeQuery();
		query.setDeleteFlag(0);
		query.parentId(parentId);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT asc ");

		int start = pageSize * (pageNo - 1);
		List<SysTree> list = this.getSysTreesByQueryCriteria(start, pageSize, query);
		Collections.sort(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SysTree> getSysTreeListForDept(long parentId, int status) {
		SysTreeQuery query = new SysTreeQuery();
		query.setDeleteFlag(0);
		query.setParentId(parentId);
		if (status != -1) {
			query.setDepartmentStatus(status);
		}
		query.setOrderBy(" E.SORT asc ");
		List<SysTree> list = this.list(query);
		List<SysTree> nodes = this.getAvailableSysTrees(list);
		Collections.sort(nodes);
		this.initDepartments(nodes);
		return list;
	}

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 * 
	 * @return List
	 */
	public List<SysTree> getSysTreeListWithChildren(long parentId) {
		SysTree parent = this.findById(parentId);
		SysTreeQuery query = new SysTreeQuery();
		query.setDeleteFlag(0);
		query.treeIdLike(parent.getTreeId() + "%");
		query.setOrderBy("  E.SORT asc ");
		List<SysTree> list = this.list(query);
		List<SysTree> nodes = this.getAvailableSysTrees(list);
		return nodes;
	}

	/**
	 * 获取父节点列表，如:根目录>A>A1>A11
	 * 
	 * @param tree
	 * @param int
	 *            id
	 */
	public void getSysTreeParent(List<SysTree> parentList, long id) {
		// 查找是否有父节点
		SysTree bean = findById(id);
		if (bean != null) {
			if (bean.getParentId() > 0) {
				getSysTreeParent(parentList, bean.getParentId());
			}
			parentList.add(bean);
		}
	}

	public List<SysTree> getSysTrees(SysTreeQuery query) {
		List<SysTree> list = sysTreeMapper.getSysTrees(query);
		if (list != null && !list.isEmpty()) {
			StringTokenizer token = null;
			for (SysTree tree : list) {
				tree.setDeep(0);
				if (StringUtils.isNotEmpty(tree.getTreeId())) {
					token = new StringTokenizer(tree.getTreeId(), "|");
					while (token.hasMoreTokens()) {
						if (StringUtils.isNotEmpty(token.nextToken())) {
							tree.setDeep(tree.getDeep() + 1);
						}
					}
				}
			}
		}
		return list;
	}

	public List<SysTree> getSysTreesByQueryCriteria(int start, int pageSize, SysTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysTree> rows = sqlSessionTemplate.selectList("getSysTrees", query, rowBounds);
		Collections.sort(rows);
		return rows;
	}

	protected String getTreeId(Map<Long, SysTree> dataMap, SysTree tree) {
		long parentId = tree.getParentId();
		long id = tree.getId();
		SysTree parent = dataMap.get(parentId);
		if (parent != null && parent.getId() != 0) {
			if (StringUtils.isEmpty(parent.getTreeId())) {
				return getTreeId(dataMap, parent) + id + "|";
			}
			if (!parent.getTreeId().endsWith("|")) {
				parent.setTreeId(parent.getTreeId() + "|");
			}
			return parent.getTreeId() + id + "|";
		}
		return tree.getTreeId();
	}

	protected void initApplications(List<SysTree> list) {
		if (list != null && !list.isEmpty()) {
			SysApplicationQuery query = new SysApplicationQuery();
			query.setDeleteFlag(0);
			List<SysApplication> apps = sysApplicationMapper.getSysApplications(query);
			Map<Long, SysApplication> appMap = new HashMap<Long, SysApplication>();
			if (apps != null && !apps.isEmpty()) {
				for (SysApplication m : apps) {
					appMap.put(m.getNodeId(), m);
				}
			}
			for (SysTree bean : list) {
				bean.setApp(appMap.get(bean.getId()));
			}
		}
	}

	protected void initDepartments(List<SysTree> list) {
		if (list != null && !list.isEmpty()) {
			SysDepartmentQuery query = new SysDepartmentQuery();
			query.setDeleteFlag(0);
			query.status(0);
			List<SysDepartment> depts = sysDepartmentMapper.getSysDepartments(query);
			Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
			if (depts != null && !depts.isEmpty()) {
				for (SysDepartment dept : depts) {
					deptMap.put(dept.getNodeId(), dept);
				}
			}
			for (SysTree bean : list) {
				bean.setDepartment(deptMap.get(bean.getId()));
			}
		}
	}

	public List<SysTree> list(SysTreeQuery query) {
		List<SysTree> list = sysTreeMapper.getSysTrees(query);
		return list;
	}

	public void loadChildren(List<SysTree> treeList, long parentId) {
		logger.debug("--------------loadChildren---------");
		SysTree root = this.findById(parentId);
		if (root != null) {
			if (StringUtils.isNotEmpty(root.getTreeId())) {
				SysTreeQuery query = new SysTreeQuery();
				query.setDeleteFlag(0);
				query.treeIdLike(root.getTreeId() + "%");
				query.setOrderBy(" E.TREEID asc ");
				List<SysTree> list = this.list(query);
				List<SysTree> nodes = this.getAvailableSysTrees(list);
				if (nodes != null && !nodes.isEmpty()) {
					Iterator<SysTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						SysTree bean = iter.next();
						if (bean.getId() != parentId) {
							treeList.add(bean);
						}
					}
				}
			} else {
				SysTreeQuery query = new SysTreeQuery();
				query.setDeleteFlag(0);
				query.setParentId(parentId);
				List<SysTree> list = this.list(query);
				List<SysTree> nodes = this.getAvailableSysTrees(list);
				if (nodes != null && !nodes.isEmpty()) {
					Iterator<SysTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						SysTree bean = iter.next();
						treeList.add(bean);// 加入到数组
						loadChildren(treeList, bean.getId());// 递归遍历
					}
				}
			}
		}
	}

	public void loadChildren2(List<SysTree> list, long parentId) {
		SysTreeQuery query = new SysTreeQuery();
		query.setParentId(parentId);
		query.setDeleteFlag(0);
		List<SysTree> list2 = this.list(query);
		List<SysTree> nodes = this.getAvailableSysTrees(list2);
		if (nodes != null && !nodes.isEmpty()) {
			for (SysTree node : nodes) {
				list.add(node);
				this.loadChildren2(list, node.getId());
			}
		}
	}

	protected void loadChildrenTreeListForDept(List<SysTree> treeList, long parentId, int status) {
		logger.debug("--------------loadChildrenTreeListForDept---------");
		SysTree root = this.findById(parentId);
		if (root != null) {
			logger.debug("dept name:" + root.getName());
			if (StringUtils.isNotEmpty(root.getTreeId())) {
				SysTreeQuery query = new SysTreeQuery();
				query.setDeleteFlag(0);
				query.setDiscriminator("D");
				query.treeIdLike(root.getTreeId() + "%");
				query.setOrderBy(" E.TREEID asc ");
				List<SysTree> list = this.list(query);
				List<SysTree> nodes = this.getAvailableSysTrees(list);
				if (nodes != null && !nodes.isEmpty()) {
					// this.initDepartments(nodes);
					Iterator<SysTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						SysTree bean = iter.next();
						if (bean.getId() != parentId) {
							treeList.add(bean);
						}
					}
				}
			} else {
				SysTreeQuery query = new SysTreeQuery();
				query.setParentId(parentId);
				query.setDeleteFlag(0);
				query.setDiscriminator("D");
				List<SysTree> list = this.list(query);
				List<SysTree> nodes = this.getAvailableSysTrees(list);
				if (nodes != null && !nodes.isEmpty()) {
					// this.initDepartments(nodes);
					Iterator<SysTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						SysTree bean = iter.next();
						treeList.add(bean);// 加入到数组
						loadChildrenTreeListForDept(treeList, bean.getId(), status);// 递归遍历
					}
				}
			}
		}
	}

	protected void loadChildrenTreeListForDept2(List<SysTree> list, long parentId, int status) {
		SysTreeQuery query = new SysTreeQuery();
		query.setDeleteFlag(0);
		query.setParentId(parentId);
		if (status != -1) {
			query.setDepartmentStatus(status);
		}
		List<SysTree> list2 = this.list(query);
		List<SysTree> nodes = this.getAvailableSysTrees(list2);
		if (nodes != null && !nodes.isEmpty()) {
			for (SysTree tt : nodes) {
				list.add(tt);
				this.loadChildrenTreeListForDept2(list, tt.getId(), status);
			}
		}
	}

	public void loadSysTrees(List<SysTree> treeList, long parentId, int deep) {
		logger.debug("--------------loadSysTrees---------------");
		SysTree root = this.findById(parentId);
		if (root != null) {
			if (StringUtils.isNotEmpty(root.getTreeId())) {
				SysTreeQuery query = new SysTreeQuery();
				query.treeIdLike(root.getTreeId() + "%");
				query.setOrderBy(" E.TREEID asc ");
				List<SysTree> list = this.list(query);
				List<SysTree> nodes = this.getAvailableSysTrees(list);
				if (nodes != null && !nodes.isEmpty()) {
					this.initDepartments(nodes);
					this.initApplications(nodes);
					Iterator<SysTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						SysTree bean = iter.next();
						if (bean.getId() != parentId) {
							String treeId = bean.getTreeId();
							String tmp = treeId.substring(root.getTreeId().length(), treeId.length());
							StringTokenizer token = new StringTokenizer(tmp, "|");
							bean.setDeep(token.countTokens());
							treeList.add(bean);// 加入到数组
							// logger.debug("dept level:" + bean.getDeep());
						}
					}
				}
			} else {
				SysTreeQuery query = new SysTreeQuery();
				query.setParentId(parentId);
				List<SysTree> list = this.list(query);
				List<SysTree> nodes = this.getAvailableSysTrees(list);
				if (nodes != null && !nodes.isEmpty()) {
					this.initDepartments(nodes);
					this.initApplications(nodes);
					Iterator<SysTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						SysTree bean = iter.next();
						bean.setDeep(deep + 1);
						treeList.add(bean);// 加入到数组
						loadSysTrees(treeList, bean.getId(), bean.getDeep());// 递归遍历
					}
				}
			}
		}
	}

	public void loadSysTrees2(List<SysTree> treeList, long parentId, int deep) {
		SysTreeQuery query = new SysTreeQuery();
		query.setDeleteFlag(0);
		query.setParentId(parentId);
		List<SysTree> list = this.list(query);
		List<SysTree> nodes = this.getAvailableSysTrees(list);
		if (nodes != null && !nodes.isEmpty()) {
			this.initDepartments(nodes);
			this.initApplications(nodes);
			Iterator<SysTree> iter = nodes.iterator();
			while (iter.hasNext()) {// 递归遍历
				SysTree bean = iter.next();
				bean.setDeep(deep + 1);
				treeList.add(bean);// 加入到数组
				loadSysTrees2(treeList, bean.getId(), bean.getDeep());
			}
		}
	}

	@Transactional
	public void save(SysTree bean) {
		String parentTreeId = null;
		if (bean.getParentId() != 0) {
			SysTree parent = this.findById(bean.getParentId());
			if (parent != null) {
				if (bean.getDiscriminator() == null) {
					bean.setDiscriminator(parent.getDiscriminator());
				}
				if (bean.getCacheFlag() == null) {
					bean.setCacheFlag(parent.getCacheFlag());
				}
				if (StringUtils.isNotEmpty(parent.getTreeId())) {
					parentTreeId = parent.getTreeId();
				}
			}
		}

		if (bean.getId() == 0) {
			bean.setSort(1);
			bean.setId(idGenerator.nextId());
			bean.setCreateDate(new Date());
			if (parentTreeId != null) {
				bean.setTreeId(parentTreeId + bean.getId() + "|");
			} else {
				bean.setTreeId(bean.getId() + "|");
			}
			sysTreeMapper.insertSysTree(bean);

			if (bean.getParentId() == 4 && bean.getCode() != null) {// 基础数据
				if (sysDataItemService.getSysDataItemByName(bean.getCode()) == null) {
					SysDataItem dataItem = new SysDataItem();
					dataItem.setName(bean.getCode());
					dataItem.setQuerySQL(
							"select NAME as name, CODE as value from sys_dictory where TYPEID = " + bean.getId());
					dataItem.setTextField("name");
					dataItem.setValueField("value");
					dataItem.setTitle(bean.getName());
					dataItem.setCreateBy(bean.getCreateBy());
					dataItem.setLocked(0);
					dataItem.setType("SYS");
					sysDataItemService.save(dataItem);
				}
			}
		} else {
			bean.setUpdateDate(new Date());
			this.update(bean);
			String cacheKey = "sys_tree_" + bean.getId();
			CacheFactory.remove("tree", cacheKey);
			cacheKey = "cache_treemodel_" + bean.getId();
			CacheFactory.remove("tree", cacheKey);
			CacheFactory.clear("tree");
		}
	}

	@Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setSysApplicationMapper(SysApplicationMapper sysApplicationMapper) {
		this.sysApplicationMapper = sysApplicationMapper;
	}

	@Resource
	public void setSysDataItemService(ISysDataItemService sysDataItemService) {
		this.sysDataItemService = sysDataItemService;
	}

	@Resource
	public void setSysDepartmentMapper(SysDepartmentMapper sysDepartmentMapper) {
		this.sysDepartmentMapper = sysDepartmentMapper;
	}

	@Resource
	public void setSysTreeMapper(SysTreeMapper sysTreeMapper) {
		this.sysTreeMapper = sysTreeMapper;
	}

	@Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@Transactional
	public void sort(long parent, SysTree bean, int operate) {
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(parent, bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(parent, bean);
		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(long parent, SysTree bean) {
		SysTreeQuery query = new SysTreeQuery();
		query.setDeleteFlag(0);
		query.parentId(parent);
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		// 查找后一个对象
		List<SysTree> list = this.list(query);
		List<SysTree> nodes = this.getAvailableSysTrees(list);
		if (nodes != null && nodes.size() > 0) {// 有记录
			SysTree temp = (SysTree) nodes.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parent, SysTree bean) {
		SysTreeQuery query = new SysTreeQuery();
		query.setDeleteFlag(0);
		query.parentId(parent);
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc ");

		// 查找前一个对象
		List<SysTree> list = this.list(query);
		List<SysTree> nodes = this.getAvailableSysTrees(list);
		if (nodes != null && nodes.size() > 0) {// 有记录
			SysTree temp = (SysTree) nodes.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public boolean update(SysTree bean) {
		SysTree model = this.findById(bean.getId());
		/**
		 * 如果节点移动了位置，即移动到别的节点下面去了
		 */
		if (model.getParentId() != bean.getParentId()) {
			List<SysTree> list = new ArrayList<SysTree>();
			this.loadChildren(list, bean.getId());
			if (!list.isEmpty()) {
				for (SysTree node : list) {
					/**
					 * 不能移动到ta自己的子节点下面去
					 */
					if (bean.getParentId() == node.getId()) {
						throw new RuntimeException("Can't change node into children");
					}
				}
				/**
				 * 修正所有子节点的treeId
				 */
				SysTree oldParent = this.findById(model.getParentId());
				SysTree newParent = this.findById(bean.getParentId());
				if (oldParent != null && newParent != null && StringUtils.isNotEmpty(oldParent.getTreeId())
						&& StringUtils.isNotEmpty(newParent.getTreeId())) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName("SYS_TREE");
					ColumnModel idColumn = new ColumnModel();
					idColumn.setColumnName("ID");
					idColumn.setJavaType("Long");
					tableModel.setIdColumn(idColumn);

					ColumnModel treeColumn = new ColumnModel();
					treeColumn.setColumnName("TREEID");
					treeColumn.setJavaType("String");
					tableModel.addColumn(treeColumn);

					for (SysTree node : list) {
						String treeId = node.getTreeId();
						if (StringUtils.isNotEmpty(treeId)) {
							treeId = StringTools.replace(treeId, oldParent.getTreeId(), newParent.getTreeId());
							idColumn.setValue(node.getId());
							treeColumn.setValue(treeId);
							tableDataService.updateTableData(tableModel);
						}
					}
				}
			}
		}

		if (bean.getParentId() != 0) {
			SysTree parent = this.findById(bean.getParentId());
			if (parent != null) {
				if (bean.getDiscriminator() == null) {
					bean.setDiscriminator(parent.getDiscriminator());
				}
				if (bean.getCacheFlag() == null) {
					bean.setCacheFlag(parent.getCacheFlag());
				}
				if (StringUtils.isNotEmpty(parent.getTreeId())) {
					bean.setTreeId(parent.getTreeId() + bean.getId() + "|");
				}
			}
		}
		String cacheKey = "sys_tree_" + bean.getId();
		CacheFactory.remove("tree", cacheKey);
		cacheKey = "cache_treemodel_" + bean.getId();
		CacheFactory.remove("tree", cacheKey);
		CacheFactory.clear("tree");
		sysTreeMapper.updateSysTree(bean);
		return true;
	}

	@Transactional
	public void updateTreeIds() {
		SysTree root = this.findById(1);
		if (root != null) {
			if (!StringUtils.equals(root.getTreeId(), "1|")) {
				root.setTreeId("1|");
				this.update(root);
			}
			List<SysTree> trees = this.getAllSysTreeList();
			if (trees != null && !trees.isEmpty()) {
				Map<Long, SysTree> dataMap = new HashMap<Long, SysTree>();
				for (SysTree tree : trees) {
					dataMap.put(tree.getId(), tree);
				}
				Map<Long, String> treeIdMap = new HashMap<Long, String>();
				for (SysTree tree : trees) {
					String cacheKey = "sys_tree_" + tree.getId();
					CacheFactory.remove("tree", cacheKey);
					if (StringUtils.isEmpty(tree.getTreeId())) {
						String treeId = this.getTreeId(dataMap, tree);
						if (treeId != null && treeId.endsWith("|")) {
							treeIdMap.put(tree.getId(), treeId);
						}
					}
				}
				this.updateTreeIds(treeIdMap);
			}
		}
	}

	/**
	 * 更新树的treeId字段
	 * 
	 * @param treeMap
	 */
	@Transactional
	public void updateTreeIds(Map<Long, String> treeMap) {
		TableModel tableModel = new TableModel();
		tableModel.setTableName("SYS_TREE");
		ColumnModel idColumn = new ColumnModel();
		idColumn.setColumnName("ID");
		idColumn.setJavaType("Long");
		tableModel.setIdColumn(idColumn);

		ColumnModel treeColumn = new ColumnModel();
		treeColumn.setColumnName("TREEID");
		treeColumn.setJavaType("String");
		tableModel.addColumn(treeColumn);

		Iterator<Long> iterator = treeMap.keySet().iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			String value = treeMap.get(id);
			if (value != null) {
				idColumn.setValue(id);
				treeColumn.setValue(value);
				tableDataService.updateTableData(tableModel);
			}
		}
	}

}
