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

package com.glaf.core.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.ConfigFactory;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.UUID32;

public class ConfigTest {

	protected static Configuration conf = BaseConfiguration.create();

	protected long start = 0L;

	@Before
	public void setUp() throws Exception {
		System.out.println("开始测试..................................");
		System.setProperty("config.path", ".");
		conf.setBoolean("distributed.config.enabled", true);
		start = System.currentTimeMillis();
		System.out.println(SystemProperties.getConfigRootPath());
	}

	@After
	public void tearDown() throws Exception {
		long times = System.currentTimeMillis() - start;
		System.out.println("总共耗时(毫秒):" + times);
		System.out.println("测试完成。");
	}

	@Test
	public void testGet() {
		System.out.println("------------------------get0-------------------");
		ConfigFactory.getString("test_cfg", "cache_0");
		System.out.println("------------------------get1-------------------");
		for (int i = 0; i < 10; i++) {
			System.out.println(ConfigFactory.getString("test_cfg", "cache_" + i));
		}
	}
	
	@Test
	public void testGet2() {
		System.out.println("------------------------get0-------------------");
		ConfigFactory.getString("test_cfg", "cache_0");
		System.out.println("------------------------get1-------------------");
		for (int i = 0; i < 10; i++) {
			System.out.println(ConfigFactory.getString("test_cfg", "cache_" + i));
		}
	}

	@Test
	public void testPut() {
		System.out.println("------------------------put-------------------");
		for (int i = 0; i < 10000; i++) {
			ConfigFactory.put("test_cfg", "cache_" + i, "value_" + i + "#" + UUID32.getUUID());
			if (i % 500 == 0) {
				System.out.println(ConfigFactory.getString("test_cfg", "cache_" + i));
			}
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(ConfigFactory.getString("test_cfg", "cache_" + i));
		}
	}

	@Test
	public void testRemove() {
		ConfigFactory.clear("test_cfg");
		for (int i = 0; i < 10; i++) {
			System.out.println(ConfigFactory.getString("test_cfg", "cache_" + i));
		}
	}

}
