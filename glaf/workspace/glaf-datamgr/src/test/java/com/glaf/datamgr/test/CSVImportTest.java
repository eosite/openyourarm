package com.glaf.datamgr.test;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.glaf.core.base.TableModel;
import com.glaf.core.db.TableDataManager;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.Constants;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.core.xml.XmlReader;
import com.glaf.datamgr.parse.CsvTextParser;

public class CSVImportTest {

	@Test
	public void importData() throws IOException {
		System.setProperty("includeJars", "glaf-core.jar");
		System.setProperty(Constants.CONFIG_PATH, ".");
		String mappingFile = "./mapping/WEB_STAT.mapping.xml";
		String dataFile = "./data/WEB_STAT.csv";

		XmlMappingReader xmlReader = new XmlMappingReader();
		TableModel tableModel = xmlReader.read(new java.io.FileInputStream(mappingFile));
		CsvTextParser textReader = new CsvTextParser();
		List<TableModel> rows = textReader.parse(tableModel, new java.io.FileInputStream(dataFile));
		for (TableModel row : rows) {
			row.setDbType("hbase");
			System.out.println(row.toString());
		}
		XmlReader reader = new XmlReader();
		TableDefinition tableDefinition = reader.read(new java.io.FileInputStream(mappingFile));
		tableDefinition.setDbType("hbase");
		com.glaf.core.config.Environment.setCurrentSystemName("phoenix");

		TableDataManager mgr = new TableDataManager();
		mgr.insertTableData("phoenix", "hbase", rows);
	}

}
