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

@Component("OperationMapper")
public interface OperationMapper {

	void deleteOperations(OperationQuery query);

	void deleteOperationById(Long id);

	Operation getOperationById(Long id);

	int getOperationCount(OperationQuery query);

	List<Operation> getOperations(OperationQuery query);

	void insertOperation(Operation model);

	void updateOperation(Operation model);

}
