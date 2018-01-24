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

package com.glaf.cluster.catalina.session;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.BitSetSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyListSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptyMapSerializer;
import de.javakaffee.kryoserializers.CollectionsEmptySetSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonListSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonMapSerializer;
import de.javakaffee.kryoserializers.CollectionsSingletonSetSerializer;
import de.javakaffee.kryoserializers.CopyForIterateCollectionSerializer;
import de.javakaffee.kryoserializers.CopyForIterateMapSerializer;
import de.javakaffee.kryoserializers.DateSerializer;
import de.javakaffee.kryoserializers.EnumMapSerializer;
import de.javakaffee.kryoserializers.EnumSetSerializer;
import de.javakaffee.kryoserializers.GregorianCalendarSerializer;
import de.javakaffee.kryoserializers.JdkProxySerializer;
import de.javakaffee.kryoserializers.KryoReflectionFactorySupport;
import de.javakaffee.kryoserializers.RegexSerializer;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.URISerializer;
import de.javakaffee.kryoserializers.UUIDSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;
import io.netty.util.concurrent.FastThreadLocal;

/**
 * 基于Kryo 2.x的(序列化/反序列化)类. 可以处理任何类(包括没有实现Serializable接口的类).
 */

public class KryoSerializer implements
		com.glaf.cluster.catalina.session.Serializer {
	protected ClassLoader loader;

	private static final FastThreadLocal<Kryo> _threadLocalKryo = new FastThreadLocal<Kryo>() {
		protected Kryo initialValue() {
			Kryo kryo = new KryoReflectionFactorySupport() {

				@Override
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Serializer<?> getDefaultSerializer(final Class type) {
					if (EnumSet.class.isAssignableFrom(type)) {
						return new EnumSetSerializer();
					}
					if (EnumMap.class.isAssignableFrom(type)) {
						return new EnumMapSerializer();
					}
					if (Collection.class.isAssignableFrom(type)) {
						return new CopyForIterateCollectionSerializer();
					}
					if (Map.class.isAssignableFrom(type)) {
						return new CopyForIterateMapSerializer();
					}
					if (Date.class.isAssignableFrom(type)) {
						return new DateSerializer(type);
					}

					// 判断是否是EnhancerByCGLIB
					try {
						if (type.getName().indexOf("$$EnhancerByCGLIB$$") > 0) {
							Method method = Class
									.forName(
											"de.javakaffee.kryoserializers.cglib.CGLibProxySerializer")
									.getDeclaredMethod("canSerialize",
											Class.class);
							if ((Boolean) method.invoke(null, type)) {
								return getSerializer(Class
										.forName("de.javakaffee.kryoserializers.cglib.CGLibProxySerializer$CGLibProxyMarker"));
							}
						}
					} catch (Throwable thex) {
						// thex.printStackTrace();
					}

					return super.getDefaultSerializer(type);
				}
			};

			kryo.setClassLoader(Thread.currentThread().getContextClassLoader());
			kryo.setRegistrationRequired(false);

			kryo.register(Arrays.asList("").getClass(),
					new ArraysAsListSerializer());
			kryo.register(Collections.EMPTY_LIST.getClass(),
					new CollectionsEmptyListSerializer());
			kryo.register(Collections.EMPTY_MAP.getClass(),
					new CollectionsEmptyMapSerializer());
			kryo.register(Collections.EMPTY_SET.getClass(),
					new CollectionsEmptySetSerializer());
			kryo.register(Collections.singletonList("").getClass(),
					new CollectionsSingletonListSerializer());
			kryo.register(Collections.singleton("").getClass(),
					new CollectionsSingletonSetSerializer());
			kryo.register(Collections.singletonMap("", "").getClass(),
					new CollectionsSingletonMapSerializer());
			kryo.register(BigDecimal.class,
					new DefaultSerializers.BigDecimalSerializer());
			kryo.register(BigInteger.class,
					new DefaultSerializers.BigIntegerSerializer());
			kryo.register(Pattern.class, new RegexSerializer());
			kryo.register(BitSet.class, new BitSetSerializer());
			kryo.register(URI.class, new URISerializer());
			kryo.register(UUID.class, new UUIDSerializer());
			kryo.register(GregorianCalendar.class,
					new GregorianCalendarSerializer());
			kryo.register(InvocationHandler.class, new JdkProxySerializer());

			UnmodifiableCollectionsSerializer.registerSerializers(kryo);
			SynchronizedCollectionsSerializer.registerSerializers(kryo);

			// 动态注册JodaDateTimeSerializer
			try {
				Class<?> clazz = Class.forName("org.joda.time.DateTime");
				Serializer<?> serializer = (Serializer<?>) Class
						.forName(
								"de.javakaffee.kryoserializers.jodatime.JodaDateTimeSerializer")
						.newInstance();
				kryo.register(clazz, serializer);
			} catch (Throwable t) {

			}

			// 动态注册CGLibProxySerializer
			try {
				Class<?> clazz = Class
						.forName("de.javakaffee.kryoserializers.cglib.CGLibProxySerializer$CGLibProxyMarker");
				Serializer<?> serializer = (Serializer<?>) Class
						.forName(
								"de.javakaffee.kryoserializers.cglib.CGLibProxySerializer")
						.newInstance();
				kryo.register(clazz, serializer);
			} catch (Throwable t) {

			}

			return kryo;
		}
	};

	public KryoSerializer() {

	}

	public HttpSession deserializeInto(byte[] data, HttpSession session)
			throws IOException {
		ZooKeeperSession zooKeeperSession = (ZooKeeperSession) read(data);
		return zooKeeperSession;
	}

	public byte[] serializeFrom(HttpSession session) throws IOException {
		ZooKeeperSession zooKeeperSession = (ZooKeeperSession) session;
		return write(zooKeeperSession);
	}

	public byte[] write(Object obj) {
		return write(obj, -1);
	}

	public byte[] write(Object obj, int maxBufferSize) {
		Kryo kryo = _threadLocalKryo.get();
		Output output = new Output(1024, maxBufferSize);
		kryo.writeClassAndObject(output, obj);
		return output.toBytes();
	}

	public Object read(byte[] bytes) {
		Kryo kryo = _threadLocalKryo.get();
		Input input = new Input(bytes);
		return kryo.readClassAndObject(input);
	}

	public void setClassLoader(ClassLoader loader) {
		this.loader = loader;
	}

}