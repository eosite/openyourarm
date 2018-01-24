package com.glaf.cell.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.cell.domain.DictionaryDim;
import com.glaf.cell.domain.ElementExtProp;
import com.glaf.cell.mapper.CellConvertTaskMapper;

@Service("com.glaf.cell.service.cellConvertReferenceService")
@Transactional(readOnly = true)
public class CellConvertReferenceServiceImpl implements
		CellConvertReferenceService {

	protected CellConvertTaskMapper cellConvertTaskMapper;

	/**
	 * 获取WBS命名规则引用
	 * 
	 * @param elementExtProp
	 * @return
	 */
	public String getWbsNameReference(String fileId,
			ElementExtProp elementExtProp) {
		String sql = "select c.index_name from cell_repinfo a,s_treewbs_name b,treepinfo c where "
				+ " a.data_fieldname='"
				+ elementExtProp.getDataFieldName()
				+ "' and a.data_fieldname=b.index_id and a.filedot_fileid ='"
				+ fileId
				+ "' "
				+ "and c.wbsindex=b.wbsindex and c.index_id in (dbo.getAutoInstIndex(@wbsInstId,"+"'"
				+ fileId + "'))";
		return sql;
	}

	/**
	 * 引用工程类型
	 * 
	 * @return
	 */
	public String getProjectTypeReference(ElementExtProp elementExtProp) {
		String sql = "select "
				+ elementExtProp.getDataFieldName()
				+ " from treeproj a,treepinfo b where a.index_id=b.fromid and b.index_id=@wbsInstId";
		return sql;
	}

	/**
	 * 引用WBS启动表格(treepinfo wbs实例表 ;mycell_tasks_sub cell wbs模板表单任务表 ; filedot
	 * cell定义表)
	 */
	public String getStartWbsNodeReference(ElementExtProp elementExtProp) {
		String columnName = "dotname";
		if (elementExtProp.getDataFieldName().equals("2")) {
			columnName = "c.num";
		}
		String sql = "select "
				+ columnName
				+ " from treepinfo a,dbo.mycell_tasks_sub b,filedot c where a.wbsindex=b.index_id and b.filedot_fileid=c.fileid and b.intistasks=22  and a.index_id=@wbsInstId order by b.listno asc";
		return sql;
	}
	/**
	 * 获取所属节点引用
	 * @param elementExtProp
	 * @return
	 */
   public String getSelfWbsNodeReference(ElementExtProp elementExtProp){
	   String sql ="select b."+elementExtProp.getDataFieldName()+" from treepinfo a left join dbo.pinfo b on a.index_id=b.index_id where a.index_id=@wbsInstId";
	   return sql;
   }
	/**
	 * 获取工程项目
	 */
	public String getProjectReference(
			ElementExtProp elementExtProp) {
		String sql = "select "
				+ elementExtProp.getDataFieldName()
				+ " from pinfo a,treepname b where a.index_id=b.index_id and domain_index=@domainId";
		return sql;
	}

	/**
	 * 获取下级工程信息引用 单位、分部、分项
	 * 
	 * @param elementExtProp
	 * @return
	 */
	public String getSubProjectReference(ElementExtProp elementExtProp) {
		// 引用类型
		int refType = elementExtProp.getNodeIndex();
		int projtype = 0;
		// 单位工程
		if (refType == 3) {
			projtype = 1;
		}
		// 分部工程
		else if (refType == 4) {
			projtype = 3;
		}
		// 分项工程
		else if (refType == 5) {
			projtype = 2;
		}
		String sql = "select a."
				+ elementExtProp.getDataFieldName()
				+ " from treepinfo a left join dbo.pinfo b on a.index_id=b.index_id where a.index_id=dbo.getTreepinfolistIdByType(@wbsInstId,'"
				+ projtype + "')";
		return sql;
	}

	/**
	 * 获取引用规范 解析规范形成验证规则
	 * 
	 * @param elementExtProp
	 * @return
	 */
	public String getCriterionReference(ElementExtProp elementExtProp) {
		String sql = "select * from cell_criterion  where id='"
				+ elementExtProp.getOrderId() + "' and index_id='"
				+ elementExtProp.getOrderIndex() + "'";
		return sql;
	}

	/**
	 * 获取引用规范参数 获取完成后需对页面变量赋值 用于规范验证与计算
	 * 
	 * @return
	 */
	public String getCriterionParamReference(ElementExtProp elementExtProp) {
		String sql = "select * from dbo.interface where frmtype='"
				+ elementExtProp.getDataTableName() + "' and fname='"
				+ elementExtProp.getDataFieldName() + "' and index_id='"
				+ elementExtProp.getOrderIndex() + "'";
		return sql;
	}

	/**
	 * 转换页面规范初始化参数值规则
	 */
	public void getInitCriterionParamVal() {

	}

	/**
	 * 获取引用字典表规则
	 * 
	 * @return
	 */
	public String getDirecoryReference(String fileId) {
		List<DictionaryDim> dictionaryDims = cellConvertTaskMapper
				.getDictionaryDimByFileId(fileId);
		String sql = "select val from sys_dictionary where 1=1 ";
		// 维度方向
		int dimWay = 0;
		for (DictionaryDim dictionaryDim : dictionaryDims) {
			dimWay = dictionaryDim.getDimWay();
			// 纵维
			if (dimWay == 0) {
				sql += " and row_index >= (select row_index from sys_dictionary where col_index>="
						+ dictionaryDim.getStartColIndex()
						+ " and col_index<="
						+ dictionaryDim.getEndColIndex()
						+ " and val=#{"
						+ fileId
						+ " "
						+ dictionaryDim.getDimId()
						+ "})"
						+ " and row_index <= (select row_indexend from sys_dictionary where col_index>="
						+ dictionaryDim.getStartColIndex()
						+ " and col_index<="
						+ dictionaryDim.getEndColIndex()
						+ " and val=#{"
						+ fileId + " " + dictionaryDim.getDimId() + "})";
			} else if (dimWay == 1) {
				sql += " and col_index >= (select col_index from sys_dictionary where row_index>="
						+ dictionaryDim.getStartRowIndex()
						+ " and row_index<="
						+ dictionaryDim.getEndRowIndex()
						+ " and val=#{"
						+ fileId
						+ " "
						+ dictionaryDim.getDimId()
						+ "})"
						+ " and col_index <= (select col_indexend from sys_dictionary where row_index>="
						+ dictionaryDim.getStartRowIndex()
						+ " and row_index<="
						+ dictionaryDim.getEndRowIndex()
						+ " and val=#{"
						+ fileId + " " + dictionaryDim.getDimId() + "})";
			}
		}
		return sql;
	}

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
	public String getCellDataReference(String tableName, String columnName,
			String fileId) {
		String sql = "select " + columnName + " from " + tableName
				+ " where index_id in (dbo.getAutoInstIndex(@wbsInstId,'"
				+ fileId + "'))";
		return sql;
	}

	/**
	 * 获取引用业务表数据SQL
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public String getBusinessDataReference(String tableName, String columnName) {
		String sql = "select " + columnName + " from " + tableName
				+ " where id=dbo.getBusinessDataInstId(@topId,'" + tableName
				+ "')";
		return sql;
	}

	@javax.annotation.Resource
	public void setCellConvertTaskMapper(
			CellConvertTaskMapper cellConvertTaskMapper) {
		this.cellConvertTaskMapper = cellConvertTaskMapper;
	}

}
