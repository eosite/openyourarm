package com.glaf.teim.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.teim.mapper.EimServerDataImpMapper")
public interface EimServerDataImpMapper {

	void deleteEimServerDataImps(EimServerDataImpQuery query);

	void deleteEimServerDataImpById(String id);

	EimServerDataImp getEimServerDataImpById(String id);

	int getEimServerDataImpCount(EimServerDataImpQuery query);

	List<EimServerDataImp> getEimServerDataImps(EimServerDataImpQuery query);

	void insertEimServerDataImp(EimServerDataImp model);

	void updateEimServerDataImp(EimServerDataImp model);
	
	List<Map> getEimServerDataImpData();

	void updateEimServerDataImpLog(@Param("id")String id,@Param("params")String params, @Param("successFlag")int successFlag, @Param("finishDate")Date finishDate);

}
