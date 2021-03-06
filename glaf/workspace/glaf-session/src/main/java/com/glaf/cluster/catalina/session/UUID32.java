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

package com.glaf.cluster.catalina.session;

import java.util.Map;
import java.util.UUID;

public class UUID32 {

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}

	public static void main(String[] args) {
		Map<String, Object> map = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		for (int i = 0; i < 10000; i++) {
			String id = UUID32.getUUID();
			System.out.println(id);
			map.put(id, id);
		}
		System.out.println(map.size());
	}

	private UUID32() {

	}
}