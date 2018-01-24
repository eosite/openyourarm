package com.glaf.designer.web.springmvc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.ObjectCategory;
import com.glaf.form.core.query.ObjectCategoryQuery;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.service.ObjectCategoryService;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.form.exception.FormPageDesignerException;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Controller("/page/designer")
@RequestMapping("/page/designer")
public class PageDesignerController {

	@Autowired
	FormPageService formPageService;
	@Autowired
	ObjectCategoryService objectCategoryService;

	@RequestMapping("/kendo")
	public ModelAndView kendoDesigner(HttpServletRequest request, ModelMap modelMap) {
		String view = request.getParameter("view");
		// 获取控件列表
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/designer/kendo_designer", modelMap);
	}

	@RequestMapping("/metro")
	public ModelAndView metroDesigner(HttpServletRequest request, ModelMap modelMap) {
		//view:
		String view = request.getParameter("view");
		// 获取UI列表
		List<FormDictory> uiList = FormDictoryFactory.getInstance().getFormDictoryListByTreeCode("ui_type");
		modelMap.put("uiList", uiList);
		// 获取模板分类
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		List<ObjectCategory> objectCategorys = objectCategoryService.getPageCompTemplateObjectCategorys(actorId,null);
		if (objectCategorys != null && objectCategorys.size() > 0) {
			String objectCategoryJson = JSONObject.toJSONString(objectCategorys);
			modelMap.put("objectCategoryJson", objectCategoryJson);
		}
		// 获取历史页面设计
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			FormPage formPage = formPageService.getFormPage(id,false);
			modelMap.put("desingerHtml", formPage.getDesignerHtml());
			modelMap.put("desingerJson", formPage.getDesignerJson());
		}
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/designer/metro_designer", modelMap);
	}
	
	@RequestMapping("/metro_new")
	public ModelAndView metroDesignerNew(HttpServletRequest request, ModelMap modelMap) {
		//view:
		String view = request.getParameter("view");
		// 获取UI列表
		List<FormDictory> uiList = FormDictoryFactory.getInstance().getFormDictoryListByTreeCode("ui_type");
		modelMap.put("uiList", uiList);
		// 获取模板分类
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		List<ObjectCategory> objectCategorys = objectCategoryService.getPageCompTemplateObjectCategorys(actorId,null);
		if (objectCategorys != null && objectCategorys.size() > 0) {
			String objectCategoryJson = JSONObject.toJSONString(objectCategorys);
			modelMap.put("objectCategoryJson", objectCategoryJson);
		}
		// 获取历史页面设计
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			FormPage formPage = formPageService.getFormPage(id,false);
			modelMap.put("desingerHtml", formPage.getDesignerHtml());
			modelMap.put("desingerJson", formPage.getDesignerJson());
			modelMap.put("themeTmpId", formPage.getThemeTmpId());
		}
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/designer/metro_designer_new", modelMap);
	}

	@RequestMapping("/freelayoutmetro")
	public ModelAndView freeLayoutMetroDesigner(HttpServletRequest request, ModelMap modelMap) {
		String view = request.getParameter("view");
		// 获取UI列表
		List<FormDictory> uiList = FormDictoryFactory.getInstance().getFormDictoryListByTreeCode("ui_type");
		modelMap.put("uiList", uiList);
		// 获取模板分类
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		List<ObjectCategory> objectCategorys = objectCategoryService.getPageCompTemplateObjectCategorys(actorId,null);
		if (objectCategorys != null && objectCategorys.size() > 0) {
			String objectCategoryJson = JSONObject.toJSONString(objectCategorys);
			modelMap.put("objectCategoryJson", objectCategoryJson);
		}
		// 获取历史页面设计
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			FormPage formPage = formPageService.getFormPage(id);
			modelMap.put("desingerHtml", formPage.getDesignerHtml());
			modelMap.put("desingerJson", formPage.getDesignerJson());
		}
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/designer/metro_freelayout_designer", modelMap);
	}

	@RequestMapping("/metro/template")
	public ModelAndView metroTemplate(HttpServletRequest request, ModelMap modelMap) {
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/designer/metro_template_mng", modelMap);
	}
	

	@RequestMapping("/metro/pageTemplate")
	public ModelAndView metroPageTemplate(HttpServletRequest request, ModelMap modelMap) {
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/designer/metro_page_template", modelMap);
	}

	@RequestMapping("/metro/editor")
	public ModelAndView editor(HttpServletRequest request, ModelMap modelMap) {
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/designer/metro_editor", modelMap);
	}

	@RequestMapping("/main")
	public ModelAndView designer(HttpServletRequest request, ModelMap modelMap) {
		String view = request.getParameter("view");
		// 获取UI列表
		List<FormDictory> uiList = FormDictoryFactory.getInstance().getFormDictoryListByTreeCode("ui_type");
		modelMap.put("uiList", uiList);
		// 获取历史页面设计
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			FormPage formPage = formPageService.getFormPage(id);
			modelMap.put("desingerHtml", formPage.getDesignerHtml());
			modelMap.put("desingerJson", formPage.getDesignerJson());
		}
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/designer/page_designer", modelMap);
	}

	/**
	 * 导出页面
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/download")
	@ResponseBody
	public void download(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		String pageLayout = request.getParameter("pageLayout");
		String themeType = request.getParameter("themeType") == null ? "bootstrap" : request.getParameter("themeType");
		if (StringUtils.isNotEmpty(pageLayout)) {
			try {
				String resultHtml = getConvertLayoutHtml(request, pageLayout, themeType);
				String filename = "pageLayout.html";
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				response.setContentType("application/OCTET-STREAM;charset=UTF-8");
				byte[] bpmnBytes = resultHtml.getBytes("UTF-8");
				ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
				IOUtils.copy(in, response.getOutputStream());
				response.flushBuffer();

			} catch (IOException | TemplateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 导出页面布局设计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/downloadDesigner")
	@ResponseBody
	public void downloadDesigner(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		String pageDesignerLayout = request.getParameter("pageDesigner");
		if (StringUtils.isNotEmpty(pageDesignerLayout)) {
			try {
				String filename = "pageDesignerLayout.tmpl";
				response.setHeader("Content-Disposition", "attachment; filename=" + filename);
				response.setContentType("application/OCTET-STREAM;charset=UTF-8");
				byte[] bpmnBytes = pageDesignerLayout.getBytes("UTF-8");
				ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
				IOUtils.copy(in, response.getOutputStream());
				response.flushBuffer();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getConvertLayoutHtml(HttpServletRequest request, String layoutHtml, String themeType)
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
			TemplateException {
		// 加载模板
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("utf-8");
		configuration.setServletContextForTemplateLoading(request.getServletContext(), "/WEB-INF/conf/templates");
		Template temp = configuration.getTemplate("page/" + themeType + ".tmpl");
		StringWriter stringWriter = new StringWriter();
		Map attributes = new HashMap();
		attributes.put("content", layoutHtml);
		attributes.put("contextPath", request.getContextPath());
		temp.process(attributes, stringWriter);
		String resultHtml = stringWriter.toString();
		return resultHtml;
	}

	@RequestMapping("/save")
	@ResponseBody
	public byte[] save(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		
				
		JSONObject jsonObj = new JSONObject();
		
		//platform_defined admin之外的用户 需要配置角色才可以操作
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Collection<String> roles = loginContext.getRoles();
		if(!loginContext.isSystemAdministrator() && !roles.contains("platform_defined")){
			jsonObj.put("result", -999);
			return JSON.toJSONString(jsonObj).getBytes("UTF-8");
		}
		try {
			String pageLayout = request.getParameter("pageLayout");
			String pageDesigner = request.getParameter("pageDesigner");
			String designerJson = request.getParameter("designerJson");
			String id = request.getParameter("id");
			String pId = request.getParameter("pId");
			String themeType = request.getParameter("themeType") == null ? "bootstrap"
					: request.getParameter("themeType");
			if (StringUtils.isNotEmpty(pageLayout) && StringUtils.isNotEmpty(pageDesigner)) {
				FormPage formPage = null;
				// pageLayout = URLDecoder.decode(pageLayout, "UTF-8");
				pageLayout = getConvertLayoutHtml(request, pageLayout, themeType);
				// pageDesigner = URLDecoder.decode(pageDesigner, "UTF-8");
				if (StringUtils.isNotEmpty(id)) {
					formPage = formPageService.getFormPage(id);
				} else if (StringUtils.isNotEmpty(pId)) {
					formPage = new FormPage();
					formPage.setParentId(pId);
				}
				formPage.setDesignerHtml(pageDesigner);
				formPage.setDesignerJson(designerJson);
				formPage.setFormHtml(pageLayout);
				formPageService.save(formPage);
			}
			jsonObj.put("result", 1);
		} catch (FormPageDesignerException e) {
			jsonObj.put("result", -2);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.put("result", -1);
		}

		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}
}
