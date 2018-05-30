package com.study.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyWordCount {

	/**
	 * linux运行命令：hadoop jar testMr.jar com.study.hadoop.MyWordCount
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Configuration conf = new Configuration();
//		conf.set("fs.defaultFS","hdfs://master.hadoop:9000");
//		conf.set("yarn.resourcemanager.address","master.hadoop:8032");
//		conf.set("yarn.resoucemanager.hostname","master.hadoop");
//		conf.set("mapreduce.framework.name", "yarn"); // 指定使用yarn框架
//		conf.set("mapreduce.app-submission.cross-platform", "true");
//		conf.set("mapreduce.job.jar", "t.jar");//此处代码，一定放在Job任务前面，否则会报类找不到的异常
//		conf.set("mapred.jar", "t.jar");
		
		Job job = Job.getInstance(conf);
		//使得hadoop可以根据类包，找到jar包在哪里
		job.setJarByClass(MyWordCount.class);
		//指定Mapper的类
		job.setMapperClass(MyWordCountMapper.class);
		//指定reduce的类
		job.setReducerClass(MyWordCountReducer.class);
		
		/**
		Combiner组件：
		1、是在每一个map task的本地运行，能收到map输出的每一个key的valuelist，所以可以做局部汇总处理
		2、因为在map task的本地进行了局部汇总，就会让map端的输出数据量大幅精简，减小shuffle过程的网络IO
		3、combiner其实就是一个reducer组件，跟真实的reducer的区别就在于，combiner运行maptask的本地
		4、combiner在使用时需要注意，输入输出KV数据类型要跟map和reduce的相应数据类型匹配
		5、要注意业务逻辑不能因为combiner的加入而受影响
		*/
		job.setCombinerClass(MyWordCountReducer.class);
		
		//设置Mapper输出的类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		//设置最终输出的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
//		FileInputFormat.addInputPath(job, new Path("E:\\study\\hadoop\\mr\\input"));
//		FileOutputFormat.setOutputPath(job, new Path("E:\\study\\hadoop\\mr\\output"));
		
		FileInputFormat.addInputPath(job, new Path("hdfs://master.hadoop:9000/user/mr/input"));
		FileOutputFormat.setOutputPath(job, new Path("hdfs://master.hadoop:9000/user/mr/output"));
		
		try {
			job.waitForCompletion(true);
			//这里的为true,会打印执行结果
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
