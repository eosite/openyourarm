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
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.TableModel;
import com.glaf.core.domain.Membership;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.query.MembershipQuery;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.MembershipService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.LockedHolder;
import com.glaf.base.modules.sys.mapper.SysAccessMapper;
import com.glaf.base.modules.sys.mapper.SysApplicationMapper;
import com.glaf.base.modules.sys.mapper.SysFunctionMapper;
import com.glaf.base.modules.sys.mapper.SysPermissionMapper;
import com.glaf.base.modules.sys.mapper.SysRoleMapper;
import com.glaf.base.modules.sys.mapper.SysUserMapper;
import com.glaf.base.modules.sys.mapper.SysUserRoleMapper;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.model.SysUserRole;
import com.glaf.base.modules.sys.model.UserRole;
import com.glaf.base.modules.sys.query.SysUserQuery;
import com.glaf.base.modules.sys.query.UserRoleQuery;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.modules.sys.util.SysUserJsonFactory;

@Service("sysUserService")
@Transactional(readOnly = true)
public class SysUserServiceImpl implements SysUserService {
	protected final static Log logger = LogFactory.getLog(SysUserServiceImpl.class);

	protected IdGenerator idGenerator;

	protected MembershipService membershipService;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysAccessMapper sysAccessMapper;

	protected SysApplicationMapper sysApplicationMapper;

	protected SysDepartmentService sysDepartmentService;

	protected SysFunctionMapper sysFunctionMapper;

	protected SysPermissionMapper sysPermissionMapper;

	protected SysRoleMapper sysRoleMapper;

	protected SysUserMapper sysUserMapper;

	protected SysUserRoleMapper sysUserRoleMapper;

	protected ITableDataService tableDataService;

	private static Configuration conf = BaseConfiguration.create();

	public SysUserServiceImpl() {

	}

	/**
	 * 修改用户密码
	 * 
	 * @param account
	 * @param password
	 */
	public void changePassword(String account, String password, int pwdUpdateFlag) {
		if (!StringUtils.equals(password, "88888888") && password.length() < 64) {
			String pwd_hash = DigestUtils.sha512Hex(account + ":" + password);
			SysUser bean = new SysUser();
			bean.setAccount(account);
			bean.setPasswordHash(pwd_hash);
			bean.setPwdUpdateFlag(pwdUpdateFlag);
			sysUserMapper.updateUserPassword(bean);
		}
	}

	/**
	 * 检查用户登录密锁是否正确
	 * 
	 * @param account
	 * @param loginSecret
	 * @return
	 */
	public boolean checkLoginSecret(String account, String loginSecret) {
		boolean result = false;
		SysUser bean = sysUserMapper.getSysUserLoginSecret(account);
		if (StringUtils.isNotEmpty(loginSecret) && StringUtils.equals(loginSecret, bean.getLoginSecret())) {
			result = true;
		}
		return result;
	}

	/**
	 * 检查用户密码是否正确
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public boolean checkPassword(String account, String password) {
		boolean result = false;
		String pwd_hash = DigestUtils.sha512Hex(account + ":" + password);
		String pwd = sysUserMapper.getPasswordHashByAccount(account);
		if (StringUtils.isNotEmpty(password) && StringUtils.equals(pwd_hash, pwd)) {
			result = true;
		}
		return result;
	}

	public int count(SysUserQuery query) {
		return sysUserMapper.getSysUserCount(query);
	}

	@Transactional
	public boolean create(SysUser bean) {
		String pwd_hash = DigestUtils.sha512Hex(bean.getAccount() + ":" + bean.getPasswordHash());
		bean.setPasswordHash(pwd_hash);
		this.save(bean);
		return true;
	}

	@Transactional
	public void createRoleUser(long roleId, String actorId) {
		SysUser user = this.findByAccount(actorId);

		TableModel table = new TableModel();
		table.setTableName("userrole");
		table.addStringColumn("ID", idGenerator.getNextId());
		table.addIntegerColumn("AUTHORIZED", 0);
		table.addLongColumn("ROLEID", roleId);
		table.addStringColumn("USERID", actorId);
		tableDataService.insertTableData(table);

		TableModel table2 = new TableModel();
		table2.setTableName("SYS_MEMBERSHIP");
		table2.addLongColumn("ID_", idGenerator.nextId());
		table2.addStringColumn("ACTORID_", actorId);
		table2.addLongColumn("ROLEID_", roleId);
		table2.addLongColumn("NODEID_", user.getDeptId());
		table2.addStringColumn("TYPE_", "UserRole");
		tableDataService.insertTableData(table2);

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
			CacheFactory.clear("user");
		}
	}

	@Transactional
	public boolean delete(SysUser bean) {
		TableModel table = new TableModel();
		table.setTableName("UserInfo");
		table.addStringColumn("USERID", bean.getAccount());
		tableDataService.deleteTableData(table);

		TableModel table1 = new TableModel();
		table1.setTableName("userrole");
		table1.addIntegerColumn("AUTHORIZED", 0);
		table1.addStringColumn("USERID", bean.getAccount());
		tableDataService.deleteTableData(table1);

		TableModel table2 = new TableModel();
		table2.setTableName("SYS_MEMBERSHIP");
		table2.addStringColumn("ACTORID_", bean.getAccount());
		tableDataService.deleteTableData(table2);

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
			CacheFactory.clear("user");
		}
		return true;
	}

	@Transactional
	public void deleteRoleUser(long roleId, String actorId) {
		SysUser user = this.findByAccount(actorId);

		TableModel table = new TableModel();
		table.setTableName("userrole");
		table.addIntegerColumn("AUTHORIZED", 0);
		table.addLongColumn("ROLEID", roleId);
		table.addStringColumn("USERID", actorId);
		tableDataService.deleteTableData(table);

		TableModel table2 = new TableModel();
		table2.setTableName("SYS_MEMBERSHIP");
		table2.addStringColumn("ACTORID_", actorId);
		table2.addLongColumn("ROLEID_", roleId);
		table2.addLongColumn("NODEID_", user.getDeptId());
		tableDataService.deleteTableData(table2);

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
			CacheFactory.clear("user");
		}
	}

	/**
	 * 删除角色用户
	 * 
	 * @param role
	 * @param userIds
	 */
	@Transactional
	public void deleteRoleUsers(SysRole role, String[] userIds) {
		for (int i = 0; i < userIds.length; i++) {
			SysUser user = this.findById(userIds[i]);
			if (user != null) {
				logger.debug(user.getName());
				TableModel table = new TableModel();
				table.setTableName("userrole");
				table.addIntegerColumn("AUTHORIZED", 0);
				table.addStringColumn("ROLEID", String.valueOf(role.getId()));
				table.addStringColumn("USERID", user.getActorId());
				tableDataService.deleteTableData(table);

				TableModel table2 = new TableModel();
				table2.setTableName("SYS_MEMBERSHIP");
				table2.addStringColumn("ACTORID_", user.getAccount());
				table2.addLongColumn("ROLEID_", role.getId());
				tableDataService.deleteTableData(table2);
			}
		}

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
			CacheFactory.clear("user");
		}
	}

	public SysUser findByAccount(String account) {
		SysUser user = sysUserMapper.getSysUserByAccount(account);
		if (user != null) {
			if (user.getDeptId() > 0) {
				user.setDepartment(sysDepartmentService.findById(user.getDeptId()));
			}
			return user;
		}

		return null;
	}

	/**
	 * 获取用户权限相关的全部信息
	 */
	public SysUser findByAccountWithAll(String account) {
		String cacheKey = "sys_user_all_" + account;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("user", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject json = JSON.parseObject(text);
					return SysUserJsonFactory.jsonToObject(json);
				} catch (Exception ex) {
					// Ignore error
				}
			}
		}

		SysUser user = sysUserMapper.getSysUserByAccount(account);
		if (user != null) {
			if (user.getDeptId() > 0) {
				user.setDepartment(sysDepartmentService.findById(user.getDeptId()));
			}
			List<String> actorIds = new ArrayList<String>();
			actorIds.add(account);
			List<SysRole> roles = this.getUserRoles(actorIds);
			user.getRoles().addAll(roles);
			List<SysUserRole> userRoles = sysUserRoleMapper.getSysUserRolesByUserId(user.getActorId());
			user.getUserRoles().addAll(userRoles);
			List<SysApplication> apps = null;
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				apps = sysApplicationMapper.getSysApplicationsByUserId_oracle(user.getActorId());
			} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
				apps = sysApplicationMapper.getSysApplicationsByUserId_postgresql(user.getActorId());
			} else {
				apps = sysApplicationMapper.getSysApplicationsByUserId(user.getActorId());
			}
			if (apps != null && !apps.isEmpty()) {
				user.getApps().addAll(apps);
			}

			// List<SysFunction> funs = null;
			// if (StringUtils.equals(DBUtils.ORACLE,
			// DBConnectionFactory.getDatabaseType())) {
			// funs = sysFunctionMapper.getSysFunctionsByUserId_oracle(user.getActorId());
			// } else if (StringUtils.equals(DBUtils.POSTGRESQL,
			// DBConnectionFactory.getDatabaseType())) {
			// funs =
			// sysFunctionMapper.getSysFunctionsByUserId_postgresql(user.getActorId());
			// } else {
			// funs = sysFunctionMapper.getSysFunctionsByUserId(user.getActorId());
			// }
			// user.getFunctions().addAll(funs);

			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONObject jsonObject = SysUserJsonFactory.toJsonObject(user);
				if (user.getApps() != null && !user.getApps().isEmpty()) {
					JSONArray array = new JSONArray();
					for (SysApplication app : user.getApps()) {
						array.add(app.toJsonObject());
					}
					jsonObject.put("apps", array);
				}
				CacheFactory.put("user", cacheKey, jsonObject.toJSONString());
			}

			return user;
		}

		return null;
	}

	@Override
	public SysUser findById(String account) {
		String cacheKey = "sys_user_" + account;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("user", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject json = JSON.parseObject(text);
					return SysUserJsonFactory.jsonToObject(json);
				} catch (Exception ex) {
					// Ignore error
				}
			}
		}

		SysUser user = sysUserMapper.getSysUserByAccount(account);
		if (user != null) {
			if (user.getDeptId() > 0) {
				user.setDepartment(sysDepartmentService.findById(user.getDeptId()));
			}
			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONObject json = user.toJsonObject();
				CacheFactory.put("user", cacheKey, json.toJSONString());
			}
		}
		return user;
	}

	public SysUser findByMail(String mail) {
		SysUser user = sysUserMapper.getSysUserByMail(mail);
		return user;
	}

	public SysUser findByMobile(String mobile) {
		SysUser user = sysUserMapper.getSysUserByMobile(mobile);
		return user;
	}

	protected Map<Long, SysDepartment> getDepartmentMap() {
		Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
		List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
		if (depts != null && !depts.isEmpty()) {
			for (SysDepartment dept : depts) {
				deptMap.put(dept.getId(), dept);
			}
		}
		return deptMap;
	}

	public List<UserRole> getRoleUserViews(UserRoleQuery query) {
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return sysUserMapper.getRoleUserViews_oracle(query);
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			return sysUserMapper.getRoleUserViews_postgresql(query);
		}
		return sysUserMapper.getRoleUserViews(query);
	}

	public List<SysUser> getSuperiors(String account) {
		List<SysUser> superiors = new ArrayList<SysUser>();
		SysUser bean = this.findByAccount(account);
		if (bean != null && bean.getSuperiorIds() != null) {
			List<String> superiorIds = StringTools.split(bean.getSuperiorIds());
			if (superiorIds != null && !superiorIds.isEmpty()) {
				for (String superiorId : superiorIds) {
					SysUser user = this.findByAccount(superiorId);
					if (user != null && user.getLocked() == 0) {
						superiors.add(user);
					}
				}
			}
		}
		return superiors;
	}

	public List<SysUser> getSupplierUser(String supplierNo) {
		SysUserQuery query = new SysUserQuery();
		query.account(supplierNo);
		query.setDeleteFlag(0);
		List<SysUser> users = this.list(query);
		this.initUserDepartments(users);
		return users;
	}

	public SysUser getSysUserByAppId(String appId) {
		SysUser user = sysUserMapper.getSysUserByAppId(appId);
		return user;
	}

	public int getSysUserCountByQueryCriteria(SysUserQuery query) {
		return sysUserMapper.getSysUserCount(query);
	}

	public int getSysUserExCountByQueryCriteria(SysUserQuery query) {
		return sysUserMapper.getSysUserExCount(query);
	}

	public List<SysUser> getSysUserList() {
		SysUserQuery query = new SysUserQuery();
		query.setDeleteFlag(0);
		return this.list(query);
	}

	public PageResult getSysUserList(int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysUserQuery query = new SysUserQuery();
		query.setDeleteFlag(0);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.STATUS asc, E.UserName asc ");

		int start = pageSize * (pageNo - 1);
		List<SysUser> list = this.getSysUsersByQueryCriteria(start, pageSize, query);
		this.initUserDepartments(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	/**
	 * 获取全部员工数据集 分页列表
	 * 
	 * @param pageNo
	 *            int
	 * @param pageSize
	 *            int
	 * @param query
	 * @return
	 */
	public PageResult getSysUserList(int pageNo, int pageSize, SysUserQuery query) {
		PageResult pager = new PageResult();

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.STATUS asc, E.UserName asc ");

		int start = pageSize * (pageNo - 1);
		List<SysUser> list = this.getSysUsersByQueryCriteria(start, pageSize, query);
		this.initUserDepartments(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SysUser> getSysUserList(long deptId) {
		SysUserQuery query = new SysUserQuery();
		query.deptId(deptId);
		query.setDeleteFlag(0);
		return this.list(query);
	}

	public PageResult getSysUserList(long deptId, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysUserQuery query = new SysUserQuery();
		query.deptId(deptId);
		query.setDeleteFlag(0);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.STATUS asc, E.UserName asc ");

		int start = pageSize * (pageNo - 1);
		List<SysUser> list = this.getSysUsersByQueryCriteria(start, pageSize, query);
		this.initUserDepartments(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public PageResult getSysUserList(long deptId, String fullName, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysUserQuery query = new SysUserQuery();
		query.deptId(deptId);
		query.setDeleteFlag(0);

		if (fullName != null && fullName.trim().length() > 0) {
			query.nameLike(fullName);
		}
		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.STATUS asc, E.UserName asc ");

		int start = pageSize * (pageNo - 1);
		List<SysUser> list = this.getSysUsersByQueryCriteria(start, pageSize, query);
		this.initUserDepartments(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public PageResult getSysUserList(long deptId, String userName, String account, int pageNo, int pageSize) {
		// 计算总数
		PageResult pager = new PageResult();
		SysUserQuery query = new SysUserQuery();
		query.deptId(deptId);
		query.setDeleteFlag(0);

		int count = this.count(query);
		if (count == 0) {// 结果集为空
			pager.setPageSize(pageSize);
			return pager;
		}
		query.setOrderBy(" E.DEPID asc, E.STATUS asc, E.UserName asc  ");

		int start = pageSize * (pageNo - 1);
		List<SysUser> list = this.getSysUsersByQueryCriteria(start, pageSize, query);
		this.initUserDepartments(list);
		pager.setResults(list);
		pager.setPageSize(pageSize);
		pager.setCurrentPageNo(pageNo);
		pager.setTotalRecordCount(count);

		return pager;
	}

	public List<SysUser> getSysUserLoginSecretList(SysUserQuery query) {
		return sysUserMapper.getSysUserLoginSecretList(query);
	}

	/**
	 * 获取某个应用的权限用户
	 * 
	 * @param appId
	 * @return
	 */
	public List<SysUser> getSysUsersByAppId(Long appId) {
		return sysUserMapper.getSysUsersByAppId(appId);
	}

	public List<SysUser> getSysUsersByQueryCriteria(int start, int pageSize, SysUserQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysUser> rows = sqlSessionTemplate.selectList("getSysUsers", query, rowBounds);
		this.initUserDepartments(rows);
		return rows;
	}

	public List<SysUser> getAllUsers(SysUserQuery query) {
		RowBounds rowBounds = new RowBounds(0, 50000);
		List<SysUser> rows = sqlSessionTemplate.selectList("getSysUsers", query, rowBounds);
		return rows;
	}

	/**
	 * 获取某个角色代码的用户
	 * 
	 * @param roleCode
	 * @return
	 */
	public List<SysUser> getSysUsersByRoleCode(String roleCode) {
		if (roleCode == null) {
			return null;
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return sysUserMapper.getSysUsersByRoleCode_oracle(roleCode);
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			return sysUserMapper.getSysUsersByRoleCode_postgresql(roleCode);
		}
		return sysUserMapper.getSysUsersByRoleCode(roleCode);
	}

	/**
	 * 获取某个角色代码的用户
	 * 
	 * @param roleCode
	 * @return
	 */
	public List<SysUser> getSysUsersByRoleId(long roleId) {
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return sysUserMapper.getSysUsersByRoleId_oracle(roleId);
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			return sysUserMapper.getSysUsersByRoleId_postgresql(roleId);
		}
		return sysUserMapper.getSysUsersByRoleId(roleId);
	}

	public List<SysUser> getSysUsersByRoleIds(List<Long> roleIds) {
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return sysUserMapper.getSysUsersByRoleIds_oracle(roleIds);
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			return sysUserMapper.getSysUsersByRoleIds_postgresql(roleIds);
		}
		return sysUserMapper.getSysUsersByRoleIds(roleIds);
	}

	public List<SysUser> getSysUsersExByQueryCriteria(int start, int pageSize, SysUserQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysUser> rows = sqlSessionTemplate.selectList("getSysUsersEx", query, rowBounds);
		initUserDepartments(rows);
		return rows;
	}

	public SysUser getSysUserToken(String account) {
		return sysUserMapper.getSysUserToken(account);
	}

	public List<SysUser> getSysUserWithDeptList() {
		SysUserQuery query = new SysUserQuery();
		query.setDeleteFlag(0);
		List<SysUser> users = this.list(query);
		if (users != null && !users.isEmpty()) {
			this.initUserDepartments(users);
		}
		return users;
	}

	public SysUser getUserAndPrivileges(SysUser user) {
		List<String> actorIds = new ArrayList<String>();
		actorIds.add(user.getActorId());
		user.setRoles(getUserRoles(actorIds));
		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			Iterator<SysRole> roles = user.getRoles().iterator();
			while (roles.hasNext()) {
				SysRole role = roles.next();
				List<SysApplication> apps = sysApplicationMapper.getSysApplicationsByRoleId(role.getId());
				List<SysFunction> functions = sysFunctionMapper.getSysFunctionsByRoleId(role.getId());
				user.getFunctions().addAll(functions);
				user.getApps().addAll(apps);
			}
		}

		return user;
	}

	/**
	 * 获取某个用户的首页链接
	 * 
	 * @param actorId
	 * @return
	 */
	public String getUserIndexUrl(String actorId) {
		List<SysRole> list = null;
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			list = sysRoleMapper.getSysRolesOfUser_oracle(actorId);
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			list = sysRoleMapper.getSysRolesOfUser_postgresql(actorId);
		} else {
			list = sysRoleMapper.getSysRolesOfUser(actorId);
		}
		if (list != null && !list.isEmpty()) {
			Collections.sort(list);
			for (SysRole role : list) {
				if (StringUtils.isNotEmpty(role.getIndexUrl())) {
					return role.getIndexUrl();
				}
			}
		}
		return null;
	}

	public SysUser getUserPrivileges(SysUser user) {
		List<String> actorIds = new ArrayList<String>();
		actorIds.add(user.getActorId());
		user.setRoles(getUserRoles(actorIds));
		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			Iterator<SysRole> roles = user.getRoles().iterator();
			while (roles.hasNext()) {
				SysRole role = (SysRole) roles.next();
				List<SysApplication> apps = sysApplicationMapper.getSysApplicationsByRoleId(role.getId());
				List<SysFunction> functions = sysFunctionMapper.getSysFunctionsByRoleId(role.getId());
				user.getFunctions().addAll(functions);
				user.getApps().addAll(apps);
			}
		}

		return user;
	}

	/**
	 * 获取某些用户的角色
	 * 
	 * @param actorIds
	 * @return
	 */
	public List<SysRole> getUserRoles(List<String> actorIds) {
		List<SysRole> roles = new ArrayList<SysRole>();
		if (actorIds != null && !actorIds.isEmpty()) {
			for (String actorId : actorIds) {
				List<SysRole> list = null;
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					list = sysRoleMapper.getSysRolesOfUser_oracle(actorId);
				} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
					list = sysRoleMapper.getSysRolesOfUser_postgresql(actorId);
				} else {
					list = sysRoleMapper.getSysRolesOfUser(actorId);
				}
				if (list != null && !list.isEmpty()) {
					for (SysRole role : list) {
						if (!roles.contains(role)) {
							roles.add(role);
						}
					}
				}
			}
		}
		return roles;
	}

	public List<SysRole> getUserRoles(String actorId) {
		List<SysRole> roles = new ArrayList<SysRole>();
		List<SysRole> list = null;
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			list = sysRoleMapper.getSysRolesOfUser_oracle(actorId);
		} else if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			list = sysRoleMapper.getSysRolesOfUser_postgresql(actorId);
		} else {
			list = sysRoleMapper.getSysRolesOfUser(actorId);
		}
		if (list != null && !list.isEmpty()) {
			for (SysRole role : list) {
				if (!roles.contains(role)) {
					roles.add(role);
				}
			}
		}
		return roles;
	}

	protected void initUserDepartments(List<SysUser> users) {
		if (users != null && !users.isEmpty()) {
			List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
			Map<Long, SysDepartment> deptMap = new HashMap<Long, SysDepartment>();
			if (depts != null && !depts.isEmpty()) {
				for (SysDepartment dept : depts) {
					deptMap.put(dept.getId(), dept);
				}
			}
			for (SysUser user : users) {
				user.setDepartment(deptMap.get(user.getDeptId()));
			}
		}
	}

	public boolean isPermission(SysUser user, String roleCode) {
		boolean flag = false;

		List<String> actorIds = new ArrayList<String>();
		actorIds.add(user.getActorId());
		List<SysRole> roles = this.getUserRoles(actorIds);
		if (roles != null && !roles.isEmpty()) {
			for (SysRole role : roles) {
				if (role != null && StringUtils.equals(role.getCode(), roleCode)) {
					// 代判断用户是否拥有此角色
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	public List<SysUser> list(SysUserQuery query) {
		List<SysUser> list = sysUserMapper.getSysUsers(query);
		this.initUserDepartments(list);
		return list;
	}

	/**
	 * 登录失败
	 * 
	 * @param actorId
	 *            登录用户编号
	 * @return
	 */
	@Transactional
	public void loginFailure(String actorId) {
		SysUser user = sysUserMapper.getSysUserByAccount(actorId);
		if (user != null) {
			/**
			 * 系统管理员账号不允许被锁定
			 */
			if (!(StringUtils.equals(actorId, "root") || StringUtils.equals(actorId, "admin"))) {
				BaseDataManager bm = BaseDataManager.getInstance();
				// 获取连续登录失败次数
				BaseDataInfo baseDataInfo = bm.getBaseData("failures", "userLockRule");
				int retryCount = conf.getInt("login.retryCount", 5);
				int curr_retryCount = 0;
				if (baseDataInfo != null) {
					retryCount = Integer.parseInt(baseDataInfo.getValue());
				}
				// 获取重复登录时间段设置
				BaseDataInfo rangeBaseDataInfo = bm.getBaseData("range", "userLockRule");
				int range = conf.getInt("login.retryMinutes", 30);
				if (baseDataInfo != null) {
					range = Integer.parseInt(rangeBaseDataInfo.getValue());
				}
				if ((System.currentTimeMillis() - user.getLastLoginTime().getTime()) < 60 * 1000 * range) {
					curr_retryCount = user.getLoginRetry() + 1;
				} else {
					curr_retryCount = 1;
				}
				if (curr_retryCount == retryCount) {
					LockedHolder.setLocked("1");
					user.setLockLoginTime(new Date());
				}
				user.setLoginRetry(curr_retryCount);
				// 设置第一次登录失败时间
				if (user.getLoginRetry() == 1) {
					user.setLastLoginTime(new Date());
				}
				sysUserMapper.updateUserLoginRetry(user);
			}
		}
	}

	/**
	 * 重置登录信息
	 * 
	 * @param actorId
	 *            登录用户编号
	 * @return
	 */
	@Transactional
	public void resetLoginStatus(String actorId) {
		SysUser user = sysUserMapper.getSysUserByAccount(actorId);
		if (user != null) {
			user.setLoginRetry(0);
			user.setLockLoginTime(null);
			user.setLoginSecret(UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID());
			user.setLoginSecretUpdateTime(new Date());
			BaseDataManager baseDataManager = BaseDataManager.getInstance();
			BaseDataInfo baseDataInfo = baseDataManager.getBaseData("update", "syncOperatorType");
			if (baseDataInfo != null) {
				user.setSyncOperatorType(baseDataInfo.getValue());
			}
			user.setSyncFlag(0);
			user.setSyncTime(null);
			sysUserMapper.resetLoginStatus(user);
		}
	}

	/**
	 * 重置用户状态
	 * 
	 * @param userIds
	 *            用户编号集合
	 * @param status
	 *            状态
	 */
	@Transactional
	public void resetStatus(List<String> userIds, int status) {
		if (userIds != null && !userIds.isEmpty()) {
			for (String userId : userIds) {
				SysUser user = sysUserMapper.getSysUserByAccount(userId);
				if (user != null) {
					user.setStatus(String.valueOf(status));
					sysUserMapper.updateSysUser(user);
				}
			}
		}
	}

	/**
	 * 重置用户AppId及密锁
	 * 
	 * @param actorId
	 * @return
	 */
	@Transactional
	public SysUser resetUserAppSecret(String actorId) {
		SysUser sysUser = this.findByAccount(actorId);
		if (sysUser != null) {
			sysUser.setAppId(UUID32.getUUID());
			sysUser.setAppSecret(UUID32.getUUID());
			sysUser.setToken(UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID());
			sysUser.setTokenTime(new Date(System.currentTimeMillis()));
			sysUserMapper.resetUserAppSecret(sysUser);
			String cacheKey = "sys_user_" + sysUser.getActorId();
			CacheFactory.remove("user", cacheKey);

			cacheKey = "sys_user_all_" + sysUser.getActorId();
			CacheFactory.remove("user", cacheKey);

			cacheKey = "cache_sys_user_" + sysUser.getActorId();
			CacheFactory.remove("user", cacheKey);
		}
		return sysUser;
	}

	@Transactional
	public SysUser resetUserToken(String actorId) {
		SysUser sysUser = this.findByAccount(actorId);
		if (sysUser != null) {
			sysUser.setToken(UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID());
			sysUser.setTokenTime(new Date(System.currentTimeMillis()));
			sysUserMapper.resetUserToken(sysUser);
			String cacheKey = "sys_user_" + sysUser.getActorId();
			CacheFactory.remove("user", cacheKey);

			cacheKey = "cache_sys_user_" + sysUser.getActorId();
			CacheFactory.remove("user", cacheKey);
		}
		return sysUser;
	}

	@Transactional
	public void save(SysUser sysUser) {
		BaseDataManager baseDataManager = BaseDataManager.getInstance();

		if (this.findByAccount(sysUser.getAccount()) == null) {
			sysUser.setCreateTime(new Date());
			sysUser.setAppId(UUID32.getUUID());
			sysUser.setAppSecret(UUID32.getUUID());
			sysUser.setToken(UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID());
			sysUser.setLoginSecret(UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID() + UUID32.getUUID());
			sysUser.setTokenTime(new Date(System.currentTimeMillis()));
			sysUser.setSyncFlag(0);

			BaseDataInfo baseDataInfo = baseDataManager.getBaseData("create", "syncOperatorType");
			if (baseDataInfo != null) {
				sysUser.setSyncOperatorType(baseDataInfo.getValue());
			}

			sysUserMapper.insertSysUser(sysUser);
		} else {
			sysUser.setUpdateDate(new Date(System.currentTimeMillis()));

			BaseDataInfo baseDataInfo = baseDataManager.getBaseData("update", "syncOperatorType");
			if (baseDataInfo != null) {
				sysUser.setSyncOperatorType(baseDataInfo.getValue());
			}
			sysUser.setSyncFlag(0);
			sysUser.setSyncTime(null);

			sysUserMapper.updateSysUser(sysUser);
			String cacheKey = "sys_user_" + sysUser.getActorId();
			CacheFactory.remove("user", cacheKey);

			cacheKey = "sys_user_all_" + sysUser.getActorId();
			CacheFactory.remove("user", cacheKey);

			cacheKey = "cache_sys_user_" + sysUser.getActorId();
			CacheFactory.remove("user", cacheKey);
		}

		TableModel table = new TableModel();
		table.setTableName("SYS_MEMBERSHIP");
		table.addStringColumn("ACTORID_", sysUser.getAccount());
		table.addStringColumn("TYPE_", "Superior");
		tableDataService.deleteTableData(table);

		if (StringUtils.isNotEmpty(sysUser.getSuperiorIds())) {
			List<String> superiorIds = StringTools.split(sysUser.getSuperiorIds());
			if (superiorIds != null && !superiorIds.isEmpty()) {
				for (String superiorId : superiorIds) {
					if (!StringUtils.equals(superiorId, sysUser.getAccount())) {
						Membership membership = new Membership();
						membership.setActorId(sysUser.getAccount());
						membership.setSuperiorId(superiorId);
						membership.setModifyBy(sysUser.getUpdateBy());
						membership.setModifyDate(new java.util.Date());
						membership.setType("Superior");
						membership.setObjectId("Superior");
						membership.setObjectValue(superiorId);
						membershipService.save(membership);
					}
				}
			}
		}

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
		}
	}

	@Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@Resource
	public void setMembershipService(MembershipService membershipService) {
		this.membershipService = membershipService;
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

	@Resource
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@Resource
	public void setSysFunctionMapper(SysFunctionMapper sysFunctionMapper) {
		this.sysFunctionMapper = sysFunctionMapper;
	}

	@Resource
	public void setSysPermissionMapper(SysPermissionMapper sysPermissionMapper) {
		this.sysPermissionMapper = sysPermissionMapper;
	}

	@Resource
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	@Resource
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	@Resource
	public void setSysUserRoleMapper(SysUserRoleMapper sysUserRoleMapper) {
		this.sysUserRoleMapper = sysUserRoleMapper;
	}

	@Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@Transactional
	public boolean update(SysUser sysUser) {
		sysUser.setLoginRetry(0);
		sysUser.setLockLoginTime(null);
		sysUser.setUpdateDate(new Date());

		BaseDataManager baseDataManager = BaseDataManager.getInstance();
		BaseDataInfo baseDataInfo = null;
		if (sysUser.getStatus() != null && sysUser.getStatus().equals("0")) {
			baseDataInfo = baseDataManager.getBaseData("update", "syncOperatorType");
		} else {
			baseDataInfo = baseDataManager.getBaseData("freeze", "syncOperatorType");
		}
		if (baseDataInfo != null) {
			sysUser.setSyncOperatorType(baseDataInfo.getValue());
		}
		sysUser.setSyncFlag(0);
		sysUser.setSyncTime(null);

		sysUserMapper.updateSysUser(sysUser);
		TableModel table = new TableModel();
		table.setTableName("SYS_MEMBERSHIP");
		table.addStringColumn("ACTORID_", sysUser.getAccount());
		table.addStringColumn("TYPE_", "Superior");
		tableDataService.deleteTableData(table);

		if (StringUtils.isNotEmpty(sysUser.getSuperiorIds())) {
			List<String> superiorIds = StringTools.split(sysUser.getSuperiorIds());
			if (superiorIds != null && !superiorIds.isEmpty()) {
				for (String superiorId : superiorIds) {
					if (!StringUtils.equals(superiorId, sysUser.getAccount())) {
						Membership membership = new Membership();
						membership.setActorId(sysUser.getAccount());
						membership.setSuperiorId(superiorId);
						membership.setModifyBy(sysUser.getUpdateBy());
						membership.setModifyDate(new java.util.Date());
						membership.setType("Superior");
						membership.setObjectId("Superior");
						membership.setObjectValue(superiorId);
						membershipService.save(membership);
					}
				}
			}
		}

		String cacheKey = "sys_user_" + sysUser.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "sys_user_all_" + sysUser.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "cache_user_" + sysUser.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "cache_sys_user_" + sysUser.getActorId();
		CacheFactory.remove("user", cacheKey);

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
		}
		return true;
	}

	@Transactional
	public boolean updateUser(SysUser sysUser) {
		sysUser.setLoginRetry(0);
		sysUser.setLockLoginTime(null);
		sysUserMapper.updateSysUser(sysUser);
		String cacheKey = "sys_user_" + sysUser.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "cache_sys_user_" + sysUser.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "sys_user_all_" + sysUser.getActorId();
		CacheFactory.remove("user", cacheKey);

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
		}
		return true;
	}

	/**
	 * 更新登录密锁
	 * 
	 * @param model
	 */
	@Transactional
	public void updateUserLoginSecret(SysUser model) {
		sysUserMapper.updateUserLoginSecret(model);
	}

	@Transactional
	public boolean updateUserRole(SysUser user, Set<SysRole> newRoles) {
		// 先删除用户之前的权限
		List<SysUserRole> userRoles = sysUserRoleMapper.getSysUserRolesByUserId(user.getActorId());
		if (userRoles != null && !userRoles.isEmpty()) {
			for (SysUserRole userRole : userRoles) {
				TableModel table = new TableModel();
				table.setTableName("userrole");
				table.addStringColumn("userId", user.getActorId());
				table.addStringColumn("roleId", userRole.getRoleId());
				tableDataService.deleteTableData(table);
			}
		}

		List<Membership> memberships = new ArrayList<Membership>();

		// 增加新权限
		if (newRoles != null && !newRoles.isEmpty()) {
			Iterator<SysRole> iter = newRoles.iterator();
			while (iter.hasNext()) {
				SysRole role = iter.next();

				SysUserRole userRole = new SysUserRole();
				userRole.setId(idGenerator.getNextId());
				userRole.setUserId(user.getActorId());
				userRole.setRoleId(String.valueOf(role.getId()));
				userRole.setCreateDate(new Date());
				userRole.setCreateBy(user.getUpdateBy());
				sysUserRoleMapper.insertSysUserRole(userRole);

				Membership membership = new Membership();
				membership.setActorId(user.getAccount());
				membership.setModifyBy(user.getUpdateBy());
				membership.setModifyDate(new java.util.Date());
				membership.setNodeId(user.getDeptId());
				membership.setRoleId(role.getId());
				membership.setObjectId("userrole");
				membership.setObjectValue(String.valueOf(userRole.getId()));
				memberships.add(membership);
			}
		}

		membershipService.saveMemberships(user.getDeptId(), "UserRole", memberships);

		String cacheKey = "sys_user_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "sys_user_all_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "cache_sys_user_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
		}

		return true;
	}

	@Transactional
	public boolean insertUserRole(SysUser user, Set<SysRole> newRoles) {
		// 增加新权限
		List<Membership> memberships = new ArrayList<Membership>();
		if (newRoles != null && !newRoles.isEmpty()) {
			Iterator<SysRole> iter = newRoles.iterator();
			while (iter.hasNext()) {
				SysRole role = iter.next();

				SysUserRole userRole = new SysUserRole();
				userRole.setId(idGenerator.getNextId());
				userRole.setUserId(user.getActorId());
				userRole.setRoleId(String.valueOf(role.getId()));
				userRole.setCreateDate(new Date());
				userRole.setCreateBy(user.getUpdateBy());
				sysUserRoleMapper.insertSysUserRole(userRole);

				Membership membership = new Membership();
				membership.setActorId(user.getAccount());
				membership.setModifyBy(user.getUpdateBy());
				membership.setModifyDate(new java.util.Date());
				membership.setNodeId(user.getDeptId());
				membership.setRoleId(role.getId());
				membership.setObjectId("userrole");
				membership.setObjectValue(String.valueOf(userRole.getId()));
				memberships.add(membership);
			}
		}

		membershipService.insertMemberships(user.getDeptId(), "UserRole", memberships);

		String cacheKey = "sys_user_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "cache_sys_user_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "sys_user_all_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);

		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
		}

		return true;
	}

	@Transactional
	public boolean deleteUserRole(SysUser user, Set<SysRole> newRoles) {
		// 先删除用户之前的权限
		// List<Membership> memberships = new ArrayList<Membership>();
		List<SysUserRole> userRoles = sysUserRoleMapper.getSysUserRolesByUserId(user.getActorId());
		if (userRoles != null && !userRoles.isEmpty() && newRoles != null && !newRoles.isEmpty()) {
			Iterator<SysRole> iter = newRoles.iterator();
			while (iter.hasNext()) {
				SysRole role = iter.next();

				TableModel table = new TableModel();
				table.setTableName("userrole");
				table.addStringColumn("userId", user.getActorId());
				table.addStringColumn("roleId", String.valueOf(role.getId()));
				tableDataService.deleteTableData(table);

				MembershipQuery query = new MembershipQuery();
				query.setObjectId("userrole");
				query.setActorId(user.getAccount());
				query.setNodeId(user.getDeptId());
				query.setRoleId(role.getId());
				query.setObjectValue(String.valueOf(role.getId()));
				membershipService.deleteMemberships(query);
			}
		}

		String cacheKey = "sys_user_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "sys_user_all_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);

		cacheKey = "cache_sys_user_" + user.getActorId();
		CacheFactory.remove("user", cacheKey);
		if (SystemConfig.getBoolean("use_query_cache")) {
			CacheFactory.clear("application");
			CacheFactory.clear("tree");
		}
		return true;
	}

}
