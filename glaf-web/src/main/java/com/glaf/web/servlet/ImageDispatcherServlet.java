package com.glaf.web.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.web.domain.PageResource;
import com.glaf.web.service.PageResourceService;

public class ImageDispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5305608071138971757L;

	protected static final Log logger = LogFactory.getLog(ImageDispatcherServlet.class);
	protected PageResourceService pageResourceService;

	public ImageDispatcherServlet() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) {
		// 获取上下文路径
		String contextPath = request.getContextPath();
		// 获取请求路径
		String requestURI = request.getRequestURI();
		String resPath = requestURI.substring(contextPath.length(), requestURI.length());
		String urlPattern = "";
		urlPattern = urlPattern.replace("*", "");
		// 获取有效文件路径
		String filePath = resPath.replace(urlPattern, "");
		// 从redis缓存获取图片文件
        
		// 从数据库获取附件文件
		ByteArrayInputStream in = null;
		ServletOutputStream output = null;
		byte[] resContent = null;
		try {
			if (filePath != null && filePath.trim().length() > 0) {
				pageResourceService=getPageResourceService();
				PageResource pageResource = pageResourceService.getPageResourceByFilePath(filePath);
				if (pageResource != null) {
					resContent = pageResource.getResContent();
					//获取文件类型
					String fileType=pageResource.getResType();
					String contentType=pageResource.getResMime();
					output = response.getOutputStream();
					in = new ByteArrayInputStream(resContent);
					IOUtils.copy(in, output);
					response.setContentType(contentType);
					response.flushBuffer();
				}
			}
			
		} catch (IOException ex) {
			//ex.printStackTrace();
		}

	}

	public PageResourceService getPageResourceService() {
		if (pageResourceService == null) {
			pageResourceService = ContextFactory.getBean("com.glaf.web.service.pageResourceService");
		}
		return pageResourceService;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		service(request, response);
	}
}
