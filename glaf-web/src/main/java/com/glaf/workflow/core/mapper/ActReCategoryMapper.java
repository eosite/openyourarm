package com.glaf.workflow.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.workflow.core.domain.*;
import com.glaf.workflow.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.workflow.core.mapper.ActReCategoryMapper")
public interface ActReCategoryMapper {

	void deleteActReCategorys(ActReCategoryQuery query);

	void deleteActReCategoryById(Long id);

	ActReCategory getActReCategoryById(Long id);

	int getActReCategoryCount(ActReCategoryQuery query);

	List<ActReCategory> getActReCategorys(ActReCategoryQuery query);

	void insertActReCategory(ActReCategory model);

	void updateActReCategory(ActReCategory model);
	
    void rename(@Param("categoryId")Long categoryId,@Param("name")String name,@Param("modifier")String modifier,@Param("modifyDatetime")Date modifyDatatime);
	
	void move(@Param("categoryId")Long categoryId,@Param("pId")Long pId,@Param("treeId")String treeId,@Param("modifier")String modifier,@Param("modifyDatetime")Date modifyDatatime);
	
	void workFlowMoveToCategory(@Param("modelId")String modelId, @Param("categoryId")String categoryId);
}
