package com.study.hadoop.mapreducer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 单词数量统计
 * @author zengfa
 *
 */
public class WordCount {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		
		//使得hadoop可以根据类包，找到jar包在哪里
		job.setJarByClass(WordCount.class);
		//指定Mapper的类
		job.setMapperClass(WordCountMapper.class);
		//指定reduce的类
		job.setReducerClass(WordCountReduce.class);
		//设置Mapper输出的类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		//设置最终输出的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);//这里的为true,会打印执行结果

	}
	
	public static class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable>{

		Text k = new Text();
		LongWritable v = new LongWritable();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] words = value.toString().split(" ");
			for(String word : words){
				k.set(word);
				v.set(1);
				context.write(k, v);
			}
		}
		
	}
	
	public static class WordCountReduce extends Reducer<Text, LongWritable, Text, LongWritable>{

		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,Context content) throws IOException, InterruptedException {
			long count = 0;
			for(LongWritable value : values){
				count += value.get();
			}
			content.write(key, new LongWritable(count));
		}
		
	}

}
