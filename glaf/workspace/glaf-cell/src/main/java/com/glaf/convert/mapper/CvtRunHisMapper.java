package com.glaf.convert.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.convert.domain.CvtRunHis;
import com.glaf.convert.query.CvtRunHisQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.CvtRunHisMapper")
public interface CvtRunHisMapper {

	void deleteCvtRunHiss(CvtRunHisQuery query);

	void deleteCvtRunHisById(String id);

	CvtRunHis getCvtRunHisById(String id);

	int getCvtRunHisCount(CvtRunHisQuery query);

	List<CvtRunHis> getCvtRunHiss(CvtRunHisQuery query);

	void insertCvtRunHis(CvtRunHis model);

	void updateCvtRunHis(CvtRunHis model);

}
