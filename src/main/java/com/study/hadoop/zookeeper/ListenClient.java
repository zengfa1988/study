package com.study.hadoop.zookeeper;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ListenClient {

	private static final String connectString = "hadoop01:2181,hadoop02:2181,hadoop03:2181";// zookeeper地址
	private static final int sessionTimeout = 2000;// session超时时间,ms
	private ZooKeeper zk = null;
	private static final String groupNode = "/myServer";
	private List<String> serverList  = null;
	
	public static void main(String[] args) throws Exception {
		ListenClient listenClient = new ListenClient();
		listenClient.connet();
		listenClient.getServiceList();
		listenClient.handle();
	}
	
	public void connet() throws Exception {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if(event.getType() == Event.EventType.NodeChildrenChanged && (groupNode).equals(event.getPath())){
					try {
						getServiceList();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 更新server列表
	 * @throws Exception
	 */
	public void getServiceList() throws Exception{
		List<String> newServerList = new ArrayList<String>();
		List<String> nodeList = zk.getChildren(groupNode, true);
		for (String node : nodeList) {
			byte[] data = zk.getData(groupNode+"/"+node,false,null);
			newServerList.add(new String(data));
		}
		serverList = newServerList;
		System.out.println("服务列表:\n"+serverList);
	}
	
	/**
	 * client的工作逻辑写在这个方法中
	 * @throws Exception
	 */
	public void handle() throws Exception{
		System.out.println("处理业务");
		Thread.sleep(Long.MAX_VALUE);
	}
}
