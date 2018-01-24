/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.datamgr.web.rest;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
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
import com.glaf.datamgr.domain.FileHistory;
import com.glaf.datamgr.query.FileHistoryQuery;
import com.glaf.datamgr.service.FileHistoryService;
import com.glaf.datamgr.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller("/rs/sys/update/pkg")
@Path("/rs/sys/update/pkg")
public class UpdatePackageResource {
	protected static final Log logger = LogFactory.getLog(UpdatePackageResource.class);

	protected FileHistoryService fileHistoryService;

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FileHistoryQuery query = new FileHistoryQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		FileHistoryDomainFactory.processDataRequest(dataRequest);
		String fileNameLike = request.getParameter("fileNameLike");
		String startDay = request.getParameter("startDay");
		String endDay = request.getParameter("endDay");
		if (StringUtils.isNotEmpty(startDay)) {
			Date dateStart = DateUtils.toDate(startDay);
			query.setLastModifiedGreaterThanOrEqual(dateStart.getTime());
		}

		if (StringUtils.isNotEmpty(endDay)) {
			Date dateEnd = DateUtils.toDate(endDay + " 23:59:59");
			query.setLastModifiedLessThanOrEqual(dateEnd.getTime());
		}

		if (StringUtils.isNotEmpty(fileNameLike)) {
			query.setFileNameLike("%" + fileNameLike + "%");
		}

		query.type("pkg");
		query.setDeleteFlag(0);

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
		int total = fileHistoryService.getFileHistoryCountByQueryCriteria(query);
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
				if (StringUtils.equals(orderName, "modifyDate_datetime")) {
					query.setSortColumn("lastModified");
				} else {
					query.setSortColumn(orderName);
				}
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<FileHistory> list = fileHistoryService.getFileHistoriesByQueryCriteria(start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				for (FileHistory fileHistory : list) {
					JSONObject rowJSON = fileHistory.toJsonObject();
					rowJSON.put("id", fileHistory.getFileId());
					rowJSON.put("fileHistoryId", fileHistory.getFileId());
					rowJSON.put("startIndex", ++start);
					if (StringUtils.equals(fileHistory.getPkgStatus(), "NEW")) {
						rowJSON.put("pkgStatus_title", "未更新");
					} else if (StringUtils.equals(fileHistory.getPkgStatus(), "UPDATE_SUCESS")) {
						rowJSON.put("pkgStatus_title", "更新成功");
					}
					rowsJSON.add(rowJSON);
				}
				result.put("rows", rowsJSON);
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setFileHistoryService(FileHistoryService fileHistoryService) {
		this.fileHistoryService = fileHistoryService;
	}

}
