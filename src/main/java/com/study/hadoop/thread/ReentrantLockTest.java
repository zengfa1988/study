package com.study.hadoop.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Lock lock2 = new ReentrantLock();
		MyThread3 myThread1 = new MyThread3(lock);
		MyThread3 myThread2 = new MyThread3(lock);

		myThread1.start();
		myThread2.start();
	}
	
	public static class MyThread extends Thread{

		private Lock lock;
		public MyThread(Lock lock){
			this.lock = lock;
		}
		
		@Override
		public void run() {
			lock.lock();
			try {
				for(int i=0;i<10;i++){
					System.out.println(Thread.currentThread().getName()+"输出"+i);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
			System.out.println(Thread.currentThread().getName()+"结束");
		}
		
	}
	
	public static class MyThread2 extends Thread{
		private Lock lock;
		public MyThread2(Lock lock){
			this.lock = lock;
		}
		
		@Override
		public void run() {
			if (lock.tryLock()) {
				try {
					for(int i=0;i<10;i++){
						System.out.println(Thread.currentThread().getName()+"输出"+i);
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
			}
			System.out.println(Thread.currentThread().getName()+"结束");
		}
	}
	
	public static class MyThread3 extends Thread{
		private Lock lock;
		public MyThread3(Lock lock){
			this.lock = lock;
		}
		
		@Override
		public void run() {
			
			try {
				if (lock.tryLock(1000*3,TimeUnit.MILLISECONDS)){
					try {
						for(int i=0;i<10;i++){
							System.out.println(Thread.currentThread().getName()+"输出"+i);
							Thread.sleep(1000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						lock.unlock();
					}
				}
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			System.out.println(Thread.currentThread().getName()+"结束");
		}
	}

}
