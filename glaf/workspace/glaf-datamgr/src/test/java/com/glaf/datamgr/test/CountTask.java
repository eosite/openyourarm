package com.glaf.datamgr.test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {
	private static final long serialVersionUID = -3611254198265061729L;

	public static final int threshold = 2;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		ForkJoinPool forkjoinPool = new ForkJoinPool();

		// 生成一个计算任务，计算1+2+3+4
		CountTask task = new CountTask(1, 10000);

		// 执行一个任务
		Future<Long> result = forkjoinPool.submit(task);

		try {
			System.out.println(result.get());
		} catch (Exception e) {
			System.out.println(e);
		}
		long ts = System.currentTimeMillis() - start;
		System.out.println("time(ms):" + ts);
	}

	private int start;

	private int end;

	public CountTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long sum = 0;

		// 如果任务足够小就计算任务
		boolean canCompute = (end - start) <= threshold;
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum += i;
			}
		} else {
			// 如果任务大于阈值，就分裂成两个子任务计算
			int middle = (start + end) / 2;
			CountTask leftTask = new CountTask(start, middle);
			CountTask rightTask = new CountTask(middle + 1, end);

			// 执行子任务
			leftTask.fork();
			rightTask.fork();

			// 等待任务执行结束合并其结果
			long leftResult = leftTask.join();
			long rightResult = rightTask.join();

			// 合并子任务
			sum = leftResult + rightResult;

		}

		return sum;
	}

}