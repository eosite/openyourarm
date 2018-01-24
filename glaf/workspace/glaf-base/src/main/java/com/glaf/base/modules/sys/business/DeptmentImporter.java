package com.glaf.base.modules.sys.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.SysDepartmentService;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.security.Authentication;

public class DeptmentImporter {
	protected final static Log logger = LogFactory.getLog(DeptmentImporter.class);

	public void doImport(java.io.InputStream inputStream) {
		logger.debug("----------------DeptmentImporter------------------");
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
				}
			}
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		List<SysDepartment> departments = new ArrayList<SysDepartment>();
		int rows = sheet.getLastRowNum();
		logger.debug("row num:" + rows);
		for (int rowIndex = 1; rowIndex <= rows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			SysDepartment department = new SysDepartment();

			if (Authentication.getAuthenticatedActorId() != null) {
				department.setCreateBy(Authentication.getAuthenticatedActorId());
			}
			int cells = row.getLastCellNum();
			for (int colIndex = 0; colIndex < cells; colIndex++) {
				logger.debug("colIndex:" + colIndex);
				HSSFCell cell = row.getCell(colIndex);
				if (cell != null) {
					String cellValue = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						cellValue = cell.getNumericCellValue() + "";
						if (cellValue.indexOf(".") != -1) {
							cellValue = cellValue.substring(0, cellValue.indexOf("."));
						}
						break;
					case HSSFCell.CELL_TYPE_STRING:
						cellValue = cell.getRichStringCellValue().getString();
						break;
					default:
						cellValue = cell.getStringCellValue();
						break;
					}
					if (cellValue == null) {
						cellValue = "";
					}
					cellValue = cellValue.trim();
					if (cellValue.startsWith("__$$__")) {
						cellValue = cellValue.substring(6, cellValue.length());
					}
					switch (colIndex) {
					case 0:
						logger.debug("code:" + cellValue);
						department.setCode(cellValue);
						break;
					case 1:
						department.setName(cellValue);
						break;
					case 2:
						department.setAnotherName(cellValue);
						break;
					case 3:
						department.setShortName(cellValue);
						break;
					case 4:
						department.setFormalFlag(cellValue);
						break;
					default:
						break;
					}
				}
			}
			logger.debug(department.toJsonObject().toJSONString());
			departments.add(department);
		}
		if (departments.size() > 0) {
			logger.debug("----------------------------------");
			logger.debug("department size:" + departments.size());
			SysDepartmentService sysDepartmentService = ContextFactory.getBean("sysDepartmentService");
			String name = null;
			List<String> names = new ArrayList<String>();
			for (SysDepartment department : departments) {
				try {
					name = null;
					if (sysDepartmentService.findByName(department.getName()) == null) {
						name = department.getName();
						SysTree node = new SysTree();
						node.setName(department.getName());
						node.setDesc(department.getName());
						node.setCode(department.getCode());
						node.setDiscriminator("D");
						node.setParentId(5);
						node.setCreateBy(department.getCreateBy());
						if (StringUtils.isNotEmpty(department.getCode())
								&& StringUtils.isNumeric(department.getCode())) {
							//node.setId(Long.parseLong(department.getCode()));
						}
						//logger.debug("node id:" + node.getId());
						department.setNode(node);

						sysDepartmentService.create(department);
						name = null;
					}
				} catch (Exception ex) {
					if (name != null) {
						names.add(name);
					}
					ex.printStackTrace();
					logger.error(ex);
				}
			}
			if (names.size() > 0) {
				logger.warn(names.size() + " records import failure");
				logger.warn(names);
			}
		}
	}

}
