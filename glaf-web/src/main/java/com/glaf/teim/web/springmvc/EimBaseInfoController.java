package com.glaf.teim.web.springmvc;

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

import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;
import com.glaf.teim.service.*;
import com.glaf.teim.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/teim/base")
@RequestMapping("/teim/base")
public class EimBaseInfoController {
	protected static final Log logger = LogFactory.getLog(EimBaseInfoController.class);

	protected EimBaseInfoService eimBaseInfoService;

	public EimBaseInfoController() {

	}

	@javax.annotation.Resource(name = "com.glaf.teim.service.eimBaseInfoService")
	public void setEimBaseInfoService(EimBaseInfoService eimBaseInfoService) {
		this.eimBaseInfoService = eimBaseInfoService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		EimBaseInfo eimBaseInfo = new EimBaseInfo();
		Tools.populate(eimBaseInfo, params);

		eimBaseInfo.setIp(request.getParameter("ip"));
		eimBaseInfo.setHost(request.getParameter("host"));
		eimBaseInfo.setSecret(request.getParameter("secret"));
		eimBaseInfo.setPaasId(request.getParameter("paasId"));
		eimBaseInfo.setCreateBy(request.getParameter("createBy"));
		eimBaseInfo.setCreateTime(RequestUtils.getDate(request, "createTime"));
		eimBaseInfo.setUpdateBy(request.getParameter("updateBy"));
		eimBaseInfo.setUpdateTime(RequestUtils.getDate(request, "updateTime"));

		// eimBaseInfo.setCreateBy(actorId);

		eimBaseInfoService.save(eimBaseInfo);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveEimBaseInfo")
	public byte[] saveEimBaseInfo(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimBaseInfo eimBaseInfo = null;
		try {
			String id=request.getParameter("id");
			if (StringUtils.isEmpty(id)) {
				eimBaseInfo = new EimBaseInfo();
				eimBaseInfo.setCreateBy(actorId);
				eimBaseInfo.setCreateTime(new Date());
			} else {
				eimBaseInfo=eimBaseInfoService.getEimBaseInfo(id);
				eimBaseInfo.setUpdateBy(actorId);
				eimBaseInfo.setUpdateTime(new Date());
			}
			Tools.populate(eimBaseInfo, params);
			eimBaseInfo.setName(request.getParameter("name"));
			eimBaseInfo.setIp(request.getParameter("ip"));
			eimBaseInfo.setHost(request.getParameter("host"));
			eimBaseInfo.setSecret(request.getParameter("secret"));
			eimBaseInfo.setPaasId(request.getParameter("paasId"));
			eimBaseInfo.setDeleteFlag(RequestUtils.getInteger(request,"deleteFlag"));
			this.eimBaseInfoService.save(eimBaseInfo);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody EimBaseInfo saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		EimBaseInfo eimBaseInfo = new EimBaseInfo();
		try {
			Tools.populate(eimBaseInfo, model);
			eimBaseInfo.setName(ParamUtils.getString(model,"name"));
			eimBaseInfo.setIp(ParamUtils.getString(model, "ip"));
			eimBaseInfo.setHost(ParamUtils.getString(model, "host"));
			eimBaseInfo.setSecret(ParamUtils.getString(model, "secret"));
			eimBaseInfo.setPaasId(ParamUtils.getString(model, "paasId"));
			eimBaseInfo.setCreateBy(ParamUtils.getString(model, "createBy"));
			eimBaseInfo.setCreateTime(ParamUtils.getDate(model, "createTime"));
			eimBaseInfo.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			eimBaseInfo.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
			eimBaseInfo.setCreateBy(actorId);
			this.eimBaseInfoService.save(eimBaseInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return eimBaseInfo;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		EimBaseInfo eimBaseInfo = eimBaseInfoService.getEimBaseInfo(request.getParameter("id"));

		Tools.populate(eimBaseInfo, params);
		eimBaseInfo.setName(request.getParameter("name"));
		eimBaseInfo.setIp(request.getParameter("ip"));
		eimBaseInfo.setHost(request.getParameter("host"));
		eimBaseInfo.setSecret(request.getParameter("secret"));
		eimBaseInfo.setPaasId(request.getParameter("paasId"));
		eimBaseInfo.setCreateBy(request.getParameter("createBy"));
		eimBaseInfo.setCreateTime(RequestUtils.getDate(request, "createTime"));
		eimBaseInfo.setUpdateBy(request.getParameter("updateBy"));
		eimBaseInfo.setUpdateTime(RequestUtils.getDate(request, "updateTime"));

		eimBaseInfoService.save(eimBaseInfo);

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
					EimBaseInfo eimBaseInfo = eimBaseInfoService.getEimBaseInfo(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (eimBaseInfo != null && (StringUtils.equals(eimBaseInfo.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						// eimBaseInfo.setDeleteFlag(1);
						eimBaseInfoService.save(eimBaseInfo);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			EimBaseInfo eimBaseInfo = eimBaseInfoService.getEimBaseInfo(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (eimBaseInfo != null && (StringUtils.equals(eimBaseInfo.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// eimBaseInfo.setDeleteFlag(1);
				eimBaseInfoService.save(eimBaseInfo);
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
		EimBaseInfo eimBaseInfo = eimBaseInfoService.getEimBaseInfo(request.getParameter("id"));
		if (eimBaseInfo != null) {
			request.setAttribute("eimBaseInfo", eimBaseInfo);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("eimBaseInfo.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/teim/base/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimBaseInfo eimBaseInfo = eimBaseInfoService.getEimBaseInfo(request.getParameter("id"));
		request.setAttribute("eimBaseInfo", eimBaseInfo);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("eimBaseInfo.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/teim/base/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("eimBaseInfo.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/teim/base/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimBaseInfoQuery query = new EimBaseInfoQuery();
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
		int total = eimBaseInfoService.getEimBaseInfoCountByQueryCriteria(query);
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
			List<EimBaseInfo> list = eimBaseInfoService.getEimBaseInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EimBaseInfo eimBaseInfo : list) {
					JSONObject rowJSON = eimBaseInfo.toJsonObject();
					rowJSON.put("id", eimBaseInfo.getId());
					rowJSON.put("rowId", eimBaseInfo.getId());
					rowJSON.put("eimBaseInfoId", eimBaseInfo.getId());
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
		EimBaseInfoQuery query = new EimBaseInfoQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		EimBaseInfoDomainFactory.processDataRequest(dataRequest);

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
		int total = eimBaseInfoService.getEimBaseInfoCountByQueryCriteria(query);
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
			List<EimBaseInfo> list = eimBaseInfoService.getEimBaseInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EimBaseInfo eimBaseInfo : list) {
					JSONObject rowJSON = eimBaseInfo.toJsonObject();
					rowJSON.put("id", eimBaseInfo.getId());
					rowJSON.put("eimBaseInfoId", eimBaseInfo.getId());
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

		return new ModelAndView("/teim/base/list", modelMap);
	}

}
