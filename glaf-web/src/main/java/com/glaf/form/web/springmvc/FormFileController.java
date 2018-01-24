package com.glaf.form.web.springmvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.domain.FormFile;
import com.glaf.form.core.query.FormFileQuery;
import com.glaf.form.core.service.FormFileService;
import com.glaf.form.core.util.FormAttachmentJsonFactory;

@Controller("/form/formfile")
@RequestMapping("/form/formfile")
public class FormFileController {

	protected static final Log logger = LogFactory.getLog(FormFileController.class);

	@Autowired
	protected FormFileService formFileService;

	public FormFileController() {
		// TODO Auto-generated constructor stub
	}

	protected String to_db = "1";

	protected String to_dir = "0";

	@RequestMapping(params = "method=upload")
	@ResponseBody
	public byte[] upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("to") String to,
			@RequestParam("file") List<MultipartFile> mFiles, ModelMap modelMap) throws UnsupportedEncodingException {

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String fileId = request.getParameter("fieldId"); // 获取文件ID
		String randomParent = request.getParameter("randomParent");

		boolean isSuccess = true;
		JSONArray result = new JSONArray();

		for (MultipartFile mFile : mFiles) {
			FormFile atta = new FormFile();
			try {
				if (fileId != null && !fileId.isEmpty()) {
					atta.setId(fileId);
				}
				atta.setParent(randomParent);
				atta.setFileName(mFile.getOriginalFilename());
				atta.setFileSize(mFile.getSize());
				atta.setVision("");
				atta.setStatus(0);
				atta.setCreateBy(loginContext.getActorId());
				atta.setCreateDate(new Date());
				String contentType = mFile.getContentType();
				atta.setFieldType(contentType);
				
				if ("to_db".equalsIgnoreCase(to)) {
					// 保存到数据库
					atta.setType(to_db);
					atta.setFileContent(mFile.getBytes());
					atta.setSaveServicePath(null);
					atta = this.formFileService.save(atta);

				} else if ("to_dir".equalsIgnoreCase(to)) {
					// 保存到服务器目录
					String separator = File.separator;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String projectpath = request.getSession().getServletContext().getRealPath("/");
					String dirpath = "/upload/" + sdf.format(new Date());

					String fileName = mFile.getOriginalFilename();
					String stuffix = fileName.substring(fileName.lastIndexOf("."));

					sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					String newFileName = sdf.format(new Date()) + stuffix;

					String fullFilepath = projectpath + dirpath + separator + newFileName;
					String saveServicePath = dirpath + "/" + newFileName;

					if (!new File(projectpath + dirpath).exists()) {
						new File(projectpath + dirpath).mkdirs();
					}

					if (!new File(fullFilepath).exists()) {
						new File(fullFilepath).createNewFile();
					}

					FileUtils.save(fullFilepath, mFile.getInputStream());

					atta.setType(to_dir);
					atta.setFileContent(null);

					atta.setSaveServicePath(saveServicePath);
					atta = this.formFileService.save(atta);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("文件：" + atta.getFileName() + "上传失败！");
				atta = new FormFile();
				atta.setError("上传失败！");
				isSuccess = false;
			} finally {
				if (isSuccess) {
					result.add(atta.toJsonObject());
				}
			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	@RequestMapping(params = "method=download")
	public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
		InputStream fis = null;
		try {
			// 从数据库中读取
			FormFile atta = this.formFileService.getFormFile(id);

			if (to_db.equals(atta.getType())) {
				ResponseUtils.download(request, response, atta.getFileContent(), atta.getFileName());
			} else if (to_dir.equals(atta.getType())) {
				// 从服务器目录中读取
				String projectpath = request.getSession().getServletContext().getRealPath("/");
				File file = new File(projectpath + atta.getSaveServicePath());
				if (file.exists()) {
					fis = new FileInputStream(file);
					ResponseUtils.download(request, response, fis, atta.getFileName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
	}
	
	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, @RequestBody Map<String, Object> params) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
//		Map<String, Object> params = RequestUtils.getParameterMap(request);
		FormFileQuery query = new FormFileQuery();
		Object gridparams = params.get("params");
		if(gridparams != null){
			String gridparamsStr = gridparams.toString();
			if(!gridparamsStr.isEmpty()){
				JSONObject gridparamsJSON = JSON.parseObject(gridparamsStr);
				Tools.populate(query, gridparamsJSON);
			}
		}
		
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "pageSize");
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
		int total = formFileService.getFormFileCountByQueryCriteria(query);
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

			List<FormFile> list = formFileService.getFormFilesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (FormFile formfile : list) {
					JSONObject rowJSON = formfile.toJsonObject();
					rowJSON.put("id", formfile.getId());
					rowJSON.put("rowId", formfile.getId());
					rowJSON.put("imageId", formfile.getId());
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
}
