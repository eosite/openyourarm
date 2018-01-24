package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FlowStation;
import com.glaf.isdp.query.FlowStationQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FlowStationMapper")
public interface FlowStationMapper {

	void deleteFlowStations(FlowStationQuery query);

	void deleteFlowStationById(String id);

	FlowStation getFlowStationById(String id);

	int getFlowStationCount(FlowStationQuery query);

	List<FlowStation> getFlowStations(FlowStationQuery query);

	void insertFlowStation(FlowStation model);

	void updateFlowStation(FlowStation model);

}
