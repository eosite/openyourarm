package com.glaf.form.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemConfig;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/form/mobile")
public class FormMobileResource {
	protected static final Log logger = LogFactory.getLog(FormMobileResource.class);

	@GET
	@POST
	@Path("/urlInfo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] urlInfo(@Context HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		result.put("home", SystemConfig.getString("mobileHome"));
		//result.put("home", "/textjs.html");
		return result.toJSONString().getBytes("UTF-8");
	}
}
