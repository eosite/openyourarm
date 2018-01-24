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

package com.glaf.base.modules.sys.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.DeptRolePostTreeNode;
import com.glaf.base.modules.sys.model.Post;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.PostQuery;
import com.glaf.core.util.PageResult;

@Transactional(readOnly = true)
public interface PostService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            Post
	 * @return boolean
	 */
	@Transactional
	boolean create(Post bean);

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
	 *            Post
	 * @return boolean
	 */
	@Transactional
	boolean delete(Post bean);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	boolean deleteAll(long[] ids);

	/**
	 * 按code查找对象
	 * 
	 * @param name
	 *            String
	 * @return Post
	 */
	Post findByCode(String code);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	Post findById(long id);

	/**
	 * 按名称查找对象
	 * 
	 * @param name
	 *            String
	 * @return Post
	 */
	Post findByName(String name);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getPostCountByQueryCriteria(PostQuery query);

	/**
	 * 获取列表
	 * 
	 * @return List
	 */
	List<Post> getPostList();

	/**
	 * 获取分页列表
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @return
	 */
	PageResult getPostList(int pageNo, int pageSize);

	List<SysUser> getSysUsers(long postId);

	/**
	 * 获取分页列表
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @param query
	 * @return
	 */
	PageResult getPostList(int pageNo, int pageSize, PostQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Post> getPostsByQueryCriteria(int start, int pageSize, PostQuery query);
	
	/**
	 * 获取部门角色岗位列表
	 * @return
	 */
	List<DeptRolePostTreeNode> getDeptRolePostTreeNodes();
	/**
	 * 获取岗位用户
	 * @param postCodes
	 * @return
	 */
	List<String> getPostUsersByPostCodeList(List<String> postCodes);

	/**
	 * 排序
	 * 
	 * @param bean
	 *            Post
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	void sort(Post bean, int operate);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            Post
	 * @return boolean
	 */
	@Transactional
	boolean update(Post bean);

	@Transactional
	void saveUsers(long postId, List<String> actorIds);

}