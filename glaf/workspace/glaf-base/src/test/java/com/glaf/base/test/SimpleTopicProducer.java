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

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SimpleTopicProducer {
	public static final String DEFAULT_TOPIC = "one.two.three.four";

	public static void main(String[] args) {
		try {

			// String uri = (args.length > 0) ? args[0] : "amqp://localhost";
			String topic = (args.length > 1) ? args[1] : DEFAULT_TOPIC;
			String exchange = (args.length > 2) ? args[2] : null;
			String message = (args.length > 3) ? args[3] : "the time is " + new java.util.Date().toString();

			ConnectionFactory connFactory = new ConnectionFactory();
			// cfconn.setUri(uri);
			connFactory.setHost(TestConstants.HOST);
			connFactory.setPort(5672);
			connFactory.setUsername(TestConstants.USER);
			connFactory.setPassword(TestConstants.PWD);
			Connection conn = connFactory.newConnection();

			Channel ch = conn.createChannel();

			if (exchange == null) {
				exchange = "amq.topic";
			} else {
				ch.exchangeDeclare(exchange, "fanout");
			}

			System.out.println("Sending to exchange " + exchange + ", topic " + topic);
			ch.basicPublish(exchange, topic, null, message.getBytes());
			ch.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("Main thread caught exception: " + e);
			e.printStackTrace();
			//System.exit(1);
		}
	}
}
