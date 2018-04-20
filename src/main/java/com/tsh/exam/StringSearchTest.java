package com.tsh.exam;

public class StringSearchTest {

    public static void main(String[] args){
        String testString = "BBC ABCDAB ABCDABCDABDE";
        String templetStr = "ABCDABD";

        int index =test2(testString,templetStr);
        System.out.println(index);
    }

    public static int test2(String testString,String templetStr){
        for(int index=0;index<=testString.length()-templetStr.length();index++){
            for(int j=0;j<=templetStr.length();j++){
                if(testString.charAt(index + j) != templetStr.charAt(j)){
                    break;
                }
                if(j==templetStr.length()-1){
                    return index;
                }
            }
        }
        return -1;
    }

    public static int test1(String testString,String templetStr){
        int index = -1;
        int returnFlag = 0;
        for(int i=0;i<=testString.length()-templetStr.length();i++){
            for(int j=0;j<=templetStr.length();j++){
                if(testString.charAt(i) != templetStr.charAt(j)){
                    if(index>0){
                        i = index;
                    }
                    index = -1;
                    break;
                }
                if(index == -1){
                    index = i;
                }
                i++;
                if(j==templetStr.length()-1){
                    returnFlag = 1;
                    break;
                }
            }
            if(returnFlag == 1){
                break;
            }
        }
        return index;
    }
}
