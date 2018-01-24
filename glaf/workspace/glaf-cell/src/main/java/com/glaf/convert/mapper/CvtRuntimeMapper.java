package com.glaf.convert.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.convert.domain.CvtRuntime;
import com.glaf.convert.query.CvtRuntimeQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.CvtRuntimeMapper")
public interface CvtRuntimeMapper {

	void deleteCvtRuntimes(CvtRuntimeQuery query);

	void deleteCvtRuntimeById(String id);

	CvtRuntime getCvtRuntimeById(String id);

	int getCvtRuntimeCount(CvtRuntimeQuery query);

	List<CvtRuntime> getCvtRuntimes(CvtRuntimeQuery query);

	void insertCvtRuntime(CvtRuntime model);

	void updateCvtRuntime(CvtRuntime model);

}
