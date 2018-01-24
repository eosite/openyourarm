package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_SQL_RESULT")
public class SqlResult implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "DATABASEID_")
	protected Long databaseId;

	@Column(name = "SQLDEFID_")
	protected Long sqlDefId;

	@Column(name = "PROJECTID_")
	protected Long projectId;

	@Column(name = "COUNT_")
	protected Integer count;

	@Column(name = "VALUE_")
	protected Double value;

	@Column(name = "EXT1_", length = 200)
	protected String ext1;

	@Column(name = "EXT2_", length = 200)
	protected String ext2;

	@Column(name = "EXT3_", length = 200)
	protected String ext3;

	@Column(name = "EXT4_", length = 200)
	protected String ext4;

	@Column(name = "EXT5_", length = 200)
	protected String ext5;

	@Column(name = "EXT6_", length = 200)
	protected String ext6;

	@Column(name = "EXT7_", length = 200)
	protected String ext7;

	@Column(name = "EXT8_", length = 200)
	protected String ext8;

	@Column(name = "EXT9_", length = 200)
	protected String ext9;

	@Column(name = "EXT10_", length = 200)
	protected String ext10;

	@Column(name = "EXT101_")
	protected Double ext101;

	@Column(name = "EXT102_")
	protected Double ext102;

	@Column(name = "EXT103_")
	protected Double ext103;

	@Column(name = "EXT104_")
	protected Double ext104;

	@Column(name = "EXT105_")
	protected Double ext105;

	@Column(name = "EXT106_")
	protected Double ext106;

	@Column(name = "EXT107_")
	protected Double ext107;

	@Column(name = "EXT108_")
	protected Double ext108;

	@Column(name = "EXT109_")
	protected Double ext109;

	@Column(name = "EXT110_")
	protected Double ext110;

	@Column(name = "EXT111_")
	protected Double ext111;

	@Column(name = "EXT112_")
	protected Double ext112;

	@Column(name = "EXT113_")
	protected Double ext113;

	@Column(name = "EXT114_")
	protected Double ext114;

	@Column(name = "EXT115_")
	protected Double ext115;

	@Column(name = "EXT116_")
	protected Double ext116;

	@Column(name = "EXT117_")
	protected Double ext117;

	@Column(name = "EXT118_")
	protected Double ext118;

	@Column(name = "EXT119_")
	protected Double ext119;

	@Column(name = "EXT120_")
	protected Double ext120;

	@Column(name = "EXT201_")
	protected Long ext201;

	@Column(name = "EXT202_")
	protected Long ext202;

	@Column(name = "EXT203_")
	protected Long ext203;

	@Column(name = "EXT204_")
	protected Long ext204;

	@Column(name = "EXT205_")
	protected Long ext205;

	@Column(name = "EXT206_")
	protected Long ext206;

	@Column(name = "EXT207_")
	protected Long ext207;

	@Column(name = "EXT208_")
	protected Long ext208;

	@Column(name = "EXT209_")
	protected Long ext209;

	@Column(name = "EXT210_")
	protected Long ext210;

	@Column(name = "EXT301_")
	protected Integer ext301;

	@Column(name = "EXT302_")
	protected Integer ext302;

	@Column(name = "EXT303_")
	protected Integer ext303;

	@Column(name = "EXT304_")
	protected Integer ext304;

	@Column(name = "EXT305_")
	protected Integer ext305;

	@Column(name = "EXT306_")
	protected Integer ext306;

	@Column(name = "EXT307_")
	protected Integer ext307;

	@Column(name = "EXT308_")
	protected Integer ext308;

	@Column(name = "EXT309_")
	protected Integer ext309;

	@Column(name = "EXT310_")
	protected Integer ext310;

	@Column(name = "TYPE_", length = 50)
	protected String type;

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

	@Column(name = "OPERATION_", length = 50)
	protected String operation;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@javax.persistence.Transient
	protected String suffix;

	public SqlResult() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SqlResult other = (SqlResult) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getCount() {
		return this.count;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public Long getDatabaseId() {
		return this.databaseId;
	}

	public String getExt1() {
		return this.ext1;
	}

	public String getExt10() {
		return this.ext10;
	}

	public Double getExt101() {
		return this.ext101;
	}

	public Double getExt102() {
		return this.ext102;
	}

	public Double getExt103() {
		return this.ext103;
	}

	public Double getExt104() {
		return this.ext104;
	}

	public Double getExt105() {
		return this.ext105;
	}

	public Double getExt106() {
		return this.ext106;
	}

	public Double getExt107() {
		return this.ext107;
	}

	public Double getExt108() {
		return this.ext108;
	}

	public Double getExt109() {
		return this.ext109;
	}

	public Double getExt110() {
		return this.ext110;
	}

	public Double getExt111() {
		return this.ext111;
	}

	public Double getExt112() {
		return this.ext112;
	}

	public Double getExt113() {
		return this.ext113;
	}

	public Double getExt114() {
		return this.ext114;
	}

	public Double getExt115() {
		return this.ext115;
	}

	public Double getExt116() {
		return this.ext116;
	}

	public Double getExt117() {
		return this.ext117;
	}

	public Double getExt118() {
		return this.ext118;
	}

	public Double getExt119() {
		return this.ext119;
	}

	public Double getExt120() {
		return this.ext120;
	}

	public String getExt2() {
		return ext2;
	}

	public Long getExt201() {
		return ext201;
	}

	public Long getExt202() {
		return this.ext202;
	}

	public Long getExt203() {
		return this.ext203;
	}

	public Long getExt204() {
		return this.ext204;
	}

	public Long getExt205() {
		return this.ext205;
	}

	public Long getExt206() {
		return this.ext206;
	}

	public Long getExt207() {
		return this.ext207;
	}

	public Long getExt208() {
		return this.ext208;
	}

	public Long getExt209() {
		return this.ext209;
	}

	public Long getExt210() {
		return this.ext210;
	}

	public String getExt3() {
		return this.ext3;
	}

	public Integer getExt301() {
		return this.ext301;
	}

	public Integer getExt302() {
		return this.ext302;
	}

	public Integer getExt303() {
		return this.ext303;
	}

	public Integer getExt304() {
		return this.ext304;
	}

	public Integer getExt305() {
		return this.ext305;
	}

	public Integer getExt306() {
		return this.ext306;
	}

	public Integer getExt307() {
		return this.ext307;
	}

	public Integer getExt308() {
		return this.ext308;
	}

	public Integer getExt309() {
		return this.ext309;
	}

	public Integer getExt310() {
		return this.ext310;
	}

	public String getExt4() {
		return this.ext4;
	}

	public String getExt5() {
		return this.ext5;
	}

	public String getExt6() {
		return this.ext6;
	}

	public String getExt7() {
		return this.ext7;
	}

	public String getExt8() {
		return this.ext8;
	}

	public String getExt9() {
		return this.ext9;
	}

	public Long getId() {
		return this.id;
	}

	public String getJobNo() {
		return this.jobNo;
	}

	public String getOperation() {
		return operation;
	}

	public Long getProjectId() {
		return this.projectId;
	}

	public Integer getRunDay() {
		return this.runDay;
	}

	public Integer getRunMonth() {
		return this.runMonth;
	}

	public Integer getRunQuarter() {
		return this.runQuarter;
	}

	public Integer getRunWeek() {
		return this.runWeek;
	}

	public Integer getRunYear() {
		return this.runYear;
	}

	public Long getSqlDefId() {
		return this.sqlDefId;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getType() {
		return this.type;
	}

	public Double getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SqlResult jsonToObject(JSONObject jsonObject) {
		return SqlResultJsonFactory.jsonToObject(jsonObject);
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt10(String ext10) {
		this.ext10 = ext10;
	}

	public void setExt101(Double ext101) {
		this.ext101 = ext101;
	}

	public void setExt102(Double ext102) {
		this.ext102 = ext102;
	}

	public void setExt103(Double ext103) {
		this.ext103 = ext103;
	}

	public void setExt104(Double ext104) {
		this.ext104 = ext104;
	}

	public void setExt105(Double ext105) {
		this.ext105 = ext105;
	}

	public void setExt106(Double ext106) {
		this.ext106 = ext106;
	}

	public void setExt107(Double ext107) {
		this.ext107 = ext107;
	}

	public void setExt108(Double ext108) {
		this.ext108 = ext108;
	}

	public void setExt109(Double ext109) {
		this.ext109 = ext109;
	}

	public void setExt110(Double ext110) {
		this.ext110 = ext110;
	}

	public void setExt111(Double ext111) {
		this.ext111 = ext111;
	}

	public void setExt112(Double ext112) {
		this.ext112 = ext112;
	}

	public void setExt113(Double ext113) {
		this.ext113 = ext113;
	}

	public void setExt114(Double ext114) {
		this.ext114 = ext114;
	}

	public void setExt115(Double ext115) {
		this.ext115 = ext115;
	}

	public void setExt116(Double ext116) {
		this.ext116 = ext116;
	}

	public void setExt117(Double ext117) {
		this.ext117 = ext117;
	}

	public void setExt118(Double ext118) {
		this.ext118 = ext118;
	}

	public void setExt119(Double ext119) {
		this.ext119 = ext119;
	}

	public void setExt120(Double ext120) {
		this.ext120 = ext120;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setExt201(Long ext201) {
		this.ext201 = ext201;
	}

	public void setExt202(Long ext202) {
		this.ext202 = ext202;
	}

	public void setExt203(Long ext203) {
		this.ext203 = ext203;
	}

	public void setExt204(Long ext204) {
		this.ext204 = ext204;
	}

	public void setExt205(Long ext205) {
		this.ext205 = ext205;
	}

	public void setExt206(Long ext206) {
		this.ext206 = ext206;
	}

	public void setExt207(Long ext207) {
		this.ext207 = ext207;
	}

	public void setExt208(Long ext208) {
		this.ext208 = ext208;
	}

	public void setExt209(Long ext209) {
		this.ext209 = ext209;
	}

	public void setExt210(Long ext210) {
		this.ext210 = ext210;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public void setExt301(Integer ext301) {
		this.ext301 = ext301;
	}

	public void setExt302(Integer ext302) {
		this.ext302 = ext302;
	}

	public void setExt303(Integer ext303) {
		this.ext303 = ext303;
	}

	public void setExt304(Integer ext304) {
		this.ext304 = ext304;
	}

	public void setExt305(Integer ext305) {
		this.ext305 = ext305;
	}

	public void setExt306(Integer ext306) {
		this.ext306 = ext306;
	}

	public void setExt307(Integer ext307) {
		this.ext307 = ext307;
	}

	public void setExt308(Integer ext308) {
		this.ext308 = ext308;
	}

	public void setExt309(Integer ext309) {
		this.ext309 = ext309;
	}

	public void setExt310(Integer ext310) {
		this.ext310 = ext310;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}

	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}

	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}

	public void setExt9(String ext9) {
		this.ext9 = ext9;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public JSONObject toJsonObject() {
		return SqlResultJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SqlResultJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
