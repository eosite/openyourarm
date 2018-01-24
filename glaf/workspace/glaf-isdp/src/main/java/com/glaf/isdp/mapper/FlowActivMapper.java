package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FlowActiv;
import com.glaf.isdp.query.FlowActivQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FlowActivMapper")
public interface FlowActivMapper {

	void deleteFlowActivs(FlowActivQuery query);

	void deleteFlowActivById(String id);

	FlowActiv getFlowActivById(String id);

	int getFlowActivCount(FlowActivQuery query);

	List<FlowActiv> getFlowActivs(FlowActivQuery query);

	void insertFlowActiv(FlowActiv model);

	void updateFlowActiv(FlowActiv model);

}
