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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.glaf.base.business.TreeHelper;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.mapper.SysAccessMapper;
import com.glaf.base.modules.sys.mapper.SysApplicationMapper;
import com.glaf.base.modules.sys.mapper.SysTreeMapper;
import com.glaf.base.modules.sys.model.RealmInfo;
import com.glaf.base.modules.sys.model.SysAccess;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysApplicationQuery;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.sys.util.SysApplicationJsonFactory;
import com.glaf.core.base.BaseTree;
import com.glaf.core.base.BlobItem;
import com.glaf.core.base.TreeModel;
import com.glaf.core.context.ApplicationContext;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.identity.Agent;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;

@Service("sysApplicationService")
@Transactional(readOnly = true)
public class SysApplicationServiceImpl implements SysApplicationService {
	protected final static Log logger = LogFactory.getLog(SysApplicationServiceImpl.class);

	protected IdGenerator idGenerator;

	protected IBlobService blobService;

	protected EntityService entityService;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysAccessMapper sysAccessMapper;

	protected SysTreeMapper sysTreeMapper;

	protected SysTreeService sysTreeService;

	protected SysRoleService sysRoleService;

	protected SysUserService sysUserService;

	public SysApplicationServiceImpl() {

	}

	@Transactional
	public void batchCreate(List<SysApplication> rows) {
		if (rows != null && !rows.isEmpty()) {
			for (SysApplication bean : rows) {
				if (bean.getNode() != null && bean.getNode().getId() == 0) {
					this.create(bean);
				}
			}
		}
	}

	public int count(SysApplicationQuery query) {
		return sysApplicationMapper.getSysApplicationCount(query);
	}

	@Transactional
	public boolean create(SysApplication app) {
		boolean ret = false;
		if (app.getNode() != null) {
			SysTree tree = app.getNode();
			tree.setCode(app.getCode());
			tree.setDiscriminator("A");
			tree.setCreateBy(app.getCreateBy());
			tree.setUrl(app.getUrl());
			sysTreeService.create(tree);

			app.setId(tree.getId());
			app.setNodeId(tree.getId());

			if (StringUtils.isEmpty(app.getCode()) || StringUtils.startsWith(app.getCode(), "app_")) {
				app.setCode("app_" + app.getId());
			}

			app.setSort(0);// 设置排序号为刚插入的id值
			app.setCreateDate(new Date());
			if (app.getLinkFileContent() != null) {
				app.setLinkFileId("sys_app_" + UUID32.getUUID());
			}
			if (app.getPrintFileContent() != null) {
				app.setPrintFileId("sys_app_print_" + UUID32.getUUID());
			}
			if (StringUtils.endsWithIgnoreCase(app.getLinkFileName(), ".cpt")) {
				app.setUrl("/mx/menu/jump?menuId=" + RequestUtils.encodeString(app.getId() + ""));
			}

			sysApplicationMapper.insertSysApplication(app);

			if (SystemConfig.getBoolean("use_query_cache")) {
				CacheFactory.clear("application");
				CacheFactory.clear("tree");
			}

			if (app.getLinkFileContent() != null) {
				BlobItem dataFile = new BlobItemEntity();
				dataFile.setLastModified(System.currentTimeMillis());
				dataFile.setCreateBy(app.getCreateBy());
				dataFile.setFileId(app.getLinkFileId());
				dataFile.setData(app.getLinkFileContent());
				dataFile.setFilename(app.getLinkFileName());
				dataFile.setName(app.getLinkFileName());
				dataFile.setSize(app.getLinkFileContent().length);
				dataFile.setType(app.getLinkType());
				dataFile.setStatus(9);
				dataFile.setObjectId("sys_app");
				dataFile.setObjectValue("sys_app_" + app.getId());
				dataFile.setServiceKey("sys_app_file");
				blobService.insertBlob(dataFile);
			}

			if (app.getPrintFileContent() != null) {
				BlobItem dataFile = new BlobItemEntity();
				dataFile.setLastModified(System.currentTimeMillis());
				dataFile.setCreateBy(app.getCreateBy());
				dataFile.setFileId(app.getPrintFileId());
				dataFile.setData(app.getPrintFileContent());
				dataFile.setFilename(app.getPrintFileName());
				dataFile.setName(app.getPrintFileName());
				dataFile.setSize(app.getPrintFileContent().length);
				dataFile.setType(app.getPrintType());
				dataFile.setStatus(9);
				dataFile.setObjectId("sys_app_print");
				dataFile.setObjectValue("sys_app_print_" + app.getId());
				dataFile.setServiceKey("sys_app_print_file");
				blobService.insertBlob(dataFile);
			}
			ret = true;
		}
		return ret;
	}

	@Transactional
	public boolean delete(long id) {
		this.deleteById(id);
		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
		}
		return true;
	}

	@Transactional
	public boolean delete(SysApplication bean) {
		this.deleteById(bean.getId());
		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
		}
		return true;
	}

	@Transactional
	public boolean deleteAll(long[] ids) {
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				this.deleteById(id);
			}
			if (SystemConfig.getBoolean("use_query_cache")) {
				CacheFactory.clear("application");
				CacheFactory.clear("tree");
			}
		}
		return true;
	}

	@Transactional
	public void deleteById(Long appId) {
		if (appId != null && appId > 0) {
			SysApplication app = this.getSysApplication(appId);
			if (app != null) {
				SysTree tree = sysTreeService.findById(app.getNodeId());
				if (tree != null) {
					List<SysTree> treeList = sysTreeService.getSysTreeList(tree.getId());
					if (treeList != null && !treeList.isEmpty()) {
						throw new RuntimeException("tree node exist children ");
					}
					sysAccessMapper.deleteSysAccessByAppId(appId);
					sysApplicationMapper.deleteSysApplicationById(appId);
					sysTreeMapper.deleteSysTreeById(app.getNodeId());
					if (SystemConfig.getBoolean("use_query_cache")) {
						CacheFactory.clear("application");
						CacheFactory.clear("tree");
					}
				}
			}
		}
	}

	/**
	 * 按编码查找对象
	 * 
	 * @param code
	 *            String
	 * @return SysApplication
	 */
	public SysApplication findByCode(String code) {
		SysApplicationQuery query = new SysApplicationQuery();
		query.code(code);

		List<SysApplication> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			SysApplication sysApplication = list.get(0);
			SysTree node = sysTreeService.findById(sysApplication.getNodeId());
			sysApplication.setNode(node);
			return sysApplication;
		}

		return null;
	}

	public SysApplication findById(long id) {
		String cacheKey = "sys_app_" + id;

		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("application", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					com.alibaba.fastjson.JSONObject json = JSON.parseObject(text);
					SysApplication app = SysApplicationJsonFactory.jsonToObject(json);
					if (app != null && app.getNodeId() > 0) {
						SysTree node = sysTreeService.findById(app.getNodeId());
						app.setNode(node);
						if (node != null) {
							SysApplication parent = this.getSysApplicationByNodeId(node.getParentId());
							app.setParent(parent);
						}
					}
					return app;
				} catch (Exception ex) {
				}
			}
		}

		SysApplication app = sysApplicationMapper.getSysApplicationById(id);
		if (app != null && app.getNodeId() > 0) {
			SysTree node = sysTreeService.findById(app.getNodeId());
			app.setNode(node);
			if (node != null) {
				SysApplication parent = this.getSysApplicationByNodeId(node.getParentId());
				app.setParent(parent);
			}
		}

		if (app != null && SystemConfig.getBoolean("use_query_cache")) {
			com.alibaba.fastjson.JSONObject json = app.toJsonObject();
			CacheFactory.put("application", cacheKey, json.toJSONString());
		}

		return app;
	}

	public SysApplication findByName(String name) {
		SysApplicationQuery query = new SysApplicationQuery();
		query.name(name);

		List<SysApplication> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			SysApplication sysApplication = list.get(0);
			SysTree node = sysTreeService.findById(sysApplication.getNodeId());
			sysApplication.setNode(node);
			return sysApplication;
		}

		return null;
	}

	public SysApplication findByNodeId(long nodeId) {
		SysApplicationQuery query = new SysApplicationQuery();
		query.nodeId(nodeId);

		List<SysApplication> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			SysApplication sysApplication = list.get(0);
			SysTree node = sysTreeService.findById(sysApplication.getNodeId());
			sysApplication.setNode(node);
			return sysApplication;
		}

		return null;
	}

	public List<SysApplication> getAccessAppList(long parentId, SysUser user) {
		long parentAppId = parentId;
		SysApplication parentApp = findById(parentId);
		if (parentApp != null) {
			parentAppId = parentApp.getNode().getId();
		}
		logger.debug("parent node:" + parentAppId);
		StringBuilder sb = new StringBuilder();
		sb.append("cache_app_").append(parentId).append("_").append(user.getActorId());
		String cacheKey = sb.toString();

		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("application", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				// logger.debug("json array:"+text);
				try {
					com.alibaba.fastjson.JSONArray array = JSON.parseArray(text);
					if (array != null) {
						List<SysApplication> list = SysApplicationJsonFactory.arrayToList(array);
						return list;
					}
				} catch (Exception ex) {
				}
			}
		}

		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(parentAppId);
		query.setOrderBy(" E.LOCKED asc, E.SORT asc ");
		query.setLocked(0);
		query.setDeleteFlag(0);
		List<Long> nodeIds = new ArrayList<Long>();
		nodeIds.add(-1L);

		List<SysApplication> apps = null;
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			apps = sysApplicationMapper.getSysApplicationsByUserId_oracle(user.getActorId());
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			apps = sysApplicationMapper.getSysApplicationsByUserId_postgresql(user.getActorId());
		} else {
			apps = sysApplicationMapper.getSysApplicationsByUserId(user.getActorId());
		}

		if (apps != null && !apps.isEmpty()) {
			for (SysApplication app : apps) {
				nodeIds.add(app.getNodeId());
			}
		}

		query.nodeIds(nodeIds);
		List<SysApplication> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				com.alibaba.fastjson.JSONArray array = SysApplicationJsonFactory.listToArray(list);
				CacheFactory.put("application", cacheKey, array.toJSONString());
				// logger.debug(array.toJSONString());
			}
		}
		return list;
	}

	public List<SysApplication> getApplicationList() {
		SysApplicationQuery query = new SysApplicationQuery();
		query.setDeleteFlag(0);
		query.setOrderBy(" E.LOCKED asc, E.SORT asc ");
		return this.list(query);
	}

	public PageResult getApplicationList(int pageNo, int pageSize, SysApplicationQuery query) {
		// 计算总数
		PageResult pager = new PageResult();

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.LOCKED asc, E.SORT asc ");

		int start = pageSize * (pageNo - 1);
		List<SysApplication> list = this.getSysApplicationsByQueryCriteria(start, pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SysApplication> getApplicationList(long parentId) {
		String cacheKey = "sys_apps_" + parentId;

		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("application", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					com.alibaba.fastjson.JSONArray array = JSON.parseArray(text);
					if (array != null) {
						List<SysApplication> list = SysApplicationJsonFactory.arrayToList(array);
						return list;
					}
				} catch (Exception ex) {
				}
			}
		}
		long parentAppId = parentId;
		SysApplication parentApp = findById(parentId);
		if (parentApp != null) {
			parentAppId = parentApp.getNode().getId();
		}

		logger.debug("->parent node:" + parentAppId);

		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(parentAppId);
		query.setLocked(0);
		query.setDeleteFlag(0);
		query.setOrderBy(" E.LOCKED asc, E.SORT asc");
		List<SysApplication> apps = this.list(query);

		if (apps != null && !apps.isEmpty()) {
			for (SysApplication bean : apps) {
				if (StringUtils.isEmpty(bean.getUrl())) {
					if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
						String url = SysConstants.WPF_DEF_URL + RequestUtils.encodeString(String.valueOf(bean.getId()));
						bean.setUrl(url);
						// logger.debug("-------------------WPF-------------");
					} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
							&& StringUtils.isNotEmpty(bean.getPageId())) {
						String url = SysConstants.FORM_DEF_URL + bean.getPageId();
						bean.setUrl(url);
						// logger.debug("-------------------JAVA-------------");
					}
				}
			}
		}

		if (apps != null && SystemConfig.getBoolean("use_query_cache")) {
			com.alibaba.fastjson.JSONArray array = SysApplicationJsonFactory.listToArray(apps);
			CacheFactory.put("application", cacheKey, array.toJSONString());
		}

		logger.debug("----------------apps size:" + apps.size());
		return apps;
	}

	public PageResult getApplicationList(long parentId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysApplicationQuery query = new SysApplicationQuery();
		query.setDeleteFlag(0);
		query.parentId(parentId);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.LOCKED asc, E.SORT asc ");

		int start = pageSize * (pageNo - 1);
		List<SysApplication> list = this.getSysApplicationsByQueryCriteria(start, pageSize, query);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	/**
	 * 获取某个模块的角色及用户
	 * 
	 * @param appId
	 * @return
	 */
	public List<SysRole> getApplicationRoleWithUsers(long appId) {
		List<SysRole> roles = sysRoleService.getSysRolesByAppId(appId);
		if (roles != null && !roles.isEmpty()) {
			for (SysRole role : roles) {
				List<SysUser> users = sysUserService.getSysUsersByRoleId(role.getId());
				role.setUsers(users);
			}
		}
		return roles;
	}

	public String getMenu(long parent, SysUser user) {
		StringBuilder menu = new StringBuilder(5000);
		List<SysApplication> list = getAccessAppList(parent, user);
		if (list == null || list.isEmpty()) {
			if (user.isSystemAdmin()) {
				list = getApplicationList(parent);
			}
		}
		if (list != null && list.size() > 0) {
			Iterator<SysApplication> iter = list.iterator();
			while (iter.hasNext()) {
				SysApplication bean = (SysApplication) iter.next();
				menu.append("<li>");
				menu.append("<a href=\"javascript:jump('");
				// System.out.println("ContextUtil.getContextPath():"+ContextUtil.getContextPath());
				if (bean.getUrl() != null && bean.getUrl().startsWith("/")) {
					if (ApplicationContext.getContextPath() != null) {
						menu.append(ApplicationContext.getContextPath());
					}
				}
				// menu.append(bean.getUrl());

				if (StringUtils.isNotEmpty(bean.getUrl())) {
					menu.append(bean.getUrl());
					if (StringUtils.startsWith(bean.getUrl(), "/mx/form/page/viewPage?isPublish=true")) {
						String new_url = StringTools.replace(bean.getUrl(), "isPublish=true&", "");
						menu.append(new_url);
					} else {
						menu.append(bean.getUrl());
					}
				} else {
					if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
						String url = SysConstants.WPF_DEF_URL + RequestUtils.encodeString(String.valueOf(bean.getId()));
						// treeModel.setUrl(url);
						menu.append(url);
					} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
							&& StringUtils.isNotEmpty(bean.getPageId())) {
						String url = SysConstants.FORM_DEF_URL + bean.getPageId();
						bean.setUrl(url);
						// logger.debug("-------------------JAVA-------------");
					}
				}

				menu.append("',");
				menu.append(bean.getShowMenu());
				menu.append(");\">");
				menu.append(bean.getName()).append("</a>\n");

				List<SysApplication> sonNode = getAccessAppList(bean.getId(), user);
				if (sonNode == null || sonNode.isEmpty()) {
					if (user.isSystemAdmin()) {
						sonNode = getApplicationList(bean.getId());
					}
				}
				if (sonNode != null && sonNode.size() > 0) {// 有子菜单
					menu.append("<ul>");
					menu.append(getMenu(bean.getId(), user));
					menu.append("</ul>");
				}
				menu.append("</li>").append("<li></li>\n");
			}
		}
		return menu.toString();
	}

	public List<RealmInfo> getRealmInfos() {
		Map<String, Object> params = new HashMap<String, Object>();
		return sysApplicationMapper.getRealmInfos(params);
	}

	public JSONArray getRoleMenu(String roleCode) {
		logger.debug("-----------------roleCode--------------");
		JSONArray array = new JSONArray();
		List<SysApplication> apps = this.getSysApplicationsByRoleCode(roleCode);
		if (apps != null && !apps.isEmpty()) {
			List<Long> nodeIds = new ArrayList<Long>();
			for (SysApplication app : apps) {
				nodeIds.add(app.getNodeId());
			}
			SysTreeQuery query = new SysTreeQuery();
			query.nodeIds(nodeIds);
			query.setDeleteFlag(0);
			List<SysTree> treeList = sysTreeService.getApplicationSysTrees(query);
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			for (SysTree tree : treeList) {
				tree.setLocked(0);
				treeModels.add(tree);
			}
			TreeHelper treeHelper = new TreeHelper();
			array = treeHelper.getTreeJSONArray(treeModels);
		}
		return array;
	}

	/**
	 * 获取角色菜单之json对象
	 * 
	 * @param serviceUrl
	 * @param roleCode
	 * @return
	 */
	public JSONArray getRoleMenu(String serviceUrl, String roleCode) {
		JSONArray array = new JSONArray();
		List<SysApplication> apps = this.getSysApplicationsByRoleCode(roleCode);
		if (apps != null && !apps.isEmpty()) {
			Map<Long, String> urlMap = new HashMap<Long, String>();
			List<Long> nodeIds = new ArrayList<Long>();
			for (SysApplication bean : apps) {
				nodeIds.add(bean.getNodeId());
				if (StringUtils.isNotEmpty(bean.getUrl())) {
					if (StringUtils.startsWith(bean.getUrl(), "http://")
							|| StringUtils.startsWith(bean.getUrl(), "https://")) {
						urlMap.put(bean.getNodeId(), bean.getUrl());
					} else {
						if (StringUtils.startsWith(bean.getUrl(), "/")) {
							urlMap.put(bean.getNodeId(), serviceUrl + bean.getUrl());
						} else {
							urlMap.put(bean.getNodeId(), serviceUrl + "/" + bean.getUrl());
						}
					}
				} else {
					if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
						String url = SysConstants.WPF_DEF_URL + RequestUtils.encodeString(String.valueOf(bean.getId()));
						// treeModel.setUrl(url);
						urlMap.put(bean.getNodeId(), serviceUrl + url);
					} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
							&& StringUtils.isNotEmpty(bean.getPageId())) {
						String url = SysConstants.FORM_DEF_URL + bean.getPageId();
						bean.setUrl(url);
						// logger.debug("-------------------JAVA-------------");
					}
				}
			}

			logger.debug("urlMap:" + urlMap);

			SysTreeQuery query = new SysTreeQuery();
			query.nodeIds(nodeIds);
			query.setDeleteFlag(0);
			List<SysTree> treeList = sysTreeService.getApplicationSysTrees(query);
			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			for (SysTree tree : treeList) {
				tree.setLocked(0);
				tree.setUrl(urlMap.get(tree.getId()));
				// logger.debug(tree.getUrl());
				treeModels.add(tree);
			}
			logger.debug("treeModels:" + treeModels.size());
			TreeHelper treeHelper = new TreeHelper();
			array = treeHelper.getTreeJSONArray(treeModels);
		}
		return array;
	}

	public SysApplication getSysApplication(Long id) {
		if (id == null) {
			return null;
		}
		SysApplication sysApplication = sysApplicationMapper.getSysApplicationById(id);
		if (sysApplication != null) {
			SysTree node = sysTreeService.findById(sysApplication.getNodeId());
			sysApplication.setNode(node);
		}
		return sysApplication;
	}

	/**
	 * 通过节点编号获取模块信息
	 * 
	 * @param nodeId
	 * @return
	 */
	public SysApplication getSysApplicationByNodeId(long nodeId) {
		String cacheKey2 = "sys_app2_" + nodeId;

		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("application", cacheKey2);
			if (StringUtils.isNotEmpty(text)) {
				try {
					com.alibaba.fastjson.JSONObject json = JSON.parseObject(text);
					SysApplication app = SysApplicationJsonFactory.jsonToObject(json);
					if (app != null) {
						return app;
					}
				} catch (Exception ex) {
				}
			}
		}

		SysApplicationQuery query = new SysApplicationQuery();
		query.nodeId(nodeId);
		query.setDeleteFlag(0);
		query.setOrderBy(" E.ID asc ");

		List<SysApplication> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			SysApplication app = list.get(0);
			if (SystemConfig.getBoolean("use_query_cache")) {
				CacheFactory.put("application", cacheKey2, app.toJsonObject().toJSONString());
			}
			return app;
		}

		return null;
	}

	/**
	 * 获取某个用户的全部模块列表
	 * 
	 * @return List
	 */
	public List<SysApplication> getSysApplicationByUserId(String actorId) {
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return sysApplicationMapper.getSysApplicationsByUserId_oracle(actorId);
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			return sysApplicationMapper.getSysApplicationsByUserId_postgresql(actorId);
		}
		return sysApplicationMapper.getSysApplicationsByUserId(actorId);
	}

	public int getSysApplicationCountByQueryCriteria(SysApplicationQuery query) {
		return sysApplicationMapper.getSysApplicationCount(query);
	}

	public List<SysApplication> getSysApplicationsByQueryCriteria(int start, int pageSize, SysApplicationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysApplication> list = sqlSessionTemplate.selectList("getSysApplications", query, rowBounds);
		if (list != null && !list.isEmpty()) {
			this.initTrees(list);
		}
		return list;
	}

	public List<SysApplication> getSysApplicationsByRoleCode(String roleCode) {
		return sysApplicationMapper.getSysApplicationsByRoleCode(roleCode);
	}

	public List<SysApplication> getSysApplicationsByRoleId(long roleId) {
		return sysApplicationMapper.getSysApplicationsByRoleId(roleId);
	}

	/**
	 * 获取用户某个分类下的全部分类节点
	 * 
	 * @param parent
	 *            父节点编号
	 * @param actorId
	 *            用户登录账号
	 * @return
	 */
	public List<TreeModel> getTopLevelTreeModels(long parentId, String actorId) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		SysUser user = sysUserService.findByAccountWithAll(actorId);
		if (user != null) {
			this.loadTreeModels(treeModels, parentId, user);
		}
		return treeModels;
	}

	/**
	 * 获取用户某个分类下的顶级菜单节点
	 * 
	 * @param appCode
	 *            应用编码
	 * @param userId
	 *            用户登录账号
	 * @return
	 */
	public List<TreeModel> getTopLevelTreeModels(String appCode, String actorId) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		SysApplication sysApplication = this.findByCode(appCode);
		long parentId = sysApplication.getId();
		SysUser user = sysUserService.findByAccountWithAll(actorId);
		if (user != null) {
			this.loadTreeModels(treeModels, parentId, user);
		}
		return treeModels;
	}

	public TreeModel getTreeModelByAppId(long appId) {
		SysApplication bean = this.findById(appId);
		if (bean != null) {
			TreeModel treeModel = sysTreeService.findById(bean.getNodeId());
			treeModel.setCode(bean.getCode());
			treeModel.setName(bean.getName());
			treeModel.setLocked(bean.getLocked());
			treeModel.setDescription(bean.getDesc());
			// treeModel.setUrl(bean.getUrl());
			if (StringUtils.isNotEmpty(bean.getUrl())) {
				treeModel.setUrl(bean.getUrl());
				if (StringUtils.startsWith(bean.getUrl(), "/mx/form/page/viewPage?isPublish=true")) {
					String new_url = StringTools.replace(bean.getUrl(), "isPublish=true&", "");
					treeModel.setUrl(new_url);
				}
			} else {
				if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
					String url = SysConstants.WPF_DEF_URL + RequestUtils.encodeString(String.valueOf(bean.getId()));
					treeModel.setUrl(url);
				} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
						&& StringUtils.isNotEmpty(bean.getPageId())) {
					String url = SysConstants.FORM_DEF_URL + bean.getPageId();
					bean.setUrl(url);
					// logger.debug("-------------------JAVA-------------");
				}
			}
			treeModel.setSortNo(bean.getSort());
			return treeModel;
		}
		return null;
	}

	/**
	 * 获取用户某个分类下的全部分类节点
	 * 
	 * @param parent
	 *            父节点编号
	 * @param actorId
	 *            用户登录账号
	 * @return
	 */
	public List<TreeModel> getTreeModels(long parentId, String actorId) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		SysUser user = sysUserService.findByAccountWithAll(actorId);
		if (user != null) {
			this.loadChildrenTreeModels(treeModels, parentId, user);
		}
		return treeModels;
	}

	/**
	 * 获取某个角色编码的全部分类节点（包含已经失效的菜单）
	 * 
	 * @param roleCode
	 *            角色编码
	 * @return
	 */
	public List<TreeModel> getTreeModels(String roleCode) {
		List<TreeModel> treeModels = new ArrayList<TreeModel>();
		List<SysApplication> apps = this.getSysApplicationsByRoleCode(roleCode);
		if (apps != null && !apps.isEmpty()) {
			List<Long> nodeIds = new ArrayList<Long>();
			for (SysApplication app : apps) {
				nodeIds.add(app.getNodeId());
			}
			SysTreeQuery query = new SysTreeQuery();
			query.nodeIds(nodeIds);
			query.setDeleteFlag(0);
			List<SysTree> treeList = sysTreeService.getApplicationSysTrees(query);
			for (SysTree tree : treeList) {
				treeModels.add(tree);
			}
		}
		return treeModels;
	}

	public List<SysTree> getTreeWithApplicationList(long parentId) {
		List<SysApplication> apps = this.getApplicationList();
		Map<Long, SysApplication> appMap = new HashMap<Long, SysApplication>();
		if (apps != null && !apps.isEmpty()) {
			for (SysApplication app : apps) {
				appMap.put(app.getNodeId(), app);
			}
		}
		List<SysTree> trees = new ArrayList<SysTree>();
		SysTree root = sysTreeService.findById(parentId);
		SysTreeQuery query = new SysTreeQuery();
		query.treeIdLike(root.getTreeId());
		List<SysTree> list = sysTreeMapper.getSysTrees(query);
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
				if (disableMap.get(tree.getId()) != null) {
					continue;
				}
				if (disableMap.get(tree.getParentId()) != null) {
					continue;
				}
				if (tree.getParentId() == parentId) {
					// tree.setParentId(0);
				}
				if (tree.getLocked() == 0 && tree.getDeleteFlag() == 0) {
					SysApplication app = appMap.get(tree.getId());
					if (app != null && app.getLocked() == 0 && app.getDeleteFlag() == 0) {
						tree.setApp(app);
						trees.add(tree);
					}
				}
			}
		}

		return trees;
	}

	public JSONArray getUserMenu(long parent, String actorId) {
		JSONArray array = new JSONArray();
		SysUser user = sysUserService.findByAccountWithAll(actorId);
		if (user != null) {
			Map<Long, String> urlMap = new HashMap<Long, String>();
			SysApplicationQuery queryx = new SysApplicationQuery();
			queryx.setDeleteFlag(0);
			List<SysApplication> apps = this.list(queryx);
			for (SysApplication bean : apps) {
				if (StringUtils.isEmpty(bean.getUrl())) {
					if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
						String url = SysConstants.WPF_DEF_URL + RequestUtils.encodeString(String.valueOf(bean.getId()));
						urlMap.put(bean.getNodeId(), url);
					} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
							&& StringUtils.isNotEmpty(bean.getPageId())) {
						String url = SysConstants.FORM_DEF_URL + bean.getPageId();
						bean.setUrl(url);
						// logger.debug("-------------------JAVA-------------");
					}
				} else {
					urlMap.put(bean.getNodeId(), bean.getUrl());
				}
			}

			List<SysTree> treeList = null;
			logger.debug("appId=" + parent);
			SysApplication app = this.findById(parent);
			SysTreeQuery query = new SysTreeQuery();
			query.setDeleteFlag(0);
			// logger.debug("node=" + app.getNode());
			String treeId = app.getNode().getTreeId();
			logger.debug("treeId=" + treeId);
			query.treeId(treeId);
			query.treeIdLike(treeId + "%");
			if (!user.isSystemAdmin()) {
				List<String> actorIds = new ArrayList<String>();
				List<Object> rows = entityService.getList("getAgents", actorId);
				if (rows != null && !rows.isEmpty()) {
					for (Object object : rows) {
						if (object instanceof Agent) {
							Agent agent = (Agent) object;
							if (!agent.isValid()) {
								continue;
							}
							switch (agent.getAgentType()) {
							case 0:// 全局代理
								actorIds.add(agent.getAssignFrom());
								break;
							default:
								break;
							}
						}
					}
				}
				if (!actorIds.isEmpty()) {
					actorIds.add(actorId);
					query.setActorIds(actorIds);
				} else {
					query.setActorId(actorId);
				}
				logger.debug("treeId=" + query.getTreeId());
				logger.debug("treeIdLike=" + query.getTreeIdLike());
				treeList = sysTreeMapper.getTreeListByUsers(query);
			} else {
				treeList = sysTreeMapper.getTreeList(query);
			}

			List<TreeModel> treeModels = new ArrayList<TreeModel>();
			for (SysTree tree : treeList) {
				if (StringUtils.isEmpty(tree.getUrl())) {
					tree.setUrl(urlMap.get(tree.getId()));
				}
				treeModels.add(tree);
			}
			// logger.debug("urlMap=" + urlMap);
			TreeHelper treeHelper = new TreeHelper();
			array = treeHelper.getTreeJSONArray(treeModels);
			logger.debug("-------------------user menu-----------------------");
			logger.debug(array.toString('\n'));
		}
		return array;
	}

	protected JSONArray getUserMenu(long parent, SysUser user) {
		JSONArray array = new JSONArray();
		if (user != null) {
			List<SysApplication> list = null;
			if (user.isSystemAdmin()) {
				logger.debug("#admin user=" + user.getName());
				list = getApplicationList(parent);
			} else {
				logger.debug("#user=" + user.getName());
				list = getAccessAppList(parent, user);
				// logger.debug("#app list=" + list);
			}
			if (list != null && list.size() > 0) {
				Iterator<SysApplication> iter = list.iterator();
				while (iter.hasNext()) {
					SysApplication bean = (SysApplication) iter.next();
					if (bean.getLocked() == 1) {
						continue;
					}
					JSONObject item = new JSONObject();
					item.put("id", String.valueOf(bean.getId()));
					item.put("nodeId", bean.getNodeId());
					item.put("showMenu", bean.getShowMenu());
					item.put("sort", bean.getSort());
					item.put("description", bean.getDesc());
					item.put("name", bean.getName());
					item.put("icon", "icon-sys");
					// item.put("url", bean.getUrl());
					if (StringUtils.isNotEmpty(bean.getUrl())) {
						item.put("url", bean.getUrl());
						if (StringUtils.startsWith(bean.getUrl(), "/mx/form/page/viewPage?isPublish=true")) {
							String new_url = StringTools.replace(bean.getUrl(), "isPublish=true&", "");
							item.put("url", new_url);
						}
					} else {
						if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
							String url = SysConstants.WPF_DEF_URL
									+ RequestUtils.encodeString(String.valueOf(bean.getId()));
							item.put("url", url);
						} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
								&& StringUtils.isNotEmpty(bean.getPageId())) {
							String url = SysConstants.FORM_DEF_URL + bean.getPageId();
							bean.setUrl(url);
							// logger.debug("-------------------JAVA-------------");
						}
					}

					List<SysApplication> childrenNodes = null;
					if (user.isSystemAdmin()) {
						childrenNodes = getApplicationList(bean.getId());
					} else {
						childrenNodes = getAccessAppList(bean.getId(), user);
					}
					if (childrenNodes != null && childrenNodes.size() > 0) {// 有子菜单
						JSONArray children = this.getUserMenu(bean.getId(), user);
						item.put("children", children);
					}
					array.put(item);
				}
			}
		}
		return array;
	}

	public JSONArray getUserMenu2(long parent, String actorId) {
		JSONArray array = new JSONArray();
		SysUser user = sysUserService.findByAccountWithAll(actorId);
		if (user != null) {
			List<SysApplication> list = null;
			if (user.isSystemAdmin()) {
				logger.debug("#admin user=" + user.getName());
				list = getApplicationList(parent);
			} else {
				logger.debug("#user=" + user.getName());
				list = getAccessAppList(parent, user);
				// logger.debug("#app list=" + list);
			}
			if (list != null && list.size() > 0) {
				Iterator<SysApplication> iter = list.iterator();
				while (iter.hasNext()) {
					SysApplication bean = (SysApplication) iter.next();
					if (bean.getLocked() == 1) {
						continue;
					}
					JSONObject item = new JSONObject();
					item.put("id", String.valueOf(bean.getId()));
					item.put("nodeId", bean.getNodeId());
					item.put("showMenu", bean.getShowMenu());
					item.put("sort", bean.getSort());
					item.put("description", bean.getDesc());
					item.put("name", bean.getName());
					item.put("icon", "icon-sys");
					item.put("url", bean.getUrl());
					if (StringUtils.isNotEmpty(bean.getUrl())) {
						item.put("url", bean.getUrl());
						if (StringUtils.startsWith(bean.getUrl(), "/mx/form/page/viewPage?isPublish=true")) {
							String new_url = StringTools.replace(bean.getUrl(), "isPublish=true&", "");
							item.put("url", new_url);
						}
					} else {
						if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
							String url = SysConstants.WPF_DEF_URL
									+ RequestUtils.encodeString(String.valueOf(bean.getId()));
							item.put("url", url);
						} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
								&& StringUtils.isNotEmpty(bean.getPageId())) {
							String url = SysConstants.FORM_DEF_URL + bean.getPageId();
							bean.setUrl(url);
							// logger.debug("-------------------JAVA-------------");
						}
					}

					List<SysApplication> childrenNodes = null;
					if (user.isSystemAdmin()) {
						childrenNodes = getApplicationList(bean.getId());
					} else {
						childrenNodes = getAccessAppList(bean.getId(), user);
					}
					if (childrenNodes != null && childrenNodes.size() > 0) {// 有子菜单
						JSONArray children = this.getUserMenu(bean.getId(), user);
						item.put("children", children);
					}
					array.put(item);
				}
			}
		}
		return array;
	}

	protected void initTrees(List<SysApplication> list) {
		if (list != null && !list.isEmpty()) {
			SysTreeQuery query = new SysTreeQuery();
			List<SysTree> nodes = sysTreeService.getApplicationSysTrees(query);
			List<SysTree> trees = sysTreeService.getAvailableSysTrees(nodes);
			Map<Long, SysTree> treeMap = new HashMap<Long, SysTree>();
			if (trees != null && !trees.isEmpty()) {
				for (SysTree tree : trees) {
					treeMap.put(tree.getId(), tree);
				}
			}
			for (SysApplication bean : list) {
				bean.setNode(treeMap.get(bean.getNodeId()));
				if (bean.getNode() != null) {
					bean.setNodeParentId(bean.getNode().getParentId());
				} else {
					bean.setLocked(1);// 如果没有树节点，说明已经标记为无效
				}
			}
		}
	}

	public List<SysApplication> list(SysApplicationQuery query) {
		List<SysApplication> list = sysApplicationMapper.getSysApplications(query);

		for (SysApplication bean : list) {
			if (StringUtils.isEmpty(bean.getUrl())) {
				if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
					String url = SysConstants.WPF_DEF_URL + RequestUtils.encodeString(String.valueOf(bean.getId()));
					bean.setUrl(url);
					// logger.debug("-------------------WPF-------------");
				} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
						&& StringUtils.isNotEmpty(bean.getPageId())) {
					String url = SysConstants.FORM_DEF_URL + bean.getPageId();
					bean.setUrl(url);
					// logger.debug("-------------------JAVA-------------");
				}
			}
		}

		return list;
	}

	protected void loadChildrenTreeModels(List<TreeModel> treeModels, long parentId, SysUser user) {
		List<SysApplication> list = null;
		if (user.isSystemAdmin()) {
			logger.debug("#admin user=" + user.getName());
			list = getApplicationList(parentId);
		} else {
			logger.debug("#user=" + user.getName());
			list = getAccessAppList(parentId, user);
			// logger.debug("#app list=" + list);
		}
		if (list != null && list.size() > 0) {
			Iterator<SysApplication> iter = list.iterator();
			while (iter.hasNext()) {
				SysApplication bean = (SysApplication) iter.next();
				if (bean.getLocked() == 1) {
					continue;
				}
				TreeModel treeModel = new BaseTree();
				treeModel.setCode(bean.getCode());
				treeModel.setId(bean.getId());
				treeModel.setParentId(parentId);
				treeModel.setName(bean.getName());
				treeModel.setLocked(bean.getLocked());
				treeModel.setDescription(bean.getDesc());
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("showmenu", bean.getShowMenu());
				treeModel.setDataMap(dataMap);
				if (StringUtils.isNotEmpty(bean.getUrl())) {
					treeModel.setUrl(bean.getUrl());
					if (StringUtils.startsWith(bean.getUrl(), "/mx/form/page/viewPage?isPublish=true")) {
						String new_url = StringTools.replace(bean.getUrl(), "isPublish=true&", "");
						treeModel.setUrl(new_url);
					}
				} else {
					if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
						String url = SysConstants.WPF_DEF_URL + RequestUtils.encodeString(String.valueOf(bean.getId()));
						treeModel.setUrl(url);
						// logger.debug("-------------------WPF-------------");
					} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
							&& StringUtils.isNotEmpty(bean.getPageId())) {
						String url = SysConstants.FORM_DEF_URL + bean.getPageId();
						bean.setUrl(url);
						// logger.debug("-------------------JAVA-------------");
					}
				}
				treeModel.setSortNo(bean.getSort());
				treeModel.setIcon(bean.getImagePath());
				List<SysApplication> childrenNodes = null;
				if (user.isSystemAdmin()) {
					childrenNodes = getApplicationList(bean.getId());
				} else {
					childrenNodes = getAccessAppList(bean.getId(), user);
				}
				if (childrenNodes != null && childrenNodes.size() > 0) {// 有子菜单
					this.loadChildrenTreeModels(treeModels, bean.getId(), user);
				}
				if (!treeModels.contains(treeModel)) {
					treeModels.add(treeModel);
				}
			}
		}
	}

	protected void loadTreeModels(List<TreeModel> treeModels, long parentId, SysUser user) {
		List<SysApplication> list = null;
		if (user.isSystemAdmin()) {
			logger.debug("#admin user=" + user.getName());
			list = getApplicationList(parentId);
		} else {
			logger.debug("#user=" + user.getName());
			list = getAccessAppList(parentId, user);
			// logger.debug("#app list=" + list);
		}
		if (list != null && list.size() > 0) {
			Iterator<SysApplication> iter = list.iterator();
			while (iter.hasNext()) {
				SysApplication bean = (SysApplication) iter.next();
				if (bean.getLocked() == 1) {
					continue;
				}
				TreeModel treeModel = new BaseTree();
				treeModel.setCode(bean.getCode());
				treeModel.setId(bean.getId());
				treeModel.setParentId(parentId);
				treeModel.setName(bean.getName());
				treeModel.setLocked(bean.getLocked());
				treeModel.setDescription(bean.getDesc());
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("showmenu", bean.getShowMenu());
				treeModel.setDataMap(dataMap);
				if (StringUtils.isNotEmpty(bean.getUrl())) {
					treeModel.setUrl(bean.getUrl());
					if (StringUtils.startsWith(bean.getUrl(), "/mx/form/page/viewPage?isPublish=true")) {
						String new_url = StringTools.replace(bean.getUrl(), "isPublish=true&", "");
						treeModel.setUrl(new_url);
					}
				} else {
					if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "WPF")) {
						String url = SysConstants.WPF_DEF_URL + RequestUtils.encodeString(String.valueOf(bean.getId()));
						treeModel.setUrl(url);
					} else if (StringUtils.equalsIgnoreCase(bean.getSystemFlag(), "JAVA")
							&& StringUtils.isNotEmpty(bean.getPageId())) {
						String url = SysConstants.FORM_DEF_URL + bean.getPageId();
						bean.setUrl(url);
						// logger.debug("-------------------JAVA-------------");
					}
				}
				treeModel.setSortNo(bean.getSort());
				treeModel.setIcon(bean.getImagePath());
				treeModels.add(treeModel);
			}
		}
	}

	@Transactional
	public void markDeleteFlag(long id, int deleteFlag) {
		if (id > 0) {
			SysApplication application = this.getSysApplication(id);
			if (application != null) {
				SysTree tree = sysTreeService.findById(application.getNodeId());
				if (tree != null) {
					Date deleteTime = new Date();
					List<SysTree> treeList = sysTreeService.getSysTreeListWithChildren(tree.getId());
					if (treeList != null && !treeList.isEmpty()) {
						for (SysTree t : treeList) {
							t.setDeleteFlag(deleteFlag);
							t.setDeleteTime(deleteTime);
							sysTreeMapper.updateSysTree(t);
							SysApplication app = this.getSysApplicationByNodeId(t.getId());
							if (app != null) {
								app.setDeleteFlag(deleteFlag);
								app.setDeleteTime(deleteTime);
								sysApplicationMapper.updateSysApplication(app);
							}
						}
					}
					tree.setDeleteFlag(deleteFlag);
					tree.setDeleteTime(deleteTime);
					sysTreeMapper.updateSysTree(tree);

					application.setDeleteFlag(deleteFlag);
					application.setDeleteTime(deleteTime);
					sysApplicationMapper.updateSysApplication(application);

					CacheFactory.clear("application");
					CacheFactory.clear("tree");
				}
			}
		}
	}

	/**
	 * 设置删除标记
	 * 
	 * @param ids
	 * @param deleteFlag
	 */
	@Transactional
	public void markDeleteFlag(long[] ids, int deleteFlag) {
		if (ids != null && ids.length > 0) {
			for (long id : ids) {
				this.markDeleteFlag(id, deleteFlag);
			}
		}
	}

	@Transactional
	public void saveRoleApplications(long roleId, List<Long> appIds) {
		sysAccessMapper.deleteSysAccessByRoleId(roleId);
		for (Long appId : appIds) {
			SysAccess model = new SysAccess();
			model.setRoleId(roleId);
			model.setAppId(appId);
			sysAccessMapper.insertSysAccess(model);
		}
		CacheFactory.clear("application");
		CacheFactory.clear("tree");
	}

	@Transactional
	public void saveRoleApplicationsNoDelete(long roleId, List<Long> appIds) {
		for (Long appId : appIds) {
			SysAccess model = new SysAccess();
			model.setRoleId(roleId);
			model.setAppId(appId);
			sysAccessMapper.insertSysAccess(model);
		}
		CacheFactory.clear("application");
		CacheFactory.clear("tree");
	}

	@Transactional
	public void deleteRoleApplication(Long appId, long roleId) {
		sysAccessMapper.deleteSysAccessByAppIdAndRoleId(appId, roleId);
		CacheFactory.clear("application");
		CacheFactory.clear("tree");
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@Resource
	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
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
	public void setSysAccessMapper(SysAccessMapper sysAccessMapper) {
		this.sysAccessMapper = sysAccessMapper;
	}

	@Resource
	public void setSysApplicationMapper(SysApplicationMapper sysApplicationMapper) {
		this.sysApplicationMapper = sysApplicationMapper;
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@Resource
	public void setSysTreeMapper(SysTreeMapper sysTreeMapper) {
		this.sysTreeMapper = sysTreeMapper;
	}

	@Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@Transactional
	public void sort(long parent, SysApplication bean, int operate) {
		if (bean == null)
			return;
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
	private void sortByForward(long parentId, SysApplication bean) {
		SysApplicationQuery query = new SysApplicationQuery();
		query.setDeleteFlag(0);
		query.parentId(parentId);
		query.setSortLessThan(bean.getSort());
		query.setOrderBy(" E.SORT desc ");
		List<SysApplication> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysApplication temp = (SysApplication) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean
			SysTree node = sysTreeService.findById(bean.getNodeId());
			node.setSort(bean.getSort());
			sysTreeService.update(node);

			temp.setSort(i);
			this.update(temp);// 更新temp
			node = sysTreeService.findById(temp.getNodeId());
			node.setSort(temp.getSort());
			sysTreeService.update(node);
		}
	}

	/**
	 * 向前移动排序
	 * 
	 * @param bean
	 */
	private void sortByPrevious(long parentId, SysApplication bean) {
		// 查找前一个对象
		SysApplicationQuery query = new SysApplicationQuery();
		query.parentId(parentId);
		query.setSortGreaterThan(bean.getSort());
		query.setOrderBy(" E.SORT asc ");
		query.setDeleteFlag(0);

		List<SysApplication> list = this.list(query);
		if (list != null && list.size() > 0) {// 有记录
			SysApplication temp = (SysApplication) list.get(0);
			int i = bean.getSort();
			bean.setSort(temp.getSort());
			this.update(bean);// 更新bean
			SysTree node = sysTreeService.findById(bean.getNodeId());
			node.setSort(bean.getSort());
			sysTreeService.update(node);

			temp.setSort(i);
			this.update(temp);// 更新temp
			node = sysTreeService.findById(temp.getNodeId());
			node.setSort(temp.getSort());
			sysTreeService.update(node);
		}
	}

	@Transactional
	public boolean update(SysApplication bean) {
		String cacheKey = "sys_app_" + bean.getId();
		CacheFactory.remove("application", cacheKey);
		String cacheKey2 = "sys_app2_" + bean.getNodeId();
		CacheFactory.remove("application", cacheKey2);
		String cacheKey3 = "sys_apps_" + bean.getId();
		CacheFactory.remove("application", cacheKey3);
		CacheFactory.clear("application");
		CacheFactory.clear("tree");

		bean.setUpdateDate(new Date());
		if (StringUtils.isEmpty(bean.getCode())) {
			bean.setCode("app_" + bean.getId());
		}
		if (bean.getLinkFileContent() != null) {
			bean.setLinkFileId("sys_app_" + UUID32.getUUID());
		}
		if (bean.getPrintFileContent() != null) {
			bean.setPrintFileId("sys_app_print_" + UUID32.getUUID());
		}
		if (StringUtils.endsWithIgnoreCase(bean.getLinkFileName(), ".cpt")) {
			bean.setUrl("/mx/menu/jump?menuId=" + RequestUtils.encodeString(bean.getId() + ""));
		}
		this.sysApplicationMapper.updateSysApplication(bean);

		if (bean.getNode() != null) {
			bean.getNode().setCode(bean.getCode());
			bean.getNode().setLocked(bean.getLocked());
			bean.getNode().setUrl(bean.getUrl());
			bean.getNode().setSortNo(bean.getSort());
			bean.getNode().setUpdateBy(bean.getUpdateBy());
			bean.getNode().setUpdateDate(bean.getUpdateDate());
			sysTreeMapper.updateSysTree(bean.getNode());
			cacheKey = "sys_tree_" + bean.getNode().getId();
			CacheFactory.remove("tree", cacheKey);
			cacheKey = "sys_tree_" + bean.getNode().getParentId();
			CacheFactory.remove("tree", cacheKey);
		}

		if (bean.getLinkFileContent() != null) {
			BlobItem dataFile = new BlobItemEntity();
			dataFile.setLastModified(System.currentTimeMillis());
			dataFile.setCreateBy(bean.getUpdateBy());
			dataFile.setFileId(bean.getLinkFileId());
			dataFile.setData(bean.getLinkFileContent());
			dataFile.setFilename(bean.getLinkFileName());
			dataFile.setName(bean.getLinkFileName());
			dataFile.setSize(bean.getLinkFileContent().length);
			dataFile.setType(bean.getLinkType());
			dataFile.setStatus(9);
			dataFile.setObjectId("sys_app");
			dataFile.setObjectValue("sys_app_" + bean.getId());
			dataFile.setServiceKey("sys_app_file");
			blobService.insertBlob(dataFile);
		}

		if (bean.getPrintFileContent() != null) {
			BlobItem dataFile = new BlobItemEntity();
			dataFile.setLastModified(System.currentTimeMillis());
			dataFile.setCreateBy(bean.getUpdateBy());
			dataFile.setFileId(bean.getPrintFileId());
			dataFile.setData(bean.getPrintFileContent());
			dataFile.setFilename(bean.getPrintFileName());
			dataFile.setName(bean.getPrintFileName());
			dataFile.setSize(bean.getPrintFileContent().length);
			dataFile.setType(bean.getPrintType());
			dataFile.setStatus(9);
			dataFile.setObjectId("sys_app_print");
			dataFile.setObjectValue("sys_app_print_" + bean.getId());
			dataFile.setServiceKey("sys_app_print_file");
			blobService.insertBlob(dataFile);
		}
		return true;
	}

}
