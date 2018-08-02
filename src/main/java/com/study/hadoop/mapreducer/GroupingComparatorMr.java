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
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * 
 * @author zengfa
 *统计订单明细中金额最大的商品
 *输入：
1 1 222
1 5 25
2 2 2000
2 4 122
2 5 722
3 1 222
2 3 3000
3 2 221

输出：
1 1 222
2 3 3000
3 1 222
 */
public class GroupingComparatorMr {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"GroupingComparatorMr");
		
		job.setJarByClass(GroupingComparatorMr.class);
		job.setMapperClass(GroupingComparatorMapper.class);
		job.setReducerClass(GroupingComparatorReduce.class);
		
		job.setMapOutputKeyClass(OrderItem.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(OrderItem.class);
		job.setOutputValueClass(NullWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//在此设置自定义的Groupingcomparator类 
		job.setGroupingComparatorClass(OrderItemGroupingComparator.class);
		
		job.waitForCompletion(true);
	}
	
	static class GroupingComparatorMapper extends Mapper<LongWritable, Text, OrderItem, NullWritable>{
		OrderItem orderItem = new OrderItem();
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] words = line.split("\t");
			orderItem.setOrderId(Integer.parseInt(words[0]));
			orderItem.setpId(Integer.parseInt(words[1]));
			orderItem.setMoney(Integer.parseInt(words[2]));
			
			context.write(orderItem, NullWritable.get());
		}
	}
	
	static class GroupingComparatorReduce extends Reducer<OrderItem, NullWritable, OrderItem, NullWritable>{

		@Override
		protected void reduce(OrderItem orderItem, Iterable<NullWritable> values,Context context)
				throws IOException, InterruptedException {
			context.write(orderItem, NullWritable.get());
		}
		
	}
	
}
