package com.study.kafka;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaProducerClient {

	public static void main(String[] args) {
		//1、准备配置文件 参考:ProducerConfig.java
		Properties props = new Properties();
		//kafka集群地址，多个服务器用"，"分隔
		props.put("bootstrap.servers", "spark01:9092,spark02:9092,spark03:9092");
		//acks:消息的确认机制，默认值是0。
		//acks=0：如果设置为0，生产者不会等待kafka的响应。
		//cks=1：这个配置意味着kafka会把这条消息写到本地日志文件中，但是不会等待集群中其他机器的成功响应。
		//acks=all：这个配置意味着leader会等待所有的follower同步完成。这个确保消息不会丢失，除非kafka集群中所有机器挂掉。这是最强的可用性保证。
		props.put("acks", "all");
		//retries：配置为大于0的值的话，客户端会在消息发送失败时重新发送。
		props.put("retries", 0);
		//batch.size:当多条消息需要发送到同一个分区时，生产者会尝试合并网络请求。这会提高client和生产者的效率。
		props.put("batch.size", 16384);
		//key.serializer: 键序列化，默认org.apache.kafka.common.serialization.StringDeserializer。
		props.put("key.serializer", StringSerializer.class.getName());
		//value.deserializer:值序列化，默认org.apache.kafka.common.serialization.StringDeserializer。
		props.put("value.serializer", StringSerializer.class.getName());
		
		//创建生产者
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		
        int events = 100;
        for (int i = 100; i < events; i++){
        	long runtime = new Date().getTime();
            String ip = "192.168.1." + i;
            String msg = runtime + "时间的模拟ip：" + ip;
            //写入名为"tradeMq"的topic
            ProducerRecord<String, String> producerRecord = 
            		new ProducerRecord<String, String>("tradeMq", "key-"+i, msg);
            producer.send(producerRecord);
            System.out.println("写入tradeMq：" + msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.close();
	}
}
