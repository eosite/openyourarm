package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.query.FieldInterfaceQuery;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.isdp.domain.CellUTableTree;
import com.glaf.isdp.domain.Pinfo;
import com.glaf.isdp.domain.TreepName;
import com.glaf.isdp.query.CellUTableTreeQuery;
import com.glaf.isdp.query.PinfoQuery;
import com.glaf.isdp.service.CellUTableTreeService;
import com.glaf.isdp.service.PinfoService;
import com.glaf.isdp.service.TreepNameService;
import com.glaf.isdp.util.PinfoDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/isdp/wbs/pinfo")
public class PinfoResource {
	protected static final Log logger = LogFactory.getLog(PinfoResource.class);

	private CellUTableTreeService cellUTableTreeService;

	private TreepNameService treepNameService;

	private IFieldInterfaceService fieldInterfaceService;

	private PinfoService pinfoService;

	private int getTableType() {
		return 11;
	}

	private int getDomainIndexId() {
		return 2;
	}

	/**
	 * 获取工程项目信息TAB
	 * 
	 * @param domainIndexId 项目域ID
	 * @param tableType
	 * @return
	 */
	private List<CellUTableTree> getCellUTableTreeList(int domainIndexId, int tableType) {
		List<CellUTableTree> cellUTableTreeList = new ArrayList<CellUTableTree>();

		CellUTableTreeQuery query = new CellUTableTreeQuery();
		query.setTableType(tableType);
		query.setBusiessId("pinfo");
		query.setDomainIndex(domainIndexId);
		query.setSqlCondition(" and parent_id<>-1");
		query.setOrderBy("listno");
		cellUTableTreeList = cellUTableTreeService.list(query);

		if (cellUTableTreeList == null || cellUTableTreeList.size() == 0) {
			query.setSqlCondition(null);
			query.setDomainIndex(null);
			query.setIndexName("工程项目");
			cellUTableTreeList = cellUTableTreeService.list(query);
			cellUTableTreeList.get(0).setIndexName(cellUTableTreeList.get(0).getIndexName() + "著录");
		}
		return cellUTableTreeList;
	}

	@POST
	@Path("/update")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] update(@Context HttpServletRequest request) throws IOException {
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", systemName);
		Environment.setCurrentSystemName(databaseCode);

		Map<String, Object> paramsMap = RequestUtils.getParameterMap(request);
		TreepName treepName = treepNameService.getTreepNameByDomainIndexId(2);
		if (paramsMap.get("treepNameIndexName") != null) {
			treepName.setIndexName(paramsMap.get("treepNameIndexName").toString());
		} else {
			treepName.setIndexName("");
		}
		if (paramsMap.get("treepNameNum") != null) {
			treepName.setNum(paramsMap.get("treepNameNum").toString());
		} else {
			treepName.setNum("");
		}
		treepNameService.save(treepName);

		List<CellUTableTree> cellUTableTreeList = this.getCellUTableTreeList(this.getDomainIndexId(),
				this.getTableType());
		request.setAttribute("cellUTableTreeList", cellUTableTreeList);

		List<Integer> listShowIndexIds = new ArrayList<Integer>();
		listShowIndexIds.add(-99);
		for (CellUTableTree model : cellUTableTreeList) {
			listShowIndexIds.add(model.getIndexId());
		}

		FieldInterfaceQuery interfaceQuery = new FieldInterfaceQuery();
		interfaceQuery.setFrmtype("pinfo");
		interfaceQuery.setListShowIndexIds(listShowIndexIds);
		List<FieldInterface> interfaceList = fieldInterfaceService.getInterfaceListByQuery(interfaceQuery);

		pinfoService.update(interfaceList, paramsMap);

		Environment.setCurrentSystemName(systemName);
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				pinfoService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		pinfoService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		PinfoQuery query = new PinfoQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		PinfoDomainFactory.processDataRequest(dataRequest);

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
		int total = pinfoService.getPinfoCountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<Pinfo> list = pinfoService.getPinfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Pinfo pinfo : list) {
					JSONObject rowJSON = pinfo.toJsonObject();
					rowJSON.put("id", pinfo.getId());
					rowJSON.put("pinfoId", pinfo.getId());
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
		PinfoQuery query = new PinfoQuery();
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
		int total = pinfoService.getPinfoCountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<Pinfo> list = pinfoService.getPinfosByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Pinfo pinfo : list) {
					JSONObject rowJSON = pinfo.toJsonObject();
					rowJSON.put("id", pinfo.getId());
					rowJSON.put("pinfoId", pinfo.getId());
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
	@Path("/savePinfo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] savePinfo(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Pinfo pinfo = new Pinfo();
		try {
			Tools.populate(pinfo, params);

			pinfo.setIndexId(RequestUtils.getInt(request, "indexId"));
			pinfo.setItemNum(request.getParameter("itemNum"));
			pinfo.setDtag(request.getParameter("dtag"));
			pinfo.setFtag(request.getParameter("ftag"));
			pinfo.setName(request.getParameter("name"));
			pinfo.setAllName(request.getParameter("allName"));
			pinfo.setBcompany(request.getParameter("bcompany"));
			pinfo.setCcompany(request.getParameter("ccompany"));
			pinfo.setDcompany(request.getParameter("dcompany"));
			pinfo.setConCompany(request.getParameter("conCompany"));
			pinfo.setIcompany(request.getParameter("icompany"));
			pinfo.setCmark(request.getParameter("cmark"));
			pinfo.setPmark(request.getParameter("pmark"));
			pinfo.setTpmark(request.getParameter("tpmark"));
			pinfo.setConMark(request.getParameter("conMark"));
			pinfo.setMapNum(request.getParameter("mapNum"));
			pinfo.setConStart(request.getParameter("conStart"));
			pinfo.setConEnd(request.getParameter("conEnd"));
			pinfo.setTotalLen(RequestUtils.getDouble(request, "totalLen"));
			pinfo.setStartDate(RequestUtils.getDate(request, "startDate"));
			pinfo.setEndDate(RequestUtils.getDate(request, "endDate"));
			pinfo.setCost(RequestUtils.getDouble(request, "cost"));
			pinfo.setBalance(RequestUtils.getDouble(request, "balance"));
			pinfo.setPmannager(request.getParameter("pmannager"));
			pinfo.setEnginee(request.getParameter("enginee"));
			pinfo.setOwner(request.getParameter("owner"));
			pinfo.setPlanDate(RequestUtils.getDate(request, "planDate"));
			pinfo.setHintDay(RequestUtils.getInt(request, "hintDay"));
			pinfo.setDuty(request.getParameter("duty"));
			pinfo.setRemoveDate(RequestUtils.getDate(request, "removeDate"));
			pinfo.setRemoveDuty(request.getParameter("removeDuty"));
			pinfo.setRemoveFile(request.getParameter("removeFile"));
			pinfo.setPartProj(request.getParameter("partProj"));
			pinfo.setCnum(request.getParameter("cnum"));
			pinfo.setConcompany2(request.getParameter("concompany2"));
			pinfo.setIcompany2(request.getParameter("icompany2"));
			pinfo.setMileAge(RequestUtils.getDouble(request, "mileAge"));
			pinfo.setPlace(request.getParameter("place"));
			pinfo.setPlace1(RequestUtils.getDouble(request, "place1"));
			pinfo.setPlace2(RequestUtils.getDouble(request, "place2"));
			pinfo.setPlace3(RequestUtils.getDouble(request, "place3"));
			pinfo.setPlace4(RequestUtils.getDouble(request, "place4"));
			pinfo.setSetPlace(request.getParameter("setPlace"));
			pinfo.setSetTemp(request.getParameter("setTemp"));
			pinfo.setBdNum(request.getParameter("bdNum"));
			pinfo.setDtNum(request.getParameter("dtNum"));
			pinfo.setPinfoUser2(request.getParameter("pinfoUser2"));
			pinfo.setPinfoUser3(RequestUtils.getDouble(request, "pinfoUser3"));
			pinfo.setPinfoUser4(RequestUtils.getDouble(request, "pinfoUser4"));
			pinfo.setPinfoUser5(request.getParameter("pinfoUser5"));
			pinfo.setPinfoUser6(RequestUtils.getInt(request, "pinfoUser6"));
			pinfo.setPinfoUser7(RequestUtils.getDouble(request, "pinfoUser7"));
			pinfo.setPinfoUser8(RequestUtils.getDouble(request, "pinfoUser8"));
			pinfo.setPinfoUser9(request.getParameter("pinfoUser9"));

			this.pinfoService.save(pinfo);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		Pinfo pinfo = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			pinfo = pinfoService.getPinfo(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (pinfo != null) {
			result = pinfo.toJsonObject();
			// Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", pinfo.getId());
			result.put("pinfoId", pinfo.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@Resource(name = "com.glaf.isdp.service.cellUTableTreeService")
	public void setCellUTableTreeService(CellUTableTreeService cellUTableTreeService) {
		this.cellUTableTreeService = cellUTableTreeService;
	}

	@Resource(name = "com.glaf.isdp.service.treepNameService")
	public void setTreepNameService(TreepNameService treepNameService) {
		this.treepNameService = treepNameService;
	}

	@Resource
	public void setFieldInterfaceService(IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

	@Resource(name = "com.glaf.isdp.service.pinfoService")
	public void setPinfoService(PinfoService pinfoService) {
		this.pinfoService = pinfoService;
	}

}
