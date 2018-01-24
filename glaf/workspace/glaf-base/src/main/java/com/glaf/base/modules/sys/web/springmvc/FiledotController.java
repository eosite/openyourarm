package com.glaf.base.modules.sys.web.springmvc;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.model.Filedot;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.base.modules.sys.service.IFiledotService;
import com.glaf.core.config.Environment;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/cell/filedot")
@RequestMapping("/cell/filedot")
public class FiledotController {
	protected static final Log logger = LogFactory
			.getLog(FiledotController.class);

	protected IFiledotService filedotService;

	protected IFieldInterfaceService fieldInterfaceService;

	public FiledotController() {

	}

	@ResponseBody
	@RequestMapping("/download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) {
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode",systemName);
		Environment.setCurrentSystemName(databaseCode);
		
		String fileId = request.getParameter("fileID");
		try {
			//fileId = RequestUtils.decodeString(fileId);
			fileId = new String(Base64.decodeBase64(fileId),"UTF-8");
			Filedot filedot = filedotService.getFiledot(fileId);
			Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
			if (filedot != null && filedot.getFileName() != null
					&& filedot.getFileContent() != null) {
				ResponseUtils.download(request, response,
						filedot.getFileContent(), filedot.getFileName());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(systemName);
		}
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils
					.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute(
					"fromUrl",
					RequestUtils.encodeURL(requestURI + "?"
							+ request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String frmType = request.getParameter("frmType");
		if (StringUtils.isNotEmpty(frmType)) {
			List<FieldInterface> fields = fieldInterfaceService
					.getListShowFieldsByFrmType(frmType);
			request.setAttribute("fields", fields);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/isdp/filedot/list", modelMap);
	}

	@javax.annotation.Resource
	public void setFieldInterfaceService(
			IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

	@javax.annotation.Resource(name = "filedotService")
	public void setFiledotService(IFiledotService filedotService) {
		this.filedotService = filedotService;
	}

}
