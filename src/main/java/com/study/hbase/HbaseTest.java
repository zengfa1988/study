package com.study.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseTest {

	private static Configuration conf = null;
	static{
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "ha01,ha02,ha03");
	}
	
	public static void main(String[] args) {
		try {
//			createTable();
			testPut();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testPut() throws Exception{
		HTable table = new HTable(conf, Bytes.toBytes("emp"));
		Put put = new Put(Bytes.toBytes("2"));
		put.add(Bytes.toBytes("personal data"), Bytes.toBytes("city"), Bytes.toBytes("chenzhou"));
		table.put(put);
		table.close();
	}
	
	public static void createTable() throws Exception{
		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor td = new HTableDescriptor("account");
		HColumnDescriptor family = new HColumnDescriptor("info");
		family.setMaxVersions(5);
		td.addFamily(family);
		admin.createTable(td);
		admin.close();
		
	}

}
