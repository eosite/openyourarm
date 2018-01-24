package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.*;
import com.glaf.form.core.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/wdatasetSqllite")
@RequestMapping("/form/wdatasetSqllite")
public class WdatasetSqlliteController {
	protected static final Log logger = LogFactory.getLog(WdatasetSqlliteController.class);

	protected WdatasetSqlliteService wdatasetSqlliteService;

	public WdatasetSqlliteController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.wdatasetSqlliteService")
	public void setWdatasetSqlliteService(WdatasetSqlliteService wdatasetSqlliteService) {
		this.wdatasetSqlliteService = wdatasetSqlliteService;
	}

	@ResponseBody
	@RequestMapping("/saveWdatasetSqllite")
	public byte[] saveWdatasetSqllite(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		WdatasetSqllite wdatasetSqllite = new WdatasetSqllite();
		try {
			Tools.populate(wdatasetSqllite, params);
			Date nowDate = new Date();
			if (wdatasetSqllite.getId() != null && wdatasetSqllite.getId().longValue() != 0) {
				// 新增
				wdatasetSqllite.setUpdateBy(actorId);
				wdatasetSqllite.setUpdateDate(nowDate);
			} else {
				// 修改
				wdatasetSqllite.setCreateBy(actorId);
				wdatasetSqllite.setCreateDate(nowDate);
				wdatasetSqllite.setDelflag("0");
			}

			this.wdatasetSqlliteService.save(wdatasetSqllite);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					WdatasetSqllite wdatasetSqllite = wdatasetSqlliteService.getWdatasetSqllite(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (wdatasetSqllite != null
							&& (StringUtils.equals(wdatasetSqllite.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// wdatasetSqllite.setDeleteFlag(1);
						wdatasetSqlliteService.save(wdatasetSqllite);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			WdatasetSqllite wdatasetSqllite = wdatasetSqlliteService.getWdatasetSqllite(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (wdatasetSqllite != null && (StringUtils.equals(wdatasetSqllite.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				 wdatasetSqllite.setDelflag("1");
				wdatasetSqlliteService.save(wdatasetSqllite);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		WdatasetSqllite wdatasetSqllite = wdatasetSqlliteService.getWdatasetSqllite(request.getParameter("id"));
		if (wdatasetSqllite != null) {
			request.setAttribute("wdatasetSqllite", wdatasetSqllite);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("wdatasetSqllite.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/defined/sqllite/edit", modelMap);
	}

	@RequestMapping("/getById")
	@ResponseBody
	public byte[] getById(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		WdatasetSqllite wdatasetSqllite = wdatasetSqlliteService.getWdatasetSqllite(request.getParameter("id"));
		
		return wdatasetSqllite.toJsonObject().toJSONString().getBytes("UTF-8");
	}
	
	@RequestMapping("/getByCode")
	@ResponseBody
	public byte[] getByCode(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		WdatasetSqlliteQuery query = new WdatasetSqlliteQuery();
		Tools.populate(query, params);
		List<WdatasetSqllite> list = wdatasetSqlliteService.list(query);
		if (list != null && !list.isEmpty()) {
			WdatasetSqllite wdatasetSqllite = list.get(0);
			return wdatasetSqllite.toJsonObject().toJSONString().getBytes("UTF-8");
		}
		JSONObject result = new JSONObject();
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/form/defined/sqllite/sqlliteRule");
		// return new ModelAndView("/etl/wdatasetSqllite/list", modelMap);
	}

}
