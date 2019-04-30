package com.study.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 异步操作结果,中间等待几秒
 * @author Thinkpad
 *
 */
public class AsyncWaitTest {

	public static void async() throws Exception{
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Map<String,Object>> future = executor.submit(new Callable<Map<String,Object>>() {
			@Override
			public Map<String, Object> call() throws Exception {
				Thread.sleep(1000*1);
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("name", "admin");
				return data;
			}
		});
		executor.shutdown();
		long s = System.currentTimeMillis();
		boolean waitResult = executor.awaitTermination(5, TimeUnit.SECONDS);
		Map<String,Object> data = null;
		if(!waitResult) {
			data = new HashMap<String,Object>();
		}else {
			data = future.get();
		}
		long e = System.currentTimeMillis();
		System.out.println("执行时间："+(e-s)/1000+"s");
		System.out.println(data);
	}
	
	public static void main(String[] args) {
		try {
			async();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
