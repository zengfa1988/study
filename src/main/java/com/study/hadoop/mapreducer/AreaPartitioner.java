package com.study.hadoop.mapreducer;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 流量用户统计按手机号分区文件
 * @author zengfa
 *
 * @param <KEY>
 * @param <VALUE>
 */
public class AreaPartitioner<KEY, VALUE> extends Partitioner<KEY, VALUE>{

	private static Map<String,Integer> areaMap = new HashMap<String,Integer>();
	static{
		areaMap.put("136", 0);
		areaMap.put("137", 1);
		areaMap.put("138", 2);
		areaMap.put("139", 3);
	}
	
	@Override
	public int getPartition(KEY key, VALUE value, int numPartitions) {
		String phone = key.toString();
		String area = phone.substring(0, 3);
		Integer index = areaMap.get(area);
		if(index == null){
			index = 4;
		}
		return index;
	}

}
