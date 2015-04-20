package ge.combal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vano on 4/20/15.
 */
public class Fetcher {
	private String url;
	private final String userAgent = Util.getConfig("fetcher.userAgent");
	private final String referer = Util.getConfig("fetcher.referer");

	private String text;

	public Fetcher(String url){
		this.url = url;
	}

	private void fetch() throws IOException{
		Document doc = Jsoup.connect(this.url)
				.userAgent(userAgent)
				.referrer(referer)
				.get();
		text = doc.body().text();
	}

	private ArrayList<String> splitWords(){
		//([^ა-ჰ]+(\-)[ა-ჰ]+)|([^ა-ჰ\-]+)
		//text = text.replaceAll("[^ა-ჰ \\-]", "").replaceAll("( )+", " ");
		Pattern p = Pattern.compile("[ა-ჰ]+[-]?[ა-ჰ]+", Pattern.MULTILINE);
		Matcher m = p.matcher(text);
		ArrayList<String> words = new ArrayList<>();
		while (m.find()) {
			words.add(m.group());
		}
		return words;
	}

	public ArrayList<String> execute() throws Exception {
		fetch();
		ArrayList<String> words = splitWords();
//		for(String word : words){
//			System.out.println(word);
//		}
		return words;
	}
}
