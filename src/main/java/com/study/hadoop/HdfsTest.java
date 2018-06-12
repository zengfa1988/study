package com.study.hadoop;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HdfsTest {

	private static final String HDFS = "hdfs://192.168.103.35:9000";
	
	private static final Configuration conf = new Configuration();
	
	static{
		conf.setBoolean("dfs.support.append", true);
		conf.set("dfs.replication", "1");
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
//		HdfsTest.put("E:\\study\\setting\\settings.xml", "/user/zengfa/");
		HdfsTest.cat("/user/zengfa");
//		HdfsTest.mkDir("/user/test");
//		HdfsTest.delete("/user/test");
//		HdfsTest.append("/user/zengfa", "appendTest");
	}
	
	/**
	 * 肯遇到问题：
	 * 1，Permission denied: user=***, access=WRITE, inode="staging":root:supergroup:rwxr-xr-x 
	 * 		修改hdfs-site.xml配置文件，找到dfs.permissions属性修改为false（默认为true）
	 * 2，Cannot create file/user/zengfa. Name node is in safe mode
	 * 		据资料是说hdfs刚刚启动，还在验证和适配，所以进入安全模式，等一会儿就好了
	 * @param localPathStr
	 * @param remotePathStr
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static void put(String localPathStr,String remotePathStr) throws IOException, URISyntaxException{
		FileSystem fs = FileSystem.get(new URI(HDFS), conf);
		System.out.println("Put :" + localPathStr + " To : " + remotePathStr);
		fs.copyFromLocalFile(new Path(localPathStr), new Path(remotePathStr));
		fs.close();
	}
	
	private static void cat(String pathStr) throws IOException, URISyntaxException{
		FileSystem fs = FileSystem.get(new URI(HDFS), conf);
		Path path = new Path(pathStr);
		System.out.println("Cat: " + pathStr);
		FSDataInputStream fsdis = fs.open(path);
		IOUtils.copyBytes(fsdis, System.out, conf, false);
		IOUtils.closeStream(fsdis);
		fs.close();
	}
	
	private static void mkDir(String dirStr) throws IOException, URISyntaxException{
		FileSystem fs = FileSystem.get(new URI(HDFS), conf);
		System.out.println("Create dir:" + dirStr);
		Path path = new Path(dirStr);
		fs.mkdirs(path);
		fs.close();
	}
	
	private static void delete(String pathStr) throws IOException, URISyntaxException {
		FileSystem fs = FileSystem.get(new URI(HDFS), conf);
		System.out.println("Delete :" + pathStr);
		Path path = new Path(pathStr);
		fs.delete(path,true);
		fs.close();
	}
	
	public static void append(String pathStr, String content) throws URISyntaxException, IOException {
		FileSystem fs = FileSystem.get(new URI(HDFS), conf);
		System.out.println("Append " + content + " to " + pathStr);
		Path p = new Path(pathStr);
		FSDataOutputStream out = fs.append(p);
		try {
			InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
			IOUtils.copyBytes(in, out, 4096,false);
		}finally {
			IOUtils.closeStream(out);
			fs.close();
		}
	}
}
