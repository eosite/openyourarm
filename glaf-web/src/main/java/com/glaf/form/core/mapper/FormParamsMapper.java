package com.glaf.form.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormParamsMapper")
public interface FormParamsMapper {

	void deleteFormParamss(FormParamsQuery query);

	void deleteFormParamsById(String id);

	FormParams getFormParamsById(String id);

	int getFormParamsCount(FormParamsQuery query);

	List<FormParams> getFormParamss(FormParamsQuery query);

	void insertFormParams(FormParams model);

	void updateFormParams(FormParams model);

	void deleteByParams(@Param("params")List<String> params, @Param("widgetId")String widgetId);

	List<FormParams> queryByParam(@Param("odatasetId")String odatasetId, @Param("outPage")String outPage, @Param("outid")String outid, @Param("param")String param);

	List<FormParams> queryByParamWithRuleId(@Param("odatasetId")String odatasetId, @Param("outPage")String outPage, @Param("ruleId")String ruleId, @Param("param")String param);
}
