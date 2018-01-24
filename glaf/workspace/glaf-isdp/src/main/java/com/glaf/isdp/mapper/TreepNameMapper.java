package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.TreepName;
import com.glaf.isdp.query.TreepNameQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.TreepNameMapper")
public interface TreepNameMapper {

	void deleteTreepNames(TreepNameQuery query);

	void deleteTreepNameById(Integer id);

	TreepName getTreepNameById(Integer id);

	int getTreepNameCount(TreepNameQuery query);

	List<TreepName> getTreepNames(TreepNameQuery query);

	void insertTreepName(TreepName model);

	void updateTreepName(TreepName model);

	TreepName getTreepNameByDomainIndexId(Integer domainIndexId);
}
