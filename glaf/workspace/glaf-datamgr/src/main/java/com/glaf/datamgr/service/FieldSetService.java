package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface FieldSetService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FieldSet> list(FieldSetQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFieldSetCountByQueryCriteria(FieldSetQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FieldSet> getFieldSetsByQueryCriteria(int start, int pageSize, FieldSetQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FieldSet getFieldSet(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FieldSet fieldSet);

}
