package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FlowProcess;
import com.glaf.isdp.query.FlowProcessQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FlowProcessMapper")
public interface FlowProcessMapper {

	void deleteFlowProcesss(FlowProcessQuery query);

	void deleteFlowProcessById(String id);

	FlowProcess getFlowProcessById(String id);

	int getFlowProcessCount(FlowProcessQuery query);

	List<FlowProcess> getFlowProcesss(FlowProcessQuery query);

	void insertFlowProcess(FlowProcess model);

	void updateFlowProcess(FlowProcess model);

}
