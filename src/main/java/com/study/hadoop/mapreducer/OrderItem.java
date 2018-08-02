package com.study.hadoop.mapreducer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 订单明细数据
 * @author zengfa
 *
 */
public class OrderItem implements WritableComparable<OrderItem>{

	private Integer orderId;
	private Integer pId;
	private Integer money;
	
	public OrderItem(){};
	public OrderItem(Integer orderId,Integer pId,Integer money){
		this.orderId = orderId;
		this.pId = pId;
		this.money = money;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(orderId);
		out.writeInt(pId);
		out.writeInt(money);
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.orderId = in.readInt();
		this.pId = in.readInt();
		this.money = in.readInt();
		
	}

	@Override
	public int compareTo(OrderItem o) {
		int cm = this.orderId.compareTo(o.getOrderId());
		if(cm == 0){
			cm  = -this.money.compareTo(o.getMoney());//大的排在前面
		}
		return cm;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	
	@Override
	public String toString() {
		return orderId + "\t" + pId + "\t" + money ;
	}

}
