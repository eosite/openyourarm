package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.TreeDot;
import com.glaf.isdp.query.TreeDotQuery;

@Transactional(readOnly = true)
public interface TreeDotService {

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

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<TreeDot> list(TreeDotQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTreeDotCountByQueryCriteria(TreeDotQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TreeDot> getTreeDotsByQueryCriteria(int start, int pageSize,
			TreeDotQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	TreeDot getTreeDot(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TreeDot treeDot);

	/**
	 * 根据父节点查询记录，并查询子节点个数
	 * @param parentId
	 * @return
	 */
	List<TreeDot> getTreeDotsAndChildCountByParentId(Integer parentId);
	
	List<TreeDot> getTreeDotsAndChildCountByIdLike(String idLike);

}
