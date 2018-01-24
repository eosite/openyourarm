package com.glaf.isdp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.util.TreepInfoCountJsonFactory;

@Entity
@Table(name = "TREEPINFO_COUNT")
public class TreepInfoCount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号，主键
	 */
	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 数据库编号
	 */
	@Column(name = "DATABASEID_", nullable = false)
	protected Long databaseId;

	/**
	 * 数据库映射名，标段简称
	 */
	@Column(name = "MAPPING_", length = 50)
	protected String mapping;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 100)
	protected String title;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 标段标识
	 */
	@Column(name = "SECTION_", length = 50)
	protected String section;

	/**
	 * treepinfo 的 index_id
	 */
	@Column(name = "INDEX_ID")
	protected Integer indexId;

	/**
	 * treepinfo 的 parent_id
	 */
	@Column(name = "PARENT_ID")
	protected Integer parentId;

	/**
	 * treepinfo 的 index_name
	 */
	@Column(name = "INDEX_NAME", length = 255)
	protected String indexName;

	/**
	 * treepinfo 的 wbsindex
	 */
	@Column(name = "WBSINDEX")
	protected Integer wbsIndex;

	@Column(name = "DOUBLEVALUE1_")
	protected double doubleValue1;

	@Column(name = "DOUBLEVALUE2_")
	protected double doubleValue2;

	@Column(name = "DOUBLEVALUE3_")
	protected double doubleValue3;

	@Column(name = "DOUBLEVALUE4_")
	protected double doubleValue4;

	@Column(name = "DOUBLEVALUE5_")
	protected double doubleValue5;

	@Column(name = "DOUBLEVALUE6_")
	protected double doubleValue6;

	@Column(name = "DOUBLEVALUE7_")
	protected double doubleValue7;

	@Column(name = "DOUBLEVALUE8_")
	protected double doubleValue8;

	@Column(name = "DOUBLEVALUE9_")
	protected double doubleValue9;

	@Column(name = "DOUBLEVALUE10_")
	protected double doubleValue10;

	@Column(name = "INTVALUE1_")
	protected int intValue1;

	@Column(name = "INTVALUE2_")
	protected int intValue2;

	@Column(name = "INTVALUE3_")
	protected int intValue3;

	@Column(name = "INTVALUE4_")
	protected int intValue4;

	@Column(name = "INTVALUE5_")
	protected int intValue5;

	@Column(name = "INTVALUE6_")
	protected int intValue6;

	@Column(name = "INTVALUE7_")
	protected int intValue7;

	@Column(name = "INTVALUE8_")
	protected int intValue8;

	@Column(name = "INTVALUE9_")
	protected int intValue9;

	@Column(name = "INTVALUE10_")
	protected int intValue10;

	@Column(name = "LONGVALUE1_")
	protected long longValue1;

	@Column(name = "LONGVALUE2_")
	protected long longValue2;

	@Column(name = "LONGVALUE3_")
	protected long longValue3;

	@Column(name = "LONGVALUE4_")
	protected long longValue4;

	@Column(name = "LONGVALUE5_")
	protected long longValue5;

	@Column(name = "LONGVALUE6_")
	protected long longValue6;

	@Column(name = "LONGVALUE7_")
	protected long longValue7;

	@Column(name = "LONGVALUE8_")
	protected long longValue8;

	@Column(name = "LONGVALUE9_")
	protected long longValue9;

	@Column(name = "LONGVALUE10_")
	protected long longValue10;

	@Column(name = "STRINGVALUE1_")
	protected String stringValue1;

	@Column(name = "STRINGVALUE2_")
	protected String stringValue2;

	@Column(name = "STRINGVALUE3_")
	protected String stringValue3;

	@Column(name = "STRINGVALUE4_")
	protected String stringValue4;

	@Column(name = "STRINGVALUE5_")
	protected String stringValue5;

	@Column(name = "STRINGVALUE6_")
	protected String stringValue6;

	@Column(name = "STRINGVALUE7_")
	protected String stringValue7;

	@Column(name = "STRINGVALUE8_")
	protected String stringValue8;

	@Column(name = "STRINGVALUE9_")
	protected String stringValue9;

	@Column(name = "STRINGVALUE10_")
	protected String stringValue10;

	@Column(name = "RUNYEAR_")
	protected Integer runYear;

	@Column(name = "RUNMONTH_")
	protected Integer runMonth;

	@Column(name = "RUNWEEK_")
	protected Integer runWeek;

	@Column(name = "RUNQUARTER_")
	protected Integer runQuarter;

	@Column(name = "RUNDAY_")
	protected Integer runDay;

	@Column(name = "JOBNO_", length = 50)
	protected String jobNo;

	public TreepInfoCount() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreepInfoCount other = (TreepInfoCount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public double getDoubleValue1() {
		return doubleValue1;
	}

	public double getDoubleValue10() {
		return doubleValue10;
	}

	public double getDoubleValue2() {
		return doubleValue2;
	}

	public double getDoubleValue3() {
		return doubleValue3;
	}

	public double getDoubleValue4() {
		return doubleValue4;
	}

	public double getDoubleValue5() {
		return doubleValue5;
	}

	public double getDoubleValue6() {
		return doubleValue6;
	}

	public double getDoubleValue7() {
		return doubleValue7;
	}

	public double getDoubleValue8() {
		return doubleValue8;
	}

	public double getDoubleValue9() {
		return doubleValue9;
	}

	public Long getId() {
		return id;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public String getIndexName() {
		return indexName;
	}

	public int getIntValue1() {
		return intValue1;
	}

	public int getIntValue10() {
		return intValue10;
	}

	public int getIntValue2() {
		return intValue2;
	}

	public int getIntValue3() {
		return intValue3;
	}

	public int getIntValue4() {
		return intValue4;
	}

	public int getIntValue5() {
		return intValue5;
	}

	public int getIntValue6() {
		return intValue6;
	}

	public int getIntValue7() {
		return intValue7;
	}

	public int getIntValue8() {
		return intValue8;
	}

	public int getIntValue9() {
		return intValue9;
	}

	public String getJobNo() {
		return jobNo;
	}

	public long getLongValue1() {
		return longValue1;
	}

	public long getLongValue10() {
		return longValue10;
	}

	public long getLongValue2() {
		return longValue2;
	}

	public long getLongValue3() {
		return longValue3;
	}

	public long getLongValue4() {
		return longValue4;
	}

	public long getLongValue5() {
		return longValue5;
	}

	public long getLongValue6() {
		return longValue6;
	}

	public long getLongValue7() {
		return longValue7;
	}

	public long getLongValue8() {
		return longValue8;
	}

	public long getLongValue9() {
		return longValue9;
	}

	public String getMapping() {
		return mapping;
	}

	public Integer getParentId() {
		return parentId;
	}

	public Integer getRunDay() {
		return runDay;
	}

	public Integer getRunMonth() {
		return runMonth;
	}

	public Integer getRunQuarter() {
		return runQuarter;
	}

	public Integer getRunWeek() {
		return runWeek;
	}

	public Integer getRunYear() {
		return runYear;
	}

	public String getSection() {
		return section;
	}

	public String getStringValue1() {
		return stringValue1;
	}

	public String getStringValue10() {
		return stringValue10;
	}

	public String getStringValue2() {
		return stringValue2;
	}

	public String getStringValue3() {
		return stringValue3;
	}

	public String getStringValue4() {
		return stringValue4;
	}

	public String getStringValue5() {
		return stringValue5;
	}

	public String getStringValue6() {
		return stringValue6;
	}

	public String getStringValue7() {
		return stringValue7;
	}

	public String getStringValue8() {
		return stringValue8;
	}

	public String getStringValue9() {
		return stringValue9;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public Integer getWbsIndex() {
		return wbsIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public TreepInfoCount jsonToObject(JSONObject jsonObject) {
		return TreepInfoCountJsonFactory.jsonToObject(jsonObject);
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDoubleValue1(double doubleValue1) {
		this.doubleValue1 = doubleValue1;
	}

	public void setDoubleValue10(double doubleValue10) {
		this.doubleValue10 = doubleValue10;
	}

	public void setDoubleValue2(double doubleValue2) {
		this.doubleValue2 = doubleValue2;
	}

	public void setDoubleValue3(double doubleValue3) {
		this.doubleValue3 = doubleValue3;
	}

	public void setDoubleValue4(double doubleValue4) {
		this.doubleValue4 = doubleValue4;
	}

	public void setDoubleValue5(double doubleValue5) {
		this.doubleValue5 = doubleValue5;
	}

	public void setDoubleValue6(double doubleValue6) {
		this.doubleValue6 = doubleValue6;
	}

	public void setDoubleValue7(double doubleValue7) {
		this.doubleValue7 = doubleValue7;
	}

	public void setDoubleValue8(double doubleValue8) {
		this.doubleValue8 = doubleValue8;
	}

	public void setDoubleValue9(double doubleValue9) {
		this.doubleValue9 = doubleValue9;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public void setIntValue1(int intValue1) {
		this.intValue1 = intValue1;
	}

	public void setIntValue10(int intValue10) {
		this.intValue10 = intValue10;
	}

	public void setIntValue2(int intValue2) {
		this.intValue2 = intValue2;
	}

	public void setIntValue3(int intValue3) {
		this.intValue3 = intValue3;
	}

	public void setIntValue4(int intValue4) {
		this.intValue4 = intValue4;
	}

	public void setIntValue5(int intValue5) {
		this.intValue5 = intValue5;
	}

	public void setIntValue6(int intValue6) {
		this.intValue6 = intValue6;
	}

	public void setIntValue7(int intValue7) {
		this.intValue7 = intValue7;
	}

	public void setIntValue8(int intValue8) {
		this.intValue8 = intValue8;
	}

	public void setIntValue9(int intValue9) {
		this.intValue9 = intValue9;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public void setLongValue1(long longValue1) {
		this.longValue1 = longValue1;
	}

	public void setLongValue10(long longValue10) {
		this.longValue10 = longValue10;
	}

	public void setLongValue2(long longValue2) {
		this.longValue2 = longValue2;
	}

	public void setLongValue3(long longValue3) {
		this.longValue3 = longValue3;
	}

	public void setLongValue4(long longValue4) {
		this.longValue4 = longValue4;
	}

	public void setLongValue5(long longValue5) {
		this.longValue5 = longValue5;
	}

	public void setLongValue6(long longValue6) {
		this.longValue6 = longValue6;
	}

	public void setLongValue7(long longValue7) {
		this.longValue7 = longValue7;
	}

	public void setLongValue8(long longValue8) {
		this.longValue8 = longValue8;
	}

	public void setLongValue9(long longValue9) {
		this.longValue9 = longValue9;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setRunDay(Integer runDay) {
		this.runDay = runDay;
	}

	public void setRunMonth(Integer runMonth) {
		this.runMonth = runMonth;
	}

	public void setRunQuarter(Integer runQuarter) {
		this.runQuarter = runQuarter;
	}

	public void setRunWeek(Integer runWeek) {
		this.runWeek = runWeek;
	}

	public void setRunYear(Integer runYear) {
		this.runYear = runYear;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setStringValue1(String stringValue1) {
		this.stringValue1 = stringValue1;
	}

	public void setStringValue10(String stringValue10) {
		this.stringValue10 = stringValue10;
	}

	public void setStringValue2(String stringValue2) {
		this.stringValue2 = stringValue2;
	}

	public void setStringValue3(String stringValue3) {
		this.stringValue3 = stringValue3;
	}

	public void setStringValue4(String stringValue4) {
		this.stringValue4 = stringValue4;
	}

	public void setStringValue5(String stringValue5) {
		this.stringValue5 = stringValue5;
	}

	public void setStringValue6(String stringValue6) {
		this.stringValue6 = stringValue6;
	}

	public void setStringValue7(String stringValue7) {
		this.stringValue7 = stringValue7;
	}

	public void setStringValue8(String stringValue8) {
		this.stringValue8 = stringValue8;
	}

	public void setStringValue9(String stringValue9) {
		this.stringValue9 = stringValue9;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWbsIndex(Integer wbsIndex) {
		this.wbsIndex = wbsIndex;
	}

	public JSONObject toJsonObject() {
		return TreepInfoCountJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TreepInfoCountJsonFactory.toObjectNode(this);
	}

}
