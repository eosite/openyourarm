package com.glaf.platforms.rule.interfaces;

/**
 * GRID抽象接口
 * @author Administrator
 *
 */
public interface IGridRule extends IRule {
	
	/**
	 * 列
	 * @return
	 */
	String getColumns();
	/**
	 * 分页组件
	 * @return
	 */
	String getPageable();
}
