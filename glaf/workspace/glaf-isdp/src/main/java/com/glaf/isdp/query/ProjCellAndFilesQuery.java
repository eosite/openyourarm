package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ProjCellAndFilesQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected Integer intType;
	protected Integer intTypeGreaterThanOrEqual;
	protected Integer intTypeLessThanOrEqual;
	protected List<Integer> intTypes;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String defId;
	protected String defIdLike;
	protected List<String> defIds;
	protected String useId;
	protected String useIdLike;
	protected List<String> useIds;
	protected Integer intPage0;
	protected Integer intPage0GreaterThanOrEqual;
	protected Integer intPage0LessThanOrEqual;
	protected List<Integer> intPage0s;
	protected Integer intPage1;
	protected Integer intPage1GreaterThanOrEqual;
	protected Integer intPage1LessThanOrEqual;
	protected List<Integer> intPage1s;
	protected Integer intPage2;
	protected Integer intPage2GreaterThanOrEqual;
	protected Integer intPage2LessThanOrEqual;
	protected List<Integer> intPage2s;
	protected Integer intFinish;
	protected Integer intFinishGreaterThanOrEqual;
	protected Integer intFinishLessThanOrEqual;
	protected List<Integer> intFinishs;

	public ProjCellAndFilesQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public Integer getIndexIdGreaterThanOrEqual() {
		return indexIdGreaterThanOrEqual;
	}

	public Integer getIndexIdLessThanOrEqual() {
		return indexIdLessThanOrEqual;
	}

	public List<Integer> getIndexIds() {
		return indexIds;
	}

	public Integer getIntType() {
		return intType;
	}

	public Integer getIntTypeGreaterThanOrEqual() {
		return intTypeGreaterThanOrEqual;
	}

	public Integer getIntTypeLessThanOrEqual() {
		return intTypeLessThanOrEqual;
	}

	public List<Integer> getIntTypes() {
		return intTypes;
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

	public String getDefId() {
		return defId;
	}

	public String getDefIdLike() {
		if (defIdLike != null && defIdLike.trim().length() > 0) {
			if (!defIdLike.startsWith("%")) {
				defIdLike = "%" + defIdLike;
			}
			if (!defIdLike.endsWith("%")) {
				defIdLike = defIdLike + "%";
			}
		}
		return defIdLike;
	}

	public List<String> getDefIds() {
		return defIds;
	}

	public String getUseId() {
		return useId;
	}

	public String getUseIdLike() {
		if (useIdLike != null && useIdLike.trim().length() > 0) {
			if (!useIdLike.startsWith("%")) {
				useIdLike = "%" + useIdLike;
			}
			if (!useIdLike.endsWith("%")) {
				useIdLike = useIdLike + "%";
			}
		}
		return useIdLike;
	}

	public List<String> getUseIds() {
		return useIds;
	}

	public Integer getIntPage0() {
		return intPage0;
	}

	public Integer getIntPage0GreaterThanOrEqual() {
		return intPage0GreaterThanOrEqual;
	}

	public Integer getIntPage0LessThanOrEqual() {
		return intPage0LessThanOrEqual;
	}

	public List<Integer> getIntPage0s() {
		return intPage0s;
	}

	public Integer getIntPage1() {
		return intPage1;
	}

	public Integer getIntPage1GreaterThanOrEqual() {
		return intPage1GreaterThanOrEqual;
	}

	public Integer getIntPage1LessThanOrEqual() {
		return intPage1LessThanOrEqual;
	}

	public List<Integer> getIntPage1s() {
		return intPage1s;
	}

	public Integer getIntPage2() {
		return intPage2;
	}

	public Integer getIntPage2GreaterThanOrEqual() {
		return intPage2GreaterThanOrEqual;
	}

	public Integer getIntPage2LessThanOrEqual() {
		return intPage2LessThanOrEqual;
	}

	public List<Integer> getIntPage2s() {
		return intPage2s;
	}

	public Integer getIntFinish() {
		return intFinish;
	}

	public Integer getIntFinishGreaterThanOrEqual() {
		return intFinishGreaterThanOrEqual;
	}

	public Integer getIntFinishLessThanOrEqual() {
		return intFinishLessThanOrEqual;
	}

	public List<Integer> getIntFinishs() {
		return intFinishs;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIndexIdGreaterThanOrEqual(Integer indexIdGreaterThanOrEqual) {
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
	}

	public void setIndexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
	}

	public void setIndexIds(List<Integer> indexIds) {
		this.indexIds = indexIds;
	}

	public void setIntType(Integer intType) {
		this.intType = intType;
	}

	public void setIntTypeGreaterThanOrEqual(Integer intTypeGreaterThanOrEqual) {
		this.intTypeGreaterThanOrEqual = intTypeGreaterThanOrEqual;
	}

	public void setIntTypeLessThanOrEqual(Integer intTypeLessThanOrEqual) {
		this.intTypeLessThanOrEqual = intTypeLessThanOrEqual;
	}

	public void setIntTypes(List<Integer> intTypes) {
		this.intTypes = intTypes;
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

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public void setDefIdLike(String defIdLike) {
		this.defIdLike = defIdLike;
	}

	public void setDefIds(List<String> defIds) {
		this.defIds = defIds;
	}

	public void setUseId(String useId) {
		this.useId = useId;
	}

	public void setUseIdLike(String useIdLike) {
		this.useIdLike = useIdLike;
	}

	public void setUseIds(List<String> useIds) {
		this.useIds = useIds;
	}

	public void setIntPage0(Integer intPage0) {
		this.intPage0 = intPage0;
	}

	public void setIntPage0GreaterThanOrEqual(Integer intPage0GreaterThanOrEqual) {
		this.intPage0GreaterThanOrEqual = intPage0GreaterThanOrEqual;
	}

	public void setIntPage0LessThanOrEqual(Integer intPage0LessThanOrEqual) {
		this.intPage0LessThanOrEqual = intPage0LessThanOrEqual;
	}

	public void setIntPage0s(List<Integer> intPage0s) {
		this.intPage0s = intPage0s;
	}

	public void setIntPage1(Integer intPage1) {
		this.intPage1 = intPage1;
	}

	public void setIntPage1GreaterThanOrEqual(Integer intPage1GreaterThanOrEqual) {
		this.intPage1GreaterThanOrEqual = intPage1GreaterThanOrEqual;
	}

	public void setIntPage1LessThanOrEqual(Integer intPage1LessThanOrEqual) {
		this.intPage1LessThanOrEqual = intPage1LessThanOrEqual;
	}

	public void setIntPage1s(List<Integer> intPage1s) {
		this.intPage1s = intPage1s;
	}

	public void setIntPage2(Integer intPage2) {
		this.intPage2 = intPage2;
	}

	public void setIntPage2GreaterThanOrEqual(Integer intPage2GreaterThanOrEqual) {
		this.intPage2GreaterThanOrEqual = intPage2GreaterThanOrEqual;
	}

	public void setIntPage2LessThanOrEqual(Integer intPage2LessThanOrEqual) {
		this.intPage2LessThanOrEqual = intPage2LessThanOrEqual;
	}

	public void setIntPage2s(List<Integer> intPage2s) {
		this.intPage2s = intPage2s;
	}

	public void setIntFinish(Integer intFinish) {
		this.intFinish = intFinish;
	}

	public void setIntFinishGreaterThanOrEqual(
			Integer intFinishGreaterThanOrEqual) {
		this.intFinishGreaterThanOrEqual = intFinishGreaterThanOrEqual;
	}

	public void setIntFinishLessThanOrEqual(Integer intFinishLessThanOrEqual) {
		this.intFinishLessThanOrEqual = intFinishLessThanOrEqual;
	}

	public void setIntFinishs(List<Integer> intFinishs) {
		this.intFinishs = intFinishs;
	}

	public ProjCellAndFilesQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public ProjCellAndFilesQuery indexIdGreaterThanOrEqual(
			Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery indexIdLessThanOrEqual(
			Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public ProjCellAndFilesQuery intType(Integer intType) {
		if (intType == null) {
			throw new RuntimeException("intType is null");
		}
		this.intType = intType;
		return this;
	}

	public ProjCellAndFilesQuery intTypeGreaterThanOrEqual(
			Integer intTypeGreaterThanOrEqual) {
		if (intTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("intType is null");
		}
		this.intTypeGreaterThanOrEqual = intTypeGreaterThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intTypeLessThanOrEqual(
			Integer intTypeLessThanOrEqual) {
		if (intTypeLessThanOrEqual == null) {
			throw new RuntimeException("intType is null");
		}
		this.intTypeLessThanOrEqual = intTypeLessThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intTypes(List<Integer> intTypes) {
		if (intTypes == null) {
			throw new RuntimeException("intTypes is empty ");
		}
		this.intTypes = intTypes;
		return this;
	}

	public ProjCellAndFilesQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public ProjCellAndFilesQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public ProjCellAndFilesQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public ProjCellAndFilesQuery defId(String defId) {
		if (defId == null) {
			throw new RuntimeException("defId is null");
		}
		this.defId = defId;
		return this;
	}

	public ProjCellAndFilesQuery defIdLike(String defIdLike) {
		if (defIdLike == null) {
			throw new RuntimeException("defId is null");
		}
		this.defIdLike = defIdLike;
		return this;
	}

	public ProjCellAndFilesQuery defIds(List<String> defIds) {
		if (defIds == null) {
			throw new RuntimeException("defIds is empty ");
		}
		this.defIds = defIds;
		return this;
	}

	public ProjCellAndFilesQuery useId(String useId) {
		if (useId == null) {
			throw new RuntimeException("useId is null");
		}
		this.useId = useId;
		return this;
	}

	public ProjCellAndFilesQuery useIdLike(String useIdLike) {
		if (useIdLike == null) {
			throw new RuntimeException("useId is null");
		}
		this.useIdLike = useIdLike;
		return this;
	}

	public ProjCellAndFilesQuery useIds(List<String> useIds) {
		if (useIds == null) {
			throw new RuntimeException("useIds is empty ");
		}
		this.useIds = useIds;
		return this;
	}

	public ProjCellAndFilesQuery intPage0(Integer intPage0) {
		if (intPage0 == null) {
			throw new RuntimeException("intPage0 is null");
		}
		this.intPage0 = intPage0;
		return this;
	}

	public ProjCellAndFilesQuery intPage0GreaterThanOrEqual(
			Integer intPage0GreaterThanOrEqual) {
		if (intPage0GreaterThanOrEqual == null) {
			throw new RuntimeException("intPage0 is null");
		}
		this.intPage0GreaterThanOrEqual = intPage0GreaterThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intPage0LessThanOrEqual(
			Integer intPage0LessThanOrEqual) {
		if (intPage0LessThanOrEqual == null) {
			throw new RuntimeException("intPage0 is null");
		}
		this.intPage0LessThanOrEqual = intPage0LessThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intPage0s(List<Integer> intPage0s) {
		if (intPage0s == null) {
			throw new RuntimeException("intPage0s is empty ");
		}
		this.intPage0s = intPage0s;
		return this;
	}

	public ProjCellAndFilesQuery intPage1(Integer intPage1) {
		if (intPage1 == null) {
			throw new RuntimeException("intPage1 is null");
		}
		this.intPage1 = intPage1;
		return this;
	}

	public ProjCellAndFilesQuery intPage1GreaterThanOrEqual(
			Integer intPage1GreaterThanOrEqual) {
		if (intPage1GreaterThanOrEqual == null) {
			throw new RuntimeException("intPage1 is null");
		}
		this.intPage1GreaterThanOrEqual = intPage1GreaterThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intPage1LessThanOrEqual(
			Integer intPage1LessThanOrEqual) {
		if (intPage1LessThanOrEqual == null) {
			throw new RuntimeException("intPage1 is null");
		}
		this.intPage1LessThanOrEqual = intPage1LessThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intPage1s(List<Integer> intPage1s) {
		if (intPage1s == null) {
			throw new RuntimeException("intPage1s is empty ");
		}
		this.intPage1s = intPage1s;
		return this;
	}

	public ProjCellAndFilesQuery intPage2(Integer intPage2) {
		if (intPage2 == null) {
			throw new RuntimeException("intPage2 is null");
		}
		this.intPage2 = intPage2;
		return this;
	}

	public ProjCellAndFilesQuery intPage2GreaterThanOrEqual(
			Integer intPage2GreaterThanOrEqual) {
		if (intPage2GreaterThanOrEqual == null) {
			throw new RuntimeException("intPage2 is null");
		}
		this.intPage2GreaterThanOrEqual = intPage2GreaterThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intPage2LessThanOrEqual(
			Integer intPage2LessThanOrEqual) {
		if (intPage2LessThanOrEqual == null) {
			throw new RuntimeException("intPage2 is null");
		}
		this.intPage2LessThanOrEqual = intPage2LessThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intPage2s(List<Integer> intPage2s) {
		if (intPage2s == null) {
			throw new RuntimeException("intPage2s is empty ");
		}
		this.intPage2s = intPage2s;
		return this;
	}

	public ProjCellAndFilesQuery intFinish(Integer intFinish) {
		if (intFinish == null) {
			throw new RuntimeException("intFinish is null");
		}
		this.intFinish = intFinish;
		return this;
	}

	public ProjCellAndFilesQuery intFinishGreaterThanOrEqual(
			Integer intFinishGreaterThanOrEqual) {
		if (intFinishGreaterThanOrEqual == null) {
			throw new RuntimeException("intFinish is null");
		}
		this.intFinishGreaterThanOrEqual = intFinishGreaterThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intFinishLessThanOrEqual(
			Integer intFinishLessThanOrEqual) {
		if (intFinishLessThanOrEqual == null) {
			throw new RuntimeException("intFinish is null");
		}
		this.intFinishLessThanOrEqual = intFinishLessThanOrEqual;
		return this;
	}

	public ProjCellAndFilesQuery intFinishs(List<Integer> intFinishs) {
		if (intFinishs == null) {
			throw new RuntimeException("intFinishs is empty ");
		}
		this.intFinishs = intFinishs;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEX_ID" + a_x;
			}

			if ("intType".equals(sortColumn)) {
				orderBy = "E.INTTYPE" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME" + a_x;
			}

			if ("defId".equals(sortColumn)) {
				orderBy = "E.DEFID" + a_x;
			}

			if ("useId".equals(sortColumn)) {
				orderBy = "E.USEID" + a_x;
			}

			if ("intPage0".equals(sortColumn)) {
				orderBy = "E.INTPAGE0" + a_x;
			}

			if ("intPage1".equals(sortColumn)) {
				orderBy = "E.INTPAGE1" + a_x;
			}

			if ("intPage2".equals(sortColumn)) {
				orderBy = "E.INTPAGE2" + a_x;
			}

			if ("intFinish".equals(sortColumn)) {
				orderBy = "E.INTFINISH" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("indexId", "INDEX_ID");
		addColumn("intType", "INTTYPE");
		addColumn("name", "NAME");
		addColumn("defId", "DEFID");
		addColumn("useId", "USEID");
		addColumn("intPage0", "INTPAGE0");
		addColumn("intPage1", "INTPAGE1");
		addColumn("intPage2", "INTPAGE2");
		addColumn("intFinish", "INTFINISH");
	}

}