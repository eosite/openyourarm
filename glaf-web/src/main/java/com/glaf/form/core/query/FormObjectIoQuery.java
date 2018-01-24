package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormObjectIoQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
  	protected String name;
  	protected String nameLike;
  	protected List<String> names;
  	protected String code;
  	protected String codeLike;
  	protected List<String> codes;
  	protected String codeMapping;
  	protected String codeMappingLike;
  	protected List<String> codeMappings;
  	protected String paramType;
  	protected String paramTypeLike;
  	protected List<String> paramTypes;
  	protected String defValue;
  	protected String defValueLike;
  	protected List<String> defValues;
  	protected String type;
  	protected String typeLike;
  	protected List<String> types;
  	protected String parent_id;
  	protected String parent_idLike;
  	protected List<String> parent_ids;

    public FormObjectIoQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
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


    public String getCodeMapping(){
        return codeMapping;
    }

    public String getCodeMappingLike(){
	    if (codeMappingLike != null && codeMappingLike.trim().length() > 0) {
		if (!codeMappingLike.startsWith("%")) {
		    codeMappingLike = "%" + codeMappingLike;
		}
		if (!codeMappingLike.endsWith("%")) {
		   codeMappingLike = codeMappingLike + "%";
		}
	    }
	return codeMappingLike;
    }

    public List<String> getCodeMappings(){
	return codeMappings;
    }


    public String getParamType(){
        return paramType;
    }

    public String getParamTypeLike(){
	    if (paramTypeLike != null && paramTypeLike.trim().length() > 0) {
		if (!paramTypeLike.startsWith("%")) {
		    paramTypeLike = "%" + paramTypeLike;
		}
		if (!paramTypeLike.endsWith("%")) {
		   paramTypeLike = paramTypeLike + "%";
		}
	    }
	return paramTypeLike;
    }

    public List<String> getParamTypes(){
	return paramTypes;
    }


    public String getDefValue(){
        return defValue;
    }

    public String getDefValueLike(){
	    if (defValueLike != null && defValueLike.trim().length() > 0) {
		if (!defValueLike.startsWith("%")) {
		    defValueLike = "%" + defValueLike;
		}
		if (!defValueLike.endsWith("%")) {
		   defValueLike = defValueLike + "%";
		}
	    }
	return defValueLike;
    }

    public List<String> getDefValues(){
	return defValues;
    }


    public String getType(){
        return type;
    }

    public String getTypeLike(){
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

    public List<String> getTypes(){
	return types;
    }


    public String getParent_id(){
        return parent_id;
    }

    public String getParent_idLike(){
	    if (parent_idLike != null && parent_idLike.trim().length() > 0) {
		if (!parent_idLike.startsWith("%")) {
		    parent_idLike = "%" + parent_idLike;
		}
		if (!parent_idLike.endsWith("%")) {
		   parent_idLike = parent_idLike + "%";
		}
	    }
	return parent_idLike;
    }

    public List<String> getParent_ids(){
	return parent_ids;
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


    public void setCode(String code){
        this.code = code;
    }

    public void setCodeLike( String codeLike){
	this.codeLike = codeLike;
    }

    public void setCodes(List<String> codes){
        this.codes = codes;
    }


    public void setCodeMapping(String codeMapping){
        this.codeMapping = codeMapping;
    }

    public void setCodeMappingLike( String codeMappingLike){
	this.codeMappingLike = codeMappingLike;
    }

    public void setCodeMappings(List<String> codeMappings){
        this.codeMappings = codeMappings;
    }


    public void setParamType(String paramType){
        this.paramType = paramType;
    }

    public void setParamTypeLike( String paramTypeLike){
	this.paramTypeLike = paramTypeLike;
    }

    public void setParamTypes(List<String> paramTypes){
        this.paramTypes = paramTypes;
    }


    public void setDefValue(String defValue){
        this.defValue = defValue;
    }

    public void setDefValueLike( String defValueLike){
	this.defValueLike = defValueLike;
    }

    public void setDefValues(List<String> defValues){
        this.defValues = defValues;
    }


    public void setType(String type){
        this.type = type;
    }

    public void setTypeLike( String typeLike){
	this.typeLike = typeLike;
    }

    public void setTypes(List<String> types){
        this.types = types;
    }


    public void setParent_id(String parent_id){
        this.parent_id = parent_id;
    }

    public void setParent_idLike( String parent_idLike){
	this.parent_idLike = parent_idLike;
    }

    public void setParent_ids(List<String> parent_ids){
        this.parent_ids = parent_ids;
    }




    public FormObjectIoQuery name(String name){
	if (name == null) {
	    throw new RuntimeException("name is null");
        }         
	this.name = name;
	return this;
    }

    public FormObjectIoQuery nameLike( String nameLike){
        if (nameLike == null) {
            throw new RuntimeException("name is null");
        }
        this.nameLike = nameLike;
        return this;
    }

    public FormObjectIoQuery names(List<String> names){
        if (names == null) {
            throw new RuntimeException("names is empty ");
        }
        this.names = names;
        return this;
    }


    public FormObjectIoQuery code(String code){
	if (code == null) {
	    throw new RuntimeException("code is null");
        }         
	this.code = code;
	return this;
    }

    public FormObjectIoQuery codeLike( String codeLike){
        if (codeLike == null) {
            throw new RuntimeException("code is null");
        }
        this.codeLike = codeLike;
        return this;
    }

    public FormObjectIoQuery codes(List<String> codes){
        if (codes == null) {
            throw new RuntimeException("codes is empty ");
        }
        this.codes = codes;
        return this;
    }


    public FormObjectIoQuery codeMapping(String codeMapping){
	if (codeMapping == null) {
	    throw new RuntimeException("codeMapping is null");
        }         
	this.codeMapping = codeMapping;
	return this;
    }

    public FormObjectIoQuery codeMappingLike( String codeMappingLike){
        if (codeMappingLike == null) {
            throw new RuntimeException("codeMapping is null");
        }
        this.codeMappingLike = codeMappingLike;
        return this;
    }

    public FormObjectIoQuery codeMappings(List<String> codeMappings){
        if (codeMappings == null) {
            throw new RuntimeException("codeMappings is empty ");
        }
        this.codeMappings = codeMappings;
        return this;
    }


    public FormObjectIoQuery paramType(String paramType){
	if (paramType == null) {
	    throw new RuntimeException("paramType is null");
        }         
	this.paramType = paramType;
	return this;
    }

    public FormObjectIoQuery paramTypeLike( String paramTypeLike){
        if (paramTypeLike == null) {
            throw new RuntimeException("paramType is null");
        }
        this.paramTypeLike = paramTypeLike;
        return this;
    }

    public FormObjectIoQuery paramTypes(List<String> paramTypes){
        if (paramTypes == null) {
            throw new RuntimeException("paramTypes is empty ");
        }
        this.paramTypes = paramTypes;
        return this;
    }


    public FormObjectIoQuery defValue(String defValue){
	if (defValue == null) {
	    throw new RuntimeException("defValue is null");
        }         
	this.defValue = defValue;
	return this;
    }

    public FormObjectIoQuery defValueLike( String defValueLike){
        if (defValueLike == null) {
            throw new RuntimeException("defValue is null");
        }
        this.defValueLike = defValueLike;
        return this;
    }

    public FormObjectIoQuery defValues(List<String> defValues){
        if (defValues == null) {
            throw new RuntimeException("defValues is empty ");
        }
        this.defValues = defValues;
        return this;
    }


    public FormObjectIoQuery type(String type){
	if (type == null) {
	    throw new RuntimeException("type is null");
        }         
	this.type = type;
	return this;
    }

    public FormObjectIoQuery typeLike( String typeLike){
        if (typeLike == null) {
            throw new RuntimeException("type is null");
        }
        this.typeLike = typeLike;
        return this;
    }

    public FormObjectIoQuery types(List<String> types){
        if (types == null) {
            throw new RuntimeException("types is empty ");
        }
        this.types = types;
        return this;
    }


    public FormObjectIoQuery parent_id(String parent_id){
	if (parent_id == null) {
	    throw new RuntimeException("parent_id is null");
        }         
	this.parent_id = parent_id;
	return this;
    }

    public FormObjectIoQuery parent_idLike( String parent_idLike){
        if (parent_idLike == null) {
            throw new RuntimeException("parent_id is null");
        }
        this.parent_idLike = parent_idLike;
        return this;
    }

    public FormObjectIoQuery parent_ids(List<String> parent_ids){
        if (parent_ids == null) {
            throw new RuntimeException("parent_ids is empty ");
        }
        this.parent_ids = parent_ids;
        return this;
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

            if ("code".equals(sortColumn)) {
                orderBy = "E.CODE_" + a_x;
            } 

            if ("codeMapping".equals(sortColumn)) {
                orderBy = "E.CODE_MAPPING_" + a_x;
            } 

            if ("paramType".equals(sortColumn)) {
                orderBy = "E.PARAM_TYPE_" + a_x;
            } 

            if ("defValue".equals(sortColumn)) {
                orderBy = "E.DEFVALUE_" + a_x;
            } 

            if ("type".equals(sortColumn)) {
                orderBy = "E.TYPE_" + a_x;
            } 

            if ("parent_id".equals(sortColumn)) {
                orderBy = "E.PARENT_ID_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id", "ID_");
        addColumn("name", "NAME_");
        addColumn("code", "CODE_");
        addColumn("codeMapping", "CODE_MAPPING_");
        addColumn("paramType", "PARAM_TYPE_");
        addColumn("defValue", "DEFVALUE_");
        addColumn("type", "TYPE_");
        addColumn("parent_id", "PARENT_ID_");
    }

}