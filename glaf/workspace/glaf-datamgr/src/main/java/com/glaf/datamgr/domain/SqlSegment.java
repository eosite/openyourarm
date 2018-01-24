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

package com.glaf.datamgr.domain;

public class SqlSegment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * SQL片段
	 */
	protected String segment;

	/**
	 * 片段类型(SELECT, FROM, WHERE, JOIN, GROUP BY, HAVING, ORDER BY)
	 */
	protected String type;

	/**
	 * 连接条件
	 */
	protected String connector;

	/**
	 * 循环次数
	 */
	protected int loopCount;

	public SqlSegment() {

	}

	public String getConnector() {
		return connector;
	}

	public int getLoopCount() {
		return loopCount;
	}

	public String getSegment() {
		return segment;
	}

	public String getType() {
		return type;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}

	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setType(String type) {
		this.type = type;
	}

}
