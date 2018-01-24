package com.glaf.form.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface FormPageHistoryMapper {

	void bulkInsertFormPageHistory(List<FormPageHistory> list);

	void bulkInsertFormPageHistory_oracle(List<FormPageHistory> list);

	FormPageHistory getFormPageHistoryById(String id);

	int getFormPageHistoryCount(FormPageHistoryQuery query);
	
	List<FormPageHistory> getFormPageHistorys(FormPageHistoryQuery query);

	Integer getMaxVersionFormPageHistory(String pageId);

	void insertFormPageHistory(FormPageHistory model);

	void deleteOldVersion(@Param("pageId")String pageId,@Param("num") int num);

}
