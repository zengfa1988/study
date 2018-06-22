package com.tsh.algorithm.leetcode.rotateImage;

public class RotateImage {

	public static void main(String[] args) {
		int[][] a = {
		    {1,2,3,4},
		    {5,6,7,8},
		    {9,10,11,12},
		    {13,14,15,16}
		};
//		int[][] a = {
//			    {1,2},
//			    {3,4}
//			};
//		int[][] a = {
//			    {1,2,3},
//			    {4,5,6},
//			    {7,8,9}
//			};
		
		RotateCycle rotateCycle = new RotateCycle(a,1,1,0);
		for(int i=1;i<=a.length-i;i++){
			rotateCycle.setCycleNum(i);
			if(i%2==0){
				rotateCycle.setModel(2);
			}else{
				rotateCycle.setModel(1);
			}
			rotateCycle.initDirectLine();
			rotateCycle.ratate();
		}
		printArray(a);
		
	}
	
	
	
	public static void printArray(int[][] a){
		for(int[] b : a){
			for(int c : b){
				System.out.print(c+"\t");
			}
			System.out.println();
		}
	}
	
	public static void printArray(int[] a){
		for(int b : a){
			System.out.print(b+"\t");
		}
	}

}
