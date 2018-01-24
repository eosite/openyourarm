package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.identity.User;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.util.http.HttpClientUtils;
import com.glaf.form.core.domain.UserSqlite;
import com.glaf.form.core.query.UserSqliteQuery;
import com.glaf.form.core.service.UserSqliteService;
import com.glaf.form.core.util.SqliteOperateUtils;
import com.glaf.form.core.util.UserSqliteDomainFactory;


/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("form/dataset/userSqlite")
@RequestMapping("form/dataset/userSqlite")
public class UserSqliteController {
	protected static final Log logger = LogFactory.getLog(UserSqliteController.class);

	protected UserSqliteService userSqliteService;
	
	@Autowired
	protected SqliteOperateUtils sqliteOperateUtils;

	public UserSqliteController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.userSqliteService")
	public void setUserSqliteService(UserSqliteService userSqliteService) {
		this.userSqliteService = userSqliteService;
	}

	@ResponseBody
	@RequestMapping("/saveUserSqlite")
	public byte[] saveUserSqlite(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		UserSqlite userSqlite = new UserSqlite();
		try {
			Tools.populate(userSqlite, params);

			Date nowDate = new Date();
			if (userSqlite.getId() == null) {
				userSqlite.setSqliteCode(UUID.randomUUID().toString());
				userSqlite.setUserId(actorId);
				userSqlite.setCreateDate(nowDate);
				userSqlite.setCreateBy(actorId);
			} else {
				UserSqlite oldUserSqlite =  this.userSqliteService.getUserSqlite(userSqlite.getId());
				if(oldUserSqlite == null ){
					return ResponseUtils.responseJsonResult(false);
				}
				userSqlite.setSqliteCode(oldUserSqlite.getSqliteCode());
				userSqlite.setUpdateDate(nowDate);
				userSqlite.setUpdateBy(actorId);
			}
			userSqlite.setStatus(1); // 设置状态为正在生成

			this.userSqliteService.save(userSqlite);

			// 创建线程并开启线程，生成SQLITE文件
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						JSONObject retJson = sqliteOperateUtils.createSqliteFile(userSqlite);
						String status = retJson.getString("status");
						if(status != null && status.equals("200")){
							userSqlite.setFileDate(new Date());
							userSqlite.setStatus(2);	//设置生成完毕
						}else{
							userSqlite.setStatus(0);	//设置生成错误
						}
						userSqlite.setErrorMessage(retJson.getString("message"));
						userSqliteService.save(userSqlite);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		UserSqliteQuery query = new UserSqliteQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setDataRequest(dataRequest);
		UserSqliteDomainFactory.processDataRequest(dataRequest);

		int start = 0;
		int limit = 1;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = 1;
		}

		JSONObject result = new JSONObject();
		int total = userSqliteService.getUserSqliteCountByQueryCriteria(query);
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

			List<UserSqlite> list = userSqliteService.getUserSqlitesByQueryCriteria(start, limit, query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (UserSqlite userSqlite : list) {
					JSONObject rowJSON = userSqlite.toJsonObject();
					rowJSON.put("id", userSqlite.getId());
					rowJSON.put("userSqliteId", userSqlite.getId());
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

	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		
		String sqliteCode = RequestUtils.getString(request, "sqliteCode");
		
		if(sqliteCode != null){
			UserSqliteQuery query = new UserSqliteQuery();
			query.setSqliteCode(sqliteCode);
			List<UserSqlite> list = userSqliteService.list(query);
			if(list != null && list.size() > 0){
				UserSqlite userSqlite = list.get(0);
				String zipFile = SystemProperties.getConfigRootPath() + "/tablesqlite/" + userSqlite.getSqliteCode() +".db";
				Path zipFilePath = Paths.get(zipFile);
				if(Files.exists(zipFilePath)){
					ResponseUtils.download(request, response, Files.newInputStream(zipFilePath), userSqlite.getSqliteCode() +".db");
				}
			}
		}
		
	}
	
	
	@RequestMapping("/getBySqliteCode")
	@ResponseBody
	public byte[] getBySqliteCode(HttpServletRequest request) throws Exception {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		
		String sqliteCode = RequestUtils.getString(request, "sqliteCode");
		
		UserSqliteQuery query = new UserSqliteQuery();
		query.setSqliteCode(sqliteCode);
		List<UserSqlite> list = userSqliteService.list(query);
		UserSqlite userSqlite = null;
		if(list != null && list.size() > 0){
			userSqlite = list.get(0);
		}
		
		return userSqlite.toJsonObject().toJSONString().getBytes("UTF-8");
	}
	
	@RequestMapping("view/{category}")
	public ModelAndView definedEx(HttpServletRequest request, @PathVariable String category) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/form/sqlite/" + category);
	}
	
	@RequestMapping("/getDefaultData")
	@ResponseBody
	public byte[] getDefaultData(HttpServletRequest request) throws Exception {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		
		String defaultDataFile = SystemProperties.getConfigRootPath() + "/views/form/sqlite/sqliteDefaultDatas.js";
		
		Path defaultPath = Paths.get(defaultDataFile);
		if(Files.exists(defaultPath)){
			return FileUtils.getBytes(Files.newInputStream(defaultPath));
		}
		return "[]".getBytes("UTF-8");
	}
}
