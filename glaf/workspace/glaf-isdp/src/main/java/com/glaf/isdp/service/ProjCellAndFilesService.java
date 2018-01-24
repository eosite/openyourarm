package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.ProjCellAndFiles;
import com.glaf.isdp.query.ProjCellAndFilesQuery;

@Transactional(readOnly = true)
public interface ProjCellAndFilesService {

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
	List<ProjCellAndFiles> list(ProjCellAndFilesQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getProjCellAndFilesCountByQueryCriteria(ProjCellAndFilesQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ProjCellAndFiles> getProjCellAndFilessByQueryCriteria(int start,
			int pageSize, ProjCellAndFilesQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	ProjCellAndFiles getProjCellAndFiles(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ProjCellAndFiles projCellAndFiles);

	/**
	 * 表格检查，查询节点下面表格填写情况
	 * @param indexId
	 * @return
	 */
	List<ProjCellAndFiles> getProjCellList(Integer indexId);
}
