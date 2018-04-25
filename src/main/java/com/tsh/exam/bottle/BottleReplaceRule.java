package com.tsh.exam.bottle;

import java.util.Map;

/**
 * 换汽水规则
 * @author zengfa
 *
 */
public interface BottleReplaceRule {

	/**
	 * 判断篮子里属性是否匹配规则
	 * @param pocket
	 * @return
	 */
	public boolean match(Map<String,Integer> pocket);
	
	/**
	 * 执行规则
	 * @param pocket
	 */
	public void consume(Map<String,Integer> pocket);
}
