package com.glaf.teim.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.teim.mapper.EimServerCategoryMapper")
public interface EimServerCategoryMapper {

	void deleteEimServerCategorys(EimServerCategoryQuery query);

	void deleteEimServerCategoryById(Long id);

	EimServerCategory getEimServerCategoryById(Long id);

	int getEimServerCategoryCount(EimServerCategoryQuery query);

	List<EimServerCategory> getEimServerCategorys(EimServerCategoryQuery query);

	void insertEimServerCategory(EimServerCategory model);

	void updateEimServerCategory(EimServerCategory model);

	void rename(@Param("categoryId") Long categoryId, @Param("name") String name, @Param("modifier") String modifier,
			@Param("modifyDatetime") Date modifyDatatime);

	void move(@Param("categoryId") Long categoryId, @Param("pId") Long pId, @Param("treeId") String treeId,
			@Param("modifier") String modifier, @Param("modifyDatetime") Date modifyDatatime);

}
