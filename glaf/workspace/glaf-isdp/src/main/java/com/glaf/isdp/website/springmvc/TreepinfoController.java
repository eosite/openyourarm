package com.glaf.isdp.website.springmvc;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.core.base.DataFile;
import com.glaf.core.config.Configuration;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.factory.SysLogFactory;
import com.glaf.core.query.BlobItemQuery;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.web.AuthHelper;
import com.glaf.datamgr.domain.DownloadHistory;
import com.glaf.datamgr.query.DownloadHistoryQuery;
import com.glaf.datamgr.service.DownloadHistoryService;

@Controller("/public/treepinfo")
@RequestMapping("/public/treepinfo")
public class TreepinfoController {

	protected static final Log logger = LogFactory.getLog(TreepinfoController.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static ConcurrentMap<String, AtomicInteger> accessCounter = new ConcurrentHashMap<String, AtomicInteger>();

	protected AuthHelper authHelper = new AuthHelper();

	protected DownloadHistoryService downloadHistoryService;

	protected IBlobService blobService;

	public TreepinfoController() {

	}

	private void decreaseCounter(String userId) {
		AtomicInteger count = accessCounter.get(userId);
		if (count == null) {
			count = new AtomicInteger(0);
		}
		count.decrementAndGet();
		accessCounter.put(userId, count);
	}

	@RequestMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		if (StringUtils.isNotEmpty(userId)) {
			increaseCounter(userId);
			try {
				authHelper.checkToken(request, response);
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				String id = request.getParameter("id");
				if (StringUtils.isNotEmpty(id)) {
					DataFile dataFile = blobService.getBlobById(id);
					if (dataFile != null && dataFile.getFilename() != null) {
						byte[] data = blobService.getBytesByFileId(dataFile.getFileId());
						String filename = dataFile.getFilename();
						ResponseUtils.download(request, response, data, filename);
					}
				}
			} catch (Exception ex) {
				logger.error("download error", ex);
			} finally {
				this.decreaseCounter(userId);
			}
		}
	}

	private void increaseCounter(String userId) {
		AtomicInteger count = accessCounter.get(userId);
		if (count == null) {
			count = new AtomicInteger(0);
		}
		if (count.get() > conf.getInt("client.threads", 5)) {
			throw new RuntimeException("client threads so much");
		}
		count.incrementAndGet();
		accessCounter.put(userId, count);
	}

	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String databaseId = request.getParameter("databaseId");
		String userId = request.getParameter("userId");
		if (StringUtils.isNotEmpty(userId)) {
			increaseCounter(userId);
			try {
				authHelper.checkToken(request, response);
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");

				BlobItemQuery query = new BlobItemQuery();
				query.type("export");
				query.setServiceKey("treepinfo");
				query.setBusinessKey("treepinfo_" + databaseId);

				List<DataFile> dataFiles = blobService.getBlobList(query);

				Document doc = DocumentHelper.createDocument();
				Element root = doc.addElement("Envelope");
				Element body = root.addElement("Body");
				Element rows = body.addElement("Rows");

				if (dataFiles != null && !dataFiles.isEmpty()) {
					DownloadHistoryQuery q = new DownloadHistoryQuery();
					q.setUserId(userId);
					List<DownloadHistory> list = downloadHistoryService.list(q);

					for (DataFile item : dataFiles) {
						if (list != null && !list.isEmpty()) {
							for (DownloadHistory h : list) {
								if (StringUtils.equals(item.getId(), h.getFileId())) {
									continue;
								}
							}
						}

						Element row = rows.addElement("Row");
						row.addAttribute("Id", item.getId());
						if (item.getFilename() != null) {
							row.addAttribute("Filename", item.getFilename());
						}
						if (item.getSize() != 0) {
							row.addAttribute("Size", String.valueOf(item.getSize()));
						}
						row.addAttribute("Date", String.valueOf(item.getLastModified()));
					}
				}

				ResponseUtils.download(request, response, Dom4jUtils.getBytesFromDocument(doc, "UTF-8"), "result.xml");

			} catch (Exception ex) {
				logger.error("download list error", ex);
			} finally {
				this.decreaseCounter(userId);
			}
		}
	}

	@javax.annotation.Resource
	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	@javax.annotation.Resource
	public void setDownloadHistoryService(DownloadHistoryService downloadHistoryService) {
		this.downloadHistoryService = downloadHistoryService;
	}

	@ResponseBody
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public byte[] status(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		if (StringUtils.isNotEmpty(userId)) {
			increaseCounter(userId);
			try {
				authHelper.checkToken(request, response);
				String server = request.getParameter("server");
				if (StringUtils.isNotEmpty(server)) {
					SysDataLog log = new SysDataLog();
					log.setActorId(userId);
					log.setCreateTime(new Date());
					log.setFlag(1);
					log.setIp(RequestUtils.getIPAddress(request));
					log.setModuleId("server");
					log.setTimeMS(1);
					log.setContent(server);
					SysLogFactory.getInstance().addLog(log);
				}
			} catch (Exception ex) {
				logger.error("check status error", ex);
				throw new RuntimeException("check status error", ex);
			} finally {
				this.decreaseCounter(userId);
			}
		}

		return ResponseUtils.responseXmlResult(false);
	}

}
