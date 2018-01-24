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

@Component("com.glaf.form.core.mapper.FormVideoMapper")
public interface FormVideoMapper {

	void deleteFormVideos(FormVideoQuery query);

	void deleteFormVideoById(Long id);

	FormVideo getFormVideoById(Long id);

	int getFormVideoCount(FormVideoQuery query);

	List<FormVideo> getFormVideos(FormVideoQuery query);

	void insertFormVideo(FormVideo model);

	void updateFormVideo(FormVideo model);

}
