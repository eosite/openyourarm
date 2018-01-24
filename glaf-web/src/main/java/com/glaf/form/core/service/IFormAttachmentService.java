package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.query.FormAttachmentQuery;

public interface IFormAttachmentService {

	@Transactional
	FormAttachment save(FormAttachment model);

	@Transactional
	void update(FormAttachment model);

	@Transactional
	void deleteById(String id);

	@Transactional
	void deleteByIds(List<String> ids);

	@Transactional
	void deleteByParent(String parent);

	FormAttachment getCachedFormAttachmentNotContentById(String id);

	FormAttachment getFormAttachmentById(String id);

	List<FormAttachment> getFormAttachmentByParent(String parent);

	List<FormAttachment> getFormAttachmentByParentNotContent(String parent);

	int getFormAttachmentCountByQueryCriteria(FormAttachmentQuery query);

	List<FormAttachment> list(FormAttachmentQuery query);

	List<FormAttachment> listNotContent(FormAttachmentQuery query);

	List<FormAttachment> getFormAttachmentByQueryCriteria(int start, int pageSize, FormAttachmentQuery query);
}
