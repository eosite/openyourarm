package com.glaf.datamgr.web.springmvc;

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

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;
import com.glaf.datamgr.sqlparser.TranlateFactory;
import com.glaf.datamgr.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/systemDBFunc")
@RequestMapping("/datamgr/systemDBFunc")
public class SystemDBFuncController {
	protected static final Log logger = LogFactory.getLog(SystemDBFuncController.class);

	protected SystemDBFuncService systemDBFuncService;

	public SystemDBFuncController() {

	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.systemDBFuncService")
	public void setSystemDBFuncService(SystemDBFuncService systemDBFuncService) {
		this.systemDBFuncService = systemDBFuncService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SystemDBFunc systemDBFunc = new SystemDBFunc();
		Tools.populate(systemDBFunc, params);

		systemDBFunc.setCode(request.getParameter("code"));
		systemDBFunc.setName(request.getParameter("name"));
		systemDBFunc.setPublicParams(request.getParameter("publicParams"));
		systemDBFunc.setDesc(request.getParameter("desc"));
		systemDBFunc.setCreateBy(request.getParameter("createBy"));
		systemDBFunc.setCreateTime(RequestUtils.getDate(request, "createTime"));
		systemDBFunc.setUpdateBy(request.getParameter("updateBy"));
		systemDBFunc.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		systemDBFunc.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		// systemDBFunc.setCreateBy(actorId);

		systemDBFuncService.save(systemDBFunc);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveSystemDBFunc")
	public byte[] saveSystemDBFunc(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDBFunc systemDBFunc = new SystemDBFunc();
		try {
			Tools.populate(systemDBFunc, params);
			systemDBFunc.setCode(request.getParameter("code"));
			systemDBFunc.setName(request.getParameter("name"));
			systemDBFunc.setPublicParams(request.getParameter("publicParams"));
			systemDBFunc.setDesc(request.getParameter("desc"));
			systemDBFunc.setCreateBy(request.getParameter("createBy"));
			systemDBFunc.setCreateTime(RequestUtils.getDate(request, "createTime"));
			systemDBFunc.setUpdateBy(request.getParameter("updateBy"));
			systemDBFunc.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
			systemDBFunc.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			// systemDBFunc.setCreateBy(actorId);
			this.systemDBFuncService.save(systemDBFunc);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SystemDBFunc saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SystemDBFunc systemDBFunc = new SystemDBFunc();
		try {
			Tools.populate(systemDBFunc, model);
			systemDBFunc.setCode(ParamUtils.getString(model, "code"));
			systemDBFunc.setName(ParamUtils.getString(model, "name"));
			systemDBFunc.setPublicParams(ParamUtils.getString(model, "publicParams"));
			systemDBFunc.setDesc(ParamUtils.getString(model, "desc"));
			systemDBFunc.setCreateBy(ParamUtils.getString(model, "createBy"));
			systemDBFunc.setCreateTime(ParamUtils.getDate(model, "createTime"));
			systemDBFunc.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			systemDBFunc.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
			systemDBFunc.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			systemDBFunc.setCreateBy(actorId);
			this.systemDBFuncService.save(systemDBFunc);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return systemDBFunc;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SystemDBFunc systemDBFunc = systemDBFuncService.getSystemDBFunc(request.getParameter("id"));

		Tools.populate(systemDBFunc, params);

		systemDBFunc.setCode(request.getParameter("code"));
		systemDBFunc.setName(request.getParameter("name"));
		systemDBFunc.setPublicParams(request.getParameter("publicParams"));
		systemDBFunc.setDesc(request.getParameter("desc"));
		systemDBFunc.setCreateBy(request.getParameter("createBy"));
		systemDBFunc.setCreateTime(RequestUtils.getDate(request, "createTime"));
		systemDBFunc.setUpdateBy(request.getParameter("updateBy"));
		systemDBFunc.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		systemDBFunc.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		systemDBFuncService.save(systemDBFunc);

		return this.list(request, modelMap);
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
					SystemDBFunc systemDBFunc = systemDBFuncService.getSystemDBFunc(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (systemDBFunc != null
							&& (StringUtils.equals(systemDBFunc.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// systemDBFunc.setDeleteFlag(1);
						systemDBFuncService.save(systemDBFunc);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SystemDBFunc systemDBFunc = systemDBFuncService.getSystemDBFunc(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (systemDBFunc != null && (StringUtils.equals(systemDBFunc.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// systemDBFunc.setDeleteFlag(1);
				systemDBFuncService.save(systemDBFunc);
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
		SystemDBFunc systemDBFunc = systemDBFuncService.getSystemDBFunc(request.getParameter("id"));
		if (systemDBFunc != null) {
			request.setAttribute("systemDBFunc", systemDBFunc);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("systemDBFunc.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/systemDBFunc/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDBFunc systemDBFunc = systemDBFuncService.getSystemDBFunc(request.getParameter("id"));
		request.setAttribute("systemDBFunc", systemDBFunc);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("systemDBFunc.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/systemDBFunc/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("systemDBFunc.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/systemDBFunc/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDBFuncQuery query = new SystemDBFuncQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = systemDBFuncService.getSystemDBFuncCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SystemDBFunc> list = systemDBFuncService.getSystemDBFuncsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SystemDBFunc systemDBFunc : list) {
					JSONObject rowJSON = systemDBFunc.toJsonObject();
					rowJSON.put("id", systemDBFunc.getId());
					rowJSON.put("rowId", systemDBFunc.getId());
					rowJSON.put("systemDBFuncId", systemDBFunc.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SystemDBFuncQuery query = new SystemDBFuncQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SystemDBFuncDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = systemDBFuncService.getSystemDBFuncCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<SystemDBFunc> list = systemDBFuncService.getSystemDBFuncsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SystemDBFunc systemDBFunc : list) {
					JSONObject rowJSON = systemDBFunc.toJsonObject();
					rowJSON.put("id", systemDBFunc.getId());
					rowJSON.put("systemDBFuncId", systemDBFunc.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
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

		return new ModelAndView("/datamgr/systemDBFunc/list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/reload")
	public byte[] reload(HttpServletRequest request, ModelMap modelMap) {
		return ResponseUtils.responseResult(TranlateFactory.reload());
	}

}
