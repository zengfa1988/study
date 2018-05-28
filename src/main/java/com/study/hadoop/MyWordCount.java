package com.study.hadoop;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyWordCount {

	/**
	 * linux运行命令：hadoop jar testMr.jar com.study.hadoop.MyWordCount
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Job job = Job.getInstance();
		//使得hadoop可以根据类包，找到jar包在哪里
		job.setJarByClass(MyWordCount.class);
		//指定Mapper的类
		job.setMapperClass(MyWordCountMapper.class);
		//指定reduce的类
		job.setReducerClass(MyWordCountReducer.class);
		
		//设置Mapper输出的类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		//设置最终输出的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
//		FileInputFormat.addInputPath(job, new Path("E:\\study\\hadoop\\mr\\input"));
//		FileOutputFormat.setOutputPath(job, new Path("E:\\study\\hadoop\\mr\\output"));
		
		FileInputFormat.addInputPath(job, new Path("hdfs://master.hadoop:9000/user/mr/input"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://master.hadoop:9000/user/mr/output"));
		
		try {
			job.waitForCompletion(true);
			//这里的为true,会打印执行结果
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
