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

@Component("com.glaf.form.core.mapper.FormFileMapper")
public interface FormFileMapper {

	void deleteFormFiles(FormFileQuery query);

	void deleteFormFileById(String id);

	FormFile getFormFileById(String id);

	int getFormFileCount(FormFileQuery query);

	List<FormFile> getFormFiles(FormFileQuery query);

	void insertFormFile(FormFile model);

	void updateFormFile(FormFile model);

}
