package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormEvent;
import com.glaf.form.core.query.FormEventQuery;
import com.glaf.form.core.service.FormEventService;
import com.glaf.form.core.util.FormEventDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/form/formEvent")
@RequestMapping("/form/formEvent")
public class FormEventController {
	protected static final Log logger = LogFactory.getLog(FormEventController.class);

	protected FormEventService formEventService;

	public FormEventController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formEventService")
	public void setFormEventService(FormEventService formEventService) {
		this.formEventService = formEventService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormEvent formEvent = new FormEvent();
		Tools.populate(formEvent, params);

		formEvent.setDiagram(request.getParameter("diagram"));
		formEvent.setRule(request.getParameter("rule"));
		formEvent.setPageId(request.getParameter("pageId"));
		formEvent.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formEvent.setCreateBy(request.getParameter("createBy"));
		formEvent.setUpdateBy(request.getParameter("updateBy"));
		formEvent.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		// formEvent.setCreateBy(actorId);

		formEventService.save(formEvent);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormEvent")
	public byte[] saveFormEvent(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEvent formEvent = new FormEvent();
		try {
			Tools.populate(formEvent, params);
			formEvent.setDiagram(request.getParameter("diagram"));
			formEvent.setRule(request.getParameter("rule"));
			formEvent.setPageId(request.getParameter("pageId"));
			formEvent.setCreateDate(new Date());
			formEvent.setCreateBy(actorId);
			formEvent.setVersion(request.getParameter("version"));
			formEvent.setEleId(request.getParameter("eleId"));
			if(StringUtils.isNotEmpty(request.getParameter("id"))){
				formEvent.setUpdateBy(actorId);
				formEvent.setUpdateDate(new Date());
			}
			// formEvent.setCreateBy(actorId);
			this.formEventService.save(formEvent);
			return JSON.toJSONString(formEvent).getBytes("UTF-8");
			//return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormEvent saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormEvent formEvent = new FormEvent();
		try {
			Tools.populate(formEvent, model);
			formEvent.setDiagram(ParamUtils.getString(model, "diagram"));
			formEvent.setRule(ParamUtils.getString(model, "rule"));
			formEvent.setPageId(ParamUtils.getString(model, "pageId"));
			formEvent.setCreateDate(ParamUtils.getDate(model, "createDate"));
			formEvent.setCreateBy(ParamUtils.getString(model, "createBy"));
			formEvent.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			formEvent.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			formEvent.setCreateBy(actorId);
			this.formEventService.save(formEvent);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formEvent;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormEvent formEvent = formEventService.getFormEvent(request.getParameter("id"));

		Tools.populate(formEvent, params);

		formEvent.setDiagram(request.getParameter("diagram"));
		formEvent.setRule(request.getParameter("rule"));
		formEvent.setPageId(request.getParameter("pageId"));
		formEvent.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formEvent.setCreateBy(request.getParameter("createBy"));
		formEvent.setUpdateBy(request.getParameter("updateBy"));
		formEvent.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		formEventService.save(formEvent);

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
					FormEvent formEvent = formEventService.getFormEvent(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (formEvent != null && (StringUtils.equals(formEvent.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						// formEvent.setDeleteFlag(1);
						formEventService.save(formEvent);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormEvent formEvent = formEventService.getFormEvent(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (formEvent != null && (StringUtils.equals(formEvent.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				// formEvent.setDeleteFlag(1);
				formEventService.save(formEvent);
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
		FormEvent formEvent = formEventService.getFormEvent(request.getParameter("id"));
		if (formEvent != null) {
			request.setAttribute("formEvent", formEvent);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formEvent.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/formEvent/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEvent formEvent = formEventService.getFormEvent(request.getParameter("id"));
		request.setAttribute("formEvent", formEvent);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formEvent.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/formEvent/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formEvent.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/formEvent/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventQuery query = new FormEventQuery();
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
		int total = formEventService.getFormEventCountByQueryCriteria(query);
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
			List<FormEvent> list = formEventService.getFormEventsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormEvent formEvent : list) {
					JSONObject rowJSON = formEvent.toJsonObject();
					rowJSON.put("id", formEvent.getId());
					rowJSON.put("rowId", formEvent.getId());
					rowJSON.put("formEventId", formEvent.getId());
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
		FormEventQuery query = new FormEventQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormEventDomainFactory.processDataRequest(dataRequest);

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
		int total = formEventService.getFormEventCountByQueryCriteria(query);
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
			List<FormEvent> list = formEventService.getFormEventsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormEvent formEvent : list) {
					JSONObject rowJSON = formEvent.toJsonObject();
					rowJSON.put("id", formEvent.getId());
					rowJSON.put("formEventId", formEvent.getId());
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

		return new ModelAndView("/apps/formEvent/list", modelMap);
	}
	
	
	@RequestMapping("/getEventByPageId")
	@ResponseBody
	public byte[] getEventByPageId(HttpServletRequest request, ModelMap modelMap) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormEventQuery query = new FormEventQuery();
		Tools.populate(query, params);

		JSONObject result = new JSONObject();
		List<FormEvent> list  = formEventService.list(query);
		if(list!=null && !list.isEmpty()){
			result = JSON.parseObject(JSON.toJSONString(list.get(0)));
		}
		return result.toJSONString().getBytes("UTF-8");
	}

}
