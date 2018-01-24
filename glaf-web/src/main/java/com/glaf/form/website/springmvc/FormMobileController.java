package com.glaf.form.website.springmvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemConfig;

/**
 * 
 * Rest响应类
 *
 */

@Controller("/mobile")
@RequestMapping("/mobile")
public class FormMobileController {
	protected static final Log logger = LogFactory.getLog(FormMobileController.class);

	@RequestMapping("/urlInfo")
	@ResponseBody
	public byte[] urlInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONObject result = new JSONObject();
		result.put("home", SystemConfig.getString("mobileHome"));
		result.put("login", (SystemConfig.getString("mobileLogin")+"").replaceAll("/mx/", "/website/public/"));
		return result.toJSONString().getBytes("UTF-8");
	}
}
