package com.glaf.cell.service;

import com.glaf.cell.domain.ElementExtProp;

public interface CellConvertReferenceService {
	/**
	 * 获取所属节点引用
	 * @param elementExtProp
	 * @return
	 */
    String getSelfWbsNodeReference(ElementExtProp elementExtProp);
	/**
	 * 获取WBS命名规则引用
	 * 
	 * @param elementExtProp
	 * @return
	 */
	String getWbsNameReference(String fileId, ElementExtProp elementExtProp);

	/**
	 * 引用WBS启动表格(treepinfo wbs实例表 ;mycell_tasks_sub cell wbs模板表单任务表 ; filedot
	 * cell定义表)
	 */
	String getStartWbsNodeReference(ElementExtProp elementExtProp);

	/**
	 * 获取工程信息
	 * 
	 * @param elementExtProp
	 * @return
	 */
	String getProjectReference(ElementExtProp elementExtProp);

	/**
	 * 获取下级工程信息引用 单位、分部、分项
	 * 
	 * @param elementExtProp
	 * @return
	 */
	String getSubProjectReference(ElementExtProp elementExtProp);
	/**
	 * 引用工程类型
	 * 
	 * @return
	 */
	String getProjectTypeReference(ElementExtProp elementExtProp);
	/**
	 * 获取引用规范 解析规范形成验证规则
	 * 
	 * @param elementExtProp
	 * @return
	 */
	String getCriterionReference(ElementExtProp elementExtProp);

	/**
	 * 获取引用规范参数 获取完成后需对页面变量赋值 用于规范验证与计算
	 * 
	 * @return
	 */
	String getCriterionParamReference(ElementExtProp elementExtProp);

	/**
	 * 获取字典引用SQL规则
	 * 
	 * @param fileId
	 * @return
	 */
	String getDirecoryReference(String fileId);

	/**
	 * 获取引用CELL表数据SQL
	 * 
	 * @param tableName
	 *            引用表名
	 * @param columnName
	 *            引用字段名
	 * @param fileId
	 *            被引用CELL表ID
	 * @return
	 */
	String getCellDataReference(String tableName, String columnName,
			String fileId);

	/**
	 * 获取引用业务表数据SQL
	 * 
	 * @param tableName
	 *            引用表名
	 * @param columnName
	 *            引用字段名
	 * @return
	 */
	String getBusinessDataReference(String tableName, String columnName);
}
