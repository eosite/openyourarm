package com.glaf.datamgr.test;

import java.util.List;

import org.junit.Test;

import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.core.xml.XmlReader;
import com.glaf.datamgr.parse.SqliteParser;

public class SqliteImportTest {

	@Test
	public void test() throws Exception {
		String mappingFile = "./report/mapping/SKU.mapping.xml";
		String dataFile = "./report/data/SKU.db";
		XmlReader reader = new XmlReader();
		TableDefinition tableDefinition = reader.read(new java.io.FileInputStream(mappingFile));
		if (tableDefinition != null) {
			if (DBUtils.tableExists(tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(tableDefinition);
			}
		}
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableModel tableModel = xmlReader.read(new java.io.FileInputStream(mappingFile));
		SqliteParser parser = new SqliteParser();
		List<TableModel> rows = parser.parse(tableModel, new java.io.FileInputStream(dataFile));
		for (TableModel row : rows) {
			System.out.println(row.toString());
		}
		ITableDefinitionService tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);

		ITableDataService tableDataService = ContextFactory.getBean("tableDataService");
		tableDataService.saveAll(tableModel.getTableName(), null, rows);
	}

}
