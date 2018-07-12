package com.study.hadoop.hdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * hdfs api操作
 * @author zengfa
 *
 */
public class HdfsTest {

	private static final String hdfsUrl = "hdfs://192.168.103.187:9000";
	private FileSystem fs;
	
	@Before
	public void getFileSystem() throws Exception{
		Configuration conf = new Configuration();
		fs = FileSystem.get(new URI(hdfsUrl), conf);//带URL参数的是分布式文件系统，fs类型为DistributedFileSystem
//		fs = FileSystem.get(conf);//不带URL参数的是本地文件系统，fs类型为LocalFileSystem
	}
	
	@Test
	public void pFileSystem(){
		System.out.println(fs);
		//分布式文件系统输出为DFS[DFSClient[clientName=DFSClient_NONMAPREDUCE_1468464288_1, ugi=hadoop (auth:SIMPLE)]]
		//本地文件系统输出为org.apache.hadoop.fs.LocalFileSystem@675c2785
	}
	
	@Test
	public void testMkdir() throws Exception{
		String path = "/user/test";
		fs.mkdirs(new Path(path));
	}
	
	@Test
	public void testUpFile() throws Exception{
		String localPathStr = "E:\\study\\hadoop\\test.txt";
		String remotePathStr = "/user/test/";
		fs.copyFromLocalFile(new Path(localPathStr), new Path(remotePathStr));
	}
	
	/**
	 * 通过输入输出流上传
	 * @throws Exception
	 */
	@Test
	public void testUpFile2() throws Exception{
		String localPathStr = "E:\\study\\hadoop\\test.txt";
		String remotePathStr = "/user/test/test2.txt";
		OutputStream out = fs.create(new Path(remotePathStr));
		InputStream input = new FileInputStream(new File(localPathStr));
		int lentth = IOUtils.copy(input, out);
		System.out.println(lentth);
	}
	
	@Test
	public void testDownFile() throws Exception{
		String localPathStr = "E:\\study\\hadoop";
		String remotePathStr = "/user/test/test.txt";
		fs.copyToLocalFile(new Path(remotePathStr), new Path(localPathStr));
	}
	
	@Test
	public void testDownFile2() throws Exception{
		String localPathStr = "E:\\study\\hadoop\\test.txt";
		String remotePathStr = "/user/test/test2.txt";
		InputStream input = fs.open(new Path(remotePathStr));
		OutputStream out = new FileOutputStream(new File(localPathStr));
		int lentth = IOUtils.copy(input, out);
		System.out.println(lentth);
	}
	
	/**
	 * recursive为true时删除目录,可以删除子目录
	 * @throws Exception
	 */
	@Test
	public void testDelDir() throws Exception{
		String remotePathStr = "/user";
		boolean result = fs.delete(new Path(remotePathStr), true);
		System.out.println(result);
	}
	
	/**
	 * 列出文件
	 * @throws Exception
	 */
	@Test
	public void testListFile() throws Exception{
		String remotePathStr = "/";
		Path path = new Path(remotePathStr);
		RemoteIterator<LocatedFileStatus> it = fs.listFiles(path, true);//recursive为false,当前文件夹下，为true，当前文件夹和子文件夹下
		while(it.hasNext()){
			LocatedFileStatus locatedFileStatus = it.next();
			System.out.println("url路径:"+locatedFileStatus.getPath().toString());
			System.out.println("fileName:"+locatedFileStatus.getPath().getName());
			System.out.println("replication:"+locatedFileStatus.getReplication());
			System.out.println("isDirectory:"+locatedFileStatus.isDirectory());
			System.out.println("--------------------------------------------");
		}
	}
	
	
	/**
	 * 列出文件和目录
	 * @throws Exception
	 */
	@Test
	public void testListPath() throws Exception{
		String remotePathStr = "/";
		Path path = new Path(remotePathStr);
		FileStatus[] list = fs.listStatus(path);
		for(FileStatus fileStatus : list){
			System.out.println("url路径:"+fileStatus.getPath().toString());
			System.out.println("fileName:"+fileStatus.getPath().getName());
			System.out.println("isDirectory:"+fileStatus.isDirectory());
			
			System.out.println("--------------------------------------------");
		}
	}
	
	@Test
	public void testListPath2() throws Exception{
		String remotePathStr = "/";
		Path path = new Path(remotePathStr);
		RemoteIterator<LocatedFileStatus> it = fs.listLocatedStatus(path);
		while(it.hasNext()){
			LocatedFileStatus locatedFileStatus = it.next();
			System.out.println("url路径:"+locatedFileStatus.getPath().toString());
			System.out.println("fileName:"+locatedFileStatus.getPath().getName());
			System.out.println("replication:"+locatedFileStatus.getReplication());
			System.out.println("isDirectory:"+locatedFileStatus.isDirectory());
			if(locatedFileStatus.isFile()){
				BlockLocation[] blockList = locatedFileStatus.getBlockLocations();
				for(BlockLocation blockLocation : blockList){
					System.out.print("***********host:");
					for(String host : blockLocation.getHosts()){
						System.out.print(host+",");
					}
					System.out.println();
					System.out.print("***********name:");
					for(String name : blockLocation.getNames()){
						System.out.print(name+",");
					}
					System.out.println();
					System.out.print("***********path:");
					for(String topologyPath : blockLocation.getTopologyPaths()){
						System.out.print(topologyPath+",");
					}
					System.out.println();
//					System.out.println("***********cachedHost:"+blockLocation.getCachedHosts());
					System.out.println("***********offset:"+blockLocation.getOffset());
					System.out.println("***********length:"+blockLocation.getLength());
				}
			}
			System.out.println("--------------------------------------------");
		}
	}
	
	@Test
	public void testExist() throws Exception{
		String remotePathStr = "/study";
		boolean result = fs.exists(new Path(remotePathStr));
		System.out.println(result);
	}
	
	
	@After
	public void closeFileSystem() throws Exception{
		fs.close();
	}
}
