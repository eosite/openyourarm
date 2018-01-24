package com.glaf.format.web.springmvc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jgroups.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.factory.RedisFileStorageFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.service.IFormAttachmentService;

/**
 * 文件上传接口
 * 
 * @author Administrator
 *
 */

@Controller("/upload/file")
@RequestMapping("/upload/file")
public class UploadFileController {

	protected IFormAttachmentService formAttachmentService;

	@Resource
	public void setFormAttachmentService(IFormAttachmentService formAttachmentService) {
		this.formAttachmentService = formAttachmentService;
	}

	@RequestMapping("/upload")
	@ResponseBody
	public byte[] upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile mFile) throws Exception {
		String actorId = RequestUtils.getActorId(request);
		if (StringUtils.isEmpty(actorId)) {
			return null;
		}
		String id = RequestUtils.getString(request, "id", null);
		StringBuffer names = new StringBuffer("");
		FormAttachment atta = new FormAttachment();
		try {
			if (StringUtils.isNotEmpty(id)) {
				atta = formAttachmentService.getFormAttachmentById(id);
				atta.setUpdateBy(actorId);
				atta.setUpdateDate(new Date());
			}else{
				atta.setCreateBy(actorId);
				atta.setCreateDate(new Date());
				atta.setParent(UUID.randomUUID().toString());
				atta.setVersion("");
				atta.setStatus(0);
				atta.setType("0");
				atta.setFileContent(null);
			}
			atta.setFileName(mFile.getOriginalFilename());
			atta.setFileSize(mFile.getSize());
			names.append(mFile.getOriginalFilename());

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

			atta.setSaveServicePath(saveServicePath);
			atta = this.formAttachmentService.save(atta);

		} catch (Exception e) {
			atta = new FormAttachment();
			e.printStackTrace();
		}

		return atta.toJsonObject().toJSONString().getBytes("UTF-8");
		// return JSONObject.toJSON(atta).toString().getBytes("UTF-8");
	}

	private byte[] getFileContentById(String attaId) {
		byte[] data = null;
		String cacheKey = "form_attach_" + attaId;
		if (SystemConfig.getBoolean("use_file_cache")) {
			try {
				data = RedisFileStorageFactory.getInstance().getData(cacheKey);
			} catch (Exception ex) {
			}
		}
		if (data != null) {
			return data;
		}
		return data;
	}

	protected static final String to_db = "1";

	protected static final String to_dir = "0";

	@RequestMapping("/downloadById")
	public void downloadById(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String sid) {
		String actorId = RequestUtils.getActorId(request);
		if (StringUtils.isEmpty(actorId)) {
			return;
		}
		try {
			byte[] data = getFileContentById(sid);
			if (data == null) {
				// 加入缓存
				// 从数据库中读取
				FormAttachment atta = this.formAttachmentService.getFormAttachmentById(sid);
				if (to_db.equals(atta.getType())) {
					ResponseUtils.download(request, response, atta.getFileContent(), atta.getFileName());
				} else if (to_dir.equals(atta.getType())) {
					// 从服务器目录中读取,获取原图
					String projectpath = request.getSession().getServletContext().getRealPath("/");
					byte[] allBytes = null;
					File file = new File(projectpath + atta.getSaveServicePath());
					if (file.exists()) {
						allBytes = FileUtils.getBytes(file);
						ResponseUtils.download(request, response, allBytes, atta.getFileName());
					}
				}
			} else {
				// 直接取缓存
				ResponseUtils.download(request, response, data, "image.jpg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	public static void main(String[] args) {
		FormAttachment atta = new FormAttachment();
		atta.setFileName("a");
		System.out.println(atta.toString());
		System.out.println(atta.toJsonObject());
	}
}
