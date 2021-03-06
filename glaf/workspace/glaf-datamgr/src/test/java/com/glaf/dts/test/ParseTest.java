package com.glaf.dts.test;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.parse.ParserFacede;

public class ParseTest {

	@Test
	public void testImportXls() {
		String mappingFile = "./report/mapping/Plans.mapping.xml";
		String dataFile = "./report/data/logistics_Plans_Temp01.xls";
		ContextFactory.hasBean("dataSource");
		ParserFacede parser = new ParserFacede();
		List<TableModel> rows = parser.parse(Environment.DEFAULT_SYSTEM_NAME, mappingFile, dataFile,
				"root_" + DateUtils.getDateTime("yyyyMMddHHmmss", new Date()), true);
		if (rows != null && !rows.isEmpty()) {
			JSONArray array = new JSONArray();
			for (TableModel model : rows) {
				JSONObject jsonObject = new JSONObject();
				List<ColumnModel> columns = model.getColumns();
				if (columns != null && !columns.isEmpty()) {
					for (ColumnModel col : columns) {
						if (col.getName() != null) {
							jsonObject.put(col.getName(), col.getValue());
						}
						if (col.getColumnName() != null) {
							jsonObject.put(col.getColumnName().toLowerCase(), col.getValue());
						}
					}
				}
				if (model.getIdColumn() != null) {
					ColumnModel col = model.getIdColumn();
					if (col.getName() != null) {
						jsonObject.put(col.getName(), col.getValue());
					}
					if (col.getColumnName() != null) {
						jsonObject.put(col.getColumnName().toLowerCase(), col.getValue());
					}
				}
				array.put(jsonObject);
			}
			System.out.println(array.toString('\n'));
		}
	}

}
