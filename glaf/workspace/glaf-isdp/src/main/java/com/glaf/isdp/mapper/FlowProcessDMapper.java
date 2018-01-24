package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FlowProcessD;
import com.glaf.isdp.query.FlowProcessDQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FlowProcessDMapper")
public interface FlowProcessDMapper {

	void deleteFlowProcessDs(FlowProcessDQuery query);

	void deleteFlowProcessDById(String id);

	FlowProcessD getFlowProcessDById(String id);

	int getFlowProcessDCount(FlowProcessDQuery query);

	List<FlowProcessD> getFlowProcessDs(FlowProcessDQuery query);

	void insertFlowProcessD(FlowProcessD model);

	void updateFlowProcessD(FlowProcessD model);

}
