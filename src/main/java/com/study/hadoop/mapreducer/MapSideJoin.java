package com.study.hadoop.mapreducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * map join
 * 输入两文件
 * product.txt(商品id,商品名称)
 * 	001	Redis教程
 * 	002	hadoop内幕
 * 	003	Java核心技术
 * 
 * order.txt(商品id,订单号)
 * 	001	20180531001
 * 	002	20180531002
 * 	001	20180531003
 * 	003	20180531004
 * 	003	20180531005
 * 
 * 
 * 输出:
20180531001	001	Redis教程
20180531002	002	hadoop内幕
20180531003	001	Redis教程
20180531004	003	Java核心技术
20180531005	003	Java核心技术

 * @author zengfa
 *
 */
public class MapSideJoin {

	private static final String hdfsUrl = "hdfs://192.168.103.187:9000";
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "MapSideJoin");
		job.setJarByClass(MapSideJoin.class);
		
		job.setMapperClass(MapSideJoinMapper.class);
		//因为mapside-join机制中不需要reduce阶段，所以通过这句代码强制限定reduce task数量为0
		job.setNumReduceTasks(0);
//		job.setReducerClass(JoinQueryReduce.class);//不需要reduce阶段了
		
//		job.setMapOutputKeyClass(Text.class);
//		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		// 将hdfs上的一个文件添加到mapreduce框架提供的distributedCache中
		// distributedCache的本质就是将文件分发到每一个map task进程所在机器的工作目录中
		job.addCacheFile(new URI("/study/mr/mapsidejoin/cachefile/product.txt"));
		// 注意：输入数据中只包含订单详情文件
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	static class MapSideJoinMapper extends Mapper<LongWritable, Text, Text, Text>{

		Map<String,String> productMap = new HashMap<String,String>();
		
		Text k = new Text();
		Text v = new Text();
		/**
		 * 读取order.txt文件,product.txt不需要读取
		 */
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = StringUtils.split(line, "\t");
			String productName = productMap.get(words[0]);
			k.set(words[1]);
			v.set(words[0]+"\t"+productName);
			context.write(k, v);
		}

		/**
		 * setup()方法是由map task在执行map处理逻辑之前执行的一个初始化方法，仅执行一次
		 * 所以，我们可以在setup方法中，将“分布式缓存distributedCache”(maptask进程所在的工作目录中)
		 * 的商品信息文件加载到内存
		 */
		@Override
		protected void setup(Context context)
				throws IOException, InterruptedException {
			FileSystem fs = FileSystem.get(URI.create(hdfsUrl), context.getConfiguration());
			InputStream in = fs.open(new Path("/study/mr/mapsidejoin/cachefile/product.txt"));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while((line = br.readLine()) != null){
				String[] words = line.split("\t");
				productMap.put(words[0], words[1]);
			}
			br.close();
			fs.close();
		}
	}
	
}
