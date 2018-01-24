package com.glaf.datamgr.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.DataRequest;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

import com.glaf.datamgr.domain.TreeTableAggregate;
import com.glaf.datamgr.domain.TreeTableAggregateRule;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.TreeTableAggregateService;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/treeTableAggregateRule")
public class TreeTableAggregateRuleResource {
	protected static final Log logger = LogFactory.getLog(TreeTableAggregateRuleResource.class);

	protected SqlDefinitionService sqlDefinitionService;

	protected TreeTableAggregateService treeTableAggregateService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		long aggregateId = ParamUtils.getLong(params, "aggregateId");

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
		TreeTableAggregate treeTableAggregate = treeTableAggregateService.getTreeTableAggregate(aggregateId);

		if (treeTableAggregate != null && treeTableAggregate.getRules() != null
				&& !treeTableAggregate.getRules().isEmpty()) {
			int total = treeTableAggregate.getRules().size();
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			List<TreeTableAggregateRule> list = treeTableAggregate.getRules();

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeTableAggregateRule r : list) {
					JSONObject rowJSON = r.toJsonObject();
					rowJSON.put("id", r.getId());
					rowJSON.put("ruleId", r.getId());
					rowJSON.put("startIndex", ++start);

					if (StringUtils.equals(r.getTargetColumnType(), "Integer")) {
						rowJSON.put("typeName", "整数型");
					} else if (StringUtils.equals(r.getTargetColumnType(), "Long")) {
						rowJSON.put("typeName", "长整数型");
					} else if (StringUtils.equals(r.getTargetColumnType(), "Double")) {
						rowJSON.put("typeName", "数值型");
					} else {
						rowJSON.put("typeName", "字符串型");
					}
					rowsJSON.add(rowJSON);
				}
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", 0);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		treeTableAggregateService.deleteRuleById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@javax.annotation.Resource
	public void setTreeTableAggregateService(TreeTableAggregateService treeTableAggregateService) {
		this.treeTableAggregateService = treeTableAggregateService;
	}

}
