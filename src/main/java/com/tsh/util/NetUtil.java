package com.tsh.util;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetUtil {

	/**
	 * 检测本机端口是否被使用,true为已使用
	 * @param port
	 * @return
	 */
	public static boolean isLocalPortUsing(int port){
		boolean flag = true;
		flag = isPortUsing("0.0.0.0", port);
		if(!flag){
			//运行在WebLogic之上的应用它的端口为8406
			//8406绑定的内部IP地址为本机的IP地址192.168.1.101
			//这时通过如上0.0.0.0的方式得到的结果为这个端口没有被使用
			//原因在于通过ServerSocket建立的连接绑定的IP为0.0.0.0
			try {
				flag = isPortUsing(InetAddress.getLocalHost().getHostAddress(), port);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 检测主机端口是否被使用,true为已使用
	 * @param host	主机地址,如192.168.1.103
	 * @param port
	 * @return
	 */
	public static boolean isPortUsing(String host,int port){
		boolean flag = false;
		try{
			InetAddress theAddress = InetAddress.getByName(host);
			//new Socket操作后只是对目标端口进行了监听，并非去使用目标端口；所以如果可以正常创建Socket，则可以证明主机上的目标端口已经被使用（并非此Socket使用的）；反之则证明这个端口并没有程序使用
			Socket socket = new Socket(theAddress, port);
			flag = true;
			socket.close();
		}catch(Exception e){
			
		}
		return flag;
	}
	
	public static void main(String[] args) {
		System.out.println(isLocalPortUsing(80));
	}
}
