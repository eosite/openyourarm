package com.glaf.form.website.springmvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ParamUtil;
import com.glaf.base.utils.RequestUtil;
import com.glaf.core.util.ResponseUtils;
import com.glaf.dep.base.factory.DataProcessFactory;
import com.glaf.dep.base.service.DepBaseWdataSetService;

@RestController
@RequestMapping("/ws/execute")
public class FormWebSiteDataContorller {

	static Log logger = LogFactory.getLog(FormWebSiteDataContorller.class);

	@Autowired
	protected DepBaseWdataSetService depBaseWdataSetService;
	
	@Autowired
	protected SysUserService sysUserService;

	/**
	 * 更新集执行
	 * 
	 * @param request
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/crud", produces = "text/html;charset=utf-8")
	public byte[] mtcrud(HttpServletRequest request, @RequestParam Map<String, Object> parameter) throws Exception {

		// 更新集 id
		long useId = MapUtils.getLong(parameter, "useId", 0L);
		// 传入的参数
		String params = MapUtils.getString(parameter, "params", null);
		// 操作类型
		String type = MapUtils.getString(parameter, "type", "cu");

		// Boolean isBatch = MapUtils.getBooleanValue(parameter, "isBatch",
		// false);

		if (StringUtils.isNotBlank(params)) {
			try {

				List<Map<String, Object>> paramList = new ArrayList<>();

				JSONArray jsonArray = JSON.parseArray(params);

				if (CollectionUtils.isNotEmpty(jsonArray)) {
					jsonArray.forEach(json -> {
						paramList.add((JSONObject) json);
					});
				}

				if ("cu".equalsIgnoreCase(type)) {
					DataProcessFactory.getInstance().execBatch(useId, paramList);
				} else {
					DataProcessFactory.getInstance().execRemove(useId, paramList);
				}

				JSONObject result = new JSONObject();

				result.put("total", paramList.size());
				result.put("data", paramList);
				result.put("statusCode", 200);
				result.put("status", 200);
				result.put("message", "操作成功!");

				return result.toJSONString().getBytes("UTF-8");

			} catch (Exception e) {
				logger.error("mtcrud" + e.getMessage());
				return ResponseUtils.responseJsonResult(false, e.getMessage());
			}
		}

		return ResponseUtils.responseJsonResult(false, "未传递任何参数信息！");

		// return FormDataController.mtcrud(useId, params, type);
	}

	/**
	 * 获取更新集参数
	 * 
	 * @param request
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/params", produces = "text/html;charset=utf-8")
	public byte[] params(HttpServletRequest request, @RequestParam Map<String, Object> parameter) throws Exception {

		JSONObject result = depBaseWdataSetService.getWdataSetCud(//
				MapUtils.getLong(parameter, "useId", 0L));

		result.remove("columns");

		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/savePwd")
	@ResponseBody
	public byte[] savePwd2(HttpServletRequest request, ModelMap modelMap) throws Exception {
		SysUser bean = RequestUtil.getLoginUser(request);
		boolean ret = false;
		String oldPwd = ParamUtil.getParameter(request, "oldPwd");
		String newPwd = ParamUtil.getParameter(request, "newPwd");
		if (bean != null && StringUtils.isNotEmpty(oldPwd) && StringUtils.isNotEmpty(newPwd)) {
			try {
				SysUser user = sysUserService.findById(bean.getActorId());
				if (sysUserService.checkPassword(user.getAccount(), oldPwd)) {
					sysUserService.changePassword(user.getAccount(), newPwd,1);
					ret = true;
				}
			} catch (Exception ex) {
				ret = false;
			}
		}
		JSONObject result = new JSONObject();
		if(ret){
			result.put("statusCode", 200);
			result.put("msg", "修改成功");
		}else{
			result.put("statusCode", 400);
			result.put("msg", "修改失败");
		}
		return result.toJSONString().getBytes("UTF-8");
	}

}
