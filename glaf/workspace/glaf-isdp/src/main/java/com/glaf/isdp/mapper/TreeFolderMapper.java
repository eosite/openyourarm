package com.glaf.isdp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.TreeFolder;
import com.glaf.isdp.query.TreeFolderQuery;

@Component("com.glaf.isdp.mapper.TreeFolderMapper")
public interface TreeFolderMapper {

	void deleteTreeFolderById(String id);

	void deleteTreeFolderByIndexId(int indexId);

	TreeFolder getTreeFolderById(String id);

	TreeFolder getTreeFolderByIndexId(int indexId);

	int getTreeFolderCount(Map<String, Object> parameter);

	int getTreeFolderCountByQueryCriteria(TreeFolderQuery query);

	List<TreeFolder> getTreeFolders(Map<String, Object> parameter);

	List<TreeFolder> getTreeFoldersByQueryCriteria(TreeFolderQuery query);

	void insertTreeFolder(TreeFolder model);

	void updateTreeFolder(TreeFolder model);

	TreeFolder getMaxTreeFolderByParentId(int parentId);

	void deleteTreeFolderByParentId(int indexId);

	void updateTreeFoldeNumByMove(@Param("selectedNextMaxNum")String selectedNextMaxNum, @Param("checkedNum")String checkedNum,
			@Param("cId")String cId);

	void updateTreeFoldeIndexNameByMove(@Param("cId")String cId);

	void updateTreeFoldeIdNameByMove(@Param("tagerId")String tagerId, @Param("cId")String cId);

}
