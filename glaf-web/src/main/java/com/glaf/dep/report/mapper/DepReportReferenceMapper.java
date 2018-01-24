package com.glaf.dep.report.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.dep.report.domain.*;
import com.glaf.dep.report.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.dep.report.mapper.DepReportReferenceMapper")
public interface DepReportReferenceMapper {

	void deleteDepReportReferences(DepReportReferenceQuery query);

	void deleteDepReportReferenceById(Long id);

	DepReportReference getDepReportReferenceById(Long id);

	int getDepReportReferenceCount(DepReportReferenceQuery query);

	List<DepReportReference> getDepReportReferences(DepReportReferenceQuery query);

	void insertDepReportReference(DepReportReference model);

	void updateDepReportReference(DepReportReference model);

}
