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
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.LockedHolder;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.AuthorizeService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;

@Service("authorizeService")
@Transactional
public class AuthorizeServiceImpl implements AuthorizeService {
	private static final Log logger = LogFactory.getLog(AuthorizeServiceImpl.class);

	private static Configuration conf = BaseConfiguration.create();

	private SysDepartmentService sysDepartmentService;

	private SysUserService sysUserService;
	
	/**
	 * 用户登陆
	 * 仅仅验证用户名
	 * @param account
	 * @param pwd
	 * @return
	 */
	@Transactional
	public SysUser authorizeByTelephone(String telephone) {
		SysUser bean = sysUserService.findByMobile(telephone);
		if (bean != null) {
			//获取系统参数登录约束规则启用标识
			boolean loginRuleLimit=SystemConfig.getBoolean("login_rule_limit",true);
			if (bean.isDepartmentAdmin()) {
				logger.debug(bean.getAccount() + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(bean.getAccount() + " is system admin");
			}
			if(StringUtils.isNotEmpty(bean.getStatus()) && StringUtils.equals(bean.getStatus(),"1")){	//账号无效
				return null;
			}
			if (bean.getLocked() == 1) {// 帐号禁止
				return null;
			}
			if (bean.getDeleteFlag() == 1) {// 帐号禁止
				return null;
			}
			if (!bean.isSystemAdmin()&&loginRuleLimit) {
				/**
				 * 当登录重试次数大于系统默认的重试次数并且登录时间间隔没有达到系统默认的时间间隔，登录失败
				 */
				// 从字典获取连续登录失败次数
				BaseDataManager bm = BaseDataManager.getInstance();
				BaseDataInfo baseDataInfo = bm.getBaseData("failures", "userLockRule");
				int retryCount = conf.getInt("login.retryCount", 10);
				if (baseDataInfo != null) {
					retryCount = Integer.parseInt(baseDataInfo.getValue());
				}
				if (bean.getLoginRetry() >= retryCount) {
					LockedHolder.setLocked("1");
					return null;
				}
			}
		}
		return bean;
	}
	
	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	@Transactional
	public SysUser authorize(String account, String pwd) {
		SysUser bean = sysUserService.findByAccount(account);
		if (bean != null) {
			//获取系统参数登录约束规则启用标识
			boolean loginRuleLimit=SystemConfig.getBoolean("login_rule_limit",true);
			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if(StringUtils.isNotEmpty(bean.getStatus()) && StringUtils.equals(bean.getStatus(),"1")){	//账号无效
				return null;
			}
			if (bean.getLocked() == 1) {// 帐号禁止
				return null;
			}
			if (bean.getDeleteFlag() == 1) {// 帐号禁止
				return null;
			}
			if (!bean.isSystemAdmin()&&loginRuleLimit) {
				/**
				 * 当登录重试次数大于系统默认的重试次数并且登录时间间隔没有达到系统默认的时间间隔，登录失败
				 */
				// 从字典获取连续登录失败次数
				BaseDataManager bm = BaseDataManager.getInstance();
				BaseDataInfo baseDataInfo = bm.getBaseData("failures", "userLockRule");
				int retryCount = conf.getInt("login.retryCount", 10);
				if (baseDataInfo != null) {
					retryCount = Integer.parseInt(baseDataInfo.getValue());
				}
				if (bean.getLoginRetry() >= retryCount) {
					LockedHolder.setLocked("1");
					return null;
				}
			}
			boolean success = sysUserService.checkPassword(account, pwd);
			if (!success) {// 密码不匹配
				sysUserService.loginFailure(account);
				bean = null;
			}
		}
		return bean;
	}

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param loginSecret
	 * @return
	 */
	@Transactional
	public SysUser authorizeLoginSecret(String account, String loginSecret) {
		SysUser bean = sysUserService.findByAccount(account);
		if (bean != null) {
			//获取系统参数登录约束规则启用标识
			boolean loginRuleLimit=SystemConfig.getBoolean("login_rule_limit",true);
			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if(StringUtils.isNotEmpty(bean.getStatus()) && StringUtils.equals(bean.getStatus(),"1")){	//账号无效
				return null;
			}
			if (bean.getLocked() == 1) {// 帐号禁止
				return null;
			}
			if (bean.getDeleteFlag() == 1) {// 帐号禁止
				return null;
			}
			if (!StringUtils.equals(bean.getSecretLoginFlag(), "Y")) {
				return null;// 不允许密锁登录
			}
			if (!bean.isSystemAdmin()&&loginRuleLimit) {
				/**
				 * 当登录重试次数大于系统默认的重试次数并且登录时间间隔没有达到系统默认的时间间隔，登录失败
				 */
				// 从字典获取连续登录失败次数
				BaseDataManager bm = BaseDataManager.getInstance();
				BaseDataInfo baseDataInfo = bm.getBaseData("failures", "userLockRule");
				int retryCount = conf.getInt("login.retryCount", 5);
				double unlocktime=0d;
				if (baseDataInfo != null) {
					retryCount = Integer.parseInt(baseDataInfo.getValue());
					//获取自动解锁时长
					baseDataInfo =bm.getBaseData("autoUnlockTime", "userLockRule");
					unlocktime=Double.parseDouble(baseDataInfo.getValue())*60*60*1000;
				}
			    boolean lockFlag=bean.getLocked()==1?true:false;
			    lockFlag=lockFlag&&(System.currentTimeMillis()-bean.getLockLoginTime().getTime()>unlocktime?false:true);
				if (lockFlag&&bean.getLoginRetry() >= retryCount) {
					return null;
				}
			}
			boolean success = sysUserService.checkLoginSecret(account, loginSecret);
			if (!success) {// 密码不匹配
				sysUserService.loginFailure(account);
				bean = null;
			}
		}
		return bean;
	}

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	@Transactional
	public SysUser login(String account) {
		SysUser bean = sysUserService.findByAccountWithAll(account);
		if (bean != null) {
			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if (bean.getDeleteFlag() == 1) {// 帐号禁止
				return null;
			}

			// 取出用户对应的模块权限
			bean = sysUserService.getUserPrivileges(bean);

			if (bean.getDepartment() != null) {
				// 取出用户的部门列表
				List<SysDepartment> list = new ArrayList<SysDepartment>();
				sysDepartmentService.findNestingDepartment(list, bean.getDepartment());
				bean.setNestingDepartment(list);
			}
		}
		return bean;
	}

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	@Transactional
	public SysUser login(String account, String pwd) {
		SysUser bean = sysUserService.findByAccountWithAll(account);
		if (bean != null) {
			//获取系统参数登录约束规则启用标识
			boolean loginRuleLimit=SystemConfig.getBoolean("login_rule_limit",true);
			if (bean.isDepartmentAdmin()) {
				logger.debug(account + " is department admin");
			}
			if (bean.isSystemAdmin()) {
				logger.debug(account + " is system admin");
			}
			if(StringUtils.isNotEmpty(bean.getStatus()) && StringUtils.equals(bean.getStatus(),"1")){	//账号无效
				return null;
			}
			if (bean.getLocked() == 1) {// 帐号禁止
				return null;
			}
			if (bean.getDeleteFlag() == 1) {// 帐号禁止
				return null;
			}

			if (!bean.isSystemAdmin()&&loginRuleLimit) {
				/**
				 * 当登录重试次数大于系统默认的重试次数并且登录时间间隔没有达到系统默认的时间间隔，登录失败
				 */
				// 从字典获取连续登录失败次数
				BaseDataManager bm = BaseDataManager.getInstance();
				BaseDataInfo baseDataInfo = bm.getBaseData("failures", "userLockRule");
				int retryCount = conf.getInt("login.retryCount", 5);
				double unlocktime=0d;
				if (baseDataInfo != null) {
					retryCount = Integer.parseInt(baseDataInfo.getValue());
					//获取自动解锁时长
					baseDataInfo =bm.getBaseData("autoUnlockTime", "userLockRule");
					unlocktime=Double.parseDouble(baseDataInfo.getValue())*60*60*1000;
				}
			    boolean lockFlag=bean.getLocked()==1?true:false;
			    lockFlag=lockFlag&&(System.currentTimeMillis()-bean.getLockLoginTime().getTime()>unlocktime?false:true);
				if (lockFlag&&bean.getLoginRetry() >= retryCount) {
					return null;
				}
			}
			boolean success = sysUserService.checkPassword(account, pwd);
			if (!success) {// 密码不匹配
				sysUserService.loginFailure(account);
				bean = null;
			} else if (bean.getAccountType() != 1) {
				// 取出用户对应的模块权限
				bean = sysUserService.getUserPrivileges(bean);

				// 取出用户的部门列表
				List<SysDepartment> list = new ArrayList<SysDepartment>();
				sysDepartmentService.findNestingDepartment(list, bean.getDepartment());
				bean.setNestingDepartment(list);
			}
		}
		return bean;
	}

	@Resource
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

}