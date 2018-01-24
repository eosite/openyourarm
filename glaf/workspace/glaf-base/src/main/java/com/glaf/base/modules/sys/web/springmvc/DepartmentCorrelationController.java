package com.glaf.base.modules.sys.web.springmvc;

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

import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/departmentCorrelation")
@RequestMapping("/sys/departmentCorrelation")
public class DepartmentCorrelationController {
	protected static final Log logger = LogFactory.getLog(DepartmentCorrelationController.class);

	protected DepartmentCorrelationService departmentCorrelationService;

	public DepartmentCorrelationController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DepartmentCorrelation departmentCorrelation = departmentCorrelationService
							.getDepartmentCorrelation(Long.valueOf(x));
					if (departmentCorrelation != null
							&& (StringUtils.equals(departmentCorrelation.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						departmentCorrelationService.deleteById(departmentCorrelation.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DepartmentCorrelation departmentCorrelation = departmentCorrelationService
					.getDepartmentCorrelation(Long.valueOf(id));
			if (departmentCorrelation != null
					&& (StringUtils.equals(departmentCorrelation.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				if (departmentCorrelation != null
						&& (StringUtils.equals(departmentCorrelation.getCreateBy(), loginContext.getActorId())
								|| loginContext.isSystemAdministrator())) {
					departmentCorrelationService.deleteById(departmentCorrelation.getId());
				}
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DepartmentCorrelation departmentCorrelation = departmentCorrelationService
				.getDepartmentCorrelation(RequestUtils.getLong(request, "id"));
		if (departmentCorrelation != null) {
			request.setAttribute("departmentCorrelation", departmentCorrelation);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("departmentCorrelation.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/sys/departmentCorrelation/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepartmentCorrelationQuery query = new DepartmentCorrelationQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

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
		int total = departmentCorrelationService.getDepartmentCorrelationCountByQueryCriteria(query);
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

			List<DepartmentCorrelation> list = departmentCorrelationService
					.getDepartmentCorrelationsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DepartmentCorrelation departmentCorrelation : list) {
					JSONObject rowJSON = departmentCorrelation.toJsonObject();
					rowJSON.put("id", departmentCorrelation.getId());
					rowJSON.put("rowId", departmentCorrelation.getId());
					rowJSON.put("departmentCorrelationId", departmentCorrelation.getId());
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

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/sys/departmentCorrelation/list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDepartmentCorrelation")
	public byte[] saveDepartmentCorrelation(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DepartmentCorrelation departmentCorrelation = new DepartmentCorrelation();
		try {
			Tools.populate(departmentCorrelation, params);
			departmentCorrelation.setDeptId(RequestUtils.getLong(request, "deptId"));
			departmentCorrelation.setName(request.getParameter("name"));
			departmentCorrelation.setCode(request.getParameter("code"));
			departmentCorrelation.setSysId(request.getParameter("sysId"));
			departmentCorrelation.setCreateBy(actorId);

			departmentCorrelation.setUpdateBy(actorId);

			this.departmentCorrelationService.save(departmentCorrelation);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "departmentCorrelationService")
	public void setDepartmentCorrelationService(DepartmentCorrelationService departmentCorrelationService) {
		this.departmentCorrelationService = departmentCorrelationService;
	}

}
