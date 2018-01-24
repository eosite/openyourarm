package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.RdataField;
import com.glaf.isdp.query.RdataFieldQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.RdataFieldMapper")
public interface RdataFieldMapper {

	void deleteRdataFields(RdataFieldQuery query);

	void deleteRdataFieldById(String id);

	RdataField getRdataFieldById(String id);

	int getRdataFieldCount(RdataFieldQuery query);

	List<RdataField> getRdataFields(RdataFieldQuery query);

	void insertRdataField(RdataField model);

	void updateRdataField(RdataField model);

	int getNextMaxUser(String tableid);

}
