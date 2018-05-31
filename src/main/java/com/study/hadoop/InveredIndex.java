package com.study.hadoop;

import java.io.IOException;

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
 * 倒排索引
 * 输入
 * a.txt
 * 	mapreduce is simple
 * b.txt
 * 	mapreduce is powerful is simple
 * c.txt
 * 	hello mapreduce bye mapreduce
 * 
 * 输出:
bye     c.txt:1;
hello   c.txt:1;
is      a.txt:1;b.txt:2;
mapreduce       b.txt:1;c.txt:2;a.txt:1;
powerful        b.txt:1;
simple  b.txt:1;a.txt:1;
 * @author Administrator
 *
 */
public class InveredIndex {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "InveredIndex");
		job.setJarByClass(InveredIndex.class);
		
		job.setMapperClass(InveredIndexMapper.class);
		job.setCombinerClass(InveredIndexCombine.class);
		job.setReducerClass(InveredIndexReduce.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	public static class InveredIndexMapper extends Mapper<LongWritable, Text, Text, Text>{

		Text k = new Text();
		Text v = new Text();
		FileSplit inputSplit = null;
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = StringUtils.split(line, " ");
			inputSplit = (FileSplit)context.getInputSplit();
			String fileName = inputSplit.getPath().getName();
			for(String word : words){
				k.set(word+":"+fileName);
				v.set("1");
				context.write(k, v);
			}
		}
		
	}
	
	public static class InveredIndexCombine extends Reducer<Text, Text, Text, Text>{
		Text k = new Text();
		Text v = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
			String wordAndfield = key.toString();
			String[] wordAndfields = StringUtils.split(wordAndfield, ":");
			Long count = 0l;
			for(Text value : values){
				count += Long.parseLong(value.toString());
			}
			k.set(wordAndfields[0]);
			v.set(wordAndfields[1]+":"+count);
			context.write(k, v);
		}
		
	}
	
	public static class InveredIndexReduce extends Reducer<Text, Text, Text, Text>{

		private Text v=new Text();
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			String s = "";
			for(Text value : values){
				s += value.toString()+";";
			}
			
			v.set(s);
			context.write(key, v);
		}
		
	}
}
