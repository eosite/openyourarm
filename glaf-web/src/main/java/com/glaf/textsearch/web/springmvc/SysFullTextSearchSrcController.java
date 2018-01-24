package com.glaf.textsearch.web.springmvc;

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

import com.glaf.textsearch.domain.*;
import com.glaf.textsearch.query.*;
import com.glaf.textsearch.service.*;
import com.glaf.textsearch.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/apps/sysFullTextSearchSrc")
@RequestMapping("/apps/sysFullTextSearchSrc")
public class SysFullTextSearchSrcController {
	protected static final Log logger = LogFactory.getLog(SysFullTextSearchSrcController.class);

	protected SysFullTextSearchSrcService sysFullTextSearchSrcService;

	public SysFullTextSearchSrcController() {

	}

	@javax.annotation.Resource(name = "com.glaf.textsearch.service.sysFullTextSearchSrcService")
	public void setSysFullTextSearchSrcService(SysFullTextSearchSrcService sysFullTextSearchSrcService) {
		this.sysFullTextSearchSrcService = sysFullTextSearchSrcService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysFullTextSearchSrc sysFullTextSearchSrc = new SysFullTextSearchSrc();
		Tools.populate(sysFullTextSearchSrc, params);

		sysFullTextSearchSrc.setServiceName_(request.getParameter("serviceName_"));
		sysFullTextSearchSrc.setServiceAddress_(request.getParameter("serviceAddress_"));
		sysFullTextSearchSrc.setFullTextServer_(request.getParameter("fullTextServer_"));
		sysFullTextSearchSrc.setIndexName_(request.getParameter("indexName_"));
		sysFullTextSearchSrc.setTypeName_(request.getParameter("typeName_"));
		sysFullTextSearchSrc.setCreateBy_(request.getParameter("createBy_"));
		sysFullTextSearchSrc.setCreateTime_(RequestUtils.getDate(request, "createTime_"));
		sysFullTextSearchSrc.setUpdateBy_(request.getParameter("updateBy_"));
		sysFullTextSearchSrc.setUpdateTime_(RequestUtils.getDate(request, "updateTime_"));
		sysFullTextSearchSrc.setDeleteFlag_(RequestUtils.getInt(request, "deleteFlag_"));

		// sysFullTextSearchSrc.setCreateBy(actorId);

		sysFullTextSearchSrcService.save(sysFullTextSearchSrc);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveSysFullTextSearchSrc")
	public byte[] saveSysFullTextSearchSrc(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysFullTextSearchSrc sysFullTextSearchSrc = new SysFullTextSearchSrc();
		try {
			Tools.populate(sysFullTextSearchSrc, params);
			sysFullTextSearchSrc.setServiceName_(request.getParameter("serviceName_"));
			sysFullTextSearchSrc.setServiceAddress_(request.getParameter("serviceAddress_"));
			sysFullTextSearchSrc.setFullTextServer_(request.getParameter("fullTextServer_"));
			sysFullTextSearchSrc.setIndexName_(request.getParameter("indexName_"));
			sysFullTextSearchSrc.setTypeName_(request.getParameter("typeName_"));
			sysFullTextSearchSrc.setCreateBy_(request.getParameter("createBy_"));
			sysFullTextSearchSrc.setCreateTime_(RequestUtils.getDate(request, "createTime_"));
			sysFullTextSearchSrc.setUpdateBy_(request.getParameter("updateBy_"));
			sysFullTextSearchSrc.setUpdateTime_(RequestUtils.getDate(request, "updateTime_"));
			sysFullTextSearchSrc.setDeleteFlag_(RequestUtils.getInt(request, "deleteFlag_"));
			// sysFullTextSearchSrc.setCreateBy(actorId);
			this.sysFullTextSearchSrcService.save(sysFullTextSearchSrc);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SysFullTextSearchSrc saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		SysFullTextSearchSrc sysFullTextSearchSrc = new SysFullTextSearchSrc();
		try {
			Tools.populate(sysFullTextSearchSrc, model);
			sysFullTextSearchSrc.setServiceName_(ParamUtils.getString(model, "serviceName_"));
			sysFullTextSearchSrc.setServiceAddress_(ParamUtils.getString(model, "serviceAddress_"));
			sysFullTextSearchSrc.setFullTextServer_(ParamUtils.getString(model, "fullTextServer_"));
			sysFullTextSearchSrc.setIndexName_(ParamUtils.getString(model, "indexName_"));
			sysFullTextSearchSrc.setTypeName_(ParamUtils.getString(model, "typeName_"));
			sysFullTextSearchSrc.setCreateBy_(ParamUtils.getString(model, "createBy_"));
			sysFullTextSearchSrc.setCreateTime_(ParamUtils.getDate(model, "createTime_"));
			sysFullTextSearchSrc.setUpdateBy_(ParamUtils.getString(model, "updateBy_"));
			sysFullTextSearchSrc.setUpdateTime_(ParamUtils.getDate(model, "updateTime_"));
			sysFullTextSearchSrc.setDeleteFlag_(ParamUtils.getInt(model, "deleteFlag_"));
			sysFullTextSearchSrc.setCreateBy_(actorId);
			this.sysFullTextSearchSrcService.save(sysFullTextSearchSrc);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return sysFullTextSearchSrc;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		SysFullTextSearchSrc sysFullTextSearchSrc = sysFullTextSearchSrcService
				.getSysFullTextSearchSrc(request.getParameter("id"));

		Tools.populate(sysFullTextSearchSrc, params);

		sysFullTextSearchSrc.setServiceName_(request.getParameter("serviceName_"));
		sysFullTextSearchSrc.setServiceAddress_(request.getParameter("serviceAddress_"));
		sysFullTextSearchSrc.setFullTextServer_(request.getParameter("fullTextServer_"));
		sysFullTextSearchSrc.setIndexName_(request.getParameter("indexName_"));
		sysFullTextSearchSrc.setTypeName_(request.getParameter("typeName_"));
		sysFullTextSearchSrc.setCreateBy_(request.getParameter("createBy_"));
		sysFullTextSearchSrc.setCreateTime_(RequestUtils.getDate(request, "createTime_"));
		sysFullTextSearchSrc.setUpdateBy_(request.getParameter("updateBy_"));
		sysFullTextSearchSrc.setUpdateTime_(RequestUtils.getDate(request, "updateTime_"));
		sysFullTextSearchSrc.setDeleteFlag_(RequestUtils.getInt(request, "deleteFlag_"));

		sysFullTextSearchSrcService.save(sysFullTextSearchSrc);

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
					SysFullTextSearchSrc sysFullTextSearchSrc = sysFullTextSearchSrcService
							.getSysFullTextSearchSrc(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (sysFullTextSearchSrc != null
							&& (StringUtils.equals(sysFullTextSearchSrc.getCreateBy_(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// sysFullTextSearchSrc.setDeleteFlag(1);
						sysFullTextSearchSrcService.save(sysFullTextSearchSrc);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SysFullTextSearchSrc sysFullTextSearchSrc = sysFullTextSearchSrcService
					.getSysFullTextSearchSrc(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (sysFullTextSearchSrc != null
					&& (StringUtils.equals(sysFullTextSearchSrc.getCreateBy_(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				// sysFullTextSearchSrc.setDeleteFlag(1);
				sysFullTextSearchSrcService.save(sysFullTextSearchSrc);
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
		SysFullTextSearchSrc sysFullTextSearchSrc = sysFullTextSearchSrcService
				.getSysFullTextSearchSrc(request.getParameter("id"));
		if (sysFullTextSearchSrc != null) {
			request.setAttribute("sysFullTextSearchSrc", sysFullTextSearchSrc);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysFullTextSearchSrc.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/textsearch/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysFullTextSearchSrc sysFullTextSearchSrc = sysFullTextSearchSrcService
				.getSysFullTextSearchSrc(request.getParameter("id"));
		request.setAttribute("sysFullTextSearchSrc", sysFullTextSearchSrc);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysFullTextSearchSrc.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/textsearch/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sysFullTextSearchSrc.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/textsearch/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysFullTextSearchSrcQuery query = new SysFullTextSearchSrcQuery();
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
		int total = sysFullTextSearchSrcService.getSysFullTextSearchSrcCountByQueryCriteria(query);
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
			List<SysFullTextSearchSrc> list = sysFullTextSearchSrcService.getSysFullTextSearchSrcsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysFullTextSearchSrc sysFullTextSearchSrc : list) {
					JSONObject rowJSON = sysFullTextSearchSrc.toJsonObject();
					rowJSON.put("id", sysFullTextSearchSrc.getId());
					rowJSON.put("rowId", sysFullTextSearchSrc.getId());
					rowJSON.put("sysFullTextSearchSrcId", sysFullTextSearchSrc.getId());
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
		SysFullTextSearchSrcQuery query = new SysFullTextSearchSrcQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysFullTextSearchSrcDomainFactory.processDataRequest(dataRequest);

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
		int total = sysFullTextSearchSrcService.getSysFullTextSearchSrcCountByQueryCriteria(query);
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
			List<SysFullTextSearchSrc> list = sysFullTextSearchSrcService.getSysFullTextSearchSrcsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysFullTextSearchSrc sysFullTextSearchSrc : list) {
					JSONObject rowJSON = sysFullTextSearchSrc.toJsonObject();
					rowJSON.put("id", sysFullTextSearchSrc.getId());
					rowJSON.put("sysFullTextSearchSrcId", sysFullTextSearchSrc.getId());
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

		return new ModelAndView("/textsearch/list", modelMap);
	}

}
