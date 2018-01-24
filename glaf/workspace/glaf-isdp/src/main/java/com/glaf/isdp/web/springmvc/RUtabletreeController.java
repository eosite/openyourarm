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

@Controller("/isdp/rUtabletree")
@RequestMapping("/isdp/rUtabletree")
public class RUtabletreeController {
	protected static final Log logger = LogFactory.getLog(RUtabletreeController.class);

	protected RUtabletreeService rUtabletreeService;

	public RUtabletreeController() {

	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.rUtabletreeService")
	public void setRUtabletreeService(RUtabletreeService rUtabletreeService) {
		this.rUtabletreeService = rUtabletreeService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		RUtabletree rUtabletree = new RUtabletree();
		Tools.populate(rUtabletree, params);

		rUtabletree.setId(request.getParameter("id"));
		rUtabletree.setParentId(RequestUtils.getInt(request, "parentId"));
		rUtabletree.setIndexName(request.getParameter("indexName"));
		rUtabletree.setNlevel(RequestUtils.getInt(request, "nlevel"));
		rUtabletree.setNodeico(RequestUtils.getInt(request, "nodeico"));
		rUtabletree.setListno(RequestUtils.getInt(request, "listno"));
		rUtabletree.setTabletype(RequestUtils.getInt(request, "tabletype"));
		rUtabletree.setIntdel(RequestUtils.getInt(request, "intdel"));
		rUtabletree.setBusiessId(request.getParameter("busiessId"));
		rUtabletree.setContent(request.getParameter("content"));
		rUtabletree.setNum(request.getParameter("num"));
		rUtabletree.setMenuindex(RequestUtils.getInt(request, "menuindex"));
		rUtabletree.setDomainIndex(RequestUtils.getInt(request, "domainIndex"));
		rUtabletree.setWinWidth(RequestUtils.getInt(request, "winWidth"));
		rUtabletree.setWinHeight(RequestUtils.getInt(request, "winHeight"));

		// rUtabletree.setCreateBy(actorId);

		rUtabletreeService.save(rUtabletree);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveRUtabletree")
	public byte[] saveRUtabletree(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RUtabletree rUtabletree = new RUtabletree();
		try {
			Tools.populate(rUtabletree, params);
			rUtabletree.setId(request.getParameter("id"));
			rUtabletree.setParentId(RequestUtils.getInt(request, "parentId"));
			rUtabletree.setIndexName(request.getParameter("indexName"));
			rUtabletree.setNlevel(RequestUtils.getInt(request, "nlevel"));
			rUtabletree.setNodeico(RequestUtils.getInt(request, "nodeico"));
			rUtabletree.setListno(RequestUtils.getInt(request, "listno"));
			rUtabletree.setTabletype(RequestUtils.getInt(request, "tableType"));
			rUtabletree.setIntdel(RequestUtils.getInt(request, "intdel"));
			rUtabletree.setBusiessId(request.getParameter("busiessId"));
			rUtabletree.setContent(request.getParameter("content"));
			rUtabletree.setNum(request.getParameter("num"));
			rUtabletree.setMenuindex(RequestUtils.getInt(request, "menuindex"));
			rUtabletree.setDomainIndex(RequestUtils.getInt(request, "domainIndex"));
			rUtabletree.setWinWidth(RequestUtils.getInt(request, "winWidth"));
			rUtabletree.setWinHeight(RequestUtils.getInt(request, "winHeight"));
			// rUtabletree.setCreateBy(actorId);
			this.rUtabletreeService.save(rUtabletree);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody RUtabletree saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RUtabletree rUtabletree = new RUtabletree();
		try {
			Tools.populate(rUtabletree, model);
			rUtabletree.setId(ParamUtils.getString(model, "id"));
			rUtabletree.setParentId(ParamUtils.getInt(model, "parentId"));
			rUtabletree.setIndexName(ParamUtils.getString(model, "indexName"));
			rUtabletree.setNlevel(ParamUtils.getInt(model, "nlevel"));
			rUtabletree.setNodeico(ParamUtils.getInt(model, "nodeico"));
			rUtabletree.setListno(ParamUtils.getInt(model, "listno"));
			rUtabletree.setTabletype(ParamUtils.getInt(model, "tabletype"));
			rUtabletree.setIntdel(ParamUtils.getInt(model, "intdel"));
			rUtabletree.setBusiessId(ParamUtils.getString(model, "busiessId"));
			rUtabletree.setContent(ParamUtils.getString(model, "content"));
			rUtabletree.setNum(ParamUtils.getString(model, "num"));
			rUtabletree.setMenuindex(ParamUtils.getInt(model, "menuindex"));
			rUtabletree.setDomainIndex(ParamUtils.getInt(model, "domainIndex"));
			rUtabletree.setWinWidth(ParamUtils.getInt(model, "winWidth"));
			rUtabletree.setWinHeight(ParamUtils.getInt(model, "winHeight"));
			this.rUtabletreeService.save(rUtabletree);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return rUtabletree;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		RUtabletree rUtabletree = rUtabletreeService.getRUtabletree(RequestUtils.getInt(request, "indexId"));

		Tools.populate(rUtabletree, params);

		rUtabletree.setId(request.getParameter("id"));
		rUtabletree.setParentId(RequestUtils.getInt(request, "parentId"));
		rUtabletree.setIndexName(request.getParameter("indexName"));
		rUtabletree.setNlevel(RequestUtils.getInt(request, "nlevel"));
		rUtabletree.setNodeico(RequestUtils.getInt(request, "nodeico"));
		rUtabletree.setListno(RequestUtils.getInt(request, "listno"));
		rUtabletree.setTabletype(RequestUtils.getInt(request, "tabletype"));
		rUtabletree.setIntdel(RequestUtils.getInt(request, "intdel"));
		rUtabletree.setBusiessId(request.getParameter("busiessId"));
		rUtabletree.setContent(request.getParameter("content"));
		rUtabletree.setNum(request.getParameter("num"));
		rUtabletree.setMenuindex(RequestUtils.getInt(request, "menuindex"));
		rUtabletree.setDomainIndex(RequestUtils.getInt(request, "domainIndex"));
		rUtabletree.setWinWidth(RequestUtils.getInt(request, "winWidth"));
		rUtabletree.setWinHeight(RequestUtils.getInt(request, "winHeight"));

		rUtabletreeService.save(rUtabletree);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Integer indexId = RequestUtils.getInteger(request, "indexId");
		String indexIds = request.getParameter("indexIds");
		if (StringUtils.isNotEmpty(indexIds)) {
			StringTokenizer token = new StringTokenizer(indexIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					RUtabletree rUtabletree = rUtabletreeService.getRUtabletree(Integer.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (rUtabletree != null && (StringUtils.equals("admin", loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						// rUtabletree.setDeleteFlag(1);
						rUtabletreeService.save(rUtabletree);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (indexId != null) {
			RUtabletree rUtabletree = rUtabletreeService.getRUtabletree(Integer.valueOf(indexId));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (rUtabletree != null && (StringUtils.equals("admin", loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// rUtabletree.setDeleteFlag(1);
				rUtabletreeService.save(rUtabletree);
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
		RUtabletree rUtabletree = rUtabletreeService.getRUtabletree(RequestUtils.getInt(request, "indexId"));
		if (rUtabletree != null) {
			request.setAttribute("rUtabletree", rUtabletree);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("rUtabletree.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/isdp/rUtabletree/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RUtabletree rUtabletree = rUtabletreeService.getRUtabletree(RequestUtils.getInt(request, "indexId"));
		request.setAttribute("rUtabletree", rUtabletree);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("rUtabletree.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/isdp/rUtabletree/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("rUtabletree.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/isdp/rUtabletree/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RUtabletreeQuery query = new RUtabletreeQuery();
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
		int total = rUtabletreeService.getRUtabletreeCountByQueryCriteria(query);
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
			List<RUtabletree> list = rUtabletreeService.getRUtabletreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (RUtabletree rUtabletree : list) {
					JSONObject rowJSON = rUtabletree.toJsonObject();
					rowJSON.put("id", rUtabletree.getId());
					rowJSON.put("rowId", rUtabletree.getId());
					rowJSON.put("rUtabletreeId", rUtabletree.getId());
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
		RUtabletreeQuery query = new RUtabletreeQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		RUtabletreeDomainFactory.processDataRequest(dataRequest);

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
		int total = rUtabletreeService.getRUtabletreeCountByQueryCriteria(query);
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
			List<RUtabletree> list = rUtabletreeService.getRUtabletreesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (RUtabletree rUtabletree : list) {
					JSONObject rowJSON = rUtabletree.toJsonObject();
					rowJSON.put("id", rUtabletree.getId());
					rowJSON.put("rUtabletreeId", rUtabletree.getId());
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

		return new ModelAndView("/isdp/rUtabletree/list", modelMap);
	}

}
