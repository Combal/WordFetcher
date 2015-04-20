package ge.combal;

import java.util.HashMap;

/**
 * Created by vano on 4/20/15.
 */
public class Main {
	public static void main(String [] args) throws Exception{

		/**
		 * URL to parse
		 * Parsing same urls win increase ranks of the words in it
		 */
		String url = "http://mebonia.com/igavebi/nu-daashorebt-gulebs-ertmanets.html";


		Fetcher fetcher = new Fetcher(url);
		HashMap<String, Counter> words = fetcher.execute();
		SolrMng.addWords(words);
	}
}
