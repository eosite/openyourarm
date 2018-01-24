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

package com.glaf.base.test;

import java.util.List;

import org.junit.Test;
import com.glaf.test.AbstractTest;
import com.glaf.base.modules.sys.model.CellTreedotPer;
import com.glaf.base.modules.sys.query.CellTreedotPerQuery;
import com.glaf.base.modules.sys.service.ICellTreedotPerService;
 

public class CellTreedotPerTest extends AbstractTest {

	protected ICellTreedotPerService cellTreedotPerService;

	@Test
	public void testList() {
		cellTreedotPerService = super.getBean("cellTreedotPerService");
		CellTreedotPerQuery query = new CellTreedotPerQuery();
		//query.nameLike("工程");
		List<CellTreedotPer> rows = cellTreedotPerService
				.getCellTreedotPersByQueryCriteria(0, 10, query);
		for (CellTreedotPer row : rows) {
			cellTreedotPerService.save(row);
			logger.debug(row.toJsonObject().toJSONString());
		}
	}

}
