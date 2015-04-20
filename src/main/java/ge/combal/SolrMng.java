package ge.combal;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by vano on 4/20/15.
 */
public class SolrMng {
	private final static String url = Util.getConfig("solr.url");
	public static SolrClient solr = new HttpSolrClient(url);
	public static void addWords(HashMap<String, Counter> words) throws Exception{

		Iterator it = words.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry) it.next();
			addWord((String) pair.getKey(), (Counter) pair.getValue());
		}

		solr.commit();
	}

	private static void addWord(String word, Counter counter) throws Exception{
		SolrQuery parameters = new SolrQuery();
		parameters.set("q", "word:" + word);
		QueryResponse response = solr.query(parameters);
		SolrDocumentList list = response.getResults();
		Integer rank = counter.getCount();
		String id = null;

		if(list.getNumFound() > 0) {
			SolrDocument doc = list.get(0);
			rank += (Integer) doc.getFieldValue("rank");
			id = (String) doc.getFieldValue("id");
		}

		SolrInputDocument newDoc = new SolrInputDocument();
		if(id != null && !id.isEmpty()){
			newDoc.addField("id", id);
		}
		System.out.println("add word: \t" + rank + ",\t" + word);
		newDoc.addField("word", word);
		newDoc.addField("rank", rank);
		solr.add(newDoc);
	}
}
