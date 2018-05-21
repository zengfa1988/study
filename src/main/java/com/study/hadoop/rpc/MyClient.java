package com.study.hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

/**
 * 参考https://my.oschina.net/u/2503731/blog/661216
 * @author zengfa
 *
 */
public class MyClient {

	public static void main(String[] args) throws IOException {
		InetSocketAddress inetSocketAddress = new InetSocketAddress(
                MyServer.IPAddress, MyServer.PORT);
		
		Configuration conf = new Configuration();
		IProxyProtocol proxy = RPC.getProxy(IProxyProtocol.class, IProxyProtocol.versionID, inetSocketAddress, conf);
		int num = proxy.add(1, 2);
		System.out.println(num);
		RPC.stopProxy(proxy);
	}
}
