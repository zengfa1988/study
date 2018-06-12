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
 * 数据排序
 * file1:
43
32
5
6
file2:
98
1234
356
2
output:
1 2
2 5
3 6
4 32
5 43
6 98
7 356
8 1234
 * @author Administrator
 *
 */
public class Sort {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(Sort.class);
		
		job.setMapperClass(SortMapper.class);
		job.setReducerClass(SortReducer.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(LongWritable.class);
		
		FileInputFormat.addInputPath(job, new Path("E:\\study\\hadoop\\mr\\sort\\input"));
		FileOutputFormat.setOutputPath(job, new Path("E:\\study\\hadoop\\mr\\sort\\output"));
		
		job.waitForCompletion(true);

	}
	
	public static class SortMapper extends Mapper<LongWritable, Text, LongWritable, NullWritable>{

		LongWritable key = new LongWritable();
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			long num = Long.parseLong(value.toString());
			key.set(num);
			context.write(key, NullWritable.get());
		}
		
	}
	
	public static class SortReducer extends Reducer<LongWritable, NullWritable, LongWritable, LongWritable>{

		LongWritable indexKey = new LongWritable();
		long i = 0;
		@Override
		protected void reduce(LongWritable key, Iterable<NullWritable> values,Context context)
				throws IOException, InterruptedException {
			indexKey.set(++i);
			context.write(indexKey, key);
		}
		
	}

}
