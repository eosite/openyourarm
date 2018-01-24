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

@Component("com.glaf.form.core.mapper.FormTreeMapper")
public interface FormTreeMapper {

	void deleteFormTrees(FormTreeQuery query);

	void deleteFormTreeById(Long id);

	FormTree getFormTreeById(Long id);

	int getFormTreeCount(FormTreeQuery query);

	List<FormTree> getFormTrees(FormTreeQuery query);

	void insertFormTree(FormTree model);

	void updateFormTree(FormTree model);

}
