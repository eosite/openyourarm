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
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.domain.TreeTableSynthetic;
import com.glaf.datamgr.domain.TreeTableSyntheticRule;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.SqlDefinitionService;
import com.glaf.datamgr.service.TreeTableSyntheticService;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/treeTableSyntheticRule")
public class TreeTableSyntheticRuleResource {
	protected static final Log logger = LogFactory.getLog(TreeTableSyntheticRuleResource.class);

	protected DataSetService dataSetService;

	protected SqlDefinitionService sqlDefinitionService;

	protected TreeTableSyntheticService treeTableSyntheticService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		long syntheticId = ParamUtils.getLong(params, "syntheticId");

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
		TreeTableSynthetic treeTableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(syntheticId);

		if (treeTableSynthetic != null && treeTableSynthetic.getRules() != null
				&& !treeTableSynthetic.getRules().isEmpty()) {
			int total = treeTableSynthetic.getRules().size();
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			List<TreeTableSyntheticRule> list = treeTableSynthetic.getRules();

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (TreeTableSyntheticRule r : list) {
					JSONObject rowJSON = r.toJsonObject();
					rowJSON.put("id", r.getId());
					rowJSON.put("ruleId", r.getId());
					rowJSON.put("startIndex", ++start);

					if (StringUtils.equals(r.getType(), "Date")) {
						rowJSON.put("typeName", "日期时间型");
					} else if (StringUtils.equals(r.getType(), "Integer")) {
						rowJSON.put("typeName", "整数型");
					} else if (StringUtils.equals(r.getType(), "Long")) {
						rowJSON.put("typeName", "长整数型");
					} else if (StringUtils.equals(r.getType(), "Double")) {
						rowJSON.put("typeName", "数值型");
					} else if (StringUtils.equals(r.getType(), "Boolean")) {
						rowJSON.put("typeName", "逻辑型");
					} else {
						rowJSON.put("typeName", "字符串型");
					}

					if (StringUtils.isNotEmpty(r.getDatasetId())) {
						DataSet dataSet = dataSetService.getDataSet(r.getDatasetId());
						if (dataSet != null) {
							rowJSON.put("datasetId", dataSet.getId());
							rowJSON.put("datasetName", dataSet.getName());
							rowJSON.put("datasetTitle", dataSet.getTitle());
						}
					}
					if (r.getSqlDefId() > 0) {
						SqlDefinition sqlDefinition = sqlDefinitionService.getSqlDefinition(r.getSqlDefId());
						if (sqlDefinition != null) {
							rowJSON.put("sqlDefId", sqlDefinition.getId());
							rowJSON.put("sqlDefName", sqlDefinition.getName());
							rowJSON.put("sqlDefTitle", sqlDefinition.getTitle());
						}
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
		treeTableSyntheticService.deleteRuleById(RequestUtils.getLong(request, "id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

	@javax.annotation.Resource
	public void setTreeTableSyntheticService(TreeTableSyntheticService treeTableSyntheticService) {
		this.treeTableSyntheticService = treeTableSyntheticService;
	}

}
