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

package com.glaf.base.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.base.modules.sys.model.DeptRolePostTreeNode;
import com.glaf.base.modules.sys.model.Post;
import com.glaf.base.modules.sys.query.PostQuery;

@Component
public interface PostMapper {

	void deletePostById(Long id);

	void deletePostByStringId(String id);

	void deletePosts(PostQuery query);

	/**
	 * 获取部门角色岗位列表
	 * 
	 * @return
	 */
	List<DeptRolePostTreeNode> getDeptRolePostTreeNodes();

	Post getPostById(Long id);

	Post getPostByStringId(String id);

	int getPostCount(PostQuery query);

	List<Post> getPosts(PostQuery query);

	List<String> getPostUsersByPostCodeList(@Param("postCodes") List<String> postCodes);

	void insertPost(Post model);

	void updatePost(Post model);

}
