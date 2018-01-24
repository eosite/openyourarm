package com.glaf.form.web.springmvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.form.handler.FormHandlerFactory;

@Controller("/form/handler")
@RequestMapping("/form/handler")
public class FormHandlerController {

	/**
	 * 执行业务处理
	 * 
	 * @param request
	 * @param response
	 * @return 返回结果为JSON对象
	 */
	@RequestMapping("/execute")
	@ResponseBody
	public byte[] execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject json = FormHandlerFactory.getInstance().execute(request, response);
		return json.toJSONString().getBytes("UTF-8");
	}

}
