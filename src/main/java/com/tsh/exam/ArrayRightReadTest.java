package com.tsh.exam;

import java.util.HashMap;
import java.util.Map;

/**
 * 输入一个矩阵，按照从外向里已顺时针依次打印出每个数字，例如:
 * 1	2	3	4
 * 5	6	7	8
 * 9	10	11	12
 * 13	14	15	16
 * 则依次打印输出：1、2、3、4、8、12、16、15、14、13、9、5、6、7、11、10
 * @author zengfa
 *
 */
public class ArrayRightReadTest {

    public static void main(String [] args){
        test(4,4,2);
    }

    /**
     * 
     * @param rowNum	行数
     * @param colNum	列数
     * @param readType	读取方式,1顺时2逆时
     */
    public static void test(int rowNum,int colNum,int readType){
    	int[][] array = new int[rowNum][colNum];
    	int index = 0;
    	for(int i=0;i<array.length;i++){
    		int[] subArray = array[i];
    		for(int j=0;j<subArray.length;j++){
    			array[i][j] = ++index ;
    		}
    	}
    	Integer[] readArray = rightRead(array,readType);
    	for(int i=0;i<readArray.length;i++){
    		System.out.print(readArray[i]+" ");
    	}
    }

    /**
     * 读取数组
     * @param array
     * @param readType	1顺时2逆时
     * @return
     */
    public static Integer[] rightRead(int[][] array,int readType){
    	//初始化
        int rowNum = array.length;
        if(rowNum<1) {
            return null;
        }
        int colNum = array[0].length;
        Integer[] readArray = new Integer[rowNum * colNum];

        Map<String,Integer> readMap = new HashMap<String,Integer>();
        NodePoint nodePoint = new NodePoint(0,0,1,readType,0);
        Integer errorStep = 0;
        do{
        	//先获得当前坐标
        	//判断坐标限制和坐标数据读取状态
        	//如果当前位置不合要求，则回退，并且前往下一个坐标，位置不合次数为4时退出
        	//如果坐标合要求,则存储数据
            Integer xIndex = nodePoint.getxIndex();
            Integer yIndex = nodePoint.getyIndex();
            boolean canGo = checkIndex(xIndex, yIndex, rowNum, colNum, readMap);
            if(!canGo){
            	errorStep ++;//记录错误次数
            	if(errorStep>=4){//错误超过4次退出，4个方向不可达
            		break;
            	}
            	nodePoint.back();//回退
            	nodePoint.changeDirection();//改变方向
            	nodePoint.forward();//前进
            	continue;
            }
            if(errorStep>0){//坐标正确，将错误次数清0
            	errorStep = 0;
            }
            Integer step = nodePoint.getStep();
            readArray[step] = array[xIndex][yIndex];
            readMap.put(xIndex+"_"+yIndex,step);
            nodePoint.forward();//前进
        } while(true);
        return readArray;
    }
    
    /**
     * 验证坐标可达性
     * @param xIndex
     * @param yIndex
     * @param rowNum
     * @param colNum
     * @param readMap
     * @return
     */
    private static boolean checkIndex(Integer xIndex,Integer yIndex,Integer rowNum,Integer colNum,Map<String,Integer> readMap){
    	//判断坐标范围
    	if(xIndex<0 || xIndex >rowNum-1 || yIndex<0 || yIndex > colNum-1){
    		return false;
    	}
    	//判断数据是否已读
    	Integer step = readMap.get(xIndex+"_"+yIndex);
        if(step != null){
        	return false;
        }
    	return true;
    }

}
