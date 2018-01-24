package com.glaf.base.mq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.util.RequestUtils;
import com.glaf.core.web.callback.LoginCallback;

public class RabbitMQLoginCallback implements LoginCallback {
	private static final Log logger = LogFactory.getLog(RabbitMQLoginCallback.class);

	public void afterLogin(String actorId, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("------------------RabbitMQLoginCallback---------------------------");
		String loginIP = RequestUtils.getIPAddress(request);
		String token = DigestUtils.md5Hex(loginIP + "/" + actorId);
		Runnable command = new RabbitMQLoginQueueProducerThread(token, actorId, loginIP, "queue_db_" + token);
		com.glaf.core.util.threads.ThreadFactory.execute(command);
	}

}
