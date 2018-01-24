package com.glaf.isdp.website.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.isdp.domain.TreepInfo;
import com.glaf.isdp.service.DotUseService;
import com.glaf.isdp.service.TreepInfoService;

///website/isdp/websiteWBS/cellSearch?databaseId=2

@Controller("/isdp/websiteWBS")
@RequestMapping("/isdp/websiteWBS")
public class WebsiteWbsController {
	protected static final Log logger = LogFactory.getLog(WebsiteWbsController.class);

	protected IDatabaseService databaseService;

	protected TreepInfoService treepInfoService;

	protected DotUseService dotUseService;

	@RequestMapping("/cellSearch")
	public ModelAndView cellSearch(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String currentSystemName = Environment.getCurrentSystemName();
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
		request.setAttribute("systemName", systemName);
		long databaseId = RequestUtils.getLong(request, "databaseId");
		request.setAttribute("databaseId", databaseId);

		String databaseMapping = request.getParameter("databaseMapping");
		Database database = databaseService.getDatabaseById(databaseId);
		if (StringUtils.isNotEmpty(databaseMapping)) {
			database = databaseService.getDatabaseByMapping(databaseMapping);
		}
		if (database != null) {
			request.setAttribute("databaseId", database.getId());
		}

		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> databases = databaseService.list(query);
		request.setAttribute("databases", databases);

		return new ModelAndView("/isdp/website/cell_search");
	}

	@RequestMapping("/cellSearchCheckBox")
	public ModelAndView cellSearchCheckBox(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String currentSystemName = Environment.getCurrentSystemName();
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
		request.setAttribute("systemName", systemName);
		long databaseId = RequestUtils.getLong(request, "databaseId");
		request.setAttribute("databaseId", databaseId);

		String databaseMapping = request.getParameter("databaseMapping");
		Database database = databaseService.getDatabaseById(databaseId);
		if (StringUtils.isNotEmpty(databaseMapping)) {
			database = databaseService.getDatabaseByMapping(databaseMapping);
		}
		if (database != null) {
			request.setAttribute("databaseId", database.getId());
		}

		return new ModelAndView("/isdp/website/cell_search_checkbox");
	}

	@GET
	@POST
	@RequestMapping("/cellTableList")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] cellTableList(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String callback = RequestUtils.getString(request, "callback", "callback");
		int indexId = RequestUtils.getInteger(request, "indexId", 0);
		String currentSystemName = Environment.getCurrentSystemName();

		String callbackString = "";
		JSONObject result = new JSONObject();
		try {

			int page = RequestUtils.getInt(request, "page", 1);
			int limit = RequestUtils.getInt(request, "rows", PageResult.DEFAULT_PAGE_SIZE);
			boolean showSubTable = RequestUtils.getBoolean(request, "showSubTable");

			int start = 0;

			start = (page - 1) * limit;

			if (start < 0) {
				start = 0;
			}

			if (limit <= 0) {
				limit = PageResult.DEFAULT_PAGE_SIZE;
			}

			String base64SystemName = RequestUtils.getString(request, "systemName");
			if (StringUtils.isNotEmpty(base64SystemName)) {
				String systemName = new String(Base64.decodeBase64(base64SystemName), "UTF-8");
				Environment.setCurrentSystemName(systemName);
			}

			Long databaseId = RequestUtils.getLong(request, "databaseId");
			Database database = databaseService.getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}

			String databaseMapping = request.getParameter("databaseMapping");
			database = databaseService.getDatabaseById(databaseId);
			if (StringUtils.isNotEmpty(databaseMapping)) {
				database = databaseService.getDatabaseByMapping(databaseMapping);
			}
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}

			StringBuffer buffer = new StringBuffer();
			// buffer.append("http://" + request.getLocalAddr() + ":"
			// buffer.append("http://" + "zygs.fzmt.com.cn" + ":" +
			// request.getLocalPort());

			// buffer.append(SystemConfig.getString("wbs_service_url"));
			buffer.append(request.getContextPath());
			String readURL = buffer.toString() + "/website/isdp/websiteWBS/cellweb?systemName=" + base64SystemName
					+ "&databaseId=" + databaseId;
			String downloadURL = buffer.toString() + "/website/cell/websiteFiledotUse/download?databaseCode="
					+ base64SystemName + "&databaseId=" + databaseId;

			if (showSubTable) {
				TreepInfo treepInfo = treepInfoService.getTreepInfo(indexId);
				int total = dotUseService.getDotUseCellFillFormCountByTreepinfoId(treepInfo.getId());
				result.put("total", total);

				if (total > 0) {
					JSONArray rows = new JSONArray();
					List<Map<String, Object>> list = dotUseService.getDotUseCellFillFormByTreepinfoId(start, limit,
							treepInfo.getId());
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = list.get(i);

						JSONObject row = new JSONObject();
						row.put("id", map.get("ID").toString());
						row.put("name", map.get("name").toString());

						String fileID = Base64.encodeBase64String(map.get("fileID").toString().getBytes("UTF-8"));
						row.put("fileID", fileID);
						row.put("readURL", readURL + "&fileID=" + fileID);
						row.put("downloadURL", downloadURL + "&fileID=" + fileID);

						rows.add(row);
					}

					result.put("rows", rows);
				} else {
					JSONArray rows = new JSONArray();
					result.put("rows", rows);
				}
			} else {
				int total = dotUseService.getDotUseCellFillFormCountByIndexId(indexId);
				result.put("total", total);

				if (total > 0) {
					JSONArray rows = new JSONArray();
					List<Map<String, Object>> list = dotUseService.getDotUseCellFillFormByIndexId(start, limit,
							indexId);
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = list.get(i);

						JSONObject row = new JSONObject();
						row.put("id", map.get("ID").toString());
						row.put("name", map.get("name").toString());

						String fileID = Base64.encodeBase64String(map.get("fileID").toString().getBytes("UTF-8"));
						row.put("fileID", fileID);
						row.put("readURL", readURL + "&fileID=" + fileID);
						row.put("downloadURL", downloadURL + "&fileID=" + fileID);

						rows.add(row);
					}

					result.put("rows", rows);
				} else {
					JSONArray rows = new JSONArray();
					result.put("rows", rows);
				}
			}
		} catch (Exception e) {
			logger.error("Error=" + e.getMessage());
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		callbackString = callback + "(" + result.toJSONString() + ")";

		return callbackString.getBytes("UTF-8");
	}

	@RequestMapping("/cellweb")
	public ModelAndView cellweb(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String currentSystemName = Environment.getCurrentSystemName();
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
		request.setAttribute("systemName", systemName);
		long databaseId = RequestUtils.getLong(request, "databaseId");
		request.setAttribute("databaseId", databaseId);

		String databaseMapping = request.getParameter("databaseMapping");
		Database database = databaseService.getDatabaseById(databaseId);
		if (StringUtils.isNotEmpty(databaseMapping)) {
			database = databaseService.getDatabaseByMapping(databaseMapping);
		}
		if (database != null) {
			request.setAttribute("databaseId", database.getId());
		}

		String fileID = RequestUtils.getString(request, "fileID");
		request.setAttribute("fileID", fileID);

		return new ModelAndView("/isdp/website/cellweb");
	}

	@GET
	@POST
	@RequestMapping("/getDatabases")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getDatabases(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		DatabaseQuery query = new DatabaseQuery();
		query.active("1");
		List<Database> databases = databaseService.list(query);
		JSONArray jsonArray = new JSONArray();
		int i = 0;
		for (Database db : databases) {
			JSONObject jobject = new JSONObject();
			jobject.put("id", String.valueOf(db.getId()));
			jobject.put("title", String.valueOf(db.getTitle()));
			if (i == 0) {
				jobject.put("selected", true);
			}
			jsonArray.add(jobject);
			i++;
		}
		// System.out.println("===========getDatabases打印测试："+jsonArray.toString());
		return jsonArray.toString().getBytes("UTF-8");
	}

	@GET
	@POST
	@RequestMapping("/getProjects")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getProjects(@Context HttpServletRequest request) throws IOException {
		JSONArray results = new JSONArray();

		return results.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@RequestMapping("/getTreepInfoJSON")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getTreepInfoJSON(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String callback = RequestUtils.getString(request, "callback", "callback");
		Integer indexId = RequestUtils.getInteger(request, "indexId", -1);
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "systemName");
		String token = request.getParameter("token");
		logger.debug("request params systemName:" + databaseCode);
		if (StringUtils.isNotEmpty(databaseCode)) {
			databaseCode = new String(Base64.decodeBase64(databaseCode), "UTF-8");
			logger.debug("base64 decode requst params:" + databaseCode);
			Environment.setCurrentSystemName(databaseCode);
		}

		String callbackString = "";
		JSONArray result = new JSONArray();
		try {
			Long databaseId = RequestUtils.getLong(request, "databaseId");
			logger.debug("requst params databaseId:" + databaseId);

			String databaseMapping = request.getParameter("databaseMapping");
			Database database = databaseService.getDatabaseById(databaseId);
			if (StringUtils.isNotEmpty(databaseMapping)) {
				database = databaseService.getDatabaseByMapping(databaseMapping);
			}
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
				if (!StringUtils.equals(token, database.getToken())) {
					throw new RuntimeException("token not match.");
				}
			}

			List<TreepInfo> list = treepInfoService.getTreepInfoListByParentId(indexId);
			for (TreepInfo model : list) {
				JSONObject rowJSON = new JSONObject();
				rowJSON.put("indexName", model.getIndexName());
				rowJSON.put("name", model.getIndexName());
				rowJSON.put("indexId", model.getIndexId());
				rowJSON.put("parentId", model.getParentId());
				rowJSON.put("level", model.getLevel());
				if (model.getChildrenCount() == null || model.getChildrenCount() == 0) {
					rowJSON.put("isParent", false);
				} else {
					rowJSON.put("isParent", true);
				}
				result.add(rowJSON);
			}
		} catch (Exception e) {
			logger.error("Error=" + e.getMessage());
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(systemName);
		}

		callbackString = callback + "(" + result.toJSONString() + ")";

		return callbackString.getBytes("UTF-8");
	}

	@Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Resource(name = "com.glaf.isdp.service.dotUseService")
	public void setDotUseService(DotUseService dotUseService) {
		this.dotUseService = dotUseService;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.treepInfoService")
	public void setTreepInfoService(TreepInfoService treepInfoService) {
		this.treepInfoService = treepInfoService;
	}

}
