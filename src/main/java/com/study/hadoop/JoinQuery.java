package com.study.hadoop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
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




 * @author Administrator
 *
 */
public class JoinQuery {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "InveredIndex");
		job.setJarByClass(JoinQuery.class);
		
		job.setMapperClass(JoinQueryMapper.class);
		job.setReducerClass(JoinQueryReduce.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	public static class JoinQueryMapper extends Mapper<LongWritable, Text, Text, Text>{

		Text k = new Text();
		Text v = new Text();
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = StringUtils.split(line, "\t");
			
			FileSplit fileSplit = (FileSplit)context.getInputSplit();
			String filename = fileSplit.getPath().getName();
			k.set(words[0]);
			v.set(words[1] + ":" + filename);
			context.write(k, v);
		}
		
	}
	
	public static class JoinQueryReduce extends Reducer<Text, Text, Text, Text>{

		Text k = new Text();
		Text v = new Text();
		List<String> orderNoList = null;
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String produceName = "";
			orderNoList = new ArrayList<String>();
			for(Text value : values){
				String[] nameAndField = StringUtils.split(value.toString(), ":");
				if(nameAndField.length<2){
					continue;
				}
				if(("product.txt").equals(nameAndField[1])){
					produceName = nameAndField[0];
					continue;
				}
				if(("order.txt").equals(nameAndField[1])){
					orderNoList.add(nameAndField[0]);
				}
			}
			v.set(key.toString()+"\t"+produceName);
			for(String orderNo : orderNoList){
				k.set(orderNo);
				context.write(k, v);
			}
			
		}
		
	}
}
