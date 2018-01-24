package com.glaf.base.modules.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.base.modules.sys.model.FiledotUse;
import com.glaf.base.modules.sys.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface MyFiledotUseMapper {

	void deleteFiledotUses(FiledotUseQuery query);

	void deleteFiledotUseById(String id);

	FiledotUse getFiledotUseById(String id);

	int getFiledotUseCount(FiledotUseQuery query);

	List<FiledotUse> getFiledotUses(FiledotUseQuery query);

	void insertFiledotUse(FiledotUse model);

	void updateFiledotUse(FiledotUse model);

}
