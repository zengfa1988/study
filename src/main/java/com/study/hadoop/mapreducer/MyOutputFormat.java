package com.study.hadoop.mapreducer;

import java.io.IOException;

import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MyOutputFormat<K, V> extends FileOutputFormat<K, V>{

	@Override
	public RecordWriter<K, V> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
		RecordWriter<K, V> recordWriter = new RecordWriter<K, V>() {

			@Override
			public void write(K key, V value) throws IOException, InterruptedException {
				System.out.println(key.toString() + "\t" + value.toString());
				
			}

			@Override
			public void close(TaskAttemptContext context) throws IOException, InterruptedException {
				// TODO Auto-generated method stub
				
			}
			
		};
		return recordWriter;
	}

}
