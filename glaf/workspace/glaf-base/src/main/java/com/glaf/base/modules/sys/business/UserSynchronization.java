package com.glaf.base.modules.sys.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;

public class UserSynchronization {
	protected final static Log logger = LogFactory.getLog(UserSynchronization.class);

	public void syncUser(List<SysUser> users) {
		logger.debug("----------------UserSynchronization------------------");
		if (users != null && users.size() > 0) {
			logger.debug("user size:" + users.size());
			String currentName = Environment.getCurrentSystemName();
			SysUserService sysUserService = ContextFactory.getBean("sysUserService");
			DatabaseConnectionConfig config = new DatabaseConnectionConfig();
			List<Database> databases = config.getDatabases();
			if (databases != null && !databases.isEmpty()) {
				for (Database database : databases) {
					try {
						Environment.setCurrentSystemName(database.getName());
						logger.debug("sys name:" + Environment.getCurrentSystemName());

						List<SysUser> userList = sysUserService.getSysUserList();
						Map<String, SysUser> userMap = new HashMap<String, SysUser>();
						for (SysUser model : userList) {
							userMap.put(model.getAccount().toLowerCase(), model);
						}
						for (SysUser user : users) {
							if (userMap.get(user.getAccount().toLowerCase()) != null) {
								SysUser model = userMap.get(user.getAccount().toLowerCase());
								model.setPasswordHash(user.getPasswordHash());
								sysUserService.changePassword(user.getAccount(), user.getPasswordHash(),user.getPwdUpdateFlag());
							} else {
								user.setAdminFlag("0");
								sysUserService.create(user);
							}
						}
					} catch (Exception ex) {
						//ex.printStackTrace();
						logger.error(ex);
					} finally {
						Environment.setCurrentSystemName(currentName);
					}
				}
			}
		}
	}

}
