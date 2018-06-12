package com.study.hadoop.mapreducer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 数据去重
 * @author Administrator
 *
 */
public class Dedup {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(Dedup.class);
		job.setMapperClass(DedupMapper.class);
		job.setReducerClass(DedupReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		FileInputFormat.addInputPath(job, new Path("E:\\study\\hadoop\\mr\\input"));
		FileOutputFormat.setOutputPath(job, new Path("E:\\study\\hadoop\\mr\\output"));
		
		job.waitForCompletion(true);

	}
	
	public static class DedupMapper extends Mapper<LongWritable, Text, Text, NullWritable>{

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			context.write(value, NullWritable.get());
		}
	}
	
	public static class DedupReducer extends Reducer<Text, NullWritable, Text, NullWritable>{

		@Override
		protected void reduce(Text key, Iterable<NullWritable> value,Context context) throws IOException, InterruptedException {
			context.write(key, NullWritable.get());
		}
		
	}
	

}
