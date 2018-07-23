package com.study.hadoop.mapreducer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 用户流量类
 * @author zengfa
 *
 */
public class FlowBean implements WritableComparable<FlowBean>{

	private String phone;//手机号
	private long upFlow;//上行流量
	private long downFlow;//下行流量
	private long sumFlow;//总流量
	public FlowBean(){}
	
	public FlowBean(String phone,long upFlow,long downFlow){
		this.phone = phone;
		this.upFlow = upFlow;
		this.downFlow = downFlow;
		this.sumFlow = upFlow + downFlow;
	}

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
	
	@Override
	public int compareTo(FlowBean o) {
		return this.sumFlow > o.getSumFlow() ? -1 : 1;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(long upFlow) {
		this.upFlow = upFlow;
	}

	public long getDownFlow() {
		return downFlow;
	}

	public void setDownFlow(long downFlow) {
		this.downFlow = downFlow;
	}

	public long getSumFlow() {
		return sumFlow;
	}

	public void setSumFlow(long sumFlow) {
		this.sumFlow = sumFlow;
	}

	@Override
	public String toString() {
		return this.upFlow + "\t" + this.downFlow + "\t" + this.sumFlow;
	}
	
}
