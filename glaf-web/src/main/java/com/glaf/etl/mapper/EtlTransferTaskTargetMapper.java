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

@Component("com.glaf.etl.mapper.EtlTransferTaskTargetMapper")
public interface EtlTransferTaskTargetMapper {

	void deleteEtlTransferTaskTargets(EtlTransferTaskTargetQuery query);
	void deleteByTaskId(String id);
	void deleteEtlTransferTaskTargetById(String id);

	EtlTransferTaskTarget getEtlTransferTaskTargetById(String id);
	EtlTransferTaskTarget getEtlTransferTaskTargetByTaskId(String id);
	
	int getEtlTransferTaskTargetCount(EtlTransferTaskTargetQuery query);

	List<EtlTransferTaskTarget> getEtlTransferTaskTargets(EtlTransferTaskTargetQuery query);

	void insertEtlTransferTaskTarget(EtlTransferTaskTarget model);

	void updateEtlTransferTaskTarget(EtlTransferTaskTarget model);

}
