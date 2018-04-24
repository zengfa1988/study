package com.tsh.exam;

import java.util.HashMap;
import java.util.Map;

/**
 * 喝汽水问题
 * 一瓶汽水单价2元，4个瓶盖可换取一个汽水，2个空瓶可换取一个汽水。给定金额得出一共能喝几瓶汽水？
 * @author zengfa
 *
 */
public class BottleReplaceTest {

	public static void main(String[] args) {
		Integer drinkNum = getBottleDrink(10);
		System.out.println(drinkNum);
	}
	
	public static Integer getBottleDrink(Integer money){
		Integer drinkNum = money / 2;//已喝数量
		Integer emptyNum = drinkNum;//空瓶数量
		Integer capNum = drinkNum;//瓶盖数量
		Map<String,Integer> pocket = new HashMap<String,Integer>();
		pocket.put("drinkNum", drinkNum);
		pocket.put("emptyNum", emptyNum);
		pocket.put("capNum", capNum);
		
		while(emptyNum >=2 || capNum >=4 || (emptyNum >=1 && capNum>=3)){
			//调用空瓶替换方法消耗空瓶
			emptyBottleReplace(pocket);
			//调用瓶盖替换方法消耗瓶盖
			capReplace(pocket);
			//1个空瓶和3个瓶盖换一瓶汽水
			capAndEmptyReplace(pocket);
			emptyNum = pocket.get("emptyNum");
			capNum = pocket.get("capNum");
		}
		drinkNum = pocket.get("drinkNum");
		return drinkNum;
	}
	
	/**
	 * 空瓶替换汽水
	 * @param pocket
	 */
	public static void emptyBottleReplace(Map<String,Integer> pocket){
		Integer emptyNum = pocket.get("emptyNum");
		if(emptyNum < 2){
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
	
	/**
	 * 瓶盖替换汽水
	 * @param pocket
	 */
	public static void capReplace(Map<String,Integer> pocket){
		Integer capNum = pocket.get("capNum");
		if(capNum < 4){
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
	
	/**
	 * 1个空瓶和3个瓶盖换一瓶汽水
	 * @param pocket
	 */
	public static void capAndEmptyReplace(Map<String,Integer> pocket){
		Integer capNum = pocket.get("capNum");
		Integer emptyNum = pocket.get("emptyNum");
		if(emptyNum<1 || capNum<2){
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
