package com.study.hadoop.mapreducer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class FlowBeanWritable implements Writable{

	private String phone;
	
	private long upFlow;
	
	private long downFlow;
	
	private long sumFlow;
	
	// 只要数据在网络中进行传输，就需要序列化与反序列化
	// 先序列化,将对象(字段)写到字节输出流当中
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.phone);
		out.writeLong(this.upFlow);
		out.writeLong(this.downFlow);
		out.writeLong(this.sumFlow);
	}

	// 反序列化,将对象从字节输入流当中读取出来，并且序列化与反序列化的字段顺序要相同
	@Override
	public void readFields(DataInput in) throws IOException {
		this.phone = in.readUTF();
		this.upFlow = in.readLong();
		this.downFlow = in.readLong();
		this.sumFlow = in.readLong();
	}

}
