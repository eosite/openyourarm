package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.ObjectCategory;
import com.glaf.form.core.query.ObjectCategoryQuery;

 
@Transactional(readOnly = true)
public interface ObjectCategoryService {
	 
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
	 List<ObjectCategory> list(ObjectCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getObjectCategoryCountByQueryCriteria(ObjectCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ObjectCategory> getObjectCategorysByQueryCriteria(int start, int pageSize,
			ObjectCategoryQuery query) ;
	 /**
	  * 获取页面复合控件模板分类
	  * @return
	  */
	 List<ObjectCategory> getPageCompTemplateObjectCategorys(String actorId,String treeId);

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ObjectCategory getObjectCategory(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ObjectCategory objectCategory);
	/**
	 * 修改名称
	 * @param categoryId
	 * @param name
	 */
	@Transactional
	 void rename(Long categoryId,String name) throws Exception;
	
	 int getMaxOrder(Long categoryId);

}
