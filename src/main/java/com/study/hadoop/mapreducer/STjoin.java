package com.study.hadoop.mapreducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * 单表关联
输入：
child parent
Tom Lucy
Tom Jack
Lucy Mary
Lucy Ben
Jone Alma
输出：
grandchild grandparent
Tom Mary
Tom Ben　
 * @author Administrator
 *
 */
public class STjoin {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(STjoin.class);
		job.setMapperClass(STjoinMapper.class);
		job.setReducerClass(STjoinReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
//		job.setOutputFormatClass(MyOutputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path("E:\\study\\hadoop\\mr\\stjoin\\input"));
		FileOutputFormat.setOutputPath(job, new Path("E:\\study\\hadoop\\mr\\stjoin\\output"));
		
		job.waitForCompletion(true);
	}

	public static class STjoinMapper extends Mapper<LongWritable, Text, Text, Text>{

		Text k1 = new Text();
		Text k2 = new Text();
		Text v = new Text();
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] names = value.toString().split("\t");
			v.set(names[0]+"-"+names[1]);
			k1.set(names[0]);
			context.write(k1, v);
			k2.set(names[1]);
			context.write(k2, v);
		}
		
	}
	
	/**
	 * Lucy [Tom-Lucy,Lucy-Mary,Lucy Ben]
	 * Mary [Lucy-Mary,Mary-tt]
	 * 
	 * Lucy Tom-Mary
	 * @author Administrator
	 *
	 */
	public static class STjoinReducer extends Reducer<Text, Text, Text, Text>{

		Text k = new Text();
		Text v = new Text();
		List<String> list = null;
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			list = new ArrayList<String>();
			for(Text value : values){
				list.add(value.toString());
			}
			for(String value : list){
				for(String value2 : list){
					if(value2 == value){
						continue;
					}
					String[] a1 = value.toString().split("-");
					String[] a2 = value2.toString().split("-");
					if(!a1[1].equals(a2[0])){
						continue;
					}
					k.set(a1[0]);
					v.set(a2[1]);
					context.write(k, v);
				}
			}
		}
		
	}
}
