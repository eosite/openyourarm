package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.Pfile;
import com.glaf.isdp.query.PfileQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.PfileMapper")
public interface PfileMapper {

	void deletePfiles(PfileQuery query);

	void deletePfileById(String id);

	Pfile getPfileById(String id);

	int getPfileCount(PfileQuery query);

	List<Pfile> getPfiles(PfileQuery query);

	void insertPfile(Pfile model);

	void updatePfile(Pfile model);

	List<Pfile> getPfileDataSum(List<String> idLikeList, String sDate,
			String eDate);

}
