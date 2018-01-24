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

@Controller("/form/formObjectIns")
@RequestMapping("/form/formObjectIns")
public class FormObjectInsController {
	protected static final Log logger = LogFactory.getLog(FormObjectInsController.class);

	protected FormObjectInsService formObjectInsService;

	public FormObjectInsController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formObjectInsService")
	public void setFormObjectInsService(FormObjectInsService formObjectInsService) {
		this.formObjectInsService = formObjectInsService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormObjectIns formObjectIns = new FormObjectIns();
		Tools.populate(formObjectIns, params);

		formObjectIns.setName(request.getParameter("name"));
		formObjectIns.setCode(request.getParameter("code"));
		formObjectIns.setDesc(request.getParameter("desc"));
		formObjectIns.setUrl(request.getParameter("url"));
		formObjectIns.setStatus(RequestUtils.getInt(request, "status"));
		formObjectIns.setParent_id(request.getParameter("parent_id"));
		formObjectIns.setCreateBy(request.getParameter("createBy"));
		formObjectIns.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formObjectIns.setUpdateBy(request.getParameter("updateBy"));
		formObjectIns.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		// formObjectIns.setCreateBy(actorId);

		formObjectInsService.save(formObjectIns);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormObjectIns")
	public byte[] saveFormObjectIns(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormObjectIns formObjectIns = new FormObjectIns();
		try {
			Tools.populate(formObjectIns, params);
			formObjectIns.setName(request.getParameter("name"));
			formObjectIns.setCode(request.getParameter("code"));
			formObjectIns.setDesc(request.getParameter("desc"));
			formObjectIns.setUrl(request.getParameter("url"));
			formObjectIns.setStatus(RequestUtils.getInt(request, "status"));
			formObjectIns.setParent_id(request.getParameter("parent_id"));
			formObjectIns.setCreateBy(request.getParameter("createBy"));
			formObjectIns.setCreateDate(RequestUtils.getDate(request, "createDate"));
			formObjectIns.setUpdateBy(request.getParameter("updateBy"));
			formObjectIns.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			// formObjectIns.setCreateBy(actorId);
			this.formObjectInsService.save(formObjectIns);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormObjectIns saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormObjectIns formObjectIns = new FormObjectIns();
		try {
			Tools.populate(formObjectIns, model);
			formObjectIns.setName(ParamUtils.getString(model, "name"));
			formObjectIns.setCode(ParamUtils.getString(model, "code"));
			formObjectIns.setDesc(ParamUtils.getString(model, "desc"));
			formObjectIns.setUrl(ParamUtils.getString(model, "url"));
			formObjectIns.setStatus(ParamUtils.getInt(model, "status"));
			formObjectIns.setParent_id(ParamUtils.getString(model, "parent_id"));
			formObjectIns.setCreateBy(ParamUtils.getString(model, "createBy"));
			formObjectIns.setCreateDate(ParamUtils.getDate(model, "createDate"));
			formObjectIns.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			formObjectIns.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			formObjectIns.setCreateBy(actorId);
			this.formObjectInsService.save(formObjectIns);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formObjectIns;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormObjectIns formObjectIns = formObjectInsService.getFormObjectIns(request.getParameter("id"));

		Tools.populate(formObjectIns, params);

		formObjectIns.setName(request.getParameter("name"));
		formObjectIns.setCode(request.getParameter("code"));
		formObjectIns.setDesc(request.getParameter("desc"));
		formObjectIns.setUrl(request.getParameter("url"));
		formObjectIns.setStatus(RequestUtils.getInt(request, "status"));
		formObjectIns.setParent_id(request.getParameter("parent_id"));
		formObjectIns.setCreateBy(request.getParameter("createBy"));
		formObjectIns.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formObjectIns.setUpdateBy(request.getParameter("updateBy"));
		formObjectIns.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		formObjectInsService.save(formObjectIns);

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
					FormObjectIns formObjectIns = formObjectInsService.getFormObjectIns(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (formObjectIns != null
							&& (StringUtils.equals(formObjectIns.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// formObjectIns.setDeleteFlag(1);
						formObjectInsService.save(formObjectIns);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormObjectIns formObjectIns = formObjectInsService.getFormObjectIns(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (formObjectIns != null && (StringUtils.equals(formObjectIns.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// formObjectIns.setDeleteFlag(1);
				formObjectInsService.save(formObjectIns);
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
		FormObjectIns formObjectIns = formObjectInsService.getFormObjectIns(request.getParameter("id"));
		if (formObjectIns != null) {
			request.setAttribute("formObjectIns", formObjectIns);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formObjectIns.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/formObjectIns/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormObjectIns formObjectIns = formObjectInsService.getFormObjectIns(request.getParameter("id"));
		request.setAttribute("formObjectIns", formObjectIns);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formObjectIns.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/form/formObjectIns/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formObjectIns.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/form/formObjectIns/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormObjectInsQuery query = new FormObjectInsQuery();
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
		int total = formObjectInsService.getFormObjectInsCountByQueryCriteria(query);
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
			List<FormObjectIns> list = formObjectInsService.getFormObjectInssByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormObjectIns formObjectIns : list) {
					JSONObject rowJSON = formObjectIns.toJsonObject();
					rowJSON.put("id", formObjectIns.getId());
					rowJSON.put("rowId", formObjectIns.getId());
					rowJSON.put("formObjectInsId", formObjectIns.getId());
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
		FormObjectInsQuery query = new FormObjectInsQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormObjectInsDomainFactory.processDataRequest(dataRequest);

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
		int total = formObjectInsService.getFormObjectInsCountByQueryCriteria(query);
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
			List<FormObjectIns> list = formObjectInsService.getFormObjectInssByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormObjectIns formObjectIns : list) {
					JSONObject rowJSON = formObjectIns.toJsonObject();
					rowJSON.put("id", formObjectIns.getId());
					rowJSON.put("formObjectInsId", formObjectIns.getId());
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

		return new ModelAndView("/form/formObjectIns/list", modelMap);
	}

}
