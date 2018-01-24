/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.datamgr.website.springmvc;

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

import com.glaf.core.base.DataFile;
import com.glaf.core.query.BlobItemQuery;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.Dom4jUtils;
import com.glaf.core.util.GZIPUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.web.AuthHelper;
import com.glaf.datamgr.domain.DataExport;
import com.glaf.datamgr.domain.DownloadHistory;
import com.glaf.datamgr.query.DownloadHistoryQuery;
import com.glaf.datamgr.service.DataExportService;
import com.glaf.datamgr.service.DownloadHistoryService;

@Controller("/public/export")
@RequestMapping("/public/export")
public class DataExportController {

	protected static final Log logger = LogFactory.getLog(DataExportController.class);

	protected static ConcurrentMap<String, AtomicInteger> accessCounter = new ConcurrentHashMap<String, AtomicInteger>();

	protected AuthHelper authHelper = new AuthHelper();

	protected DownloadHistoryService downloadHistoryService;

	protected DataExportService dataExportService;

	protected IBlobService blobService;

	public DataExportController() {

	}

	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	public byte[] changeStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		if (StringUtils.isNotEmpty(userId)) {
			increaseCounter(userId);
			try {
				authHelper.checkToken(request, response);
				String historyId = request.getParameter("historyId");
				if (StringUtils.isNotEmpty(historyId)) {
					DownloadHistory history = downloadHistoryService.getDownloadHistory(historyId);
					if (history != null) {
						history.setStatus(RequestUtils.getInt(request, "status", 0));
						downloadHistoryService.updateDownloadHistory(history);
					}
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
				String gzip = request.getParameter("gzip");
				if (StringUtils.isNotEmpty(id)) {
					DataFile dataFile = blobService.getBlobById(id);
					if (dataFile != null && dataFile.getFilename() != null) {
						byte[] data = blobService.getBytesByFileId(dataFile.getFileId());
						String filename = dataFile.getFilename();
						if (StringUtils.equals(gzip, "gz")) {
							byte[] gz = GZIPUtils.zip(data);
							filename = dataFile.getFilename() + ".gz";
							ResponseUtils.download(request, response, gz, filename);
						} else {
							ResponseUtils.download(request, response, data, filename);
						}
						DownloadHistory history = new DownloadHistory();
						history.setDownloadTime(new Date());
						history.setFileId(id);
						history.setFilename(filename);
						history.setUserId(userId);
						history.setIp(RequestUtils.getIPAddress(request));
						downloadHistoryService.save(history);
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

		count.incrementAndGet();
		accessCounter.put(userId, count);
	}

	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		if (StringUtils.isNotEmpty(userId)) {
			increaseCounter(userId);

			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("Envelope");
			Element body = root.addElement("Body");
			Element rows = body.addElement("Rows");

			try {
				authHelper.checkToken(request, response);
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");

				DataExport dataExport = dataExportService.getDataExportByUserId(userId);

				if (dataExport != null) {
					BlobItemQuery query = new BlobItemQuery();
					query.type("export");
					query.setServiceKey(dataExport.getServiceKey());
					query.setBusinessKey(dataExport.getServiceKey() + "_" + dataExport.getDatabaseId());

					List<DataFile> dataFiles = blobService.getBlobList(query);

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

					ResponseUtils.download(request, response, Dom4jUtils.getBytesFromDocument(doc, "UTF-8"),
							"result.xml");
				}

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
	public void setDataExportService(DataExportService dataExportService) {
		this.dataExportService = dataExportService;
	}

	@javax.annotation.Resource
	public void setDownloadHistoryService(DownloadHistoryService downloadHistoryService) {
		this.downloadHistoryService = downloadHistoryService;
	}

}
