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

@Component("com.glaf.form.core.mapper.FormObjectIoMapper")
public interface FormObjectIoMapper {

	void deleteFormObjectIos(FormObjectIoQuery query);

	void deleteFormObjectIoById(String id);

	FormObjectIo getFormObjectIoById(String id);

	int getFormObjectIoCount(FormObjectIoQuery query);

	List<FormObjectIo> getFormObjectIos(FormObjectIoQuery query);

	void insertFormObjectIo(FormObjectIo model);

	void updateFormObjectIo(FormObjectIo model);

}
