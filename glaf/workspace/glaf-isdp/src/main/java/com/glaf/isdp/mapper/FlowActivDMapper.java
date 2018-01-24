package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FlowActivD;
import com.glaf.isdp.query.FlowActivDQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FlowActivDMapper")
public interface FlowActivDMapper {

	void deleteFlowActivDs(FlowActivDQuery query);

	void deleteFlowActivDById(String id);

	FlowActivD getFlowActivDById(String id);

	int getFlowActivDCount(FlowActivDQuery query);

	List<FlowActivD> getFlowActivDs(FlowActivDQuery query);

	void insertFlowActivD(FlowActivD model);

	void updateFlowActivD(FlowActivD model);

	FlowActivD getLastFlowActivDByProcessId(String processId);

}
