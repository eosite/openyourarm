package com.glaf.base.modules.image.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.base.modules.image.domain.*;
import com.glaf.base.modules.image.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface ImageMapper {

	void deleteImages(ImageQuery query);

	void deleteImageById(Long id);

	Image getImageById(Long id);

	int getImageCount(ImageQuery query);

	List<Image> getImages(ImageQuery query);

	void insertImage(Image model);

	void updateImage(Image model);

}
