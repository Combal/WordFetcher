package ge.combal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vano on 4/20/15.
 */
public class Fetcher {
	private String url;
	private final String userAgent = Util.getConfig("fetcher.userAgent");
	private final String referrer = Util.getConfig("fetcher.referrer");

	private String text;

	public Fetcher(String url){
		this.url = url;
	}

	private void fetch() throws IOException{
		Document doc = Jsoup.connect(this.url)
				.userAgent(userAgent)
				.referrer(referrer)
				.timeout(10000)
				.get();
		text = doc.body().text();
	}

	private HashMap<String, Counter> splitWords(){
		//([^ა-ჰ]+(\-)[ა-ჰ]+)|([^ა-ჰ\-]+)
		//text = text.replaceAll("[^ა-ჰ \\-]", "").replaceAll("( )+", " ");
		Pattern p = Pattern.compile("[ა-ჰ]+[-]?[ა-ჰ]+", Pattern.MULTILINE);
		Matcher m = p.matcher(text);
//		ArrayList<String> words2 = new ArrayList<>();
		HashMap<String, Counter> words = new HashMap<>();
		while (m.find()) {
			String word = m.group();
			Counter counter;
			if(words.containsKey(word)) {
				counter = words.get(word);
				counter.increment();
			} else {
				counter = new Counter();
			}
			words.put(word, counter);
		}
		return words;
	}

	public HashMap<String, Counter> execute() throws Exception {
		fetch();
		HashMap<String, Counter> words = splitWords();
//		for(String word : words){
//			System.out.println(word);
//		}
		return words;
	}
}
