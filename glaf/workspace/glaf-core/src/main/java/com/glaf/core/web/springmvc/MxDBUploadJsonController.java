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

package com.glaf.core.web.springmvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.CustomProperties;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.RequestUtils;

@Controller("/uploadJson")
@RequestMapping("/uploadJson")
public class MxDBUploadJsonController {

	public final static String sp = System.getProperty("file.separator");

	protected IBlobService blobService;

	private String getError(String message) throws Exception {
		JSONObject object = new JSONObject();
		object.put("error", 1);
		object.put("message", message);
		return object.toString();
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@RequestMapping
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		// 定义允许上传的文件扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp", "swf" };
		// 最大文件大小
		long maxSize = FileUtils.MB_SIZE * 5;

		String allowSize = CustomProperties.getString("upload.maxSize");
		if (StringUtils.isEmpty(allowSize)) {
			allowSize = SystemProperties.getString("upload.maxSize");
		}

		if (StringUtils.isNotEmpty(allowSize) && StringUtils.isNumeric(allowSize)) {
			maxSize = Long.parseLong(allowSize);
		}

		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			String businessKey = request.getParameter("businessKey");
			String serviceKey = request.getParameter("serviceKey");
			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
					// 检查文件大小
					if (mFile.getSize() > maxSize) {
						response.getWriter().write(getError("上传文件大小超过限制。"));
						return;
					}
					String fileName = mFile.getOriginalFilename();
					// 检查扩展名
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if (!Arrays.<String> asList(fileTypes).contains(fileExt)) {
						response.getWriter().write(getError("上传文件扩展名是不允许的扩展名。"));
						return;
					}
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = df.format(new Date()) + "_" + new Random().nextInt(10000) + "." + fileExt;
					try {
						DataFile dataFile = new BlobItemEntity();
						dataFile.setBusinessKey(businessKey);
						dataFile.setCreateBy(loginContext.getActorId());
						dataFile.setCreateDate(new Date());
						dataFile.setFileId(newFileName);
						dataFile.setLastModified(System.currentTimeMillis());
						dataFile.setName(fileName);
						if (StringUtils.isNotEmpty(serviceKey)) {
							dataFile.setServiceKey(serviceKey);
						} else {
							dataFile.setServiceKey("IMG_" + loginContext.getActorId());
						}
						dataFile.setData(mFile.getBytes());
						dataFile.setFilename(fileName);
						dataFile.setType(fileExt);
						dataFile.setSize(mFile.getSize());
						dataFile.setStatus(1);
						blobService.insertBlob(dataFile);
					} catch (Exception ex) {
						ex.printStackTrace();
						response.getWriter().write(getError("上传文件失败。"));
						return;
					}

					JSONObject object = new JSONObject();
					object.put("error", 0);
					object.put("url", request.getContextPath() + "/rs/blob/file/" + newFileName);
					response.getWriter().write(object.toString());
				}
			}
		}
	}

}