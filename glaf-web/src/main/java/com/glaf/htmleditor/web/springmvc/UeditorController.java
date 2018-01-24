package com.glaf.htmleditor.web.springmvc;


import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;
import com.glaf.web.service.PageResourceService;

@Controller
@RequestMapping(value = "/ueditor")
public class UeditorController{
    
	protected PageResourceService pageResourceService;
	
	@RequestMapping("/dispatch")
	@ResponseBody
	public byte[] config(HttpServletRequest request,
			HttpServletResponse response, String action) throws UnsupportedEncodingException {
		 String exec = "";

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.setContentType("application/json");
		String rootPath = request.getServletContext()
				.getRealPath("/");
		ActionEnter actionEnter=new ActionEnter(request, rootPath); 
		actionEnter.setPageResourceService(pageResourceService);
		exec = actionEnter.exec();
		//处理在线管理图片路径错误问题
		if( action!=null && 
				   (action.equals("listfile") || action.equals("listimage") ) ){
				    rootPath = rootPath.replace("\\", "/");
				    //exec = exec.replaceAll(rootPath, request.getContextPath()+"/");//把返回路径中的物理路径替换为 '/'
				    exec = exec.replaceAll(rootPath, request.getContextPath());//针对图片保存到库处理
				}	
		return exec.getBytes("UTF-8");
	}
    @Resource
	public void setPageResourceService(PageResourceService pageResourceService) {
		this.pageResourceService = pageResourceService;
	}

}
