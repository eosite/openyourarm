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
public interface MatrixColumnTransformMapper {

	void bulkInsertMatrixColumnTransform(List<MatrixColumnTransform> list);

	void bulkInsertMatrixColumnTransform_oracle(List<MatrixColumnTransform> list);

	void deleteMatrixColumnTransformById(Long id);
	
	void deleteMatrixColumnTransformsByTransformId(String transformId);

	MatrixColumnTransform getMatrixColumnTransformById(Long id);

	int getMatrixColumnTransformCount(MatrixColumnTransformQuery query);

	List<MatrixColumnTransform> getMatrixColumnTransforms(MatrixColumnTransformQuery query);

	List<MatrixColumnTransform> getMatrixColumnTransformsByTransformId(String transformId);

	void insertMatrixColumnTransform(MatrixColumnTransform model);

	void updateMatrixColumnTransform(MatrixColumnTransform model);

}
