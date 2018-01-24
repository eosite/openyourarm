package com.glaf.base.modules.sys.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.FiledotUse;
import com.glaf.base.modules.sys.query.*;

@Transactional(readOnly = true)
public interface FiledotUseService {

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
	List<FiledotUse> list(FiledotUseQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFiledotUseCountByQueryCriteria(FiledotUseQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FiledotUse> getFiledotUsesByQueryCriteria(int start, int pageSize,
			FiledotUseQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FiledotUse getFiledotUseById(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FiledotUse filedotUse);

}
