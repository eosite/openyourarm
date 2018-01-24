// Copyright (c) 2007-Present Pivotal Software, Inc.  All rights reserved.
//
// This software, the RabbitMQ Java client library, is triple-licensed under the
// Mozilla Public License 1.1 ("MPL"), the GNU General Public License version 2
// ("GPL") and the Apache License version 2 ("ASL"). For the MPL, please see
// LICENSE-MPL-RabbitMQ. For the GPL, please see LICENSE-GPL2.  For the ASL,
// please see LICENSE-APACHE2.
//
// This software is distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND,
// either express or implied. See the LICENSE file for specific language governing
// rights and limitations of this software.
//
// If you have any questions regarding licensing, please contact us at
// info@rabbitmq.com.

package com.glaf.base.test;

import com.glaf.core.mq.rabbitmq.PublishProducer;
import com.glaf.core.util.DateUtils;

public class SimpleTopicProducer2 {

	public static void main(String[] args) throws Exception {
		PublishProducer producer = new PublishProducer("amq.fanout", "LoginQueue");
		producer.setHost(TestConstants.HOST);
		producer.setPort(5672);
		producer.setUsername(TestConstants.USER);
		producer.setPassword(TestConstants.PWD);
		for (int i = 0; i < 10; i++) {
			try {
				producer.sendBytesMessage("fanout", (DateUtils.getNowYearMonthDayHHmmss() + " Test Msg").getBytes());
				producer.close();
				System.out.println("Message sent.");
				Thread.sleep(500);
			} catch (Exception e) {
				System.err.println("Main thread caught exception: " + e);
				e.printStackTrace();
				// System.exit(1);
			}
		}
		producer.closeConnection();
	}
}
