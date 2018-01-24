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

package com.glaf.form.website.springmvc;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.base.DataFile;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.ResponseUtils;

@Controller("/lob/lob2")
@RequestMapping("/lob/lob2")
public class BlobController {
	protected final static Log logger = LogFactory.getLog(BlobController.class);

	private static Configuration conf = BaseConfiguration.create();

	protected IBlobService blobService;

//	@ResponseBody
//	@RequestMapping("/delete")
//	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
//		RequestUtils.setRequestParameterToAttribute(request);
//		String fileId = request.getParameter("fileId");
//		if (StringUtils.isNotEmpty(fileId)) {
//			blobService.deleteBlobByFileId(fileId);
//			return ResponseUtils.responseJsonResult(true);
//		}
//		return ResponseUtils.responseJsonResult(false);
//	}

	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String fileId = request.getParameter("fileId");
		if (StringUtils.isNotEmpty(fileId)) {
			logger.debug("fileId:" + fileId);
			DataFile blob = blobService.getBlobByFileId(fileId);
			if (blob != null) {
				logger.debug("id:" + blob.getId());
				InputStream inputStream = null;
				try {
					inputStream = blobService.getInputStreamById(blob.getId());
					ResponseUtils.download(request, response, inputStream, blob.getFilename());
				} catch (Exception ex) {
				} finally {
					blob.setData(null);
					blob = null;
					IOUtils.closeQuietly(inputStream);
				}
			}
		}
	}

//	@RequestMapping("/filelist")
//	public byte[] filelist(HttpServletRequest request, ModelMap modelMap) throws IOException {
//		JSONArray array = new JSONArray();
//		RequestUtils.setRequestParameterToAttribute(request);
//		String businessKey = request.getParameter("businessKey");
//		if (StringUtils.isNotEmpty(businessKey)) {
//			List<DataFile> dataList = blobService.getBlobList(businessKey);
//			array = BlobItemJsonFactory.datalistToArray(dataList);
//		}
//		return array.toJSONString().getBytes("UTF-8");
//	}
//
//	@RequestMapping("/files")
//	public ModelAndView files(HttpServletRequest request, ModelMap modelMap) {
//		RequestUtils.setRequestParameterToAttribute(request);
//		String businessKey = request.getParameter("businessKey");
//		if (StringUtils.isNotEmpty(businessKey)) {
//			List<DataFile> dataList = blobService.getBlobList(businessKey);
//			modelMap.put("dataList", dataList);
//		}
//
//		String jx_view = request.getParameter("jx_view");
//
//		if (StringUtils.isNotEmpty(jx_view)) {
//			return new ModelAndView(jx_view, modelMap);
//		}
//
//		return new ModelAndView("/modules/main/lob/files", modelMap);
//	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

//	@RequestMapping("/showUpload")
//	public ModelAndView showUpload(HttpServletRequest request, ModelMap modelMap) {
//		RequestUtils.setRequestParameterToAttribute(request);
//		String businessKey = request.getParameter("businessKey");
//		if (StringUtils.isNotEmpty(businessKey)) {
//			List<DataFile> dataList = blobService.getBlobList(businessKey);
//			modelMap.put("dataList", dataList);
//		}
//
//		String jx_view = request.getParameter("jx_view");
//
//		if (StringUtils.isNotEmpty(jx_view)) {
//			return new ModelAndView(jx_view, modelMap);
//		}
//
//		return new ModelAndView("/modules/main/lob/showUpload", modelMap);
//	}
//
//	@ResponseBody
//	@RequestMapping("/upload")
//	public void upload(HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("text/plain;charset=UTF-8");
//		LoginContext loginContext = RequestUtils.getLoginContext(request);
//		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
//				request.getSession().getServletContext());
//		// 检查form中是否有enctype="multipart/form-data"
//		if (multipartResolver.isMultipart(request)) {
//			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
//			String businessKey = req.getParameter("businessKey");
//			String serviceKey = req.getParameter("serviceKey");
//			String responseType = req.getParameter("responseType");
//			Map<String, Object> paramMap = RequestUtils.getParameterMap(req);
//			logger.debug("paramMap:" + paramMap);
//
//			String type = req.getParameter("type");
//			if (StringUtils.isEmpty(type)) {
//				type = "0";
//			}
//			int maxUploadSize = conf.getInt(serviceKey + ".maxUploadSize", 0);
//			if (maxUploadSize == 0) {
//				maxUploadSize = conf.getInt("upload.maxUploadSize", 50);// 50MB
//			}
//			maxUploadSize = maxUploadSize * FileUtils.MB_SIZE;
//
//			/**
//			 * 文件大小超过maxDiskSize时将文件写到本地硬盘,默认超过5MB的将写到本地硬盘
//			 */
//			int maxDiskSize = conf.getInt(serviceKey + ".maxDiskSize", 0);
//			if (maxDiskSize == 0) {
//				maxDiskSize = conf.getInt("upload.maxDiskSize", 1024 * 1024 * 2);// 2MB
//			}
//
//			logger.debug("maxUploadSize:" + maxUploadSize);
//			String uploadDir = Constants.UPLOAD_PATH;
//			try {
//				PrintWriter out = response.getWriter();
//				Map<String, MultipartFile> fileMap = req.getFileMap();
//				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
//				for (Entry<String, MultipartFile> entry : entrySet) {
//					MultipartFile mFile = entry.getValue();
//					logger.debug("fize size:" + mFile.getSize());
//					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0 && mFile.getSize() < maxUploadSize) {
//						String filename = mFile.getOriginalFilename();
//						logger.debug("upload file:" + filename);
//						logger.debug("fize size:" + mFile.getSize());
//						String fileId = UUID32.getUUID();
//
//						// 每天上传的文件根据日期存放在不同的文件夹
//						String parttern = "yyyy/MM/dd";
//						String autoCreatedDateDir = DateFormatUtils.format(new Date(), parttern);
//						String filePath = uploadDir + autoCreatedDateDir + "/"
//								+ (JenkinsHash.getInstance().hash(fileId.getBytes()) % 1024);
//						filePath = StringTools.replace(filePath, FileUtils.sp, "/");
//
//						BlobItem dataFile = new BlobItemEntity();
//						dataFile.setLastModified(System.currentTimeMillis());
//						dataFile.setCreateBy(loginContext.getActorId());
//						dataFile.setFileId(fileId);
//						dataFile.setFilename(mFile.getOriginalFilename());
//						dataFile.setName(mFile.getOriginalFilename());
//						dataFile.setContentType(mFile.getContentType());
//						dataFile.setType(type);
//						dataFile.setStatus(0);
//						dataFile.setBusinessKey(businessKey);
//						dataFile.setServiceKey(serviceKey);
//
//						if (mFile.getSize() <= maxDiskSize) {
//							dataFile.setPath(null);
//							dataFile.setData(mFile.getBytes());
//						} else {
//							dataFile.setPath(filePath + "/" + fileId);
//							dataFile.setData(null);
//						}
//						blobService.insertBlob(dataFile);
//
//						if (mFile.getSize() > maxDiskSize) {
//							String rootDir = SystemProperties.getConfigRootPath();
//							if (!rootDir.endsWith(String.valueOf(FileUtils.sp))) {
//								rootDir = rootDir + FileUtils.sp;
//							}
//							String savePath = rootDir + filePath;
//							FileUtils.mkdirs(savePath);
//							FileUtils.save(savePath + "/" + fileId, mFile.getInputStream());
//							logger.debug(savePath + "/" + fileId + " save ok.");
//						}
//
//						if (StringUtils.equalsIgnoreCase(responseType, "json")) {
//							StringBuilder json = new StringBuilder();
//							json.append("{");
//							json.append("'");
//							json.append("fileId");
//							json.append("':'");
//							json.append(fileId);
//							json.append("'");
//							Enumeration<String> pNames = request.getParameterNames();
//							String pName;
//							while (pNames.hasMoreElements()) {
//								json.append(",");
//								pName = (String) pNames.nextElement();
//								json.append("'");
//								json.append(pName);
//								json.append("':'");
//								json.append(request.getParameter(pName));
//								json.append("'");
//							}
//							json.append("}");
//							logger.debug(json.toString());
//							response.getWriter().write(json.toString());
//						} else {
//							out.print(fileId);
//						}
//					}
//				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//	}

}