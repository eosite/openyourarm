package com.glaf.isdp.render;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.glaf.core.security.LoginContext;
import com.glaf.base.modules.sys.model.CellTreedot;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;

public class RenderContext {

	protected CellTreedot cellTreedot;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected LoginContext loginContext;

	protected Map<String, Object> parameterMap = new HashMap<String, Object>();

	protected Map<String, Object> contextMap = new HashMap<String, Object>();

	protected IFieldInterfaceService fieldInterfaceService;

	public RenderContext() {

	}

	public RenderContext(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public RenderContext(LoginContext loginContext,
			Map<String, Object> parameterMap) {
		this.loginContext = loginContext;
		this.parameterMap = parameterMap;
	}

	public CellTreedot getCellTreedot() {
		return cellTreedot;
	}

	public Map<String, Object> getContextMap() {
		return contextMap;
	}

	public IFieldInterfaceService getFieldInterfaceService() {
		return fieldInterfaceService;
	}

	public LoginContext getLoginContext() {
		return loginContext;
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setAttribute(String key, Object value) {
		if (request != null && key != null) {
			request.setAttribute(key, value);
		}
	}

	public void setCellTreedot(CellTreedot cellTreedot) {
		this.cellTreedot = cellTreedot;
	}

	public void setContextMap(Map<String, Object> contextMap) {
		this.contextMap = contextMap;
	}

	public void setFieldInterfaceService(
			IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

	public void setLoginContext(LoginContext loginContext) {
		this.loginContext = loginContext;
	}

	public void setParameterMap(Map<String, Object> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
