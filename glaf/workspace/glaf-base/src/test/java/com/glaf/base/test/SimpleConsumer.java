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
import com.rabbitmq.client.QueueingConsumer;

public class SimpleConsumer {
	public static void main(String[] args) {
		try {
			// String uri = (args.length > 0) ? args[0] : "amqp://localhost";
			// String queueName = (args.length > 1) ? args[1] : "SimpleQueue";

			ConnectionFactory connFactory = new ConnectionFactory();
			// connFactory.setUri(uri);
			connFactory.setHost(TestConstants.HOST);
			connFactory.setPort(5672);
			connFactory.setUsername(TestConstants.USER);
			connFactory.setPassword(TestConstants.PWD);
			String queueName = TestConstants.QNAME;
			Connection conn = connFactory.newConnection();
			System.out.println("conn:" + conn);
			final Channel ch = conn.createChannel();

			ch.queueDeclare(queueName, false, false, false, null);

			QueueingConsumer consumer = new QueueingConsumer(ch);
			ch.basicConsume(queueName, consumer);
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				System.out.println("Message: " + new String(delivery.getBody()));
				ch.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}
		} catch (Exception ex) {
			System.err.println("Main thread caught exception: " + ex);
			ex.printStackTrace();
			// System.exit(1);
		}
	}
}
