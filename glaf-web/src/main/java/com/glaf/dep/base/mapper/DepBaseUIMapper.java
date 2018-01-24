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

@Component("com.glaf.dep.base.mapper.DepBaseUIMapper")
public interface DepBaseUIMapper {

	void deleteDepBaseUIs(DepBaseUIQuery query);

	void deleteDepBaseUIById(String id);

	DepBaseUI getDepBaseUIById(String id);

	int getDepBaseUICount(DepBaseUIQuery query);

	List<DepBaseUI> getDepBaseUIs(DepBaseUIQuery query);

	void insertDepBaseUI(DepBaseUI model);

	void updateDepBaseUI(DepBaseUI model);

}
