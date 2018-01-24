package com.glaf.model.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.model.domain.*;
import com.glaf.model.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.model.mapper.SubSystemDefMapper")
public interface SubSystemDefMapper {

	void deleteSubSystemDefs(SubSystemDefQuery query);

	void deleteSubSystemDefById(String id);
	
	void deleteSubSystemDefBySysId(String sysId);

	SubSystemDef getSubSystemDefById(String id);

	int getSubSystemDefCount(SubSystemDefQuery query);

	List<SubSystemDef> getSubSystemDefs(SubSystemDefQuery query);

	List<SubSystemDef> getSystemSubSystems(String sysId);
	
	List<SubSystemDef> getSystemSubSystemsByTreeId(@Param("treeId")String treeId);

	void insertSubSystemDef(SubSystemDef model);

	void updateSubSystemDef(SubSystemDef model);

	void updateNameType(SubSystemDef model);
}
