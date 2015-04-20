package ge.combal;

import java.util.ArrayList;

/**
 * Created by vano on 4/20/15.
 */
public class Main {
	public static void main(String [] args) throws Exception{
		Fetcher fetcher = new Fetcher("http://oxun.ge/index.php?newsid=152991");
		ArrayList<String> words = fetcher.execute();
		SolrMng.addWords(words);
	}
}
