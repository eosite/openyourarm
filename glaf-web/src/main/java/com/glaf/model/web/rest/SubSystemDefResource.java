package com.glaf.model.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.activiti.engine.RepositoryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.model.domain.SubSystemDef;
import com.glaf.model.query.SubSystemDefQuery;
import com.glaf.model.service.SubSystemDefService;
import com.glaf.model.util.SubSystemDefDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/modeling/subSystemDef")
public class SubSystemDefResource {
	protected static final Log logger = LogFactory.getLog(SubSystemDefResource.class);

	protected SubSystemDefService subSystemDefService;
	protected RepositoryService repositoryService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("subSysIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				subSystemDefService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		subSystemDefService.deleteById(RequestUtils.getString(request, "subSysId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SubSystemDefQuery query = new SubSystemDefQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		SubSystemDefDomainFactory.processDataRequest(dataRequest);

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
		int total = subSystemDefService.getSubSystemDefCountByQueryCriteria(query);
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
			List<SubSystemDef> list = subSystemDefService.getSubSystemDefsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SubSystemDef subSystemDef : list) {
					JSONObject rowJSON = subSystemDef.toJsonObject();
					rowJSON.put("id", subSystemDef.getSubSysId());
					rowJSON.put("subSystemDefId", subSystemDef.getSubSysId());
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
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SubSystemDefQuery query = new SubSystemDefQuery();
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
		int total = subSystemDefService.getSubSystemDefCountByQueryCriteria(query);
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
			List<SubSystemDef> list = subSystemDefService.getSubSystemDefsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SubSystemDef subSystemDef : list) {
					JSONObject rowJSON = subSystemDef.toJsonObject();
					rowJSON.put("id", subSystemDef.getSubSysId());
					rowJSON.put("subSystemDefId", subSystemDef.getSubSysId());
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
	@Path("/saveSubSystemDef")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveSubSystemDef(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SubSystemDef subSystemDef = new SubSystemDef();
		try {
			Tools.populate(subSystemDef, params);

			subSystemDef.setSysId(RequestUtils.getString(request, "sysId"));
			subSystemDef.setLevel(RequestUtils.getInt(request, "level"));
			subSystemDef.setCode(request.getParameter("code"));
			subSystemDef.setName(request.getParameter("name"));
			subSystemDef.setDesc(request.getParameter("desc"));
			subSystemDef.setParentId_(RequestUtils.getString(request, "parentId_"));
			subSystemDef.setTreeId(request.getParameter("treeId"));
			subSystemDef.setCreateBy(request.getParameter("createBy"));
			subSystemDef.setCreateTime(RequestUtils.getDate(request, "createTime"));
			subSystemDef.setUpdateBy(request.getParameter("updateBy"));
			subSystemDef.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
			subSystemDef.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

			this.subSystemDefService.save(subSystemDef);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.model.service.subSystemDefService")
	public void setSubSystemDefService(SubSystemDefService subSystemDefService) {
		this.subSystemDefService = subSystemDefService;
	}

	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		SubSystemDef subSystemDef = null;
		if (StringUtils.isNotEmpty(request.getParameter("subSysId"))) {
			subSystemDef = subSystemDefService.getSubSystemDef(RequestUtils.getString(request, "subSysId"));
		}
		JSONObject result = new JSONObject();
		if (subSystemDef != null) {
			result = subSystemDef.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", subSystemDef.getSubSysId());
			result.put("subSystemDefId", subSystemDef.getSubSysId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取所有子系统
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/subSystems")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getCategorys(@Context HttpServletRequest request) throws Exception {
		JSONArray  jsonArray=new JSONArray();
		String objectSubSystemJson = "";
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String contextPath=request.getContextPath();
		String sysId = RequestUtils.getString(request, "sysId");
		String treeId = RequestUtils.getString(request, "treeId");
		List<SubSystemDef> objectSubSystems =null;
		if(StringUtils.isNotEmpty(treeId)){
			objectSubSystems = subSystemDefService.getSystemSubSystemsByTreeId(treeId);
		}else{
			objectSubSystems = subSystemDefService.getSystemSubSystems(sysId);
		}
		if (objectSubSystems != null && objectSubSystems.size() > 0) {
			JSONObject subSystemDefJson = null;
			for (SubSystemDef subSystemDef : objectSubSystems) {
				subSystemDefJson = (JSONObject) JSONObject.toJSON(subSystemDef);
				if(subSystemDef.getEleType()!=null){
				String dataObjType=(subSystemDef.getDataObjType()==null||subSystemDef.getDataObjType()==0)?"":"_1";
				switch (subSystemDef.getEleType()+dataObjType) {
				case "subsystem":
					subSystemDefJson.put("icon", contextPath+"/scripts/model/img/sitemap-application-blue.png");
					break;
				case "page":
					subSystemDefJson.put("icon", contextPath+"/scripts/model/img/page_world.png");
					break;
				case "report":
					subSystemDefJson.put("icon", contextPath+"/scripts/model/img/report-excel.png");
					break;
				case "service":
					subSystemDefJson.put("icon", contextPath+"/scripts/model/img/database_server.png");
					break;
				case "dataobj":
					subSystemDefJson.put("icon", contextPath+"/scripts/model/img/database_import.png");
					break;
				case "dataobj_1":
					subSystemDefJson.put("icon", contextPath+"/scripts/model/img/database_export.png");
					break;
				default:
					break;
				}
				}
				jsonArray.add(subSystemDefJson);
			}
			objectSubSystemJson = jsonArray.toJSONString();
		} else {
			objectSubSystems = new ArrayList<SubSystemDef>();
			objectSubSystemJson = jsonArray.toJSONString(objectSubSystems);
		}
		return objectSubSystemJson.getBytes("UTF-8");
	}

	/**
	 * 保存系统规划流程元素到数据表中
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GET
	@POST
	@Path("/saveSystemPlan")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveSystemPlan(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject retJonObj = new JSONObject();
		retJonObj.put("result", "0");
		String modelId = request.getParameter("modelId");
		String subSysId = RequestUtils.getString(request, "subSysId");
		if (subSysId == null)
			return retJonObj.toJSONString().getBytes("UTF-8");
		SubSystemDef subSystemDef = subSystemDefService.getSubSystemDef(subSysId);
		if (subSystemDef == null)
			return retJonObj.toJSONString().getBytes("UTF-8");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		try {
			byte[] processModelBytes = repositoryService.getModelEditorSource(modelId);
			JSONObject sourceObj = subSystemDefService.saveSystemPlanByModelId(processModelBytes, modelId, subSystemDef,
					actorId);
			repositoryService.addModelEditorSource(modelId, JSON.toJSONString(sourceObj).getBytes("utf-8"));
			retJonObj.put("result", "1");
		} catch (Exception e) {
			logger.error(e.getMessage());
			retJonObj.put("result", "0");
		}
		return retJonObj.toJSONString().getBytes("UTF-8");
	}
}
