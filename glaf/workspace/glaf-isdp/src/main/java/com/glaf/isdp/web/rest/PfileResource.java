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
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.Pfile;
import com.glaf.isdp.query.PfileQuery;
import com.glaf.isdp.service.PfileService;
import com.glaf.isdp.util.PfileDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/pfile")
public class PfileResource {
	protected static final Log logger = LogFactory.getLog(PfileResource.class);

	protected PfileService pfileService;

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
				pfileService.deleteByIds(ids);
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
		pfileService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PfileQuery query = new PfileQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		PfileDomainFactory.processDataRequest(dataRequest);

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
		int total = pfileService.getPfileCountByQueryCriteria(query);
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
			List<Pfile> list = pfileService.getPfilesByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Pfile pfile : list) {
					JSONObject rowJSON = pfile.toJsonObject();
					rowJSON.put("id", pfile.getId());
					rowJSON.put("pfileId", pfile.getId());
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
		PfileQuery query = new PfileQuery();
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
		int total = pfileService.getPfileCountByQueryCriteria(query);
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
			List<Pfile> list = pfileService.getPfilesByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Pfile pfile : list) {
					JSONObject rowJSON = pfile.toJsonObject();
					rowJSON.put("id", pfile.getId());
					rowJSON.put("pfileId", pfile.getId());
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
	@Path("/savePfile")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] savePfile(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Pfile pfile = new Pfile();
		try {
			Tools.populate(pfile, params);

			pfile.setEfileNum(RequestUtils.getInt(request, "efileNum"));
			pfile.setListNum(request.getParameter("listNum"));
			pfile.setListId(request.getParameter("listId"));
			pfile.setPid(RequestUtils.getInt(request, "pid"));
			pfile.setProjId(RequestUtils.getInt(request, "projId"));
			pfile.setDwid(RequestUtils.getInt(request, "dwid"));
			pfile.setFbid(RequestUtils.getInt(request, "fbid"));
			pfile.setFxid(RequestUtils.getInt(request, "fxid"));
			pfile.setJid(request.getParameter("jid"));
			pfile.setFlid(request.getParameter("flid"));
			pfile.setTopNode(request.getParameter("topNode"));
			pfile.setTopNodeM(request.getParameter("topNodeM"));
			pfile.setVid(request.getParameter("vid"));
			pfile.setOldVid(request.getParameter("oldVid"));
			pfile.setVisFlag(request.getParameter("visFlag"));
			pfile.setListNo(RequestUtils.getInt(request, "listNo"));
			pfile.setFilingFlag(request.getParameter("filingFlag"));
			pfile.setSaveFlag(request.getParameter("saveFlag"));
			pfile.setOpenFlag(request.getParameter("openFlag"));
			pfile.setCheckupFlag(request.getParameter("checkupFlag"));
			pfile.setFinishFlag(request.getParameter("finishFlag"));
			pfile.setFromID(request.getParameter("fromID"));
			pfile.setTname(request.getParameter("tname"));
			pfile.setDuty(request.getParameter("duty"));
			pfile.setTagnum(request.getParameter("tagnum"));
			pfile.setFileNum(request.getParameter("fileNum"));
			pfile.setThematic(request.getParameter("thematic"));
			pfile.setCtime(RequestUtils.getDate(request, "ctime"));
			pfile.setPageNo(request.getParameter("pageNo"));
			pfile.setLevel(request.getParameter("Level"));
			pfile.setPage(RequestUtils.getInt(request, "page"));
			pfile.setFileType(request.getParameter("fileType"));
			pfile.setFontsNum(request.getParameter("fontsNum"));
			pfile.setSaveTime(request.getParameter("saveTime"));
			pfile.setCompany(request.getParameter("company"));
			pfile.setYear(RequestUtils.getInt(request, "year"));
			pfile.setFileAtt(request.getParameter("fileAtt"));
			pfile.setAnnotations(request.getParameter("annotations"));
			pfile.setCarrierType(request.getParameter("carrierType"));
			pfile.setSummary(request.getParameter("summary"));
			pfile.setPtext(request.getParameter("ptext"));
			pfile.setCarrieru(request.getParameter("carrieru"));
			pfile.setGlideNum(request.getParameter("glideNum"));
			pfile.setEfile(request.getParameter("efile"));
			pfile.setFtime(RequestUtils.getDate(request, "ftime"));
			pfile.setTotalNum(request.getParameter("totalNum"));
			pfile.setInputMan(request.getParameter("inputMan"));
			pfile.setEtime(RequestUtils.getDate(request, "etime"));
			pfile.setDotNum(request.getParameter("dotNum"));
			pfile.setPicNum(request.getParameter("picNum"));
			pfile.setRecNum(request.getParameter("recNum"));
			pfile.setTotal(RequestUtils.getInt(request, "total"));
			pfile.setPageType(request.getParameter("pageType"));
			pfile.setIsCom(request.getParameter("isCom"));
			pfile.setIsLocate(request.getParameter("isLocate"));
			pfile.setWcompany(request.getParameter("wcompany"));
			pfile.setWcompanyID(request.getParameter("wcompanyID"));
			pfile.setSendFlag(request.getParameter("sendFlag"));
			pfile.setLcontent(request.getParameter("lcontent"));
			pfile.setLcompany(request.getParameter("lcompany"));
			pfile.setLman(request.getParameter("lman"));
			pfile.setLlen(request.getParameter("llen"));
			pfile.setLdate(RequestUtils.getDate(request, "ldate"));
			pfile.setJconten(request.getParameter("jconten"));
			pfile.setJplace(request.getParameter("jplace"));
			pfile.setJman(request.getParameter("jman"));
			pfile.setJback(request.getParameter("jback"));
			pfile.setJactor(request.getParameter("jactor"));
			pfile.setJnum(request.getParameter("jnum"));
			pfile.setJbnum(request.getParameter("jbnum"));
			pfile.setTnum(request.getParameter("tnum"));
			pfile.setTzoom(request.getParameter("tzoom"));
			pfile.setTflag(request.getParameter("tflag"));
			pfile.setTdesigner(request.getParameter("tdesigner"));
			pfile.setTviewer(request.getParameter("tviewer"));
			pfile.setTassessor(request.getParameter("tassessor"));
			pfile.setTvision(request.getParameter("tvision"));
			pfile.setClistNo(RequestUtils.getInt(request, "clistNo"));
			pfile.setCpageNo(request.getParameter("cpageNo"));
			pfile.setVnum(request.getParameter("vnum"));
			pfile.setCvnum(request.getParameter("cvnum"));
			pfile.setTreedListStr(request.getParameter("treedListStr"));
			pfile.setCtimeEnd(RequestUtils.getDate(request, "ctimeEnd"));
			pfile.setProjIndex(RequestUtils.getInt(request, "projIndex"));
			pfile.setTreeParent(RequestUtils.getInt(request, "treeParent"));
			pfile.setTreeList(RequestUtils.getInt(request, "treeList"));

			this.pfileService.save(pfile);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.pfileService")
	public void setPfileService(PfileService pfileService) {
		this.pfileService = pfileService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		Pfile pfile = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			pfile = pfileService.getPfile(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (pfile != null) {
			result = pfile.toJsonObject();
			//Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", pfile.getId());
			result.put("pfileId", pfile.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
