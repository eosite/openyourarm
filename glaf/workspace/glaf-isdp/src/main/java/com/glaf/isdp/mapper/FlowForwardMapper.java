package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FlowForward;
import com.glaf.isdp.query.FlowForwardQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FlowForwardMapper")
public interface FlowForwardMapper {

	void deleteFlowForwards(FlowForwardQuery query);

	void deleteFlowForwardById(String id);

	FlowForward getFlowForwardById(String id);

	int getFlowForwardCount(FlowForwardQuery query);

	List<FlowForward> getFlowForwards(FlowForwardQuery query);

	void insertFlowForward(FlowForward model);

	void updateFlowForward(FlowForward model);

}
