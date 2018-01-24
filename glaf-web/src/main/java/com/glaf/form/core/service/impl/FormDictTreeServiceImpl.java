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

import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.SysApplicationMapper;
import com.glaf.base.modules.sys.mapper.SysDepartmentMapper;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.query.SysApplicationQuery;
import com.glaf.base.modules.sys.query.SysDepartmentQuery;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.SysDataItem;
import com.glaf.core.factory.DataServiceFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.ISysDataItemService;

import com.glaf.core.util.PageResult;
import com.glaf.core.util.StringTools;
import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.mapper.FormDictTreeMapper;
import com.glaf.form.core.query.FormDictTreeQuery;
import com.glaf.form.core.service.IFormDictTreeService;

@Service("formDictTreeService")
@Transactional(readOnly = true)
public class FormDictTreeServiceImpl implements IFormDictTreeService {
	protected final static Log logger = LogFactory.getLog(FormDictTreeServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysDepartmentMapper sysDepartmentMapper;

	protected FormDictTreeMapper formDictTreeMapper;

	protected ISysDataItemService sysDataItemService;

	public FormDictTreeServiceImpl() {

	}

	public int count(FormDictTreeQuery query) {
		return formDictTreeMapper.getFormDictTreeCount(query);
	}

	@Transactional
	public boolean create(FormDictTree bean) {
		if (bean.getParentId() != 0) {
			FormDictTree parent = this.findById(bean.getParentId());
			if (parent != null) {
				if (bean.getDiscriminator() == null) {
					bean.setDiscriminator(parent.getDiscriminator());
				}
				if (bean.getCacheFlag() == null) {
					bean.setCacheFlag(parent.getCacheFlag());
				}
			}
		}
		bean.setCreateDate(new Date());
		this.save(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(FormDictTree bean) {
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
			List<FormDictTree> treeList = this.getFormDictTreeList(id);
			if (treeList != null && !treeList.isEmpty()) {
				throw new RuntimeException("tree node exist children ");
			}
			formDictTreeMapper.deleteFormDictTreeById(id);
		}
	}
	
	@Transactional
	@Override
	public void deleteAll(FormDictTreeQuery query){
		formDictTreeMapper.deleteFormDictTrees(query);
	}

	public FormDictTree findById(long id) {
		return this.getFormDictTree(id);
	}

	public FormDictTree findByName(String name) {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.name(name);

		List<FormDictTree> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public List<FormDictTree> getAllFormDictTreeList() {
		FormDictTreeQuery query = new FormDictTreeQuery();
		List<FormDictTree> list = this.list(query);
		Collections.sort(list);
		return list;
	}

	public List<FormDictTree> getAllFormDictTreeListForDept(long parentId, int status) {
		List<FormDictTree> list = new ArrayList<FormDictTree>();
		this.loadChildrenTreeListForDept(list, parentId, status);
		Collections.sort(list);
		this.initDepartments(list);
		return list;
	}

	public List<FormDictTree> getDictoryFormDictTrees(FormDictTreeQuery query) {
		return formDictTreeMapper.getDictoryFormDictTrees(query);
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
	public List<FormDictTree> getRelationFormDictTrees(String relationTable, String relationColumn, FormDictTreeQuery query) {
		query.setRelationTable(relationTable);
		query.setRelationColumn(relationColumn);
		return formDictTreeMapper.getRelationFormDictTrees(query);
	}

	public void getFormDictTree(List<FormDictTree> treeList, long parentId, int deep) {
		this.loadFormDictTrees(treeList, parentId, deep);
	}

	public FormDictTree getFormDictTree(Long id) {
		if (id == null) {
			return null;
		}
		FormDictTree FormDictTree = formDictTreeMapper.getFormDictTreeById(id);
		return FormDictTree;
	}

	/**
	 * 获取某个节点及其祖先
	 * 
	 * @param id
	 * @return
	 */
	public FormDictTree getFormDictTreeByIdWithAncestor(long id) {
		FormDictTree FormDictTree = formDictTreeMapper.getFormDictTreeById(id);
		if (FormDictTree != null) {
			if (FormDictTree.getParentId() > 0) {
				FormDictTree parent = this.getFormDictTreeByIdWithAncestor(FormDictTree.getParentId());
				if (parent != null) {
					FormDictTree.setParent(parent);
					FormDictTree.setParentTree(parent);
				}
			}
		}
		return FormDictTree;
	}

	public FormDictTree getFormDictTreeByCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.code(code);

		List<FormDictTree> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public int getFormDictTreeCountByQueryCriteria(FormDictTreeQuery query) {
		return formDictTreeMapper.getFormDictTreeCount(query);
	}

	public List<FormDictTree> getFormDictTreeList(long parentId) {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		query.setOrderBy("  E.SORT asc ");
		List<FormDictTree> list = this.list(query);
		Collections.sort(list);
		return list;
	}

	public PageResult getFormDictTreeList(long parentId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.parentId(Long.valueOf(parentId));
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.SORT asc ");

		int start = pageSize * (pageNo - 1);
		List<FormDictTree> list = this.getFormDictTreesByQueryCriteria(start, pageSize, query);
		Collections.sort(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<FormDictTree> getFormDictTreeListForDept(long parentId, int status) {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		if (status != -1) {
			query.setDepartmentStatus(status);
		}
		query.setOrderBy(" E.SORT asc ");
		List<FormDictTree> list = this.list(query);
		Collections.sort(list);
		this.initDepartments(list);
		return list;
	}

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 * 
	 * @return List
	 */
	public List<FormDictTree> getFormDictTreeListWithChildren(long parentId) {
		FormDictTree parent = this.findById(parentId);
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.treeIdLike(parent.getTreeId() + "%");
		query.setOrderBy("  E.SORT asc ");
		List<FormDictTree> list = this.list(query);
		return list;
	}

	/**
	 * 获取父节点列表，如:根目录>A>A1>A11
	 * 
	 * @param tree
	 * @param int
	 *            id
	 */
	public void getFormDictTreeParent(List<FormDictTree> tree, long id) {
		// 查找是否有父节点
		FormDictTree bean = findById(id);
		if (bean != null) {
			if (bean.getParentId() != 0) {
				getFormDictTreeParent(tree, bean.getParentId());
			}
			tree.add(bean);
		}
	}

	public List<FormDictTree> getFormDictTreesByQueryCriteria(int start, int pageSize, FormDictTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormDictTree> rows = sqlSessionTemplate.selectList("getFormDictTrees", query, rowBounds);
		Collections.sort(rows);
		return rows;
	}

	protected String getTreeId(Map<Long, FormDictTree> dataMap, FormDictTree tree) {
		long parentId = tree.getParentId();
		long id = tree.getId();
		FormDictTree parent = dataMap.get(parentId);
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

	protected void initApplications(List<FormDictTree> list) {
		if (list != null && !list.isEmpty()) {
			SysApplicationQuery query = new SysApplicationQuery();
			List<SysApplication> apps = sysApplicationMapper.getSysApplications(query);
			Map<Long, SysApplication> appMap = new HashMap<Long, SysApplication>();
			if (apps != null && !apps.isEmpty()) {
				for (SysApplication m : apps) {
					appMap.put(m.getNodeId(), m);
				}
			}
			for (FormDictTree bean : list) {
				bean.setApp(appMap.get(bean.getId()));
			}
		}
	}

	protected void initDepartments(List<FormDictTree> list) {
		if (list != null && !list.isEmpty()) {
			SysDepartmentQuery query = new SysDepartmentQuery();
			query.status(0);
			List<SysDepartment> depts = sysDepartmentMapper.getSysDepartments(query);
			Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
			if (depts != null && !depts.isEmpty()) {
				for (SysDepartment dept : depts) {
					deptMap.put(dept.getNodeId(), dept);
				}
			}
			for (FormDictTree bean : list) {
				bean.setDepartment(deptMap.get(bean.getId()));
			}
		}
	}

	public List<FormDictTree> list(FormDictTreeQuery query) {
		List<FormDictTree> list = formDictTreeMapper.getFormDictTrees(query);
		return list;
	}

	public void loadChildren(List<FormDictTree> treeList, long parentId) {
		logger.debug("--------------loadChildren---------");
		FormDictTree root = this.findById(parentId);
		if (root != null) {
			if (StringUtils.isNotEmpty(root.getTreeId())) {
				FormDictTreeQuery query = new FormDictTreeQuery();
				query.treeIdLike(root.getTreeId() + "%");
				query.setOrderBy(" E.TREEID asc ");
				List<FormDictTree> nodes = this.list(query);
				if (nodes != null && !nodes.isEmpty()) {
					Iterator<FormDictTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						FormDictTree bean = iter.next();
						if (bean.getId() != parentId) {
							treeList.add(bean);
						}
					}
				}
			} else {
				FormDictTreeQuery query = new FormDictTreeQuery();
				query.setParentId(Long.valueOf(parentId));
				List<FormDictTree> nodes = this.list(query);
				if (nodes != null && !nodes.isEmpty()) {
					Iterator<FormDictTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						FormDictTree bean = iter.next();
						treeList.add(bean);// 加入到数组
						loadChildren(treeList, bean.getId());// 递归遍历
					}
				}
			}
		}
	}

	public void loadChildren2(List<FormDictTree> list, long parentId) {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		List<FormDictTree> nodes = this.list(query);
		if (nodes != null && !nodes.isEmpty()) {
			for (FormDictTree node : nodes) {
				list.add(node);
				this.loadChildren2(list, node.getId());
			}
		}
	}

	protected void loadChildrenTreeListForDept(List<FormDictTree> treeList, long parentId, int status) {
		logger.debug("--------------loadChildrenTreeListForDept---------");
		FormDictTree root = this.findById(parentId);
		if (root != null) {
			logger.debug("dept name:" + root.getName());
			if (StringUtils.isNotEmpty(root.getTreeId())) {
				FormDictTreeQuery query = new FormDictTreeQuery();
				query.treeIdLike(root.getTreeId() + "%");
				query.setOrderBy(" E.TREEID asc ");
				List<FormDictTree> nodes = this.list(query);
				if (nodes != null && !nodes.isEmpty()) {
					// this.initDepartments(nodes);
					Iterator<FormDictTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						FormDictTree bean = iter.next();
						if (bean.getId() != parentId) {
							treeList.add(bean);
						}
					}
				}
			} else {
				FormDictTreeQuery query = new FormDictTreeQuery();
				query.setParentId(Long.valueOf(parentId));
				List<FormDictTree> nodes = this.list(query);
				if (nodes != null && !nodes.isEmpty()) {
					// this.initDepartments(nodes);
					Iterator<FormDictTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						FormDictTree bean = iter.next();
						treeList.add(bean);// 加入到数组
						loadChildrenTreeListForDept(treeList, bean.getId(), status);// 递归遍历
					}
				}
			}
		}
	}

	protected void loadChildrenTreeListForDept2(List<FormDictTree> list, long parentId, int status) {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		if (status != -1) {
			query.setDepartmentStatus(status);
		}
		List<FormDictTree> trees = this.list(query);
		if (trees != null && !trees.isEmpty()) {
			for (FormDictTree tt : trees) {
				list.add(tt);
				this.loadChildrenTreeListForDept2(list, tt.getId(), status);
			}
		}
	}

	public void loadFormDictTrees(List<FormDictTree> treeList, long parentId, int deep) {
		logger.debug("--------------loadFormDictTrees---------------");
		FormDictTree root = this.findById(parentId);
		if (root != null) {
			if (StringUtils.isNotEmpty(root.getTreeId())) {
				FormDictTreeQuery query = new FormDictTreeQuery();
				query.treeIdLike(root.getTreeId() + "%");
				query.setOrderBy(" E.TREEID asc ");
				List<FormDictTree> nodes = this.list(query);
				if (nodes != null && !nodes.isEmpty()) {
					this.initDepartments(nodes);
					this.initApplications(nodes);
					Iterator<FormDictTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						FormDictTree bean = iter.next();
						//if (bean.getId() != parentId) {
							String treeId = bean.getTreeId();
							StringTokenizer token = new StringTokenizer(treeId, "|");
							bean.setDeep(token.countTokens());
							treeList.add(bean);// 加入到数组
							// logger.debug("dept level:" + bean.getDeep());
						//}
					}
				}
			} else {
				FormDictTreeQuery query = new FormDictTreeQuery();
				query.setParentId(Long.valueOf(parentId));
				List<FormDictTree> nodes = this.list(query);
				if (nodes != null && !nodes.isEmpty()) {
					this.initDepartments(nodes);
					this.initApplications(nodes);
					Iterator<FormDictTree> iter = nodes.iterator();
					while (iter.hasNext()) {
						FormDictTree bean = iter.next();
						bean.setDeep(deep + 1);
						treeList.add(bean);// 加入到数组
						loadFormDictTrees(treeList, bean.getId(), bean.getDeep());// 递归遍历
					}
				}
			}
		}
	}

	public void loadFormDictTrees2(List<FormDictTree> treeList, long parentId, int deep) {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.setParentId(Long.valueOf(parentId));
		List<FormDictTree> nodes = this.list(query);
		if (nodes != null && !nodes.isEmpty()) {
			this.initDepartments(nodes);
			this.initApplications(nodes);
			Iterator<FormDictTree> iter = nodes.iterator();
			while (iter.hasNext()) {// 递归遍历
				FormDictTree bean = iter.next();
				bean.setDeep(deep + 1);
				treeList.add(bean);// 加入到数组
				loadFormDictTrees2(treeList, bean.getId(), bean.getDeep());
			}
		}
	}

	@Transactional
	public void save(FormDictTree bean) {
		String parentTreeId = null;
		if (bean.getParentId() != 0) {
			FormDictTree parent = this.findById(bean.getParentId());
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
			}
			formDictTreeMapper.insertFormDictTree(bean);

			if (bean.getParentId() == 4 && bean.getCode() != null) {// 基础数据
				if (sysDataItemService.getSysDataItemByName(bean.getCode()) == null) {
					SysDataItem dataItem = new SysDataItem();
					dataItem.setName(bean.getCode());
					dataItem.setQuerySQL(
							"select NAME as name, CODE as value from form_dictory where TYPEID = " + bean.getId());
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
	public void setFormDictTreeMapper(FormDictTreeMapper formDictTreeMapper) {
		this.formDictTreeMapper = formDictTreeMapper;
	}

	@Transactional
	public void sort(long parent, FormDictTree bean, int operate) {
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
	private void sortByForward(long parent, FormDictTree bean) {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.parentId(Long.valueOf(parent));
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		// 查找后一个对象
		List<FormDictTree> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			FormDictTree temp = (FormDictTree) list.get(0);
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
	private void sortByPrevious(long parent, FormDictTree bean) {
		FormDictTreeQuery query = new FormDictTreeQuery();
		query.parentId(Long.valueOf(parent));
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc ");

		// 查找前一个对象
		List<FormDictTree> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			FormDictTree temp = (FormDictTree) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public boolean update(FormDictTree bean) {
		FormDictTree model = this.findById(bean.getId());
		/**
		 * 如果节点移动了位置，即移动到别的节点下面去了
		 */
		if (model.getParentId() != bean.getParentId()) {
			List<FormDictTree> list = new ArrayList<FormDictTree>();
			this.loadChildren(list, bean.getId());
			if (!list.isEmpty()) {
				for (FormDictTree node : list) {
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
				FormDictTree oldParent = this.findById(model.getParentId());
				FormDictTree newParent = this.findById(bean.getParentId());
				if (oldParent != null && newParent != null && StringUtils.isNotEmpty(oldParent.getTreeId())
						&& StringUtils.isNotEmpty(newParent.getTreeId())) {
					TableModel tableModel = new TableModel();
					tableModel.setTableName("FORM_DICT_TREE");
					ColumnModel idColumn = new ColumnModel();
					idColumn.setColumnName("ID");
					idColumn.setJavaType("Long");
					tableModel.setIdColumn(idColumn);

					ColumnModel treeColumn = new ColumnModel();
					treeColumn.setColumnName("TREEID");
					treeColumn.setJavaType("String");
					tableModel.addColumn(treeColumn);

					for (FormDictTree node : list) {
						String treeId = node.getTreeId();
						if (StringUtils.isNotEmpty(treeId)) {
							treeId = StringTools.replace(treeId, oldParent.getTreeId(), newParent.getTreeId());
							idColumn.setValue(node.getId());
							treeColumn.setValue(treeId);
							DataServiceFactory.getInstance().updateTableData(tableModel);
						}
					}
				}
			}
		}

		if (bean.getParentId() != 0) {
			FormDictTree parent = this.findById(bean.getParentId());
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

		formDictTreeMapper.updateFormDictTree(bean);
		return true;
	}

	@Transactional
	public void updateTreeIds() {
		FormDictTree root = this.findById(1);
		if (root != null) {
			if (!StringUtils.equals(root.getTreeId(), "1|")) {
				root.setTreeId("1|");
				this.update(root);
			}
			List<FormDictTree> trees = this.getAllFormDictTreeList();
			if (trees != null && !trees.isEmpty()) {
				Map<Long, FormDictTree> dataMap = new HashMap<Long, FormDictTree>();
				for (FormDictTree tree : trees) {
					dataMap.put(tree.getId(), tree);
				}
				Map<Long, String> treeIdMap = new HashMap<Long, String>();
				for (FormDictTree tree : trees) {
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
		tableModel.setTableName("FORM_DICT_TREE");
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
				DataServiceFactory.getInstance().updateTableData(tableModel);
			}
		}
	}

}
