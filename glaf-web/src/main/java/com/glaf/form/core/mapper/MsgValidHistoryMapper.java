package com.glaf.form.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.MsgValidHistoryMapper")
public interface MsgValidHistoryMapper {

	void deleteMsgValidHistorys(MsgValidHistoryQuery query);

	void deleteMsgValidHistoryById(String id);

	MsgValidHistory getMsgValidHistoryById(String id);

	int getMsgValidHistoryCount(MsgValidHistoryQuery query);

	List<MsgValidHistory> getMsgValidHistorys(MsgValidHistoryQuery query);

	void insertMsgValidHistory(MsgValidHistory model);

	void updateMsgValidHistory(MsgValidHistory model);

}
