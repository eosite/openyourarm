package com.glaf.dep.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.dep.base.domain.DepBaseCategory;
import com.glaf.dep.base.query.DepBaseCategoryQuery;

/**
 * 
 * Mapper接口
 * 
 */

@Component("com.glaf.dep.base.mapper.DepBaseCategoryMapper")
public interface DepBaseCategoryMapper {

	void deleteDepBaseCategorys(DepBaseCategoryQuery query);

	void deleteDepBaseCategoryById(Long id);

	DepBaseCategory getDepBaseCategoryById(Long id);

	int getDepBaseCategoryCount(DepBaseCategoryQuery query);

	List<DepBaseCategory> getDepBaseCategorys(DepBaseCategoryQuery query);

	void insertDepBaseCategory(DepBaseCategory model);

	void updateDepBaseCategory(DepBaseCategory model);

	void updateDelFlagByTreeId(@Param("treeId")String treeId,@Param("delFlag")String delFlag);

	DepBaseCategory getDepBaseCategorysByCode(String code);
	DepBaseCategory getDepBaseCategorysByCode_oracle(String code);
	
	List<DepBaseCategory> getChildrenByPid(Long pid);
	
	String getMaxCodeByPid(@Param("pId")Long pId,@Param("code")String code);
}
