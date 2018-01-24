package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
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
import com.glaf.form.core.domain.FormTree;
import com.glaf.form.core.query.FormTreeQuery;
import com.glaf.form.core.service.FormTreeService;
import com.glaf.form.core.util.FormTreeDomainFactory;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/form/formTree")
@RequestMapping("/form/formTree")
public class FormTreeController {
	protected static final Log logger = LogFactory.getLog(FormTreeController.class);

	protected FormTreeService formTreeService;

	public FormTreeController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.formTreeService")
	public void setFormTreeService(FormTreeService formTreeService) {
		this.formTreeService = formTreeService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormTree formTree = new FormTree();
		Tools.populate(formTree, params);

		formTree.setCode(request.getParameter("code"));
		formTree.setCreateBy(request.getParameter("createBy"));
		formTree.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formTree.setNodeDesc(request.getParameter("nodeDesc"));
		formTree.setDiscriminator(request.getParameter("discriminator"));
		formTree.setIcon(request.getParameter("icon"));
		formTree.setIconCls(request.getParameter("iconCls"));
		formTree.setLocked(RequestUtils.getInt(request, "locked"));
		formTree.setMoveable(request.getParameter("moveable"));
		formTree.setName(request.getParameter("name"));
		formTree.setParent(RequestUtils.getLong(request, "parent"));
		formTree.setSort(RequestUtils.getInt(request, "sort"));
		formTree.setTreeId(request.getParameter("treeId"));
		formTree.setUpdateBy(request.getParameter("updateBy"));
		formTree.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		formTree.setUrl(request.getParameter("url"));
		formTree.setCategory(request.getParameter("category"));

		// formTree.setCreateBy(actorId);

		formTreeService.save(formTree);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFormTree")
	public byte[] saveFormTree(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTree formTree = new FormTree();
		try {
			Tools.populate(formTree, params);
			formTree.setCode(request.getParameter("code"));
			formTree.setCreateBy(request.getParameter("createBy"));
			formTree.setCreateDate(RequestUtils.getDate(request, "createDate"));
			formTree.setNodeDesc(request.getParameter("nodeDesc"));
			formTree.setDiscriminator(request.getParameter("discriminator"));
			formTree.setIcon(request.getParameter("icon"));
			formTree.setIconCls(request.getParameter("iconCls"));
			formTree.setLocked(RequestUtils.getInt(request, "locked"));
			formTree.setMoveable(request.getParameter("moveable"));
			formTree.setName(request.getParameter("name"));
			formTree.setParent(RequestUtils.getLong(request, "parent"));
			formTree.setSort(RequestUtils.getInt(request, "sort"));
			formTree.setTreeId(request.getParameter("treeId"));
			formTree.setUpdateBy(request.getParameter("updateBy"));
			formTree.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			formTree.setUrl(request.getParameter("url"));
			formTree.setCategory(request.getParameter("category"));
			// formTree.setCreateBy(actorId);
			this.formTreeService.save(formTree);

			return formTree.toJsonObject().toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FormTree saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FormTree formTree = new FormTree();
		try {
			Tools.populate(formTree, model);
			formTree.setCode(ParamUtils.getString(model, "code"));
			formTree.setCreateBy(ParamUtils.getString(model, "createBy"));
			formTree.setCreateDate(ParamUtils.getDate(model, "createDate"));
			formTree.setNodeDesc(ParamUtils.getString(model, "nodeDesc"));
			formTree.setDiscriminator(ParamUtils.getString(model, "discriminator"));
			formTree.setIcon(ParamUtils.getString(model, "icon"));
			formTree.setIconCls(ParamUtils.getString(model, "iconCls"));
			formTree.setLocked(ParamUtils.getInt(model, "locked"));
			formTree.setMoveable(ParamUtils.getString(model, "moveable"));
			formTree.setName(ParamUtils.getString(model, "name"));
			formTree.setParent(ParamUtils.getLong(model, "parent"));
			formTree.setSort(ParamUtils.getInt(model, "sort"));
			formTree.setTreeId(ParamUtils.getString(model, "treeId"));
			formTree.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			formTree.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			formTree.setUrl(ParamUtils.getString(model, "url"));
			formTree.setCategory(ParamUtils.getString(model, "category"));
			formTree.setCreateBy(actorId);
			this.formTreeService.save(formTree);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return formTree;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FormTree formTree = formTreeService.getFormTree(RequestUtils.getLong(request, "id"));

		Tools.populate(formTree, params);

		formTree.setCode(request.getParameter("code"));
		formTree.setCreateBy(request.getParameter("createBy"));
		formTree.setCreateDate(RequestUtils.getDate(request, "createDate"));
		formTree.setNodeDesc(request.getParameter("nodeDesc"));
		formTree.setDiscriminator(request.getParameter("discriminator"));
		formTree.setIcon(request.getParameter("icon"));
		formTree.setIconCls(request.getParameter("iconCls"));
		formTree.setLocked(RequestUtils.getInt(request, "locked"));
		formTree.setMoveable(request.getParameter("moveable"));
		formTree.setName(request.getParameter("name"));
		formTree.setParent(RequestUtils.getLong(request, "parent"));
		formTree.setSort(RequestUtils.getInt(request, "sort"));
		formTree.setTreeId(request.getParameter("treeId"));
		formTree.setUpdateBy(request.getParameter("updateBy"));
		formTree.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		formTree.setUrl(request.getParameter("url"));
		formTree.setCategory(request.getParameter("category"));

		formTreeService.save(formTree);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FormTree formTree = formTreeService.getFormTree(Long.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (formTree != null && (StringUtils.equals(formTree.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						// formTree.setDeleteFlag(1);
						formTreeService.save(formTree);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FormTree formTree = formTreeService.getFormTree(Long.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (formTree != null && (StringUtils.equals(formTree.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// formTree.setDeleteFlag(1);
				// formTreeService.save(formTree);
				formTreeService.deleteById(formTree.getId());
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
		FormTree formTree = formTreeService.getFormTree(RequestUtils.getLong(request, "id"));
		if (formTree != null) {
			request.setAttribute("formTree", formTree);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formTree.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/apps/formTree/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTree formTree = formTreeService.getFormTree(RequestUtils.getLong(request, "id"));
		request.setAttribute("formTree", formTree);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("formTree.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/apps/formTree/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("formTree.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/apps/formTree/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormTreeQuery query = new FormTreeQuery();
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
		int total = formTreeService.getFormTreeCountByQueryCriteria(query);
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
			List<FormTree> list = formTreeService.getFormTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormTree formTree : list) {
					JSONObject rowJSON = formTree.toJsonObject();
					rowJSON.put("id", formTree.getId());
					rowJSON.put("rowId", formTree.getId());
					rowJSON.put("formTreeId", formTree.getId());
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
		FormTreeQuery query = new FormTreeQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FormTreeDomainFactory.processDataRequest(dataRequest);

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
		int total = formTreeService.getFormTreeCountByQueryCriteria(query);
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
			List<FormTree> list = formTreeService.getFormTreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormTree formTree : list) {
					JSONObject rowJSON = formTree.toJsonObject();
					rowJSON.put("id", formTree.getId());
					rowJSON.put("formTreeId", formTree.getId());
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

		return new ModelAndView("/apps/formTree/list", modelMap);
	}

	@RequestMapping("/getTreeByCode")
	@ResponseBody
	public byte[] getTreeByCode(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		Map<String, Object> params = RequestUtils.getParameterMap(request);

		JSONObject result = new JSONObject();

		String code = MapUtils.getString(params, "code");

		Object rows = this.formTreeService.getTreeByCode(code, MapUtils.getBoolean(params, "is", true));

		result.put("rows", rows);

		return result.toJSONString().getBytes("UTF-8");
	}
}
