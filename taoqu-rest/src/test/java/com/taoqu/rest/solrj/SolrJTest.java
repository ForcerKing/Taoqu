/**
 * 
 */
package com.taoqu.rest.solrj;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 2018年5月28日
 * SolrJTest.java
 * @author xushaoqun
 * desc:solrJ测试类
 */
public class SolrJTest {
	
	@Test
	public void addDocument() throws Exception{
		//创建连接
		HttpSolrServer solrServer = new HttpSolrServer("http://192.168.195.178:8080/solr");
		
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品1");
		document.addField("item_price", 12345);
		//把文档对象写入索引库
		solrServer.add(document);
		
		//提交
		solrServer.commit();
	}
	
	
	@Test
	public void deleteDocument() throws Exception{
		//创建连接
		HttpSolrServer solrServer = new HttpSolrServer("http://192.168.195.178:8080/solr");

		
		//solrServer.deleteById("test001");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}

}
