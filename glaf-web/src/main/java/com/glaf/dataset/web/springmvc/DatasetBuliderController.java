package com.glaf.dataset.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.RequestUtils;
 

@Controller("/dataset/bulider")
@RequestMapping("/dataset/bulider")
public class DatasetBuliderController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/view")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/dataset/dataset_builder", modelMap);
	}
	

	@ResponseBody
	@RequestMapping("/validata")
	public byte[] validataExpression(HttpServletRequest request,
			ModelMap modelMap) {
		String expression = RequestUtils.getString(request, "expression");
		byte[] returnBytes = null;
		JSONObject resultJson = null;
		try {
			returnBytes = resultJson.toJSONString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnBytes;
	}

	@ResponseBody
	@RequestMapping("/getTableColumn")
	public byte[] getTableColumn(HttpServletRequest request,
			ModelMap modelMap) {
		String tableId = RequestUtils.getString(request, "tableId");
		byte[] returnBytes = null;
		JSONObject resultJson = new JSONObject();
		try {
			//待实现业务方法(获取数据表定义)
			resultJson.put("columnJson", "");
			returnBytes = resultJson.toJSONString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnBytes;
	}
	
	
}
