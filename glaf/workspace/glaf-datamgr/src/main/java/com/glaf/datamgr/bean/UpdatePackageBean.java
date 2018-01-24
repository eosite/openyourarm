package com.glaf.datamgr.bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.Constants;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.core.util.ZipUtils;
import com.glaf.datamgr.domain.FileHistory;
import com.glaf.datamgr.query.FileHistoryQuery;
import com.glaf.datamgr.service.FileHistoryService;

public class UpdatePackageBean {
	protected static final Log logger = LogFactory.getLog(UpdatePackageBean.class);

	private static int BUFFER = 8192;

	protected FileHistoryService fileHistoryService;

	public FileHistoryService getFileHistoryService() {
		if (fileHistoryService == null) {
			fileHistoryService = ContextFactory.getBean("fileHistoryService");
		}
		return fileHistoryService;
	}

	public void setFileHistoryService(FileHistoryService fileHistoryService) {
		this.fileHistoryService = fileHistoryService;
	}

	public boolean updatePkg() {
		boolean success = true;
		FileHistoryQuery query = new FileHistoryQuery();
		query.type("pkg");
		query.setDeleteFlag(0);
		List<FileHistory> list = getFileHistoryService().list(query);
		if (list != null && !list.isEmpty()) {
			/**
			 * 获取最新版本的更新包
			 */
			FileHistory fileHistory = list.get(0);
			if (!StringUtils.equals(fileHistory.getPkgStatus(), "NEW")) {
				return false;
			}
			fileHistory = getFileHistoryService().getFileHistory(fileHistory.getFileId());
			if (fileHistory.getFileContent() != null) {
				File file = null;
				ZipInputStream zis = null;
				ByteArrayInputStream bais = null;
				BufferedInputStream bis = null;
				ByteArrayOutputStream baos = null;
				BufferedOutputStream bos = null;
				java.util.zip.ZipEntry entry = null;
				byte tmpByte[] = null;
				Map<String, byte[]> backupMap = new HashMap<String, byte[]>();
				Map<String, byte[]> updateMap = new HashMap<String, byte[]>();
				try {
					bais = new ByteArrayInputStream(fileHistory.getFileContent());
					bis = new BufferedInputStream(bais);
					zis = new ZipInputStream(bis);
					String root = System.getProperty(Constants.APP_PATH);
					while ((entry = zis.getNextEntry()) != null) {
						String key = entry.getName();
						long size = entry.getSize();
						long time = entry.getTime();
						key = StringTools.replace(key, "\\", "/");
						if (key.toLowerCase().endsWith(".jsp") && !StringUtils.startsWith(key, "WEB-INF/")) {
							continue;
						}
						file = new File(root + FileUtils.sp + key);
						if (file.exists() && file.isFile()) {
							if (file.length() == size) {
								if (time == file.lastModified()) {
									continue;
								}
							}
							backupMap.put(key, FileUtils.getBytes(file));
						}
						if (size == 0) {// 如果是目录
							String path = root + FileUtils.sp + key;
							logger.info("检查目录:" + path);
							FileUtils.mkdirs(path);
						} else {
							tmpByte = new byte[BUFFER];
							baos = new ByteArrayOutputStream();
							bos = new BufferedOutputStream(baos, BUFFER);
							int i = 0;
							while ((i = zis.read(tmpByte, 0, BUFFER)) != -1) {
								bos.write(tmpByte, 0, i);
							}
							bos.flush();
							byte[] bytes = baos.toByteArray();
							IOUtils.closeQuietly(baos);
							IOUtils.closeQuietly(baos);
							updateMap.put(entry.getName(), bytes);
						}
					}

					if (!backupMap.isEmpty()) {
						byte[] zipBytes = ZipUtils.toZipBytes(backupMap);
						FileHistory bean = new FileHistory();
						bean.setFileId(UUID32.getUUID());
						bean.setCreateBy("system");
						bean.setCreateTime(new Date());
						bean.setFileContent(zipBytes);
						bean.setFileName("backup_" + DateUtils.getNowYearMonthDayHHmmss() + ".zip");
						bean.setFileSize(zipBytes.length);
						bean.setLastModified(System.currentTimeMillis());
						bean.setMd5(DigestUtils.md5Hex(zipBytes));
						bean.setType("pkg_backup");
						bean.setVersion(1);
						bean.setDeleteFlag(0);
						bean.setPkgStatus("BACKUP");
						bean.setReferId(fileHistory.getFileId());
						getFileHistoryService().saveUpdatePackage(bean);
					}
					if (!updateMap.isEmpty()) {
						byte[] zipBytes = ZipUtils.toZipBytes(updateMap);
						FileHistory bean = new FileHistory();
						bean.setFileId(UUID32.getUUID());
						bean.setCreateBy("system");
						bean.setCreateTime(new Date());
						bean.setFileContent(zipBytes);
						bean.setFileName("update_" + DateUtils.getNowYearMonthDayHHmmss() + ".zip");
						bean.setFileSize(zipBytes.length);
						bean.setLastModified(System.currentTimeMillis());
						bean.setMd5(DigestUtils.md5Hex(zipBytes));
						bean.setType("pkg_update");
						bean.setVersion(1);
						bean.setDeleteFlag(0);
						bean.setPkgStatus("UPDATE");
						bean.setReferId(fileHistory.getFileId());
						getFileHistoryService().saveUpdatePackage(bean);

						Set<Entry<String, byte[]>> entrySet = updateMap.entrySet();
						for (Entry<String, byte[]> entry2 : entrySet) {
							String key = entry2.getKey();
							byte[] value = entry2.getValue();
							String path = root + FileUtils.sp + key;
							logger.info("prepare update file:" + path);
							try {
								FileUtils.save(path, value);
							} catch (Exception ex) {
								success = false;
								logger.error("save file error:" + path);
							}
						}
					}
					if (success) {
						fileHistory.setPkgStatus("UPDATE_SUCESS");
						fileHistory.setPkgUpdateTime(new Date());
						getFileHistoryService().update(fileHistory);
						logger.info("更新成功！");
						success = true;
					}
				} catch (Exception ex) {
					success = false;
					logger.error(ex);
				} finally {
					IOUtils.closeQuietly(bais);
					IOUtils.closeQuietly(bis);
					IOUtils.closeQuietly(zis);
					IOUtils.closeQuietly(baos);
					IOUtils.closeQuietly(baos);
				}
			}
		}
		return success;
	}

}
