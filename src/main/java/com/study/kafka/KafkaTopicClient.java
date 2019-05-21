package com.study.kafka;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartitionInfo;

/**
 * 参考：https://blog.csdn.net/u013256816/article/details/79996056
 * @author Thinkpad
 *
 */
public class KafkaTopicClient {

	private static final String brokerUrl = "spark01:9092,spark02:9092,spark03:9092";
	
	public static void main(String[] args) throws Exception{
//		listTopics();
		createTopic();
//		topicDetail();
//		deleteTopic();
	}
	
	/**
	 * 显示所有主题
	 * @throws Exception
	 */
	public static void listTopics() throws Exception{
		Properties properties = new Properties();
		properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, brokerUrl);
		AdminClient adminClient = AdminClient.create(properties);
		ListTopicsResult result = adminClient.listTopics();
		KafkaFuture<Collection<TopicListing>> kafkaFuture = result.listings();
		Collection<TopicListing> collections = kafkaFuture.get();
		for(TopicListing topicListing : collections) {
			String topicName = topicListing.name();
			System.out.println(topicName);
		}
		adminClient.close();
	}
	
	/**
	 * 创建主题
	 * @throws Exception
	 */
	public static void createTopic() throws Exception{
		Properties properties = new Properties();
		properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, brokerUrl);
		AdminClient adminClient = AdminClient.create(properties);
        NewTopic newTopic = new NewTopic("tradeMq",1,(short)1);
		adminClient.createTopics(Arrays.asList(newTopic));
		adminClient.close();
        System.out.println("创建主题成功");
	}
	
	public static void topicDetail() throws Exception{
		Properties properties = new Properties();
		properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, brokerUrl);
		AdminClient adminClient = AdminClient.create(properties);
		DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList("tradeMq","orderMq"));
		Map<String, KafkaFuture<TopicDescription>> mapFuture = result.values();
		for(String name : mapFuture.keySet()) {
			System.out.println(name);
			KafkaFuture<TopicDescription> future = mapFuture.get(name);
			TopicDescription descript = future.get();
			System.out.println(descript.name());
			for(TopicPartitionInfo topicPartitionInfo : descript.partitions()) {
				System.out.println(topicPartitionInfo.leader().id());
				System.out.println(topicPartitionInfo.partition());
//				System.out.println(topicPartitionInfo.);
				
			}
		}
		adminClient.close();
	}
	
	/**
	 * 删除主题
	 * @throws Exception
	 */
	public static void deleteTopic() throws Exception{
		Properties properties = new Properties();
		properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, brokerUrl);
		AdminClient adminClient = AdminClient.create(properties);
		adminClient.deleteTopics(Arrays.asList("tradeMq"));
		adminClient.close();
		System.out.println("删除主题成功");
	}
}
