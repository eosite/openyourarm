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

@Component("com.glaf.form.core.mapper.WdatasetSqlliteMapper")
public interface WdatasetSqlliteMapper {

	void deleteWdatasetSqllites(WdatasetSqlliteQuery query);

	void deleteWdatasetSqlliteById(String id);

	WdatasetSqllite getWdatasetSqlliteById(String id);

	int getWdatasetSqlliteCount(WdatasetSqlliteQuery query);

	List<WdatasetSqllite> getWdatasetSqllites(WdatasetSqlliteQuery query);
	
	List<WdatasetSqllite> getWdatasetSqllites2(WdatasetSqlliteQuery query);

	void insertWdatasetSqllite(WdatasetSqllite model);

	void updateWdatasetSqllite(WdatasetSqllite model);

}
