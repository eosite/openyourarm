package com.glaf.etl.query;

import java.util.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.query.DataQuery;

public class EtlTransferTaskConfigQuery extends DataQuery {
       
        public String getDir() {
			return dir;
		}

		public String getField() {
			return field;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public void setField(String field) {
			this.field = field;
		}
		
		

		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}

		private static final long serialVersionUID = 1L;
		private String field;
		private String dir;


}