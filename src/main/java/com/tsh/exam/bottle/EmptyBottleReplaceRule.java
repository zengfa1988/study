package com.tsh.exam.bottle;

import java.util.Map;

/**
 * 2个空瓶换一瓶汽水
 * @author zengfa
 *
 */
public class EmptyBottleReplaceRule implements BottleReplaceRule {

	public boolean match(Map<String, Integer> pocket) {
		Integer emptyNum = pocket.get("emptyNum");
		if(emptyNum >= 2){
			return true;
		}
		return false;
	}

	public void consume(Map<String, Integer> pocket) {
		Integer emptyNum = pocket.get("emptyNum");
		if(!match(pocket)){
			return;
		}
		Integer curDrinkNum = emptyNum / 2;//当前空瓶可替换汽水数量
		emptyNum = curDrinkNum + emptyNum % 2;
		pocket.put("emptyNum", emptyNum);
		//已喝数量累加
		Integer drinkNum = pocket.get("drinkNum");
		drinkNum = drinkNum + curDrinkNum;
		pocket.put("drinkNum", drinkNum);
		//瓶盖累加
		Integer capNum = pocket.get("capNum");
		capNum = capNum + curDrinkNum;
		pocket.put("capNum", capNum);
	}

}
