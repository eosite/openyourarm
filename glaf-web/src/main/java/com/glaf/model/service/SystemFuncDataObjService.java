package com.glaf.model.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.model.domain.*;
import com.glaf.model.query.*;

@Transactional(readOnly = true)
public interface SystemFuncDataObjService {

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
	void deleteByIds(List<String> sysDataObjIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SystemFuncDataObj> list(SystemFuncDataObjQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSystemFuncDataObjCountByQueryCriteria(SystemFuncDataObjQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SystemFuncDataObj> getSystemFuncDataObjsByQueryCriteria(int start, int pageSize, SystemFuncDataObjQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SystemFuncDataObj getSystemFuncDataObj(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SystemFuncDataObj systemFuncDataObj);
    /**
     * 保存功能输入输出数据对象
     * @param user
     * @param funcId
     * @param dataObjFuncId
     * @param dataObjType
     */
	@Transactional
	 void saveFuncDataObj(String user,String funcId,String dataObjFuncId,int dataObjType);

}
