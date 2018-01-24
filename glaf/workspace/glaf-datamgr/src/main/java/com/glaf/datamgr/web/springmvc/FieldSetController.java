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
import com.glaf.datamgr.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/fieldset")
@RequestMapping("/datamgr/fieldset")
public class FieldSetController {
	protected static final Log logger = LogFactory.getLog(FieldSetController.class);

	protected FieldSetService fieldSetService;

	public FieldSetController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					FieldSet fieldSet = fieldSetService.getFieldSet(String.valueOf(x));
					if (fieldSet != null && (StringUtils.equals(fieldSet.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						fieldSet.setDeleteFlag(1);
						fieldSet.setUpdateBy(loginContext.getActorId());
						fieldSetService.save(fieldSet);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			FieldSet fieldSet = fieldSetService.getFieldSet(String.valueOf(id));
			if (fieldSet != null && (StringUtils.equals(fieldSet.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				fieldSet.setDeleteFlag(1);
				fieldSet.setUpdateBy(loginContext.getActorId());
				fieldSetService.save(fieldSet);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		FieldSet fieldSet = fieldSetService.getFieldSet(request.getParameter("id"));
		if (fieldSet != null) {
			request.setAttribute("fieldset", fieldSet);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("fieldset.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/fieldset/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FieldSetQuery query = new FieldSetQuery();
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
		int total = fieldSetService.getFieldSetCountByQueryCriteria(query);
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

			List<FieldSet> list = fieldSetService.getFieldSetsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FieldSet fieldSet : list) {
					JSONObject rowJSON = fieldSet.toJsonObject();
					rowJSON.put("id", fieldSet.getId());
					rowJSON.put("rowId", fieldSet.getId());
					rowJSON.put("fieldsetId", fieldSet.getId());
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

		return new ModelAndView("/datamgr/fieldset/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("fieldset.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/fieldset/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FieldSetQuery query = new FieldSetQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		FieldSetDomainFactory.processDataRequest(dataRequest);

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
		int total = fieldSetService.getFieldSetCountByQueryCriteria(query);
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

			List<FieldSet> list = fieldSetService.getFieldSetsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FieldSet fieldSet : list) {
					JSONObject rowJSON = fieldSet.toJsonObject();
					rowJSON.put("id", fieldSet.getId());
					rowJSON.put("fieldsetId", fieldSet.getId());
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
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FieldSet fieldSet = new FieldSet();
		Tools.populate(fieldSet, params);

		fieldSet.setDatasetId(RequestUtils.getLong(request, "datasetId"));
		fieldSet.setName(request.getParameter("name"));
		fieldSet.setCode(request.getParameter("code"));
		fieldSet.setFieldTable(request.getParameter("fieldTable"));
		fieldSet.setTableNameCN(request.getParameter("tableNameCN"));
		fieldSet.setColumnName(request.getParameter("columnName"));
		fieldSet.setColumnWidth(request.getParameter("columnWidth"));
		fieldSet.setText(request.getParameter("text"));
		fieldSet.setDescription(request.getParameter("description"));
		fieldSet.setFieldId(request.getParameter("fieldId"));
		fieldSet.setFieldLength(RequestUtils.getInt(request, "fieldLength"));
		fieldSet.setFieldType(request.getParameter("fieldType"));
		fieldSet.setIsShowList(request.getParameter("isShowList"));
		fieldSet.setIsShowTooltip(request.getParameter("isShowTooltip"));
		fieldSet.setIsEditor(request.getParameter("isEditor"));
		fieldSet.setEditor(request.getParameter("editor"));
		fieldSet.setState(request.getParameter("state"));
		fieldSet.setChecked(request.getParameter("checked"));
		fieldSet.setAlignment(request.getParameter("alignment"));
		fieldSet.setDomId(request.getParameter("domId"));
		fieldSet.setTarget(request.getParameter("target"));
		fieldSet.setUrl(request.getParameter("url"));
		fieldSet.setType(request.getParameter("type"));
		fieldSet.setLocked(RequestUtils.getInt(request, "locked"));
		fieldSet.setCreateBy(actorId);
		fieldSet.setUpdateBy(actorId);

		fieldSetService.save(fieldSet);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveFieldSet")
	public byte[] saveFieldSet(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FieldSet fieldSet = new FieldSet();
		try {
			Tools.populate(fieldSet, params);
			fieldSet.setDatasetId(RequestUtils.getLong(request, "datasetId"));
			fieldSet.setName(request.getParameter("name"));
			fieldSet.setCode(request.getParameter("code"));
			fieldSet.setFieldTable(request.getParameter("fieldTable"));
			fieldSet.setTableNameCN(request.getParameter("tableNameCN"));
			fieldSet.setColumnName(request.getParameter("columnName"));
			fieldSet.setColumnWidth(request.getParameter("columnWidth"));
			fieldSet.setText(request.getParameter("text"));
			fieldSet.setDescription(request.getParameter("description"));
			fieldSet.setFieldId(request.getParameter("fieldId"));
			fieldSet.setFieldLength(RequestUtils.getInt(request, "fieldLength"));
			fieldSet.setFieldType(request.getParameter("fieldType"));
			fieldSet.setIsShowList(request.getParameter("isShowList"));
			fieldSet.setIsShowTooltip(request.getParameter("isShowTooltip"));
			fieldSet.setIsEditor(request.getParameter("isEditor"));
			fieldSet.setEditor(request.getParameter("editor"));
			fieldSet.setState(request.getParameter("state"));
			fieldSet.setChecked(request.getParameter("checked"));
			fieldSet.setAlignment(request.getParameter("alignment"));
			fieldSet.setDomId(request.getParameter("domId"));
			fieldSet.setTarget(request.getParameter("target"));
			fieldSet.setUrl(request.getParameter("url"));
			fieldSet.setType(request.getParameter("type"));
			fieldSet.setLocked(RequestUtils.getInt(request, "locked"));
			fieldSet.setCreateBy(actorId);
			fieldSet.setUpdateBy(actorId);

			this.fieldSetService.save(fieldSet);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody FieldSet saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		FieldSet fieldSet = new FieldSet();
		try {
			Tools.populate(fieldSet, model);
			fieldSet.setDatasetId(ParamUtils.getLong(model, "datasetId"));
			fieldSet.setName(ParamUtils.getString(model, "name"));
			fieldSet.setCode(ParamUtils.getString(model, "code"));
			fieldSet.setFieldTable(ParamUtils.getString(model, "fieldTable"));
			fieldSet.setTableNameCN(ParamUtils.getString(model, "tableNameCN"));
			fieldSet.setColumnName(ParamUtils.getString(model, "columnName"));
			fieldSet.setColumnWidth(ParamUtils.getString(model, "columnWidth"));
			fieldSet.setText(ParamUtils.getString(model, "text"));
			fieldSet.setDescription(ParamUtils.getString(model, "description"));
			fieldSet.setFieldId(ParamUtils.getString(model, "fieldId"));
			fieldSet.setFieldLength(ParamUtils.getInt(model, "fieldLength"));
			fieldSet.setFieldType(ParamUtils.getString(model, "fieldType"));
			fieldSet.setIsShowList(ParamUtils.getString(model, "isShowList"));
			fieldSet.setIsShowTooltip(ParamUtils.getString(model, "isShowTooltip"));
			fieldSet.setIsEditor(ParamUtils.getString(model, "isEditor"));
			fieldSet.setEditor(ParamUtils.getString(model, "editor"));
			fieldSet.setState(ParamUtils.getString(model, "state"));
			fieldSet.setChecked(ParamUtils.getString(model, "checked"));
			fieldSet.setAlignment(ParamUtils.getString(model, "alignment"));
			fieldSet.setDomId(ParamUtils.getString(model, "domId"));
			fieldSet.setTarget(ParamUtils.getString(model, "target"));
			fieldSet.setUrl(ParamUtils.getString(model, "url"));
			fieldSet.setType(ParamUtils.getString(model, "type"));
			fieldSet.setLocked(ParamUtils.getInt(model, "locked"));
			fieldSet.setCreateBy(actorId);
			fieldSet.setUpdateBy(actorId);

			this.fieldSetService.save(fieldSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return fieldSet;
	}

	@javax.annotation.Resource
	public void setFieldSetService(FieldSetService fieldSetService) {
		this.fieldSetService = fieldSetService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		FieldSet fieldSet = fieldSetService.getFieldSet(request.getParameter("id"));

		Tools.populate(fieldSet, params);

		fieldSet.setDatasetId(RequestUtils.getLong(request, "datasetId"));
		fieldSet.setName(request.getParameter("name"));
		fieldSet.setCode(request.getParameter("code"));
		fieldSet.setFieldTable(request.getParameter("fieldTable"));
		fieldSet.setTableNameCN(request.getParameter("tableNameCN"));
		fieldSet.setColumnName(request.getParameter("columnName"));
		fieldSet.setColumnWidth(request.getParameter("columnWidth"));
		fieldSet.setText(request.getParameter("text"));
		fieldSet.setDescription(request.getParameter("description"));
		fieldSet.setFieldId(request.getParameter("fieldId"));
		fieldSet.setFieldLength(RequestUtils.getInt(request, "fieldLength"));
		fieldSet.setFieldType(request.getParameter("fieldType"));
		fieldSet.setIsShowList(request.getParameter("isShowList"));
		fieldSet.setIsShowTooltip(request.getParameter("isShowTooltip"));
		fieldSet.setIsEditor(request.getParameter("isEditor"));
		fieldSet.setEditor(request.getParameter("editor"));
		fieldSet.setState(request.getParameter("state"));
		fieldSet.setChecked(request.getParameter("checked"));
		fieldSet.setAlignment(request.getParameter("alignment"));
		fieldSet.setDomId(request.getParameter("domId"));
		fieldSet.setTarget(request.getParameter("target"));
		fieldSet.setUrl(request.getParameter("url"));
		fieldSet.setType(request.getParameter("type"));
		fieldSet.setLocked(RequestUtils.getInt(request, "locked"));
		fieldSet.setUpdateBy(user.getActorId());

		fieldSetService.save(fieldSet);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		FieldSet fieldset = fieldSetService.getFieldSet(request.getParameter("id"));
		request.setAttribute("fieldset", fieldset);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("fieldset.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/datamgr/fieldset/view");
	}

}
