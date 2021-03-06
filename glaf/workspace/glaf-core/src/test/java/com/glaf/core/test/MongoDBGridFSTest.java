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

import java.io.*;

import com.glaf.core.factory.MongodbContainer;
import com.mongodb.gridfs.GridFS;

public class MongoDBGridFSTest {

	public static void save(File path, GridFS gridFS) {
		File contents[] = path.listFiles();
		if (contents != null) {
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()) {
					System.out.println("read file:"+contents[i].getAbsolutePath());
					MongoDBGridFSThread command = new MongoDBGridFSThread(
							contents[i], gridFS);
					com.glaf.core.util.ThreadFactory.run(command);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				} else {
					save(contents[i], gridFS);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		String dbname = "gridfs";
		// MongoDatabase db =
		// MongodbContainer.getInstance().getDatabase(dbname);
		GridFS gridFS = new GridFS(
				MongodbContainer.getInstance().getDB(dbname), "files");
		save(new File(args[0]), gridFS);
		long time = System.currentTimeMillis() - start;
		System.out.println("times:" + time);
	}

}
