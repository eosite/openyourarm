package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface PageUrlConversionMapper {

	void bulkInsertPageUrlConversion(List<PageUrlConversion> list);

	void bulkInsertPageUrlConversion_oracle(List<PageUrlConversion> list);

	void deletePageUrlConversions(PageUrlConversionQuery query);

	void deletePageUrlConversionById(String id);

	PageUrlConversion getPageUrlConversionById(String id);

	int getPageUrlConversionCount(PageUrlConversionQuery query);

	List<PageUrlConversion> getPageUrlConversions(PageUrlConversionQuery query);

	void insertPageUrlConversion(PageUrlConversion model);

	void updatePageUrlConversion(PageUrlConversion model);

}
