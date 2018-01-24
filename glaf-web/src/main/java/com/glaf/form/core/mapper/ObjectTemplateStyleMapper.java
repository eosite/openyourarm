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

@Component("com.glaf.form.core.mapper.ObjectTemplateStyleMapper")
public interface ObjectTemplateStyleMapper {

	void deleteObjectTemplateStyles(ObjectTemplateStyleQuery query);

	void deleteObjectTemplateStyleById(Long id);

	ObjectTemplateStyle getObjectTemplateStyleById(Long id);

	int getObjectTemplateStyleCount(ObjectTemplateStyleQuery query);

	List<ObjectTemplateStyle> getObjectTemplateStyles(ObjectTemplateStyleQuery query);

	void insertObjectTemplateStyle(ObjectTemplateStyle model);

	void updateObjectTemplateStyle(ObjectTemplateStyle model);

}
