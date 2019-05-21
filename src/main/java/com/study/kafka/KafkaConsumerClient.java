package com.study.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerClient {

	public static void main(String[] args) {
        Properties props = new Properties();
        //集群地址，多个地址用"，"分隔
        props.put("bootstrap.servers","spark01:9092,spark02:9092,spark03:9092");
        //设置消费者的group id
        props.put("group.id", "trade1");
        //如果为真，consumer所消费消息的offset将会自动的同步到zookeeper。如果消费者死掉时，由新的consumer使用继续接替
        props.put("enable.auto.commit", "true");
        //consumer向zookeeper提交offset的频率
        //设置多久一次更新被消费消息的偏移量
        props.put("auto.commit.interval.ms", "1000");
        //超时时间。设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
        props.put("session.timeout.ms", "30000");
        //一次最大拉取的条数。
//        props.put("max.poll.records", 1000);
        //消费规则，默认earliest 。
        //earliest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费 。
        //latest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据 。
        //none: topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常。
        props.put("auto.offset.reset", "earliest");
        //key.serializer: 键序列化，默认org.apache.kafka.common.serialization.StringDeserializer。
        props.put("key.deserializer", StringDeserializer.class.getName());
        //value.deserializer:值序列化，默认org.apache.kafka.common.serialization.StringDeserializer。
        props.put("value.deserializer", StringDeserializer.class.getName());
        
        //创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        // 订阅topic，可以为多个用,隔开，此处订阅了"tradeMq"主题
        consumer.subscribe(Arrays.asList("tradeMq"));
        //持续监听
        while(true){
            //poll频率
            ConsumerRecords<String,String> consumerRecords = consumer.poll(100);
            for(ConsumerRecord<String,String> consumerRecord : consumerRecords){
                System.out.println("在tradeMq中读到：" + consumerRecord.key()+">>"+consumerRecord.value());
            }
        }
	}
}
