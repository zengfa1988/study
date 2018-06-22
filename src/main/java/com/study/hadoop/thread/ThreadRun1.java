package com.study.hadoop.thread;

/**
 * 
 * @author Administrator
 *
 */
public class ThreadRun1 {

	public static void main(String[] args) {
//		noShareData();
		shareData();
//		noShareDataSynchronized();
//		shareDataSynchronized();
	}
	
	/**
	 * 不共享数据的情况<br \>
	 * 可以看出每个线程都有一个属于自己的实例变量count，它们之间互不影响
	 */
	public static void noShareData(){
		MyThread a = new MyThread("A");
        MyThread b = new MyThread("B");
        MyThread c = new MyThread("C");
        a.start();
        b.start();
        c.start();
	}
	
	/**
	 * 不共享数据的情况<br \>
	 * run方法加不加synchronized关键字更上面一样
	 */
	public static void noShareDataSynchronized(){
		SynchronizedMyThread a = new SynchronizedMyThread("A");
		SynchronizedMyThread b = new SynchronizedMyThread("B");
		SynchronizedMyThread c = new SynchronizedMyThread("C");
        a.start();
        b.start();
        c.start();
	}
	
	/**
	 * 共享数据的情况
	 * 顺序混乱
	 */
	public static void shareData(){
		MyThread mythread=new MyThread();
		//下列线程都是通过mythread对象创建的
        Thread a=new Thread(mythread,"A");
        Thread b=new Thread(mythread,"B");
        Thread c=new Thread(mythread,"C");
        Thread d=new Thread(mythread,"D");
        Thread e=new Thread(mythread,"E");
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
	}
	
	/**
	 * 共享数据的情况Synchronized关键字
	 * 顺序是有序的,但只会有一个线程执行
	 */
	public static void shareDataSynchronized(){
		SynchronizedMyThread mythread=new SynchronizedMyThread();
		//下列线程都是通过mythread对象创建的
        Thread a=new Thread(mythread,"A");
        Thread b=new Thread(mythread,"B");
        Thread c=new Thread(mythread,"C");
        Thread d=new Thread(mythread,"D");
        Thread e=new Thread(mythread,"E");
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
	}

}
