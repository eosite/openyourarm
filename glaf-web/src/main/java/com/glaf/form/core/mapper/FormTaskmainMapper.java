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

@Component("com.glaf.form.core.mapper.FormTaskmainMapper")
public interface FormTaskmainMapper {

	void deleteFormTaskmains(FormTaskmainQuery query);

	void deleteFormTaskmainById(Long id);

	FormTaskmain getFormTaskmainById(Long id);

	int getFormTaskmainCount(FormTaskmainQuery query);

	List<FormTaskmain> getFormTaskmains(FormTaskmainQuery query);

	void insertFormTaskmain(FormTaskmain model);

	void updateFormTaskmain(FormTaskmain model);

}
