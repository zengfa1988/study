package com.study.hadoop;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class FlowCountSort {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "flowSortJob");
		
		job.setJarByClass(FlowCountSort.class);
		job.setMapperClass(FlowCountSortMapper.class);
		job.setReducerClass(FlowCountSortReduce.class);
		
		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
		
	}
	
	public static class FlowCountSortMapper extends Mapper<LongWritable, Text, FlowBean, NullWritable>{

		FlowBean flowBean = new FlowBean();
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, FlowBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = StringUtils.split(line, "\t");
			String phone = fields[0];
			long upFlow = Long.parseLong(fields[1]);
			long downFlow = Long.parseLong(fields[2]);
			flowBean = new FlowBean(phone,upFlow,downFlow);
			context.write(flowBean, NullWritable.get());
		}
		
	}
	
	public static class FlowCountSortReduce extends Reducer<FlowBean, NullWritable, Text, FlowBean>{

		@Override
		protected void reduce(FlowBean flowBean, Iterable<NullWritable> values,
				Reducer<FlowBean, NullWritable, Text, FlowBean>.Context context) throws IOException, InterruptedException {
			context.write(new Text(flowBean.getPhone()), flowBean);
		}
		
	}
}
