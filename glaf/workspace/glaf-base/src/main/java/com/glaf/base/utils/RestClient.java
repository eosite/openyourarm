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

package com.glaf.base.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.core.security.DESUtils;

public class RestClient {
	protected static final Log logger = LogFactory.getLog(RestClient.class);

	public class RestForm extends LinkedMultiValueMap<Object, Object> {

		private static final long serialVersionUID = 1L;

	}

	public static void main(String[] args) throws Exception {
		RestClient client = new RestClient();
		RestForm form = client.newRestForm();
		form.add("token", "e53aa86fd0884dcea00a670a420745c1");
		form.add("srcsystem", "WM_ZLGL");
		String text = restTemplate.postForObject("http://171.221.203.82:9000/portal/rest/rest/dumy/getSecretKey", form,
				String.class);
		logger.debug(text);
	}

	private final static RestTemplate restTemplate = new RestTemplate();

	public String decrypt(String token, String input) {
		JSONObject json = this.getSecretKeyJSON(token);
		String secretIv = json.getString("secretIv");
		String secretKey = json.getString("secretKey");
		return new String(DESUtils.decrypt3DES(input.getBytes(), secretKey.getBytes(), secretIv.getBytes()));
	}

	public JSONObject getSecretKeyJSON(String token) {
		BaseDataManager manager = BaseDataManager.getInstance();
		BaseDataInfo url = manager.getBaseData("third_getSecretKeyUrl", "login_param");
		BaseDataInfo srcsystem = manager.getBaseData("third_srcsystem", "login_param");
		logger.debug(url);
		logger.debug(srcsystem);
		RestForm form = newRestForm();
		form.add("token", token);
		form.add("srcsystem", srcsystem.getValue());
		String text = this.post(url.getValue(), form);
		logger.debug(text);
		return JSON.parseObject(text);
	}

	public RestForm newRestForm() {
		return new RestForm();
	}

	public String post(String url, RestForm form) {
		logger.debug("url:" + url);
		return restTemplate.postForObject(url, form, String.class);
	}

}
