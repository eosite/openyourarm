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

package com.glaf.datamgr.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.core.util.IOUtils;
import com.glaf.datamgr.domain.EntitySegment;

public class MapperParseThread extends Thread {
	protected static final Log logger = LogFactory.getLog(MapperParseThread.class);

	protected File file;

	protected List<EntitySegment> list;

	protected AtomicInteger counter;

	public MapperParseThread(File file, List<EntitySegment> list, AtomicInteger counter) {
		this.file = file;
		this.list = list;
		this.counter = counter;
	}

	public void run() {
		SAXReader xmlReader = new SAXReader();
		xmlReader.setIgnoreComments(true);
		xmlReader.setValidation(false);
		logger.debug("parse xml mapper:" + file.getName());
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();

			if (root != null) {
				String namespace = root.attributeValue("namespace");
				logger.debug("namespace:" + namespace);
				List<?> insertElements = root.elements("insert");
				if (insertElements != null && !insertElements.isEmpty()) {
					Iterator<?> iterator = insertElements.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						EntitySegment model = new EntitySegment();
						model.setNamespace(namespace);
						model.setId(namespace + "." + elem.attributeValue("id"));
						model.setOperation("insert");
						model.setParameterType(elem.attributeValue("parameterType"));
						list.add(model);
					}
				}

				List<?> updateElements = root.elements("update");
				if (updateElements != null && !updateElements.isEmpty()) {
					Iterator<?> iterator = updateElements.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						EntitySegment model = new EntitySegment();
						model.setNamespace(namespace);
						model.setId(namespace + "." + elem.attributeValue("id"));
						model.setOperation("update");
						model.setParameterType(elem.attributeValue("parameterType"));
						list.add(model);
					}
				}

				List<?> deleteElements = root.elements("delete");
				if (deleteElements != null && !deleteElements.isEmpty()) {
					Iterator<?> iterator = deleteElements.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						EntitySegment model = new EntitySegment();
						model.setNamespace(namespace);
						model.setId(namespace + "." + elem.attributeValue("id"));
						model.setOperation("delete");
						model.setParameterType(elem.attributeValue("parameterType"));
						list.add(model);
					}
				}

				List<?> selectElements = root.elements("select");
				if (selectElements != null && !selectElements.isEmpty()) {
					Iterator<?> iterator = selectElements.iterator();
					while (iterator.hasNext()) {
						Element elem = (Element) iterator.next();
						EntitySegment model = new EntitySegment();
						model.setNamespace(namespace);
						model.setId(namespace + "." + elem.attributeValue("id"));
						model.setOperation("select");
						model.setParameterType(elem.attributeValue("parameterType"));
						model.setResultType(elem.attributeValue("resultType"));
						list.add(model);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			counter.incrementAndGet();
			IOUtils.closeStream(inputStream);
		}
	}
}
