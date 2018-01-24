package com.glaf.isdp.query;

import com.glaf.core.query.DataQuery;

public class HintListQuery extends DataQuery {
	private static final long serialVersionUID = 1L;

	protected String id;
	protected String idLike;
	protected String hintId;
	protected String list;
	protected String content;
	protected Integer listNo;
	protected Integer hintData;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdLike() {
		if (idLike != null && idLike.trim().length() > 0) {
			if (!idLike.startsWith("%")) {
				idLike = "%" + idLike;
			}
			if (!idLike.endsWith("%")) {
				idLike = idLike + "%";
			}
		}
		return idLike;
	}

	public void setIdLike(String idLike) {
		this.idLike = idLike;
	}

	public String getHintId() {
		return hintId;
	}

	public void setHintId(String hintId) {
		this.hintId = hintId;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public Integer getHintData() {
		return hintData;
	}

	public void setHintData(Integer hintData) {
		this.hintData = hintData;
	}

}
