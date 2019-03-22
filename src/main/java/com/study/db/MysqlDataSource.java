package com.study.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源连接池
 * @author Thinkpad
 *
 */
public class MysqlDataSource {

	private Map<String,Connection> connMap = new HashMap<String,Connection>();
	
	public Connection getConnection(String id) throws Exception{
		Connection conn = connMap.get(id);
		if(conn != null) {
			//判断连接是否可用
			//不能使用conn.isClosed(),
			//第一次调用这个方法时总是为false,
			//后面如果操作数据库,才修改为true
			if(conn.isValid(1)) {
				return conn;
			}
			//如果连接不可用,则要关闭连接
			conn.close();
			conn = null;
		}
		String url = "";
		String user = "";
		String password = "";
		conn = DriverManager.getConnection(url, user, password);
		connMap.put(id, conn);
		return conn;
	}
	
}
