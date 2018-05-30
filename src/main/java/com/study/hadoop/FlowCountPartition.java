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
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class FlowCountPartition {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"flowpartitionJob");
		
		job.setJarByClass(FlowCount.class);
		job.setMapperClass(FlowCountPartitionMapper.class);
		job.setReducerClass(FlowCountPartitionReduce.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		/**
		 * 加入自定义分区定义 ： AreaPartitioner
		 */
		job.setPartitionerClass(AreaPartitioner.class);
		/**
		 * 设置reduce task的数量，要跟AreaPartitioner返回的partition个数匹配
		 * 如果reduce task的数量比partitioner中分组数多，就会产生多余的几个空文件
		 * 如果reduce task的数量比partitioner中分组数少，就会发生异常，因为有一些key没有对应reducetask接收
		 * (如果reduce task的数量为1，也能正常运行，所有的key都会分给这一个reduce task)
		 * reduce task 或 map task 指的是，reuder和mapper在集群中运行的实例
		 */
		job.setNumReduceTasks(1);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}

	public static class FlowCountPartitionMapper extends Mapper<LongWritable, Text, Text, FlowBean>{

		FlowBean flowBean = new FlowBean();
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = StringUtils.split(line, "\t");
			String phone = fields[1];
			long upFlow = Long.parseLong(fields[fields.length-3]);
			long downFlow = Long.parseLong(fields[fields.length-2]);
			flowBean = new FlowBean(phone,upFlow,downFlow);
			
			context.write(new Text(phone), flowBean);
		}
		
	}
	
	public static class FlowCountPartitionReduce extends Reducer<Text, FlowBean, Text, FlowBean>{

		FlowBean flowBean = new FlowBean();
		@Override
		protected void reduce(Text key, Iterable<FlowBean> values, Reducer<Text, FlowBean, Text, FlowBean>.Context context)
				throws IOException, InterruptedException {
			long upFlow = 0l;
			long downFlow = 0;
			for(FlowBean flowBean : values){
				upFlow += flowBean.getUpFlow();
				downFlow += flowBean.getDownFlow();
			}
			flowBean = new FlowBean(key.toString(),upFlow,downFlow);
			context.write(key, flowBean);
		}
		
	}
}
