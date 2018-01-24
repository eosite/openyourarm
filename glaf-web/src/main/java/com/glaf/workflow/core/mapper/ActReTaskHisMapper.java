package com.glaf.workflow.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.workflow.core.domain.*;
import com.glaf.workflow.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.workflow.core.mapper.ActReTaskHisMapper")
public interface ActReTaskHisMapper {

	void deleteActReTaskHiss(ActReTaskHisQuery query);

	void deleteActReTaskHisById(Integer id);

	ActReTaskHis getActReTaskHisById(Integer id);

	int getActReTaskHisCount(ActReTaskHisQuery query);

	List<ActReTaskHis> getActReTaskHiss(ActReTaskHisQuery query);

	void insertActReTaskHis(ActReTaskHis model);

	void updateActReTaskHis(ActReTaskHis model);

}
