package com.tsh.exam.bottle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tsh.exam.ArrangeTest;

/**
 * 多规则换汽水问题
 * @author zengfa
 *
 */
public class MutilRuleBottleReplaceTest {

	public static void main(String[] args) {
		getBottleDrink(10);
	}
	
	public static void getBottleDrink(Integer money){
		Integer drinkNum = money / 2;//已喝数量
		Integer emptyNum = drinkNum;//空瓶数量
		Integer capNum = drinkNum;//瓶盖数量
		
		List<BottleReplaceRule> ruleList = new ArrayList<BottleReplaceRule>();
		BottleReplaceRule rule1 = new EmptyBottleReplaceRule();
		BottleReplaceRule rule2 = new CapBottleReplaceRule();
		BottleReplaceRule rule3 = new EmptyAndCapBottleReplaceRule();
		ruleList.add(rule1);
		ruleList.add(rule2);
		ruleList.add(rule3);
		
		List<List<BottleReplaceRule>> priorityRuleList = getRuleCombination(ruleList);
		for(List<BottleReplaceRule> rules : priorityRuleList){
			Map<String,Integer> pocket = new HashMap<String,Integer>();
			pocket.put("drinkNum", drinkNum);
			pocket.put("emptyNum", emptyNum);
			pocket.put("capNum", capNum);
			
			System.out.print("优先级规则序列:");
			pList(rules);
			Integer num = getBottleDrink(rules,pocket);
			System.out.println("规则序列执行结果:"+JSON.toJSONString(pocket));
		}
	}
	
	/**
	 * 根据规则优先级序列获得已喝数量
	 * @param ruleList	有优先级的规则序列，优先级高的在前面
	 * @param pocket
	 * @return
	 */
	public static Integer getBottleDrink(List<BottleReplaceRule> ruleList,Map<String,Integer> pocket){
		BottleReplaceRule rule = null;
		System.out.print("规则执行顺序:");
		do{
			rule = getBottleReplaceRule(ruleList,pocket);
			if(rule != null){
				rule.consume(pocket);
				System.out.print(rule.getClass().getSimpleName()+"->");
			}
		} while(rule != null);
		
		Integer drinkNum = pocket.get("drinkNum");
		System.out.println();
		return drinkNum;
	}
	
	/**
	 * 获得匹配的优先级高的规则
	 * @param ruleList
	 * @param pocket
	 * @return
	 */
	public static BottleReplaceRule getBottleReplaceRule(List<BottleReplaceRule> ruleList,Map<String,Integer> pocket){
		for(BottleReplaceRule rule : ruleList){
			if(rule.match(pocket)){
				return rule;
			}
		}
		return null;
	}
	
	/**
	 * 获得规则序列的不同排列组合
	 * @param ruleList
	 * @return
	 */
	public static List<List<BottleReplaceRule>> getRuleCombination(List<BottleReplaceRule> ruleList){
		List<List<BottleReplaceRule>> priorityRuleList = new ArrayList<List<BottleReplaceRule>>();
		int[] indexArray = new int[ruleList.size()];
		for(int i=0;i<ruleList.size();i++){
			indexArray[i] = i;
		}
		List<int[]> priorityRuleIndexList = new ArrayList<int[]>();
		ArrangeTest.arrange(indexArray, 0, ruleList.size(), priorityRuleIndexList);
		for(int[] priorityRuleIndex : priorityRuleIndexList){
			List<BottleReplaceRule> rules = new ArrayList<BottleReplaceRule>();
			for(int index : priorityRuleIndex){
				rules.add(ruleList.get(index));
			}
			priorityRuleList.add(rules);
		}
		return priorityRuleList;
	}
	
	public static void pList(List<BottleReplaceRule> rules){
		for(BottleReplaceRule rule : rules){
			System.out.print(rule.getClass().getSimpleName()+"\t");
		}
		System.out.println();
	}

}
