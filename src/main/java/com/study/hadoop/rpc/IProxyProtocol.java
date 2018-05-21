package com.study.hadoop.rpc;

public interface IProxyProtocol{

	long versionID = 1l;//该字段必须要有，不然会报java.lang.NoSuchFieldException: versionID异常
	
	int add(int number1,int number2);
}
