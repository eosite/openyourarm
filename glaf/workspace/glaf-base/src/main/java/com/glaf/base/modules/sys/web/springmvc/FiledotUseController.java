package com.glaf.base.modules.sys.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
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
import com.glaf.core.config.Environment;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;
import com.glaf.base.modules.sys.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/cell/filedotUse")
@RequestMapping("/cell/filedotUse")
public class FiledotUseController {
	protected static final Log logger = LogFactory
			.getLog(FiledotUseController.class);

	protected FiledotUseService filedotUseService;

	protected IFieldInterfaceService fieldInterfaceService;

	public FiledotUseController() {

	}

	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode",systemName);
		Environment.setCurrentSystemName(databaseCode);
		
		String fileId = request.getParameter("fileID");
		try {
			//fileId = RequestUtils.decodeString(fileId);
			fileId = new String(Base64.decodeBase64(fileId),"UTF-8");
			FiledotUse filedotUse = filedotUseService.getFiledotUseById(fileId);
			if (filedotUse != null && filedotUse.getFileName() != null
					&& filedotUse.getFileContent() != null) {
				ResponseUtils.download(request, response,
						filedotUse.getFileContent(), filedotUse.getFileName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(systemName);
		}
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		FiledotUse filedotUse = filedotUseService.getFiledotUseById(request
				.getParameter("fileID"));
		if (filedotUse != null) {
			request.setAttribute("filedotUse", filedotUse);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("filedotUse.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/isdp/filedotUse/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FiledotUseQuery query = new FiledotUseQuery();
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
		int total = filedotUseService.getFiledotUseCountByQueryCriteria(query);
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

			List<FiledotUse> list = filedotUseService
					.getFiledotUsesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FiledotUse filedotUse : list) {
					JSONObject rowJSON = filedotUse.toJsonObject();

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
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String frmType = request.getParameter("frmType");
		if (StringUtils.isNotEmpty(frmType)) {
			List<FieldInterface> fields = fieldInterfaceService
					.getListShowFieldsByFrmType(frmType);
			request.setAttribute("fields", fields);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/isdp/filedotUse/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("filedotUse.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/isdp/filedotUse/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FiledotUseQuery query = new FiledotUseQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FiledotUseDomainFactory.processDataRequest(dataRequest);

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
		int total = filedotUseService.getFiledotUseCountByQueryCriteria(query);
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

			if (dataRequest.getSort() != null
					&& !dataRequest.getSort().isEmpty()) {
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

			List<FiledotUse> list = filedotUseService
					.getFiledotUsesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FiledotUse filedotUse : list) {
					JSONObject rowJSON = filedotUse.toJsonObject();

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

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		// User user = RequestUtils.getUser(request);
		// String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FiledotUse filedotUse = new FiledotUse();
		Tools.populate(filedotUse, params);

		filedotUse.setIndexId(RequestUtils.getInt(request, "indexId"));
		filedotUse.setProjid(RequestUtils.getInt(request, "projid"));
		filedotUse.setPid(RequestUtils.getInt(request, "pid"));
		filedotUse.setDotid(request.getParameter("dotid"));
		filedotUse.setNum(request.getParameter("num"));
		filedotUse.setName(request.getParameter("name"));
		filedotUse.setCman(request.getParameter("cman"));

		filedotUse.setFileName(request.getParameter("fileName"));
		filedotUse.setFilesize(RequestUtils.getInt(request, "filesize"));
		filedotUse.setVision(request.getParameter("vision"));
		filedotUse.setSavetime(request.getParameter("savetime"));
		filedotUse.setRemark(request.getParameter("remark"));
		filedotUse.setDwid(RequestUtils.getInt(request, "dwid"));
		filedotUse.setFbid(RequestUtils.getInt(request, "fbid"));
		filedotUse.setFxid(RequestUtils.getInt(request, "fxid"));
		filedotUse.setJid(request.getParameter("jid"));
		filedotUse.setFlid(request.getParameter("flid"));
		filedotUse.setTopnode(request.getParameter("topnode"));
		filedotUse.setTopid(request.getParameter("topid"));
		filedotUse.setFname(request.getParameter("fname"));
		filedotUse.setIscheck(RequestUtils.getInt(request, "ischeck"));
		filedotUse.setIsink(request.getParameter("isink"));
		filedotUse.setOldid(request.getParameter("oldid"));
		filedotUse.setTaskid(request.getParameter("taskid"));
		filedotUse.setType(RequestUtils.getInt(request, "type"));

		// filedotUse.setCreateBy(actorId);

		filedotUseService.save(filedotUse);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFiledotUse")
	public byte[] saveFiledotUse(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FiledotUse filedotUse = new FiledotUse();
		try {
			Tools.populate(filedotUse, params);
			filedotUse.setIndexId(RequestUtils.getInt(request, "indexId"));
			filedotUse.setProjid(RequestUtils.getInt(request, "projid"));
			filedotUse.setPid(RequestUtils.getInt(request, "pid"));
			filedotUse.setDotid(request.getParameter("dotid"));
			filedotUse.setNum(request.getParameter("num"));
			filedotUse.setName(request.getParameter("name"));
			filedotUse.setCman(request.getParameter("cman"));
			filedotUse.setFileName(request.getParameter("fileName"));
			filedotUse.setFilesize(RequestUtils.getInt(request, "filesize"));
			filedotUse.setVision(request.getParameter("vision"));
			filedotUse.setSavetime(request.getParameter("savetime"));
			filedotUse.setRemark(request.getParameter("remark"));
			filedotUse.setDwid(RequestUtils.getInt(request, "dwid"));
			filedotUse.setFbid(RequestUtils.getInt(request, "fbid"));
			filedotUse.setFxid(RequestUtils.getInt(request, "fxid"));
			filedotUse.setJid(request.getParameter("jid"));
			filedotUse.setFlid(request.getParameter("flid"));
			filedotUse.setTopnode(request.getParameter("topnode"));
			filedotUse.setTopid(request.getParameter("topid"));
			filedotUse.setFname(request.getParameter("fname"));
			filedotUse.setIscheck(RequestUtils.getInt(request, "ischeck"));
			filedotUse.setIsink(request.getParameter("isink"));
			filedotUse.setOldid(request.getParameter("oldid"));
			filedotUse.setTaskid(request.getParameter("taskid"));
			filedotUse.setType(RequestUtils.getInt(request, "type"));
			// filedotUse.setCreateBy(actorId);
			this.filedotUseService.save(filedotUse);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FiledotUse saveOrUpdate(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		FiledotUse filedotUse = new FiledotUse();
		try {
			Tools.populate(filedotUse, model);
			filedotUse.setIndexId(ParamUtils.getInt(model, "indexId"));
			filedotUse.setProjid(ParamUtils.getInt(model, "projid"));
			filedotUse.setPid(ParamUtils.getInt(model, "pid"));
			filedotUse.setDotid(ParamUtils.getString(model, "dotid"));
			filedotUse.setNum(ParamUtils.getString(model, "num"));
			filedotUse.setName(ParamUtils.getString(model, "name"));
			filedotUse.setCman(ParamUtils.getString(model, "cman"));
			filedotUse.setFileName(ParamUtils.getString(model, "fileName"));
			filedotUse.setFilesize(ParamUtils.getInt(model, "filesize"));
			filedotUse.setVision(ParamUtils.getString(model, "vision"));
			filedotUse.setSavetime(ParamUtils.getString(model, "savetime"));
			filedotUse.setRemark(ParamUtils.getString(model, "remark"));
			filedotUse.setDwid(ParamUtils.getInt(model, "dwid"));
			filedotUse.setFbid(ParamUtils.getInt(model, "fbid"));
			filedotUse.setFxid(ParamUtils.getInt(model, "fxid"));
			filedotUse.setJid(ParamUtils.getString(model, "jid"));
			filedotUse.setFlid(ParamUtils.getString(model, "flid"));
			filedotUse.setTopnode(ParamUtils.getString(model, "topnode"));
			filedotUse.setTopid(ParamUtils.getString(model, "topid"));
			filedotUse.setFname(ParamUtils.getString(model, "fname"));
			filedotUse.setIscheck(ParamUtils.getInt(model, "ischeck"));
			filedotUse.setIsink(ParamUtils.getString(model, "isink"));
			filedotUse.setOldid(ParamUtils.getString(model, "oldid"));
			filedotUse.setTaskid(ParamUtils.getString(model, "taskid"));
			filedotUse.setType(ParamUtils.getInt(model, "type"));

			this.filedotUseService.save(filedotUse);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return filedotUse;
	}

	@javax.annotation.Resource(name = "com.glaf.base.modules.sys.service.filedotUseService")
	public void setFiledotUseService(FiledotUseService filedotUseService) {
		this.filedotUseService = filedotUseService;
	}

	@javax.annotation.Resource
	public void setFieldInterfaceService(
			IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		// User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FiledotUse filedotUse = filedotUseService.getFiledotUseById(request
				.getParameter("fileID"));

		Tools.populate(filedotUse, params);

		filedotUse.setIndexId(RequestUtils.getInt(request, "indexId"));
		filedotUse.setProjid(RequestUtils.getInt(request, "projid"));
		filedotUse.setPid(RequestUtils.getInt(request, "pid"));
		filedotUse.setDotid(request.getParameter("dotid"));
		filedotUse.setNum(request.getParameter("num"));
		filedotUse.setName(request.getParameter("name"));
		filedotUse.setCman(request.getParameter("cman"));

		filedotUse.setFileName(request.getParameter("fileName"));
		filedotUse.setFilesize(RequestUtils.getInt(request, "filesize"));
		filedotUse.setVision(request.getParameter("vision"));
		filedotUse.setSavetime(request.getParameter("savetime"));
		filedotUse.setRemark(request.getParameter("remark"));
		filedotUse.setDwid(RequestUtils.getInt(request, "dwid"));
		filedotUse.setFbid(RequestUtils.getInt(request, "fbid"));
		filedotUse.setFxid(RequestUtils.getInt(request, "fxid"));
		filedotUse.setJid(request.getParameter("jid"));
		filedotUse.setFlid(request.getParameter("flid"));
		filedotUse.setTopnode(request.getParameter("topnode"));
		filedotUse.setTopid(request.getParameter("topid"));
		filedotUse.setFname(request.getParameter("fname"));
		filedotUse.setIscheck(RequestUtils.getInt(request, "ischeck"));
		filedotUse.setIsink(request.getParameter("isink"));
		filedotUse.setOldid(request.getParameter("oldid"));
		filedotUse.setTaskid(request.getParameter("taskid"));
		filedotUse.setType(RequestUtils.getInt(request, "type"));

		filedotUseService.save(filedotUse);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		FiledotUse filedotUse = filedotUseService.getFiledotUseById(request
				.getParameter("fileID"));
		request.setAttribute("filedotUse", filedotUse);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("filedotUse.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/isdp/filedotUse/view");
	}

}
