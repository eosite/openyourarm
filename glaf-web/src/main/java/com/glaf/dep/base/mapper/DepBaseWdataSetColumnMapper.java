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

@Component("com.glaf.dep.base.mapper.DepBaseWdataSetColumnMapper")
public interface DepBaseWdataSetColumnMapper {

	void deleteDepBaseWdataSetColumns(DepBaseWdataSetColumnQuery query);

	void deleteDepBaseWdataSetColumnById(Long id);

	DepBaseWdataSetColumn getDepBaseWdataSetColumnById(Long id);

	int getDepBaseWdataSetColumnCount(DepBaseWdataSetColumnQuery query);

	List<DepBaseWdataSetColumn> getDepBaseWdataSetColumns(DepBaseWdataSetColumnQuery query);

	void insertDepBaseWdataSetColumn(DepBaseWdataSetColumn model);

	void updateDepBaseWdataSetColumn(DepBaseWdataSetColumn model);

}
