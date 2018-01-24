package com.glaf.form.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.ActReBusinessMapper")
public interface ActReBusinessMapper {

	void deleteActReBusinesss(ActReBusinessQuery query);

	void deleteActReBusinessById(Long id);

	ActReBusiness getActReBusinessById(Long id);

	int getActReBusinessCount(ActReBusinessQuery query);

	List<ActReBusiness> getActReBusinesss(ActReBusinessQuery query);

	void insertActReBusiness(ActReBusiness model);

	void updateActReBusiness(ActReBusiness model);

}
