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
public interface TreeTableSyntheticMapper {

	void bulkInsertTreeTableSynthetic(List<TreeTableSynthetic> list);

	void bulkInsertTreeTableSynthetic_oracle(List<TreeTableSynthetic> list);

	void deleteTreeTableSynthetics(TreeTableSyntheticQuery query);

	void deleteTreeTableSyntheticById(Long id);

	TreeTableSynthetic getTreeTableSyntheticById(Long id);

	int getTreeTableSyntheticCount(TreeTableSyntheticQuery query);

	List<TreeTableSynthetic> getTreeTableSynthetics(TreeTableSyntheticQuery query);

	void insertTreeTableSynthetic(TreeTableSynthetic model);
	
	void resetAllTreeTableSyntheticStatus();

	void updateTreeTableSynthetic(TreeTableSynthetic model);
	
	void updateTreeTableSyntheticStatus(TreeTableSynthetic model);

}
