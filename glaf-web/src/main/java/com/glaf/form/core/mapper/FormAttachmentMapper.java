package com.glaf.form.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.query.FormAttachmentQuery;

@Component
public interface FormAttachmentMapper {

	void insertFormAttachment(FormAttachment model);

	void updateFormAttachment(FormAttachment model);

	void deleteFormAttachmentById(String id);
	
	void deleteFormAttachmentByIds(@Param("rowIds")List<String> rowIds);

	void deleteFormAttachmentByParent(String parent);
	
	FormAttachment getFormAttachmentById(String id);
	
	FormAttachment getFormAttachmentNotContentById(String id); 

	List<FormAttachment> getFormAttachments(FormAttachmentQuery query);

	List<FormAttachment> getFormAttachmentsNotContent(FormAttachmentQuery query);
	
	int getFormAttachmentCount(FormAttachmentQuery query);
}
