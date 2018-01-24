package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.isdp.domain.*;
import com.glaf.isdp.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.RinterfaceMapper")
public interface RinterfaceMapper {

	void deleteRinterfaces(RinterfaceQuery query);

	void deleteRinterfaceById(Long id);

	Rinterface getRinterfaceById(String id);

	int getRinterfaceCount(RinterfaceQuery query);

	List<Rinterface> getRinterfaces(RinterfaceQuery query);

	void insertRinterface(Rinterface model);

	void updateRinterface(Rinterface model);

}
