package com.glaf.htmleditor.web.springmvc;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.ActionEnter;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.etl.domain.ETLDataTransfer;
import com.glaf.web.domain.PageResource;
import com.glaf.web.query.PageResourceQuery;
import com.glaf.web.service.PageResourceService;

@Controller
@RequestMapping("/ueditor/image")
public class UeditorImageController{
    
	protected PageResourceService pageResourceService;
	
	@RequestMapping("/data")
	@ResponseBody
	public byte[] getData(HttpServletRequest request,
			HttpServletResponse response, String action) throws UnsupportedEncodingException {
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/json");

		int pageNo = RequestUtils.getInteger(request, "page", 1);//
		int limit =  RequestUtils.getInteger(request, "rows", 20);//
		
		int start = (pageNo - 1) * limit;		
		if (start < 0) {
			start = 0;
		}
		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}
		
		JSONObject result = new JSONObject();
		PageResourceQuery query = new PageResourceQuery();
		int total = pageResourceService.getPageResourceCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("start", start);
			result.put("page", pageNo);
			result.put("pageSize", limit);
		
			List<PageResource> list = pageResourceService.getPageResourcesByQueryCriteria(start,limit,query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (PageResource resource : list) {
					JSONObject rowJSON = new JSONObject();
					rowJSON.put("id", resource.getResId());
					rowJSON.put("path", resource.getResPath());
					rowJSON.put("filename", resource.getResName());
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
	
	
    @Resource
	public void setPageResourceService(PageResourceService pageResourceService) {
		this.pageResourceService = pageResourceService;
	}

}
