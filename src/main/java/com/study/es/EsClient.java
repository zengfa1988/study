package com.study.es;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSONObject;

public class EsClient {

	static Map<String, String> m = new HashMap<String, String>();

	// 设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
	static Settings settings = Settings.builder().put("cluster.name", "elasticsearch")// 设置ES实例的名称
			.put("client.transport.sniff", true)// 自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
			.build();

	// 取得实例
	public static synchronized TransportClient getTransportClient() {
		InetAddress addr;
		try {
			addr = InetAddress.getByName("192.168.103.21");
			InetSocketAddress ip = new InetSocketAddress(addr, 9300);
			TransportAddress transportAddress = new TransportAddress(ip);
			TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(transportAddress);
			return client;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
//		delete();
//		get();
//		search();
		jsonBuilderTest();
		/*
		try {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", "3");  
	         hashMap.put("title","双宿双飞从");
	         hashMap.put("describe", "测试123");  
	         hashMap.put("author", "测试doc");  
            TransportClient client=getTransportClient();
           IndexResponse response = client.prepareIndex("tbname", "type",hashMap.get("id").toString())
                   .setSource(hashMap).execute().actionGet();  
           System.out.println("主键id"+response.getId());;  
       } catch (Exception e) {
           e.printStackTrace();
       }
       */
	}
	
	public static void get(){
		TransportClient client=getTransportClient();
		GetResponse response = client.prepareGet("tbname", "type", "3").setOperationThreaded(false).get();
		Map source = response.getSource();
		System.out.println(JSONObject.toJSONString(source));
	}
	
	public static void delete(){
		TransportClient client=getTransportClient();
		DeleteResponse response = client.prepareDelete("tbname", "type", "3").get();
//		Result source = response.getResult();
	}
	
	public static void search(){
		TransportClient client=getTransportClient();
		SearchResponse response = client.prepareSearch("tbname")
				.get();
		SearchHit[] hits = response.getHits().getHits();
		long length = response.getHits().totalHits;
		System.out.println(length);
		for(SearchHit hit : hits){
			Map<String, Object> source = hit.getSourceAsMap();
			System.out.println(JSONObject.toJSONString(source));
		}
	}
	
	public static void jsonBuilderTest(){
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
				.field("user","kimchy")
				.field("postDate",new Date())
				.field("message", "Elasticsearch")
				.endObject();
			String json = builder.string();
			System.out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
