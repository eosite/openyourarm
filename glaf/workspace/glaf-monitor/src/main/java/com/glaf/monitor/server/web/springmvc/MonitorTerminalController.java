package com.glaf.monitor.server.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller("/monitor/monitorTerminal")
@RequestMapping("/monitor/monitorTerminal")
public class MonitorTerminalController {
	protected static final Log logger = LogFactory.getLog(MonitorTerminalController.class);

	protected MonitorTerminalService monitorTerminalService;
	@Autowired
	protected MonitorServerUtil monitorServerUtil;

	public MonitorTerminalController() {

	}

	@javax.annotation.Resource(name = "com.glaf.monitor.server.service.monitorTerminalService")
	public void setMonitorTerminalService(MonitorTerminalService monitorTerminalService) {
		this.monitorTerminalService = monitorTerminalService;
	}

	@ResponseBody
	@RequestMapping("/getSystemInfo")
	public byte[] getSystemInfo(HttpServletRequest request) {
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveMonitorTerminal")
	public byte[] saveMonitorTerminal(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminal monitorTerminal = new MonitorTerminal();
		try {
			Tools.populate(monitorTerminal, params);
			Date nowDate = new Date();
			if (StringUtils.isEmpty(monitorTerminal.getId())) {
				// 新增
				monitorTerminal.setCreateby(actorId);
				monitorTerminal.setCreatetime(nowDate);
				monitorTerminal.setDeleteFlag(0);
				this.monitorTerminalService.save(monitorTerminal, ParamUtils.getIntValue(params, "categoryId"));
			} else {
				// 修改
				monitorTerminal.setUpdateby(actorId);
				monitorTerminal.setUpdatetime(nowDate);
				this.monitorTerminalService.save(monitorTerminal);
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					MonitorTerminal monitorTerminal = monitorTerminalService.getMonitorTerminal(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MonitorTerminal monitorTerminal = monitorTerminalService.getMonitorTerminal(String.valueOf(id));
			if (monitorTerminal != null) {
				MonitorTerminal monitorTerminal2 = new MonitorTerminal();
				monitorTerminal2.setId(monitorTerminal.getId());
				monitorTerminal2.setDeleteFlag(1);
				this.monitorTerminalService.save(monitorTerminal2);
			}
			return ResponseUtils.responseResult(true);
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/list")
	@ResponseBody
	public byte[] list(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalQuery query = new MonitorTerminalQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		String orderName = null;
		String order = null;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");
		
		if (StringUtils.isNotEmpty(orderName)) {
			query.setSortOrder(orderName);
			if (StringUtils.equals(order, "desc")) {
				query.setSortOrder(" desc ");
			}
		}

		JSONObject result = new JSONObject();
		List<MonitorTerminal> list = monitorTerminalService.list(query);
		if (list != null && !list.isEmpty()) {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			for (MonitorTerminal monitorTerminal : list) {
				JSONObject rowJSON = monitorTerminal.toJsonObject();
				rowJSON.put("id", monitorTerminal.getId());
				rowJSON.put("rowId", monitorTerminal.getId());
				rowJSON.put("monitorTerminalId", monitorTerminal.getId());
				rowsJSON.add(rowJSON);
			}

		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody Map<String, Object> dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MonitorTerminalQuery query = new MonitorTerminalQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		Tools.populate(query, dataRequest);

		
		if (dataRequest.get("params") != null ) {
			String paramstr = (String)dataRequest.get("params");
			if(StringUtils.isNotEmpty(paramstr)){
				Tools.populate(query, JSON.parseObject(paramstr));
			}
			
		}

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = 0;
		limit = 0;
		if(dataRequest.get("page") != null){
			pageNo = (int)dataRequest.get("page");
		}
		if(dataRequest.get("pageSize") != null){
			limit = (int)dataRequest.get("pageSize");
		}
		

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = monitorTerminalService.getMonitorTerminalCountByQueryCriteria(query);
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
//
//			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
//				SortDescriptor sort = dataRequest.getSort().get(0);
//				orderName = sort.getField();
//				order = sort.getDir();
//				logger.debug("orderName:" + orderName);
//				logger.debug("order:" + order);
//			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<MonitorTerminal> list = monitorTerminalService.getMonitorTerminalsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MonitorTerminal monitorTerminal : list) {
					JSONObject rowJSON = monitorTerminal.toJsonObject();
					rowJSON.put("id", monitorTerminal.getId());
					rowJSON.put("monitorTerminalId", monitorTerminal.getId());
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
