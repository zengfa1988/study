package com.study.hadoop.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 指定工作线程数量的线程池
 * @author zengfa
 *
 */
public class FixedThreadPoolExecutorTest {

	public static void main(String[] args) {
		int num = Runtime.getRuntime().availableProcessors();
		System.out.println(num);
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(num);
		for (int i = 0; i < 10; i++) {
			final int index = i;
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(index);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
