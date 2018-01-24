package com.glaf.lanxin.website.springmvc;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.cache.CacheFactory;

@RestController
@RequestMapping("/laxin/authorize")
public class LanxinAuthorizeResource {
	@RequestMapping("/callback")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] callHttpRequest(@Context HttpServletRequest request) throws IOException {
		JSONObject returnJson = new JSONObject();
		returnJson.put("success", 0);
		// 获取code
		String code = request.getParameter("code");
		// 获取访问状态
		String state = request.getParameter("state");
		// 验证状态码合法性
		if(StringUtils.isNotEmpty(code)&&StringUtils.isNotEmpty(state)){
			returnJson.put("success", 1);
			returnJson.put("code", code);
			// 记录数据到缓存
			long invalidTime = new Date().getTime() + 5 * 60 * 1000;
			JSONObject json = new JSONObject();
			json.put("invalidTime", invalidTime);
			json.put("code", code);
			CacheFactory.put("lanxin", "code", json.toJSONString());
		}
		
		return returnJson.toJSONString().getBytes("UTF-8");
	}
}
