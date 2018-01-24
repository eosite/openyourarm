package com.glaf.base.modules.sys.callback;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.DeptRole;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.DeptRoleQuery;
import com.glaf.base.modules.sys.query.SysDepartmentQuery;
import com.glaf.base.modules.sys.service.DeptRoleService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.web.callback.LoginCallback;

public class DeptRoleAuthorityCallback implements LoginCallback {

	protected static final Log logger = LogFactory.getLog(DeptRoleAuthorityCallback.class);

	@Override
	public void afterLogin(String actorId, HttpServletRequest request, HttpServletResponse response) {
		int retry = 0;
		boolean success = false;
		while (retry < 3 && !success) {
			try {
				retry++;
				if (SystemConfig.getBoolean("deptRoleAuthority", false)) {
					LoginContext loginContext = RequestUtils.getLoginContext(request);
					if (loginContext.isSystemAdministrator()) {
						return;
					}
					long deptId = loginContext.getUser().getDeptId();
					if (deptId == 0) {
						return;
					}
					SysTreeService sysTreeService = ContextFactory.getBean("sysTreeService");
					DeptRoleService deptRoleService = ContextFactory.getBean("deptRoleService");
					SysUserRoleService sysUserRoleService = ContextFactory.getBean("sysUserRoleService");
					SysDepartmentService sysDepartmentService = ContextFactory.getBean("sysDepartmentService");
					SysDepartment sysDepartment = sysDepartmentService.findById(deptId);
					if (sysDepartment != null && sysDepartment.getNode() != null) {
						SysTree node = sysDepartment.getNode();
						List<SysTree> treeList = new ArrayList<SysTree>();
						sysTreeService.getSysTreeParent(treeList, node.getId());
						if (!treeList.contains(node)) {
							treeList.add(node);
						}
						List<Long> nodeIds = new ArrayList<Long>();
						for (SysTree tree : treeList) {
							nodeIds.add(tree.getId());
						}
						SysDepartmentQuery query = new SysDepartmentQuery();
						query.nodeIds(nodeIds);
						List<SysDepartment> depts = sysDepartmentService.getSysDepartmentsByQueryCriteria(0, 1000,
								query);
						List<Long> deptIds = new ArrayList<Long>();
						deptIds.add(-1L);
						for (SysDepartment dept : depts) {
							// logger.debug(dept.getId() + "->" +
							// dept.getName());
							deptIds.add(dept.getId());
						}
						DeptRoleQuery q = new DeptRoleQuery();
						q.deptIds(deptIds);
						q.menuFlag(9);// 菜单角色
						List<DeptRole> deptRoles = deptRoleService.list(q);
						List<Long> roleIds = new ArrayList<Long>();
						if (deptRoles != null && !deptRoles.isEmpty()) {
							for (DeptRole dr : deptRoles) {
								if (dr.getDeptId() == deptId) {
									roleIds.add(dr.getRoleId());
								} else if (StringUtils.equals(dr.getIsPropagationAllowed(), "Y")) {
									roleIds.add(dr.getRoleId());
								}
							}
						}
						sysUserRoleService.saveUserRoles(loginContext.getActorId(), -1, roleIds);
						success = true;
					}
				}
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}

}
