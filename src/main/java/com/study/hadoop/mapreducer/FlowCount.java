package com.study.hadoop.mapreducer;

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

/**
 * 流量用户统计
 * 将resource/mapreduce文件夹下的数据文件HTTP_20130313143750.dat上传到hdfs：/study/mr/flowcount/input
 * 然后在集群中运行hadoop jar testMr.jar com.study.hadoop.mapreducer.FlowCount /study/mr/flowcount/input /study/mr/flowcount/output
 * @author zengfa
 *
 */
public class FlowCount {
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"flowJob");
		
		job.setJarByClass(FlowCount.class);
		job.setMapperClass(FlowCountMapper.class);
		job.setReducerClass(FlowCountReduce.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlowBean.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}

	public static class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean>{

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
	
	public static class FlowCountReduce extends Reducer<Text, FlowBean, Text, FlowBean>{

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
