package com.glaf.base.modules.sys.model;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;

public class TableEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	protected ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected String tableName;

	protected String sortColumn;

	protected String sortOrder = "asc";

	protected FieldInterface idField;

	protected List<FieldInterface> fields;

	protected DataRequest dataRequest;

	public TableEntity() {

	}

	public ConcurrentMap<String, String> getColumnMap() {
		return columnMap;
	}

	public DataRequest getDataRequest() {
		return dataRequest;
	}

	public List<FieldInterface> getFields() {
		return fields;
	}

	public FieldInterface getIdField() {
		return idField;
	}

	public ConcurrentMap<String, String> getJavaTypeMap() {
		return javaTypeMap;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getTableName() {
		return tableName;
	}

	public void processDataRequest(DataRequest dataRequest) {
		if (dataRequest.getFilter() != null) {
			if (dataRequest.getFilter().getField() != null) {
				dataRequest.getFilter().setColumn(
						columnMap.get(dataRequest.getFilter().getField()));
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
			}

			List<FilterDescriptor> filters = dataRequest.getFilter()
					.getFilters();
			for (FilterDescriptor filter : filters) {
				filter.setParent(dataRequest.getFilter());
				if (filter.getField() != null) {
					filter.setColumn(columnMap.get(filter.getField()));
					filter.setJavaType(javaTypeMap.get(filter.getField()));
				}

				List<FilterDescriptor> subFilters = filter.getFilters();
				for (FilterDescriptor f : subFilters) {
					f.setColumn(columnMap.get(f.getField()));
					f.setJavaType(javaTypeMap.get(f.getField()));
					f.setParent(filter);
				}
			}
		}
		this.dataRequest = dataRequest;
	}

	public void setDataRequest(DataRequest dataRequest) {
		this.dataRequest = dataRequest;
	}

	public void setFields(List<FieldInterface> fields) {
		this.fields = fields;
		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface field : fields) {
				columnMap.put(field.getDname(), field.getDname());
				if (StringUtils.equalsIgnoreCase(field.getDtype(), "i4")) {
					javaTypeMap.put(field.getDname(), "Integer");
				} else if (StringUtils
						.equalsIgnoreCase(field.getDtype(), "int")) {
					javaTypeMap.put(field.getDname(), "Integer");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"integer")) {
					javaTypeMap.put(field.getDname(), "Integer");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(), "i8")) {
					javaTypeMap.put(field.getDname(), "Long");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"long")) {
					javaTypeMap.put(field.getDname(), "Long");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"double")) {
					javaTypeMap.put(field.getDname(), "Double");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"float")) {
					javaTypeMap.put(field.getDname(), "Double");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(), "r8")) {
					javaTypeMap.put(field.getDname(), "Double");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"date")) {
					javaTypeMap.put(field.getDname(), "Date");
				} else if (StringUtils.equalsIgnoreCase(field.getDtype(),
						"datetime")) {
					javaTypeMap.put(field.getDname(), "Date");
				} else {
					javaTypeMap.put(field.getDname(), "String");
				}
			}
		}
	}

	public void setIdField(FieldInterface idField) {
		this.idField = idField;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
