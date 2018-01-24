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

@Component("com.glaf.etl.mapper.EtlTransferTaskSrcMapper")
public interface EtlTransferTaskSrcMapper {

	void deleteEtlTransferTaskSrcs(EtlTransferTaskSrcQuery query);
	void deleteByTaskId(String id);
	void deleteEtlTransferTaskSrcById(String id);

	EtlTransferTaskSrc getEtlTransferTaskSrcById(String id);
	EtlTransferTaskSrc getEtlTransferTaskSrcByTaskId(String id);

	int getEtlTransferTaskSrcCount(EtlTransferTaskSrcQuery query);

	List<EtlTransferTaskSrc> getEtlTransferTaskSrcs(EtlTransferTaskSrcQuery query);

	void insertEtlTransferTaskSrc(EtlTransferTaskSrc model);

	void updateEtlTransferTaskSrc(EtlTransferTaskSrc model);

}
