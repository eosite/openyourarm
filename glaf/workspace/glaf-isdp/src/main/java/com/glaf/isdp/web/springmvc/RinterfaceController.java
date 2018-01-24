package com.glaf.isdp.web.springmvc;

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

import com.glaf.isdp.domain.*;
import com.glaf.isdp.query.*;
import com.glaf.isdp.service.*;
import com.glaf.isdp.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/isdp/rinterface")
@RequestMapping("/isdp/rinterface")
public class RinterfaceController {
	protected static final Log logger = LogFactory.getLog(RinterfaceController.class);

	protected RinterfaceService rinterfaceService;

	public RinterfaceController() {

	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.rinterfaceService")
	public void setRinterfaceService(RinterfaceService rinterfaceService) {
		this.rinterfaceService = rinterfaceService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Rinterface rinterface = new Rinterface();
		Tools.populate(rinterface, params);

		rinterface.setIndexId(request.getParameter("indexId"));
		rinterface.setFrmtype(request.getParameter("frmtype"));
		rinterface.setListId(request.getParameter("listId"));
		rinterface.setIssystem(request.getParameter("issystem"));
		rinterface.setFname(request.getParameter("fname"));
		rinterface.setDname(request.getParameter("dname"));
		rinterface.setDtype(request.getParameter("dtype"));
		rinterface.setShowtype(request.getParameter("showtype"));
		rinterface.setStrlen(RequestUtils.getInt(request, "strlen"));
		rinterface.setForm(request.getParameter("form"));
		rinterface.setIntype(request.getParameter("intype"));
		rinterface.setHintID(request.getParameter("hintID"));
		rinterface.setListno(RequestUtils.getInt(request, "listno"));
		rinterface.setZtype(request.getParameter("ztype"));
		rinterface.setIsmustfill(request.getParameter("ismustfill"));
		rinterface.setIsListShow(request.getParameter("isListShow"));
		rinterface.setListweigth(RequestUtils.getInt(request, "listweigth"));
		rinterface.setIsallwidth(request.getParameter("isallwidth"));
		rinterface.setIstname(request.getParameter("istname"));
		rinterface.setImportType(RequestUtils.getInt(request, "importType"));
		rinterface.setDatapoint(RequestUtils.getInt(request, "datapoint"));
		rinterface.setCreateDate(RequestUtils.getDate(request, "createDate"));
		rinterface.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		rinterface.setCreateBy(request.getParameter("createBy"));
		rinterface.setUpdateBy(request.getParameter("updateBy"));
		rinterface.setIsPrimaryKey(request.getParameter("isPrimaryKey"));
		rinterface.setIsGroupBy(request.getParameter("isGroupBy"));

		rinterface.setCreateBy(actorId);

		rinterfaceService.save(rinterface);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveRinterface")
	public byte[] saveRinterface(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Rinterface rinterface = new Rinterface();
		try {
			Tools.populate(rinterface, params);
			rinterface.setIndexId(request.getParameter("indexId"));
			rinterface.setFrmtype(request.getParameter("frmtype"));
			rinterface.setListId(request.getParameter("listId"));
			rinterface.setIssystem(request.getParameter("issystem"));
			rinterface.setFname(request.getParameter("fname"));
			rinterface.setDname(request.getParameter("dname"));
			rinterface.setDtype(request.getParameter("dtype"));
			rinterface.setShowtype(request.getParameter("showtype"));
			rinterface.setStrlen(RequestUtils.getInt(request, "strlen"));
			rinterface.setForm(request.getParameter("form"));
			rinterface.setIntype(request.getParameter("intype"));
			rinterface.setHintID(request.getParameter("hintID"));
			rinterface.setListno(RequestUtils.getInt(request, "listno"));
			rinterface.setZtype(request.getParameter("ztype"));
			rinterface.setIsmustfill(request.getParameter("ismustfill"));
			rinterface.setIsListShow(request.getParameter("isListShow"));
			rinterface.setListweigth(RequestUtils.getInt(request, "listweigth"));
			rinterface.setIsallwidth(request.getParameter("isallwidth"));
			rinterface.setIstname(request.getParameter("istname"));
			rinterface.setImportType(RequestUtils.getInt(request, "importType"));
			rinterface.setDatapoint(RequestUtils.getInt(request, "datapoint"));
			rinterface.setCreateDate(RequestUtils.getDate(request, "createDate"));
			rinterface.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			rinterface.setCreateBy(request.getParameter("createBy"));
			rinterface.setUpdateBy(request.getParameter("updateBy"));
			rinterface.setIsPrimaryKey(request.getParameter("isPrimaryKey"));
			rinterface.setIsGroupBy(request.getParameter("isGroupBy"));
			rinterface.setCreateBy(actorId);
			this.rinterfaceService.save(rinterface);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody Rinterface saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Rinterface rinterface = new Rinterface();
		try {
			Tools.populate(rinterface, model);
			rinterface.setIndexId(ParamUtils.getString(model, "indexId"));
			rinterface.setFrmtype(ParamUtils.getString(model, "frmtype"));
			rinterface.setListId(ParamUtils.getString(model, "listId"));
			rinterface.setIssystem(ParamUtils.getString(model, "issystem"));
			rinterface.setFname(ParamUtils.getString(model, "fname"));
			rinterface.setDname(ParamUtils.getString(model, "dname"));
			rinterface.setDtype(ParamUtils.getString(model, "dtype"));
			rinterface.setShowtype(ParamUtils.getString(model, "showtype"));
			rinterface.setStrlen(ParamUtils.getInt(model, "strlen"));
			rinterface.setForm(ParamUtils.getString(model, "form"));
			rinterface.setIntype(ParamUtils.getString(model, "intype"));
			rinterface.setHintID(ParamUtils.getString(model, "hintID"));
			rinterface.setListno(ParamUtils.getInt(model, "listno"));
			rinterface.setZtype(ParamUtils.getString(model, "ztype"));
			rinterface.setIsmustfill(ParamUtils.getString(model, "ismustfill"));
			rinterface.setIsListShow(ParamUtils.getString(model, "isListShow"));
			rinterface.setListweigth(ParamUtils.getInt(model, "listweigth"));
			rinterface.setIsallwidth(ParamUtils.getString(model, "isallwidth"));
			rinterface.setIstname(ParamUtils.getString(model, "istname"));
			rinterface.setImportType(ParamUtils.getInt(model, "importType"));
			rinterface.setDatapoint(ParamUtils.getInt(model, "datapoint"));
			rinterface.setCreateDate(ParamUtils.getDate(model, "createDate"));
			rinterface.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			rinterface.setCreateBy(ParamUtils.getString(model, "createBy"));
			rinterface.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			rinterface.setIsPrimaryKey(ParamUtils.getString(model, "isPrimaryKey"));
			rinterface.setIsGroupBy(ParamUtils.getString(model, "isGroupBy"));
			rinterface.setCreateBy(actorId);
			this.rinterfaceService.save(rinterface);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return rinterface;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		Rinterface rinterface = rinterfaceService.getRinterface(RequestUtils.getString(request, "id"));

		Tools.populate(rinterface, params);

		rinterface.setIndexId(request.getParameter("indexId"));
		rinterface.setFrmtype(request.getParameter("frmtype"));
		rinterface.setListId(request.getParameter("listId"));
		rinterface.setIssystem(request.getParameter("issystem"));
		rinterface.setFname(request.getParameter("fname"));
		rinterface.setDname(request.getParameter("dname"));
		rinterface.setDtype(request.getParameter("dtype"));
		rinterface.setShowtype(request.getParameter("showtype"));
		rinterface.setStrlen(RequestUtils.getInt(request, "strlen"));
		rinterface.setForm(request.getParameter("form"));
		rinterface.setIntype(request.getParameter("intype"));
		rinterface.setHintID(request.getParameter("hintID"));
		rinterface.setListno(RequestUtils.getInt(request, "listno"));
		rinterface.setZtype(request.getParameter("ztype"));
		rinterface.setIsmustfill(request.getParameter("ismustfill"));
		rinterface.setIsListShow(request.getParameter("isListShow"));
		rinterface.setListweigth(RequestUtils.getInt(request, "listweigth"));
		rinterface.setIsallwidth(request.getParameter("isallwidth"));
		rinterface.setIstname(request.getParameter("istname"));
		rinterface.setImportType(RequestUtils.getInt(request, "importType"));
		rinterface.setDatapoint(RequestUtils.getInt(request, "datapoint"));
		rinterface.setCreateDate(RequestUtils.getDate(request, "createDate"));
		rinterface.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		rinterface.setCreateBy(request.getParameter("createBy"));
		rinterface.setUpdateBy(request.getParameter("updateBy"));
		rinterface.setIsPrimaryKey(request.getParameter("isPrimaryKey"));
		rinterface.setIsGroupBy(request.getParameter("isGroupBy"));

		rinterfaceService.save(rinterface);

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
					Rinterface rinterface = rinterfaceService.getRinterface(RequestUtils.getString(request, "id"));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (rinterface != null && (StringUtils.equals(rinterface.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						// rinterface.setDeleteFlag(1);
						rinterfaceService.save(rinterface);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			Rinterface rinterface = rinterfaceService.getRinterface(RequestUtils.getString(request, "id"));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (rinterface != null && (StringUtils.equals(rinterface.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// rinterface.setDeleteFlag(1);
				rinterfaceService.save(rinterface);
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
		Rinterface rinterface = rinterfaceService.getRinterface(RequestUtils.getString(request, "id"));
		if (rinterface != null) {
			request.setAttribute("rinterface", rinterface);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("rinterface.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/isdp/rinterface/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Rinterface rinterface = rinterfaceService.getRinterface(RequestUtils.getString(request, "id"));
		request.setAttribute("rinterface", rinterface);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("rinterface.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/isdp/rinterface/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("rinterface.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/isdp/rinterface/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RinterfaceQuery query = new RinterfaceQuery();
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
		int total = rinterfaceService.getRinterfaceCountByQueryCriteria(query);
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
			List<Rinterface> list = rinterfaceService.getRinterfacesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Rinterface rinterface : list) {
					JSONObject rowJSON = rinterface.toJsonObject();
					rowJSON.put("id", rinterface.getListId());
					rowJSON.put("rowId", rinterface.getListId());
					rowJSON.put("rinterfaceId", rinterface.getListId());
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
		RinterfaceQuery query = new RinterfaceQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		RinterfaceDomainFactory.processDataRequest(dataRequest);

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
		int total = rinterfaceService.getRinterfaceCountByQueryCriteria(query);
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
			List<Rinterface> list = rinterfaceService.getRinterfacesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Rinterface rinterface : list) {
					JSONObject rowJSON = rinterface.toJsonObject();
					rowJSON.put("id", rinterface.getListId());
					rowJSON.put("rinterfaceId", rinterface.getListId());
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

		return new ModelAndView("/isdp/rinterface/list", modelMap);
	}

}
