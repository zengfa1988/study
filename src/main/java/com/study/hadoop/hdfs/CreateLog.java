package com.study.hadoop.hdfs;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CreateLog {

	public static void main(String[] args) throws Exception{
		Logger logger = LogManager.getLogger("testlog");
		int i = 0;
		while(true){
			logger.info(new Date().toString() + "-----------------------------");
			i++;
			Thread.sleep(500);
			if(i>1000000){
				break;
			}
		}
	}
}
