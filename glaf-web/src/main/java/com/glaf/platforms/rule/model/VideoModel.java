package com.glaf.platforms.rule.model;

import java.io.File;
import java.io.IOException;

import com.glaf.core.config.SystemProperties;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.mchange.io.FileUtils;

public class VideoModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	public String getElementTagName() {
		return null;
	}

	@Override
	public String getBind() {
		return null;
	}

	public String getTemplateScript() {
		String model = source.get("model");
		String rootPath = SystemProperties.getConfigRootPath() ;
		String script = "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/video/ext/jquery.video.extend.js'></script>";
		String path = "" ;
		if(!isNullOrEmpty(model) && "ys".equalsIgnoreCase(model)){ //萤石云模式
			path = rootPath + "/conf/templates/form/ysVideo.css.tmp";
		}
		else if(!isNullOrEmpty(model) && "platform".equalsIgnoreCase(model)){
			path = rootPath + "/conf/templates/form/plateform.css.tmp";
		}
		else if(!isNullOrEmpty(model) && "traffic".equalsIgnoreCase(model)){
			path = rootPath + "/conf/templates/form/traffic.css.tmp";
		}
		else{
			path = rootPath + "/conf/templates/form/video.css.tmp";
		}
		File file = new File(path);
		try {
			// 修改备注   只有第一次控件id为video的时候才加载   如果页面有多个video控件的时候 则不加载当前JS
			scriptMap.put("video", FileUtils.getContentsAsString(file, "UTF-8").replace("Video_ID", super.getId())
					.replace("{{autoPlay}}", getAutoPlay())
					.replaceAll("[\\t\\n\\r]", "") + script) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this.templateScript;
	}

	@Override
	public String getElementHtml() {
		String model = source.get("model");
		String str = "";
		String rootPath = SystemProperties.getConfigRootPath() ;
		String path = "" ;
		String clsId = "";
		if(!isNullOrEmpty(model) && "ys".equalsIgnoreCase(model)){ //萤石云模式
			//clsid:8d7d8518-ca58-4863-b94d-3c616fda7b35
			path = rootPath + "/conf/templates/form/ysVideo.tmp";
			clsId = "clsid:" + source.get("clsid");
		}
		else if(!isNullOrEmpty(model) && "platform".equalsIgnoreCase(model)){ //萤石云模式
			//clsid:8d7d8518-ca58-4863-b94d-3c616fda7b35
			path = rootPath + "/conf/templates/form/platform.tmp";
			clsId = "clsid:" + source.get("clsid");
		}
		else if(!isNullOrEmpty(model) && "traffic".equalsIgnoreCase(model)){ //萤石云模式
			//clsid:8d7d8518-ca58-4863-b94d-3c616fda7b35
			path = rootPath + "/conf/templates/form/traffic.tmp";
			clsId = "clsid:" + source.get("clsid");
		}
		else{
			path = rootPath + "/conf/templates/form/video.tmp";
		}
		File file = new File(path);
		try {
			str = FileUtils.getContentsAsString(file, "UTF-8")
					.replace("videoRid", super.getRuleId())
					.replace("Video_ID", super.getId())
					.replace("Video_CLSID", clsId)
					.replaceAll("[\\t\\n\\r]", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 是否自动播放
	 * @return
	 */
	public String getAutoPlay(){
		return this.getSource("autoPlay", "false");
	}
	/**
	 * 播放模式
	 * @return
	 */
	public String getModel(){
		return source.get("model");
	}
	
}
