/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.glaf.datamgr.jdbc;

import com.alibaba.druid.sql.SQLUtils;

/**
 * @author Clinton Begin
 */
public class SQL extends AbstractSQL<SQL> {

	@Override
	public SQL getSelf() {
		return this;
	}

	/**
	 * sql format
	 * 
	 * @param sql
	 * @return
	 */
	public static String format(String sql) {

		try {

			Class<?> cls = Class.forName("org.hibernate.jdbc.util.BasicFormatterImpl");

			Object object = cls.newInstance();

			sql = cls.getDeclaredMethod("format", String.class).invoke(object, sql).toString();

		} catch (Exception ex) {
			sql = SQLUtils.formatSQLServer(sql);
		}

		return sql.trim();
	}

}
