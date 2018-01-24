package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.Pinfo;
import com.glaf.isdp.query.PinfoQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.PinfoMapper")
public interface PinfoMapper {

	void deletePinfos(PinfoQuery query);

	void deletePinfoById(String id);

	Pinfo getPinfoById(String id);

	int getPinfoCount(PinfoQuery query);

	List<Pinfo> getPinfos(PinfoQuery query);

	void insertPinfo(Pinfo model);

	void updatePinfo(Pinfo model);

	Pinfo getPinfoListByDomainIndexId(Integer domainIndexId);
}
