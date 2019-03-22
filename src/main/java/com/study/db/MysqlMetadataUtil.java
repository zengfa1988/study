package com.study.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

public class MysqlMetadataUtil {

	static {
		try {
			Driver driver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(driver);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void listCols(Connection conn,String dbName,String tbName) throws Exception{
		if(StringUtils.isNotBlank(dbName)) {
			tbName = dbName + "." +tbName;
		}
		String sql = "show columns from "+tbName;
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		while(rs.next()) {
			String field = rs.getString(1);
			String type = rs.getString(2);
			System.out.println(field+"\t"+type);
		}
		rs.close();
		rs = null;
		stat.close();
		stat = null;
	}
	
	public static void main(String[] args) throws Exception{
		String url = "jdbc:mysql://localhost:3306/yanci";
		String user = "root";
		String password = "root";
		Connection conn = DriverManager.getConnection(url, user, password);
		listCols(conn, null, "article");
	}
}
