package com.glaf.expression.core.util;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.identity.User;

public class SysConstant {

	private User user;
	private SystemConfig systemConfig;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
}
