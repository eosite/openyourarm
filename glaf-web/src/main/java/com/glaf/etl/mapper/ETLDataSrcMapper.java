package com.glaf.etl.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.etl.mapper.ETLDataSrcMapper")
public interface ETLDataSrcMapper {

	void deleteETLDataSrcs(ETLDataSrcQuery query);

	void deleteETLDataSrcById(String id);

	ETLDataSrc getETLDataSrcById(String id);

	int getETLDataSrcCount(ETLDataSrcQuery query);

	List<ETLDataSrc> getETLDataSrcs(ETLDataSrcQuery query);

	void insertETLDataSrc(ETLDataSrc model);

	void updateETLDataSrc(ETLDataSrc model);

}
