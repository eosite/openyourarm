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
package com.glaf.form.core.util;

import java.io.IOException;

public class XmlResult {

	public static byte[] success(String message) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("<response>");
		buffer.append("\n    <statusCode>200</statusCode>");
		buffer.append("\n    <message>").append(message).append("</message>");
		buffer.append("\n</response>");
		try {
			return buffer.toString().getBytes("UTF-8");
		} catch (IOException ex) {
			return buffer.toString().getBytes();
		}
	}

	public static byte[] fault(String errorCode, String message) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("<response>");
		buffer.append("\n    <statusCode>500</statusCode>");
		buffer.append("\n    <errorCode>").append(errorCode)
				.append("</errorCode>");
		buffer.append("\n    <message>").append(message).append("</message>");
		buffer.append("\n</response>");
		try {
			return buffer.toString().getBytes("UTF-8");
		} catch (IOException ex) {
			return buffer.toString().getBytes();
		}
	}

}