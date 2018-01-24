package com.glaf.isdp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.TreeDot;
import com.glaf.isdp.query.TreeDotQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.TreeDotMapper")
public interface TreeDotMapper {

	void deleteTreeDots(TreeDotQuery query);

	void deleteTreeDotById(Integer id);

	TreeDot getTreeDotById(Integer id);

	int getTreeDotCount(TreeDotQuery query);

	List<TreeDot> getTreeDots(TreeDotQuery query);

	void insertTreeDot(TreeDot model);

	void updateTreeDot(TreeDot model);

	List<TreeDot> getTreeDotsAndChildCountByParentId(@Param("parentId")Integer parentId);
	
	List<TreeDot> getTreeDotsAndChildCountByIdLike(@Param("idLike") String idLike);
}
