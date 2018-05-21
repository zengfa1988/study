package com.study.hadoop.rpc;

import java.io.IOException;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Server;

public class MyServer {

	public static String IPAddress = "127.0.0.1";
	public static int PORT = 5432;
	
	public static void main(String[] args) throws HadoopIllegalArgumentException, IOException {
		MyProxy myProxy = new MyProxy();
		Configuration conf = new Configuration();
		RPC.Builder build = new RPC.Builder(conf);
		build.setBindAddress(IPAddress).setPort(PORT)
			.setProtocol(IProxyProtocol.class)
			.setInstance(myProxy);
		
		Server server = build.build();
		server.start();
		
		System.out.println("Server 启动了");
	}
}
