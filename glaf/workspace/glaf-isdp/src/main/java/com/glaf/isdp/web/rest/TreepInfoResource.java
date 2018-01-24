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
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.TreepInfo;
import com.glaf.isdp.query.TreepInfoQuery;
import com.glaf.isdp.service.HintListService;
import com.glaf.isdp.service.TreepInfoService;
import com.glaf.isdp.util.TreepInfoDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/treepInfo")
public class TreepInfoResource {
	protected static final Log logger = LogFactory
			.getLog(TreepInfoResource.class);

	protected TreepInfoService treepInfoService;
	
	protected HintListService hintListService;

	@GET
	@POST
	@Path("/getTreepInfoJSON")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getTreepInfoJSON(@Context HttpServletRequest request)
			throws IOException {
		Integer indexId = RequestUtils.getInteger(request, "indexId",null);
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", systemName);
		Environment.setCurrentSystemName(databaseCode);
		
		JSONArray result = new JSONArray();
		TreepInfoQuery query = new TreepInfoQuery();
		query.setNodeType("0");
		query.setTopId(1);
		query.setIntIsUse(1);
		query.setOrderBy("E.parent_id,E.listno");
		
		if(indexId!=null){
			TreepInfo treepinfo = treepInfoService.getTreepInfo(indexId);
			query.setIdLike(treepinfo.getId());
			query.setIndexIdNotEqual(treepinfo.getIndexId());
			query.setParentId(indexId.longValue());
		}
		
		// List<TreepInfo> list = treepInfoService.getAllWBSTreepInfos(query);
		// if(list!=null && list.size()>500){
		// list =
		// treepInfoService.getTreepInfoListByParentId(indexId==null?-1:indexId);
		// }
		
		List<TreepInfo> list = treepInfoService.getTreepInfoListByParentId(indexId==null?-1:indexId);
		for(TreepInfo model : list){
			JSONObject rowJSON = new JSONObject();
			rowJSON.put("indexName",model.getIndexName());
			rowJSON.put("indexId",model.getIndexId());
			rowJSON.put("finishInt", model.getFinishInt());
			rowJSON.put("finishString",model.getFinishString());
			rowJSON.put("intcellfinish", model.getIntCellFinish());
			rowJSON.put("intcellfinishString", model.getIntCellFinishString());
			rowJSON.put("intcellfinishIcon", model.getIntCellFinishIcon());
			rowJSON.put("bdate",DateUtils.getDate(model.getBdate()));
			rowJSON.put("edate",DateUtils.getDate(model.getEdate()));
			rowJSON.put("cell1",model.getCell1());
			rowJSON.put("cell2",model.getCell2());
			rowJSON.put("cell3",model.getCell3());
			rowJSON.put("intpfile1", model.getIntPfile1());
			rowJSON.put("intpfile2", model.getIntPfile2());
			rowJSON.put("intpfile3", model.getIntPfile3());
			rowJSON.put("strButtonStar",model.getStrButtonStar());
			rowJSON.put("parentId", model.getParentId());
			if(model.getChildrenCount()==null || model.getChildrenCount()==0){
				rowJSON.put("leaf", true);
			}
			
			rowJSON.put("uiProvider","col");
			rowJSON.put("cls", "master-task");
			rowJSON.put("iconCls","task-folder");
			result.add(rowJSON);
		}
		
		Environment.setCurrentSystemName(systemName);
		return result.toJSONString().getBytes("UTF-8");
	}
	
	@GET
	@POST
	@Path("/update")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public void update(@Context HttpServletRequest request)
			throws IOException {
		
	}
	
	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request)
			throws IOException {
		String rowIds = request.getParameter("indexIds");
		if (rowIds != null) {
			List<Integer> ids = StringTools.splitToInt(rowIds);
			if (ids != null && !ids.isEmpty()) {
				treepInfoService.deleteByIds(ids);
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
		treepInfoService.deleteById(RequestUtils.getInt(request, "indexId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreepInfoQuery query = new TreepInfoQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		TreepInfoDomainFactory.processDataRequest(dataRequest);

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
		int total = treepInfoService.getTreepInfoCountByQueryCriteria(query);
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
			List<TreepInfo> list = treepInfoService
					.getTreepInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreepInfo treepInfo : list) {
					JSONObject rowJSON = treepInfo.toJsonObject();
					rowJSON.put("id", treepInfo.getId());
					rowJSON.put("treepInfoId", treepInfo.getId());
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
		TreepInfoQuery query = new TreepInfoQuery();
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
		int total = treepInfoService.getTreepInfoCountByQueryCriteria(query);
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
			List<TreepInfo> list = treepInfoService
					.getTreepInfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreepInfo treepInfo : list) {
					JSONObject rowJSON = treepInfo.toJsonObject();
					rowJSON.put("id", treepInfo.getId());
					rowJSON.put("treepInfoId", treepInfo.getId());
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
	@Path("/saveTreepInfo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveTreepInfo(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreepInfo treepInfo = new TreepInfo();
		try {
			Tools.populate(treepInfo, params);

			treepInfo.setId(request.getParameter("id"));
			treepInfo.setNum(request.getParameter("num"));
			treepInfo.setTopId(RequestUtils.getInt(request, "topId"));
			treepInfo.setParentId(RequestUtils.getInt(request, "parentId"));
			treepInfo.setIndexName(request.getParameter("indexName"));
			treepInfo.setLevel(RequestUtils.getInt(request, "level"));
			treepInfo.setNodeType(request.getParameter("nodeType"));
			treepInfo.setFromId(RequestUtils.getInt(request, "fromId"));
			treepInfo.setPartid(RequestUtils.getInt(request, "partid"));
			treepInfo.setShowId(RequestUtils.getInt(request, "showId"));
			treepInfo.setSindexName(request.getParameter("sindexName"));
			treepInfo.setFileNum(RequestUtils.getInt(request, "fileNum"));
			treepInfo.setFileNum0(RequestUtils.getInt(request, "fileNum0"));
			treepInfo.setFileNum1(RequestUtils.getInt(request, "fileNum1"));
			treepInfo.setFilenum2(RequestUtils.getInt(request, "filenum2"));
			treepInfo.setProjType(request.getParameter("projType"));
			treepInfo.setCid(request.getParameter("cid"));
			treepInfo.setDwid(RequestUtils.getInt(request, "dwid"));
			treepInfo.setFxid(RequestUtils.getInt(request, "fxid"));
			treepInfo.setFbid(RequestUtils.getInt(request, "fbid"));
			treepInfo.setJid(request.getParameter("jid"));
			treepInfo.setFlid(request.getParameter("flid"));
			treepInfo.setTopNode(request.getParameter("topNode"));
			treepInfo.setNodeIco(RequestUtils.getInt(request, "nodeIco"));
			treepInfo.setOutFlag(request.getParameter("outFlag"));
			treepInfo.setInFlag(request.getParameter("inFlag"));
			treepInfo.setPassword(request.getParameter("password"));
			treepInfo.setListNum(request.getParameter("listNum"));
			treepInfo.setWcompany(request.getParameter("wcompany"));
			treepInfo.setListNo(RequestUtils.getInt(request, "listNo"));
			treepInfo.setUserNmu(request.getParameter("userNmu"));
			treepInfo.setIndexIdOld(RequestUtils.getInt(request, "indexIdOld"));
			treepInfo.setPindexId(RequestUtils.getInt(request, "pindexId"));
			treepInfo.setFinishInt(RequestUtils.getInt(request, "finishInt"));
			treepInfo.setTypeTableName(request.getParameter("typeTableName"));
			treepInfo.setTreedListStr(request.getParameter("treedListStr"));
			treepInfo.setPfilesIndex(RequestUtils
					.getInt(request, "pfilesIndex"));
			treepInfo.setBdate(RequestUtils.getDate(request, "bdate"));
			treepInfo.setEdate(RequestUtils.getDate(request, "edate"));
			treepInfo.setWbsIndex(RequestUtils.getInt(request, "wbsIndex"));
			treepInfo.setBdates(RequestUtils.getDate(request, "bdates"));
			treepInfo.setEdates(RequestUtils.getDate(request, "edates"));
			treepInfo.setTypeId(request.getParameter("typeId"));
			treepInfo.setCell1(RequestUtils.getInt(request, "cell1"));
			treepInfo.setCell2(RequestUtils.getInt(request, "cell2"));
			treepInfo.setCell3(RequestUtils.getInt(request, "cell3"));
			treepInfo.setStrFileLocate(request.getParameter("strFileLocate"));
			treepInfo.setIntPfile1(RequestUtils.getInt(request, "intPfile1"));
			treepInfo.setIntPfile2(RequestUtils.getInt(request, "intPfile2"));
			treepInfo.setIntPfile3(RequestUtils.getInt(request, "intPfile3"));
			treepInfo.setIntCellFinish(RequestUtils.getInt(request,
					"intCellFinish"));
			treepInfo.setSysId(request.getParameter("sysId"));
			treepInfo.setIndexIn(RequestUtils.getInt(request, "indexIn"));
			treepInfo.setStrButtonStar(request.getParameter("strButtonStar"));
			treepInfo.setIntIsUse(RequestUtils.getInt(request, "intIsUse"));
			treepInfo.setWbsIndexIn(RequestUtils.getInt(request, "wbsIndexIn"));
			treepInfo.setUindexId(RequestUtils.getInt(request, "uindexId"));
			treepInfo.setLisnoWBS(RequestUtils.getInt(request, "lisnoWBS"));
			treepInfo.setSysIdAdd(request.getParameter("sysIdAdd"));
			treepInfo.setIndexInAdd(RequestUtils.getInt(request, "indexInAdd"));

			this.treepInfoService.save(treepInfo);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.treepInfoService")
	public void setTreepInfoService(TreepInfoService treepInfoService) {
		this.treepInfoService = treepInfoService;
	}
	@javax.annotation.Resource(name = "com.glaf.isdp.service.hintListService")
	public void setHintListService(HintListService hintListService) {
		this.hintListService = hintListService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		TreepInfo treepInfo = null;
		if (StringUtils.isNotEmpty(request.getParameter("indexId"))) {
			treepInfo = treepInfoService.getTreepInfo(RequestUtils.getInt(
					request, "indexId"));
		}
		JSONObject result = new JSONObject();
		if (treepInfo != null) {
			result = treepInfo.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", treepInfo.getId());
			result.put("treepInfoId", treepInfo.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
