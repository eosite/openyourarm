package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface FieldSetMapper {

	void deleteFieldSetByDatasetId(Long datasetId);

	void deleteFieldSetById(String id);

	FieldSet getFieldSetById(String id);

	int getFieldSetCount(FieldSetQuery query);

	List<FieldSet> getFieldSets(FieldSetQuery query);

	List<FieldSet> getFieldSetsDatasetId(Long datasetId);

	void insertFieldSet(FieldSet model);

	void updateFieldSet(FieldSet model);

}
