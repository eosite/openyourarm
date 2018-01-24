package com.glaf.platforms.rule.model;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class ProgressBarModel extends CommonModel implements IRule, CssRule, AttrRule {
	
	private static final long serialVersionUID = 1L;
	protected String templateScript = "" ;
	//增加模板
	public String getTemplateScript(){
		return templateScript;
	}
	public void addTemplate(String subTemplate){
		this.templateScript += subTemplate ;
	}
	@Override
	public String getElementTagName() {
		return "div";
	}

	@Override
	public String getElementHtml() {
		return null ;
	}
	@Override
	public String getBind() {
		return null;
	}
	/**
	 * 进度块模式
	 * @return
	 */
	public String getType(){
		return source.get("type") ;
	}
	/**
	 * 进度块数量
	 * @return
	 */
	public String getChunkCount(){
		return source.get("chunkCount") ;
	}
	/**
	 * 最小值
	 * @return
	 */
	public String getMin(){
		return source.get("min");
	}
	/**
	 * 最大值
	 * @return
	 */
	public String getMax(){
		return source.get("max");
	}
	/**
	 *  方向
	 * @return
	 */
	public String getOrientation(){
		return source.get("orientation");
	}
	/**
	 * 显示数字状态
	 * @return
	 */
	public String getShowStatus(){
		return source.get("showStatus");
	}
	/**
	 * 固定值
	 */
	public String getValue(){
		return source.get("value");
	}
}
