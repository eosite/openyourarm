package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.isdp.domain.*;
import com.glaf.isdp.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.RUtabletreeMapper")
public interface RUtabletreeMapper {

	void deleteRUtabletrees(RUtabletreeQuery query);

	void deleteRUtabletreeById(Integer id);

	RUtabletree getRUtabletreeById(Integer id);

	int getRUtabletreeCount(RUtabletreeQuery query);

	List<RUtabletree> getRUtabletrees(RUtabletreeQuery query);

	void insertRUtabletree(RUtabletree model);

	void updateRUtabletree(RUtabletree model);

}
