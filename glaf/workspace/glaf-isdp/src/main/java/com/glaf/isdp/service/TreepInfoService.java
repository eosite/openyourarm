package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.TreepInfo;
import com.glaf.isdp.domain.TreepInfoCount;
import com.glaf.isdp.query.TreepInfoQuery;

@Transactional(readOnly = true)
public interface TreepInfoService {

	/**
	 * 统计WBS结构
	 * @param projtype
	 * @return
	 */
	int countProjectType(String projtype);
	
	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Integer id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Integer> indexIds);

	List<TreepInfo> getAllWBSTreepInfos(TreepInfoQuery query);

	List<TreepInfo> getCellData(TreepInfoQuery query);

	/**
	 * 根据Id获取该节点下面所有的第一级分项工程
	 * @param idLike treepinfoId
	 */
	List<TreepInfo> getFirstSubItem(TreepInfoQuery query);

	List<TreepInfo> getFirstSubItemByCriteria(int start,int pageSize,String idLike);
	
	/**
	 * 根据id获取该节点下面所有的最后一级分部工程
	 * @param idLike treepinfoId
	 * @return
	 */
	List<TreepInfo> getLastSubSection(TreepInfoQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	TreepInfo getTreepInfo(Integer id);

	/**
	 * 根据项目类型获取数据 
	 * @param projtype 1：单位；2：分项； 3：分部； 4：工序
	 * @return
	 */
	List<TreepInfo> getTreepInfoByProjType(String projtype);

	/**
	 * 根据parentId和treedotIndexId获取数据
	 * @param parentId
	 * @param treedotIndexId
	 * @return
	 */
	List<TreepInfo> getTreepInfoByTreedotIndexId(int parentId,String treedotIndexId);
	
	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTreepInfoCountByQueryCriteria(TreepInfoQuery query);
	
	/**
	 * 根据parentId获取数据
	 * @param parentId 父节点indexId
	 * @return
	 */
	List<TreepInfo> getTreepInfoListByParentId(Integer parentId);
	
	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TreepInfo> getTreepInfosByQueryCriteria(int start, int pageSize,
			TreepInfoQuery query);
	
	/**
	 * 获取单位工程列表
	 * @param systemName
	 * @return
	 */
	List<TreepInfo> getUnitList();
	
	@Transactional
	void insertAll(String type, List<TreepInfoCount> rows);
	
	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<TreepInfo> list(TreepInfoQuery query);
	
	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TreepInfo treepInfo);
	
	int validateHasChildrens(Integer index_id,String projType);
}
