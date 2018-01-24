package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.DotUse;
import com.glaf.isdp.query.DotUseQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.DotUseMapper")
public interface DotUseMapper {

	void deleteDotUses(DotUseQuery query);

	void deleteDotUseById(String id);

	DotUse getDotUseById(String id);

	int getDotUseCount(DotUseQuery query);

	List<DotUse> getDotUses(DotUseQuery query);

	void insertDotUse(DotUse model);

	void updateDotUse(DotUse model);

	int getDotUseCellFillFormCountByIndexId(Integer indexId);

	int getDotUseCellFillFormCountByTreepinfoId(String treepinfoId);

}
