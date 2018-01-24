package com.glaf.cell.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.glaf.cell.domain.CellCategory;
import com.glaf.cell.domain.DictionaryDim;
import com.glaf.cell.domain.ElementExtProp;
import com.glaf.cell.domain.FileDotExt;

public interface CellConvertTaskMapper {
	/**
	 * 增量获取待转换cell表的数量
	 * 
	 * @return
	 */
	public int getCellTemplateCount(@Param("useDomain") int useDomain, @Param("startDate") String startDate);

	/**
	 * 获取cell表模板
	 * 
	 * @return
	 */
	public List<FileDotExt> getCellTemplates(@Param("useDomain") int useDomain, @Param("startDate") String startDate);

	/**
	 * 获取cell表模板列扩展属性
	 * 
	 * @param fileId
	 * @return
	 */
	public List<ElementExtProp> getFileElementsExtProp(String fileId);

	/**
	 * 获取变长区栏目
	 * 
	 * @param param
	 * @return
	 */
	public List<CellCategory> getVarAreaCellCategorys(Map<String, Object> param);

	/**
	 * 更新栏目变成区范围
	 * 
	 * @param startRowIndex
	 * @param startColIndex
	 * @param endRowIndex
	 * @param endColIndex
	 */
	public void updateCellCategoryVarArea(@Param("startRowIndex") int startRowIndex,
			@Param("startColIndex") int startColIndex, @Param("endRowIndex") int endRowIndex,
			@Param("endColIndex") int endColIndex, @Param("cvtId") long cvtId, @Param("way") String way);

	/**
	 * 修改变长区单元格的变长标识为1
	 * 
	 * @param cvtId
	 */
	public void updateVarAreaElemsByCvtId(Long cvtId);

	/**
	 * 更新变长区元素类型
	 * 
	 * @param cvtId
	 */
	public void updateCellVarAreaElemDataRole(Long cvtId);

	/**
	 * 获取字典表维度定义
	 * 
	 * @param fileId
	 * @return
	 */
	List<DictionaryDim> getDictionaryDimByFileId(String fileId);

	/**
	 * 获取规范统计值字段定义
	 * 
	 * @param fileId
	 * @param dName
	 * @return
	 */
	ElementExtProp getCritStatFieldExtProp(@Param("fileId") String fileId, @Param("dName") String dName);
}
