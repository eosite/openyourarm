package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellDataField;
import com.glaf.isdp.query.CellDataFieldQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellDataFieldMapper")
public interface CellDataFieldMapper {

	void deleteCellDataFields(CellDataFieldQuery query);

	void deleteCellDataFieldById(String id);

	CellDataField getCellDataFieldById(String id);

	int getCellDataFieldCount(CellDataFieldQuery query);

	List<CellDataField> getCellDataFields(CellDataFieldQuery query);

	void insertCellDataField(CellDataField model);

	void updateCellDataField(CellDataField model);

	Integer getNextMaxUser(String tableId);

	CellDataField getCellDataFieldByFieldName(String fieldName);
}
