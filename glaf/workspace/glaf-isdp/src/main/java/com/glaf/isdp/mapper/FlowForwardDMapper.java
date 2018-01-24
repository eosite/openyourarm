package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FlowForwardD;
import com.glaf.isdp.query.FlowForwardDQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FlowForwardDMapper")
public interface FlowForwardDMapper {

	void deleteFlowForwardDs(FlowForwardDQuery query);

	void deleteFlowForwardDById(String id);

	FlowForwardD getFlowForwardDById(String id);

	int getFlowForwardDCount(FlowForwardDQuery query);

	List<FlowForwardD> getFlowForwardDs(FlowForwardDQuery query);

	void insertFlowForwardD(FlowForwardD model);

	void updateFlowForwardD(FlowForwardD model);

}
