package com.glaf.expression.core.util;

import com.glaf.base.security.BaseIdentityFactory;
import com.glaf.core.config.SystemConfig;

public class SysConstantFunc {
	/**
	 * 获取系统名称
	 * 
	 * @return
	 */
	public static String getSysName() {
		return SystemConfig.getString("res_system_name");
	}

	/**
	 * 获取系统版本
	 * 
	 * @return
	 */
	public static String getSysVersion() {
		return SystemConfig.getString("res_version");
	}

	/**
	 * 获取当前用户名
	 * 
	 * @return
	 */
	public static String getCurrentUserName(Object param) {
		if (param instanceof SysConstant) {
			return ((SysConstant) param).getUser() != null ? ((SysConstant) param).getUser().getName() : "";
		}
		return "";
	}

	/**
	 * 获取当前用户账号
	 * 
	 * @return
	 */
	public static String getCurrentUserAccount(Object param) {
		if (param instanceof SysConstant) {
			return ((SysConstant) param).getUser() != null ? ((SysConstant) param).getUser().getActorId() : "";
		}
		return "";

	}

	/**
	 * 获取当前用户部门名称
	 * 
	 * @param sysConstant
	 * @return
	 */
	public static String getCurrentUserDept(Object param) {
		String departName = "";
		if (param instanceof SysConstant) {
			long deptId = ((SysConstant) param).getUser() != null ? ((SysConstant) param).getUser().getDeptId() : 0;
			departName = deptId > 0l && BaseIdentityFactory.getDepartmentById(deptId) != null
					? BaseIdentityFactory.getDepartmentById(deptId).getName() : "";

		}
		return departName;
	}

	/**
	 * 获取当前用户部门code
	 * 
	 * @param param
	 * @return
	 */
	public static String getCurrentUserDeptCode(Object param) {
		String departCode = "";
		if (param instanceof SysConstant) {
			long deptId = ((SysConstant) param).getUser() != null ? ((SysConstant) param).getUser().getDeptId() : 0;
			departCode = deptId > 0l && BaseIdentityFactory.getDepartmentById(deptId) != null
					? BaseIdentityFactory.getDepartmentById(deptId).getCode() : "";

		}
		return departCode;
	}

	/**
	 * 获取当前用户部门ID
	 * 
	 * @param sysConstant
	 * @return
	 */
	public static Long getCurrentUserDeptId(Object param) {
		long deptId = 0l;
		if (param instanceof SysConstant) {
			deptId = ((SysConstant) param).getUser() != null ? ((SysConstant) param).getUser().getDeptId() : 0l;
		}
		return deptId;
	}

	/**
	 * 获取当前用户上级部门名称
	 * 
	 * @param sysConstant
	 * @return
	 */
	public static String getCurrentUserParentDept(Object param) {
		String departName = "";
		if (param instanceof SysConstant) {
			long deptId = ((SysConstant) param).getUser() != null ? ((SysConstant) param).getUser().getDeptId() : 0;
			departName = deptId > 0l && BaseIdentityFactory.getDepartmentById(deptId) != null
					&& BaseIdentityFactory.getDepartmentById(deptId).getParent() != null
							? BaseIdentityFactory.getDepartmentById(deptId).getParent().getName() : "";

		}
		return departName;
	}

	/**
	 * 获取当前用户上级部门ID
	 * 
	 * @param sysConstant
	 * @return
	 */
	public static Long getCurrentUserParentDeptId(Object param) {
		long pDeptId = 0l;
		if (param instanceof SysConstant) {
			long deptId = ((SysConstant) param).getUser() != null ? ((SysConstant) param).getUser().getDeptId() : 0l;
			pDeptId = deptId > 0l && BaseIdentityFactory.getDepartmentById(deptId) != null
					&& BaseIdentityFactory.getDepartmentById(deptId).getParent() != null
							? BaseIdentityFactory.getDepartmentById(deptId).getParent().getId() : 0l;
		}
		return pDeptId;
	}
	/**
	 * 获取当前用户上级部门CODE
	 * 
	 * @param sysConstant
	 * @return
	 */
	public static String getCurrentUserParentDeptCode(Object param) {
		String departCode = "";
		if (param instanceof SysConstant) {
			long deptId = ((SysConstant) param).getUser() != null ? ((SysConstant) param).getUser().getDeptId() : 0l;
			departCode = deptId > 0l && BaseIdentityFactory.getDepartmentById(deptId) != null
					&& BaseIdentityFactory.getDepartmentById(deptId).getParent() != null
							? BaseIdentityFactory.getDepartmentById(deptId).getParent().getCode() : "";
		}
		return departCode;
	}
	/**
	 * 获取版权信息
	 * 
	 * @return
	 */
	public static String getCopyright() {
		return SystemConfig.getString("res_copyright");
	}
}
