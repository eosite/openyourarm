package com.glaf.monitor.server.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.monitor.server.domain.*;
import com.glaf.monitor.server.query.*;
import com.glaf.monitor.server.service.*;
import com.glaf.monitor.server.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/monitor/monitorCategory")
@RequestMapping("/monitor/monitorCategory")
public class MonitorCategoryController {
	protected static final Log logger = LogFactory.getLog(MonitorCategoryController.class);

	protected MonitorCategoryService monitorCategoryService;

	public MonitorCategoryController() {

	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorCategoryService")
	public void setMonitorCategoryService(MonitorCategoryService monitorCategoryService) {
		this.monitorCategoryService = monitorCategoryService;
	}

	@ResponseBody
	@RequestMapping("/saveMonitorCategory")
	public byte[] saveMonitorCategory(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorCategory monitorCategory = new MonitorCategory();
		try {
			Tools.populate(monitorCategory, params);
			Date nowDate = new Date();
			if(monitorCategory.getId() == null){
				//新增节点
				//父节点的indexId
				Integer parentIndexId = ParamUtils.getIntValue(params, "parentIndexId");
				//父节点的treeId
				String parentTreeId = ParamUtils.getString(params, "parentTreeId");
				
				monitorCategory.setDeleteFlag(0);
				monitorCategory.setCreateby(actorId);
				monitorCategory.setCreatetime(nowDate);
				this.monitorCategoryService.add(monitorCategory,parentIndexId,parentTreeId);
			}else{
				//修改节点
				monitorCategory.setUpdateby(actorId);
				monitorCategory.setUpdatetime(nowDate);
				this.monitorCategoryService.save(monitorCategory);
			}
			
			// monitorCategory.setCreateBy(actorId);
			
			JSONObject retjson = new JSONObject();
			retjson.put("statusCode", "200");
			retjson.put("message", "保存成功");
			retjson.put("data", monitorCategory.toJsonObject());

			return retjson.toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			
		}
		return ResponseUtils.responseJsonResult(false,"代码重复");
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Integer id = RequestUtils.getInteger(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					MonitorCategory monitorCategory = monitorCategoryService.getMonitorCategory(Integer.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MonitorCategory monitorCategory = monitorCategoryService.getMonitorCategory(Integer.valueOf(id));
			if(monitorCategory != null){
				MonitorCategory monitorCategory2 = new MonitorCategory();
				monitorCategory2.setId(monitorCategory.getId());
				monitorCategory2.setDeleteFlag(1);
				monitorCategoryService.save(monitorCategory2);
			}
			return ResponseUtils.responseResult(true);
			
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorCategoryQuery query = new MonitorCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = monitorCategoryService.getMonitorCategoryCountByQueryCriteria(query);
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
			List<MonitorCategory> list = monitorCategoryService.getMonitorCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorCategory monitorCategory : list) {
					JSONObject rowJSON = monitorCategory.toJsonObject();
					rowJSON.put("id", monitorCategory.getId());
					rowJSON.put("rowId", monitorCategory.getId());
					rowJSON.put("monitorCategoryId", monitorCategory.getId());
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

	/**
	 * 查询树形数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/treeData")
	@ResponseBody
	public byte[] treeData(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorCategoryQuery query = new MonitorCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		
		String orderName = RequestUtils.getParameter(request, "orderName");
		String order = RequestUtils.getParameter(request, "order");

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}
		
		if(query.getPid() == null){
			query.setPid(-1);
		}

		JSONObject result = new JSONObject();
		int total = monitorCategoryService.getMonitorCategoryCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}else{
				query.setSortColumn("createtime");
				query.setSortOrder(" desc ");
			}
			List<MonitorCategory> list = monitorCategoryService.list(query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (MonitorCategory monitorCategory : list) {
					JSONObject rowJSON = monitorCategory.toJsonObject();
					rowJSON.put("id", monitorCategory.getId());
					rowJSON.put("monitorCategoryId", monitorCategory.getId());
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
	
	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorCategoryQuery query = new MonitorCategoryQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		MonitorCategoryDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
		int total = monitorCategoryService.getMonitorCategoryCountByQueryCriteria(query);
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
			List<MonitorCategory> list = monitorCategoryService.getMonitorCategorysByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorCategory monitorCategory : list) {
					JSONObject rowJSON = monitorCategory.toJsonObject();
					rowJSON.put("id", monitorCategory.getId());
					rowJSON.put("monitorCategoryId", monitorCategory.getId());
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

}
