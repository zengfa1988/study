package com.tsh.exam.bottle;

import java.util.Map;

/**
 * 1个空瓶和3个瓶盖换一瓶汽水
 * @author zengfa
 *
 */
public class EmptyAndCapBottleReplaceRule implements BottleReplaceRule {

	public boolean match(Map<String, Integer> pocket) {
		Integer capNum = pocket.get("capNum");
		Integer emptyNum = pocket.get("emptyNum");
		if(emptyNum>=1 && capNum>=3){
			return true;
		}
		return false;
	}

	public void consume(Map<String, Integer> pocket) {
		Integer capNum = pocket.get("capNum");
		Integer emptyNum = pocket.get("emptyNum");
		if(!match(pocket)){
			return;
		}
		Integer curDrinkNum = capNum / 3;//当前空瓶可替换汽水数量
		if(curDrinkNum > emptyNum){//如果瓶盖算的汽水数量大于空瓶数量，说明没有足够的空瓶同所有瓶盖消耗，则本次消耗为空瓶数量
			curDrinkNum = emptyNum;
		}
		capNum = capNum - curDrinkNum * 3;//剩余瓶盖数量
		pocket.put("emptyNum", emptyNum - curDrinkNum + curDrinkNum);
		pocket.put("capNum", capNum + curDrinkNum);
		//已喝数量累加
		Integer drinkNum = pocket.get("drinkNum");
		drinkNum = drinkNum + curDrinkNum;
		pocket.put("drinkNum", drinkNum);
	}

}
