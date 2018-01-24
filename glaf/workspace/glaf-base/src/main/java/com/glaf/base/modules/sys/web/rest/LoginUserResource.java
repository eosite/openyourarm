package com.glaf.base.modules.sys.web.rest;

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
import com.glaf.core.util.*;
import com.glaf.base.modules.sys.model.LoginUser;
import com.glaf.base.modules.sys.query.LoginUserQuery;
import com.glaf.base.modules.sys.service.LoginUserService;
import com.glaf.base.modules.sys.util.*;

/**
 * 
 * Rest响应类
 *
 */
@Controller
@Path("/rs/sys/loginUser")
public class LoginUserResource {
	protected static final Log logger = LogFactory.getLog(LoginUserResource.class);

	protected LoginUserService loginUserService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginUserQuery query = new LoginUserQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		LoginUserDomainFactory.processDataRequest(dataRequest);

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
		int total = loginUserService.getLoginUserCountByQueryCriteria(query);
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

			List<LoginUser> list = loginUserService.getLoginUsersByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (LoginUser loginUser : list) {
					JSONObject rowJSON = loginUser.toJsonObject();
					rowJSON.put("id", loginUser.getId());
					rowJSON.put("loginUserId", loginUser.getId());
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
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				loginUserService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		loginUserService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginUserQuery query = new LoginUserQuery();
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
		int total = loginUserService.getLoginUserCountByQueryCriteria(query);
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

			List<LoginUser> list = loginUserService.getLoginUsersByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (LoginUser loginUser : list) {
					JSONObject rowJSON = loginUser.toJsonObject();
					rowJSON.put("id", loginUser.getId());
					rowJSON.put("loginUserId", loginUser.getId());
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
	@Path("/saveLoginUser")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveLoginUser(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		LoginUser loginUser = new LoginUser();
		try {
			Tools.populate(loginUser, params);

			loginUser.setUserId(request.getParameter("userId"));
			loginUser.setName(request.getParameter("name"));
			loginUser.setLoginId(request.getParameter("loginId"));
			loginUser.setPassword(request.getParameter("password"));
			loginUser.setPasswordType(request.getParameter("passwordType"));
			loginUser.setSystemCode(request.getParameter("systemCode"));
			loginUser.setOrganization(request.getParameter("organization"));
			loginUser.setDepartment(request.getParameter("department"));
			loginUser.setPosition(request.getParameter("position"));
			loginUser.setMail(request.getParameter("mail"));
			loginUser.setMobile(request.getParameter("mobile"));
			loginUser.setTimeLive(RequestUtils.getInt(request, "timeLive"));
			loginUser.setLoginTime(RequestUtils.getDate(request, "loginTime"));
			loginUser.setAttribute(request.getParameter("attribute"));
			loginUser.setCreateBy(request.getParameter("createBy"));
			loginUser.setCreateTime(RequestUtils.getDate(request, "createTime"));

			this.loginUserService.save(loginUser);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setLoginUserService(LoginUserService loginUserService) {
		this.loginUserService = loginUserService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		LoginUser loginUser = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			loginUser = loginUserService.getLoginUser(request.getParameter("id"));
		}
		JSONObject result = new JSONObject();
		if (loginUser != null) {
			result = loginUser.toJsonObject();
			result.put("id", loginUser.getId());
			result.put("loginUserId", loginUser.getId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
}
