package com.glaf.theme.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;
import com.glaf.theme.service.*;
import com.glaf.theme.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/theme/sysThemeTmpLayoutArea")
@RequestMapping("/theme/sysThemeTmpLayoutArea")
public class SysThemeTmpLayoutAreaController {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpLayoutAreaController.class);

	protected SysThemeTmpLayoutAreaService sysThemeTmpLayoutAreaService;

	public SysThemeTmpLayoutAreaController() {

	}

	@javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpLayoutAreaService")
	public void setSysThemeTmpLayoutAreaService(SysThemeTmpLayoutAreaService sysThemeTmpLayoutAreaService) {
		this.sysThemeTmpLayoutAreaService = sysThemeTmpLayoutAreaService;
	}

	@ResponseBody
	@RequestMapping("/saveSysThemeTmpLayoutArea")
	public byte[] saveSysThemeTmpLayoutArea(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutArea sysThemeTmpLayoutArea = new SysThemeTmpLayoutArea();
		try {
			Tools.populate(sysThemeTmpLayoutArea, params);
			Date nowDate = new Date();

			if (null == sysThemeTmpLayoutArea.getAreaId() || sysThemeTmpLayoutArea.getAreaId().isEmpty()) {
				sysThemeTmpLayoutArea.setCreateBy(actorId);
				sysThemeTmpLayoutArea.setCreateTime(nowDate);
				sysThemeTmpLayoutArea.setDeleteFlag(0);
			} else {
				sysThemeTmpLayoutArea.setUpdateBy(actorId);
				sysThemeTmpLayoutArea.setUpdateTime(nowDate);
			}

			this.sysThemeTmpLayoutAreaService.save(sysThemeTmpLayoutArea);

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
		String areaId = RequestUtils.getString(request, "areaId");
		String areaIds = request.getParameter("areaIds");
		if (StringUtils.isNotEmpty(areaIds)) {
			StringTokenizer token = new StringTokenizer(areaIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysThemeTmpLayoutArea sysThemeTmpLayoutArea = sysThemeTmpLayoutAreaService
							.getSysThemeTmpLayoutArea(String.valueOf(x));
					if (sysThemeTmpLayoutArea != null
							&& (StringUtils.equals(sysThemeTmpLayoutArea.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						sysThemeTmpLayoutArea.setDeleteFlag(1);
						sysThemeTmpLayoutAreaService.save(sysThemeTmpLayoutArea);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (areaId != null) {
			SysThemeTmpLayoutArea sysThemeTmpLayoutArea = sysThemeTmpLayoutAreaService
					.getSysThemeTmpLayoutArea(String.valueOf(areaId));
			if (sysThemeTmpLayoutArea != null
					&& (StringUtils.equals(sysThemeTmpLayoutArea.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				sysThemeTmpLayoutArea.setDeleteFlag(1);
				sysThemeTmpLayoutAreaService.save(sysThemeTmpLayoutArea);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutAreaQuery query = new SysThemeTmpLayoutAreaQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysThemeTmpLayoutAreaDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpLayoutAreaService.getSysThemeTmpLayoutAreaCountByQueryCriteria(query);
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
			List<SysThemeTmpLayoutArea> list = sysThemeTmpLayoutAreaService
					.getSysThemeTmpLayoutAreasByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysThemeTmpLayoutArea sysThemeTmpLayoutArea : list) {
					JSONObject rowJSON = sysThemeTmpLayoutArea.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayoutArea.getAreaId());
					rowJSON.put("sysThemeTmpLayoutAreaId", sysThemeTmpLayoutArea.getAreaId());
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
