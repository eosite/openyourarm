package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.ObjTemplate;
import com.glaf.form.core.query.ObjTemplateQuery;

 
@Transactional(readOnly = true)
public interface ObjTemplateService {
	 
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
	 void deleteByIds(List<Long> templateIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ObjTemplate> list(ObjTemplateQuery query);
	 /**
	  * 获取分类模板
	  * @param category
	  */
	 List<ObjTemplate> getCategoryTemplates(Long category);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getObjTemplateCountByQueryCriteria(ObjTemplateQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ObjTemplate> getObjTemplatesByQueryCriteria(int start, int pageSize,
			ObjTemplateQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ObjTemplate getObjTemplate(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ObjTemplate objTemplate);
	/**
	 * 保存模板到分类
	 * @param objTemplate
	 * @param categoryId
	 */
	@Transactional
	 void saveTemplateToCategory(ObjTemplate objTemplate,Long categoryId);

}
