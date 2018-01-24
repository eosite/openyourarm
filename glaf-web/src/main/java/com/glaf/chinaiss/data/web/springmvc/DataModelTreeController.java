package com.glaf.chinaiss.data.web.springmvc;

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
 
import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;
import com.glaf.chinaiss.data.service.*;
import com.glaf.chinaiss.data.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/data/model/tree")
@RequestMapping("/data/model/tree")
public class DataModelTreeController {
	protected static final Log logger = LogFactory.getLog(DataModelTreeController.class);

	protected DataModelTreeService dataModelTreeService;

	public DataModelTreeController() {

	}

        @javax.annotation.Resource(name = "com.glaf.chinaiss.data.service.dataModelTreeService")
	public void setDataModelTreeService(DataModelTreeService dataModelTreeService) {
		this.dataModelTreeService = dataModelTreeService;
	}


	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
        params.remove("status");
		params.remove("wfStatus");

		DataModelTree dataModelTree = new DataModelTree();
		Tools.populate(dataModelTree, params);

                dataModelTree.setName(request.getParameter("name"));
                dataModelTree.setCode(request.getParameter("code"));
                dataModelTree.setTreeId(request.getParameter("treeId"));
                dataModelTree.setTopId(request.getParameter("topId"));
                dataModelTree.setParentId(request.getParameter("parentId"));
                dataModelTree.setCreateBy(request.getParameter("createBy"));
                dataModelTree.setCreateDate(RequestUtils.getDate(request, "createDate"));
                dataModelTree.setUpdateBy(request.getParameter("updateBy"));
                dataModelTree.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		
		//dataModelTree.setCreateBy(actorId);
         
		dataModelTreeService.save(dataModelTree);   

		return this.list(request, modelMap);
	}

        @ResponseBody
	@RequestMapping("/saveDataModelTree")
	public byte[] saveDataModelTree(HttpServletRequest request) { 
	        User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
	        Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelTree dataModelTree = new DataModelTree();
		try {
		    Tools.populate(dataModelTree, params);
                    dataModelTree.setName(request.getParameter("name"));
                    dataModelTree.setCode(request.getParameter("code"));
                    dataModelTree.setTreeId(request.getParameter("treeId"));
                    dataModelTree.setTopId(request.getParameter("topId"));
                    dataModelTree.setParentId(request.getParameter("parentId"));
                    dataModelTree.setCreateBy(request.getParameter("createBy"));
                    dataModelTree.setCreateDate(RequestUtils.getDate(request, "createDate"));
                    dataModelTree.setUpdateBy(request.getParameter("updateBy"));
                    dataModelTree.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
		    //dataModelTree.setCreateBy(actorId);
		    this.dataModelTreeService.save(dataModelTree);

		    return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}


	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DataModelTree saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
                User user = RequestUtils.getUser(request);
		String actorId =  user.getActorId();
		DataModelTree dataModelTree = new DataModelTree();
		try {
		    Tools.populate(dataModelTree, model);
                    dataModelTree.setName(ParamUtils.getString(model, "name"));
                    dataModelTree.setCode(ParamUtils.getString(model, "code"));
                    dataModelTree.setTreeId(ParamUtils.getString(model, "treeId"));
                    dataModelTree.setTopId(ParamUtils.getString(model, "topId"));
                    dataModelTree.setParentId(ParamUtils.getString(model, "parentId"));
                    dataModelTree.setCreateBy(ParamUtils.getString(model, "createBy"));
                    dataModelTree.setCreateDate(ParamUtils.getDate(model, "createDate"));
                    dataModelTree.setUpdateBy(ParamUtils.getString(model, "updateBy"));
                    dataModelTree.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
		    dataModelTree.setCreateBy(actorId);
		    this.dataModelTreeService.save(dataModelTree);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    logger.error(ex);
		}
		return dataModelTree;
	}


    @RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                params.remove("status");
		params.remove("wfStatus");
                
                DataModelTree dataModelTree = dataModelTreeService.getDataModelTree(request.getParameter("id"));

		Tools.populate(dataModelTree, params);

                dataModelTree.setName(request.getParameter("name"));
                dataModelTree.setCode(request.getParameter("code"));
                dataModelTree.setTreeId(request.getParameter("treeId"));
                dataModelTree.setTopId(request.getParameter("topId"));
                dataModelTree.setParentId(request.getParameter("parentId"));
                dataModelTree.setCreateBy(request.getParameter("createBy"));
                dataModelTree.setCreateDate(RequestUtils.getDate(request, "createDate"));
                dataModelTree.setUpdateBy(request.getParameter("updateBy"));
                dataModelTree.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
	
		dataModelTreeService.save(dataModelTree);   

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
					DataModelTree dataModelTree = dataModelTreeService.getDataModelTree(String.valueOf(x));
					/**
		              * 此处业务逻辑需自行调整
		              */
					 //TODO
					if (dataModelTree != null && (StringUtils.equals(dataModelTree.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						//dataModelTree.setDeleteFlag(1);
						dataModelTreeService.save(dataModelTree);
					}
				}
			}
		     return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataModelTree dataModelTree = dataModelTreeService
					.getDataModelTree(String.valueOf(id));
			/**
		     * 此处业务逻辑需自行调整
		     */
		    //TODO
			if (dataModelTree != null && ( StringUtils.equals(dataModelTree.getCreateBy(), loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
				//dataModelTree.setDeleteFlag(1);
				//dataModelTreeService.save(dataModelTree);
				dataModelTreeService.deleteById(id);
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
                DataModelTree dataModelTree = dataModelTreeService.getDataModelTree(request.getParameter("id"));
		if(dataModelTree != null) {
		    request.setAttribute("dataModelTree", dataModelTree);
		}
	

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataModelTree.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/data/model/dataModelTree/edit", modelMap);
	}


        @RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
                DataModelTree dataModelTree = dataModelTreeService.getDataModelTree(request.getParameter("id"));
		request.setAttribute("dataModelTree", dataModelTree);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataModelTree.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/data/model/dataModelTree/view");
	}

        @RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataModelTree.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/data/model/dataModelTree/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelTreeQuery query = new DataModelTreeQuery();
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
		int total = dataModelTreeService.getDataModelTreeCountByQueryCriteria(query);
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
			List<DataModelTree> list = dataModelTreeService.getDataModelTreesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DataModelTree dataModelTree : list) {
					JSONObject rowJSON = dataModelTree.toJsonObject();
					rowJSON.put("id", dataModelTree.getId());
					rowJSON.put("rowId", dataModelTree.getId());
					rowJSON.put("dataModelTreeId", dataModelTree.getId());
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
		DataModelTreeQuery query = new DataModelTreeQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DataModelTreeDomainFactory.processDataRequest(dataRequest);

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
		int total = dataModelTreeService.getDataModelTreeCountByQueryCriteria(query);
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
			List<DataModelTree> list = dataModelTreeService.getDataModelTreesByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (DataModelTree dataModelTree : list) {
					JSONObject rowJSON = dataModelTree.toJsonObject();
					rowJSON.put("id", dataModelTree.getId());
					rowJSON.put("dataModelTreeId", dataModelTree.getId());
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
 

		return new ModelAndView("/data/model/dataModelTree/list", modelMap);
	}

}
