/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.matrix.data.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.glaf.matrix.data.domain.UpdateEntity;

public class XmlReader {

	public List<UpdateEntity> read(java.io.InputStream inputStream) {
		List<UpdateEntity> tableModels = new ArrayList<UpdateEntity>();
		SAXReader xmlReader = new SAXReader();
		try {
			Document doc = xmlReader.read(inputStream);
			Element root = doc.getRootElement();
			List<?> elements = root.elements("update");
			if (elements != null) {
				Iterator<?> iterator = elements.iterator();
				while (iterator.hasNext()) {
					Element element = (Element) iterator.next();
					UpdateEntity model = new UpdateEntity();
					model.setId(element.attributeValue("id"));
					model.setSystemName(element.attributeValue("systemName"));
					model.setUpdateSql(element.getStringValue());
					String intervalSeconds = element.attributeValue("intervalSeconds");
					if (StringUtils.isNumeric(intervalSeconds)) {
						model.setIntervalSeconds(Integer.parseInt(intervalSeconds));
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return tableModels;
	}

}
