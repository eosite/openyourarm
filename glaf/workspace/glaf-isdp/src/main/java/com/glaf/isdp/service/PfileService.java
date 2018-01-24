package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.Pfile;
import com.glaf.isdp.query.PfileQuery;

@Transactional(readOnly = true)
public interface PfileService {

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
	void deleteByIds(List<String> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Pfile> list(PfileQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getPfileCountByQueryCriteria(PfileQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Pfile> getPfilesByQueryCriteria(int start, int pageSize,
			PfileQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Pfile getPfile(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Pfile pfile);

	List<Pfile> getPfileDataSum(List<String> idLikeList, String sDate, String eDate);
}
