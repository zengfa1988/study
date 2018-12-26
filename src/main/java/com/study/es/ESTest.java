package com.study.es;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 参考：
 * https://blog.csdn.net/chengyuqiang/article/details/79124029
 * https://my.oschina.net/zchuanzhao/blog/1843476
 * https://blog.csdn.net/moxiong3212/article/details/79345273
 * @author Thinkpad
 *
 */
public class ESTest {

	private static TransportClient client = null;
	
	public static void main(String[] args) throws Exception{
		initClient();
		createIndex();
//		deleteIndex();
//		createIndexSetting();
//		indexIsExist();
//		createIndexMapping();
		
//		addDoc();
//		getDoc();
//		updateDoc();
//		deleteDoc();
//		searchDoc();
	}
	
	public static void testGetClient() throws Exception {
		//1.设置集群名称
		Settings settings = Settings.builder().put("cluster.name", "my-es").build();
		//2.创建client
		TransportClient client = new PreBuiltTransportClient(settings);
		String host = "192.168.0.106";
		TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(host), 9300);
		client.addTransportAddress(transportAddress);
		
		System.out.println(client.toString());
	}
	
	public static void initClient() throws Exception{
		//1.设置集群名称
		Settings settings = Settings.builder().put("cluster.name", "my-es").build();
		//2.创建client
		client = new PreBuiltTransportClient(settings);
		String host = "192.168.0.112";
		TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(host), 9300);
		client.addTransportAddress(transportAddress);
	}
	
	/**
	 * 创建索引
	 */
	public static void createIndex() {
		String index = "index1";
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		CreateIndexResponse ciReponse = indicesAdminClient.prepareCreate(index).get();
		System.out.println(ciReponse.isAcknowledged());
	}
	
	/**
	 * 删除索引
	 */
	public static void deleteIndex() {
		String index = "index1";
		AcknowledgedResponse acknowledgedResponse = client.admin().indices()
				.prepareDelete(index).execute().actionGet();
		System.out.println(acknowledgedResponse.isAcknowledged());
		
	}
	
	/**
	 * 创建索引并设置setting
	 */
	public static void createIndexSetting() {
		int shards = 3;
		int replicas = 2;
		String index = "index1";
		Settings settings = Settings.builder()
				.put("index.number_of_shards", shards)
				.put("index.number_of_replicas", replicas)
				.build();
		CreateIndexResponse ciReponse = client.admin().indices().prepareCreate(index)
				.setSettings(settings)
				.execute().actionGet();
		System.out.println(ciReponse.isAcknowledged());
	}
	
	/**
	 * 判断索引是否存在
	 */
	public static void indexIsExist() {
		String index = "index1";
		IndicesExistsResponse response = client.admin().indices().prepareExists(index).get();
		System.out.println(response.isExists());
	}
	
	/**
	 * 先要创建索引，再设置mapping
	 * @throws Exception
	 */
	public static void createIndexMapping() throws Exception{
		createIndex();
		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()
				.startObject("properties")
				.startObject("id")
				.field("type", "long")
				.endObject()
				.startObject("title")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
				.field("search_analyzer", "ik_max_word")
				.field("boost", 2)
				.endObject()
				.startObject("content")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
				.field("search_analyzer", "ik_max_word")
				.endObject()
				.startObject("postdate")
				.field("type", "date")
				.field("format", "yyyy-MM-dd HH:mm:ss")
				.endObject()
				.startObject("url")
				.field("type", "keyword")
				.endObject()
				.endObject()
				.endObject();
		System.out.println(builder.toString());
		String index = "index1";
		String type = "blog";
		AcknowledgedResponse acknowledgedResponse = client.admin().indices()
				.preparePutMapping(index).setType(type)
				.setSource(builder).get();
		System.out.println(acknowledgedResponse.isAcknowledged());
	}
	
	/**
	 * 添加一个文档
	 * @throws Exception
	 */
	public static void addDoc() throws Exception{
		String index = "index1";
		String type = "blog";
		List<Category> categorys=new ArrayList<>(); 
		Category category1=new Category("112", "123", "意外险", null); 
		Category category2=new Category("112", "126", "财产险", "0"); 
		Collections.addAll(categorys, category1,category2);
		ProductPlan plan=new ProductPlan("004", "成人户外保险", "全面保障你的财产安全", 3500, categorys, "112", null);
		ObjectMapper mapper =new ObjectMapper();
		String json = mapper.writeValueAsString(plan);
		//此处setSource()的参数不能是json串了，要把他转化成了map
		//参考https://blog.csdn.net/nicodeme/article/details/83088931
		Map<String,Object> map = JSONObject.parseObject(json, Map.class);
		IndexResponse response=client.prepareIndex(index, type).setSource(map).get();
		System.out.println(response.status().getStatus());
		//创建成功 返回的状态码是201
		if(response.status().getStatus()==201){
			System.out.println(response.getId());
		}
	}
	
	/**
	 * 查询一个文档
	 */
	public static void getDoc() throws Exception{
		String index = "index1";
		String type = "blog";
		GetResponse response = client.prepareGet(index, type, "p27p1WcBTNkJTNabil0u").get();
		System.out.println(response.getSource());
		Map<String,Object> map = response.getSource();
//		map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
		ObjectMapper mapper =new ObjectMapper();
		//得到Json类型的结果 可以转成对象
		ProductPlan plan = mapper.readValue(response.getSourceAsString(), ProductPlan.class);
		System.out.println(JSONObject.toJSONString(plan));
	}
	
	/**
	 * 更新文档
	 * @throws Exception
	 */
	public static void updateDoc() throws Exception{
		String index = "index1";
		String type = "blog";
		
		//方案一 使用对象进行更新的话 如果对象中值为空的属性 都会被更新为null 数值更新为0
		/*
		ProductPlan plan=new ProductPlan();
		plan.setName("成人");
		ObjectMapper mapper =new ObjectMapper();
        String json=mapper.writeValueAsString(plan);
        Map<String,Object> dataMap = JSONObject.parseObject(json, Map.class);
        */
		
		//方案二 更新需要更新的属性
		XContentBuilder json=XContentFactory.jsonBuilder();
		json.startObject().field("id", "005").field("name", "成人意外保险").field("price", 6000).endObject();
		
		UpdateRequest request = new UpdateRequest(index, type, "p27p1WcBTNkJTNabil0u");
		request.doc(json);
//		request.doc(dataMap);
		UpdateResponse response=client.update(request).get();
		if(response.status().getStatus()==200){
			System.out.println("更新成功");
		}
	}
	
	/**
	 * 删除文档
	 */
	public static void deleteDoc() {
		String index = "index1";
		String type = "blog";
		DeleteResponse response = client.prepareDelete(index, type, "qW4R1mcBTNkJTNabIF1e").get();
		if(response.status().getStatus()==200){
            System.out.println("删除成功");
        }
	}
	
	/**
	 * 查询文档
	 */
	public static void searchDoc() {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price").from(3500).to(4000);
		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("productId", "111");
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
				.must(rangeQueryBuilder)
				.must(matchQueryBuilder);
		
		//使用通配符‘*’来前缀匹配index开头的所有索引库
		SearchResponse response = client.prepareSearch("index*")
//				.setTypes("")
				//设置查询类型
				// 1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询
				// 2.SearchType.SCAN = 扫描查询,无序
				// 3.SearchType.COUNT = 不设置的话,这个为默认值,还有的自己去试试吧
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				// 设置查询关键词
				.setQuery(boolQueryBuilder)
				// 设置查询数据的位置,分页用
				.setFrom(0)
				// 设置查询结果集的最大条数
				.setSize(100)
				// 设置是否按查询匹配度排序
				.setExplain(true)
				// 最后就是返回搜索响应信息
				.execute().actionGet();
		
		SearchHits searchHits = response.getHits();
		System.out.println(searchHits.getTotalHits());
		for(SearchHit searchHit:searchHits.getHits()){
			Map<String, Object> sourceAsMap=searchHit.getSourceAsMap();
			System.out.println(JSONObject.toJSONString(sourceAsMap));
		}
	}

}
