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

@Controller("/theme/sysThemeTmpControlArea")
@RequestMapping("/theme/sysThemeTmpControlArea")
public class SysThemeTmpControlAreaController {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpControlAreaController.class);

	protected SysThemeTmpControlAreaService sysThemeTmpControlAreaService;

	public SysThemeTmpControlAreaController() {

	}

        @javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpControlAreaService")
	public void setSysThemeTmpControlAreaService(SysThemeTmpControlAreaService sysThemeTmpControlAreaService) {
		this.sysThemeTmpControlAreaService = sysThemeTmpControlAreaService;
	}



        @ResponseBody
	@RequestMapping("/saveSysThemeTmpControlArea")
	public byte[] saveSysThemeTmpControlArea(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpControlArea sysThemeTmpControlArea = new SysThemeTmpControlArea();
		try {
		    Tools.populate(sysThemeTmpControlArea, params);
		    Date nowDate = new Date();
		    if(null == sysThemeTmpControlArea.getControlAreaId() || sysThemeTmpControlArea.getControlAreaId().isEmpty()){
		    	sysThemeTmpControlArea.setCreateBy(actorId);
		    	sysThemeTmpControlArea.setCreateTime(nowDate);
		    	sysThemeTmpControlArea.setDeleteFlag(0);
		    }else{
		    	sysThemeTmpControlArea.setUpdateBy(actorId);
		    	sysThemeTmpControlArea.setUpdateTime(nowDate);
		    }
		    
		    this.sysThemeTmpControlAreaService.save(sysThemeTmpControlArea);

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
		String ControlAreaId = RequestUtils.getString(request, "ControlAreaId");
		String ControlAreaIds = request.getParameter("ControlAreaIds");
		if (StringUtils.isNotEmpty(ControlAreaIds)) {
			StringTokenizer token = new StringTokenizer(ControlAreaIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysThemeTmpControlArea sysThemeTmpControlArea = sysThemeTmpControlAreaService.getSysThemeTmpControlArea(String.valueOf(x));
					if (sysThemeTmpControlArea != null && (StringUtils.equals(sysThemeTmpControlArea.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						sysThemeTmpControlArea.setDeleteFlag(1);
						sysThemeTmpControlAreaService.save(sysThemeTmpControlArea);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (ControlAreaId != null) {
			SysThemeTmpControlArea sysThemeTmpControlArea = sysThemeTmpControlAreaService
					.getSysThemeTmpControlArea(String.valueOf(ControlAreaId));
			if (sysThemeTmpControlArea != null && ( StringUtils.equals(sysThemeTmpControlArea.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				sysThemeTmpControlArea.setDeleteFlag(1);
				sysThemeTmpControlAreaService.save(sysThemeTmpControlArea);
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
		SysThemeTmpControlAreaQuery query = new SysThemeTmpControlAreaQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysThemeTmpControlAreaDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		*/
		if(!loginContext.isSystemAdministrator()){
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
		int total = sysThemeTmpControlAreaService.getSysThemeTmpControlAreaCountByQueryCriteria(query);
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

			if (dataRequest.getSort() != null
					&& !dataRequest.getSort().isEmpty()) {
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
			List<SysThemeTmpControlArea> list = sysThemeTmpControlAreaService.getSysThemeTmpControlAreasByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpControlArea sysThemeTmpControlArea : list) {
					JSONObject rowJSON = sysThemeTmpControlArea.toJsonObject();
					rowJSON.put("id", sysThemeTmpControlArea.getControlAreaId());
					rowJSON.put("sysThemeTmpControlAreaId", sysThemeTmpControlArea.getControlAreaId());
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
