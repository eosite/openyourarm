package com.glaf.datamgr.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface SyntheticFlowMapper {

	void bulkInsertSyntheticFlow(List<SyntheticFlow> list);

	void bulkInsertSyntheticFlow_oracle(List<SyntheticFlow> list);

	void deleteSyntheticFlowById(Long id);

	SyntheticFlow getSyntheticFlowById(Long id);

	int getSyntheticFlowCount(SyntheticFlowQuery query);

	List<SyntheticFlow> getSyntheticFlows(SyntheticFlowQuery query);

	void insertSyntheticFlow(SyntheticFlow model);

	void updateSyntheticFlow(SyntheticFlow model);

}
