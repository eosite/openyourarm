package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.ProjMuiProjList;
import com.glaf.isdp.query.ProjMuiProjListQuery;

@Transactional(readOnly = true)
public interface ProjMuiProjListService {

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
	List<ProjMuiProjList> list(ProjMuiProjListQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getProjMuiProjListCountByQueryCriteria(ProjMuiProjListQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ProjMuiProjList> getProjMuiProjListsByQueryCriteria(int start,
			int pageSize, ProjMuiProjListQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	ProjMuiProjList getProjMuiProjList(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ProjMuiProjList projMuiProjList);
	
	ProjMuiProjList getLocalProjMuiProjList(Integer intLocal);
}
