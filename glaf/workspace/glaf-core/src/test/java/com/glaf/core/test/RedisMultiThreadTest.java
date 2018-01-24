package com.glaf.core.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.glaf.core.factory.RedisFactory;

public class RedisMultiThreadTest {

	protected static ExecutorService pool = Executors.newFixedThreadPool(2);

	@Test
	public void test() {
		System.setProperty("config.path", ".");
		long start = System.currentTimeMillis();
		System.out.println(RedisFactory.getInstance().getString("xx1", null));
		while (true) {
			RedisTestThread command = new RedisTestThread();
			// com.glaf.core.util.ThreadFactory.execute(command);
			pool.submit(command, null);
			if (command.getCounter().get() > 10000) {
				break;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("time:(MS)" + (System.currentTimeMillis() - start));
	}

}
