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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 数据在mapper本地合并
 * @author zengfa
 *
 */
public class WordCountCombiner {

	/**
	 * linux运行命令：hadoop jar testMr.jar com.study.hadoop.MyWordCount
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf);
		//使得hadoop可以根据类包，找到jar包在哪里
		job.setJarByClass(WordCountCombiner.class);
		//指定Mapper的类
		job.setMapperClass(WordCountMapper.class);
		//指定reduce的类
		job.setReducerClass(WordCountReducer.class);
		
		/**
		Combiner组件：
		1、是在每一个map task的本地运行，能收到map输出的每一个key的valuelist，所以可以做局部汇总处理
		2、因为在map task的本地进行了局部汇总，就会让map端的输出数据量大幅精简，减小shuffle过程的网络IO
		3、combiner其实就是一个reducer组件，跟真实的reducer的区别就在于，combiner运行maptask的本地
		4、combiner在使用时需要注意，输入输出KV数据类型要跟map和reduce的相应数据类型匹配
		5、要注意业务逻辑不能因为combiner的加入而受影响
		*/
		job.setCombinerClass(WordCountReducer.class);
		
		//设置Mapper输出的类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		//设置最终输出的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
//		FileInputFormat.addInputPath(job, new Path("E:\\study\\hadoop\\mr\\input"));
//		FileOutputFormat.setOutputPath(job, new Path("E:\\study\\hadoop\\mr\\output"));
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		try {
			job.waitForCompletion(true);
			//这里的为true,会打印执行结果
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable>{

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
//			String[] words = line.split(" ");
			String[] words = StringUtils.split(line, " ");
			for(String word : words){
				context.write(new Text(word), new LongWritable(1));
			}
		}
	}
	
	static class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable>{

		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,
				Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
			Long count = 0l;
			for(LongWritable value : values){
				count += value.get();
			}
			context.write(key, new LongWritable(count));
		}
	}

}
