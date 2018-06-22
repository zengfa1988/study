package com.study.hadoop.thread;

public class SynchronizedMyThread extends Thread{

	private int count = 5;
	
	public SynchronizedMyThread() {
		super();
	}

    public SynchronizedMyThread(String name) {
        super();
        this.setName(name);
    }
    
    @Override
    public synchronized void run() {
        super.run();
        while (count > 0) {
            count--;
            System.out.println("由 " + SynchronizedMyThread.currentThread().getName()
                    + " 计算，count=" + count);
        }
    }
}
