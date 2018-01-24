package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FileDot;
import com.glaf.isdp.query.FileDotQuery;

@Transactional(readOnly = true)
public interface FileDotService {

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
	List<FileDot> list(FileDotQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFileDotCountByQueryCriteria(FileDotQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FileDot> getFileDotsByQueryCriteria(int start, int pageSize,
			FileDotQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FileDot getFileDot(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FileDot fileDot);

}
