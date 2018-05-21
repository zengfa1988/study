package com.study.hadoop.rpc;

/**
 * 参考https://my.oschina.net/u/2503731/blog/661216
 * @author zengfa
 *
 */
public interface IProxyProtocol{

	long versionID = 1l;//该字段必须要有，不然会报java.lang.NoSuchFieldException: versionID异常
	
	int add(int number1,int number2);
}
