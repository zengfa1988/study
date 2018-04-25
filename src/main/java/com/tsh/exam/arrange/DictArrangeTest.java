package com.tsh.exam.arrange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全排列-字典排序
 * 参考：https://mp.weixin.qq.com/s/q2KnZ0dovz0zVy6xNyJ6tA
 * @author zengfa
 *
 */
public class DictArrangeTest {

	public static void main(String[] args) {
		int[] numbers = {2,1,3};
		List<int[]> list = dictArrange(numbers);
		for(int[] a : list){
			pArray(a);
		}
	}
	
	/**
	 * 对顺序数组进行全排列
	 * @param numbers
	 * @return
	 */
	public static List<int[]> dictOrderArrange(int[] numbers){
		List<int[]> list = new ArrayList<int[]>();
		list.add(Arrays.copyOf(numbers, numbers.length));
		int index = 0;
		do{
			index = findTransferPoint(numbers);
			if(index>0){
				//从右向左找到倒序开始位置
				exchangeHead(numbers, index);
				reverse(numbers, index);
				int[] numbersCopy = Arrays.copyOf(numbers, numbers.length);
				list.add(numbersCopy);
			}
		} while (index != 0);
		
		return list;
	}
	
	/**
	 * 对无规则数组进行全排列
	 * @param numbers
	 * @return
	 */
	public static List<int[]> dictArrange(int[] numbers){
		List<int[]> list = new ArrayList<int[]>();
		list.add(Arrays.copyOf(numbers, numbers.length));
		do{
			int index = findTransferPoint(numbers);
			if(index>0){
				//从右向左找到倒序开始位置
				exchangeHead(numbers, index);
				reverse(numbers, index);
			}else{
				reverse(numbers, index);//如果是最大排序，也就是全部是倒序，则转换成最小排序
			}
			int[] numbersCopy = Arrays.copyOf(numbers, numbers.length);
			if(arrayEqual(numbersCopy, list.get(0))){
				break;
			}
			list.add(numbersCopy);
		} while (true);
		
		return list;
	}
	
	/**
	 * 从后向前查看逆序区域，找到逆序区域的前一位，也就是数字置换的边界
	 * @param numbers
	 * @return
	 */
	public static int findTransferPoint(int[] numbers){
		for(int i=numbers.length-1;i>0;i--){
			if(numbers[i-1]<numbers[i]){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * 把逆序区域的前一位和逆序区域中刚刚大于它的数字交换位置
	 * 数字交换后的原逆序区域还是逆序
	 * @param numbers
	 * @param index		逆序区域开始位置
	 */
	public static void exchangeHead(int[] numbers,int index){
		int head = numbers[index-1];//逆序区前一个要替换的数
		for(int i=numbers.length-1;i>=index;i--){//在逆序区域最小的在后面，所以从后面开始往前比较
			if(head < numbers[i]){
				numbers[index-1] = numbers[i];
				numbers[i] = head;
				break;
			}
		}
	}
	
	/**
	 * 把原来的逆序区域转为顺序
	 * @param numbers
	 * @param index		逆序区域开始位置
	 */
	public static void reverse(int[] numbers,int index){
		for(int i=index,j=numbers.length-1;i<j;i++,j--){
			int tem = numbers[i];
			numbers[i] = numbers[j];
			numbers[j] = tem;
		}
	}
	
	public static void pArray(int[] numbers){
		for(int number : numbers){
			System.out.print(number);
		}
		System.out.println();
	}
	
	/**
	 * 判断数组是否相等
	 * @param numbers
	 * @param number2
	 * @return
	 */
	public static boolean arrayEqual(int[] numbers,int[] number2){
		if(numbers.length != number2.length){
			return false;
		}
		for(int i=0;i<numbers.length;i++){
			if(numbers[i] != number2[i]){
				return false;
			}
		}
		return true;
	}
}
