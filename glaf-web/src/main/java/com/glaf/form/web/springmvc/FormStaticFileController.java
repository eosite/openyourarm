package com.glaf.form.web.springmvc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.bim.BimUpload;
import com.glaf.bim.BimUtils;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.factory.RedisFactory;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.excel.ExcelUpload;
import com.glaf.excel.ExcelUtils;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.query.FormAttachmentQuery;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.IFormAttachmentService;
import com.glaf.form.core.util.FormAttachmentJsonFactory;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.util.GisExcellImportUtils;
import com.itextpdf.text.log.SysoCounter;

import antlr.Utils;

@Controller("/form/staticFile")
@RequestMapping("/form/staticFile")
public class FormStaticFileController {

	protected static final Log logger = LogFactory.getLog(FormStaticFileController.class);
	
	public FormStaticFileController() {

	}
	
	@RequestMapping("/save")
	@ResponseBody
	public byte[] save(HttpServletRequest request) throws Exception {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		
		String pageId = RequestUtils.getString(request, "pageId");
		String componentId = RequestUtils.getString(request, "componentId");
		
		JSONObject result = new JSONObject();

		if(pageId != null && !pageId.isEmpty() && componentId != null && !componentId.isEmpty()){
			try {
				String ruleData = RequestUtils.getString(request, "ruleData");
				if(ruleData == null || ruleData.isEmpty()){
					result.put("status", "400");
					result.put("message", "数据规则为空，不能保存");
				}else{
					String filepath = SystemProperties.getConfigRootPath() + "/staticData/" + pageId + "/" + componentId;
					Path dir = Paths.get(filepath);
					if(!Files.exists(dir)){
						Files.createDirectories(dir);
					}
					Path file = Paths.get(filepath, "staticData.js");
					if(!Files.exists(file)){
						Files.createFile(file);
					}
					Files.write(file, ruleData.getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
				result.put("status", "400");
				result.put("message", "异常错误信息，请查看日志！");
				logger.error(e.toString());
			}
		}else{
			result.put("status", "400");
			result.put("message", "未传递页面信息和控件信息，不能保存");
		}
		result.put("status", "200");
		result.put("message", "操作成功！");
		return result.toJSONString().getBytes("UTF-8");
	}
	
	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		
		String pageId = RequestUtils.getString(request, "pageId");
		String componentId = RequestUtils.getString(request, "componentId");
		
		if(pageId != null && !pageId.isEmpty() && componentId != null && !componentId.isEmpty()){
			String filepath = SystemProperties.getConfigRootPath() + "/staticData/" + pageId + "/" + componentId + "/staticData.js";
			Path file = Paths.get(filepath);
			try {
				ResponseUtils.download(request, response, Files.newInputStream(file), "staticData.js");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
