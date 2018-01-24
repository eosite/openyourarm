package com.glaf.form.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.RequestUtils;
import com.glaf.form.core.domain.FormParamsRelation;
import com.glaf.form.core.service.FormParamsRelationService;
import com.glaf.form.core.service.FormParamsService;

/**
 * 参数关系检查
 * 数据集引用关系检查
 * 
 */
@Controller("/form/relationCheck")
@RequestMapping("/form/relationCheck")
public class FormRelationController {
	protected static final Log logger = LogFactory.getLog(FormRelationController.class);

	public FormRelationController() {

	}
	
	@Autowired
	private FormParamsRelationService formParamsRelationService;

	@RequestMapping("/param")
	public @ResponseBody byte[] getChartsDatas(HttpServletRequest request){
		
		String saveId = RequestUtils.getParameter(request, "saveId");
		String param = RequestUtils.getParameter(request, "param");
		if(StringUtils.isNotEmpty(param)){
			JSONObject paramObj = JSON.parseObject(param);
			String pageId = null;
			String paramName = paramObj.getString("param");
			String databaseId = paramObj.getString("databaseId");
			try {
				List<Map<String, Object>> list = formParamsRelationService.queryParamRelation(pageId,saveId,paramName,databaseId);
				return JSON.toJSONString(list).getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "[]".getBytes();
	}

}
