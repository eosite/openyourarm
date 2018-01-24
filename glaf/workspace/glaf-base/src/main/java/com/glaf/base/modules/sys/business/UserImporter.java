package com.glaf.base.modules.sys.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.security.Authentication;
import com.glaf.core.service.IDatabaseService;

public class UserImporter {
	protected final static Log logger = LogFactory.getLog(UserImporter.class);

	public void doImport(java.io.InputStream inputStream) {
		logger.debug("----------------UserImporter------------------");
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
		List<SysUser> users = new ArrayList<SysUser>();
		int rows = sheet.getLastRowNum();
		logger.debug("row num:" + rows);
		for (int rowIndex = 2; rowIndex <= rows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			SysUser user = new SysUser();
			user.setStatus("0");
			user.setAdminFlag("0");
			user.setLoginRetry(0);
			user.setLocked(0);
			user.setUserType(0);
			if (Authentication.getAuthenticatedActorId() != null) {
				user.setCreateBy(Authentication.getAuthenticatedActorId());
			}
			int cells = row.getLastCellNum();
			for (int colIndex = 0; colIndex < cells; colIndex++) {
				HSSFCell cell = row.getCell(colIndex);
				if (cell != null) {
					String cellValue = null;
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						cellValue = String.valueOf(cell.getNumericCellValue());
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
						user.setAttribute(cellValue);
						break;
					case 1:
						user.setAccount(cellValue);
						break;
					case 2:
						user.setName(cellValue);
						break;
					case 3:
						user.setMail(cellValue);
						break;
					case 4:
						user.setMobile(cellValue);
						break;
					default:
						break;
					}
				}
			}
			users.add(user);
		}
		if (users.size() > 0) {
			logger.debug("----------------------------------");
			logger.debug("user size:" + users.size());
			String currentName = Environment.getCurrentSystemName();
			SysUserService sysUserService = ContextFactory.getBean("sysUserService");
			IDatabaseService databaseService = ContextFactory.getBean("databaseService");
			String username = null;
			List<String> userIds = new ArrayList<String>();
			for (SysUser user : users) {
				logger.debug(user.toJsonObject().toJSONString());
				try {
					logger.debug("section:" + user.getAttribute());
					if (StringUtils.isNotEmpty(user.getAttribute())) {
						Database database = databaseService.getDatabaseByMapping(user.getAttribute());
						if (database != null) {
							Environment.setCurrentSystemName(database.getName());
							logger.debug("sys name:" + Environment.getCurrentSystemName());
						}
					}
					username = null;
					if (sysUserService.findByAccount(user.getAccount()) == null) {
						username = user.getAccount();
						//user.setPasswordHash("888888");
						sysUserService.create(user);
						username = null;
					}
				} catch (Exception ex) {
					if (username != null) {
						userIds.add(username);
					}
					ex.printStackTrace();
					logger.error(ex);
				} finally {
					Environment.setCurrentSystemName(currentName);
				}
			}
			if (userIds.size() > 0) {
				logger.warn(userIds.size() + " records import failure");
				logger.warn(userIds);
			}
		}
	}

}
