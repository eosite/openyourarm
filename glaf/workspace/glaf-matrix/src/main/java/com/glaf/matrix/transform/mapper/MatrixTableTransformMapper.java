package com.glaf.matrix.transform.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.glaf.matrix.transform.domain.*;
import com.glaf.matrix.transform.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface MatrixTableTransformMapper {

	void bulkInsertMatrixTableTransform(List<MatrixTableTransform> list);

	void bulkInsertMatrixTableTransform_oracle(List<MatrixTableTransform> list);

	void deleteMatrixTableTransformById(String id);

	MatrixTableTransform getMatrixTableTransformById(String id);

	int getMatrixTableTransformCount(MatrixTableTransformQuery query);

	List<MatrixTableTransform> getMatrixTableTransforms(MatrixTableTransformQuery query);

	void insertMatrixTableTransform(MatrixTableTransform model);
	
	void resetAllMatrixTableTransformStatus();

	void updateMatrixTableTransform(MatrixTableTransform model);
	
	void updateMatrixTableTransformStatus(MatrixTableTransform model);

}
