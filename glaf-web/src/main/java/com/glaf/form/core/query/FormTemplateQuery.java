package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormTemplateQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Integer> ids;
	protected Collection<String> appActorIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected Integer componentId;
	protected List<Integer> componentIds;
	protected String createByLike;
	protected List<String> createBys;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String type;
	protected String typeLike;
	protected List<String> types;
	protected String template;
	protected String templateLike;
	protected List<String> templates;
	protected String image;
	protected String imageLike;
	protected List<String> images;
	protected Integer deleteFlagGreaterThanOrEqual;
	protected Integer deleteFlagLessThanOrEqual;
	protected List<Integer> deleteFlags;

	public FormTemplateQuery() {

	}

	public FormTemplateQuery componentId(Integer componentId) {
		if (componentId == null) {
			throw new RuntimeException("componentId is null");
		}
		this.componentId = componentId;
		return this;
	}

	public FormTemplateQuery componentIds(List<Integer> componentIds) {
		if (componentIds == null) {
			throw new RuntimeException("componentIds is empty ");
		}
		this.componentIds = componentIds;
		return this;
	}

	public FormTemplateQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public FormTemplateQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FormTemplateQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormTemplateQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormTemplateQuery deleteFlagGreaterThanOrEqual(
			Integer deleteFlagGreaterThanOrEqual) {
		if (deleteFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
		return this;
	}

	public FormTemplateQuery deleteFlagLessThanOrEqual(
			Integer deleteFlagLessThanOrEqual) {
		if (deleteFlagLessThanOrEqual == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
		return this;
	}

	public FormTemplateQuery deleteFlags(List<Integer> deleteFlags) {
		if (deleteFlags == null) {
			throw new RuntimeException("deleteFlags is empty ");
		}
		this.deleteFlags = deleteFlags;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public Integer getComponentId() {
		return componentId;
	}

	public List<Integer> getComponentIds() {
		return componentIds;
	}

	public String getCreateByLike() {
		if (createByLike != null && createByLike.trim().length() > 0) {
			if (!createByLike.startsWith("%")) {
				createByLike = "%" + createByLike;
			}
			if (!createByLike.endsWith("%")) {
				createByLike = createByLike + "%";
			}
		}
		return createByLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public Integer getDeleteFlagGreaterThanOrEqual() {
		return deleteFlagGreaterThanOrEqual;
	}

	public Integer getDeleteFlagLessThanOrEqual() {
		return deleteFlagLessThanOrEqual;
	}

	public List<Integer> getDeleteFlags() {
		return deleteFlags;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("componentId".equals(sortColumn)) {
				orderBy = "E.COMPONENTID_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATA_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("template".equals(sortColumn)) {
				orderBy = "E.TEMPLATE_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
			}

		}
		return orderBy;
	}

	public String getTemplate() {
		return template;
	}

	public String getTemplateLike() {
		if (templateLike != null && templateLike.trim().length() > 0) {
			if (!templateLike.startsWith("%")) {
				templateLike = "%" + templateLike;
			}
			if (!templateLike.endsWith("%")) {
				templateLike = templateLike + "%";
			}
		}
		return templateLike;
	}

	public List<String> getTemplates() {
		return templates;
	}

	public String getImage() {
		return image;
	}
	
	public String getImageLike() {
		if (imageLike != null && imageLike.trim().length() > 0) {
			if (!imageLike.startsWith("%")) {
				imageLike = "%" + imageLike;
			}
			if (!imageLike.endsWith("%")) {
				imageLike = imageLike + "%";
			}
		}
		return imageLike;
	}
	
	public List<String> getImages() {
		return images;
	}

	public String getType() {
		return type;
	}

	public String getTypeLike() {
		if (typeLike != null && typeLike.trim().length() > 0) {
			if (!typeLike.startsWith("%")) {
				typeLike = "%" + typeLike;
			}
			if (!typeLike.endsWith("%")) {
				typeLike = typeLike + "%";
			}
		}
		return typeLike;
	}

	public List<String> getTypes() {
		return types;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("componentId", "COMPONENTID_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATA_");
		addColumn("type", "TYPE_");
		addColumn("template", "TEMPLATE_");
		addColumn("deleteFlag", "DELETEFLAG_");
	}

	public FormTemplateQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormTemplateQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FormTemplateQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}

	public void setComponentIds(List<Integer> componentIds) {
		this.componentIds = componentIds;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDeleteFlagGreaterThanOrEqual(
			Integer deleteFlagGreaterThanOrEqual) {
		this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
	}

	public void setDeleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual) {
		this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
	}

	public void setDeleteFlags(List<Integer> deleteFlags) {
		this.deleteFlags = deleteFlags;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setTemplateLike(String templateLike) {
		this.templateLike = templateLike;
	}

	public void setTemplates(List<String> templates) {
		this.templates = templates;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public void setImageLike(String imageLike) {
		this.imageLike = imageLike;
	}
	
	public void setImages(List<String> images) {
		this.images = images;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeLike(String typeLike) {
		this.typeLike = typeLike;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public FormTemplateQuery template(String template) {
		if (template == null) {
			throw new RuntimeException("template is null");
		}
		this.template = template;
		return this;
	}

	public FormTemplateQuery templateLike(String templateLike) {
		if (templateLike == null) {
			throw new RuntimeException("template is null");
		}
		this.templateLike = templateLike;
		return this;
	}

	public FormTemplateQuery templates(List<String> templates) {
		if (templates == null) {
			throw new RuntimeException("templates is empty ");
		}
		this.templates = templates;
		return this;
	}
	
	public FormTemplateQuery image(String image) {
		if (image == null) {
			throw new RuntimeException("image is null");
		}
		this.image = image;
		return this;
	}
	
	public FormTemplateQuery imageLike(String imageLike) {
		if (imageLike == null) {
			throw new RuntimeException("imageLike is null");
		}
		this.imageLike = imageLike;
		return this;
	}
	
	public FormTemplateQuery images(List<String> images) {
		if (images == null) {
			throw new RuntimeException("images is empty ");
		}
		this.images = images;
		return this;
	}

	public FormTemplateQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public FormTemplateQuery typeLike(String typeLike) {
		if (typeLike == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLike = typeLike;
		return this;
	}

	public FormTemplateQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

}