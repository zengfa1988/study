package com.tsh.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 喝汽水问题
 * 一瓶汽水单价2元，4个瓶盖可换取一个汽水，2个空瓶可换取一个汽水。给定金额得出一共能喝几瓶汽水？
 * @author zengfa
 *
 */
public class BottleReplaceTest2 {

	public static void main(String[] args) {
		getBottleDrink(10);
	}
	
	public static void getBottleDrink(Integer money){
		Integer drinkNum = money / 2;//已喝数量
		Integer emptyNum = drinkNum;//空瓶数量
		Integer capNum = drinkNum;//瓶盖数量
		
		String[] levelMethods = {"capReplace","emptyBottleReplace","capAndEmptyReplace"};
		List<String[]> levelList = getCombination(levelMethods);
		for(String[] _levelMethods : levelList){
			Map<String,Integer> pocket = new HashMap<String,Integer>();
			pocket.put("drinkNum", drinkNum);
			pocket.put("emptyNum", emptyNum);
			pocket.put("capNum", capNum);
			Integer _drinkNum = getBottleDrink(_levelMethods,pocket);
			System.out.println("规则序列执行结果"+_drinkNum);
		}
	}
	
	/**
	 * 获得方法组合排列
	 * @param levelMethods
	 * @return
	 */
	public static List<String[]> getCombination(String[] levelMethods){
		List<String[]> methodsList = new ArrayList<String[]>();
		ArrangeTest.arrange(levelMethods, 0, levelMethods.length,methodsList); 
//		for(String[] _chs : methodsList){
//			for(String s : _chs){
//				System.out.print(s+"\t");
//			}
//			System.out.println();
//		}
		return methodsList;
	}
	
	public static void pStrArray(String[] strs){
		for(String str : strs){
			System.out.print(str+"\t");
		}
		System.out.println();
	}
	
	/**
	 * 根据方法规则获得已喝数量
	 * @param levelMethods
	 * @param pocket
	 * @return
	 */
	public static Integer getBottleDrink(String[] levelMethods,Map<String,Integer> pocket){
		System.out.print("优先级规则序列:");
		pStrArray(levelMethods);
		System.out.print("规则执行顺序:");
		String method = null;
		do{
			method = getMethod(levelMethods, pocket);
			if("emptyBottleReplace".equals(method)){
				emptyBottleReplace(pocket);
			}else if("capReplace".equals(method)){
				capReplace(pocket);
			}else if("capAndEmptyReplace".equals(method)){
				capAndEmptyReplace(pocket);
			}else{
				break;
			}
			System.out.print(method+"->");
		}while(method != null);
		Integer drinkNum = pocket.get("drinkNum");
		System.out.println();
		return drinkNum;
	}
	
	/**
	 * 按优先级获得匹配的方法
	 * @param levelMethods
	 * @param pocket
	 * @return
	 */
	public static String getMethod(String[] levelMethods,Map<String,Integer> pocket){
		for(String method : levelMethods){
			if("emptyBottleReplace".equals(method)){
				if(matchEmptyMethod(pocket)){
					return "emptyBottleReplace";
				}
			}else if("capReplace".equals(method)){
				if(matchCapMethod(pocket)){
					return "capReplace";
				}
			}else if("capAndEmptyReplace".equals(method)){
				if(matchCapAndEmptyMethod(pocket)){
					return "capAndEmptyReplace";
				}
			}
		}
		return null;
	}
	
	public static boolean matchEmptyMethod(Map<String,Integer> pocket){
		Integer emptyNum = pocket.get("emptyNum");
		if(emptyNum >= 2){
			return true;
		}
		return false;
	}
	
	public static boolean matchCapMethod(Map<String,Integer> pocket){
		Integer capNum = pocket.get("capNum");
		if(capNum >= 4){
			return true;
		}
		return false;
	}
	
	public static boolean matchCapAndEmptyMethod(Map<String,Integer> pocket){
		Integer capNum = pocket.get("capNum");
		Integer emptyNum = pocket.get("emptyNum");
		if(emptyNum>=1 && capNum>=3){
			return true;
		}
		return false;
	}
	
	/**
	 * 空瓶替换汽水
	 * @param pocket
	 */
	public static void emptyBottleReplace(Map<String,Integer> pocket){
		Integer emptyNum = pocket.get("emptyNum");
		if(!matchEmptyMethod(pocket)){
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
		if(!matchCapMethod(pocket)){
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
		if(!matchCapAndEmptyMethod(pocket)){
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
