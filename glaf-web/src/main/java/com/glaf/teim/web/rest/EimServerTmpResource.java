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
import org.dom4j.DocumentException;
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

import com.glaf.teim.domain.EimServerTmp;
import com.glaf.teim.domain.EimServerTmpTree;
import com.glaf.teim.query.EimServerTmpQuery;
import com.glaf.teim.service.EimServerTmpService;
import com.glaf.teim.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/teim/tmp")
public class EimServerTmpResource {
	protected static final Log logger = LogFactory.getLog(EimServerTmpResource.class);

	protected EimServerTmpService eimServerTmpService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("tmpIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				eimServerTmpService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		eimServerTmpService.deleteById(request.getParameter("tmpId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerTmpQuery query = new EimServerTmpQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		EimServerTmpDomainFactory.processDataRequest(dataRequest);
		Long categoryId=RequestUtils.getLong(request, "categoryId");
		if(categoryId==-1L){
			query.setCategoryId(null);
		}
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
		int total = eimServerTmpService.getEimServerTmpCountByQueryCriteria(query);
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
			List<EimServerTmp> list = eimServerTmpService.getEimServerTmpsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EimServerTmp eimServerTmp : list) {
					JSONObject rowJSON = eimServerTmp.toJsonObject();
					rowJSON.put("id", eimServerTmp.getTmpId());
					rowJSON.put("eimServerTmpId", eimServerTmp.getTmpId());
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
		EimServerTmpQuery query = new EimServerTmpQuery();
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
		int total = eimServerTmpService.getEimServerTmpCountByQueryCriteria(query);
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
			List<EimServerTmp> list = eimServerTmpService.getEimServerTmpsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EimServerTmp eimServerTmp : list) {
					JSONObject rowJSON = eimServerTmp.toJsonObject();
					rowJSON.put("id", eimServerTmp.getTmpId());
					rowJSON.put("eimServerTmpId", eimServerTmp.getTmpId());
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
	@Path("/saveEimServerTmp")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveEimServerTmp(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EimServerTmp eimServerTmp = null;
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String tmpId=request.getParameter("tmpId");
		try {
			if(StringUtils.isEmpty(tmpId)){
				eimServerTmp=new EimServerTmp();
				eimServerTmp.setCreateBy(actorId);
				eimServerTmp.setCreateTime(new Date());
			}else{
				eimServerTmp=eimServerTmpService.getEimServerTmp(tmpId);
				eimServerTmp.setUpdateBy(actorId);
				eimServerTmp.setUpdateTime(new Date());
			}
			Tools.populate(eimServerTmp, params);
      
			eimServerTmp.setCategoryId(RequestUtils.getLong(request, "categoryId"));
			eimServerTmp.setName(request.getParameter("name"));
			eimServerTmp.setPath_(request.getParameter("path_"));
			eimServerTmp.setReqUrlParam(request.getParameter("reqUrlParam"));
			eimServerTmp.setReqType(request.getParameter("reqType"));
			eimServerTmp.setReqHeader(request.getParameter("reqHeader"));
			eimServerTmp.setReqContentType(request.getParameter("reqContentType"));
			eimServerTmp.setResContentType(request.getParameter("resContentType"));
			eimServerTmp.setReqBody(request.getParameter("reqBody"));
			eimServerTmp.setResponse_(request.getParameter("response_"));
			eimServerTmp.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));

			this.eimServerTmpService.save(eimServerTmp);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.teim.service.eimServerTmpService")
	public void setEimServerTmpService(EimServerTmpService eimServerTmpService) {
		this.eimServerTmpService = eimServerTmpService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		EimServerTmp eimServerTmp = null;
		if (StringUtils.isNotEmpty(request.getParameter("tmpId"))) {
			eimServerTmp = eimServerTmpService.getEimServerTmp(request.getParameter("tmpId"));
		}
		JSONObject result = new JSONObject();
		if (eimServerTmp != null) {
			result = eimServerTmp.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", eimServerTmp.getTmpId());
			result.put("eimServerTmpId", eimServerTmp.getTmpId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	@GET
	@POST
	@Path("/tree")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] tree(@Context HttpServletRequest request) throws IOException {
		JSONObject jsonObject = new JSONObject();
		List<EimServerTmpTree> treeData=eimServerTmpService.getEimServerTmpTreeData();
		if (treeData != null && treeData.size() > 0) {
			String result = jsonObject.toJSONString(treeData);
			return result.getBytes("UTF-8");
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
	
	@GET
	@POST
	@Path("/moveToCategory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] moveWorkFlowToCategory(@Context HttpServletRequest request) throws Exception {
		String tmpId = RequestUtils.getString(request, "tmpId");
		String categoryId = RequestUtils.getString(request, "categoryId");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject jsonObject = new JSONObject();
		try {
			eimServerTmpService.moveToCategory(tmpId, categoryId);
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			logger.error(e.getMessage());
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
	/**
	 * 获取模板响应体格式
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/resbody")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] resbody(@Context HttpServletRequest request) throws IOException {
		JSONObject jsonObject = new JSONObject();
		String tmpId = RequestUtils.getString(request, "tmpId");
		EimServerTmp tmp=eimServerTmpService.getEimServerTmp(tmpId);
		if(tmp!=null){
			return tmp.getResponse_().getBytes("UTF-8");
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取模板的输入形参
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/requestBody")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] requestBody(@Context HttpServletRequest request) throws IOException {
		JSONObject jsonObject = new JSONObject();
		String tmpId = RequestUtils.getString(request, "tmpId");
		EimServerTmp tmp=eimServerTmpService.getEimServerTmp(tmpId);
		if(tmp!=null){
			if(StringUtils.isNotEmpty(tmp.getReqType()) && tmp.getReqType().indexOf("soap") == 0){
				//使用webservice格式
				String reqBodyStr = tmp.getReqBody();
				JSONObject reqBody = JSON.parseObject(reqBodyStr);
				JSONObject obj;
				String defaultValue;
				for(String key : reqBody.keySet()){
					obj = reqBody.getJSONObject(key);
					defaultValue = obj.getString("defaultval");
					if(StringUtils.isNotEmpty(defaultValue) && defaultValue.indexOf("$") != -1){
						
					}else{
						jsonObject.put(key, obj);
					}
				}
			}else{
				//使用其他
				String urlParamStr = tmp.getReqUrlParam();
				String reqBodyStr = tmp.getReqBody();
				JSONObject urlParams = JSON.parseObject(urlParamStr);
				JSONObject reqBody = JSON.parseObject(reqBodyStr);
				JSONObject obj;
				String defaultValue;
				for(String key : urlParams.keySet()){
					obj = urlParams.getJSONObject(key);
					defaultValue = obj.getString("defaultval");
					if(StringUtils.isNotEmpty(defaultValue) && defaultValue.indexOf("$") != -1){
						
					}else{
						jsonObject.put(key, obj);
					}
				}
				for(String key : reqBody.keySet()){
					obj = reqBody.getJSONObject(key);
					defaultValue = obj.getString("defaultval");
					if(StringUtils.isNotEmpty(defaultValue) && defaultValue.indexOf("$") != -1){
						
					}else{
						jsonObject.put(key, obj);
					}
				}
			}
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
	
	@GET
	@POST
	@Path("/wsdl")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getWebServiceMethodAndParams(@Context HttpServletRequest request) throws IOException {
		JSONObject jsonObject = new JSONObject();
		String wsUrl=RequestUtils.getString(request, "wsurl");
		try {
			jsonObject= eimServerTmpService.getWebServiceMethodAndParams(wsUrl);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
}
