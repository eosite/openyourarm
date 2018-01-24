package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface OrderBySegmentMapper {

	void deleteOrderBySegmentByDataSetId(String datasetId);

	void deleteOrderBySegmentById(Long id);

	void deleteOrderBySegments(OrderBySegmentQuery query);

	OrderBySegment getOrderBySegmentById(Long id);

	int getOrderBySegmentCount(OrderBySegmentQuery query);

	List<OrderBySegment> getOrderBySegments(OrderBySegmentQuery query);

	List<OrderBySegment> getOrderBySegmentsByDataSetId(String datasetId);

	void insertOrderBySegment(OrderBySegment model);

	void updateOrderBySegment(OrderBySegment model);

}
