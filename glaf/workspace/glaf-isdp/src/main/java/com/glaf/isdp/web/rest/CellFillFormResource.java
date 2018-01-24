package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
 
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.CellFillForm;
import com.glaf.isdp.query.CellFillFormQuery;
import com.glaf.isdp.service.CellFillFormService;
import com.glaf.isdp.util.CellFillFormDomainFactory;
import com.glaf.isdp.util.JSONConvertUtil;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/cellFillForm")
public class CellFillFormResource {
	protected static final Log logger = LogFactory
			.getLog(CellFillFormResource.class);

	protected CellFillFormService cellFillFormService;
	
	/**
	 * 检查表格是否有多张
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/tableList")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] tableList(@Context HttpServletRequest request) throws Exception {
		int indexId = RequestUtils.getInt(request, "indexId", 0);
		String fileDotFileId = RequestUtils.getString(request, "fileDotFileId", "");
		String data_id = RequestUtils.getString(request, "data_id", "");
		//String name = RequestUtils.getString(request, "name");
		String systemName = Environment.getCurrentSystemName();
		
		String databaseCode = RequestUtils.getString(request, "systemName", systemName);
		Environment.setCurrentSystemName(databaseCode);
		
		JSONObject results = new JSONObject();
		
		CellFillFormQuery query = new CellFillFormQuery();
		
		//if(StringUtils.isNotEmpty(data_id)){
			query.setData_id(data_id);
		//} else {
			query.setIndexId(indexId);
		//}
		query.setFileDotFileId(fileDotFileId);
		query.setOrderBy("id");
		int total = cellFillFormService.getCellFillFormCountByQueryCriteria(query);
		results.put("total", total);

		JSONObject jobject = new JSONObject();
		jobject.put("total", total);
		List<CellFillForm> cellFillFormList = cellFillFormService.list(query);
		//for(CellFillForm model : cellFillFormList){
			//model.setName(name);
		//}
		results.put("rows", JSONConvertUtil.toJSONArray(cellFillFormList));
		
		Environment.setCurrentSystemName(systemName);
		return results.toString().getBytes("UTF-8");
	}

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				cellFillFormService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request)
			throws IOException {
		cellFillFormService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellFillFormQuery query = new CellFillFormQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellFillFormDomainFactory.processDataRequest(dataRequest);

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
		int total = cellFillFormService
				.getCellFillFormCountByQueryCriteria(query);
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

			//Map<String, User> userMap = IdentityFactory.getUserMap();
			List<CellFillForm> list = cellFillFormService
					.getCellFillFormsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellFillForm cellFillForm : list) {
					JSONObject rowJSON = cellFillForm.toJsonObject();
					rowJSON.put("id", cellFillForm.getId());
					rowJSON.put("cellFillFormId", cellFillForm.getId());
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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellFillFormQuery query = new CellFillFormQuery();
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
		int total = cellFillFormService
				.getCellFillFormCountByQueryCriteria(query);
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

			//Map<String, User> userMap = IdentityFactory.getUserMap();
			List<CellFillForm> list = cellFillFormService
					.getCellFillFormsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellFillForm cellFillForm : list) {
					JSONObject rowJSON = cellFillForm.toJsonObject();
					rowJSON.put("id", cellFillForm.getId());
					rowJSON.put("cellFillFormId", cellFillForm.getId());
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
	@Path("/saveCellFillForm")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellFillForm(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellFillForm cellFillForm = new CellFillForm();
		try {
			Tools.populate(cellFillForm, params);

			cellFillForm.setTopId(request.getParameter("topId"));
			cellFillForm.setIndexId(RequestUtils.getInt(request, "indexId"));
			cellFillForm.setTaskId(request.getParameter("taskId"));
			cellFillForm
					.setPfileFlag(RequestUtils.getInt(request, "pfileFlag"));
			cellFillForm
					.setFileDotFileId(request.getParameter("fileDotFileId"));
			cellFillForm.setName(request.getParameter("name"));
			cellFillForm.setChkNum(request.getParameter("chkNum"));
			cellFillForm.setListNo(RequestUtils.getInt(request, "listNo"));
			cellFillForm.setChkTotal(RequestUtils.getInt(request, "chkTotal"));
			cellFillForm
					.setChkResult(RequestUtils.getInt(request, "chkResult"));
			cellFillForm.setPfileId(request.getParameter("pfileId"));
			cellFillForm.setTempSave(RequestUtils.getInt(request, "tempSave"));
			cellFillForm.setUserId(request.getParameter("userId"));
			cellFillForm.setRefillFlag(RequestUtils.getInt(request,
					"refillFlag"));
			cellFillForm.setGroupId(RequestUtils.getInt(request, "groupId"));
			cellFillForm.setOldId(request.getParameter("oldId"));
			cellFillForm.setRoleId(RequestUtils.getInt(request, "roleId"));
			cellFillForm.setIsFinish(RequestUtils.getInt(request, "isFinish"));
			cellFillForm
					.setTypeTableName(request.getParameter("typeTableName"));
			cellFillForm.setTypeId(request.getParameter("typeId"));
			cellFillForm.setTypeIndexId(RequestUtils.getInt(request,
					"typeIndexId"));
			cellFillForm.setMainId(request.getParameter("mainId"));
			cellFillForm.setIntLastPage(RequestUtils.getInt(request,
					"intLastPage"));
			cellFillForm
					.setIntSheets(RequestUtils.getInt(request, "intSheets"));

			this.cellFillFormService.save(cellFillForm);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellFillFormService")
	public void setCellFillFormService(CellFillFormService cellFillFormService) {
		this.cellFillFormService = cellFillFormService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellFillForm cellFillForm = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			cellFillForm = cellFillFormService.getCellFillForm(request
					.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (cellFillForm != null) {
			result = cellFillForm.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellFillForm.getId());
			result.put("cellFillFormId", cellFillForm.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
