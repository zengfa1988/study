package com.study.hadoop.mapreducer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class WholeSmallfiles {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"WholeSmallfiles");
		
		job.setJarByClass(WholeSmallfiles.class);
		job.setMapperClass(WholeSmallfilesMapper.class);
		job.setReducerClass(IdentityReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(BytesWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(BytesWritable.class);
		
		job.setInputFormatClass(WholeFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path("E:\\study\\hadoop\\mr\\whole\\input"));
		FileOutputFormat.setOutputPath(job, new Path("E:\\study\\hadoop\\mr\\whole\\output"));
		
		job.waitForCompletion(true);
	}
	
	static class WholeSmallfilesMapper extends Mapper<NullWritable, BytesWritable, Text, BytesWritable>{

		private Text file = new Text();
		
		@Override
		protected void map(NullWritable key, BytesWritable value,
				Mapper<NullWritable, BytesWritable, Text, BytesWritable>.Context context)
				throws IOException, InterruptedException {
			String fileName = context.getConfiguration().get("map.input.file");
			file.set(fileName);
			context.write(file, value);
		}
	}
	
	static class IdentityReducer<Text, BytesWritable> extends Reducer<Text, BytesWritable, Text, BytesWritable>{

		@Override
		protected void reduce(Text key, Iterable<BytesWritable> values,Context context)
				throws IOException, InterruptedException {
			for (BytesWritable value : values) {
				context.write(key, value);
			}
		}
		
	}
}
