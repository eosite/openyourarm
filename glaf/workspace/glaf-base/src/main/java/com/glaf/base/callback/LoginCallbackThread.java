package com.glaf.base.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.web.callback.LoginCallback;

public class LoginCallbackThread extends Thread {

	private static final Log logger = LogFactory.getLog(LoginCallbackThread.class);

	protected String actorId;
	protected LoginCallback loginCallback;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	public LoginCallbackThread(LoginCallback loginCallback, String actorId, HttpServletRequest request,
			HttpServletResponse response) {
		this.loginCallback = loginCallback;
		this.actorId = actorId;
		this.request = request;
		this.response = response;
	}

	public void run() {
		logger.debug("-------------------------callback--------------------");
		logger.debug(loginCallback.getClass().getName());
		loginCallback.afterLogin(actorId, request, response);
	}

}
