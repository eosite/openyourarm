package com.glaf.isdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.DotUse;
import com.glaf.isdp.query.DotUseQuery;

@Transactional(readOnly = true)
public interface DotUseService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> fileIDs);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DotUse> list(DotUseQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDotUseCountByQueryCriteria(DotUseQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DotUse> getDotUsesByQueryCriteria(int start, int pageSize,
			DotUseQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DotUse getDotUse(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DotUse dotUse);

	int getDotUseCellFillFormCountByIndexId(int indexId);
	
	List<Map<String, Object>> getDotUseCellFillFormByIndexId(int start, int pageSize,int indexId);
	
	int getDotUseCellFillFormCountByTreepinfoId(String treepinfoId);
	
	List<Map<String, Object>> getDotUseCellFillFormByTreepinfoId(int start, int pageSize,String treepinfoId);

}
