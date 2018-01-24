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

@Controller("/teim/tmp")
@RequestMapping("/teim/tmp")
public class EimServerTmpController {
	protected static final Log logger = LogFactory.getLog(EimServerTmpController.class);

	protected EimServerTmpService eimServerTmpService;

	public EimServerTmpController() {

	}

	@javax.annotation.Resource(name = "com.glaf.teim.service.eimServerTmpService")
	public void setEimServerTmpService(EimServerTmpService eimServerTmpService) {
		this.eimServerTmpService = eimServerTmpService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		EimServerTmp eimServerTmp = new EimServerTmp();
		Tools.populate(eimServerTmp, params);

		eimServerTmp.setCategoryId(RequestUtils.getLong(request, "categoryId"));
		eimServerTmp.setName(request.getParameter("name"));
		eimServerTmp.setPath_(request.getParameter("path_"));
		eimServerTmp.setReqUrlParam(request.getParameter("reqUrlParam"));
		eimServerTmp.setReqType(request.getParameter("reqType"));
		eimServerTmp.setReqHeader(request.getParameter("reqHeader"));
		eimServerTmp.setReqContentType(request.getParameter("reqContentType"));
		eimServerTmp.setResContentType(request.getParameter("resContentType"));
		eimServerTmp.setReqBody(request.getParameter("reqBody"));
		eimServerTmp.setResponse_(request.getParameter("response_"));
		eimServerTmp.setCreateBy(request.getParameter("createBy"));
		eimServerTmp.setCreateTime(RequestUtils.getDate(request, "createTime"));
		eimServerTmp.setUpdateBy(request.getParameter("updateBy"));
		eimServerTmp.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		eimServerTmp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		// eimServerTmp.setCreateBy(actorId);

		eimServerTmpService.save(eimServerTmp);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveEimServerTmp")
	public byte[] saveEimServerTmp(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerTmp eimServerTmp = null;
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String tmpId=request.getParameter("tmpId");
		try {
			if(StringUtils.isEmpty(tmpId)){
				eimServerTmp=new EimServerTmp();
				eimServerTmp.setCreateBy(actorId);
				eimServerTmp.setCreateTime(new Date());
			}else{
				eimServerTmp=eimServerTmpService.getEimServerTmp(tmpId);
				eimServerTmp.setUpdateBy(actorId);
				eimServerTmp.setUpdateTime(new Date());
			}
			Tools.populate(eimServerTmp, params);
            Long categoryId=RequestUtils.getLong(request, "categoryId");
            if(categoryId==-1l){
            	categoryId=null;
            }
			eimServerTmp.setCategoryId(categoryId);
			eimServerTmp.setName(request.getParameter("name"));
			eimServerTmp.setPath_(request.getParameter("path_"));
			eimServerTmp.setReqUrlParam(request.getParameter("reqUrlParam"));
			eimServerTmp.setReqType(request.getParameter("reqType"));
			eimServerTmp.setReqHeader(request.getParameter("reqHeader"));
			eimServerTmp.setReqContentType(request.getParameter("reqContentType"));
			eimServerTmp.setResContentType(request.getParameter("resContentType"));
			eimServerTmp.setReqBody(request.getParameter("reqBody"));
			eimServerTmp.setReqBodyCustom(request.getParameter("reqBodyCustom"));
			eimServerTmp.setResponse_(request.getParameter("response_"));
			eimServerTmp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

			this.eimServerTmpService.save(eimServerTmp);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody EimServerTmp saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		EimServerTmp eimServerTmp = new EimServerTmp();
		try {
			Tools.populate(eimServerTmp, model);
			eimServerTmp.setCategoryId(ParamUtils.getLong(model, "categoryId"));
			eimServerTmp.setName(ParamUtils.getString(model, "name"));
			eimServerTmp.setPath_(ParamUtils.getString(model, "path_"));
			eimServerTmp.setReqUrlParam(ParamUtils.getString(model, "reqUrlParam"));
			eimServerTmp.setReqType(ParamUtils.getString(model, "reqType"));
			eimServerTmp.setReqHeader(ParamUtils.getString(model, "reqHeader"));
			eimServerTmp.setReqContentType(ParamUtils.getString(model, "reqContentType"));
			eimServerTmp.setResContentType(ParamUtils.getString(model, "resContentType"));
			eimServerTmp.setReqBody(ParamUtils.getString(model, "reqBody"));
			eimServerTmp.setResponse_(ParamUtils.getString(model, "response_"));
			eimServerTmp.setCreateBy(ParamUtils.getString(model, "createBy"));
			eimServerTmp.setCreateTime(ParamUtils.getDate(model, "createTime"));
			eimServerTmp.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			eimServerTmp.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
			eimServerTmp.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
			eimServerTmp.setCreateBy(actorId);
			this.eimServerTmpService.save(eimServerTmp);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return eimServerTmp;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		EimServerTmp eimServerTmp = eimServerTmpService.getEimServerTmp(request.getParameter("tmpId"));

		Tools.populate(eimServerTmp, params);

		eimServerTmp.setCategoryId(RequestUtils.getLong(request, "categoryId"));
		eimServerTmp.setName(request.getParameter("name"));
		eimServerTmp.setPath_(request.getParameter("path_"));
		eimServerTmp.setReqUrlParam(request.getParameter("reqUrlParam"));
		eimServerTmp.setReqType(request.getParameter("reqType"));
		eimServerTmp.setReqHeader(request.getParameter("reqHeader"));
		eimServerTmp.setReqContentType(request.getParameter("reqContentType"));
		eimServerTmp.setResContentType(request.getParameter("resContentType"));
		eimServerTmp.setReqBody(request.getParameter("reqBody"));
		eimServerTmp.setResponse_(request.getParameter("response_"));
		eimServerTmp.setCreateBy(request.getParameter("createBy"));
		eimServerTmp.setCreateTime(RequestUtils.getDate(request, "createTime"));
		eimServerTmp.setUpdateBy(request.getParameter("updateBy"));
		eimServerTmp.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
		eimServerTmp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

		eimServerTmpService.save(eimServerTmp);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String tmpId = RequestUtils.getString(request, "tmpId");
		String tmpIds = request.getParameter("tmpIds");
		if (StringUtils.isNotEmpty(tmpIds)) {
			StringTokenizer token = new StringTokenizer(tmpIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					EimServerTmp eimServerTmp = eimServerTmpService.getEimServerTmp(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (eimServerTmp != null
							&& (StringUtils.equals(eimServerTmp.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						// eimServerTmp.setDeleteFlag(1);
						eimServerTmpService.save(eimServerTmp);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (tmpId != null) {
			EimServerTmp eimServerTmp = eimServerTmpService.getEimServerTmp(String.valueOf(tmpId));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (eimServerTmp != null && (StringUtils.equals(eimServerTmp.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// eimServerTmp.setDeleteFlag(1);
				eimServerTmpService.save(eimServerTmp);
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
		EimServerTmp eimServerTmp = eimServerTmpService.getEimServerTmp(request.getParameter("tmpId"));
		if (eimServerTmp != null) {
			request.setAttribute("eimServerTmp", eimServerTmp);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("eimServerTmp.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/teim/tmp/edit", modelMap);
	}
	@RequestMapping("/ws/edit")
	public ModelAndView webserviceEdit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerTmp eimServerTmp = eimServerTmpService.getEimServerTmp(request.getParameter("tmpId"));
		if (eimServerTmp != null) {
			request.setAttribute("eimServerTmp", eimServerTmp);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("eimServerTmp.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/teim/tmp/ws_edit", modelMap);
	}
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerTmp eimServerTmp = eimServerTmpService.getEimServerTmp(request.getParameter("tmpId"));
		request.setAttribute("eimServerTmp", eimServerTmp);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("eimServerTmp.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/teim/tmp/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("eimServerTmp.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/teim/tmp/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerTmpQuery query = new EimServerTmpQuery();
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
		int total = eimServerTmpService.getEimServerTmpCountByQueryCriteria(query);
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
			List<EimServerTmp> list = eimServerTmpService.getEimServerTmpsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EimServerTmp eimServerTmp : list) {
					JSONObject rowJSON = eimServerTmp.toJsonObject();
					rowJSON.put("id", eimServerTmp.getTmpId());
					rowJSON.put("rowId", eimServerTmp.getTmpId());
					rowJSON.put("eimServerTmpId", eimServerTmp.getTmpId());
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
		EimServerTmpQuery query = new EimServerTmpQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		EimServerTmpDomainFactory.processDataRequest(dataRequest);

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
		int total = eimServerTmpService.getEimServerTmpCountByQueryCriteria(query);
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
			List<EimServerTmp> list = eimServerTmpService.getEimServerTmpsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EimServerTmp eimServerTmp : list) {
					JSONObject rowJSON = eimServerTmp.toJsonObject();
					rowJSON.put("id", eimServerTmp.getTmpId());
					rowJSON.put("eimServerTmpId", eimServerTmp.getTmpId());
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

		return new ModelAndView("/teim/tmp/list", modelMap);
	}

	@RequestMapping("/categorylist")
	public ModelAndView categorylist(HttpServletRequest request, ModelMap modelMap) {
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

		return new ModelAndView("/teim/tmp/category_tmp_list", modelMap);
	}
}
