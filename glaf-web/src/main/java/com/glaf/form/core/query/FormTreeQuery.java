package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormTreeQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String createBy;
  	protected String createByLike;
  	protected List<String> createBys;
        protected Date createDateGreaterThanOrEqual;
  	protected Date createDateLessThanOrEqual;
  	protected String nodeDesc;
  	protected String nodeDescLike;
  	protected List<String> nodeDescs;
  	protected String discriminator;
  	protected String discriminatorLike;
  	protected List<String> discriminators;
  	protected String icon;
  	protected String iconLike;
  	protected List<String> icons;
  	protected String iconCls;
  	protected String iconClsLike;
  	protected List<String> iconClss;
  	protected Integer locked;
  	protected Integer lockedGreaterThanOrEqual;
  	protected Integer lockedLessThanOrEqual;
  	protected List<Integer> lockeds;
  	protected String moveable;
  	protected String moveableLike;
  	protected List<String> moveables;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected Long parent;
  	protected Long parentGreaterThanOrEqual;
  	protected Long parentLessThanOrEqual;
  	protected List<Long> parents;
  	protected Integer sort;
  	protected Integer sortGreaterThanOrEqual;
  	protected Integer sortLessThanOrEqual;
  	protected List<Integer> sorts;
  	protected String treeId;
  	protected String treeIdLike;
  	protected List<String> treeIds;
  	protected String updateBy;
  	protected String updateByLike;
  	protected List<String> updateBys;
        protected Date updateDateGreaterThanOrEqual;
  	protected Date updateDateLessThanOrEqual;
  	protected String url;
  	protected String urlLike;
  	protected List<String> urls;
  	protected String category;
  	protected String categoryLike;
  	protected List<String> categorys;

    public FormTreeQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getCode(){
        return code;
    }

    public String getCodeLike(){
	    if (codeLike != null && codeLike.trim().length() > 0) {
		if (!codeLike.startsWith("%")) {
		    codeLike = "%" + codeLike;
		}
		if (!codeLike.endsWith("%")) {
		   codeLike = codeLike + "%";
		}
	    }
	return codeLike;
    }

    public List<String> getCodes(){
	return codes;
    }


    public String getCreateBy(){
        return createBy;
    }

    public String getCreateByLike(){
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

    public List<String> getCreateBys(){
	return createBys;
    }


    public Date getCreateDateGreaterThanOrEqual(){
        return createDateGreaterThanOrEqual;
    }

    public Date getCreateDateLessThanOrEqual(){
	return createDateLessThanOrEqual;
    }


    public String getNodeDesc(){
        return nodeDesc;
    }

    public String getNodeDescLike(){
	    if (nodeDescLike != null && nodeDescLike.trim().length() > 0) {
		if (!nodeDescLike.startsWith("%")) {
		    nodeDescLike = "%" + nodeDescLike;
		}
		if (!nodeDescLike.endsWith("%")) {
		   nodeDescLike = nodeDescLike + "%";
		}
	    }
	return nodeDescLike;
    }

    public List<String> getNodeDescs(){
	return nodeDescs;
    }


    public String getDiscriminator(){
        return discriminator;
    }

    public String getDiscriminatorLike(){
	    if (discriminatorLike != null && discriminatorLike.trim().length() > 0) {
		if (!discriminatorLike.startsWith("%")) {
		    discriminatorLike = "%" + discriminatorLike;
		}
		if (!discriminatorLike.endsWith("%")) {
		   discriminatorLike = discriminatorLike + "%";
		}
	    }
	return discriminatorLike;
    }

    public List<String> getDiscriminators(){
	return discriminators;
    }


    public String getIcon(){
        return icon;
    }

    public String getIconLike(){
	    if (iconLike != null && iconLike.trim().length() > 0) {
		if (!iconLike.startsWith("%")) {
		    iconLike = "%" + iconLike;
		}
		if (!iconLike.endsWith("%")) {
		   iconLike = iconLike + "%";
		}
	    }
	return iconLike;
    }

    public List<String> getIcons(){
	return icons;
    }


    public String getIconCls(){
        return iconCls;
    }

    public String getIconClsLike(){
	    if (iconClsLike != null && iconClsLike.trim().length() > 0) {
		if (!iconClsLike.startsWith("%")) {
		    iconClsLike = "%" + iconClsLike;
		}
		if (!iconClsLike.endsWith("%")) {
		   iconClsLike = iconClsLike + "%";
		}
	    }
	return iconClsLike;
    }

    public List<String> getIconClss(){
	return iconClss;
    }


    public Integer getLocked(){
        return locked;
    }

    public Integer getLockedGreaterThanOrEqual(){
        return lockedGreaterThanOrEqual;
    }

    public Integer getLockedLessThanOrEqual(){
	return lockedLessThanOrEqual;
    }

    public List<Integer> getLockeds(){
	return lockeds;
    }

    public String getMoveable(){
        return moveable;
    }

    public String getMoveableLike(){
	    if (moveableLike != null && moveableLike.trim().length() > 0) {
		if (!moveableLike.startsWith("%")) {
		    moveableLike = "%" + moveableLike;
		}
		if (!moveableLike.endsWith("%")) {
		   moveableLike = moveableLike + "%";
		}
	    }
	return moveableLike;
    }

    public List<String> getMoveables(){
	return moveables;
    }


    public String getName(){
        return name;
    }

    public String getNameLike(){
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

    public List<String> getNames(){
	return names;
    }


    public Long getParent(){
        return parent;
    }

    public Long getParentGreaterThanOrEqual(){
        return parentGreaterThanOrEqual;
    }

    public Long getParentLessThanOrEqual(){
	return parentLessThanOrEqual;
    }

    public List<Long> getParents(){
	return parents;
    }

    public Integer getSort(){
        return sort;
    }

    public Integer getSortGreaterThanOrEqual(){
        return sortGreaterThanOrEqual;
    }

    public Integer getSortLessThanOrEqual(){
	return sortLessThanOrEqual;
    }

    public List<Integer> getSorts(){
	return sorts;
    }

    public String getTreeId(){
        return treeId;
    }

    public String getTreeIdLike(){
	    if (treeIdLike != null && treeIdLike.trim().length() > 0) {
		if (!treeIdLike.startsWith("%")) {
		  //  treeIdLike = "%" + treeIdLike;
		}
		if (!treeIdLike.endsWith("%")) {
		   treeIdLike = treeIdLike + "%";
		}
	    }
	return treeIdLike;
    }

    public List<String> getTreeIds(){
	return treeIds;
    }


    public String getUpdateBy(){
        return updateBy;
    }

    public String getUpdateByLike(){
	    if (updateByLike != null && updateByLike.trim().length() > 0) {
		if (!updateByLike.startsWith("%")) {
		    updateByLike = "%" + updateByLike;
		}
		if (!updateByLike.endsWith("%")) {
		   updateByLike = updateByLike + "%";
		}
	    }
	return updateByLike;
    }

    public List<String> getUpdateBys(){
	return updateBys;
    }


    public Date getUpdateDateGreaterThanOrEqual(){
        return updateDateGreaterThanOrEqual;
    }

    public Date getUpdateDateLessThanOrEqual(){
	return updateDateLessThanOrEqual;
    }


    public String getUrl(){
        return url;
    }

    public String getUrlLike(){
	    if (urlLike != null && urlLike.trim().length() > 0) {
		if (!urlLike.startsWith("%")) {
		    urlLike = "%" + urlLike;
		}
		if (!urlLike.endsWith("%")) {
		   urlLike = urlLike + "%";
		}
	    }
	return urlLike;
    }

    public List<String> getUrls(){
	return urls;
    }


    public String getCategory(){
        return category;
    }

    public String getCategoryLike(){
	    if (categoryLike != null && categoryLike.trim().length() > 0) {
		if (!categoryLike.startsWith("%")) {
		    categoryLike = "%" + categoryLike;
		}
		if (!categoryLike.endsWith("%")) {
		   categoryLike = categoryLike + "%";
		}
	    }
	return categoryLike;
    }

    public List<String> getCategorys(){
	return categorys;
    }


 

    public void setCode(String code){
        this.code = code;
    }

    public void setCodeLike( String codeLike){
	this.codeLike = codeLike;
    }

    public void setCodes(List<String> codes){
        this.codes = codes;
    }


    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }

    public void setCreateByLike( String createByLike){
	this.createByLike = createByLike;
    }

    public void setCreateBys(List<String> createBys){
        this.createBys = createBys;
    }


    public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
        this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
    }

    public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual){
	this.createDateLessThanOrEqual = createDateLessThanOrEqual;
    }


    public void setNodeDesc(String nodeDesc){
        this.nodeDesc = nodeDesc;
    }

    public void setNodeDescLike( String nodeDescLike){
	this.nodeDescLike = nodeDescLike;
    }

    public void setNodeDescs(List<String> nodeDescs){
        this.nodeDescs = nodeDescs;
    }


    public void setDiscriminator(String discriminator){
        this.discriminator = discriminator;
    }

    public void setDiscriminatorLike( String discriminatorLike){
	this.discriminatorLike = discriminatorLike;
    }

    public void setDiscriminators(List<String> discriminators){
        this.discriminators = discriminators;
    }


    public void setIcon(String icon){
        this.icon = icon;
    }

    public void setIconLike( String iconLike){
	this.iconLike = iconLike;
    }

    public void setIcons(List<String> icons){
        this.icons = icons;
    }


    public void setIconCls(String iconCls){
        this.iconCls = iconCls;
    }

    public void setIconClsLike( String iconClsLike){
	this.iconClsLike = iconClsLike;
    }

    public void setIconClss(List<String> iconClss){
        this.iconClss = iconClss;
    }


    public void setLocked(Integer locked){
        this.locked = locked;
    }

    public void setLockedGreaterThanOrEqual(Integer lockedGreaterThanOrEqual){
        this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
    }

    public void setLockedLessThanOrEqual(Integer lockedLessThanOrEqual){
	this.lockedLessThanOrEqual = lockedLessThanOrEqual;
    }

    public void setLockeds(List<Integer> lockeds){
        this.lockeds = lockeds;
    }


    public void setMoveable(String moveable){
        this.moveable = moveable;
    }

    public void setMoveableLike( String moveableLike){
	this.moveableLike = moveableLike;
    }

    public void setMoveables(List<String> moveables){
        this.moveables = moveables;
    }


    public void setName(String name){
        this.name = name;
    }

    public void setNameLike( String nameLike){
	this.nameLike = nameLike;
    }

    public void setNames(List<String> names){
        this.names = names;
    }


    public void setParent(Long parent){
        this.parent = parent;
    }

    public void setParentGreaterThanOrEqual(Long parentGreaterThanOrEqual){
        this.parentGreaterThanOrEqual = parentGreaterThanOrEqual;
    }

    public void setParentLessThanOrEqual(Long parentLessThanOrEqual){
	this.parentLessThanOrEqual = parentLessThanOrEqual;
    }

    public void setParents(List<Long> parents){
        this.parents = parents;
    }


    public void setSort(Integer sort){
        this.sort = sort;
    }

    public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual){
        this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
    }

    public void setSortLessThanOrEqual(Integer sortLessThanOrEqual){
	this.sortLessThanOrEqual = sortLessThanOrEqual;
    }

    public void setSorts(List<Integer> sorts){
        this.sorts = sorts;
    }


    public void setTreeId(String treeId){
        this.treeId = treeId;
    }

    public void setTreeIdLike( String treeIdLike){
	this.treeIdLike = treeIdLike;
    }

    public void setTreeIds(List<String> treeIds){
        this.treeIds = treeIds;
    }


    public void setUpdateBy(String updateBy){
        this.updateBy = updateBy;
    }

    public void setUpdateByLike( String updateByLike){
	this.updateByLike = updateByLike;
    }

    public void setUpdateBys(List<String> updateBys){
        this.updateBys = updateBys;
    }


    public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
        this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
    }

    public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
	this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
    }


    public void setUrl(String url){
        this.url = url;
    }

    public void setUrlLike( String urlLike){
	this.urlLike = urlLike;
    }

    public void setUrls(List<String> urls){
        this.urls = urls;
    }


    public void setCategory(String category){
        this.category = category;
    }

    public void setCategoryLike( String categoryLike){
	this.categoryLike = categoryLike;
    }

    public void setCategorys(List<String> categorys){
        this.categorys = categorys;
    }




    public FormTreeQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public FormTreeQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public FormTreeQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public FormTreeQuery createBy(String createBy){
	if (createBy == null) {
	    throw new RuntimeException("createBy is null");
        }         
	this.createBy = createBy;
	return this;
    }

    public FormTreeQuery createByLike( String createByLike){
        if (createByLike == null) {
            throw new RuntimeException("createBy is null");
        }
        this.createByLike = createByLike;
        return this;
    }

    public FormTreeQuery createBys(List<String> createBys){
        if (createBys == null) {
            throw new RuntimeException("createBys is empty ");
        }
        this.createBys = createBys;
        return this;
    }



    public FormTreeQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual){
	if (createDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("createDate is null");
        }         
	this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
        return this;
    }

    public FormTreeQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual){
        if (createDateLessThanOrEqual == null) {
            throw new RuntimeException("createDate is null");
        }
        this.createDateLessThanOrEqual = createDateLessThanOrEqual;
        return this;
    }



    public FormTreeQuery nodeDesc(String nodeDesc){
	if (nodeDesc == null) {
	    throw new RuntimeException("nodeDesc is null");
        }         
	this.nodeDesc = nodeDesc;
	return this;
    }

    public FormTreeQuery nodeDescLike( String nodeDescLike){
        if (nodeDescLike == null) {
            throw new RuntimeException("nodeDesc is null");
        }
        this.nodeDescLike = nodeDescLike;
        return this;
    }

    public FormTreeQuery nodeDescs(List<String> nodeDescs){
        if (nodeDescs == null) {
            throw new RuntimeException("nodeDescs is empty ");
        }
        this.nodeDescs = nodeDescs;
        return this;
    }


    public FormTreeQuery discriminator(String discriminator){
	if (discriminator == null) {
	    throw new RuntimeException("discriminator is null");
        }         
	this.discriminator = discriminator;
	return this;
    }

    public FormTreeQuery discriminatorLike( String discriminatorLike){
        if (discriminatorLike == null) {
            throw new RuntimeException("discriminator is null");
        }
        this.discriminatorLike = discriminatorLike;
        return this;
    }

    public FormTreeQuery discriminators(List<String> discriminators){
        if (discriminators == null) {
            throw new RuntimeException("discriminators is empty ");
        }
        this.discriminators = discriminators;
        return this;
    }


    public FormTreeQuery icon(String icon){
	if (icon == null) {
	    throw new RuntimeException("icon is null");
        }         
	this.icon = icon;
	return this;
    }

    public FormTreeQuery iconLike( String iconLike){
        if (iconLike == null) {
            throw new RuntimeException("icon is null");
        }
        this.iconLike = iconLike;
        return this;
    }

    public FormTreeQuery icons(List<String> icons){
        if (icons == null) {
            throw new RuntimeException("icons is empty ");
        }
        this.icons = icons;
        return this;
    }


    public FormTreeQuery iconCls(String iconCls){
	if (iconCls == null) {
	    throw new RuntimeException("iconCls is null");
        }         
	this.iconCls = iconCls;
	return this;
    }

    public FormTreeQuery iconClsLike( String iconClsLike){
        if (iconClsLike == null) {
            throw new RuntimeException("iconCls is null");
        }
        this.iconClsLike = iconClsLike;
        return this;
    }

    public FormTreeQuery iconClss(List<String> iconClss){
        if (iconClss == null) {
            throw new RuntimeException("iconClss is empty ");
        }
        this.iconClss = iconClss;
        return this;
    }


    public FormTreeQuery locked(Integer locked){
	if (locked == null) {
            throw new RuntimeException("locked is null");
        }         
	this.locked = locked;
	return this;
    }

    public FormTreeQuery lockedGreaterThanOrEqual(Integer lockedGreaterThanOrEqual){
	if (lockedGreaterThanOrEqual == null) {
	    throw new RuntimeException("locked is null");
        }         
	this.lockedGreaterThanOrEqual = lockedGreaterThanOrEqual;
        return this;
    }

    public FormTreeQuery lockedLessThanOrEqual(Integer lockedLessThanOrEqual){
        if (lockedLessThanOrEqual == null) {
            throw new RuntimeException("locked is null");
        }
        this.lockedLessThanOrEqual = lockedLessThanOrEqual;
        return this;
    }

    public FormTreeQuery lockeds(List<Integer> lockeds){
        if (lockeds == null) {
            throw new RuntimeException("lockeds is empty ");
        }
        this.lockeds = lockeds;
        return this;
    }


    public FormTreeQuery moveable(String moveable){
	if (moveable == null) {
	    throw new RuntimeException("moveable is null");
        }         
	this.moveable = moveable;
	return this;
    }

    public FormTreeQuery moveableLike( String moveableLike){
        if (moveableLike == null) {
            throw new RuntimeException("moveable is null");
        }
        this.moveableLike = moveableLike;
        return this;
    }

    public FormTreeQuery moveables(List<String> moveables){
        if (moveables == null) {
            throw new RuntimeException("moveables is empty ");
        }
        this.moveables = moveables;
        return this;
    }


    public FormTreeQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public FormTreeQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public FormTreeQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public FormTreeQuery parent(Long parent){
	if (parent == null) {
            throw new RuntimeException("parent is null");
        }         
	this.parent = parent;
	return this;
    }

    public FormTreeQuery parentGreaterThanOrEqual(Long parentGreaterThanOrEqual){
	if (parentGreaterThanOrEqual == null) {
	    throw new RuntimeException("parent is null");
        }         
	this.parentGreaterThanOrEqual = parentGreaterThanOrEqual;
        return this;
    }

    public FormTreeQuery parentLessThanOrEqual(Long parentLessThanOrEqual){
        if (parentLessThanOrEqual == null) {
            throw new RuntimeException("parent is null");
        }
        this.parentLessThanOrEqual = parentLessThanOrEqual;
        return this;
    }

    public FormTreeQuery parents(List<Long> parents){
        if (parents == null) {
            throw new RuntimeException("parents is empty ");
        }
        this.parents = parents;
        return this;
    }


    public FormTreeQuery sort(Integer sort){
	if (sort == null) {
            throw new RuntimeException("sort is null");
        }         
	this.sort = sort;
	return this;
    }

    public FormTreeQuery sortGreaterThanOrEqual(Integer sortGreaterThanOrEqual){
	if (sortGreaterThanOrEqual == null) {
	    throw new RuntimeException("sort is null");
        }         
	this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
        return this;
    }

    public FormTreeQuery sortLessThanOrEqual(Integer sortLessThanOrEqual){
        if (sortLessThanOrEqual == null) {
            throw new RuntimeException("sort is null");
        }
        this.sortLessThanOrEqual = sortLessThanOrEqual;
        return this;
    }

    public FormTreeQuery sorts(List<Integer> sorts){
        if (sorts == null) {
            throw new RuntimeException("sorts is empty ");
        }
        this.sorts = sorts;
        return this;
    }


    public FormTreeQuery treeId(String treeId){
	if (treeId == null) {
	    throw new RuntimeException("treeId is null");
        }         
	this.treeId = treeId;
	return this;
    }

    public FormTreeQuery treeIdLike( String treeIdLike){
        if (treeIdLike == null) {
            throw new RuntimeException("treeId is null");
        }
        this.treeIdLike = treeIdLike;
        return this;
    }

    public FormTreeQuery treeIds(List<String> treeIds){
        if (treeIds == null) {
            throw new RuntimeException("treeIds is empty ");
        }
        this.treeIds = treeIds;
        return this;
    }


    public FormTreeQuery updateBy(String updateBy){
	if (updateBy == null) {
	    throw new RuntimeException("updateBy is null");
        }         
	this.updateBy = updateBy;
	return this;
    }

    public FormTreeQuery updateByLike( String updateByLike){
        if (updateByLike == null) {
            throw new RuntimeException("updateBy is null");
        }
        this.updateByLike = updateByLike;
        return this;
    }

    public FormTreeQuery updateBys(List<String> updateBys){
        if (updateBys == null) {
            throw new RuntimeException("updateBys is empty ");
        }
        this.updateBys = updateBys;
        return this;
    }



    public FormTreeQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual){
	if (updateDateGreaterThanOrEqual == null) {
	    throw new RuntimeException("updateDate is null");
        }         
	this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
        return this;
    }

    public FormTreeQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual){
        if (updateDateLessThanOrEqual == null) {
            throw new RuntimeException("updateDate is null");
        }
        this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
        return this;
    }



    public FormTreeQuery url(String url){
	if (url == null) {
	    throw new RuntimeException("url is null");
        }         
	this.url = url;
	return this;
    }

    public FormTreeQuery urlLike( String urlLike){
        if (urlLike == null) {
            throw new RuntimeException("url is null");
        }
        this.urlLike = urlLike;
        return this;
    }

    public FormTreeQuery urls(List<String> urls){
        if (urls == null) {
            throw new RuntimeException("urls is empty ");
        }
        this.urls = urls;
        return this;
    }


    public FormTreeQuery category(String category){
	if (category == null) {
	    throw new RuntimeException("category is null");
        }         
	this.category = category;
	return this;
    }

    public FormTreeQuery categoryLike( String categoryLike){
        if (categoryLike == null) {
            throw new RuntimeException("category is null");
        }
        this.categoryLike = categoryLike;
        return this;
    }

    public FormTreeQuery categorys(List<String> categorys){
        if (categorys == null) {
            throw new RuntimeException("categorys is empty ");
        }
        this.categorys = categorys;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE" + a_x;
            } 

            if ("createBy".equals(sortColumn)) {
                orderBy = "E.CREATEBY" + a_x;
            } 

            if ("createDate".equals(sortColumn)) {
                orderBy = "E.CREATEDATE" + a_x;
            } 

            if ("nodeDesc".equals(sortColumn)) {
                orderBy = "E.NODEDESC" + a_x;
            } 

            if ("discriminator".equals(sortColumn)) {
                orderBy = "E.DISCRIMINATOR" + a_x;
            } 

            if ("icon".equals(sortColumn)) {
                orderBy = "E.ICON" + a_x;
            } 

            if ("iconCls".equals(sortColumn)) {
                orderBy = "E.ICONCLS" + a_x;
            } 

            if ("locked".equals(sortColumn)) {
                orderBy = "E.LOCKED" + a_x;
            } 

            if ("moveable".equals(sortColumn)) {
                orderBy = "E.MOVEABLE" + a_x;
            } 

            if ("name".equals(sortColumn)) {
                orderBy = "E.NAME" + a_x;
            } 

            if ("parent".equals(sortColumn)) {
                orderBy = "E.PARENT" + a_x;
            } 

            if ("sort".equals(sortColumn)) {
                orderBy = "E.SORT" + a_x;
            } 

            if ("treeId".equals(sortColumn)) {
                orderBy = "E.TREEID" + a_x;
            } 

            if ("updateBy".equals(sortColumn)) {
                orderBy = "E.UPDATEBY" + a_x;
            } 

            if ("updateDate".equals(sortColumn)) {
                orderBy = "E.UPDATEDATE" + a_x;
            } 

            if ("url".equals(sortColumn)) {
                orderBy = "E.URL" + a_x;
            } 

            if ("category".equals(sortColumn)) {
                orderBy = "E.CATEGORY" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID");
        addColumn("code", "CODE");
        addColumn("createBy", "CREATEBY");
        addColumn("createDate", "CREATEDATE");
        addColumn("nodeDesc", "NODEDESC");
        addColumn("discriminator", "DISCRIMINATOR");
        addColumn("icon", "ICON");
        addColumn("iconCls", "ICONCLS");
        addColumn("locked", "LOCKED");
        addColumn("moveable", "MOVEABLE");
        addColumn("name", "NAME");
        addColumn("parent", "PARENT");
        addColumn("sort", "SORT");
        addColumn("treeId", "TREEID");
        addColumn("updateBy", "UPDATEBY");
        addColumn("updateDate", "UPDATEDATE");
        addColumn("url", "URL");
        addColumn("category", "CATEGORY");
    }

}