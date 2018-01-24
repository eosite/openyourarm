package com.glaf.report.core.domain;

import java.io.Serializable;
import java.util.Map;
/**
 * 报表模板数据集映射
 * @author Dane
 *
 */
public class ReportTmpMappingEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8623519436534142483L;
	private Map<String, String> datasetMapping;
	private Map<String, Map<String, String>> datasetColMapping;

	public Map<String, String> getDatasetMapping() {
		return datasetMapping;
	}

	public Map<String, Map<String, String>> getDatasetColMapping() {
		return datasetColMapping;
	}

	public void setDatasetMapping(Map<String, String> datasetMapping) {
		this.datasetMapping = datasetMapping;
	}

	public void setDatasetColMapping(Map<String, Map<String, String>> datasetColMapping) {
		this.datasetColMapping = datasetColMapping;
	}

}
