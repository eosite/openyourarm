package com.glaf.form.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.ObjectCategory;
import com.glaf.form.core.query.ObjectCategoryQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.ObjectCategoryMapper")
public interface ObjectCategoryMapper {

	void deleteObjectCategorys(ObjectCategoryQuery query);

	void deleteObjectCategoryById(Long id);

	ObjectCategory getObjectCategoryById(Long id);

	int getObjectCategoryCount(ObjectCategoryQuery query);

	List<ObjectCategory> getObjectCategorys(ObjectCategoryQuery query);
	
	List<ObjectCategory> getPageCompTemplateObjectCategorys(@Param("actorId")String actorId,@Param("treeId")String treeId);

	void insertObjectCategory(ObjectCategory model);

	void updateObjectCategory(ObjectCategory model);
	
	void rename(@Param("categoryId")Long categoryId,@Param("name")String name);
	
	int getMaxOrder(@Param("categoryId")Long categoryId);

}
