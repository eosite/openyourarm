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

package com.glaf.base.modules.sys.helper;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.glaf.base.helper.TreeHelper;
import com.glaf.base.modules.sys.model.CellTreedot;
import com.glaf.base.modules.sys.model.ITree;
import com.glaf.core.util.StringTools;

public class CellTreeHelper {

	public String buildKendoTreeScripts(List<CellTreedot> treedots) {
		String text = "[]";
		List<ITree> treeModels = new ArrayList<ITree>();
		if (treedots != null && !treedots.isEmpty()) {
			for (CellTreedot tree : treedots) {
				treeModels.add(tree);
			}
		}
		TreeHelper theeHelper = new TreeHelper();
		JSONArray array = theeHelper.getTreeJSONArray(treeModels, true);
		if (array != null) {
			text = array.toJSONString();
			text = StringTools.replaceIgnoreCase(text, "children", "items");
		}
		return text;
	}

	public JSONArray buildTree(List<CellTreedot> treedots) {
		JSONArray array = null;
		List<ITree> treeModels = new ArrayList<ITree>();
		if (treedots != null && !treedots.isEmpty()) {
			for (CellTreedot tree : treedots) {
				treeModels.add(tree);
			}
		}
		TreeHelper theeHelper = new TreeHelper();
		array = theeHelper.getTreeJSONArray(treeModels, true);
		return array;
	}

}
