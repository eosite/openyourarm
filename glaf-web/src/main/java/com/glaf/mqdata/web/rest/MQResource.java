package com.glaf.mqdata.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.util.ResponseUtils;
import com.glaf.mqdata.service.MQManagerService;

@Controller
@Path("/rs/mq")
public class MQResource {
	protected MQManagerService mqManagerService;
	@Path("/send")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] send(@Context HttpServletRequest request)
			throws IOException {
		mqManagerService.sendDataToMQ();
		return ResponseUtils.responseJsonResult(true);
	}
	@Path("/receive")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] receive(@Context HttpServletRequest request)
			throws IOException {
		mqManagerService.receiveDataFromMQ();
		return ResponseUtils.responseJsonResult(true);
	}
	@javax.annotation.Resource(name="com.glaf.mqdata.service.mqManagerServiceImpl")
	public void setMqManagerService(MQManagerService mqManagerService) {
		this.mqManagerService = mqManagerService;
	}

}
