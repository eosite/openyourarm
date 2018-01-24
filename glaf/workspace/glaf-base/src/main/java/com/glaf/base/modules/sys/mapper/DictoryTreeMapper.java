package com.glaf.base.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.base.modules.sys.model.DictoryTree;
@Component
public interface DictoryTreeMapper {
	/**
	 * 根据sys_tree表Id获取字典树数据
	 * @param dictoryTreeId
	 * @return
	 */
	List<DictoryTree> getDictoryTreesByDictoryTreeId(Long dictoryTreeId);
	List<DictoryTree> getDictoryTreesByDictoryTreeId_oracle(Long dictoryTreeId);
	/**
	 * 根据sys_tree表code获取字典树数据
	 * @param treeCode
	 * @return
	 */
	List<DictoryTree> getDictoryTreesByTreeCode(@Param("treeCode")String  treeCode,@Param("category")String category);
	List<DictoryTree> getDictoryTreesByTreeCode_oracle(@Param("treeCode")String  treeCode,@Param("category")String category);

}
