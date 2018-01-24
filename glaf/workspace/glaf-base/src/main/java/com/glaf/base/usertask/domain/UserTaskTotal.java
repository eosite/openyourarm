package com.glaf.base.usertask.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.usertask.util.*;

/**
 * 
 * 用户任务汇总
 *
 */

@Entity
@Table(name = "USER_TASK_TOTAL")
public class UserTaskTotal implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 用户编号
	 */
	@Column(name = "USERID_", length = 50)
	protected String userId;

	/**
	 * 用户项目
	 */
	@Column(name = "NAME_", length = 200)
	protected String name;

	/**
	 * 数据库编号
	 */
	@Column(name = "DATABASEID_")
	protected long databaseId;

	/**
	 * 总数
	 */
	@Column(name = "TOTAL_")
	protected int total;

	/**
	 * 完成数
	 */
	@Column(name = "FINISHED_")
	protected int finished;

	/**
	 * 待办数
	 */
	@Column(name = "PENDING_")
	protected int pending;

	/**
	 * 数量1
	 */
	@Column(name = "QUANTITY1_")
	protected int quantity1;

	/**
	 * 数量2
	 */
	@Column(name = "QUANTITY2_")
	protected int quantity2;

	/**
	 * 数量3
	 */
	@Column(name = "QUANTITY3_")
	protected int quantity3;

	/**
	 * 数量4
	 */
	@Column(name = "QUANTITY4_")
	protected int quantity4;

	/**
	 * 数量5
	 */
	@Column(name = "QUANTITY5_")
	protected int quantity5;

	/**
	 * 数量6
	 */
	@Column(name = "QUANTITY6_")
	protected int quantity6;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public UserTaskTotal() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTaskTotal other = (UserTaskTotal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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

	public long getDatabaseId() {
		return this.databaseId;
	}

	public int getFinished() {
		return this.finished;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getPending() {
		return this.pending;
	}

	public int getQuantity1() {
		return quantity1;
	}

	public int getQuantity2() {
		return quantity2;
	}

	public int getQuantity3() {
		return quantity3;
	}

	public int getQuantity4() {
		return quantity4;
	}

	public int getQuantity5() {
		return quantity5;
	}

	public int getQuantity6() {
		return quantity6;
	}

	public int getTotal() {
		return this.total;
	}

	public String getType() {
		return this.type;
	}

	public String getUserId() {
		return this.userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public UserTaskTotal jsonToObject(JSONObject jsonObject) {
		return UserTaskTotalJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseId(long databaseId) {
		this.databaseId = databaseId;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public void setQuantity1(int quantity1) {
		this.quantity1 = quantity1;
	}

	public void setQuantity2(int quantity2) {
		this.quantity2 = quantity2;
	}

	public void setQuantity3(int quantity3) {
		this.quantity3 = quantity3;
	}

	public void setQuantity4(int quantity4) {
		this.quantity4 = quantity4;
	}

	public void setQuantity5(int quantity5) {
		this.quantity5 = quantity5;
	}

	public void setQuantity6(int quantity6) {
		this.quantity6 = quantity6;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public JSONObject toJsonObject() {
		return UserTaskTotalJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return UserTaskTotalJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
