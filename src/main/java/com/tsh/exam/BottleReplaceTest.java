package com.tsh.exam;

import java.util.HashMap;
import java.util.Map;

/**
 * 汽水替换问题
 * 2块钱可以买一瓶汽水瓶,4个瓶盖可换取一个汽水,2个空瓶可换取一个汽水,输入一个金额可喝多少瓶汽水
 * @author zengfa
 *
 */
public class BottleReplaceTest {

	public static void main(String[] args) {
		Integer drinkNum = getBottleDrink(4);
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
		
		while(emptyNum >=2 || capNum >=4){
			//调用空瓶替换方法消耗空瓶
			emptyBottleReplace(pocket);
			//调用瓶盖替换方法消耗瓶盖
			capReplace(pocket);
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
}
