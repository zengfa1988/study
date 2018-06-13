package com.study.hadoop.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * 服务上下线监听服务端
 * 服务上线注册zk
 * @author Administrator
 *
 */
public class ListenService {

	private static final String connectString = "hadoop01:2181,hadoop02:2181,hadoop03:2181";// zookeeper地址
	private static final int sessionTimeout = 2000;// session超时时间,ms
	private ZooKeeper zk = null;
	private static final String groupNode = "/myServer";
	private static final String subNode = "node";
	
	
	public static void main(String[] args) throws Exception{
		ListenService listenService = new ListenService();
		listenService.connet();
		listenService.registerService(args[0]);
		listenService.handle();

	}
	
	public void connet() throws Exception {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
			}
		});
	}
	
	public void registerService(String address) throws Exception{
		if(!existNode(groupNode)){
			zk.create(groupNode, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		String path = zk.create(groupNode+"/"+subNode, address.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(address+"注册成功,create:"+path);
	}
	
	public boolean existNode(String path) throws Exception {
		Stat stat = zk.exists(path, null);
		return stat == null ? false : true;
	}
	
	/**
     * server的工作逻辑写在这个方法中
     * @throws InterruptedException
     */
    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

}
