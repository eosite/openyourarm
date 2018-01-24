package com.glaf.form.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormNodeMessageHistoryMapper")
public interface FormNodeMessageHistoryMapper {

	void deleteFormNodeMessageHistorys(FormNodeMessageHistoryQuery query);

	void deleteFormNodeMessageHistoryById(Long id);

	FormNodeMessageHistory getFormNodeMessageHistoryById(Long id);

	int getFormNodeMessageHistoryCount(FormNodeMessageHistoryQuery query);

	List<FormNodeMessageHistory> getFormNodeMessageHistorys(FormNodeMessageHistoryQuery query);

	void insertFormNodeMessageHistory(FormNodeMessageHistory model);

	void updateFormNodeMessageHistory(FormNodeMessageHistory model);

}
