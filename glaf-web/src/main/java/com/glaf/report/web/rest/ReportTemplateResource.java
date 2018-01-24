package com.glaf.report.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.glaf.form.core.domain.ObjectCategory;
import com.glaf.report.core.domain.ReportCategory;
import com.glaf.report.core.domain.ReportTemplate;
import com.glaf.report.core.query.ReportTemplateQuery;
import com.glaf.report.core.service.ReportCategoryService;
import com.glaf.report.core.service.ReportTemplateService;
import com.glaf.report.core.service.ReportTmpMappingService;
import com.glaf.report.core.util.ReportTemplateDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/reportTemplate")
public class ReportTemplateResource {
	protected static final Log logger = LogFactory.getLog(ReportTemplateResource.class);

	protected ReportTemplateService reportTemplateService;
	
	protected ReportTmpMappingService reportTmpMappingService;
	
	protected ReportCategoryService reportCategoryService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				reportTemplateService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		reportTemplateService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTemplateQuery query = new ReportTemplateQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		if(RequestUtils.getInt(request, "categoryId")>0){
			query.setCategoryId(RequestUtils.getInt(request, "categoryId"));
		}else{
			query.setCategoryId(null);
		}
		ReportTemplateDomainFactory.processDataRequest(dataRequest);

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
		int total = reportTemplateService.getReportTemplateCountByQueryCriteria(query);
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
			}else{
				orderName="modifyDatatime";
				order="desc";
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<ReportTemplate> list = reportTemplateService.getReportTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTemplate reportTemplate : list) {
					JSONObject rowJSON = reportTemplate.toJsonObject();
					rowJSON.put("id", reportTemplate.getId());
					rowJSON.put("reportTemplateId", reportTemplate.getId());
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
		ReportTemplateQuery query = new ReportTemplateQuery();
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
		int total = reportTemplateService.getReportTemplateCountByQueryCriteria(query);
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
			List<ReportTemplate> list = reportTemplateService.getReportTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (ReportTemplate reportTemplate : list) {
					JSONObject rowJSON = reportTemplate.toJsonObject();
					rowJSON.put("id", reportTemplate.getId());
					rowJSON.put("reportTemplateId", reportTemplate.getId());
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
	@Path("/saveReportTemplate")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveReportTemplate(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ReportTemplate reportTemplate = new ReportTemplate();
		try {
			Tools.populate(reportTemplate, params);

			reportTemplate.setRev(RequestUtils.getInt(request, "rev"));
			reportTemplate.setName(request.getParameter("name"));
			reportTemplate.setCode(request.getParameter("code"));
			reportTemplate.setCreator(request.getParameter("creator"));
			reportTemplate.setCreateDatatime(RequestUtils.getDate(request, "createDatatime"));
			reportTemplate.setModifier(request.getParameter("modifier"));
			reportTemplate.setModifyDatatime(RequestUtils.getDate(request, "modifyDatatime"));
			reportTemplate.setStatus(RequestUtils.getInt(request, "status"));
			reportTemplate.setPublish(RequestUtils.getInt(request, "publish"));
			reportTemplate.setPublishUser(request.getParameter("publishUser"));
			reportTemplate.setPublishDatetime(RequestUtils.getDate(request, "publishDatetime"));
			reportTemplate.setFileName(request.getParameter("fileName"));
			reportTemplate.setExt(request.getParameter("ext"));

			this.reportTemplateService.save(reportTemplate);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportTemplateService")
	public void setReportTemplateService(ReportTemplateService reportTemplateService) {
		this.reportTemplateService = reportTemplateService;
	}
	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpMappingService")
	public void setReportTmpMappingService(ReportTmpMappingService reportTmpMappingService) {
		this.reportTmpMappingService = reportTmpMappingService;
	}
	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportCategoryService")
	public void setReportCategoryService(ReportCategoryService reportCategoryService) {
		this.reportCategoryService = reportCategoryService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		ReportTemplate reportTemplate = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			reportTemplate = reportTemplateService.getReportTemplate(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (reportTemplate != null) {
			result = reportTemplate.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", reportTemplate.getId());
			result.put("reportTemplateId", reportTemplate.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取报表数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/reportTemplate/{mappingId}/getdata")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getReportData(HttpServletRequest request, @PathVariable String mappingId)
			throws UnsupportedEncodingException {
		String dataSetParams=request.getParameter("params");
		JSONObject result = new JSONObject();
		try {
			if (StringUtils.isNotEmpty(mappingId)) {
				
				result=reportTmpMappingService.getReportData(mappingId, StringUtils.isEmpty(dataSetParams)?new JSONObject():JSONObject.parseObject(dataSetParams));
			}else{
				result=JSONObject.parseObject("{\"dataset1\":[{\"Column1\":\"张三\",\"Column2\":\"男\"},{\"Column1\":\"张三\",\"Column2\":\"男\"}]}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return JSONObject.toJSONString(result).getBytes("UTF-8");
	}
	
	/**
	 * 获取所有分类
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportTemplate/categorys")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getCategorys(@Context HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String reportCategoryJson = null;
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		List<ReportCategory> reportCategorys = reportCategoryService.list(null);
		if (reportCategorys != null && reportCategorys.size() > 0) {
			reportCategoryJson = jsonObject.toJSONString(reportCategorys);
			return reportCategoryJson.getBytes("UTF-8");
		}
		return JSONObject.toJSONString(jsonObject).getBytes("UTF-8");
	}

	/**
	 * 更新分类名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportTemplate/category/rename")
	@ResponseBody
	public byte[] getCategoryRename(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		String name = RequestUtils.getString(request, "name");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		try {
			reportCategoryService.rename(categoryId, name,actorId,new Date());
		} catch (Exception e) {
			return ResponseUtils.responseJsonResult(false);
		}
		return ResponseUtils.responseJsonResult(true);
	}
	/**
	 * 更新分类名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportTemplate/category/move")
	@ResponseBody
	public byte[] categoryMove(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		Long pId = RequestUtils.getLong(request, "pId");
		String treeId=RequestUtils.getString(request, "treeId");
		String moveType=RequestUtils.getString(request, "moveType");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		try {
			reportCategoryService.move(moveType,categoryId, pId,treeId,actorId,new Date());
		} catch (Exception e) {
			return ResponseUtils.responseJsonResult(false);
		}
		return ResponseUtils.responseJsonResult(true);
	}
	/**
	 * 删除分类名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportTemplate/category/delete")
	@ResponseBody
	public byte[] getCategoryDelete(@Context HttpServletRequest request) throws Exception {
		Integer categoryId = RequestUtils.getInteger(request, "categoryId");
		reportCategoryService.deleteById(categoryId);
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 新增分类
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reportTemplate/category/add")
	@ResponseBody
	public byte[] addCategory(@Context HttpServletRequest request) throws Exception {
		Long pId = RequestUtils.getLong(request, "pId");
		String pTreeId = RequestUtils.getString(request, "pTreeId","");
		String name = RequestUtils.getString(request, "name");
		int level = RequestUtils.getInt(request, "level");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject jsonObject = new JSONObject();

		try {
			ReportCategory obj = new ReportCategory();
			obj.setDeleteFlag(0);
			obj.setTreeId(pTreeId);
			obj.setCreateDatetime(new Date());
			obj.setCreator(actorId);
			obj.setLevel(level);
			obj.setName(name);
			obj.setModifier(actorId);
			obj.setModifyDatetime(new Date());
			obj.setParentId_(pId);
			reportCategoryService.save(obj);
			jsonObject = (JSONObject) jsonObject.toJSON(obj);
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			logger.error(e.getMessage());
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
}
