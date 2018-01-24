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

@Component("com.glaf.form.core.mapper.FormNodeMessageMapper")
public interface FormNodeMessageMapper {

	void deleteFormNodeMessages(FormNodeMessageQuery query);

	void deleteFormNodeMessageById(Long id);

	FormNodeMessage getFormNodeMessageById(Long id);

	int getFormNodeMessageCount(FormNodeMessageQuery query);

	List<FormNodeMessage> getFormNodeMessages(FormNodeMessageQuery query);

	void insertFormNodeMessage(FormNodeMessage model);

	void updateFormNodeMessage(FormNodeMessage model);

}
