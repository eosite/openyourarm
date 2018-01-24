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

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

@Component
public interface SysUserMapper {

	void deleteSysUserById(Long id);

	void deleteSysUsers(SysUserQuery query);

	List<SysUser> getAuthorizedUsers(SysUserQuery query);

	List<SysUser> getAuthorizedUsersByUserId(String authorizeFrom);

	int getCountAuthorizedUsers(SysUserQuery query);

	String getPasswordHashByAccount(String account);

	List<UserRole> getRoleUserViews(UserRoleQuery query);

	List<UserRole> getRoleUserViews_oracle(UserRoleQuery query);

	List<UserRole> getRoleUserViews_postgresql(UserRoleQuery query);

	List<SysUser> getSysRoleUsers(Long roleId);

	SysUser getSysUserByAccount(String account);

	SysUser getSysUserByAppId(String appId);

	SysUser getSysUserByMail(String mail);

	SysUser getSysUserByMobile(String mobile);

	int getSysUserCount(SysUserQuery query);

	int getSysUserExCount(SysUserQuery query);

	SysUser getSysUserLoginSecret(String account);

	List<SysUser> getSysUserLoginSecretList(SysUserQuery query);

	List<SysUser> getSysUsers(SysUserQuery query);

	List<SysUser> getSysUsersByAppId(Long appId);

	List<SysUser> getSysUsersByPostId(long postId);

	List<SysUser> getSysUsersByRoleCode(String roleCode);

	List<SysUser> getSysUsersByRoleCode_oracle(String roleCode);

	List<SysUser> getSysUsersByRoleCode_postgresql(String roleCode);

	List<SysUser> getSysUsersByRoleId(long roleId);

	List<SysUser> getSysUsersByRoleId_oracle(long roleId);

	List<SysUser> getSysUsersByRoleId_postgresql(long roleId);

	List<SysUser> getSysUsersByRoleIds(@Param("roleIds") List<Long> roleIds);

	List<SysUser> getSysUsersByRoleIds_oracle(@Param("roleIds") List<Long> roleIds);

	List<SysUser> getSysUsersByRoleIds_postgresql(@Param("roleIds") List<Long> roleIds);

	List<SysUser> getSysUsersEx(SysUserQuery query);

	SysUser getSysUserToken(String account);

	void insertSysUser(SysUser model);

	void resetLoginStatus(SysUser model);

	void resetStatus(SysUser model);

	void resetUserAppSecret(SysUser model);

	void resetUserToken(SysUser model);

	void updateSysUser(SysUser model);

	void updateUserLoginRetry(SysUser model);

	void updateUserLoginSecret(SysUser model);

	void updateUserPassword(SysUser model);

}
