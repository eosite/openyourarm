package com.glaf.datamgr.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.User;
import com.glaf.core.util.*;

import com.glaf.datamgr.domain.FieldSet;
import com.glaf.datamgr.query.FieldSetQuery;
import com.glaf.datamgr.service.FieldSetService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/datamgr/fieldset")
public class FieldSetResource {
	protected static final Log logger = LogFactory.getLog(FieldSetResource.class);

	protected FieldSetService fieldSetService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FieldSetQuery query = new FieldSetQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FieldSetDomainFactory.processDataRequest(dataRequest);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
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

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		fieldSetService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FieldSetQuery query = new FieldSetQuery();
		Tools.populate(query, params);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
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

	@POST
	@Path("/saveFieldSet")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveFieldSet(@Context HttpServletRequest request) {
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
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setFieldSetService(FieldSetService fieldSetService) {
		this.fieldSetService = fieldSetService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		FieldSet fieldSet = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			fieldSet = fieldSetService.getFieldSet(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (fieldSet != null) {
			result = fieldSet.toJsonObject();
			result.put("id", fieldSet.getId());
			result.put("fieldsetId", fieldSet.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
