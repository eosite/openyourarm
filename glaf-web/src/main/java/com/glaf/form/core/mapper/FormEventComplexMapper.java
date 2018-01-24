package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormEventComplex;
import com.glaf.form.core.query.FormEventComplexQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.mapper.FormEventComplexMapper")
public interface FormEventComplexMapper {

	void deleteFormEventComplexs(FormEventComplexQuery query);

	void deleteFormEventComplexById(String id);

	FormEventComplex getFormEventComplexById(String id);

	int getFormEventComplexCount(FormEventComplexQuery query);

	List<FormEventComplex> getFormEventComplexs(FormEventComplexQuery query);

	void insertFormEventComplex(FormEventComplex model);

	void updateFormEventComplex(FormEventComplex model);

	List<FormEventComplex> queryComplexByPageId(FormEventComplexQuery query);

}
