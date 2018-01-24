package com.glaf.theme.web.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;
import com.glaf.theme.service.*;
import com.glaf.theme.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/theme/sysThemeTmpBytearray")
@RequestMapping("/theme/sysThemeTmpBytearray")
public class SysThemeTmpBytearrayController {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpBytearrayController.class);

	protected SysThemeTmpBytearrayService sysThemeTmpBytearrayService;

	public SysThemeTmpBytearrayController() {

	}

        @javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpBytearrayService")
	public void setSysThemeTmpBytearrayService(SysThemeTmpBytearrayService sysThemeTmpBytearrayService) {
		this.sysThemeTmpBytearrayService = sysThemeTmpBytearrayService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		SysThemeTmpBytearray sysThemeTmpBytearray = new SysThemeTmpBytearray();
		Tools.populate(sysThemeTmpBytearray, params);

                sysThemeTmpBytearray.setName(request.getParameter("name"));
                sysThemeTmpBytearray.setBussType(request.getParameter("bussType"));
                sysThemeTmpBytearray.setBussKey(request.getParameter("bussKey"));
                sysThemeTmpBytearray.setType(request.getParameter("type"));
                sysThemeTmpBytearray.setCreateBy(request.getParameter("createBy"));
                sysThemeTmpBytearray.setCreateTime(RequestUtils.getDate(request, "createTime"));
                sysThemeTmpBytearray.setUpdateBy(request.getParameter("updateBy"));
                sysThemeTmpBytearray.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                sysThemeTmpBytearray.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
		
		//sysThemeTmpBytearray.setCreateBy(actorId);
         
		sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);   

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/blukSave")
	public byte[] blukSave(HttpServletRequest request, @RequestBody JSONArray paramsAry) throws Exception { 
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		JSONObject retsult = new JSONObject();
		JSONArray retArray = new JSONArray();
		try {
			JSONObject retObj = null;
			JSONObject paramsObj = null;
			String id =  null,bussKey =  null,bussType =  null,styleData =  null,
					styleCss =  null,content =  null,type =  null,styleDataId =  null,styleCssId =  null;
			Date nowDate = new Date();
			for(int i = 0 ; i < paramsAry.size(); i++){
				
				paramsObj = paramsAry.getJSONObject(i);
				id = paramsObj.getString("id");
				bussKey = paramsObj.getString("bussKey");
				bussType = paramsObj.getString("bussType");
				styleData = paramsObj.getString("styleData");
				styleCss = paramsObj.getString("styleCss");
				content = paramsObj.getString("content");
				type = paramsObj.getString("type");
				styleDataId = paramsObj.getString("styleDataId");
				styleCssId = paramsObj.getString("styleCssId");
				
				if(type != null && !type.isEmpty() && content != null){
					SysThemeTmpBytearray sysThemeTmpBytearray = new SysThemeTmpBytearray();
					sysThemeTmpBytearray.setId(id);
					sysThemeTmpBytearray.setBussKey(bussKey);
					sysThemeTmpBytearray.setBussType(bussType);
					sysThemeTmpBytearray.setType(type);
					sysThemeTmpBytearray.setBytes(content.getBytes("UTF-8"));
					if(id == null || id.isEmpty()){
						sysThemeTmpBytearray.setCreateBy(actorId);
						sysThemeTmpBytearray.setCreateTime(nowDate);
					}else{
						sysThemeTmpBytearray.setUpdateBy(actorId);
						sysThemeTmpBytearray.setUpdateTime(nowDate);
					}
					this.sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
					//保存成功返回数据
					retObj = new JSONObject();
					retObj.put("type", type);
					retObj.put("bussKey", bussKey);
					retObj.put("bussType", bussType);
					retObj.put("id", sysThemeTmpBytearray.getId());
					retArray.add(retObj);
				}else if(styleData != null && !styleData.isEmpty() && styleCss != null){
					SysThemeTmpBytearray sysThemeTmpBytearray = new SysThemeTmpBytearray();
					sysThemeTmpBytearray.setId(styleDataId);
					sysThemeTmpBytearray.setBussKey(bussKey);
					sysThemeTmpBytearray.setBussType(bussType);
					sysThemeTmpBytearray.setType("styleData");
					sysThemeTmpBytearray.setBytes(styleData.getBytes("UTF-8"));
					if(sysThemeTmpBytearray.getId() == null || sysThemeTmpBytearray.getId().isEmpty()){
						sysThemeTmpBytearray.setCreateBy(actorId);
						sysThemeTmpBytearray.setCreateTime(nowDate);
					}else{
						sysThemeTmpBytearray.setUpdateBy(actorId);
						sysThemeTmpBytearray.setUpdateTime(nowDate);
					}
					this.sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
					retObj = new JSONObject();
					retObj.put("type", "styleData");
					retObj.put("bussKey", bussKey);
					retObj.put("bussType", bussType);
					retObj.put("id", sysThemeTmpBytearray.getId());
					retArray.add(retObj);
					sysThemeTmpBytearray = new SysThemeTmpBytearray();
					sysThemeTmpBytearray.setId(styleCssId);
					sysThemeTmpBytearray.setBussKey(bussKey);
					sysThemeTmpBytearray.setBussType(bussType);
					sysThemeTmpBytearray.setType("CSS");
					sysThemeTmpBytearray.setBytes(styleCss.getBytes("UTF-8"));
					if(sysThemeTmpBytearray.getId() == null || sysThemeTmpBytearray.getId().isEmpty()){
						sysThemeTmpBytearray.setCreateBy(actorId);
						sysThemeTmpBytearray.setCreateTime(nowDate);
					}else{
						sysThemeTmpBytearray.setUpdateBy(actorId);
						sysThemeTmpBytearray.setUpdateTime(nowDate);
					}
					this.sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
					retObj = new JSONObject();
					retObj.put("type", "CSS");
					retObj.put("bussKey", bussKey);
					retObj.put("bussType", bussType);
					retObj.put("id", sysThemeTmpBytearray.getId());
					retArray.add(retObj);
				}
			}
			retsult.put("statusCode", 200);
			retsult.put("data", retArray);
			return retsult.toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		retsult.put("statusCode", 400);
		retsult.put("data", retArray);
		return retsult.toJSONString().getBytes("UTF-8");
	}
	
        @ResponseBody
	@RequestMapping("/saveSysThemeTmpBytearray")
	public byte[] saveSysThemeTmpBytearray(HttpServletRequest request, @RequestBody Map<String, Object> paramsObj) { 
        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
        Map<String, Object> params = RequestUtils.getParameterMap(request);
		
		try {
			
			String bussKey = (String) paramsObj.get("bussKey");
			String bussType = (String) paramsObj.get("bussType");
			String styleData = (String) paramsObj.get("styleData");
			String styleCss = (String) paramsObj.get("styleCss");
			String content = (String) paramsObj.get("content");
			String type = (String) paramsObj.get("type");
			if(type != null && !type.isEmpty() && content != null){
				SysThemeTmpBytearray sysThemeTmpBytearray = new SysThemeTmpBytearray();
				sysThemeTmpBytearray.setBussKey(bussKey);
				sysThemeTmpBytearray.setBussType(bussType);
				sysThemeTmpBytearray.setType(type);
				sysThemeTmpBytearray.setBytes(content.getBytes("UTF-8"));
				this.sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
			}else if(styleData != null && !styleData.isEmpty() && styleCss != null){
				SysThemeTmpBytearray sysThemeTmpBytearray = new SysThemeTmpBytearray();
				sysThemeTmpBytearray.setBussKey(bussKey);
				sysThemeTmpBytearray.setBussType(bussType);
				sysThemeTmpBytearray.setType("styleData");
				sysThemeTmpBytearray.setBytes(styleData.getBytes("UTF-8"));
				this.sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
				sysThemeTmpBytearray = new SysThemeTmpBytearray();
				sysThemeTmpBytearray.setBussKey(bussKey);
				sysThemeTmpBytearray.setBussType(bussType);
				sysThemeTmpBytearray.setType("CSS");
				sysThemeTmpBytearray.setBytes(styleCss.getBytes("UTF-8"));
				this.sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
			}
		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody SysThemeTmpBytearray saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		SysThemeTmpBytearray sysThemeTmpBytearray = new SysThemeTmpBytearray();
		try {
		    Tools.populate(sysThemeTmpBytearray, model);
                    sysThemeTmpBytearray.setName(ParamUtils.getString(model, "name"));
                    sysThemeTmpBytearray.setBussType(ParamUtils.getString(model, "bussType"));
                    sysThemeTmpBytearray.setBussKey(ParamUtils.getString(model, "bussKey"));
                    sysThemeTmpBytearray.setType(ParamUtils.getString(model, "type"));
                    sysThemeTmpBytearray.setCreateBy(ParamUtils.getString(model, "createBy"));
                    sysThemeTmpBytearray.setCreateTime(ParamUtils.getDate(model, "createTime"));
                    sysThemeTmpBytearray.setUpdateBy(ParamUtils.getString(model, "updateBy"));
                    sysThemeTmpBytearray.setUpdateTime(ParamUtils.getDate(model, "updateTime"));
                    sysThemeTmpBytearray.setDeleteFlag(ParamUtils.getInt(model, "deleteFlag"));
		    sysThemeTmpBytearray.setCreateBy(actorId);
		    this.sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return sysThemeTmpBytearray;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                SysThemeTmpBytearray sysThemeTmpBytearray = sysThemeTmpBytearrayService.getSysThemeTmpBytearray(request.getParameter("id"));

		Tools.populate(sysThemeTmpBytearray, params);

                sysThemeTmpBytearray.setName(request.getParameter("name"));
                sysThemeTmpBytearray.setBussType(request.getParameter("bussType"));
                sysThemeTmpBytearray.setBussKey(request.getParameter("bussKey"));
                sysThemeTmpBytearray.setType(request.getParameter("type"));
                sysThemeTmpBytearray.setCreateBy(request.getParameter("createBy"));
                sysThemeTmpBytearray.setCreateTime(RequestUtils.getDate(request, "createTime"));
                sysThemeTmpBytearray.setUpdateBy(request.getParameter("updateBy"));
                sysThemeTmpBytearray.setUpdateTime(RequestUtils.getDate(request, "updateTime"));
                sysThemeTmpBytearray.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
	
		sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);   

		return this.list(request, modelMap);
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
					SysThemeTmpBytearray sysThemeTmpBytearray = sysThemeTmpBytearrayService.getSysThemeTmpBytearray(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (sysThemeTmpBytearray != null && (StringUtils.equals(sysThemeTmpBytearray.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//sysThemeTmpBytearray.setDeleteFlag(1);
						sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SysThemeTmpBytearray sysThemeTmpBytearray = sysThemeTmpBytearrayService
					.getSysThemeTmpBytearray(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (sysThemeTmpBytearray != null && ( StringUtils.equals(sysThemeTmpBytearray.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//sysThemeTmpBytearray.setDeleteFlag(1);
				sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

    
        @RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                SysThemeTmpBytearray sysThemeTmpBytearray = sysThemeTmpBytearrayService.getSysThemeTmpBytearray(request.getParameter("id"));
		if(sysThemeTmpBytearray != null) {
		    request.setAttribute("sysThemeTmpBytearray", sysThemeTmpBytearray);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("sysThemeTmpBytearray.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/theme/sysThemeTmpBytearray/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                SysThemeTmpBytearray sysThemeTmpBytearray = sysThemeTmpBytearrayService.getSysThemeTmpBytearray(request.getParameter("id"));
		request.setAttribute("sysThemeTmpBytearray", sysThemeTmpBytearray);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysThemeTmpBytearray.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/theme/sysThemeTmpBytearray/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("sysThemeTmpBytearray.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/theme/sysThemeTmpBytearray/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpBytearrayQuery query = new SysThemeTmpBytearrayQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		*/
		if(!loginContext.isSystemAdministrator()){
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
		int total = sysThemeTmpBytearrayService.getSysThemeTmpBytearrayCountByQueryCriteria(query);
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
			List<SysThemeTmpBytearray> list = sysThemeTmpBytearrayService.getSysThemeTmpBytearraysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpBytearray sysThemeTmpBytearray : list) {
					JSONObject rowJSON = sysThemeTmpBytearray.toJsonObject();
					rowJSON.put("id", sysThemeTmpBytearray.getId());
					rowJSON.put("rowId", sysThemeTmpBytearray.getId());
					rowJSON.put("sysThemeTmpBytearrayId", sysThemeTmpBytearray.getId());
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


	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpBytearrayQuery query = new SysThemeTmpBytearrayQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysThemeTmpBytearrayDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpBytearrayService.getSysThemeTmpBytearrayCountByQueryCriteria(query);
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
			List<SysThemeTmpBytearray> list = sysThemeTmpBytearrayService.getSysThemeTmpBytearraysByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (SysThemeTmpBytearray sysThemeTmpBytearray : list) {
					JSONObject rowJSON = sysThemeTmpBytearray.toJsonObject();
					rowJSON.put("id", sysThemeTmpBytearray.getId());
					rowJSON.put("sysThemeTmpBytearrayId", sysThemeTmpBytearray.getId());
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
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

                String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
 

		return new ModelAndView("/theme/sysThemeTmpBytearray/list", modelMap);
	}

}
