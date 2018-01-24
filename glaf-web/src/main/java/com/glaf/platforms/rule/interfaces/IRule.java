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

package com.glaf.platforms.rule.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormRule;

/**
 * 基础通用属性接口
 * 
 * 
 */
public interface IRule extends Serializable {
	/**
	 * 封装好的数据源信息
	 * 
	 * @param source
	 */
	public void setSource(Map<String, String> source);

	/**
	 * 整个页面规则
	 * 
	 * @param formRules
	 */
	public void setFormRules(List<FormRule> formRules);

	void setFormComponents(List<FormComponent> formComponents);

	/**
	 * 标签元素 和getElementHtml()方法相同 需要使用getElementHtml()方法 请返回该方法为null
	 * 
	 * @return
	 */
	public String getElementTagName();

	/**
	 * 根据HTML 和getElementTagName()方法相同 需要此方法 请设getElementTagName方法返回值为null
	 * 
	 * @return
	 */
	public String getElementHtml();

	/**
	 * 规则表ID
	 * 
	 * @return
	 */
	public String getRuleId();
	/**
	 * 控件ID
	 * @return
	 */
	public String getId();
	/**
	 * 控件名称
	 * @return
	 */
	public String getName();
	/**
	 * 控件对应的值
	 * @return
	 */
	public String getValue();
	/**
	 * 控件是否显示
	 * @return
	 */
	public boolean isReadable();
	/**
	 * 控件是否为必填
	 * @return
	 */
	public boolean isRequired();
	/**
	 * 控件是否可写
	 * @return
	 */
	public boolean isWritable();

}
