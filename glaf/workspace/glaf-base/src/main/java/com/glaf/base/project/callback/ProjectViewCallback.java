package com.glaf.base.project.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.project.bean.UpdateProjectBean;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.util.DBUtils;
import com.glaf.core.web.callback.LoginCallback;

public class ProjectViewCallback implements LoginCallback {
	protected static final Log logger = LogFactory.getLog(ProjectViewCallback.class);

	public void afterLogin(String actorId, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (SystemConfig.getBoolean("refreshProjectView", false)) {
				if (DBUtils.tableExists("PROJECT_VIEW")) {
					UpdateProjectBean bean = new UpdateProjectBean();
					bean.updateProjectView(actorId);
				}
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

}
