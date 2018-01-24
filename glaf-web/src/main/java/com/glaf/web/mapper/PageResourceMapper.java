package com.glaf.web.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.web.domain.*;
import com.glaf.web.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.web.mapper.PageResourceMapper")
public interface PageResourceMapper {

	void deletePageResources(PageResourceQuery query);

	void deletePageResourceById(Long id);

	PageResource getPageResourceById(Long id);

	int getPageResourceCount(PageResourceQuery query);

	List<PageResource> getPageResources(PageResourceQuery query);

	void insertPageResource(PageResource model);

	void updatePageResource(PageResource model);
	
	PageResource getPageResourceByFilePath(String filePath);

}
