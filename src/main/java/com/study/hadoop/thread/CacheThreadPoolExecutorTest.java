package com.study.hadoop.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可缓存线程池
 * @author zengfa
 *
 */
public class CacheThreadPoolExecutorTest {

	public static void main(String[] args) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		
		for (int i = 0; i < 10; i++) {
			final int index = i;
			cachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(index);
				}
			});
		}
	}
	
	
}
