package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FileAtt;
import com.glaf.isdp.query.FileAttQuery;

@Transactional(readOnly = true)
public interface FileAttService {

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
	List<FileAtt> list(FileAttQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFileAttCountByQueryCriteria(FileAttQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FileAtt> getFileAttsByQueryCriteria(int start, int pageSize,
			FileAttQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FileAtt getFileAtt(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FileAtt fileAtt);

}
