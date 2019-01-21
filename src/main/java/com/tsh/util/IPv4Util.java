package com.tsh.util;

public class IPv4Util {

	public static void main(String[] args) {
		String ip = "1.0.0.0";
//		Long ipNum = ip2int(ip);//3232235628
		Long ipNum = ipToLong(ip);
		System.out.println(ipNum);
	}
	
	/**
	 * IP转成整型
	 * @param ip
	 * @return
	 */
	public static Long ip2int(String ip) {
		Long num = 0L;
		if (ip == null){
			return num;
		}
		try{
			ip = ip.replaceAll("[^0-9\\.]", ""); //去除字符串前的空字符
			String[] ips = ip.split("\\.");
			if (ips.length == 4){
				num = Long.parseLong(ips[0], 10) * 256L * 256L * 256L + Long.parseLong(ips[1], 10) * 256L * 256L + Long.parseLong(ips[2], 10) * 256L + Long.parseLong(ips[3], 10);
				num = num >>> 0;
			}
		}catch(NullPointerException ex){
			System.out.println(ip);
		}
	    return num;
	}
	
	public static long ipToLong(String strIp) {  
        long[] ip = new long[4];  
        // 先找到IP地址字符串中.的位置  
        int position1 = strIp.indexOf(".");  
        int position2 = strIp.indexOf(".", position1 + 1);  
        int position3 = strIp.indexOf(".", position2 + 1);  
        // 将每个.之间的字符串转换成整型  
        ip[0] = Long.parseLong(strIp.substring(0, position1));  
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));  
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));  
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));  
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];  
    }  

}
