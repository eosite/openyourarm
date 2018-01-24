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

package com.glaf.form.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.core.util.PageResult;
import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.query.FormDictTreeQuery;

@Transactional(readOnly = true)
public interface IFormDictTreeService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            FormDictTree
	 * @return boolean
	 */
	@Transactional
	boolean create(FormDictTree bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	@Transactional
	boolean delete(long id);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            FormDictTree
	 * @return boolean
	 */
	@Transactional
	boolean delete(FormDictTree bean);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] ids);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	FormDictTree findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return FormDictTree
	 */
	FormDictTree findByName(String name);

	/**
	 * 获取全部列表
	 * 
	 * @return List
	 */
	List<FormDictTree> getAllFormDictTreeList();

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 *            int 父ID
	 * @param status
	 *            int 状态
	 * @return List
	 */
	List<FormDictTree> getAllFormDictTreeListForDept(long parent, int status);

	/**
	 * 获取字典树型结构
	 * 
	 * @param query
	 * @return
	 */
	List<FormDictTree> getDictoryFormDictTrees(FormDictTreeQuery query);

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
	List<FormDictTree> getRelationFormDictTrees(String relationTable, String relationColumn, FormDictTreeQuery query);

	/**
	 * 获取树型列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	void getFormDictTree(List<FormDictTree> treeList, long parentId, int deep);

	/**
	 * 按树编号获取树节点
	 * 
	 * @param tree
	 * @return FormDictTree
	 */
	FormDictTree getFormDictTreeByCode(String code);
	
	/**
	 * 获取某个节点及其祖先
	 * @param id
	 * @return
	 */
	FormDictTree getFormDictTreeByIdWithAncestor(long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormDictTreeCountByQueryCriteria(FormDictTreeQuery query);

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 * 
	 * @return List
	 */
	List<FormDictTree> getFormDictTreeList(long parentId);

	/**
	 * 获取分页列表
	 * 
	 * @param parent
	 *            int
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getFormDictTreeList(long parentId, int pageNo, int pageSize);

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 *            int 父ID
	 * @param status
	 *            int 状态
	 * @return List
	 */
	List<FormDictTree> getFormDictTreeListForDept(long parentId, int status);

	/**
	 * 获取全部列表
	 * 
	 * @param parent
	 * 
	 * @return List
	 */
	List<FormDictTree> getFormDictTreeListWithChildren(long parentId);

	/**
	 * 获取父节点列表，如:根目录>A>A1>A11
	 * 
	 * @param tree
	 * @param int
	 *            id
	 */
	void getFormDictTreeParent(List<FormDictTree> tree, long id);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormDictTree> getFormDictTreesByQueryCriteria(int start, int pageSize, FormDictTreeQuery query);

	/**
	 * 获取树型列表
	 * 
	 * @param parent
	 *            int
	 * @return List
	 */
	void loadFormDictTrees(List<FormDictTree> treeList, long parentId, int deep);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            FormDictTree
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	void sort(long parent, FormDictTree bean, int operate);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            FormDictTree
	 * @return boolean
	 */
	@Transactional
	boolean update(FormDictTree bean);

	/**
	 * 更新树的treeId字段
	 * 
	 * @param treeMap
	 */
	@Transactional
	void updateTreeIds();

	/**
	 * 更新指定树的treeId字段
	 * 
	 * @param treeMap
	 */
	@Transactional
	void updateTreeIds(Map<Long, String> treeMap);

	void deleteAll(FormDictTreeQuery query);
}