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

package com.glaf.datamgr.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

public class FastJsonUtils {

	public static JSONObject addLowerCaseKey(JSONObject jsonObject) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Iterator<Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			dataMap.put(key.toLowerCase(), value);
		}
		Set<Entry<String, Object>> entrySet = dataMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (jsonObject.get(key) == null) {
				jsonObject.put(key, value);
			}
		}
		return jsonObject;
	}

	private FastJsonUtils() {

	}

}
