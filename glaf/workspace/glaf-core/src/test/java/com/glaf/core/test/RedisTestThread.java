package com.glaf.core.test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.factory.RedisFactory;

public class RedisTestThread implements java.lang.Runnable {
	protected static final Log logger = LogFactory.getLog(RedisTestThread.class);
	static AtomicInteger counter = new AtomicInteger(0);
	static Random random = new Random();

	public AtomicInteger getCounter() {
		return counter;
	}

	public void run() {
		String key = "xx" + Math.abs(random.nextInt(99999));
		logger.info(counter.incrementAndGet() + "->" + key + "=" + RedisFactory.getInstance().getStringAsync(key));
	}

}
