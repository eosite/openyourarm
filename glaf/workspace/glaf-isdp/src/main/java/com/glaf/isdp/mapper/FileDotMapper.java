package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.FileDot;
import com.glaf.isdp.query.FileDotQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.FileDotMapper")
public interface FileDotMapper {

	void deleteFileDots(FileDotQuery query);

	void deleteFileDotById(String id);

	FileDot getFileDotById(String id);

	int getFileDotCount(FileDotQuery query);

	List<FileDot> getFileDots(FileDotQuery query);

	void insertFileDot(FileDot model);

	void updateFileDot(FileDot model);

}
