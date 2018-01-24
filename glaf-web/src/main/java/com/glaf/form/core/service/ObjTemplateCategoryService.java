package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.ObjTemplateCategory;
import com.glaf.form.core.query.ObjTemplateCategoryQuery;

 
@Transactional(readOnly = true)
public interface ObjTemplateCategoryService {
	 
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
	 void deleteByIds(List<Long> categoryIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ObjTemplateCategory> list(ObjTemplateCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getObjTemplateCategoryCountByQueryCriteria(ObjTemplateCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ObjTemplateCategory> getObjTemplateCategorysByQueryCriteria(int start, int pageSize,
			ObjTemplateCategoryQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ObjTemplateCategory getObjTemplateCategory(Long id);

	    /**
		 * 根据主键获取一条记录
		 * 
		 * @return
		 */
		 ObjTemplateCategory getObjTemplateCategoryByTemplateId(Long id);

	 
        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ObjTemplateCategory objTemplateCategory);

}
