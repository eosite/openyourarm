package com.glaf.teim.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.teim.mapper.EimBaseInfoMapper")
public interface EimBaseInfoMapper {

	void deleteEimBaseInfos(EimBaseInfoQuery query);

	void deleteEimBaseInfoById(String id);

	EimBaseInfo getEimBaseInfoById(String id);

	int getEimBaseInfoCount(EimBaseInfoQuery query);

	List<EimBaseInfo> getEimBaseInfos(EimBaseInfoQuery query);

	void insertEimBaseInfo(EimBaseInfo model);

	void updateEimBaseInfo(EimBaseInfo model);
	
	List<Map> getAllEimBaseInfo();
}
