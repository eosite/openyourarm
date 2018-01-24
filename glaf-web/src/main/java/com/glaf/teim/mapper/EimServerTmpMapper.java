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

@Component("com.glaf.teim.mapper.EimServerTmpMapper")
public interface EimServerTmpMapper {

	void deleteEimServerTmps(EimServerTmpQuery query);

	void deleteEimServerTmpById(String id);

	EimServerTmp getEimServerTmpById(String id);

	int getEimServerTmpCount(EimServerTmpQuery query);

	List<EimServerTmp> getEimServerTmps(EimServerTmpQuery query);

	void insertEimServerTmp(EimServerTmp model);

	void updateEimServerTmp(EimServerTmp model);

	void moveToCategory(@Param("tmpIds")String tmpIds, @Param("categoryId")String categoryId);
	
	List<EimServerTmpTree> getEimServerTmpCategoryTree();
	
	List<EimServerTmpTree> getEimServerTmpTree();

}
