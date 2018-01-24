package com.glaf.matrix.data.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.matrix.data.domain.*;
import com.glaf.matrix.data.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.matrix.data.mapper.RowDenormaliserMapper")
public interface RowDenormaliserMapper {

	void bulkInsertRowDenormaliser(List<RowDenormaliser> list);

	void bulkInsertRowDenormaliser_oracle(List<RowDenormaliser> list);

	void deleteRowDenormalisers(RowDenormaliserQuery query);

	void deleteRowDenormaliserById(String id);

	RowDenormaliser getRowDenormaliserById(String id);

	int getRowDenormaliserCount(RowDenormaliserQuery query);

	List<RowDenormaliser> getRowDenormalisers(RowDenormaliserQuery query);

	void insertRowDenormaliser(RowDenormaliser model);

	void updateRowDenormaliser(RowDenormaliser model);

}
