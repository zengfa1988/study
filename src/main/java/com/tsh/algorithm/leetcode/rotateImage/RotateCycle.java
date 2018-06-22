package com.tsh.algorithm.leetcode.rotateImage;

import java.util.ArrayList;
import java.util.List;

public class RotateCycle {

	private Integer model;//1顺时,2逆时
	private Integer step;//1为90度
	private Integer cycleNum;//从外向里第几圈
	private List<ArrayLine> lines = null;
	private int[][] a;//矩阵大小
	
	public RotateCycle(){
		
	}
	public RotateCycle(int[][] a,Integer model,Integer step,Integer cycleNum){
		this.a = a;
		this.model = model;
		this.step = step;
		this.cycleNum = cycleNum;
	}
	
	public void initDirectLine(){
		int start = cycleNum -1;
		int end = a.length - cycleNum;
		ArrayLine top = new ArrayLine(start,end,4);
		ArrayLine right = new ArrayLine(start,end,1);
		ArrayLine button = new ArrayLine(start,end,2);
		ArrayLine left = new ArrayLine(start,end,3);
		lines = new ArrayList<ArrayLine>();
		if(model == 1){
			lines.add(top);
			lines.add(right);
			lines.add(button);
			lines.add(left);
		}else{
			lines.add(top);
			lines.add(left);
			lines.add(button);
			lines.add(right);
		}
		
		setRotateNextLine();
	}
	
	public void ratate(){
		//获取顶部数组
		int[] subArray = getSubArray();
		for(int i=lines.size()-1;i>=0;i--){
			ArrayLine line = lines.get(i);
			ratateArray(line,subArray,i==0?true:false);
		}
	}
	
	/**
	 * 旋转数组
	 * @param line
	 * @param temp
	 * @param last
	 */
	public void ratateArray(ArrayLine line,int[] temp,boolean last){
		ArrayLine nextLine = line.getNextLine();
		
		int position = line.getPosition();
		if(position==3){
			if(nextLine.getPosition()==4){//左-上
				for(int i=nextLine.getStart(),j=line.getEnd();i<=nextLine.getEnd()-1;i++,j--){
					a[cycleNum-1][i] = a[j][cycleNum-1];
				}
//				a[0][1] = a[1][0];
//				a[0][0] = a[2][0];
			}else if(nextLine.getPosition()==2){//左-下
				for(int i=nextLine.getStart(),j=line.getStart();i<=nextLine.getEnd()-1;i++,j++){
					a[a.length-cycleNum][i] = a[j][cycleNum-1];
				}
			}
		}else if(position==2){
			if(nextLine.getPosition()==3){//下-左
				for(int i=nextLine.getStart()+1,j=line.getStart()+1;i<=nextLine.getEnd();i++,j++){
					a[i][cycleNum-1] = a[a.length-cycleNum][j];
				}
//				a[1][0] = a[2][1];
//				a[2][0] = a[2][2];
			}else if(nextLine.getPosition()==1){//下-右
				for(int i=nextLine.getStart()+1,j=line.getEnd()-1;i<=nextLine.getEnd();i++,j--){
					a[i][a.length-cycleNum] = a[a.length-cycleNum][j];
				}
			}
		}else if(position==1){
			if(nextLine.getPosition()==2){//右-下
				for(int i=nextLine.getStart()+1,j=line.getEnd()-1;i<=nextLine.getEnd();i++,j--){
					a[a.length-cycleNum][i] = a[j][a.length-cycleNum];
				}
//				a[2][1] = a[1][2];
//				a[2][2] = a[0][2];
			}else if(nextLine.getPosition()==4){//右-上
				for(int i=nextLine.getStart()+1,j=line.getStart()+1;i<=nextLine.getEnd();i++,j++){
					a[cycleNum-1][i] = a[j][a.length-cycleNum];
				}
			}
		}else if(position==4){
			if(nextLine.getPosition()==1){//上-右
				for(int i=nextLine.getEnd()-1,j=line.getEnd()-1;i>=nextLine.getStart();i--,j--){
					if(last){
						a[i][a.length-cycleNum] = temp[j];
					}else{
						a[i][a.length-cycleNum] = a[cycleNum-1][j];
					}
				}
//				a[1][2] = a[0][1];
//				a[0][2] = a[0][0];
			}else if(nextLine.getPosition()==3){//上-左
				for(int i=nextLine.getStart(),j=line.getEnd();i<=nextLine.getEnd()-1;i++,j--){
					if(last){
						a[i][cycleNum-1] = temp[j];
					}else{
						a[i][cycleNum-1] = a[cycleNum-1][j];
					}
				}
			}
		}
	}
	
	public int[] getSubArray(){
		ArrayLine firstLine = lines.get(0);
		
//		int length = 0;
		int position = firstLine.getPosition();
//		length = firstLine.getEnd() - firstLine.getStart();
//		if(length==0){
//			return null;
//		}
		int[] subArray = new int[a.length];
		
		if(position == 4){
//			int index = 0;
//			if(model == 2){
//				index = subArray.length;
//			}
			for(int i=0;i<subArray.length;i++){
				subArray[i] = a[cycleNum-1][i];
			}
			/*
			for(int i=firstLine.getStart();i<=firstLine.getEnd();i++ ){
				subArray[index] = a[0][i];
//				if(model == 1){
//					index++;
//				}else{
//					index--;
//				}
			}
			*/
		}
		return subArray;
	}
	
	//设置每条线的下个存放线
	public void setRotateNextLine(){
		for(int i=0;i<lines.size();i++){
			ArrayLine line = lines.get(i);
			int j = i;
			j = i+step;
			if(j>=lines.size()){
				j=0;
			}
			line.setNextLine(lines.get(j));
		}
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getCycleNum() {
		return cycleNum;
	}
	public void setCycleNum(Integer cycleNum) {
		this.cycleNum = cycleNum;
	}
	public List<ArrayLine> getLines() {
		return lines;
	}
	public void setLines(List<ArrayLine> lines) {
		this.lines = lines;
	}
	public int[][] getA() {
		return a;
	}
	public void setA(int[][] a) {
		this.a = a;
	}
	
	
}
