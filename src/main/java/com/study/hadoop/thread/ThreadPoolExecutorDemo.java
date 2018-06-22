package com.study.hadoop.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorDemo {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for(int i=0;i<10;i++){
			WorkerThread work = new WorkerThread(""+i);
			executor.execute(work);
		}
		executor.shutdown();
		while(!executor.isTerminated()){
		}
		System.out.println("Finished all threads");
	}

}
