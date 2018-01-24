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

@Component("com.glaf.base.modules.uis.mapper.UisAppRegisterMapper")
public interface UisAppRegisterMapper {

	void deleteUisAppRegisters(UisAppRegisterQuery query);

	void deleteUisAppRegisterById(String id);

	UisAppRegister getUisAppRegisterById(String id);

	int getUisAppRegisterCount(UisAppRegisterQuery query);

	List<UisAppRegister> getUisAppRegisters(UisAppRegisterQuery query);

	void insertUisAppRegister(UisAppRegister model);

	void updateUisAppRegister(UisAppRegister model);

}
