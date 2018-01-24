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

@Component("com.glaf.oa.doc.mapper.SendDocApplyMapper")
public interface SendDocApplyMapper {

	void deleteSendDocApplys(SendDocApplyQuery query);

	void deleteSendDocApplyById(Integer id);

	SendDocApply getSendDocApplyById(Integer id);

	int getSendDocApplyCount(SendDocApplyQuery query);

	List<SendDocApply> getSendDocApplys(SendDocApplyQuery query);

	void insertSendDocApply(SendDocApply model);

	void updateSendDocApply(SendDocApply model);

}
