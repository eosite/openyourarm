package com.glaf.expression.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.BaseDataInfo;
 
import com.glaf.core.identity.User;
import com.glaf.core.util.RequestUtils;
import com.glaf.expression.core.util.SysConstant;
import com.glaf.expression.service.ExpressionService;

@Controller("/expression/defined")
@RequestMapping("/expression/defined")
public class ExpressionController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected ExpressionService expressionService;

	@Resource
	public void setExpressionService(ExpressionService expressionService) {
		this.expressionService = expressionService;
	}

	@RequestMapping("/view")
	public ModelAndView viewExpression(HttpServletRequest request, ModelMap modelMap) {

		String view = request.getParameter("view");
		String category=request.getParameter("category")!=null?request.getParameter("category"):"";
		
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}	
		List<BaseDataInfo> operatorList=expressionService.getExpressionsByCategory("expre_operator",category);
		List<BaseDataInfo> splitList=expressionService.getExpressionsByCategory("expre_split",category);
		modelMap.put("expre_operator", operatorList);
		modelMap.put("expre_split", splitList);
		String expressStr = expressionService.getAllExpressions(category);
		modelMap.put("expressions", expressStr);
		return new ModelAndView("/expression/expressions_edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/validataExpression")
	public byte[] validataExpression(HttpServletRequest request,
			ModelMap modelMap) {
		String expression = RequestUtils.getString(request, "expression");
		//获取领域
		String category=request.getParameter("category")!=null?request.getParameter("category"):"";
		//是否不需要验证  true不验证 
		Boolean notVal = RequestUtils.getBoolean(request, "notValidate");
		byte[] returnBytes = null;
		JSONObject resultJson = null;
		//获取变量
		String variableJSONString = RequestUtils.getString(request,
				"variableJSONString");
		JSONArray expJson = JSONArray.parseArray(variableJSONString);
		//获取当前用户 此处可扩展声明CONST对象
		User currentUser=RequestUtils.getUser(request);
		SysConstant constant=new SysConstant();
		constant.setUser(currentUser);
		if(!notVal){
			resultJson = expressionService.expressionTest(category,expression, expJson,constant);
		}else{
			resultJson = JSON.parseObject("{\"message\":\"表达式未验证,请自行检测\",\"errorToken\":\"\",\"errorPosition\":-1,\"execFlag\":1}");
		}
		try {
			returnBytes = resultJson.toJSONString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnBytes;
	}
}
