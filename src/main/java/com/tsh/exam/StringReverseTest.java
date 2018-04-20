package com.tsh.exam;

public class StringReverseTest {
    public static void main(String[] args){
        String a = "abcdef";
        String reverStr = reverse2(a);

        System.out.println(reverStr);
    }

    public static String reverse2(String str){
        StringBuffer sb = new StringBuffer();
        for(int i=str.length()-1;i>=0;i--){
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static String reverse1(String str){
        int length = str.length();
        char[] charSeq = new char[length];
        for(int i=str.length()-1;i>=0;i--){
            charSeq[length-1-i] = str.charAt(i);
        }
        return new String(charSeq);
    }
}
