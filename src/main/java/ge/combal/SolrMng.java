package ge.combal;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
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
		solr.commit();
	}

	private static void addWord(String word) throws Exception{
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("word", word);
		doc.addField("rank", 0);
		UpdateResponse response = solr.add(doc);
	}
}
