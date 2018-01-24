package com.glaf.form.web.springmvc;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.UserSqliteUpload;
import com.glaf.form.core.query.UserSqliteUploadQuery;
import com.glaf.form.core.service.UserSqliteUploadService;
import com.glaf.form.core.util.SqliteOperateUtils;
import com.glaf.form.core.util.UserSqliteUploadDomainFactory;


/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("form/dataset/userSqliteUpload")
@RequestMapping("form/dataset/userSqliteUpload")
public class UserSqliteUploadController {
	protected static final Log logger = LogFactory.getLog(UserSqliteUploadController.class);

	protected UserSqliteUploadService userSqliteUploadService;
	
	@Autowired
	protected SqliteOperateUtils sqliteOperateUtils;

	public UserSqliteUploadController() {

	}

	@javax.annotation.Resource(name = "com.glaf.form.core.service.userSqliteUploadService")
	public void setUserSqliteUploadService(UserSqliteUploadService userSqliteUploadService) {
		this.userSqliteUploadService = userSqliteUploadService;
	}
	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
	        LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		UserSqliteUploadQuery query = new UserSqliteUploadQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		UserSqliteUploadDomainFactory.processDataRequest(dataRequest);

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
		int total = userSqliteUploadService.getUserSqliteUploadCountByQueryCriteria(query);
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
			List<UserSqliteUpload> list = userSqliteUploadService.getUserSqliteUploadsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				 
				result.put("rows", rowsJSON);
				 
				for (UserSqliteUpload userSqliteUpload : list) {
					JSONObject rowJSON = userSqliteUpload.toJsonObject();
					rowJSON.put("id", userSqliteUpload.getId());
					rowJSON.put("userSqliteUploadId", userSqliteUpload.getId());
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
	
	@RequestMapping("/uploadByInit")
	@ResponseBody
	public byte[] uploadByInit(HttpServletRequest request) throws Exception {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject ret = new JSONObject();
		
		JSONObject retJson = sqliteOperateUtils.importSqliteFile(null);
		
		return ret.toJSONString().getBytes("UTF-8");
	}
	
	@RequestMapping("/uploadInit")
	@ResponseBody
	public byte[] uploadInit(HttpServletRequest request){
		JSONObject retJson;
		try {
			retJson = sqliteOperateUtils.importSqliteFile(null);
			String status = retJson.getString("status");
			if(status != null && status.equals("200")){
				return ResponseUtils.responseJsonResult(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return ResponseUtils.responseJsonResult(false);
	}
	
	@RequestMapping("/upload")
	@ResponseBody
	public byte[] upload(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") List<MultipartFile> mFiles) throws UnsupportedEncodingException {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject ret = new JSONObject();
		String uploadFileName = RequestUtils.getString(request, "fileName"); 
		for (MultipartFile mFile : mFiles) {
			UserSqliteUpload userSqliteUpload = new UserSqliteUpload();
			try {
				// 保存到服务器目录
				String separator = File.separator;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String projectpath = SystemProperties.getConfigRootPath();
				String dirpath = "/tablesqlite/upload";

				String fileName = mFile.getOriginalFilename();
				String stuffix = fileName.substring(fileName.lastIndexOf("."));

				sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String newFileName = sdf.format(new Date()) + stuffix;

				String fullFilepath = projectpath + dirpath + separator + newFileName;
				String saveServicePath = dirpath + "/" + newFileName;
				String saveServiceLogPath = dirpath + "/" + sdf.format(new Date()) + ".log";

				if (!new File(projectpath + dirpath).exists()) {
					new File(projectpath + dirpath).mkdirs();
				}

				if (!new File(fullFilepath).exists()) {
					new File(fullFilepath).createNewFile();
				}
				if(!new File(projectpath +"/" + saveServiceLogPath).exists()){
					new File(projectpath +"/" + saveServiceLogPath).createNewFile();
				}
				FileUtils.save(fullFilepath, mFile.getInputStream());
				
				if(uploadFileName == null || uploadFileName.isEmpty()){
					uploadFileName = fileName;
				}
				
				userSqliteUpload.setFileName(uploadFileName);
				userSqliteUpload.setLogfilePath(saveServiceLogPath);
				userSqliteUpload.setFilePath(saveServicePath);
				userSqliteUpload.setUploadDate(new Date());
				userSqliteUpload.setUserId(actorId);
				userSqliteUpload.setStatus(1);
				userSqliteUploadService.save(userSqliteUpload);
				
				// 创建线程并开启线程，导入SQLITE文件的表信息
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject retJson = sqliteOperateUtils.importSqliteFile(userSqliteUpload);
							String status = retJson.getString("status");
							if(status != null && status.equals("200")){
								userSqliteUpload.setStatus(2);	//设置生成完毕
							}else{
								userSqliteUpload.setStatus(0);	//设置生成错误
							}
							userSqliteUploadService.save(userSqliteUpload);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				thread.start();
				
				ret.put("status", "200");
				ret.put("message", "文件上传成功！");
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("文件上传失败！");
				ret.put("status", "500");
				ret.put("message", "文件上传失败！");
			} finally {
				
			}
		}
		return ret.toJSONString().getBytes("UTF-8");
	}
	
	@ResponseBody
	@RequestMapping("/downloadLog")
	public void downloadLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		
//		String sqliteCode = RequestUtils.getString(request, "sqliteCode");
//		
//		if(sqliteCode != null){
//			UserSqliteUploadQuery query = new UserSqliteUploadQuery();
//			List<UserSqliteUpload> list = userSqliteUploadService.list(query);
//			
//			String projectpath = request.getSession().getServletContext().getRealPath("/");
//			
//			if(list != null && list.size() > 0){
//				UserSqlite userSqlite = new UserSqlite();
//				String zipFile = SystemProperties.getConfigRootPath() + "/tablesqlite/" + userSqlite.getSqliteCode() +".zip";
//				Path zipFilePath = Paths.get(zipFile);
//				if(Files.exists(zipFilePath)){
//					ResponseUtils.download(request, response, Files.newInputStream(zipFilePath), userSqlite.getSqliteCode() +".zip");
//				}
//			}
//		}
		
	}

	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		
		String id = RequestUtils.getString(request, "id");
		int type = RequestUtils.getInt(request, "type");
		
		if(id != null){
			UserSqliteUpload userSqliteUpload = this.userSqliteUploadService.getUserSqliteUpload(id);
			
			if(userSqliteUpload != null){
				String root = SystemProperties.getConfigRootPath();
				String dbpath = null;
				String fileName = userSqliteUpload.getFileName();
				if(type == 1){
					//下载原文件
					dbpath = root + userSqliteUpload.getFilePath();
				}else if(type == 2){
					//下载日志文件
					dbpath = root + userSqliteUpload.getLogfilePath();
				}
				if(dbpath != null){
					
					Path filePath = Paths.get(dbpath);
					if(fileName == null){
						fileName = filePath.getFileName().toString();
						if(type == 1){
							fileName += ".db";
						}else if(type == 2){
							fileName += ".log";
						}
					}
					if(Files.exists(filePath)){
						ResponseUtils.download(request, response, Files.newInputStream(filePath), fileName);
					}
				}
			}
		}
	}
}
