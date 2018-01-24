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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.PostMapper;
import com.glaf.base.modules.sys.mapper.SysUserMapper;
import com.glaf.base.modules.sys.model.DeptRolePostTreeNode;
import com.glaf.base.modules.sys.model.Post;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.PostQuery;
import com.glaf.base.modules.sys.service.PostService;
import com.glaf.base.modules.sys.util.PostJsonFactory;
import com.glaf.core.base.TableModel;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.PageResult;

@Service("postService")
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
	protected final static Log logger = LogFactory.getLog(PostServiceImpl.class);

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PostMapper postMapper;

	protected SysUserMapper sysUserMapper;

	protected ITableDataService tableDataService;

	public PostServiceImpl() {

	}

	public int count(PostQuery query) {
		return postMapper.getPostCount(query);
	}

	@Transactional
	public boolean create(Post bean) {
		if (bean.getId() == 0) {
			bean.setId(idGenerator.nextId("SYS_POST"));
		}
		bean.setCreateDate(new Date());
		bean.setSort(0);
		if (StringUtils.isEmpty(bean.getCode())) {
			bean.setCode("post_" + String.valueOf(bean.getId()));
		}
		postMapper.insertPost(bean);
		return true;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		return true;
	}

	@Transactional
	public boolean delete(Post bean) {
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
			Post post = postMapper.getPostById(id);
			if (post != null && StringUtils.equals(post.getType(), "SYS")) {
				throw new RuntimeException("Can't delete sys post");
			} else {
				if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
					postMapper.deletePostByStringId(String.valueOf(id));
				} else {
					postMapper.deletePostById(id);
				}
			}
		}
	}

	@Transactional
	public void deleteByIds(List<Long> rowIds) {
		if (rowIds != null && !rowIds.isEmpty()) {
			PostQuery query = new PostQuery();
			query.rowIds(rowIds);
			postMapper.deletePosts(query);
		}
	}

	public Post findByCode(String code) {
		PostQuery query = new PostQuery();
		query.code(code);
		query.setOrderBy(" E.ID desc ");

		List<Post> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public Post findById(long id) {
		String cacheKey = "sys_post_" + id;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("post", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject json = JSON.parseObject(text);
					return PostJsonFactory.jsonToObject(json);
				} catch (Exception ex) {
					// Ignore error
				}
			}
		}

		Post post = null;
		if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			post = postMapper.getPostByStringId(String.valueOf(id));
		} else {
			post = postMapper.getPostById(id);
		}
		if (post != null && SystemConfig.getBoolean("use_query_cache")) {
			JSONObject json = post.toJsonObject();
			CacheFactory.put("post", cacheKey, json.toJSONString());
		}
		return post;
	}

	public Post findByName(String name) {
		PostQuery query = new PostQuery();
		query.name(name);
		query.setOrderBy(" E.ID desc ");

		List<Post> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	public Post getPost(Long id) {
		if (id == null) {
			return null;
		}
		return this.findById(id);
	}

	public int getPostCountByQueryCriteria(PostQuery query) {
		return postMapper.getPostCount(query);
	}

	public List<Post> getPostList() {
		PostQuery query = new PostQuery();
		query.setDeleteFlag(0);
		query.setOrderBy(" E.SORTNO desc ");

		List<Post> list = this.list(query);
		return list;
	}
	/**
	 * 获取部门角色岗位列表
	 */
	public List<DeptRolePostTreeNode> getDeptRolePostTreeNodes(){
		return postMapper.getDeptRolePostTreeNodes();
	}
	
	public List<String> getPostUsersByPostCodeList(List<String> postCodes){
		return postMapper.getPostUsersByPostCodeList(postCodes);
	}
	public PageResult getPostList(int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		PostQuery query = new PostQuery();
		query.setDeleteFlag(0);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}

		query.setOrderBy(" E.SORTNO desc");

		int start = pageSize * (pageNo - 1);
		List<Post> list = this.getPostsByQueryCriteria(start, pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public PageResult getPostList(int pageNo, int pageSize, PostQuery query) {
		// 计算总数
		PageResult pager = new PageResult();

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}

		query.setOrderBy(" E.SORTNO asc ");

		int start = pageSize * (pageNo - 1);
		List<Post> list = this.getPostsByQueryCriteria(start, pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<Post> getPostsByQueryCriteria(int start, int pageSize, PostQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Post> rows = sqlSessionTemplate.selectList("getPosts", query, rowBounds);
		return rows;
	}

	public List<SysUser> getSysUsers(long postId) {
		return sysUserMapper.getSysUsersByPostId(postId);
	}

	public List<Post> list(PostQuery query) {
		List<Post> list = postMapper.getPosts(query);
		return list;
	}

	@Transactional
	public void save(Post post) {
		if (post.getId() == 0) {
			post.setId(idGenerator.nextId());
			post.setCreateDate(new Date());
			postMapper.insertPost(post);
		} else {
			post.setUpdateDate(new Date());
			postMapper.updatePost(post);
		}
	}

	@Transactional
	public void saveUsers(long postId, List<String> actorIds) {
		TableModel table = new TableModel();
		table.setTableName("sys_post_user");
		table.addLongColumn("POSTID", postId);
		tableDataService.deleteTableData(table);

		if (actorIds != null && !actorIds.isEmpty()) {
			for (String actorId : actorIds) {
				TableModel t = new TableModel();
				t.setTableName("sys_post_user");
				t.addLongColumn("ID", idGenerator.nextId("sys_post_user"));
				t.addLongColumn("POSTID", postId);
				t.addStringColumn("USERID", actorId);
				tableDataService.insertTableData(t);
			}
		}
	}

	@Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setPostMapper(PostMapper postMapper) {
		this.postMapper = postMapper;
	}

	@Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	@Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	/**
	 * 排序
	 * 
	 * @param bean
	 *            Post
	 * @param operate
	 *            int 操作
	 */
	@Transactional
	public void sort(Post bean, int operate) {
		if (bean == null)
			return;
		if (operate == SysConstants.SORT_PREVIOUS) {// 前移
			sortByPrevious(bean);
		} else if (operate == SysConstants.SORT_FORWARD) {// 后移
			sortByForward(bean);
		}
	}

	/**
	 * 向后移动排序
	 * 
	 * @param bean
	 */
	private void sortByForward(Post bean) {
		PostQuery query = new PostQuery();
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORTNO desc ");
		List<Post> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			Post temp = (Post) list.get(0);
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
	private void sortByPrevious(Post bean) {
		PostQuery query = new PostQuery();
		query.setSortGreaterThan(bean.getSort());

		List<Post> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			Post temp = (Post) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean

			temp.setSort(i);
			this.update(temp);// 更新temp
		}
	}

	@Transactional
	public boolean update(Post bean) {
		bean.setUpdateDate(new Date());
		postMapper.updatePost(bean);
		String cacheKey = "sys_post_" + bean.getId();
		CacheFactory.remove("post", cacheKey);
		return true;
	}

}
