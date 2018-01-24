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

@Component("com.glaf.base.modules.uis.mapper.UisAppUserMapper")
public interface UisAppUserMapper {

	void deleteUisAppUsers(UisAppUserQuery query);

	void deleteUisAppUserById(String id);

	UisAppUser getUisAppUserById(String id);

	int getUisAppUserCount(UisAppUserQuery query);

	List<UisAppUser> getUisAppUsers(UisAppUserQuery query);

	void insertUisAppUser(UisAppUser model);

	void updateUisAppUser(UisAppUser model);

}
