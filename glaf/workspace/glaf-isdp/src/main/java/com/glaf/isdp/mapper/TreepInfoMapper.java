package com.glaf.isdp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.TreepInfo;
import com.glaf.isdp.query.TreepInfoQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.TreepInfoMapper")
public interface TreepInfoMapper {

	void deleteTreepInfos(TreepInfoQuery query);

	void deleteTreepInfoById(Integer id);

	void deleteTreepInfoCountByType(String type);

	TreepInfo getTreepInfoById(Integer id);

	int getTreepInfoCount(TreepInfoQuery query);

	List<TreepInfo> getTreepInfos(TreepInfoQuery query);

	void insertTreepInfo(TreepInfo model);

	void updateTreepInfo(TreepInfo model);

	int countProjectType(String projtype);

	List<TreepInfo> getTreepInfoListByParentId(Integer parentId);

	List<TreepInfo> getTreepInfoByProjType(String projtype);

	List<TreepInfo> getTreepInfoByTreedotIndexId(@Param("parentId") int parentId,
			@Param("treedotIndexId") String treedotIndexId);

	List<TreepInfo> getAllWBSTreepInfos(TreepInfoQuery query);

	void updateTreepInfoByWhereCause(@Param("fieldString") String fieldString,
			@Param("whereCondition") String whereCondition);

	List<TreepInfo> getLastSubSection(TreepInfoQuery query);

	List<TreepInfo> getFirstSubItem(TreepInfoQuery query);

	int validateHasChildrens(@Param("index_id")Integer index_id, @Param("projType")String projType);

	List<TreepInfo> getCellData(TreepInfoQuery query);

}
