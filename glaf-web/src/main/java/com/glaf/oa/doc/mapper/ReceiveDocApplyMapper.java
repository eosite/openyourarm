package com.glaf.oa.doc.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.glaf.oa.doc.domain.*;
import com.glaf.oa.doc.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.oa.doc.mapper.ReceiveDocApplyMapper")
public interface ReceiveDocApplyMapper {

	void deleteReceiveDocApplys(ReceiveDocApplyQuery query);

	void deleteReceiveDocApplyById(Integer id);

	ReceiveDocApply getReceiveDocApplyById(Integer id);

	int getReceiveDocApplyCount(ReceiveDocApplyQuery query);

	List<ReceiveDocApply> getReceiveDocApplys(ReceiveDocApplyQuery query);

	void insertReceiveDocApply(ReceiveDocApply model);

	void updateReceiveDocApply(ReceiveDocApply model);

}
