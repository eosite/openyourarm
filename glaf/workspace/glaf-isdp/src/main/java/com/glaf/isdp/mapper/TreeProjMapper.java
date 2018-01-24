package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.TreeProj;
import com.glaf.isdp.query.TreeProjQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.TreeProjMapper")
public interface TreeProjMapper {

	void deleteTreeProjs(TreeProjQuery query);

	void deleteTreeProjById(Integer id);

	TreeProj getTreeProjById(Integer id);

	int getTreeProjCount(TreeProjQuery query);

	List<TreeProj> getTreeProjs(TreeProjQuery query);

	void insertTreeProj(TreeProj model);

	void updateTreeProj(TreeProj model);

}
