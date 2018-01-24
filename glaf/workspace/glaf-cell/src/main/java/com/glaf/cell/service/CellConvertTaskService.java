package com.glaf.cell.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.cell.domain.FileDotExt;
import com.glaf.convert.domain.ConvertElemTmpl;

@Transactional(readOnly = true)
public interface CellConvertTaskService {
   
    /**
    * 增量获取待转换cell表模板的数量
    * @return
    */
	public int getCellTemplateCount(int useDomain,Date startDate);
	/**
	 * 分页获取待转换cell表模板
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	public List<FileDotExt> getCellTemplatesByPageId(int pageId, int pageSize,int useDomain,Date startDate);
    /**
     * 转换cell表模板
     * @param cellTemplate
     */
	public boolean convertCellTemplate(FileDotExt cellTemplate,String desPageType);

	/**
	 * 查询CELL表扩展属性配置更新到转换规则表中
	 * @param fileId
	 * @param elems
	 * @return
	 */
	public boolean convertCellElemExtProp(String fileId,Map<String,ConvertElemTmpl> elems);
}
