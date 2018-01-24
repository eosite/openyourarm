package com.glaf.textsearch.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.textsearch.domain.*;
import com.glaf.textsearch.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.textsearch.mapper.SysFullTextSearchSrcMapper")
public interface SysFullTextSearchSrcMapper {

	void deleteSysFullTextSearchSrcs(SysFullTextSearchSrcQuery query);

	void deleteSysFullTextSearchSrcById(String id);

	SysFullTextSearchSrc getSysFullTextSearchSrcById(String id);

	int getSysFullTextSearchSrcCount(SysFullTextSearchSrcQuery query);

	List<SysFullTextSearchSrc> getSysFullTextSearchSrcs(SysFullTextSearchSrcQuery query);

	void insertSysFullTextSearchSrc(SysFullTextSearchSrc model);

	void updateSysFullTextSearchSrc(SysFullTextSearchSrc model);

}
