package ge.combal;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.util.ArrayList;

/**
 * Created by vano on 4/20/15.
 */
public class SolrMng {
	private final static String url = Util.getConfig("solr.url");
	public static SolrClient solr = new HttpSolrClient(url);
	public static void addWords(ArrayList<String> words) throws Exception{
		for(String word : words){
			addWord(word);
		}
//		solr.commit();
	}

	private static void addWord(String word) throws Exception{
		SolrQuery parameters = new SolrQuery();
		parameters.set("q", "word:" + word);
		QueryResponse response = solr.query(parameters);
		SolrDocumentList list = response.getResults();
		Integer rank = 0;
		String id = null;

		if(list.getNumFound() > 0) {
			SolrDocument doc = list.get(0);
			rank = (Integer) doc.getFieldValue("rank") + 1;
			id = (String) doc.getFieldValue("id");
		}

		SolrInputDocument newDoc = new SolrInputDocument();
		if(id != null && !id.isEmpty()){
			newDoc.addField("id", id);
		}
		System.out.println("add word: " + word + ", " + rank + ", " + id);
		newDoc.addField("word", word);
		newDoc.addField("rank", rank);
		solr.add(newDoc);
		solr.commit();
	}
}
