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

import java.util.Date;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.glaf.core.domain.SysDataLog;
import com.glaf.test.AbstractTest;

public class MyBatisBatchTest extends AbstractTest {

	@Test
	public void testInsert() {
		SqlSessionFactory sqlSessionFactory = super
				.getBean("sqlSessionFactory");
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,
					false);
			long id = System.currentTimeMillis();
			for (int i = 0; i < 10000; i++) {
				SysDataLog log = new SysDataLog();
				log.setId(id + i);
				log.setActorId("test");
				log.setCreateTime(new Date());
				log.setIp("192.168.0.12");
				log.setOperate("insert");
				log.setContent("Test Insert");
				sqlSession.insert("insertSysDataLog", log);
				if (i == 9999) {
					// throw new RuntimeException("throw exception");
				}
			}
			sqlSession.commit();
		} catch (Exception ex) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

}
