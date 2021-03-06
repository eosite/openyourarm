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

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.SysUser;

@Transactional
public interface AuthorizeService {

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	@Transactional
	SysUser authorize(String account, String pwd);

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param loginSecret
	 * @return
	 */
	@Transactional
	SysUser authorizeLoginSecret(String account, String loginSecret);

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @return
	 */
	@Transactional
	SysUser login(String account);

	/**
	 * 用户登陆
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	@Transactional
	SysUser login(String account, String pwd);
	
	/**
	 * 用户登陆
	 * 根据手机号码寻找出用户并验证
	 * @param account
	 * @param pwd
	 * @return
	 */
	SysUser authorizeByTelephone(String telephone);

}