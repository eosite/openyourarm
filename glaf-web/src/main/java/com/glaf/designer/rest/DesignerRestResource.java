package com.glaf.designer.rest;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.apache.fontbox.encoding.Encoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.identity.User;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.util.ZipUtils;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormTemplate;
import com.glaf.form.core.domain.ObjTemplate;
import com.glaf.form.core.domain.ObjTemplateCategory;
import com.glaf.form.core.domain.ObjectCategory;
import com.glaf.form.core.domain.ObjectTemplateStyle;
import com.glaf.form.core.query.FormComponentQuery;
import com.glaf.form.core.query.FormTemplateQuery;
import com.glaf.form.core.query.ObjTemplateQuery;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.service.FormTemplateService;
import com.glaf.form.core.service.ObjTemplateCategoryService;
import com.glaf.form.core.service.ObjTemplateService;
import com.glaf.form.core.service.ObjectCategoryService;
import com.glaf.form.core.service.ObjectTemplateStyleService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@RestController
public class DesignerRestResource {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private FormComponentService formComponentService;
	private FormTemplateService formTemplateService;
	private ObjTemplateService objectTemplateService;
	private ObjectCategoryService objectCategoryService;
	private ObjectTemplateStyleService objectTemplateStyleService;
	private ObjTemplateCategoryService objectTemplateCategoryService;

	@Resource
	public void setFormComponentService(FormComponentService formComponentService) {
		this.formComponentService = formComponentService;
	}

	@Resource
	public void setFormTemplateService(FormTemplateService formTemplateService) {
		this.formTemplateService = formTemplateService;
	}

	@Resource
	public void setObjectTemplateService(ObjTemplateService objectTemplateService) {
		this.objectTemplateService = objectTemplateService;
	}

	@Resource
	public void setObjectCategoryService(ObjectCategoryService objectCategoryService) {
		this.objectCategoryService = objectCategoryService;
	}
	@Resource
	public void setObjectTemplateStyleService(ObjectTemplateStyleService objectTemplateStyleService) {
		this.objectTemplateStyleService = objectTemplateStyleService;
	}
	@Resource
	public void setObjTemplateCategoryService(ObjTemplateCategoryService objectTemplateCategoryService) {
		this.objectTemplateCategoryService = objectTemplateCategoryService;
	}
	/**
	 * 获取控件属性结构以及属性值
	 * 
	 * @param request
	 * @param component
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/designer/{component}/setting")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getComponentProp(@Context HttpServletRequest request, @PathVariable String component)
			throws UnsupportedEncodingException {
		String componentProp = "";
		// 获取父控件
		String pComponent = request.getParameter("pComponent");
		String pComponentProp = "";
		if (StringUtils.isNotEmpty(pComponent)) {
			pComponentProp = getComponentPropFunc(pComponent);
		}
		componentProp = getComponentPropFunc(component);
		if (StringUtils.isNotEmpty(pComponentProp) && StringUtils.isNotEmpty(componentProp)) {
			JSONObject componentJSON = JSONObject.parseObject(componentProp);
			JSONObject pComponentJSON = JSONObject.parseObject(pComponentProp);
			JSONArray componentJSONArray = componentJSON.getJSONArray("propertyPackages");
			JSONArray pcomponentJSONArray = pComponentJSON.getJSONArray("propertyPackages");
			componentJSONArray.addAll(pcomponentJSONArray);
			componentJSON.put("propertyPackages", componentJSONArray);
			return componentJSON.toJSONString().getBytes("UTF-8");
		} else if (StringUtils.isNotEmpty(componentProp)) {
			return componentProp.getBytes("UTF-8");
		} else if (StringUtils.isNotEmpty(pComponentProp)) {
			return pComponentProp.getBytes("UTF-8");
		}
		return componentProp.getBytes("UTF-8");
	}

	public String getComponentPropFunc(String component) throws UnsupportedEncodingException {
		String resourceName = "";
		String componentProp = "";
		if (StringUtils.isNotEmpty(component)) {
			if (component.startsWith("col")) {
				resourceName = "/components/col.json";
			} else {
				resourceName = "/components/" + component + ".json";
			}
		}
		// InputStream elemsetStream = this.getClass().getClassLoader()
		// .getResourceAsStream(resourceName);
		try {
			Map<String, Object> root = new HashMap<>();
			root.put("assigns", null);
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
			configuration.setDefaultEncoding("utf-8");
			configuration.setClassForTemplateLoading(this.getClass(), "/");
			Template temp = configuration.getTemplate(resourceName);
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			temp.process(root, stringWriter);
			componentProp = stringWriter.toString();
			writer.flush();
			writer.close();
		} catch (Exception e) {
			logger.error("Error while loading Component properties" + e.getMessage());
		}
		return componentProp;
	}

	/**
	 * 获取控件模板
	 * 
	 * @param request
	 * @param component
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{component}/templates")
	@ResponseBody
	public byte[] getTemplates(@Context HttpServletRequest request, @PathVariable String component) throws Exception {
		JSONObject jsonObject = new JSONObject();
		FormComponentQuery query = new FormComponentQuery();
		query.setDataRole(component);
		List<FormComponent> list = formComponentService.list(query);
		FormComponent formComponent = null;
		if (list != null && !list.isEmpty()) {
			formComponent = list.get(0);
		}
		if (formComponent != null) {
			JSONArray jsonArray = new JSONArray();
			jsonObject.put("id", formComponent.getDataRole() + "_" + formComponent.getId());
			jsonObject.put("title", formComponent.getName());
			jsonObject.put("description", "");
			jsonObject.put("templates", jsonArray);
			if (formComponent.getId() != 0) {
				FormTemplateQuery query2 = new FormTemplateQuery();
				query2.setComponentId(Long.valueOf(formComponent.getId()).intValue());
				query2.setType("style");// 风格模板
				query2.setOrderBy("id_");
				List<FormTemplate> temps = formTemplateService.list(query2);
				if (temps != null && !temps.isEmpty()) {
					for (FormTemplate temp : temps) {
						if (temp.getDeleteFlag() == null || temp.getDeleteFlag() != 1) {
							JSONObject object = new JSONObject();
							object.put("id_", temp.getId());
							object.put("id", formComponent.getDataRole() + "_" + temp.getId());
							object.put("title", temp.getName());
							object.put("template", temp.getTemplate());
							object.put("image_exists", temp.getImage_exists());
							jsonArray.add(object);
						}
					}
				}
			}
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取控件模板
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/getTemplate")
	@ResponseBody
	public byte[] getTemplate(HttpServletRequest request) throws Exception {
		Integer id = RequestUtils.getInteger(request, "id");
		JSONObject res = new JSONObject();
		if (id != null) {
			FormTemplate temp = formTemplateService.getFormTemplate(id);
			if (temp != null) {
				res.put("template", temp.getTemplate());
			}
		}
		return res.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 加载控件模板图片
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{id}/getTemplateImage")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getTemplateImage(@Context HttpServletRequest request, @PathVariable Integer id) {
		if (id != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			FormTemplate formTemplate = formTemplateService.getTemplateImage(id);
			if (formTemplate != null) {
				return formTemplate.getImage();
			}
		}
		return null;
	}

	/**
	 * 加载控件渲染模板
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{comId}/getTemplateRender")
	@ResponseBody
	public byte[] getTemplateRender(@Context HttpServletRequest request, @PathVariable Integer comId)
			throws UnsupportedEncodingException {
		JSONObject res = new JSONObject();
		if (comId != null) {
			FormTemplateQuery query = new FormTemplateQuery();
			query.setComponentId(comId);
			query.setType("render");
			List<FormTemplate> list = formTemplateService.list(query);
			if (list != null && !list.isEmpty()) {
				res.put("template", list.get(0).getTemplate());
			}
		}
		return res.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 从模板库获取模板
	 * 
	 * @param request
	 * @param component
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{category}/categoryTemplates")
	@ResponseBody
	public byte[] getCategoryTemplates(@Context HttpServletRequest request, @PathVariable Long category)
			throws Exception {
		// 写入模板
		/*
		 * ObjTemplate objTemplate=new ObjTemplate(); byte[] tmpContent=null;
		 * tmpContent="<img src=\"/glaf/scripts/designer/images/logo.png\"/>"
		 * .getBytes(); objTemplate.setTmpContent(tmpContent); byte[] img=null;
		 * File imgFile=new
		 * File(request.getRealPath("/")+"/scripts/designer/images/logo.png");
		 * img=FileUtils.readFileToByteArray(imgFile);
		 * objTemplate.setThumbnail(img);
		 * objectTemplateService.save(objTemplate);
		 */
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		List<ObjTemplate> list = objectTemplateService.getCategoryTemplates(category);
		if (list != null && !list.isEmpty()) {
			for (ObjTemplate temp : list) {
				if (temp.getDelFlag() == null || temp.getDelFlag() != 1) {
					JSONObject object = new JSONObject();
					object.put("id", temp.getTemplateId());
					object.put("title", temp.getTmpName());
					object.put("template", new String(temp.getTmpContent()));
					if (temp.getThumbnail() != null && temp.getThumbnail().length > 0)
						object.put("image_exists", 1);
					jsonArray.add(object);
				}
			}
			jsonObject.put("templates", jsonArray);
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取模板内容
	 * 
	 * @param request
	 * @param component
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{id}/categoryTemplate")
	@ResponseBody
	public byte[] getCategoryTemplate(@Context HttpServletRequest request, @PathVariable Long id) throws Exception {
		JSONObject jsonObject = new JSONObject();
		ObjTemplate template = objectTemplateService.getObjTemplate(id);
		if (template != null) {
			jsonObject.put("template", new String(template.getTmpContent()));
			jsonObject.put("id", template.getTemplateId());
		}

		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 从模板库获取模板
	 * 
	 * @param request
	 * @param component
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/listCategoryTemplates")
	@ResponseBody
	public byte[] listCategoryTemplates(@Context HttpServletRequest request) throws Exception {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ObjTemplateQuery query = new ObjTemplateQuery();
		Long category = RequestUtils.getLong(request, "category");
		query.setCategoryId(category);
		String treeId = RequestUtils.getString(request, "treeId");
		query.setTreeId(treeId);
		Tools.populate(query, params);
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "pageNo");
		limit = ParamUtils.getInt(params, "pagesize");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = objectTemplateService.getObjTemplateCountByQueryCriteria(query);
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

			// Map<String, User> userMap = IdentityFactory.getUserMap();
			List<ObjTemplate> list = objectTemplateService.getObjTemplatesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("data", rowsJSON);

				for (ObjTemplate objTemplate : list) {
					JSONObject rowJSON = objTemplate.toJsonObject();
					rowJSON.put("id", objTemplate.getTemplateId());
					rowJSON.put("title", objTemplate.getTmpName());
					// rowJSON.put("template", new
					// String(objTemplate.getTmpContent()));
					if (objTemplate.getThumbnail() != null && objTemplate.getThumbnail().length > 0)
						rowJSON.put("image_exists", 1);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("data", rowsJSON);
		}
		result.put("count", total);
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 加载模板图片
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{templateId}/getCategoryTemplateImage")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void getCategoryTemplateImage(@Context HttpServletRequest request, @Context HttpServletResponse response,
			@PathVariable Long templateId) {
		if (templateId != null) {
			ObjTemplate objTemplate = objectTemplateService.getObjTemplate(templateId);
			OutputStream stream = null;
			try {
				stream = response.getOutputStream();
				if(objTemplate.getThumbnail()!=null){
				stream.write(objTemplate.getThumbnail());
				response.setContentType("image/jpeg;charset=UTF-8");
				response.flushBuffer();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 保存模板到模板库
	 * 
	 * @param request
	 * @param component
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{category}/saveTemplate")
	@ResponseBody
	public byte[] saveCategoryTemplate(@Context HttpServletRequest request, @PathVariable Long category)
			throws Exception {
		JSONObject jsonObject = new JSONObject();
		String code = RequestUtils.getStringValue(request, "code");
		Long templateId = RequestUtils.getLong(request, "templateId");
		String tmpName = RequestUtils.getStringValue(request, "tmpName");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		ObjTemplate objTemplate = new ObjTemplate();
		objTemplate.setCreator(actorId);
		objTemplate.setCreateTime(new Date());
		objTemplate.setDelFlag(0);
		objTemplate.setModifier(actorId);
		objTemplate.setObjType("pageComp");
		objTemplate.setOwner(actorId);
		objTemplate.setTemplateId(templateId);
		// objTemplate.setThumbnail(thumbnail);
		objTemplate.setTmpContent(code.getBytes());
		objTemplate.setTmpName(tmpName);
		objTemplate.setUpdateTime(new Date());
		Base64 base64 = new Base64();
		byte[] imgbyte = null;
		ByteArrayOutputStream out = null;
		InputStream is = null;
		try {
			// 注意点：实际的图片数据是从 data:image/jpeg;base64, 后开始的
			String imageData = RequestUtils.getStringValue(request, "imgdata");
			if(StringUtils.isNotEmpty(imageData)){
			String actData=imageData.substring(imageData.indexOf(";base64,")+";base64,".length(),imageData.length());
			byte[] k = base64.decode(actData);
			is = new ByteArrayInputStream(k);
			out = new ByteArrayOutputStream();
			// 以下其实可以忽略，将图片压缩处理了一下，可以小一点
			double ratio = 1.0;
			BufferedImage image = ImageIO.read(is);
			if (image.getWidth() > 800) {
				ratio = image.getWidth() / 800;
			}
			int newWidth = (int) (image.getWidth() * ratio);
			int newHeight = (int) (image.getHeight() * ratio);
			Image newimage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(newimage, 0, 0, null);
			g.dispose();
			ImageIO.write(tag, "jpg", out);
			imgbyte = out.toByteArray();
			objTemplate.setThumbnail(imgbyte);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (out != null)
				out.close();
			if (is != null)
				is.close();
		}
		try {
			objectTemplateService.saveTemplateToCategory(objTemplate, category);
			jsonObject.put("id", objTemplate.getTemplateId());
			//保存样式
			String compDataRule=RequestUtils.getString(request, "compDataRule");
			if(StringUtils.isNotEmpty(compDataRule)){
				objectTemplateStyleService.saveTemplateStyle(objTemplate.getTemplateId(),compDataRule,actorId);
			}
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			logger.error(e.getMessage());
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 保存模板到模板库
	 * 
	 * @param request
	 * @param component
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{templateId}/editTemplate")
	@ResponseBody
	public byte[] editCategoryTemplate(@Context HttpServletRequest request, @PathVariable Long templateId)
			throws Exception {
		JSONObject jsonObject = new JSONObject();
		String code = RequestUtils.getStringValue(request, "code");
		String tmpName = RequestUtils.getStringValue(request, "tmpName");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		ObjTemplate objTemplate = new ObjTemplate();
		objTemplate.setDelFlag(0);
		objTemplate.setModifier(actorId);
		objTemplate.setObjType("pageComp");
		objTemplate.setOwner(actorId);
		objTemplate.setTemplateId(templateId);
		// objTemplate.setThumbnail(thumbnail);
		objTemplate.setTmpContent(code.getBytes());
		objTemplate.setTmpName(tmpName);
		objTemplate.setUpdateTime(new Date());
		Base64 base64 = new Base64();
		byte[] imgbyte = null;
		ByteArrayOutputStream out = null;
		InputStream is = null;
		try {
			// 注意点：实际的图片数据是从 data:image/jpeg;base64, 后开始的
			String imageData = RequestUtils.getStringValue(request, "imgdata");
			String actData=imageData.substring(imageData.indexOf(";base64,")+";base64,".length(),imageData.length());
			byte[] k = base64.decode(actData);
			is = new ByteArrayInputStream(k);
			out = new ByteArrayOutputStream();
			// 以下其实可以忽略，将图片压缩处理了一下，可以小一点
			double ratio = 1.0;
			BufferedImage image = ImageIO.read(is);
			if (image.getWidth() > 800) {
				ratio = image.getWidth() / 800;
			}
			int newWidth = (int) (image.getWidth() * ratio);
			int newHeight = (int) (image.getHeight() * ratio);
			Image newimage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(newimage, 0, 0, null);
			g.dispose();
			ImageIO.write(tag, "jpg", out);
			imgbyte = out.toByteArray();
			objTemplate.setThumbnail(imgbyte);
			objectTemplateService.save(objTemplate);
			jsonObject.put("id", objTemplate.getTemplateId());
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			if (out != null)
				out.close();
			if (is != null)
				is.close();
		}
		try {
			//保存样式
			String compDataRule=RequestUtils.getString(request, "compDataRule");
			if(StringUtils.isNotEmpty(compDataRule)){
				objectTemplateStyleService.saveTemplateStyle(objTemplate.getTemplateId(),compDataRule,actorId);
			}
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			logger.error(e.getMessage());
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}
	/**
	 * 从模板库导出模板
	 * 
	 * @param request
	 * @param component
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/exitTemplate")
	@ResponseBody
	public void exitCategoryTemplate(@Context HttpServletRequest request,@Context HttpServletResponse response)
			throws Exception {
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String path = sdf.format(new Date());  	          
	        String serverPath = request.getSession().getServletContext().getRealPath("/upload/"); 
	        String tempath = serverPath + "\\" + path;
	        //在服务器端创建文件夹  
	        File file = new File(tempath); 
	        if(!file.exists()){  
	            file.mkdir();  
	        }
	        JSONArray jsonArray = new JSONArray();
	        FileOutputStream fileout = null;
	        ByteArrayInputStream in = null;
		    String ids = request.getParameter("ids");
			if (StringUtils.isNotEmpty(ids)) {
				StringTokenizer token = new StringTokenizer(ids, ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x)) {
						JSONObject jsonObject = new JSONObject();
						Map<String,Object> map = new HashMap<String,Object>();
						ObjTemplate template = objectTemplateService.getObjTemplate(Long.valueOf(x));
						byte[] b = template.getTmpContent();
						String resultContent = new String(template.getTmpContent(),"gbk");	
					
						fileout = new FileOutputStream(tempath+"\\"+template.getTemplateId()+".html");
						response.setHeader("Content-Disposition", "attachment; filename=" + template.getTmpName());
						response.setContentType("application/OCTET-STREAM;charset=utf-8");
						byte[] bpmnBytes = resultContent.getBytes("gbk");
						in = new ByteArrayInputStream(bpmnBytes);
						
						IOUtils.write(bpmnBytes, fileout);
						if(template.getThumbnail()!=null){
							fileout = new FileOutputStream(tempath+"\\"+template.getTemplateId()+".jpeg");
							IOUtils.write(template.getThumbnail(), fileout);				
						}
						ObjTemplateCategory templateCategory =  objectTemplateCategoryService.getObjTemplateCategoryByTemplateId(Long.valueOf(x));					
						ObjectCategory category =  objectCategoryService.getObjectCategory(templateCategory.getCategoryId());
						jsonObject.put("模板ID", template.getTemplateId());
						jsonObject.put("分类ID", templateCategory.getCategoryId());
						jsonObject.put("模板名称", template.getTmpName());
						jsonObject.put("分类名称", category.getName());
						jsonArray.add(jsonObject);
					}
				}
			}
			writeToJson(tempath+"\\data.json",jsonArray);
			//生成压缩文件
			File zipFile = new File(tempath + ".zip");
			ZipUtils.makeZip(file, zipFile);          
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFile)); 
			ResponseUtils.download(request, response, bis, path+".zip");
		    response.flushBuffer();
	        if(fileout != null){
	        	fileout.close();       
	        }
	        if(bis != null){
	        	bis.close();
	        }
	        if(in != null){
	        	in.close();
	        }
        	//删除文件
	        deleteFile(file);
	        deleteFile(zipFile);    
	 }
	private void deleteFile(File file) {  
		if (file.exists()) {// 判断文件是否存在
			if (file.isFile()) {// 判断是否是文件
				file.delete();// 删除文件
			} else if (file.isDirectory()) {// 否则如果它是一个目录
				File[] files = file.listFiles();// 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
					this.deleteFile(files[i]);// 把每个文件用这个方法进行迭代
				}
				file.delete();// 删除文件夹
			}
		}
	} 
	//生成json文件
	public void writeToJson(String filePath,JSONArray object) throws IOException
	{
		File file = new File(filePath);
		char [] stack = new char[1024];
		int top=-1;
		
		String string = object.toString();
		
		StringBuffer sb = new StringBuffer();
		char [] charArray = string.toCharArray();
		for(int i=0;i<charArray.length;i++){
			char c= charArray[i];
			if ('{' == c || '[' == c) {  
                stack[++top] = c; 
                sb.append("\n"+charArray[i] + "\n");  
                for (int j = 0; j <= top; j++) {  
                    sb.append("\t");  
                }  
                continue;  
            }
			 if ((i + 1) <= (charArray.length - 1)) {  
	                char d = charArray[i+1];  
	                if ('}' == d || ']' == d) {  
	                    top--; 
	                    sb.append(charArray[i] + "\n");  
	                    for (int j = 0; j <= top; j++) {  
	                        sb.append("\t");  
	                    }  
	                    continue;  
	                }  
	            }  
	            if (',' == c) {  
	                sb.append(charArray[i] + "");  
	                for (int j = 0; j <= top; j++) {  
	                    sb.append("");  
	                }  
	                continue;  
	            }  
	            sb.append(c);  
	        }  
	          
	        Writer write = new FileWriter(file);  
	        write.write(sb.toString());  
	        write.flush();  
	        write.close();  
	}
	 
	/**
	 * 获取所有分类
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/categorys")
	@ResponseBody
	public byte[] getCategorys(@Context HttpServletRequest request) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String objectCategoryJson = "";
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		String treeId = RequestUtils.getString(request, "treeId");
		List<ObjectCategory> objectCategorys = objectCategoryService.getPageCompTemplateObjectCategorys(actorId,
				treeId);
		if (objectCategorys != null && objectCategorys.size() > 0) {
			objectCategoryJson = jsonObject.toJSONString(objectCategorys);
			return objectCategoryJson.getBytes("UTF-8");
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 更新分类名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/category/rename")
	@ResponseBody
	public byte[] getCategoryRename(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		String name = RequestUtils.getString(request, "name");
		try {
			objectCategoryService.rename(categoryId, name);
		} catch (Exception e) {
			return ResponseUtils.responseJsonResult(false);
		}
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 删除分类名称
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/category/delete")
	@ResponseBody
	public byte[] getCategoryDelete(@Context HttpServletRequest request) throws Exception {
		Long categoryId = RequestUtils.getLong(request, "categoryId");
		objectCategoryService.deleteById(categoryId);
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 新增分类
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/category/add")
	@ResponseBody
	public byte[] addCategory(@Context HttpServletRequest request) throws Exception {
		Long pId = RequestUtils.getLong(request, "pId");
		String pTreeId = RequestUtils.getString(request, "pTreeId");
		String name = RequestUtils.getString(request, "name");
		int level = RequestUtils.getInt(request, "level");
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject jsonObject = new JSONObject();

		try {
			ObjectCategory obj = new ObjectCategory();
			obj.setCustom(1);
			obj.setDelFlag(0);
			obj.setTreeID(pTreeId);
			obj.setCreateTime(new Date());
			obj.setCreator(actorId);
			obj.setLevel(level);
			obj.setName(name);
			obj.setOwner(actorId);
			obj.setModifier(actorId);
			obj.setUpdateTime(new Date());
			obj.setParentId(pId);
			objectCategoryService.save(obj);
			jsonObject = (JSONObject) jsonObject.toJSON(obj);
			jsonObject.put("result", 1);
		} catch (Exception e) {
			jsonObject.put("result", 0);
			logger.error(e.getMessage());
		}
		return jsonObject.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 加载控件映射模板
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{dataRole}/getTemplateMapping")
	@ResponseBody
	public byte[] getTemplateMapping(@Context HttpServletRequest request, @PathVariable String dataRole)
			throws UnsupportedEncodingException {
		JSONObject res = new JSONObject();
		if (dataRole != null) {
			FormComponentQuery cquery = new FormComponentQuery();
			cquery.setDataRole(dataRole);
			List<FormComponent> coms = formComponentService.list(cquery);
			Long comId = null;
			if (coms != null && !coms.isEmpty()) {
				comId = coms.get(0).getId();
			}
			if (comId != null) {
				FormTemplateQuery query = new FormTemplateQuery();
				query.setComponentId(comId.intValue());
				query.setType("mapping");
				List<FormTemplate> list = formTemplateService.list(query);
				if (list != null && !list.isEmpty()) {
					res.put("template", list.get(0).getTemplate());
				}
			}
		}
		return res.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 导入设计模板
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/designer/importDesigner")
	@ResponseBody
	@Produces({ MediaType.TEXT_HTML })
	@javax.ws.rs.Consumes({ MediaType.MULTIPART_FORM_DATA })
	public byte[] importDesigner(@Context HttpServletRequest request, @RequestParam("fileinput") MultipartFile file)
			throws UnsupportedEncodingException {
		byte[] contentByte = null;
		JSONObject res = new JSONObject();
		try {
			contentByte = file.getBytes();
			res.put("result", new String(contentByte, "UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res.toJSONString().getBytes("UTF-8");
	}
	/**
	 * 加载控件模板图片
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/designer/{id}/getTemplateStyle")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getTemplateStyle(@Context HttpServletRequest request, @PathVariable Long id) throws UnsupportedEncodingException {
		if (id != null) {
			ObjectTemplateStyle objectTemplateStyle = objectTemplateStyleService.getObjectTemplateStyle(id);
			if (objectTemplateStyle != null) {
				return new String(objectTemplateStyle.getStyleContent()).getBytes("UTF-8");
			}
		}
		return null;
	}
}
