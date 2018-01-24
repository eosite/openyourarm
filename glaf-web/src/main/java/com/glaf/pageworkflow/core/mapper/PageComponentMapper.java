package com.glaf.pageworkflow.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.pageworkflow.core.domain.ComponentProperty;
import com.glaf.pageworkflow.core.domain.PageComponent;



@Component("com.glaf.pageworkflow.web.core.mapper.PageComponentMapper")
public interface PageComponentMapper {
	/**
	 * 获取页面包含控件
	 * @param pageId
	 * @return
	 */
	public List<PageComponent> getPageComponentsByPageId(String pageId);
	/**
	 * 获取页面控件属性
	 * @param pageId
	 * @return
	 */
	public List<ComponentProperty> getComponentEventPropertiesByPageId(String pageId);

}
