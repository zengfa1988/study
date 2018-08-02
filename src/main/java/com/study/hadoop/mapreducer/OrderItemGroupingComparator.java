package com.study.hadoop.mapreducer;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 利用reduce端的GroupingComparator来实现将一组bean看成相同的key
 * @author zengfa
 *
 */
public class OrderItemGroupingComparator extends WritableComparator{

	//传入作为key的bean的class类型，以及制定需要让框架做反射获取实例对象
	protected OrderItemGroupingComparator() {
		super(OrderItem.class, true);
	}
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		OrderItem itemA = (OrderItem)a;
		OrderItem itemB = (OrderItem)b;
		return itemA.getOrderId().compareTo(itemB.getOrderId());
	}

	
}
