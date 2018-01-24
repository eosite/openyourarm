package com.glaf.base.modules.uis.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.base.modules.uis.domain.*;
import com.glaf.base.modules.uis.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.base.modules.uis.mapper.UisAppUserIdMappingMapper")
public interface UisAppUserIdMappingMapper {

	void deleteUisAppUserIdMappings(UisAppUserIdMappingQuery query);

	void deleteUisAppUserIdMappingById(Long id);

	UisAppUserIdMapping getUisAppUserIdMappingById(Long id);

	int getUisAppUserIdMappingCount(UisAppUserIdMappingQuery query);

	List<UisAppUserIdMapping> getUisAppUserIdMappings(UisAppUserIdMappingQuery query);

	void insertUisAppUserIdMapping(UisAppUserIdMapping model);

	void updateUisAppUserIdMapping(UisAppUserIdMapping model);

}
