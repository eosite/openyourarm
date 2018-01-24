package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.ObjectTemplateStyle;
import com.glaf.form.core.query.ObjectTemplateStyleQuery;

 
@Transactional(readOnly = true)
public interface ObjectTemplateStyleService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(Long id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<Long> styleIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ObjectTemplateStyle> list(ObjectTemplateStyleQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getObjectTemplateStyleCountByQueryCriteria(ObjectTemplateStyleQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ObjectTemplateStyle> getObjectTemplateStylesByQueryCriteria(int start, int pageSize,
			ObjectTemplateStyleQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ObjectTemplateStyle getObjectTemplateStyle(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ObjectTemplateStyle objectTemplateStyle);
	/**
	 * 保持样式规则
	 * @param templateId
	 * @param stypeRuleContent
	 */
	@Transactional
	 void saveTemplateStyle(Long templateId,String stypeRuleContent,String creator);

}
