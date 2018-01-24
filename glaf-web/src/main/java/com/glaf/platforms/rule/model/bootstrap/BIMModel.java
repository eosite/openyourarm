package com.glaf.platforms.rule.model.bootstrap;

import com.glaf.core.config.SystemConfig;
import com.glaf.platforms.rule.model.CommonModel;

public class BIMModel extends CommonModel {

	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		// sb.append("<link rel='stylesheet'
		// href='${contextPath}/scripts/bim/css/style.css'/>");
		// sb.append("<script
		// src='${contextPath}/scripts/bim/js/three.js'></script>");
		// sb.append("<script	/ src='${contextPath}/scripts/bim/js/viewer3D.js'></script>");
		// sb.append("<link rel=\"stylesheet\" href=\"https://developer.api.autodesk.com/viewingservice/v1/viewers/style.min.css\" type=\"text/css\"></link>");
		// sb.append("<script	src=\"https://developer.api.autodesk.com/viewingservice/v1/viewers/three.min.js\"></script>");
		// sb.append("<script src=\"https://developer.api.autodesk.com/viewingservice/v1/viewers/viewer3D.min.js\"></script>");
		sb.append("<script src='${contextPath}/scripts/jquery.bim2.js'></script>");
		scriptMap.put("bim", sb.toString());
		return templateScript;
	}

	@Override
	public String getElementHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("<div id='%s' data-role='bim'>", this.getId()));
		sb.append(String.format("<iframe frameborder=0 scrolling=no width='%s' height='%s' ", this.getWidth(), this.getHeight())
				+ " onload=BimOnload(this) "
				+"src='${contextPath}/mx/form/defined/ex/bimView'></iframe>");
		sb.append("</div");
		return sb.toString();
	}

	@Override
	public String getElementTagName() {
		return null;
	}
}
