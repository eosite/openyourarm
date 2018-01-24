package com.glaf.form.web.springmvc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.metamodel.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.factory.RedisFileStorageFactory;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.FtpUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;

import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.query.FormAttachmentQuery;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.IFormAttachmentService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.Global;
import com.glaf.form.rule.util.ImageCompressUtils;
import com.glaf.form.rule.util.InjectUtils;

@Controller("/form/imageUpload")
@RequestMapping("/form/imageUpload")
public class FormImageUploadController {

	@Autowired
	protected DataSetService dataSetService;

	protected static final String to_db = "1";

	protected static final String to_dir = "0";

	public static void main(String[] args) {
		// String full_path =
		// "ftp://192.168.10.202:21//201510220922319360.pdf'AA" ;
		// //System.out.println(full_path.replaceAll("ftp://(\\w*\\.?)*(:\\w*)",
		// ""));

		// FTPClient ftpClient = null;
		// ftpClient = FtpUtils.connectServer("192.168.1.27", 21, "root",
		// "123456");
		// String remoteFile = "///201512171003051319.jpg";
		// byte[] bytes = FtpUtils.getBytes(ftpClient, remoteFile);
		// //System.out.println(bytes.length);
		// String checkSql = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";

		// String CHECKSQL = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";
		// String targerStr = "' + ltrim('') +
		// 'bc19f62c-3368-49f2-96c6-a57101141cf0";
		// System.out.println(Pattern.matches(CHECKSQL, targerStr));
		// //System.out.println(HtmlUtils.htmlEscape("a%27+and+%27f%27%3D%27f"));
		// String retIds = "";
		// System.out.println(retIds.lastIndexOf(",") == -1 ? "" :
		// retIds.substring(0, retIds.lastIndexOf(",")));
		String k = "ftp://192.168.10.202/201607071510250786.docx";
		System.out.println(k.replaceAll("ftp://(\\w*\\.?)*(:\\w*)?/", ""));
	}

	protected IFormAttachmentService formAttachmentService;

	protected IServerEntityService serverEntityService;

	protected FormRulePropertyService formRulePropertyService;

	protected MutilDatabaseBean mutilDatabaseBean;

	protected IDatabaseService databaseService;

	/**
	 * 获取文件内容，从redis缓存中加载文件流
	 * 
	 * @param atta
	 * @return
	 */
	private byte[] getFileContent(FormAttachment atta) {
		byte[] data = null;
		String cacheKey = "form_attach_" + SystemConfig.getIntToken() + "_" + atta.getId();
		if (to_db.equals(atta.getType())) {
			if (SystemConfig.getBoolean("use_file_cache")) {
				try {
					data = RedisFileStorageFactory.getInstance().getData(cacheKey);
					if (data != null) {
						return data;
					}
				} catch (Exception ex) {
				}
			}
			FormAttachment bean = this.formAttachmentService.getFormAttachmentById(atta.getId());
			data = bean.getFileContent();
			if (data != null) {
				if (SystemConfig.getBoolean("use_file_cache")) {
					try {
						RedisFileStorageFactory.getInstance().saveData(cacheKey, data);
					} catch (Exception ex) {
					}
				}
			}
		}
		return data;
	}

	@RequestMapping(params = "method=download")
	public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("from") String from,
			@RequestParam("randomparent") String randomparent) throws Exception {
		InputStream fis = null;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			
			// 从数据库中读取
			List<FormAttachment> attaList = this.formAttachmentService.getFormAttachmentByParent(randomparent);

			FormAttachment atta = new FormAttachment();
			if (attaList != null && attaList.size() > 0) {
				atta = attaList.get(0);
				// atta =
				// this.formAttachmentService.getFormAttachmentById(atta.getId());
			}

			if (to_db.equals(atta.getType())) {
				atta.setFileContent(this.getFileContent(atta));
				ResponseUtils.download(request, response, atta.getFileContent(), atta.getFileName());
			} else if (to_dir.equals(atta.getType())) {
				// 从服务器目录中读取
				String projectpath = request.getSession().getServletContext().getRealPath("/");
				File file = new File(projectpath + atta.getSaveServicePath());
				if (file.exists()) {
					fis = new FileInputStream(file);
					ResponseUtils.download(request, response, fis, atta.getFileName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
	}

	/**
	 * @param comBase
	 *            压缩基数
	 * @param scale
	 *            压缩限制(宽/高)比例 一般用1：
	 *            当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
	 * @param image
	 * @return
	 */
	public BufferedImage getMinImage(Image image, double comBase, double scale) {
		int srcHeight = image.getHeight(null);
		int srcWidth = image.getWidth(null);
		int deskHeight = 0;// 缩略图高
		int deskWidth = 0;// 缩略图宽
		double srcScale = (double) srcHeight / srcWidth;
		/** 缩略图宽高算法 */
		if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
			if (srcScale >= scale || 1 / srcScale > scale) {
				if (srcScale >= scale) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			} else {
				if ((double) srcHeight > comBase) {
					deskHeight = (int) comBase;
					deskWidth = srcWidth * deskHeight / srcHeight;
				} else {
					deskWidth = (int) comBase;
					deskHeight = srcHeight * deskWidth / srcWidth;
				}
			}
		} else {
			deskHeight = srcHeight;
			deskWidth = srcWidth;
		}
		BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
		tag.getGraphics().drawImage(image, 0, 0, deskWidth, deskHeight, null); // 绘制缩小后的图

		return tag;
	}

	/**
	 * 获取文件内容，从redis缓存中加载文件流
	 * 
	 * @param atta
	 * @return
	 */
	private byte[] getFileContentById(String attaId) {
		byte[] data = null;
		String cacheKey = "form_attach_" + attaId;
		if (SystemConfig.getBoolean("use_file_cache")) {
			try {
				data = RedisFileStorageFactory.getInstance().getData(cacheKey);
			} catch (Exception ex) {
			}
		}
		if (data != null) {
			return data;
		}
		return data;
	}

	/**
	 * 获取文件内容，从redis缓存中加载文件流
	 * 
	 * @param atta
	 * @return
	 */
	private void setFileContentById(String attaId, byte[] data) {
		String cacheKey = "form_attach_" + attaId;
		if (data != null) {
			if (SystemConfig.getBoolean("use_file_cache")) {
				try {
					RedisFileStorageFactory.getInstance().saveData(cacheKey, data);
				} catch (Exception ex) {
				}
			}
		}
	}

	@RequestMapping(params = "method=downloadById")
	public void downloadById(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String sid) {
		InputStream fis = null;
		try {
			int isMin = RequestUtils.getInt(request, "isMin");// 是否获取缩略图
			int min = RequestUtils.getInt(request, "min");// 是否获取缩略图
			String comBaseStr = RequestUtils.getString(request, "comBase", "");
			String scaleStr = RequestUtils.getString(request, "comBase", "");
			double comBase = RequestUtils.getDouble(request, "comBase", 100d);// 获取压缩基数
			double scale = RequestUtils.getDouble(request, "scale", 1d);// 获取缩放比例
			String id = sid + isMin + min + comBaseStr + scaleStr;
			
			//查询出无内容的附件信息，用于获取名称，并校验是否存在该文件
			FormAttachment noContentData = this.formAttachmentService.getCachedFormAttachmentNotContentById(sid);
			if(noContentData == null){
				return;
			}
			
			byte[] data = getFileContentById(id);
			if (data != null) {
				// 直接取缓存
				ResponseUtils.download(request, response, data, noContentData.getFileName());
				return;
			}
			// 加入缓存
			// 从数据库中读取
			FormAttachment atta = this.formAttachmentService.getFormAttachmentById(sid);
			if (to_db.equals(atta.getType())) {
				if (isMin == 1) {
					// 获取缩略图
					//放入缓存中
					setFileContentById(id, atta.getFilecontentThu());
					ResponseUtils.download(request, response, atta.getFilecontentThu(), atta.getFilenameThu());
				} else {
					if (min == 1) {
						// 图片默认进行压缩
						BufferedImage bufferImage = getMinImage(
								ImageIO.read(new ByteArrayInputStream(atta.getFileContent())), comBase, scale);
						ByteArrayOutputStream imageoutTh = new ByteArrayOutputStream();
						ImageIO.write(bufferImage, "JPEG", imageoutTh);
						//放入缓存中
						setFileContentById(id, imageoutTh.toByteArray());
						ResponseUtils.download(request, response, imageoutTh.toByteArray(), atta.getFileName());
					} else {
						// 获取原图
						//放入缓存中
						setFileContentById(id, atta.getFileContent());
						ResponseUtils.download(request, response, atta.getFileContent(), atta.getFileName());
					}
				}
			} else if (to_dir.equals(atta.getType())) {
				if (isMin == 1) {
					// 从服务器目录中读取,获取缩略图
					String projectpath = request.getSession().getServletContext().getRealPath("/");
					Path filePath = Paths.get(projectpath + atta.getFilepathThu());
					// File file = new File(projectpath + atta.getFilepathThu());
					if (Files.exists(filePath)) {
						byte[] allBytes = Files.readAllBytes(filePath);
						//放入缓存中
						setFileContentById(id, allBytes);
						ResponseUtils.download(request, response, allBytes, atta.getFileName());
					}
				} else {
					// 从服务器目录中读取,获取原图
					String projectpath = request.getSession().getServletContext().getRealPath("/");
					Path filePath = Paths.get(projectpath + atta.getFilepathThu());
					byte[] allBytes = null;
					if (Files.exists(filePath)) {
						allBytes = Files.readAllBytes(filePath);
					}
					File file = new File(projectpath + atta.getSaveServicePath());
					if (file.exists()) {
						allBytes = FileUtils.getBytes(file);
					}
					if (min == 1 && allBytes != null) {
						// 图片默认进行压缩
						BufferedImage bufferImage = getMinImage(ImageIO.read(new ByteArrayInputStream(allBytes)),
								comBase, scale);
						ByteArrayOutputStream imageoutTh = new ByteArrayOutputStream();
						ImageIO.write(bufferImage, "JPEG", imageoutTh);
						//放入缓存中
						setFileContentById(id, imageoutTh.toByteArray());
						ResponseUtils.download(request, response, imageoutTh.toByteArray(), atta.getFileName());
					} else {
						if (allBytes != null) {
							//放入缓存中
							setFileContentById(id, allBytes);
							ResponseUtils.download(request, response, allBytes, atta.getFileName());
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
	}

	private boolean checkSql(String field) {
		String checkSql = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";
		if (Pattern.matches(checkSql, field) || field == null || field.contains("+") || field.contains(" ")) {
			return false;
		}
		return true;
	}

	@RequestMapping(params = "method=downloadByTab")
	public void downloadByTab(HttpServletRequest request, HttpServletResponse response, @RequestParam("tid") String id,
			@RequestParam("tn") String tableNameColumn, @RequestParam("ct") String contentColumn,
			@RequestParam("id") String idColumn, @RequestParam("databaseId") Long databaseId) {
		InputStream fis = null;
		try {
			if (!checkSql(idColumn) || !checkSql(id) || !checkSql(contentColumn) || !checkSql(tableNameColumn)) {
				return;
			}
			
			String sql = "select " + contentColumn + " as " + contentColumn + " from " + tableNameColumn + " where "
					+ InjectUtils.escapeSql(idColumn) + " = '" + InjectUtils.escapeSql(id) + "'";
			List<Map<String, Object>> dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
			JSONObject josnObj = new JSONObject();
			if (dataMaps.size() > 0) {
				Map<String, Object> map = dataMaps.get(0);
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					josnObj.put(key, map.get(key) != null ? map.get(key) : "");
				}
			}
			if (dataMaps != null && dataMaps.size() > 0 && josnObj.getBytes(contentColumn) != null) {
				ResponseUtils.download(request, response, josnObj.getBytes(contentColumn), "downlownfile");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
	}

	@RequestMapping(params = "method=downloadTextById")
	public void downloadTextById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") String sid) {
		InputStream fis = null;
		PrintWriter writer = null;
		Reader reader = null;

		try {
			// 从数据库中读取
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			FormAttachment atta = this.formAttachmentService.getFormAttachmentById(sid);

			if (to_db.equals(atta.getType())) {
				writer = response.getWriter();
				byte[] strbyte = atta.getFileContent();
				long size = IOUtils.write(writer, new String(strbyte, "gbk"));
				writer.flush();
				IOUtils.closeStream(writer);
				writer = null;
			} else if (to_dir.equals(atta.getType())) {
				// 从服务器目录中读取
				String projectpath = request.getSession().getServletContext().getRealPath("/");
				File file = new File(projectpath + atta.getSaveServicePath());
				if (file.exists()) {
					writer = response.getWriter();
					fis = new FileInputStream(file);
					reader = new InputStreamReader(fis);
					IOUtils.write(reader, writer);
					writer.flush();
					IOUtils.closeStream(writer);
					IOUtils.closeStream(fis);
					IOUtils.closeStream(reader);
					fis = null;
					reader = null;
					writer = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(writer);
			IOUtils.closeStream(fis);
			IOUtils.closeStream(reader);
		}
	}

	@RequestMapping(params = "method=download2")
	public void downloadById(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("mode") String mode, @RequestParam("rp") String id, @RequestParam("id") String rid) {
		InputStream fis = null;
		try {
			String userAgent = request.getHeader("User-Agent");
			// toLowerCase():字母变小写,indexOf 方法返回一个整数值，指出 String 对象内子字符串的开始位置。
			boolean isIe = userAgent.toLowerCase().indexOf("trident") != -1
					|| userAgent.toLowerCase().indexOf("msie") != -1 ? true : false;
			Long dbid = RequestUtils.getLong(request, "databaseId", 0L);
			// 检测sql 是否有sql注入
			String checkSql = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";
			if (Pattern.matches(checkSql, id) || id == null || id.contains("+") || id.contains(" ")) {
				return;
			}
			if ("ATTA".equalsIgnoreCase(mode)) {
				FormAttachment atta = null;
				String currSystemName = Environment.getCurrentSystemName();
				String systemName = Environment.DEFAULT_SYSTEM_NAME;
				try {
					if (dbid.longValue() != 0) {
						Database database = databaseService.getDatabaseById(dbid);
						if (database != null) {
							systemName = database.getName();
						}
					}
					Environment.setCurrentSystemName(systemName);
					// 从数据库中读取
					atta = this.formAttachmentService.getFormAttachmentById(id);
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					Environment.setCurrentSystemName(currSystemName);
				}
				if (to_db.equals(atta.getType())) {
					if (isIe) {
						byte[] retByte = ImageCompressUtils.compressImage(atta.getFileContent());
						ResponseUtils.download(request, response, retByte == null ? atta.getFileContent() : retByte,
								atta.getFileName());
					} else {
						ResponseUtils.download(request, response, atta.getFileContent(), atta.getFileName());
					}
				} else if (to_dir.equals(atta.getType())) {
					// 从服务器目录中读取
					String projectpath = request.getSession().getServletContext().getRealPath("/");
					File file = new File(projectpath + atta.getSaveServicePath());
					if (file.exists()) {
						fis = new FileInputStream(file);
						if (isIe) {
							byte[] retByte = ImageCompressUtils.compressImageByFile(fis);
							if (retByte == null) {
								ResponseUtils.download(request, response, fis, atta.getFileName());
							} else {
								ResponseUtils.download(request, response, retByte, atta.getFileName());
							}
						} else {
							ResponseUtils.download(request, response, fis, atta.getFileName());
						}
					}
				}
			} else {
				Map<String, String> ruleMap = getRuleMap(rid);
				String FTPField = ruleMap.get("FTPField"); // 保存字段
															// [{"name":"文件名称",datas:{idField:'',ftpField
															// : ''}]
				String pathColumn = null;
				String idColumn = null;
				JSONArray fields = JSON.parseArray(FTPField);
				if (fields != null && !fields.isEmpty()) {
					JSONObject filedObj = fields.getJSONObject(0);
					JSONObject datas = filedObj.getJSONObject("datas");
					idColumn = datas.getString("idField");
					pathColumn = datas.getString("ftpField");
				}

				String dataSourceSet = ruleMap.get("dataSourceSet");
				JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
				JSONObject datasourceSetJSONObject = null;
				if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
					datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
				}
				if (datasourceSetJSONObject == null) {
					return;
				}

				// 构建sql start
				DataSetBuilder builder = new DataSetBuilder();
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("HttpServletRequest", request);

				JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")),
						mapping;
				if (metadata != null) {
					mapping = metadata.getJSONObject("mapping");
					idColumn = Global.getOriginalColumnName(mapping, idColumn);
				}

				Query query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"),
						idColumn.replace("_0_", ".") + "='" + InjectUtils.escapeSql(id) + "'", "", parameter);
				String sql = query.toSql();
				// 构建sql end
				List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
				// 数据源ID
				Long databaseId = datasourceSetJSONObject.getLong("databaseId");
				// 切换数据源
				if (dbid.longValue() != 0) {
					databaseId = dbid;
				}

				// 执行查询
				dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
				JSONObject josnObj = new JSONObject();
				if (dataMaps.size() > 0) {
					Map<String, Object> map = dataMaps.get(0);
					Set<String> keys = map.keySet();
					Iterator<String> iterator = keys.iterator();
					while (iterator.hasNext()) {
						String key = (String) iterator.next();
						josnObj.put(key, map.get(key) != null ? map.get(key) : "");
					}
				}

				if ("FTP".equalsIgnoreCase(mode)) {
					String full_path = josnObj.getString(pathColumn);
					String FTPServer = ruleMap.get("FTPServer"); // 服务器ID
					Long serverId = null;
					JSONArray servers = JSON.parseArray(FTPServer);
					if (servers != null && !servers.isEmpty()) {
						com.alibaba.fastjson.JSONObject server = servers.getJSONObject(0);
						serverId = server.getLong("serverId");
					}

					if (full_path == null || serverId == null) {
						return;
					}

					// FTP
					LoginContext loginContext = RequestUtils.getLoginContext(request);
					FTPClient ftpClient = null;
					String path = full_path.replaceAll("ftp://(\\w*\\.?)*(:\\w*)?/", "");
					try {
						ServerEntity server = serverEntityService.getServerEntityById(serverId);
						if (server != null) {
							if (StringUtils.isNotEmpty(server.getPerms())) {
								boolean hasPermission = false;
								List<String> perms = StringTools.split(server.getPerms());
								if (perms != null && !perms.isEmpty()) {
									for (String perm : perms) {
										if (loginContext.getPermissions().contains(perm)) {
											hasPermission = true;
											break;
										}
									}
								}
								if (!hasPermission) {
									return;
								}
							}
							String key = server.getKey();
							String pwd = server.getPassword();
							pwd = SecurityUtils.decode(key, pwd);
							ftpClient = FtpUtils.connectServer(server.getHost(), server.getPort(), server.getUser(),
									pwd);
							String remoteFile = server.getPath() + "/" + path;
							byte[] bytes = FtpUtils.getBytes(ftpClient, remoteFile);
							String filename = StringTools.replaceIgnoreCase(path, "/", "_");
							if (isIe) {
								byte[] retByte = ImageCompressUtils.compressImage(bytes);
								ResponseUtils.download(request, response, retByte == null ? bytes : retByte, filename);
							} else {
								ResponseUtils.download(request, response, bytes, filename);
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						FtpUtils.closeConnect(ftpClient);
					}
				} else {// 常规
						// Map<String, Object> map = dataMaps.get(0);
						// ResponseUtils.download(request, response, (byte[])
						// map.get("pathColumn"), "downlownfile");
					if (josnObj.getBytes(pathColumn) != null) {
						ResponseUtils.download(request, response, josnObj.getBytes(pathColumn), "downlownfile");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
	}

	@RequestMapping(params = "method=fileView")
	public void fileView(HttpServletRequest request, HttpServletResponse response, @RequestParam("mode") String mode,
			@RequestParam("rp") String id, @RequestParam("id") String rid, @RequestParam("key") String keyField,
			@RequestParam("url") String urlField, @RequestParam("fid") String ftpId) {
		InputStream fis = null;
		try {
			Long dbid = RequestUtils.getLong(request, "databaseId", 0L);
			// 检测sql 是否有sql注入
			String checkSql = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";
			if (Pattern.matches(checkSql, id) || id == null || id.contains("+") || id.contains(" ")) {
				return;
			}
			if ("ATTA".equalsIgnoreCase(mode)) {
				// 从数据库中读取
				FormAttachment atta = null;
				String currSystemName = Environment.getCurrentSystemName();
				String systemName = Environment.DEFAULT_SYSTEM_NAME;
				try {
					if (dbid.longValue() != 0) {
						Database database = databaseService.getDatabaseById(dbid);
						if (database != null) {
							systemName = database.getName();
						}
					}
					Environment.setCurrentSystemName(systemName);
					// 从数据库中读取
					List<FormAttachment> attas = formAttachmentService.getFormAttachmentByParent(id);
					atta = attas.get(0);
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					Environment.setCurrentSystemName(currSystemName);
				}

				if (to_db.equals(atta.getType())) {
					atta.setFileContent(this.getFileContent(atta));
					ResponseUtils.download(request, response, atta.getFileContent(), atta.getFileName());
				} else if (to_dir.equals(atta.getType())) {
					// 从服务器目录中读取
					String projectpath = request.getSession().getServletContext().getRealPath("/");
					File file = new File(projectpath + atta.getSaveServicePath());
					if (file.exists()) {
						fis = new FileInputStream(file);
						ResponseUtils.download(request, response, fis, atta.getFileName());
					}
				}
			} else {
				// if ("FTP".equalsIgnoreCase(mode))

				Map<String, String> ruleMap = getRuleMap(rid);

				String dataSourceSet = ruleMap.get("dataSourceSet");
				JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
				JSONObject datasourceSetJSONObject = null;
				if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
					datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
				}
				if (datasourceSetJSONObject == null) {
					return;
				}

				JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")),
						mapping;
				if (metadata != null) {
					mapping = metadata.getJSONObject("mapping");
					keyField = Global.getOriginalColumnName(mapping, keyField.replace(".", "_0_")).replace("_0_", ".");
				}

				// 构建sql start
				DataSetBuilder builder = new DataSetBuilder();
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("HttpServletRequest", request);

				Query query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"),
						keyField + "='" + InjectUtils.escapeSql(id) + "'", "", parameter);
				String sql = query.toSql();
				// 构建sql end
				List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
				// 数据源ID
				Long databaseId = datasourceSetJSONObject.getLong("databaseId");
				if (dbid.longValue() != 0) {
					databaseId = dbid;
				}
				// 执行查询
				dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
				JSONObject josnObj = new JSONObject();
				if (dataMaps.size() > 0) {
					Map<String, Object> map = dataMaps.get(0);
					Set<String> keys = map.keySet();
					Iterator<String> iterator = keys.iterator();
					while (iterator.hasNext()) {
						String key = (String) iterator.next();
						josnObj.put(key, map.get(key) != null ? map.get(key) : "");
					}
				}

				if ("FTP".equalsIgnoreCase(mode)) {
					String full_path = josnObj.getString(urlField.replace(".", "_0_"));

					Long serverId = Long.parseLong(ftpId);

					if (full_path == null || serverId == null) {
						return;
					}

					// FTP
					LoginContext loginContext = RequestUtils.getLoginContext(request);
					FTPClient ftpClient = null;
					String path = full_path.replaceAll("ftp://(\\w*\\.?)*(:\\w*)?/", "");
					try {
						ServerEntity server = serverEntityService.getServerEntityById(serverId);
						if (server != null) {
							if (StringUtils.isNotEmpty(server.getPerms())) {
								boolean hasPermission = false;
								List<String> perms = StringTools.split(server.getPerms());
								if (perms != null && !perms.isEmpty()) {
									for (String perm : perms) {
										if (loginContext.getPermissions().contains(perm)) {
											hasPermission = true;
											break;
										}
									}
								}
								if (!hasPermission) {
									return;
								}
							}
							String key = server.getKey();
							String pwd = server.getPassword();
							pwd = SecurityUtils.decode(key, pwd);
							ftpClient = FtpUtils.connectServer(server.getHost(), server.getPort(), server.getUser(),
									pwd);
							String remoteFile = server.getPath() + "/" + path;
							byte[] bytes = FtpUtils.getBytes(ftpClient, remoteFile);
							String filename = StringTools.replaceIgnoreCase(path, "/", "_");
							ResponseUtils.download(request, response, bytes, filename);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					} finally {
						FtpUtils.closeConnect(ftpClient);
					}
				} else if ("General".equalsIgnoreCase(mode)) {
					if (josnObj.getBytes(urlField.replace(".", "_0_")) != null) {
						ResponseUtils.download(request, response, josnObj.getBytes(urlField.replace(".", "_0_")),
								"downlownfile");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
	}

	/**
	 * 本地化文件
	 * 
	 * @param request
	 * @param response
	 * @param mode
	 * @param id
	 * @param rid
	 * @param keyField
	 * @param urlField
	 * @param ftpId
	 */
	@RequestMapping(params = "method=localFile")
	@ResponseBody
	public byte[] localFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("rp") String ids,
			@RequestParam("id") String rid, @RequestParam("databaseId") String dbid) {
		User user = RequestUtils.getUser(request);
		InputStream fis = null;
		try {
			// 检测sql 是否有sql注入
			String checkSql = "^(.+)\\sand\\s(.+)|(.+)\\sor(.+)\\s$";
			if (Pattern.matches(checkSql, ids) || ids == null || ids.contains("+") || ids.contains(" ")) {
				return null;
			}
			Map<String, String> ruleMap = getRuleMap(rid);
			String model = ruleMap.get("mode");
			if (!"FTP".equalsIgnoreCase(model)) {
				return null;
			}

			// 检测当前文件是否已经存在在附件表中，如果存在则直接返回id
			String dataSourceSet = ruleMap.get("dataSourceSet");
			JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
			JSONObject datasourceSetJSONObject = null;
			if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
				datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			}
			if (datasourceSetJSONObject == null) {
				return null;
			}
			JSONArray tables = datasourceSetJSONObject.getJSONArray("tables");
			String table = tables.getString(0);
			FormAttachmentQuery formAttachmentQuery = new FormAttachmentQuery();
			String[] idAry = ids.split(",");
			List<String> businesses = new ArrayList<String>();
			for (String ida : idAry) {
				businesses.add(table + "-" + ida);
			}
			formAttachmentQuery.setBusinesses(businesses);
			List<FormAttachment> attas = formAttachmentService.listNotContent(formAttachmentQuery);

			JSONObject retObj = new JSONObject();
			String retIds = "";
			String retNames = "";
			if (attas != null && !attas.isEmpty()) {
				for (FormAttachment formAttachment : attas) {
					retIds += formAttachment.getId() + ",";
					retNames += formAttachment.getFileName() + ",";
				}
				retObj.put("ids", retIds.lastIndexOf(",") == -1 ? "" : retIds.substring(0, retIds.lastIndexOf(",")));
				retObj.put("name",
						retNames.lastIndexOf(",") == -1 ? "" : retNames.substring(0, retNames.lastIndexOf(",")));
				return retObj.toJSONString().getBytes("UTF-8");
			}

			String FTPField = ruleMap.get("FTPField"); // 保存字段
			// [{"name":"文件名称",datas:{idField:'',ftpField
			// : ''}]
			String pathColumn = null;
			String idColumn = null;
			JSONArray fields = JSON.parseArray(FTPField);
			if (fields != null && !fields.isEmpty()) {
				JSONObject filedObj = fields.getJSONObject(0);
				JSONObject datas = filedObj.getJSONObject("datas");
				idColumn = datas.getString("idField");
				pathColumn = datas.getString("ftpField");
			}

			for (String id : idAry) {
				// 构建sql start
				DataSetBuilder builder = new DataSetBuilder();
				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("HttpServletRequest", request);

				JSONObject metadata = dataSetService.getDataSetMetadata(id), mapping;
				if (metadata != null) {
					mapping = metadata.getJSONObject("mapping");
					idColumn = Global.getOriginalColumnName(mapping, idColumn);
				}

				Query query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"),
						idColumn.replace("_0_", ".") + "='" + InjectUtils.escapeSql(id) + "'", "", parameter);
				String sql = query.toSql();
				// 构建sql end
				List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();
				// 数据源ID
				Long databaseId = datasourceSetJSONObject.getLong("databaseId");
				// 切换数据源
				Long dbidl = Long.parseLong(dbid);
				if (dbidl != 0) {
					databaseId = dbidl;
				}

				// 执行查询
				dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
				JSONObject josnObj = new JSONObject();
				if (dataMaps.size() > 0) {
					for (Map<String, Object> map : dataMaps) {
						Set<String> keys = map.keySet();
						Iterator<String> iterator = keys.iterator();
						while (iterator.hasNext()) {
							String key = (String) iterator.next();
							josnObj.put(key, map.get(key) != null ? map.get(key).toString() : "");
						}
						String full_path = josnObj.getString(pathColumn);

						String FTPServer = ruleMap.get("FTPServer"); // 服务器ID
						Long serverId = null;
						JSONArray servers = JSON.parseArray(FTPServer);
						if (servers != null && !servers.isEmpty()) {
							com.alibaba.fastjson.JSONObject server = servers.getJSONObject(0);
							serverId = server.getLong("serverId");
						}

						if (full_path == null || serverId == null) {
							break;
						}

						// FTP
						LoginContext loginContext = RequestUtils.getLoginContext(request);
						FTPClient ftpClient = null;
						String path = full_path.replaceAll("ftp://(\\w*\\.?)*(:\\w*)?/", "");
						try {
							ServerEntity server = serverEntityService.getServerEntityById(serverId);
							if (server != null) {
								if (StringUtils.isNotEmpty(server.getPerms())) {
									boolean hasPermission = false;
									List<String> perms = StringTools.split(server.getPerms());
									if (perms != null && !perms.isEmpty()) {
										for (String perm : perms) {
											if (loginContext.getPermissions().contains(perm)) {
												hasPermission = true;
												break;
											}
										}
									}
									if (!hasPermission) {
										break;
									}
								}
								String key = server.getKey();
								String pwd = server.getPassword();
								pwd = SecurityUtils.decode(key, pwd);
								ftpClient = FtpUtils.connectServer(server.getHost(), server.getPort(), server.getUser(),
										pwd);
								String remoteFile = server.getPath() + "/" + path;
								byte[] bytes = FtpUtils.getBytes(ftpClient, remoteFile);
								String filename = StringTools.replaceIgnoreCase(path, "/", "_");
								FormAttachment formAttachment = new FormAttachment();
								formAttachment.setFileName(filename);
								formAttachment.setParent("-1");
								formAttachment.setType(this.to_db);
								formAttachment.setFileSize(bytes.length);
								formAttachment.setFileContent(bytes);
								formAttachment.setStatus(0);
								formAttachment.setCreateBy(user.getActorId());
								formAttachment.setCreateDate(new Date());
								formAttachment.setBusiness(table + "-" + id);
								formAttachmentService.save(formAttachment);
								// ResponseUtils.download(request, response,
								// bytes,
								// filename);
								retIds += formAttachment.getId() + ",";
								retNames += filename + ",";
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						} finally {
							FtpUtils.closeConnect(ftpClient);
						}
					}
				}
			}
			retObj.put("ids", retIds.lastIndexOf(",") == -1 ? "" : retIds.substring(0, retIds.lastIndexOf(",")));
			retObj.put("name", retNames.lastIndexOf(",") == -1 ? "" : retNames.substring(0, retNames.lastIndexOf(",")));
			return retObj.toJSONString().getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
		return null;
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	@RequestMapping(params = "method=remove")
	public void remove(HttpServletRequest request, HttpServletResponse response, @RequestParam("from") String from,
			@RequestParam("id") String id) {
		FormAttachment attachment = formAttachmentService.getFormAttachmentById(id);
		if (to_db.equals(attachment.getType())) {
			// 文件保存至数据库，直接删除记录即可
			this.formAttachmentService.deleteById(attachment.getId());
		} else if (to_dir.equals(attachment.getType())) {
			// 文件保存至服务器目录，删除记录后还要删除文件
			this.formAttachmentService.deleteById(attachment.getId());
			String projectpath = request.getSession().getServletContext().getRealPath("/");
			if (new File(projectpath + attachment.getSaveServicePath()).exists()) {
				new File(projectpath + attachment.getSaveServicePath()).delete();
			}
		}
	}

	@RequestMapping(params = "method=removeByUUID")
	public void removeByUUID(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("from") String from, @RequestParam("id") String id) {
		FormAttachmentQuery query = new FormAttachmentQuery();
		query.setParent(id);
		List<FormAttachment> attachments = formAttachmentService.list(query);
		StringBuffer names = new StringBuffer("");
		for (FormAttachment attachment : attachments) {
			// FormAttachment attachment =
			// formAttachmentService.getFormAttachmentById(Long.parseLong(id));
			names.append(attachment.getFileName() + ",");
			if (to_db.equals(attachment.getType())) {
				// 文件保存至数据库，直接删除记录即可
				this.formAttachmentService.deleteById(attachment.getId());
			} else if (to_dir.equals(attachment.getType())) {
				// 文件保存至服务器目录，删除记录后还要删除文件
				this.formAttachmentService.deleteById(attachment.getId());
				String projectpath = request.getSession().getServletContext().getRealPath("/");
				if (new File(projectpath + attachment.getSaveServicePath()).exists()) {
					new File(projectpath + attachment.getSaveServicePath()).delete();
				}
			}
		}
	}

	@Resource
	public void setFormAttachmentService(IFormAttachmentService formAttachmentService) {
		this.formAttachmentService = formAttachmentService;
	}

	@Resource
	public void setServerEntityService(IServerEntityService serverEntityService) {
		this.serverEntityService = serverEntityService;
	}

	@Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

	@Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@RequestMapping(params = "method=upload")
	@ResponseBody
	public byte[] upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile mFile) throws Exception {
		String randomparent = request.getParameter("randomparent");
		String attachmentId = request.getParameter("attachmentId");
		String to = request.getParameter("to");
		String type = request.getParameter("type");

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		StringBuffer names = new StringBuffer("");
		JSONObject result = new JSONObject();
		try {
			FormAttachment atta = null;
			if (StringUtils.isNotEmpty(attachmentId)) {
				atta = formAttachmentService.getFormAttachmentById(attachmentId);
				atta.setUpdateBy(loginContext.getActorId());
				atta.setUpdateDate(new Date());
			} else {
				List<FormAttachment> list = formAttachmentService.getFormAttachmentByParentNotContent(randomparent);
				if (list != null && list.size() > 0) {
					atta = list.get(0);
					atta.setUpdateBy(loginContext.getActorId());
					atta.setUpdateDate(new Date());
				} else {
					atta = new FormAttachment();
					atta.setCreateBy(loginContext.getActorId());
					atta.setCreateDate(new Date());
				}
				if (type.equals("image")) {
					this.formAttachmentService.deleteByParent(randomparent);
					atta.setId(null);
				}
			}
			atta.setParent(randomparent);
			atta.setFileName(mFile.getOriginalFilename());
			atta.setFileSize(mFile.getSize());
			atta.setVersion("");
			atta.setStatus(0);
			names.append(mFile.getOriginalFilename());

			if ("to_db".equalsIgnoreCase(to)) {
				// 保存到数据库
				atta.setType(to_db);
				atta.setFileContent(mFile.getBytes());
				atta.setSaveServicePath(null);
				atta = this.formAttachmentService.save(atta);
			} else if ("to_dir".equalsIgnoreCase(to)) {
				// 保存到服务器目录
				String separator = File.separator;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String projectpath = request.getSession().getServletContext().getRealPath("/");
				String dirpath = "/upload/" + sdf.format(new Date());

				String fileName = mFile.getOriginalFilename();
				String stuffix = fileName.substring(fileName.lastIndexOf("."));

				sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String newFileName = sdf.format(new Date()) + stuffix;

				String fullFilepath = projectpath + dirpath + separator + newFileName;
				String saveServicePath = dirpath + "/" + newFileName;

				if (!new File(projectpath + dirpath).exists()) {
					new File(projectpath + dirpath).mkdirs();
				}

				if (!new File(fullFilepath).exists()) {
					new File(fullFilepath).createNewFile();
				}

				// byte[] buf = new byte[1024];
				// int len = 0;

				// BufferedOutputStream bos = new BufferedOutputStream(new
				// FileOutputStream(new File(fullFilepath)));
				// InputStream is = mFile.getInputStream();
				// while ((len = is.read(buf)) != -1) {
				// bos.write(buf, 0, len);
				// }
				// bos.close();

				FileUtils.save(fullFilepath, mFile.getInputStream());

				atta.setType(to_dir);
				atta.setFileContent(null);

				atta.setSaveServicePath(saveServicePath);
				atta = this.formAttachmentService.save(atta);
			}

			result.put("names", names);
			result.put("attachmentId", atta.getId());
			result.put("statusCode", 200);
			result.put("message", "ok");

		} catch (Exception e) {
			result.put("statusCode", -100);
			result.put("message", "error");
			result.put("error", e.getMessage());
			e.printStackTrace();
		}

		return result.toString().getBytes("utf-8");
	}

}
