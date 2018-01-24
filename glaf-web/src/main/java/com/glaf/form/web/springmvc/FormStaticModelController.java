package com.glaf.form.web.springmvc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.query.FormComponentQuery;
import com.glaf.form.core.query.FormPageQuery;
import com.glaf.form.core.query.FormRuleQuery;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.rule.Global;

/**
 * 静态化模块功能
 * @author J
 *
 */
@Controller("/form/static")
@RequestMapping("/form/static")
public class FormStaticModelController  {
	
	FormPageService formPageService;
	FormComponentService formComponentService;
	FormRuleService formRuleService;

	
	@RequestMapping("/publish")
	public byte[] staticPublish(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap,@RequestBody List<String> ids){
		String property = System.getProperty("DAO_TYPE");
		//super.publish(request, modelMap);
		String path = request.getServletContext().getRealPath("/");
		FormPageQuery query = new FormPageQuery();
		query.setIds(ids);
		List<FormPage> list = formPageService.list(query);
		// 查询所有控件
		FormComponentQuery formComponentQuery = new FormComponentQuery();
		formComponentQuery.setLocked(0);
		formComponentQuery.setDeleteFlag(0);
		List<FormComponent> formComponents = formComponentService.getComponentPropertyList(formComponentQuery);
		
		// 获取页面对应的规则
		FormRuleQuery formRuleQuery = new FormRuleQuery();
		formRuleQuery.setPageIds(ids);
		formRuleQuery.setLocked(0);// 有效
		formRuleQuery.deleteFlag(0);// 无删除
		List<FormRule> formRules = formRuleService.list(formRuleQuery);
		Map<String,List<FormRule>> mapsGroupById = new HashMap<>();
		String pageId = null;
		List<FormRule> ruleList = null;
		for (FormRule formRule : formRules) {
			pageId = formRule.getPageId();
			if(mapsGroupById.containsKey(pageId)){
				mapsGroupById.get(pageId).add(formRule);
			}else{
				ruleList = new ArrayList<>();
				ruleList.add(formRule);
				mapsGroupById.put(pageId,ruleList);
			}
		}
		
		FormPageController.exec(list, 100d, (subList) -> {
			FormPage formPage = null;
			for (Object object : subList) {
				formPage = (FormPage) object;
				if (formPage != null && formPage.getOutputHtml() != null) {
					try {
						// 获取页面对应的规则
						List<FormRule> rules = mapsGroupById.get(formPage.getId());
						// 执行发布
						Class<?> clazz = (Class<?>) Class.forName(Global.PAGE_PARSER_CLASS_NAME);
						if (clazz != null) {
							Object model = clazz.newInstance();
							Method method = clazz.getMethod("publish", FormPage.class, List.class, List.class, String.class, FormPageService.class);
							method.invoke(model, formPage, rules, formComponents, path, formPageService);
							
							
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		});
		return null;
	}
	
	
	/**
	 * 写文件开始前执行动作
	 */
	private void writeBefore(){
		
	}
	
	/**
	 *  写页面
	 */
	private void writePage(FormPage formPage){
		String outHtml = formPage.getOutputHtml();
		
	}
	
	/**
	 * 写文件
	 * @param file
	 * @param fileContent
	 * @throws IOException
	 */
	private void writeFile(File file,String fileContent) throws IOException{
		FileUtils.writeByteArrayToFile(file, fileContent.getBytes());
	}
	/**
	 * 写文件
	 * @param path
	 * @param fileContent
	 * @throws IOException
	 */
	private void writeFile(String path,String fileContent) throws IOException{
		writeFile(new File(path), fileContent);
	}
	
	public static void main(String[] args) {
		File file = new File("d:\\dd.txt");
		byte[] data = new String("aaaaa").getBytes();
		try {
			//FileUtils.moveFile(srcFile, destFile);
			org.apache.commons.io.FileUtils.writeByteArrayToFile(file, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
