package com.tsh.exam.bottle;

import java.util.Map;

/**
 * 4个瓶盖换一瓶汽水
 * @author zengfa
 *
 */
public class CapBottleReplaceRule implements BottleReplaceRule {

	public boolean match(Map<String, Integer> pocket) {
		Integer capNum = pocket.get("capNum");
		if(capNum >= 4){
			return true;
		}
		return false;
	}

	public void consume(Map<String, Integer> pocket) {
		Integer capNum = pocket.get("capNum");
		if(!match(pocket)){
			return;
		}
		Integer curDrinkNum = capNum / 4;//当前空瓶可替换汽水数量
		capNum = curDrinkNum + capNum % 4;
		pocket.put("capNum", capNum);
		//已喝数量累加
		Integer drinkNum = pocket.get("drinkNum");
		drinkNum = drinkNum + curDrinkNum;
		pocket.put("drinkNum", drinkNum);
		//瓶盖累加
		Integer emptyNum = pocket.get("emptyNum");
		emptyNum = emptyNum + curDrinkNum;
		pocket.put("emptyNum", emptyNum);
	}

}
