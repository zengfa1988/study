package com.study.hadoop.thread;

public class SynchronizedTest {

	public static void main(String[] args) {
		SynchronizedTest o = new SynchronizedTest();
		SynchronizedTest o2 = new SynchronizedTest();
		MyThread myThread1 = new MyThread(o);
		MyThread myThread2 = new MyThread(o2);

		myThread1.start();
		myThread2.start();
	}
	
	public static class MyThread extends Thread{

		private Object lock;
		public MyThread(Object lock){
			this.lock = lock;
		}
		
		@Override
		public void run() {
			synchronized (lock) {
				for(int i=0;i<10;i++){
					System.out.println(Thread.currentThread().getName()+"输出"+i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}

}
