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
public interface TreeTableAggregateMapper {

	void deleteTreeTableAggregates(TreeTableAggregateQuery query);

	void deleteTreeTableAggregateById(Long id);

	TreeTableAggregate getTreeTableAggregateById(Long id);

	int getTreeTableAggregateCount(TreeTableAggregateQuery query);

	List<TreeTableAggregate> getTreeTableAggregates(TreeTableAggregateQuery query);

	void insertTreeTableAggregate(TreeTableAggregate model);

	void resetAllTreeTableAggregateStatus();

	void updateTreeTableAggregate(TreeTableAggregate model);

	void updateTreeTableAggregateStatus(TreeTableAggregate model);

}
