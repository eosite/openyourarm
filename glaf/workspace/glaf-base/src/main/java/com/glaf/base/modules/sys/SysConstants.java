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

package com.glaf.base.modules.sys;

public final class SysConstants {
	// 是否失效
	public static int BLOCKED_0 = 0; // 否

	public static int BLOCKED_1 = 1; // 是

	public static String BRANCH_PREFIX = "branch_";

	public static String BRANCH_ADMIN = "BranchAdmin";// 分级管理员角色代码

	public static String PROJECT_ADMIN = "ProjectAdmin";// 项目管理员角色代码

	public static String ORG_ADMIN = "OrgAdmin";// 机构管理员角色代码

	public static String SYSTEM_ADMINISTRATOR = "SystemAdministrator";// 分级管理员角色代码

	public static String DEPT_LEVEL = "DeptLevel";// 部门层级代码，取值为SYS_DICTORY表的ext11

	// 部门状态
	public static Integer DEPT_STATUS_0 = Integer.valueOf(0); // 有效
	public static Integer DEPT_STATUS_1 = Integer.valueOf(1); // 失效
 

	public static int SORT_FORWARD = 1;// 后移

	public static int SORT_PREVIOUS = 0;// 前移

	public static String TREE_APP = "02";// 模块结构树编号

	public static String TREE_BASE = "01";// 基础数据结构树编号

	public static String TREE_DEPT = "012";// 部门结构树编号

	public static String TREE_DICTORY = "011";// 数据字典结构树编号

	public static int TREE_ROOT = 1;// 目录根节点

	public static long BILLION = 100000000L;

	public static String USER_HEADSHIP = "UserHeadship";// 用户职位代码，取值为SYS_DICTORY表的code

	public static String USER_ACCOUNTTYPE = "AccountType";// 账户类型，取值为SYS_DICTORY表的code

	public static String WPF_DEF_URL = "/mx/wpf/menu/jump?menuId=";

	public static String FORM_DEF_URL = "/mx/form/page/viewPage?id=";

	public static final int PAGE_SIZE = 10;// 缺省页面大小

	public static final String SYS_DEPTS = "SYS_DEPTS";

	public static final String SYS_FUNCTIONS = "SYS_FUNCTIONS";

	public static final String SYS_SUBJECTS = "SYS_SUBJECTS";

	public static final String SYS_USERS = "SYS_USERS";

	public static final String UPLOAD_DIR = "/WEB-INF/upload/files/";
}