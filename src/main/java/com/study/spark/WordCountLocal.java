package com.study.spark;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;

import scala.Tuple2;

public class WordCountLocal {

	public static void main(String[] args) {
		// 第一步：创建SparkConf对象,设置相关配置信息
		SparkConf conf = new SparkConf();
		conf.setAppName("wordcount");
		//设置spark应用程序要连接的spark集群的master节点url,但是如果设置为local,则表示在本地运行
		//本地执行可以直接在eclips执行的
//		conf.setMaster("local");
		
		// 第二步：创建JavaSparkContext对象，SparkContext是Spark的所有功能的入口
		//主要作用,初始化spark应用程序所需要的组建,包括调度器,还没到spark master节点注册
		//不同的应用程序使用的sparkcontent不同
		//Scala语言编写的是原生的sparkcontent
		//Java是Javasparkcontent...
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		// 第三步：创建一个初始的RDD
		// SparkContext中，用于根据文件类型的输入源创建RDD的方法，叫做textFile()方法
		//在Java中，创建的普通RDD都叫JavaRDD
		//RDD中有元素的概念,如果是hdfs或本地的文件,创建的RDD,每个元素相当于文件里的一行
//		JavaRDD<String> lines = sc.textFile("D:\\study\\spark/word.txt");
		JavaRDD<String> lines = sc.textFile("hdfs://spark01:9000/spark/words.txt");
		
		// 第四步：对初始的RDD进行transformation操作，也就是一些计算操作
		//先将每一行拆分成单个单词
		//FlatMapFunction有两个泛型参数,分别代表输入和输出类型
		//这里输入是一行文本，是String,输出也是文本，也是String
		//flatMap算子的作用,就是将RDD中的一个元素拆分成一个或多个元素
		JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterator<String> call(String line) throws Exception {
				return Arrays.asList(line.split(" ")).iterator();
			}
		});
		
		//接着,将每个单词映射为(单词,1)的这种格式
		//mapToPair就是将每个元素映射为(v1,v2)这样的Tuple2类型的元素
		//这里的Tuple2就是Scala里的tuple,包括两个值
		//mapToPair这个算子要与PairFunction配合使用,第一个泛型参数代表输入类型,第二和第三个泛型参数分别代表输出的tuple第一个值和第二个值类型
		JavaPairRDD<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {
			@Override
			public Tuple2<String, Integer> call(String word) throws Exception {
				return new Tuple2<String, Integer>(word, 1);
			}
		});
		
		//然后,需要已单词为key,统计每个单词出现的次数
		//这里使用reduceByKey算子,对每个key的value,都进行reduce操作
		//比如JavaPairRDD中有几个元素,分别是(hello,1) (hello,1) (hello,1) (world 1)
		//reduce操作相当于把第一个值和第二个值进行计算,然后再将结果与第三个值进行计算
		//这里的hello,那么相当于首先1+1=2,然后再2+1=3
		//最后返回的JavaPairRDD元素也是tuple类型,但是第一个值是每个key,第二个值是对应key的value进行reduce后的结果
		JavaPairRDD<String, Integer> wordCounts = pairs.reduceByKey(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1 + v2;
			}
		});
		
		//到这里已经统计出了单词个数
		//但是,之前使用的flatMap、mapToPair、reduceByKey操作都叫transformation操作
		//一个spark应用中，光是有transformation操作是不行的,必须要有个action操作来触发程序执行
		//比如这里的foreach
		wordCounts.foreach(new VoidFunction<Tuple2<String,Integer>>() {
			@Override
			public void call(Tuple2<String, Integer> t) throws Exception {
				System.out.println(t._1() + ":" +t._2());
				
			}
		});
		sc.close();
		
//		List<Tuple2<String, Integer>> output = wordCounts.collect();
//		for (Tuple2<?, ?> tuple : output) {
//			System.out.println(tuple._1() + ": " + tuple._2());
//		}
//		sc.stop();
	}
}
