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

@Component("com.glaf.form.core.mapper.FormOutexpRelationMapper")
public interface FormOutexpRelationMapper {

	void deleteFormOutexpRelations(FormOutexpRelationQuery query);

	void deleteFormOutexpRelationById(String id);

	FormOutexpRelation getFormOutexpRelationById(String id);

	int getFormOutexpRelationCount(FormOutexpRelationQuery query);

	List<FormOutexpRelation> getFormOutexpRelations(FormOutexpRelationQuery query);

	void insertFormOutexpRelation(FormOutexpRelation model);

	void updateFormOutexpRelation(FormOutexpRelation model);

	void delete(FormOutexpRelationQuery delete2Query);

}
