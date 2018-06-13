package com.study.hadoop.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZkStudy {

	private static final String connectString = "hadoop01:2181,hadoop02:2181,hadoop03:2181";// zookeeper地址
	private static final int sessionTimeout = 2000;// session超时时间,ms
	private ZooKeeper zk = null;

	public static void main(String[] args) throws Exception {
		ZkStudy zkStudy = new ZkStudy();
		zkStudy.connet();
		// zkStudy.listNode("/hadoop");
		// zkStudy.getData("/hadoop/api");
		// zkStudy.createNode("/hadoop/api", "edede");
		// zkStudy.setNodeDate("/hadoop/api", "ssss");
		// zkStudy.deleteNode("/hadoop/api");
		// zkStudy.existNode("/hadoop/api2");
		zkStudy.listNodeWatch("/hadoop");
		Thread.sleep(Integer.MAX_VALUE);
	}

	public void connet() throws Exception {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("type:" + event.getType() + ",path:" + event.getPath());
				try {
					listNodeWatch(event.getPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void listNode(String path) throws Exception {
		List<String> nodeList = zk.getChildren(path, null);
		for (String node : nodeList) {
			System.out.println(node);
		}
	}

	public void listNodeWatch(String path) throws Exception {
		List<String> nodeList = zk.getChildren(path, true);
		for (String node : nodeList) {
			System.out.println(node);
		}
	}

	public void getData(String path) throws Exception {
		/**
		 * 
		 * path - Znode路径。 watcher -
		 * 监视器类型的回调函数。当指定的znode的数据改变时，ZooKeeper集合将通过监视器回调进行通知。这是一次性通知。 stat -
		 * 返回znode的元数据。
		 */
		byte[] b = zk.getData(path, null, null);
		String data = new String(b);
		System.out.println(data);
	}

	public void createNode(String path, String content) throws Exception {
		String s = zk.create(path, content.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(s);
	}

	public void setNodeDate(String path, String content) throws Exception {
		// -1表示所有版本
		Stat stat = zk.setData(path, content.getBytes(), -1);
		System.out.println(stat);
	}

	public void existNode(String path) throws Exception {
		Stat stat = zk.exists(path, null);
		System.out.println(stat);
	}

	public void deleteNode(String path) throws Exception {
		zk.delete(path, -1);
	}

}
