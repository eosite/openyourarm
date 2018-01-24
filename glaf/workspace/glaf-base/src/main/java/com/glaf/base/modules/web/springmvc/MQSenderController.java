package com.glaf.base.modules.web.springmvc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.glaf.base.mq.RabbitMQLoginQueueProducer;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.RequestUtils;

@Controller("/mq/sender")
@RequestMapping("/mq/sender")
public class MQSenderController {
	protected static final Log logger = LogFactory.getLog(MQSenderController.class);

	protected IServerEntityService serverEntityService;

	@RequestMapping("/send")
	public void send(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("---------------------------send----------------------");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String userId = loginContext.getActorId();
		String loginIP = RequestUtils.getIPAddress(request);
		String endPointName = request.getParameter("endPointName");
		String token = request.getParameter("token");
		try {
			logger.debug("endPointName:" + endPointName);
			ServerEntity serverEntity = serverEntityService.getServerEntityByMapping("queue_db");
			if (serverEntity != null) {
				RabbitMQLoginQueueProducer producer = new RabbitMQLoginQueueProducer(endPointName);
				producer.setHost(serverEntity.getHost());
				producer.setPort(serverEntity.getPort());
				producer.setUsername(serverEntity.getUser());
				String password = SecurityUtils.decode(serverEntity.getKey(), serverEntity.getPassword());
				producer.setPassword(password);
				JSONObject json = producer.buildMessage(token, userId, loginIP);
				logger.debug("host:" + serverEntity.getHost());
				logger.debug("port:" + serverEntity.getPort());
				logger.debug("user:" + serverEntity.getUser());
				// logger.debug("pwd:" + password);
				logger.debug("send message:" + json.toJSONString());
				// producer.sendBytesMessage(json.toJSONString().getBytes("UTF-8"));
				producer.sendBytesMessage(Hex.encodeHexString(json.toJSONString().getBytes()).getBytes());
			} else {
				logger.warn("不能发送消息，别名为queue_db的消息服务未配置，请联系管理员。");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	@javax.annotation.Resource
	public void setServerEntityService(IServerEntityService serverEntityService) {
		this.serverEntityService = serverEntityService;
	}

}
