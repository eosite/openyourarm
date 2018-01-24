package com.glaf.model.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.model.domain.SubSystemDef;
import com.glaf.model.query.SubSystemDefQuery;

 
@Transactional(readOnly = true)
public interface SubSystemDefService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(String id);
	/**
	 * 删除系统对应的子系统
	 * @param sysId
	 */
	@Transactional
	void deleteSubSystemDefBySysId(String sysId);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<String> subSysIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<SubSystemDef> list(SubSystemDefQuery query);
	 
	 /**
	  * 获取系统子系统划分
	  * @param sysId 系统ID
	  * @return
	  */
	 List<SubSystemDef> getSystemSubSystems(String sysId);
	 
	 /**
	  * 获取子系统划分
	  * @param subSysId 子系统ID
	  * @return
	  */
	 List<SubSystemDef> getSystemSubSystemsByTreeId(String treeId);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSubSystemDefCountByQueryCriteria(SubSystemDefQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SubSystemDef> getSubSystemDefsByQueryCriteria(int start, int pageSize,
			SubSystemDefQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SubSystemDef getSubSystemDef(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SubSystemDef subSystemDef);
	/**
	 * 保存子系統定义流程
	 * @param modelId
	 * @param subSysId
	 * @return
	 */
	@Transactional
	JSONObject saveSystemPlanByModelId(byte[] processModelBytes, String modelId, SubSystemDef subSystemDef,
			String user);

}
