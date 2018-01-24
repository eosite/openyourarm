package com.glaf.teim.web.rest;

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
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.teim.domain.EimServerDataImp;
import com.glaf.teim.domain.EimServerTmp;
import com.glaf.teim.domain.EimServerTmpTree;
import com.glaf.teim.query.EimServerDataImpQuery;
import com.glaf.teim.service.EimServerDataImpService;
import com.glaf.teim.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/teim/dataimp")
public class EimServerDataImpResource {
	protected static final Log logger = LogFactory.getLog(EimServerDataImpResource.class);

	protected EimServerDataImpService eimServerDataImpService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				eimServerDataImpService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		eimServerDataImpService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerDataImpQuery query = new EimServerDataImpQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		EimServerDataImpDomainFactory.processDataRequest(dataRequest);

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
		int total = eimServerDataImpService.getEimServerDataImpCountByQueryCriteria(query);
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
			List<EimServerDataImp> list = eimServerDataImpService.getEimServerDataImpsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EimServerDataImp eimServerDataImp : list) {
					JSONObject rowJSON = eimServerDataImp.toJsonObject();
					rowJSON.put("id", eimServerDataImp.getId());
					rowJSON.put("eimServerDataImpId", eimServerDataImp.getId());
					rowJSON.put("startIndex", ++start);
					if (new Integer(1).equals(eimServerDataImp.getDeleteFlag())) {
						rowJSON.put("deleteFlag", "失效");
					} else {
						rowJSON.put("deleteFlag", "有效");
					}
					if (new Integer(1).equals(eimServerDataImp.getIncrementFlag())) {
						rowJSON.put("incrementFlag", "增量");
					} else {
						rowJSON.put("incrementFlag", "全量");
					}
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
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerDataImpQuery query = new EimServerDataImpQuery();
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
		int total = eimServerDataImpService.getEimServerDataImpCountByQueryCriteria(query);
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
			List<EimServerDataImp> list = eimServerDataImpService.getEimServerDataImpsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EimServerDataImp eimServerDataImp : list) {
					JSONObject rowJSON = eimServerDataImp.toJsonObject();
					rowJSON.put("id", eimServerDataImp.getId());
					rowJSON.put("eimServerDataImpId", eimServerDataImp.getId());
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
	@Path("/saveEimServerDataImp")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveEimServerDataImp(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerDataImp eimServerDataImp = null;
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		try {
			Tools.populate(eimServerDataImp, params);
			String id = request.getParameter("id");
			if (StringUtils.isEmpty(id)) {
				eimServerDataImp = new EimServerDataImp();
				eimServerDataImp.setCreateBy(actorId);
				eimServerDataImp.setCreateTime(new Date());
			} else {
				eimServerDataImp = eimServerDataImpService.getEimServerDataImp(id);
				eimServerDataImp.setUpdateBy(actorId);
				eimServerDataImp.setUpdateTime(new Date());
			}
			eimServerDataImp.setName(request.getParameter("name"));
			eimServerDataImp.setAppId(request.getParameter("appId"));
			eimServerDataImp.setTmpId(request.getParameter("tmpId"));
			eimServerDataImp.setEmptyTable(RequestUtils.getInt(request, "emptyTable"));
			eimServerDataImp.setPreSql(request.getParameter("preSql"));
			eimServerDataImp.setIncrementFlag(RequestUtils.getInt(request, "incrementFlag"));
			eimServerDataImp.setTargetDatabase(request.getParameter("targetDatabase"));
			eimServerDataImp.setTargetTable(request.getParameter("targetTable"));
			eimServerDataImp.setCreateBy(request.getParameter("createBy"));
			eimServerDataImp.setCreateTime(RequestUtils.getDate(request, "createTime"));
			eimServerDataImp.setUpdateBy(request.getParameter("updateBy"));
			eimServerDataImp.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
			eimServerDataImp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

			this.eimServerDataImpService.save(eimServerDataImp);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.teim.service.eimServerDataImpService")
	public void setEimServerDataImpService(EimServerDataImpService eimServerDataImpService) {
		this.eimServerDataImpService = eimServerDataImpService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		EimServerDataImp eimServerDataImp = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			eimServerDataImp = eimServerDataImpService.getEimServerDataImp(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (eimServerDataImp != null) {
			result = eimServerDataImp.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", eimServerDataImp.getId());
			result.put("eimServerDataImpId", eimServerDataImp.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/all")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] all(@Context HttpServletRequest request) throws IOException {
		JSONObject jsonObject = new JSONObject();
		List<Map> dataImpData = eimServerDataImpService.getEimServerDataImpData();
		if (dataImpData != null && dataImpData.size() > 0) {
			String result = jsonObject.toJSONString(dataImpData);
			return result.getBytes("UTF-8");
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
}
