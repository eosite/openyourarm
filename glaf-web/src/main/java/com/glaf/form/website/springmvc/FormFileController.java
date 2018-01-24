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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.factory.RedisFileStorageFactory;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.query.FormAttachmentQuery;
import com.glaf.form.core.service.IFormAttachmentService;
import com.glaf.form.web.springmvc.FormAttachmentController;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/public/form/file")
@RequestMapping("/public/form/file")
public class FormFileController {

	protected static final Log logger = LogFactory.getLog(FormFileController.class);

	protected IFormAttachmentService formAttachmentService;

	protected String to_db = "1";

	protected String to_dir = "0";

	public FormFileController() {

	}

	@Resource
	public void setFormAttachmentService(IFormAttachmentService formAttachmentService) {
		this.formAttachmentService = formAttachmentService;
	}

	@RequestMapping(params = "method=download")
	public void download(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("randomparent") String id) {
		InputStream fis = null;
		try {

			FormAttachment atta = this.getFormAttachmentByRandomParent(id);

			if (atta == null) {// 可能是主键id
				atta = this.formAttachmentService.getFormAttachmentById(id);
			}

			if (atta != null) {
				atta.setFileContent(this.getFileContent(atta));
			}

			if (atta == null) {
				return;
			}

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

	@ResponseBody
	@RequestMapping(params = "method=getFileBytes")
	public byte[] getOfficeByRandomParent(HttpServletRequest request, HttpServletResponse response) {
		String id = RequestUtils.getString(request, "randomparent");
		/*try {
			FormAttachment fac = this.getFormAttachmentByRandomParent(parent);

			if (fac == null) {// 可能是主键id
				fac = this.formAttachmentService.getFormAttachmentById(parent);
			}

			if (fac != null) {
				fac.setFileContent(this.getFileContent(fac));
				return fac.getFileContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		if(StringUtils.isBlank(id)){
			return null;
		}
		
		
		InputStream fis = null;
		try {

			FormAttachment atta = this.getFormAttachmentByRandomParent(id);

			if (atta == null) {// 可能是主键id
				atta = this.formAttachmentService.getFormAttachmentById(id);
			}

			if (atta != null) {
				atta.setFileContent(this.getFileContent(atta));
			}

			if (atta == null) {
				return null;
			}

			if (to_db.equals(atta.getType())) {
				return atta.getFileContent();
				//ResponseUtils.download(request, response, atta.getFileContent(), atta.getFileName());
			} else if (to_dir.equals(atta.getType())) {
				// 从服务器目录中读取
				String projectpath = request.getSession().getServletContext().getRealPath("/");
				File file = new File(projectpath + atta.getSaveServicePath());
				if (file.exists()) {
				//	fis = new FileInputStream(file);
					
				return 	FileUtils.readFileToByteArray(file);
					
					
				//	ResponseUtils.download(request, response, fis, atta.getFileName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
		
		return null;
	}

	@ResponseBody
	@RequestMapping(params = "method=upload")
	public byte[] upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") List<MultipartFile> mFiles, ModelMap modelMap) throws IOException {

		String to = RequestUtils.getString(request, "to", "to_dir");

		return FormAttachmentController.upload(request, response, to, mFiles);
	}

	public FormAttachment getFormAttachmentByRandomParent(String parent) {
		List<FormAttachment> list = this.formAttachmentService.getFormAttachmentByParent(parent);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取文件内容，从redis缓存中加载文件流
	 * 
	 * @param atta
	 * @return
	 */
	private byte[] getFileContent(FormAttachment atta) {
		byte[] data = null;
		String cacheKey = "form_attach_" + SystemConfig.getIntToken() + "_" + atta.getId();
		if (to_db.equals(atta.getType())) {
			if (SystemConfig.getBoolean("use_file_cache")) {
				try {
					data = RedisFileStorageFactory.getInstance().getData(cacheKey);
				} catch (Exception ex) {
				}
			}
			if (data != null) {
				return data;
			}
			FormAttachment bean = this.formAttachmentService.getFormAttachmentById(atta.getId());
			data = bean.getFileContent();
			if (data != null) {
				if (SystemConfig.getBoolean("use_file_cache")) {
					try {
						RedisFileStorageFactory.getInstance().saveData(cacheKey, data);
					} catch (Exception ex) {
					}
				}
			}
		}
		return data;
	}

	@ResponseBody
	@RequestMapping(params = "method=remove")
	public byte[] remove(HttpServletRequest request, HttpServletResponse response) {

		String randomParent = request.getParameter("randomParent");
		String[] ids = request.getParameterValues("id");

	//	List<String> fileNameList = new ArrayList<String>();
	//	for (String s : fileNames) {
		//	fileNameList.add(s);
	//	}

		List<String> rowIds = new ArrayList<String>();
		for (String s : ids) {
			rowIds.add(s);
		}

		FormAttachmentQuery query = new FormAttachmentQuery();
		query.setParent(randomParent);
		// query.setFileNames(fileNameList);
		query.setRowIds(rowIds);
		List<FormAttachment> list = this.formAttachmentService.list(query);

		if (list != null && list.size() > 0) {

			String projectpath = request.getSession().getServletContext().getRealPath("/");
			// List<String> paths = new ArrayList<String>();// 保存文件路径
			JSONArray retAry = new JSONArray();
			for (FormAttachment atta : list) {
				retAry.add(atta);
				atta.setFileContent(null);
				if (to_db.equals(atta.getType())) {
					// 文件保存至数据库，直接删除记录即可
					this.formAttachmentService.deleteById(atta.getId());
				} else if (to_dir.equals(atta.getType())) {
					// 文件保存至服务器目录，删除记录后还要删除文件
					this.formAttachmentService.deleteById(atta.getId());

					if (new File(projectpath + atta.getSaveServicePath()).exists()) {
						new File(projectpath + atta.getSaveServicePath()).delete();
					}
				}
			}
			try {
				return retAry.toJSONString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
