package com.glaf.dep.report.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.report.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DEP_REPORT_CELL_POS")
public class DepReportCellPos implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * 元素唯一标识
         */
        @Column(name = "CELL_ID_")	 
        protected Long cellId;

        /**
         * 开始行号
         */
        @Column(name = "ROW_INDEX_")
        protected Integer rowIndex;

        /**
         * 开始列号
         */
        @Column(name = "COL_INDEX_")
        protected Integer colIndex;

        /**
         * 变长标识
         */
        @Column(name = "VARFALG_", length=1) 
        protected String varFalg;

        /**
         * 变长方位
         */
        @Column(name = "DIRECTION_", length=1) 
        protected String direction;

        /**
         * 结束行号
         */
        @Column(name = "END_ROW_INDEX_")
        protected Integer endRowIndex;

        /**
         * 结束列号
         */
        @Column(name = "END_COL_INDEX_")
        protected Integer endColIndex;


         
	public DepReportCellPos() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public Long getCellId(){
	    return this.cellId;
	}
        public Integer getRowIndex(){
	    return this.rowIndex;
	}
        public Integer getColIndex(){
	    return this.colIndex;
	}
        public String getVarFalg(){
	    return this.varFalg;
	}
        public String getDirection(){
	    return this.direction;
	}
        public Integer getEndRowIndex(){
	    return this.endRowIndex;
	}
        public Integer getEndColIndex(){
	    return this.endColIndex;
	}


        public void setCellId(Long cellId) {
	    this.cellId = cellId; 
	}
        public void setRowIndex(Integer rowIndex) {
	    this.rowIndex = rowIndex; 
	}
        public void setColIndex(Integer colIndex) {
	    this.colIndex = colIndex; 
	}
        public void setVarFalg(String varFalg) {
	    this.varFalg = varFalg; 
	}
        public void setDirection(String direction) {
	    this.direction = direction; 
	}
        public void setEndRowIndex(Integer endRowIndex) {
	    this.endRowIndex = endRowIndex; 
	}
        public void setEndColIndex(Integer endColIndex) {
	    this.endColIndex = endColIndex; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepReportCellPos other = (DepReportCellPos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}


	public DepReportCellPos jsonToObject(JSONObject jsonObject) {
            return DepReportCellPosJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return DepReportCellPosJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return DepReportCellPosJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
