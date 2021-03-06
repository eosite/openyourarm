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
package com.glaf.base.modules.sys.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.glaf.base.helper.TreeHelper;
import com.glaf.base.modules.sys.model.CellTreedot;
import com.glaf.base.modules.sys.model.ITree;
import com.glaf.base.modules.sys.service.ICellTreedotService;
import com.glaf.core.util.RequestUtils;

@Controller("/rs/cell/treedot")
@Path("/rs/cell/treedot")
public class CellTreedotResource {

	protected static final Log logger = LogFactory
			.getLog(CellTreedotResource.class);

	protected ICellTreedotService cellTreedotService;

	@javax.annotation.Resource
	public void setCellTreedotService(ICellTreedotService cellTreedotService) {
		this.cellTreedotService = cellTreedotService;
	}

	@GET
	@POST
	@Path("treeJson")
	@Produces({ MediaType.APPLICATION_JSON })
	@ResponseBody
	public byte[] treeJson(@Context HttpServletRequest request,
			@Context UriInfo uriInfo) throws IOException {
		JSONArray result = new JSONArray();
		int parentId = RequestUtils.getInt(request, "parentId", -1);
		List<CellTreedot> rows = cellTreedotService
				.getAllChildrenCellTreedots(parentId);
		if (rows != null && !rows.isEmpty()) {
			List<ITree> treeModels = new ArrayList<ITree>();
			for (CellTreedot treedot : rows) {
				treeModels.add(treedot);
			}
			TreeHelper helper = new TreeHelper();
			JSONArray jsonArray = helper.getTreeJSONArray(treeModels, false);
			// logger.debug(jsonArray.toJSONString());
			return jsonArray.toJSONString().getBytes("UTF-8");
		}
		return result.toString().getBytes("UTF-8");
	}

}
