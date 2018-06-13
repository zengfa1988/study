package com.study.hadoop.zookeeper;

public class DeamonTest {

	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					System.out.println(1111);
				}
			}
		});
		//守护线程不会阻止当前线程
		t.setDaemon(true);
		t.start();
	}
}
