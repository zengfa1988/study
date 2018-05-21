package com.study.patterns.builderPattern;

/**
 * 创建一个表示食物条目和食物包装的接口
 * @author zengfa
 *
 */
public interface Item {

	public String name();
	public Packing packing();
	public float price();
}
