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

@Component("com.glaf.etl.mapper.EtlTransferTaskMapper")
public interface EtlTransferTaskMapper {

	void deleteEtlTransferTasks(EtlTransferTaskQuery query);

	void deleteEtlTransferTaskById(String id);

	EtlTransferTask getEtlTransferTaskById(String id);

	int getEtlTransferTaskCount(EtlTransferTaskQuery query);

	List<EtlTransferTask> getEtlTransferTasks(EtlTransferTaskQuery query);
	
	List<TaskSrcTarget> getTaskSrcTargetList(EtlTransferTaskQuery query);

	void insertEtlTransferTask(EtlTransferTask model);

	void updateEtlTransferTask(EtlTransferTask model);
	
	void updateTransferTaskStartStatus(EtlTransferTask etlTransferTask);
	
	void updateTransferTaskStatus(EtlTransferTask model);

}
