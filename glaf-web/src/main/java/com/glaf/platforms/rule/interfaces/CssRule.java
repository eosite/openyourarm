package com.glaf.platforms.rule.interfaces;

import java.io.Serializable;
/**
 * 基础通用样式接口
 * @author Administrator
 *
 */
public interface CssRule extends Serializable {
	/**
	 * 宽度
	 * @return
	 */
	public String getWidth();
	/**
	 * 高度
	 * @return
	 */
	public String getHeight();
	
}
