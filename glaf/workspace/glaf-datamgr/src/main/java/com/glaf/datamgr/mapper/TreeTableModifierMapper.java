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

@Component("com.glaf.datamgr.mapper.TreeTableModifierMapper")
public interface TreeTableModifierMapper {

	void bulkInsertTreeTableModifier(List<TreeTableModifier> list);

	void bulkInsertTreeTableModifier_oracle(List<TreeTableModifier> list);

	void deleteTreeTableModifierById(Long id);

	TreeTableModifier getTreeTableModifierById(Long id);

	int getTreeTableModifierCount(TreeTableModifierQuery query);

	List<TreeTableModifier> getTreeTableModifiers(TreeTableModifierQuery query);

	void insertTreeTableModifier(TreeTableModifier model);

	void updateTreeTableModifier(TreeTableModifier model);

}
