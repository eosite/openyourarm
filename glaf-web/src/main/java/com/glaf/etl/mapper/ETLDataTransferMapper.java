package com.glaf.etl.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.etl.domain.ETLDataTransfer;
import com.glaf.etl.query.ETLDataTransferQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.etl.mapper.ETLDataTransferMapper")
public interface ETLDataTransferMapper {

	void deleteETLDataTransfers(ETLDataTransferQuery query);

	void deleteETLDataTransferById(String id);

	ETLDataTransfer getETLDataTransferById(String id);

	int getETLDataTransferCount(ETLDataTransferQuery query);

	List<ETLDataTransfer> getETLDataTransfers(ETLDataTransferQuery query);

	void insertETLDataTransfer(ETLDataTransfer model);

	void updateETLDataTransfer(ETLDataTransfer model);
	
	void updateColumnMapping(@Param("targetId")String targetId,@Param("id")String transId,@Param("columnMapping")byte[] columnMapping,@Param("user")String user,@Param("updateTime")Date updateTime);

}
