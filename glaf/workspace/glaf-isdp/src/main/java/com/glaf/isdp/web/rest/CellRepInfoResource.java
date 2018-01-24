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
import com.glaf.isdp.domain.CellRepInfo;
import com.glaf.isdp.query.CellRepInfoQuery;
import com.glaf.isdp.service.CellRepInfoService;
import com.glaf.isdp.util.CellRepInfoDomainFactory;
import com.glaf.isdp.util.CellRepInfoJsonFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/cellRepInfo")
public class CellRepInfoResource {
	protected static final Log logger = LogFactory
			.getLog(CellRepInfoResource.class);

	protected CellRepInfoService cellRepInfoService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("listIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				cellRepInfoService.deleteByIds(ids);
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
		cellRepInfoService.deleteById(request.getParameter("listId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellRepInfoQuery query = new CellRepInfoQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		CellRepInfoDomainFactory.processDataRequest(dataRequest);

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
		int total = cellRepInfoService
				.getCellRepInfoCountByQueryCriteria(query);
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
			List<CellRepInfo> list = cellRepInfoService
					.getCellRepInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellRepInfo cellRepInfo : list) {
					JSONObject rowJSON = cellRepInfo.toJsonObject();
					rowJSON.put("id", cellRepInfo.getListId());
					rowJSON.put("cellRepInfoId", cellRepInfo.getListId());
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
		CellRepInfoQuery query = new CellRepInfoQuery();
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
		int total = cellRepInfoService
				.getCellRepInfoCountByQueryCriteria(query);
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
			List<CellRepInfo> list = cellRepInfoService
					.getCellRepInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CellRepInfo cellRepInfo : list) {
					JSONObject rowJSON = cellRepInfo.toJsonObject();
					rowJSON.put("id", cellRepInfo.getListId());
					rowJSON.put("cellRepInfoId", cellRepInfo.getListId());
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
	@Path("/saveCellRepInfo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveCellRepInfo(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		CellRepInfo cellRepInfo = new CellRepInfo();
		try {
			Tools.populate(cellRepInfo, params);

			cellRepInfo.setIndexId(request.getParameter("indexId"));
			cellRepInfo.setFrmType(request.getParameter("frmType"));
			cellRepInfo.setIsSystem(request.getParameter("isSystem"));
			cellRepInfo.setFname(request.getParameter("fname"));
			cellRepInfo.setDname(request.getParameter("dname"));
			cellRepInfo.setDtype(request.getParameter("dtype"));
			cellRepInfo.setShowType(request.getParameter("showType"));
			cellRepInfo.setStrLen(RequestUtils.getInt(request, "strLen"));
			cellRepInfo.setForm(request.getParameter("form"));
			cellRepInfo.setInType(request.getParameter("inType"));
			cellRepInfo.setHintID(request.getParameter("hintID"));
			cellRepInfo.setListNo(RequestUtils.getInt(request, "listNo"));
			cellRepInfo.setZtype(request.getParameter("ztype"));
			cellRepInfo.setIsMustFill(request.getParameter("isMustFill"));
			cellRepInfo.setIsListShow(request.getParameter("isListShow"));
			cellRepInfo.setListWeigth(RequestUtils
					.getInt(request, "listWeigth"));
			cellRepInfo.setPanid(request.getParameter("panid"));
			cellRepInfo.setIsAllWidth(request.getParameter("isAllWidth"));
			cellRepInfo.setIstName(request.getParameter("istName"));
			cellRepInfo.setFileDotFileId(request.getParameter("fileDotFileId"));
			cellRepInfo.setDataPoint(RequestUtils.getInt(request, "dataPoint"));
			cellRepInfo.setDefaultValue(request.getParameter("defaultValue"));
			cellRepInfo.setIsSubTable(request.getParameter("isSubTable"));
			cellRepInfo.setIsDataOnly(request.getParameter("isDataOnly"));
			cellRepInfo.setCheckType(RequestUtils.getInt(request, "checkType"));
			cellRepInfo.setOrderId(request.getParameter("orderId"));
			cellRepInfo.setCellType(RequestUtils.getInt(request, "cellType"));
			cellRepInfo.setOstTableName(request.getParameter("ostTableName"));
			cellRepInfo.setOstRow(RequestUtils.getInt(request, "ostRow"));
			cellRepInfo.setOstCol(RequestUtils.getInt(request, "ostCol"));
			cellRepInfo.setHintList(request.getParameter("hintList"));
			cellRepInfo.setOstCellId(request.getParameter("ostCellId"));
			cellRepInfo.setOldDName(request.getParameter("oldDName"));
			cellRepInfo.setOstCellName(request.getParameter("ostCellName"));
			cellRepInfo.setOstFormula(request.getParameter("ostFormula"));
			cellRepInfo.setOstColor(RequestUtils.getInt(request, "ostColor"));
			cellRepInfo.setOstSumType(RequestUtils
					.getInt(request, "ostSumType"));
			cellRepInfo.setDataTableName(request.getParameter("dataTableName"));
			cellRepInfo.setDataFieldName(request.getParameter("dataFieldName"));
			cellRepInfo.setOrderCon(RequestUtils.getInt(request, "orderCon"));
			cellRepInfo.setConNum(RequestUtils.getInt(request, "conNum"));
			cellRepInfo.setNodeIndex(RequestUtils.getInt(request, "nodeIndex"));
			cellRepInfo.setTypeId(request.getParameter("typeId"));
			cellRepInfo.setUserIndex(request.getParameter("userIndex"));
			cellRepInfo.setOrderIndex(RequestUtils
					.getInt(request, "orderIndex"));
			cellRepInfo.setParentDName(request.getParameter("parentDName"));
			cellRepInfo.setSysAuto(RequestUtils.getInt(request, "sysAuto"));
			cellRepInfo.setOrderIndexF(RequestUtils.getInt(request,
					"orderIndexF"));
			cellRepInfo.setOrderIdF(request.getParameter("orderIdF"));
			cellRepInfo.setOrderConF(RequestUtils.getInt(request, "orderConF"));
			cellRepInfo.setIsPrintValue(RequestUtils.getInt(request,
					"isPrintValue"));
			cellRepInfo.setIsShowValueOnLast(RequestUtils.getInt(request,
					"isShowValueOnLast"));
			cellRepInfo.setIsBankRoundType(RequestUtils.getInt(request,
					"isBankRoundType"));
			cellRepInfo.setFloatBankRound(RequestUtils.getDouble(request,
					"floatBankRound"));
			cellRepInfo.setIntUseWBSPlace(RequestUtils.getInt(request,
					"intUseWBSPlace"));

			this.cellRepInfoService.save(cellRepInfo);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name="com.glaf.isdp.service.cellRepInfoService")
	public void setCellRepInfoService(CellRepInfoService cellRepInfoService) {
		this.cellRepInfoService = cellRepInfoService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		CellRepInfo cellRepInfo = null;
		if (StringUtils.isNotEmpty(request.getParameter("listId"))) {
			cellRepInfo = cellRepInfoService.getCellRepInfo(request
					.getParameter("listId"));
		}
		JSONObject result = new JSONObject();
		if (cellRepInfo != null) {
			result = cellRepInfo.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", cellRepInfo.getListId());
			result.put("cellRepInfoId", cellRepInfo.getListId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	@GET
	@POST
	@Path("/getCellRepInfoByFileDotFileId")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getCellRepInfoByFileDotFileId(@Context HttpServletRequest request) throws IOException {
		
		String fileDotFileId = RequestUtils.getString(request, "fileDotFileId");
		String systemName = RequestUtils.getString(request, "systemName", Environment.DEFAULT_SYSTEM_NAME);
		Environment.setCurrentSystemName(systemName);
		
		CellRepInfoQuery query = new CellRepInfoQuery();
		query.setFileDotFileId(fileDotFileId);
		List<CellRepInfo> list = cellRepInfoService.list(query);
		
		JSONArray result = new JSONArray();
		try {
			for(CellRepInfo model : list){
				JSONObject jobject = CellRepInfoJsonFactory.toJsonObject(model);
				jobject.put("fieldName", model.getFname());
				jobject.put("fieldId", model.getListId());
				jobject.put("fieldValue", model.getDname());
				result.add(jobject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toJSONString().getBytes("UTF-8");
	}
}
