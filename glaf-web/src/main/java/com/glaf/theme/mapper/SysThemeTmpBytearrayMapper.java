package com.glaf.theme.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.theme.mapper.SysThemeTmpBytearrayMapper")
public interface SysThemeTmpBytearrayMapper {

	void deleteSysThemeTmpBytearrays(SysThemeTmpBytearrayQuery query);

	void deleteSysThemeTmpBytearrayById(String id);
	
	void deleteByBuss(@Param("bussType") String bussType,@Param("bussKey")  String bussKey,@Param("type")  String type);

	SysThemeTmpBytearray getSysThemeTmpBytearrayById(String id);

	int getSysThemeTmpBytearrayCount(SysThemeTmpBytearrayQuery query);

	List<SysThemeTmpBytearray> getSysThemeTmpBytearrays(SysThemeTmpBytearrayQuery query);

	void insertSysThemeTmpBytearray(SysThemeTmpBytearray model);

	void updateSysThemeTmpBytearray(SysThemeTmpBytearray model);
	
}
