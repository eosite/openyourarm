package com.baidu.ueditor.upload;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.glaf.web.service.PageResourceService;

public class BinaryUploader {
    
	public static final State save(HttpServletRequest request,
			Map<String, Object> conf) {
		//request 已通过spring DispatchServlet拦截底层已完成附件封装
		// 转型为MultipartHttpRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得上传的文件（根据前台的name名称得到上传的文件）
		List<MultipartFile> files = multipartRequest.getFiles((String)conf.get("fieldName"));
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		/*
		 * ServletFileUpload upload = new ServletFileUpload( new
		 * DiskFileItemFactory());
		 */
		////System.out.println(request.getContentType());
		if (isAjaxUpload) {
			try {
				multipartRequest.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Iterator<MultipartFile> iterator = files.iterator();
			MultipartFile file = null;
			while (iterator.hasNext()) {
				file = iterator.next();
			}

			if (file == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
            ////System.out.println("contentType:::::::::::::::::"+file.getContentType());
			String savePath = (String) conf.get("savePath");
			String originFileName = file.getOriginalFilename();
			String fileRelName=file.getOriginalFilename();
			String suffix = FileType.getSuffixByFilename(originFileName);
            String contentType=file.getContentType();
			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;

			long maxSize = ((Long) conf.get("maxSize")).longValue();

			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			
			savePath = PathFormat.parse(savePath, originFileName);

			//String physicalPath = (String) conf.get("rootPath") + savePath;

			//InputStream is = file.getInputStream();
			//State storageState = StorageManager.saveFileByInputStream(is,
					//physicalPath, maxSize);
			PageResourceService pageResourceService=(PageResourceService)conf.get("pageResourceService");
			State storageState =StorageManager.saveFile(pageResourceService,file.getBytes(),contentType,savePath,originFileName,fileRelName,suffix,maxSize);
			//is.close();

			if (storageState.isSuccess()) {
				storageState.putInfo("url", PathFormat.format(savePath));
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName + suffix);
			}

			return storageState;
		} /*
		 * catch (FileUploadException e) { return new BaseState(false,
		 * AppInfo.PARSE_REQUEST_ERROR); }
		 */catch (IOException e) {
			 e.printStackTrace();
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
