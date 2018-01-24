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

@Component("com.glaf.etl.mapper.EtlTransferTaskInstMapper")
public interface EtlTransferTaskInstMapper {

	void deleteEtlTransferTaskInsts(EtlTransferTaskInstQuery query);

	void deleteEtlTransferTaskInstById(String id);

	EtlTransferTaskInst getEtlTransferTaskInstById(String id);

	int getEtlTransferTaskInstCount(EtlTransferTaskInstQuery query);

	List<EtlTransferTaskInst> getEtlTransferTaskInsts(EtlTransferTaskInstQuery query);

	void insertEtlTransferTaskInst(EtlTransferTaskInst model);

	void updateEtlTransferTaskInst(EtlTransferTaskInst model);

}
