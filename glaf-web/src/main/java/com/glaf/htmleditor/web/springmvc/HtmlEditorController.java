package com.glaf.htmleditor.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.glaf.core.identity.User;
import com.glaf.core.util.RequestUtils;
import com.glaf.expression.core.util.SysConstant;
import com.glaf.htmleditor.service.HtmlEditorService;

@Controller("/html/editor")
@RequestMapping("/html/editor")
public class HtmlEditorController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected HtmlEditorService htmlEditorService;

	@Resource
	public void setHtmlEditorService(HtmlEditorService htmlEditorService) {
		this.htmlEditorService = htmlEditorService;
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/htmleditor/html_edit", modelMap);
	}
	@RequestMapping("/preview")
	public ModelAndView preview(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/htmleditor/html_preview", modelMap);
	}
	@ResponseBody
	@RequestMapping("/getPreviewHTML")
	public byte[] getPreviewHTML(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)
			throws UnsupportedEncodingException {
		String htmltemplate = RequestUtils.getString(request, "htmltemplate");
		// 获取变量
		String variableJSONString = RequestUtils.getString(request,
				"variableJSONString");
		JSONArray expJson = JSON.parseArray(variableJSONString);
		// 获取当前用户 此处可扩展声明CONST对象
		User currentUser = RequestUtils.getUser(request);
		SysConstant constant = new SysConstant();
		constant.setUser(currentUser);
		try {
			// 生成测试内容
			htmltemplate = htmlEditorService.getPreviewHtml(htmltemplate,
					expJson, constant);
		} catch (Exception e) {	
			e.printStackTrace();
			logger.error("HtmlEditorController / getPreviewHTML 错误：" + e.getMessage());
		} 
		return htmltemplate.getBytes("UTF-8");
	}
}
