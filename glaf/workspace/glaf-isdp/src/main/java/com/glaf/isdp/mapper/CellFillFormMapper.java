package com.glaf.isdp.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellFillForm;
import com.glaf.isdp.query.CellFillFormQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellFillFormMapper")
public interface CellFillFormMapper {

	void deleteCellFillForms(CellFillFormQuery query);

	void deleteCellFillFormById(String id);

	CellFillForm getCellFillFormById(String id);

	int getCellFillFormCount(CellFillFormQuery query);

	List<CellFillForm> getCellFillForms(CellFillFormQuery query);

	void insertCellFillForm(CellFillForm model);

	void updateCellFillForm(CellFillForm model);

	List<Map<String, Integer>> selectCellSum(CellFillFormQuery query);

}
