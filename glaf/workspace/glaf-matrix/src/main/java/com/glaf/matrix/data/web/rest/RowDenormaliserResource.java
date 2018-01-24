package com.glaf.matrix.data.web.rest;

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
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.Tools;

import com.glaf.matrix.data.domain.RowDenormaliser;
import com.glaf.matrix.data.query.RowDenormaliserQuery;
import com.glaf.matrix.data.service.RowDenormaliserService;
import com.glaf.matrix.data.util.RowDenormaliserDomainFactory;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/sys/rowDenormaliser")
public class RowDenormaliserResource {
	protected static final Log logger = LogFactory.getLog(RowDenormaliserResource.class);

	protected RowDenormaliserService rowDenormaliserService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RowDenormaliserQuery query = new RowDenormaliserQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		RowDenormaliserDomainFactory.processDataRequest(dataRequest);

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
		int total = rowDenormaliserService.getRowDenormaliserCountByQueryCriteria(query);
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

			List<RowDenormaliser> list = rowDenormaliserService.getRowDenormalisersByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (RowDenormaliser rowDenormaliser : list) {
					JSONObject rowJSON = rowDenormaliser.toJsonObject();
					rowJSON.put("id", rowDenormaliser.getId());
					rowJSON.put("rowDenormaliserId", rowDenormaliser.getId());
					rowJSON.put("startIndex", ++start);
					if (rowDenormaliser.getTransformStatus() == 1) {
						rowJSON.put("transformStatusText", "成功");
					} else if (rowDenormaliser.getTransformStatus() == -1) {
						rowJSON.put("transformStatusText", "失败");
					}
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

	@javax.annotation.Resource(name = "com.glaf.matrix.data.service.rowDenormaliserService")
	public void setRowDenormaliserService(RowDenormaliserService rowDenormaliserService) {
		this.rowDenormaliserService = rowDenormaliserService;
	}

}
