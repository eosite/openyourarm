package com.glaf.matrix.data.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.matrix.data.domain.*;
import com.glaf.matrix.data.query.*;

@Transactional(readOnly = true)
public interface RowDenormaliserService {

	@Transactional
	void bulkInsert(List<RowDenormaliser> list);

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
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	RowDenormaliser getRowDenormaliser(String id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getRowDenormaliserCountByQueryCriteria(RowDenormaliserQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<RowDenormaliser> getRowDenormalisersByQueryCriteria(int start, int pageSize, RowDenormaliserQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<RowDenormaliser> list(RowDenormaliserQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(RowDenormaliser rowDenormaliser);

}
