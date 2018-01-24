package com.glaf.dep.base.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.dep.base.mapper.DepBaseWdataSetParamMapper")
public interface DepBaseWdataSetParamMapper {

	void deleteDepBaseWdataSetParams(DepBaseWdataSetParamQuery query);

	void deleteDepBaseWdataSetParamById(Long id);

	DepBaseWdataSetParam getDepBaseWdataSetParamById(Long id);

	int getDepBaseWdataSetParamCount(DepBaseWdataSetParamQuery query);

	List<DepBaseWdataSetParam> getDepBaseWdataSetParams(DepBaseWdataSetParamQuery query);

	void insertDepBaseWdataSetParam(DepBaseWdataSetParam model);

	void updateDepBaseWdataSetParam(DepBaseWdataSetParam model);

}
