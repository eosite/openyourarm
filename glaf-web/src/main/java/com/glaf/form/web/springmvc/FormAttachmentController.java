package com.glaf.form.web.springmvc;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.utils.ParamUtil;
import com.glaf.bim.BimUpload;
import com.glaf.bim.BimUtils;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.factory.RedisFileStorageFactory;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.excel.ExcelUpload;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.query.FormAttachmentQuery;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.IFormAttachmentService;
import com.glaf.form.core.util.FormAttachmentJsonFactory;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.core.util.OcrHelper;
import com.glaf.form.rule.util.GisExcellImportUtils;

import sun.misc.BASE64Decoder;

@Controller("/form/attachment")
@RequestMapping("/form/attachment")
public class FormAttachmentController {

	protected static final Log logger = LogFactory.getLog(FormAttachmentController.class);

	protected IFormAttachmentService formAttachmentService;

	@Autowired
	protected FormRulePropertyService formRulePropertyService;
	@Autowired
	protected MutilDatabaseBean mutilDatabaseBean;
	@Autowired
	protected ExcelUpload excelUpload;

	protected static String to_db = "1";

	protected static String to_dir = "0";

	@Resource
	public void setFormAttachmentService(IFormAttachmentService formAttachmentService) {
		this.formAttachmentService = formAttachmentService;
	}

	public FormAttachmentController() {

	}
	
	@RequestMapping("/OrcData")
	@ResponseBody
	public byte[] getDataByOrc(HttpServletRequest request, HttpServletResponse response)throws UnsupportedEncodingException {
		JSONObject ret = new JSONObject();
		String attachmentId = RequestUtils.getParameter(request, "attachmentId");
		String msg="";
		
		//应用实例ID
		String baseId = ParamUtil.getParameter(request, "baseId");
		//模板ID
		String tmpId = ParamUtil.getParameter(request, "tmpId");
		if(StringUtils.isNotEmpty(attachmentId)){
			byte[] imageData = null;
			FormAttachment atta = formAttachmentService.getCachedFormAttachmentNotContentById(attachmentId);
			if (to_db.equals(atta.getType())) {
				//数据库中
				imageData = this.getFileContent(atta);
//				byte[] imageData = this.getFileContent(atta);
//				InputStream input = new ByteArrayInputStream(imageData);
//				InputStream[] isAry = {input};
//				OCRHelper.aliOcrIdCard(isAry);
			}else if (to_dir.equals(atta.getType())) {
				//直接目录，可以直接进行图文识别
				String dir = request.getSession().getServletContext().getRealPath("/");
				String filePath = atta.getSaveServicePath();
				Path file = Paths.get(dir, filePath);
				try {
					if(Files.exists(file)){
						imageData = Files.readAllBytes(file);
					}
//					return OCRHelper.recognizeText(file.toFile()).toJSONString().getBytes("UTF-8");
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					logger.error(sw.toString());
					msg = sw.toString();
				}
			}
			if(imageData != null){
				OcrHelper ocrHelper = ContextFactory.getBean("ocrHelper");
				ret = ocrHelper.aliOrcIdCard(imageData, tmpId, baseId);
				return ret.toJSONString().getBytes("UTF-8");
//				OcrHelper ocrHelper = ContextFactory.getBean("ocrHelper");
//				ret.put("status", "200");
//				ret.put("data",ocrHelper.aliOrcIdCard(imageData, tmpId, baseId));
//				return ret.toJSONString().getBytes("UTF-8");
			}else{
				msg = "无法获取文件信息";
			}
		}else{
			msg = "无法找到该文件，请确认该文件已上传!";
		}
		ret.put("status", "500");
		ret.put("msg",msg);
		return ret.toJSONString().getBytes("UTF-8");
	}
	

	@RequestMapping(params = "method=getFilesByRandomParent")
	@ResponseBody
	public byte[] getFilesByRandomParent(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("randomParent") String randomParent) throws UnsupportedEncodingException {

		if (randomParent != null && randomParent.length() > 0) {
			List<FormAttachment> list = formAttachmentService.getFormAttachmentByParentNotContent(randomParent);

			JSONArray results = FormAttachmentJsonFactory.listToArray(list);

			return results.toJSONString().getBytes("utf-8");
		} else {
			return null;
		}
	}

	@RequestMapping(params = "method=getFilesByRandomParentOTemplate")
	@ResponseBody
	public byte[] getFilesByRandomParentOTemplate(HttpServletRequest request, HttpServletResponse response
	/* @RequestParam("randomParent") String randomParent */) throws UnsupportedEncodingException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String randomParent = String.valueOf(params.get("randomParent"));
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (randomParent != null && randomParent.length() > 0) {
			FormAttachmentQuery query = new FormAttachmentQuery();
			query.setParent(randomParent);

			int pageSize = Integer.valueOf(String.valueOf(params.get("page")));
			int number = Integer.valueOf(String.valueOf(params.get("number")));
			int count = (pageSize - 1) * number + 1;
			List<FormAttachment> list = formAttachmentService.getFormAttachmentByQueryCriteria(count, number, query);
			int total = formAttachmentService.getFormAttachmentCountByQueryCriteria(query);
			JSONArray results = FormAttachmentJsonFactory.listToArray(list);
			jsonObject.put("total", total);
			jsonObject.put("rows", results);
			return jsonObject.toJSONString().getBytes("utf-8");
		} else {
			return null;
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
	public static BufferedImage getMinImage(Image image, double comBase, double scale) {
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
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param base64Str
	 *            base64编码字符串
	 * @param path
	 *            图片路径-具体到文件
	 * @return
	 */
	public static byte[] convertToImageByBase64(byte[] base64) {
		String base64Str = new String(base64);
		if (base64Str == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(base64Str);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}

	@RequestMapping(params = "method=upload")
	@ResponseBody
	public byte[] upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("to") String to,
			@RequestParam("file") List<MultipartFile> mFiles, ModelMap modelMap) throws IOException {
//		LoginContext loginContext = RequestUtils.getLoginContext(request);
//
//		String randomParent = request.getParameter("randomParent");
//		String fileId = request.getParameter("fieldId"); // 获取文件ID
//		// int needMin = RequestUtils.getInt(request, "needMin"); //是否需要压缩
//		String bim = request.getParameter("bim");
//		String excel = request.getParameter("excel");
//		String bimRuleId = request.getParameter("brid");
//		Map map = new HashMap();
//		JSONArray bimAry = new JSONArray();
//		boolean isSuccess = true;
//		JSONArray result = new JSONArray();
//		for (MultipartFile mFile : mFiles) {
//			FormAttachment atta = new FormAttachment();
//			try {
//				if (fileId != null && !fileId.isEmpty()) {
//					atta.setId(fileId);
//				}
//				atta.setParent(randomParent);
//
//				atta.setFileSize(mFile.getSize());
//				atta.setVersion("");
//				atta.setStatus(0);
//				atta.setCreateBy(loginContext.getActorId());
//				atta.setCreateDate(new Date());
//
//				// 判断是否是图片
//				boolean isImage = false;
//				BufferedImage bufferImage = null;
//				ByteArrayOutputStream imageoutTh = null;
//				String contentType = mFile.getContentType();
//
//				String uploadfileName = mFile.getOriginalFilename();
//				byte[] fileContent;
//				if (uploadfileName.indexOf("?base64") != -1) {
//					// 为base64位编码结构,进行转换
//					fileContent = convertToImageByBase64(mFile.getBytes());
//					uploadfileName = uploadfileName.replace("?base64", "");
//				} else {
//					fileContent = mFile.getBytes();
//				}
//
//				atta.setFileName(uploadfileName);
//
//				if (contentType.indexOf("image") != -1) {
//					isImage = true;
//					// 图片默认进行压缩
//					double comBase = RequestUtils.getDouble(request, "comBase", 100d);// 获取压缩基数
//					double scale = RequestUtils.getDouble(request, "scale", 1d);// 获取缩放比例
//					bufferImage = getMinImage(ImageIO.read(new ByteArrayInputStream(fileContent)), comBase, scale);
//					imageoutTh = new ByteArrayOutputStream();
//					ImageIO.write(bufferImage, "JPEG", imageoutTh);
//				}
//				if (contentType.indexOf("video") != -1) {
//					// 视频文件只能保存在服务器目录
//					to = "to_dir";
//				}
//				if ("to_db".equalsIgnoreCase(to)) {
//					// 保存到数据库
//					atta.setType(to_db);
//					atta.setFileContent(fileContent);
//					atta.setSaveServicePath(null);
//					if (isImage) {
//						// 图片默认保存缩略图
//						atta.setFilenameThu(uploadfileName);
//						atta.setFilesizeThu(imageoutTh.size());
//						atta.setFilecontentThu(imageoutTh.toByteArray());
//					}
//					atta = this.formAttachmentService.save(atta);
//
//				} else if ("to_dir".equalsIgnoreCase(to)) {
//					// 保存到服务器目录
//					String separator = File.separator;
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//					String projectpath = request.getSession().getServletContext().getRealPath("/");
//					String dirpath = "/upload/" + sdf.format(new Date());
//
//					String fileName = uploadfileName;
//					String stuffix = fileName.substring(fileName.lastIndexOf("."));
//
//					sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//					String newfileNameOrg = sdf.format(new Date());
//					String newFileName = newfileNameOrg + stuffix;
//
//					String fullFilepath = projectpath + dirpath + separator + newFileName;
//					String saveServicePath = dirpath + "/" + newFileName;
//
//					if (!new File(projectpath + dirpath).exists()) {
//						new File(projectpath + dirpath).mkdirs();
//					}
//
//					if (!new File(fullFilepath).exists()) {
//						new File(fullFilepath).createNewFile();
//					}
//
//					// byte[] buf = new byte[1024];
//					// int len = 0;
//					//
//					// BufferedOutputStream bos = new BufferedOutputStream(
//					// new FileOutputStream(new File(fullFilepath)));
//					// InputStream is = mFile.getInputStream();
//					// while ((len = is.read(buf)) != -1) {
//					// bos.write(buf, 0, len);
//					// }
//					// bos.close();
//
//					FileUtils.save(fullFilepath, mFile.getInputStream());
//
//					if (isImage) {
//						// 图片默认保存缩略图
//						String newFileNameThu = newfileNameOrg + "_min" + ".jpeg";
//
//						String fullFilepathThu = projectpath + dirpath + separator + newFileNameThu;
//						String saveServicePathThu = dirpath + "/" + newFileNameThu;
//
//						if (!new File(projectpath + dirpath).exists()) {
//							new File(projectpath + dirpath).mkdirs();
//						}
//
//						if (!new File(fullFilepathThu).exists()) {
//							new File(fullFilepathThu).createNewFile();
//						}
//
//						FileUtils.save(fullFilepathThu, imageoutTh.toByteArray());
//
//						atta.setFilepathThu(saveServicePathThu);
//						atta.setFilenameThu(uploadfileName);
//						atta.setFilesizeThu(imageoutTh.size());
//					}
//
//					atta.setType(to_dir);
//					atta.setFileContent(null);
//
//					atta.setSaveServicePath(saveServicePath);
//					atta = this.formAttachmentService.save(atta);
//				}
//
//				// 执行bim操作
//				if (StringUtils.isNotEmpty(bim)) {
//					BimUpload bimUpload = new BimUpload();
//					JSONObject bimReObj = bimUpload.transformMain(mFile.getInputStream(), mFile.getOriginalFilename());
//
//					JSONObject bimObj = new JSONObject();
//					bimObj.put("urn", bimReObj.getString("urn"));
//					bimObj.put("name", atta.getFileName());
//					bimObj.put("formId", atta.getParent());
//					bimObj.put("attrId", atta.getId());
//					bimAry.add(bimObj);
//				}
//
//				// 执行excel操作
//				if (StringUtils.isNotEmpty(excel)) {
//					String ruleid = request.getParameter("ruleid");
//					Map<String, String> ruleMap = getRuleMap(ruleid);// 获取所有规则
//					String excelRuleData = request.getParameter("excelRuleData");
//					ruleMap.put("excelRuleData", excelRuleData);
//					User user = RequestUtils.getUser(request);
//					Map<String, Object> dataMap = excelUpload.readExcel(mFile.getInputStream(), ruleMap,
//							mutilDatabaseBean, user, mFile.getOriginalFilename());
//					List<?> errorData = null;
//					if (dataMap != null) {
//						errorData = (ArrayList) dataMap.get("errorData");
//					}
//					if (errorData != null && errorData.size() > 0) {
//						result.add(errorData);
//					} else {
//						result.add("false");
//					}
//
//					result.add(dataMap.get("count"));
//				}
//
//			} catch (Exception e) {
//				StringWriter sw = new StringWriter();
//				PrintWriter pw = new PrintWriter(sw);
//				e.printStackTrace(pw);
//
//				// e.printStackTrace();
//				logger.error(sw.toString());
//				logger.error(e.getMessage());
//				logger.info("文件：" + atta.getFileName() + "上传失败！");
//				atta = new FormAttachment();
//				atta.setError("上传失败！");
//				isSuccess = false;
//			} finally {
//				if (isSuccess) {
//					result.add(FormAttachmentJsonFactory.toJsonObject(atta));
//				}
//			}
//		}
//		if (StringUtils.isNotEmpty(bim) && isSuccess) {
//			Map<String, String> ruleMap = getRuleMap(bimRuleId);// 获取所有规则
//			BimUtils bimUtils = new BimUtils();
//			try {
//				bimUtils.saveSource(request, ruleMap, bimAry, mutilDatabaseBean);
//			} catch (Exception e) {
//				e.printStackTrace();
//				FormAttachment atta = new FormAttachment();
//				atta.setError("BIM转换失败！");
//				result.add(FormAttachmentJsonFactory.toJsonObject(atta));
//			}
//		}

		
		return	upload(request, response, to, mFiles);
		
	//	return result.toJSONString().getBytes("UTF-8");

	}

	public static byte[] upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("to") String to,
			@RequestParam("file") List<MultipartFile> mFiles) throws IOException {

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		String randomParent = request.getParameter("randomParent");
		String fileId = request.getParameter("fieldId"); // 获取文件ID
		// int needMin = RequestUtils.getInt(request, "needMin"); //是否需要压缩
		String bim = request.getParameter("bim");
		String excel = request.getParameter("excel");
		String bimRuleId = request.getParameter("brid");
		Map map = new HashMap();
		JSONArray bimAry = new JSONArray();
		boolean isSuccess = true;
		JSONArray result = new JSONArray();

		// FormRulePropertyService formRulePropertyService =
		// ContextFactory.getBean("formRulePropertyService");

		IFormAttachmentService formAttachmentService = ContextFactory.getBean("formAttachmentService");

		MutilDatabaseBean mutilDatabaseBean = ContextFactory.getBean("mutilDatabaseBean");

		ExcelUpload excelUpload = ContextFactory.getBean("excelUpload");

		for (MultipartFile mFile : mFiles) {
			FormAttachment atta = new FormAttachment();
			try {
				if (fileId != null && !fileId.isEmpty()) {
					atta.setId(fileId);
				}
				atta.setParent(randomParent);

				atta.setFileSize(mFile.getSize());
				atta.setVersion("");
				atta.setStatus(0);
				
				if(loginContext!=null)
					atta.setCreateBy(loginContext.getActorId());
				atta.setCreateDate(new Date());

				// 判断是否是图片
				boolean isImage = false;
				BufferedImage bufferImage = null;
				ByteArrayOutputStream imageoutTh = null;
				String contentType = mFile.getContentType();

				String uploadfileName = mFile.getOriginalFilename();
				byte[] fileContent;
				if (uploadfileName.indexOf("?base64") != -1) {
					// 为base64位编码结构,进行转换
					fileContent = convertToImageByBase64(mFile.getBytes());
					uploadfileName = uploadfileName.replace("?base64", "");
				} else {
					fileContent = mFile.getBytes();
				}

				atta.setFileName(uploadfileName);

				if (contentType.indexOf("image") != -1) {
					isImage = true;
					// 图片默认进行压缩
					double comBase = RequestUtils.getDouble(request, "comBase", 100d);// 获取压缩基数
					double scale = RequestUtils.getDouble(request, "scale", 1d);// 获取缩放比例
					bufferImage = getMinImage(ImageIO.read(new ByteArrayInputStream(fileContent)), comBase, scale);
					imageoutTh = new ByteArrayOutputStream();
					ImageIO.write(bufferImage, "JPEG", imageoutTh);
				}
				if (contentType.indexOf("video") != -1) {
					// 视频文件只能保存在服务器目录
					to = "to_dir";
				}
				if ("to_db".equalsIgnoreCase(to)) {
					// 保存到数据库
					atta.setType(to_db);
					atta.setFileContent(fileContent);
					atta.setSaveServicePath(null);
					if (isImage) {
						// 图片默认保存缩略图
						atta.setFilenameThu(uploadfileName);
						atta.setFilesizeThu(imageoutTh.size());
						atta.setFilecontentThu(imageoutTh.toByteArray());
					}
					atta = formAttachmentService.save(atta);

				} else if ("to_dir".equalsIgnoreCase(to)) {
					// 保存到服务器目录
					String separator = File.separator;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String projectpath = request.getSession().getServletContext().getRealPath("/");
					String dirpath = "/upload/" + sdf.format(new Date());

					String fileName = uploadfileName;
					String stuffix = fileName.substring(fileName.lastIndexOf("."));

					sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					String newfileNameOrg = sdf.format(new Date());
					String newFileName = newfileNameOrg + stuffix;

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
					//
					// BufferedOutputStream bos = new BufferedOutputStream(
					// new FileOutputStream(new File(fullFilepath)));
					// InputStream is = mFile.getInputStream();
					// while ((len = is.read(buf)) != -1) {
					// bos.write(buf, 0, len);
					// }
					// bos.close();

					FileUtils.save(fullFilepath, mFile.getInputStream());

					if (isImage) {
						// 图片默认保存缩略图
						String newFileNameThu = newfileNameOrg + "_min" + ".jpeg";

						String fullFilepathThu = projectpath + dirpath + separator + newFileNameThu;
						String saveServicePathThu = dirpath + "/" + newFileNameThu;

						if (!new File(projectpath + dirpath).exists()) {
							new File(projectpath + dirpath).mkdirs();
						}

						if (!new File(fullFilepathThu).exists()) {
							new File(fullFilepathThu).createNewFile();
						}

						FileUtils.save(fullFilepathThu, imageoutTh.toByteArray());

						atta.setFilepathThu(saveServicePathThu);
						atta.setFilenameThu(uploadfileName);
						atta.setFilesizeThu(imageoutTh.size());
					}

					atta.setType(to_dir);
					atta.setFileContent(null);

					atta.setSaveServicePath(saveServicePath);
					atta = formAttachmentService.save(atta);
				}

				// 执行bim操作
				if (StringUtils.isNotEmpty(bim)) {
					BimUpload bimUpload = new BimUpload();
					JSONObject bimReObj = bimUpload.transformMain(mFile.getInputStream(), mFile.getOriginalFilename());

					JSONObject bimObj = new JSONObject();
					bimObj.put("urn", bimReObj.getString("urn"));
					bimObj.put("name", atta.getFileName());
					bimObj.put("formId", atta.getParent());
					bimObj.put("attrId", atta.getId());
					bimAry.add(bimObj);
				}

				// 执行excel操作
				if (StringUtils.isNotEmpty(excel)) {
					String ruleid = request.getParameter("ruleid");
					Map<String, String> ruleMap = getRuleMap(ruleid);// 获取所有规则
					String excelRuleData = request.getParameter("excelRuleData");
					ruleMap.put("excelRuleData", excelRuleData);
					User user = RequestUtils.getUser(request);
					Map<String, Object> dataMap = excelUpload.readExcel(mFile.getInputStream(), ruleMap,
							mutilDatabaseBean, user, mFile.getOriginalFilename());
					List<?> errorData = null;
					if (dataMap != null) {
						errorData = (ArrayList) dataMap.get("errorData");
					}
					if (errorData != null && errorData.size() > 0) {
						result.add(errorData);
					} else {
						result.add("false");
					}

					result.add(dataMap.get("count"));
				}

			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);

				// e.printStackTrace();
				logger.error(sw.toString());
				logger.error(e.getMessage());
				logger.info("文件：" + atta.getFileName() + "上传失败！");
				atta = new FormAttachment();
				atta.setError("上传失败！");
				isSuccess = false;
			} finally {
				if (isSuccess) {
					result.add(FormAttachmentJsonFactory.toJsonObject(atta));
				}
			}
		}
		if (StringUtils.isNotEmpty(bim) && isSuccess) {
			Map<String, String> ruleMap = getRuleMap(bimRuleId);// 获取所有规则
			BimUtils bimUtils = new BimUtils();
			try {
				bimUtils.saveSource(request, ruleMap, bimAry, mutilDatabaseBean);
			} catch (Exception e) {
				e.printStackTrace();
				FormAttachment atta = new FormAttachment();
				atta.setError("BIM转换失败！");
				result.add(FormAttachmentJsonFactory.toJsonObject(atta));
			}
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping(params = "method=remove")
	@ResponseBody
	public byte[] remove(HttpServletRequest request, HttpServletResponse response, @RequestParam("from") String from,
			@RequestParam("fileNames") String[] fileNames) {

		String randomParent = request.getParameter("randomParent");
		String[] ids = request.getParameterValues("id");

		List<String> fileNameList = new ArrayList<String>();
		for (String s : fileNames) {
			fileNameList.add(s);
		}

		List<String> rowIds = new ArrayList<String>();
		for (String s : ids) {
			rowIds.add(s);
		}

		FormAttachmentQuery query = new FormAttachmentQuery();
		query.setParent(randomParent);
		// query.setFileNames(fileNameList);
		query.setRowIds(rowIds);
		List<FormAttachment> list = this.formAttachmentService.list(query);

		if (list != null && list.size() > 0) {

			String projectpath = request.getSession().getServletContext().getRealPath("/");
			// List<String> paths = new ArrayList<String>();// 保存文件路径
			JSONArray retAry = new JSONArray();
			for (FormAttachment atta : list) {
				retAry.add(atta);
				if (to_db.equals(atta.getType())) {
					// 文件保存至数据库，直接删除记录即可
					this.formAttachmentService.deleteById(atta.getId());
				} else if (to_dir.equals(atta.getType())) {
					// 文件保存至服务器目录，删除记录后还要删除文件
					this.formAttachmentService.deleteById(atta.getId());

					if (new File(projectpath + atta.getSaveServicePath()).exists()) {
						new File(projectpath + atta.getSaveServicePath()).delete();
					}
				}
			}
			try {
				return retAry.toJSONString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
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

	@RequestMapping(params = "method=download")
	public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
		InputStream fis = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			// 查询出无内容的附件信息，用于获取名称
			FormAttachment noContentData = this.formAttachmentService.getCachedFormAttachmentNotContentById(id);
			if (noContentData == null) {
				return;
			}

			byte[] data = getFileContentById(noContentData.getId());
			if (data != null) {
				// 直接取缓存
				ResponseUtils.download(request, response, data, noContentData.getFileName());
				return;
			}
			// 从数据库中读取
			FormAttachment atta = this.formAttachmentService.getFormAttachmentById(id);
			// 解决下载乱码问题
			String encodeFileName = URLEncoder.encode(atta.getFileName(), "UTF-8");
			if (to_db.equals(atta.getType())) {
				// 放入缓存中
				setFileContentById(atta.getId(), atta.getFileContent());

				ResponseUtils.download(request, response, atta.getFileContent(), encodeFileName);
			} else if (to_dir.equals(atta.getType())) {
				// 从服务器目录中读取
				String projectpath = request.getSession().getServletContext().getRealPath("/");
				File file = new File(projectpath + atta.getSaveServicePath());
				if (file.exists()) {
					// 获取文件的字节码放入缓存中
					setFileContentById(atta.getId(), FileUtils.getBytes(file));

					fis = new FileInputStream(file);
					ResponseUtils.download(request, response, fis, encodeFileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeStream(fis);
		}
	}

	@ResponseBody
	@RequestMapping(params = "method=getFormAttachmentByRandomParent")
	public byte[] getFormAttachmentByRandomParent(HttpServletRequest request, HttpServletResponse response) {
		try {
			String parent = RequestUtils.getString(request, "randomparent");
			FormAttachment fac = this.getFormAttachmentByRandomParent(parent);
			if (fac != null) {
				return fac.toJsonObject().toJSONString().getBytes("utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public FormAttachment getFormAttachmentByRandomParent(String parent) {
		List<FormAttachment> list = this.formAttachmentService.getFormAttachmentByParent(parent);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

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
				} catch (Exception ex) {
				}
			}
			if (data != null) {
				return data;
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

	@ResponseBody
	@RequestMapping(params = "method=getOfficeFormAttachment")
	public byte[] getOfficeFormAttachment(HttpServletRequest request, HttpServletResponse response) {
		try {
			String parent = RequestUtils.getString(request, "randomparent");
			FormAttachment fac = this.getFormAttachmentByRandomParent(parent);

			if (fac == null) {// 可能是主键id
				fac = this.formAttachmentService.getFormAttachmentById(parent);
			}

			if (fac != null) {
				return fac.toJsonObject().toJSONString().getBytes("utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(params = "method=getOfficeByRandomParent")
	public byte[] getOfficeByRandomParent(HttpServletRequest request, HttpServletResponse response) {
		try {
			String parent = RequestUtils.getString(request, "randomparent");
			FormAttachment fac = this.getFormAttachmentByRandomParent(parent);

			if (fac == null) {// 可能是主键id
				fac = this.formAttachmentService.getFormAttachmentById(parent);
			}

			if (fac != null) {
				if (to_db.equalsIgnoreCase(fac.getType())) {
					// fac =
					// this.formAttachmentService.getFormAttachmentById(fac.getId());
					fac.setFileContent(this.getFileContent(fac));
					return fac.getFileContent();
				} else {
					String serverPath = request.getSession().getServletContext().getRealPath("/");
					String path = serverPath + fac.getSaveServicePath();
					return FileUtils.getBytes(new File(path));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(params = "method=officeUpload")
	public byte[] upload(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		// Map<String, Object> params = RequestUtils.getParameterMap(request);

		String randomParent = RequestUtils.getString(request, "randomparent");

		String id = RequestUtils.getString(request, "attachmentId", null);
		if (id != null && "0".equals(id)) {
			id = null;
		}

		JSONObject result = new JSONObject();

		try {
			// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				// int maxSize = 50 * FileUtils.MB_SIZE;
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {

						// if (mFile.getSize() < maxSize) {
						FormAttachment atta = new FormAttachment();

						atta.setId(id);
						atta.setParent(randomParent);
						atta.setFileName(mFile.getOriginalFilename() + "." + RequestUtils.getString(request, "ext"));
						atta.setFileSize(mFile.getSize());
						atta.setVersion("");
						atta.setStatus(0);
						atta.setCreateBy(loginContext.getActorId());
						atta.setCreateDate(new Date());

						// }// 保存到数据库

						atta.setType("1");
						atta.setFileContent(mFile.getBytes());
						atta.setSaveServicePath(null);

						atta = this.formAttachmentService.save(atta);

						result = atta.toJsonObject();
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping(params = "method=ImageUpload")
	public byte[] uploadImage(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		// Map<String, Object> params = RequestUtils.getParameterMap(request);

		String randomParent = RequestUtils.getString(request, "randomparent");
		String uploadType = RequestUtils.getString(request, "uploadType");
		String id = RequestUtils.getString(request, "attachmentId", null);
		if (id != null && "0".equals(id)) {
			id = null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String path = sdf.format(new Date());
		String serverPath = request.getSession().getServletContext().getRealPath("/scripts/upload/img/");
		String tempath = serverPath + File.separator + path;
		// 在服务器端创建文件夹
		File file = new File(tempath);
		if (!file.exists()) {
			file.mkdir();
		}
		JSONArray jsonArray = new JSONArray();
		FileOutputStream fileout = null;
		ByteArrayInputStream in = null;
		sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		JSONObject result = new JSONObject();

		try {
			// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
				Map<String, MultipartFile> fileMap = req.getFileMap();
				Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
				// int maxSize = 50 * FileUtils.MB_SIZE;
				for (Entry<String, MultipartFile> entry : entrySet) {
					MultipartFile mFile = entry.getValue();
					if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {

						// if (mFile.getSize() < maxSize) {
						FormAttachment atta = new FormAttachment();

						atta.setId(id);
						atta.setParent(randomParent);
						atta.setFileName(mFile.getOriginalFilename());
						atta.setFileSize(mFile.getSize());
						atta.setVersion("");
						atta.setStatus(0);
						atta.setCreateBy(loginContext.getActorId());
						atta.setCreateDate(new Date());
						// }// 保存到数据库
						atta.setType("1");

						if (uploadType.equals("server")) {
							String mFileName = sdf.format(new Date());
							String type = mFile.getContentType().substring(
									mFile.getContentType().indexOf("/") + "/".length(),
									mFile.getContentType().length());
							fileout = new FileOutputStream(tempath + File.separator + mFileName + "." + type);
							IOUtils.write(mFile.getInputStream(), fileout);
							atta.setSaveServicePath("/scripts/upload/img/" + path + "/" + mFileName + "." + type);
							atta.setFileContent(null);
						} else {

							atta.setFileContent(mFile.getBytes());
							atta.setSaveServicePath(null);
						}

						atta = this.formAttachmentService.save(atta);

						result = atta.toJsonObject();
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		/*
		 * if(fileout != null) fileout.close();
		 */
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 读取gis excel导入库
	 * 
	 * @param request
	 * @param response
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=uploadByGis")
	@ResponseBody
	public byte[] uploadGis(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile mFile) throws Exception {
		String randomparent = request.getParameter("randomParent");
		String attachmentId = request.getParameter("attachmentId");
		String to = request.getParameter("to");
		String rid = request.getParameter("rid");

		LoginContext loginContext = RequestUtils.getLoginContext(request);
		StringBuffer names = new StringBuffer("");
		JSONObject result = new JSONObject();
		try {
			FormAttachment atta = new FormAttachment();
			atta.setCreateBy(loginContext.getActorId());
			atta.setCreateDate(new Date());
			atta.setParent(randomparent);
			atta.setFileName(mFile.getOriginalFilename());
			atta.setFileSize(mFile.getSize());
			atta.setVersion("");
			atta.setStatus(0);
			names.append(mFile.getOriginalFilename());

			// 保存到数据库
			atta.setType(to_db);
			atta.setFileContent(mFile.getBytes());
			atta.setSaveServicePath(null);
			atta = this.formAttachmentService.save(atta);

			Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则
			// 读取excel
			ByteArrayInputStream bis = new ByteArrayInputStream(mFile.getBytes());
			long meridian = ruleMap.get("meridian") == null ? 0 : Long.parseLong(ruleMap.get("meridian"));
			int standard = ruleMap.get("standard") == null ? 0 : Integer.parseInt(ruleMap.get("standard"));
			JSONArray ary = GisExcellImportUtils.readExcel(bis, meridian, standard);

			saveExcelSource(request, ruleMap, ary);

			result.put("names", names);
			result.put("attachmentId", atta.getId());
			result.put("statusCode", 200);
			result.put("message", "上传完成");

		} catch (Exception e) {
			result.put("statusCode", -100);
			result.put("message", "上传失败");
			result.put("error", e.getMessage());
			e.printStackTrace();
		}

		return result.toString().getBytes("utf-8");
	}

	private void saveExcelSource(HttpServletRequest request, Map<String, String> ruleMap, JSONArray ary)
			throws Exception {
		User user = RequestUtils.getUser(request);

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return;
		}

		JSONArray tablesAry = datasourceSetJSONObject.getJSONArray("selectDatasource");
		if (tablesAry == null || tablesAry.size() != 1) {
			return;
		}
		Long databaseId = tablesAry.getJSONObject(0).getLong("databaseId");

		JSONArray columnAry = datasourceSetJSONObject.getJSONArray("selectColumns");

		JSONObject firstObj = columnAry.getJSONObject(0);
		String tableName = firstObj.getString("tableName");

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(databaseId, tableName);

		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();

		ColumnDefinition idColumn = null;
		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
			}
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}

		String point = null;
		String type = null;
		String stake = null;
		String name = null;

		JSONObject columnObj = null;
		for (Object object : columnAry) {
			columnObj = (JSONObject) object;
			String ctype = columnObj.getString("ctype");
			String columnName = columnObj.getString("columnName");
			String columnNameSimple = columnName.replace(tableName + "_0_", "");
			if ("point".equalsIgnoreCase(ctype)) {
				point = columnNameSimple;
			} else if ("type".equalsIgnoreCase(ctype)) {
				type = columnNameSimple;
			} else if ("stake".equalsIgnoreCase(ctype)) {
				stake = columnNameSimple;
			} else if ("name".equalsIgnoreCase(ctype)) {
				name = columnNameSimple;
			}
		}

		ColumnDefinition column = null;
		ColumnModel colModel = null;
		JSONObject obj = null;
		for (Object object : ary) {
			obj = (JSONObject) object;

			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);

			String nextId = mutilDatabaseBean.getNextId(tableName, "id", user.getActorId(), databaseId);
			ColumnModel idCol = new ColumnModel();
			idCol.setColumnName(idColumn.getColumnName());
			idCol.setJavaType(idColumn.getJavaType());
			idCol.setValue(nextId);
			tableModel.addColumn(idCol);

			column = columnMap.get(point.toLowerCase());
			colModel = new ColumnModel();
			colModel.setColumnName(column.getColumnName());
			colModel.setJavaType(column.getJavaType());
			colModel.setValue(obj.getString("psource"));
			tableModel.addColumn(colModel);

			column = columnMap.get(type.toLowerCase());
			colModel = new ColumnModel();
			colModel.setColumnName(column.getColumnName());
			colModel.setJavaType(column.getJavaType());
			colModel.setValue(obj.getString("type"));
			tableModel.addColumn(colModel);

			column = columnMap.get(name.toLowerCase());
			colModel = new ColumnModel();
			colModel.setColumnName(column.getColumnName());
			colModel.setJavaType(column.getJavaType());
			colModel.setValue(obj.getString("name"));
			tableModel.addColumn(colModel);

			column = columnMap.get(stake.toLowerCase());
			colModel = new ColumnModel();
			colModel.setColumnName(column.getColumnName());
			colModel.setJavaType(column.getJavaType());
			colModel.setValue(obj.getString("stake"));
			tableModel.addColumn(colModel);

			try {
				mutilDatabaseBean.insertTableData(tableModel, databaseId);
			} catch (Exception e) {
				throw new Exception("save error" + e.getMessage());
			}
		}

	}

	private static Map<String, String> getRuleMap(String rid) {
		FormRulePropertyService formRulePropertyService = ContextFactory.getBean("formRulePropertyService");
		return formRulePropertyService.getRuleMap(rid);
	}

	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("中国.txt", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
