package com.glaf.etl.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.etl.mapper.EtlDataTargetMapper")
public interface EtlDataTargetMapper {

	void deleteEtlDataTargets(EtlDataTargetQuery query);

	void deleteEtlDataTargetById(String id);

	EtlDataTarget getEtlDataTargetById(String id);

	int getEtlDataTargetCount(EtlDataTargetQuery query);

	List<EtlDataTarget> getEtlDataTargets(EtlDataTargetQuery query);

	void insertEtlDataTarget(EtlDataTarget model);

	void updateEtlDataTarget(EtlDataTarget model);

}
