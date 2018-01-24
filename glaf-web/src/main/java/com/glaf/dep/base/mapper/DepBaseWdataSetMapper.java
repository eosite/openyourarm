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

@Component("com.glaf.dep.base.mapper.DepBaseWdataSetMapper")
public interface DepBaseWdataSetMapper {

	void deleteDepBaseWdataSets(DepBaseWdataSetQuery query);

	void deleteDepBaseWdataSetById(Long id);

	DepBaseWdataSet getDepBaseWdataSetById(Long id);

	int getDepBaseWdataSetCount(DepBaseWdataSetQuery query);

	List<DepBaseWdataSet> getDepBaseWdataSets(DepBaseWdataSetQuery query);

	void insertDepBaseWdataSet(DepBaseWdataSet model);

	void updateDepBaseWdataSet(DepBaseWdataSet model);

}
