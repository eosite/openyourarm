package com.glaf.model.mapper;


import java.util.List;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.model.domain.*;
import com.glaf.model.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.model.mapper.SystemFuncDataObjMapper")
public interface SystemFuncDataObjMapper {

	void deleteSystemFuncDataObjs(SystemFuncDataObjQuery query);

	void deleteSystemFuncDataObjById(String id);

	SystemFuncDataObj getSystemFuncDataObjById(String id);

	int getSystemFuncDataObjCount(SystemFuncDataObjQuery query);

	List<SystemFuncDataObj> getSystemFuncDataObjs(SystemFuncDataObjQuery query);

	void insertSystemFuncDataObj(SystemFuncDataObj model);

	void updateSystemFuncDataObj(SystemFuncDataObj model);
	
	void deleteFuncDataObj(@Param("funcId")String funcId,@Param("dataObjId")String dataObjFuncId,@Param("type")int dataObjType);

}
