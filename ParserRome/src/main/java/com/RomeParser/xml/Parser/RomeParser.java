package com.RomeParser.xml.Parser;

import java.util.stream.Stream;

import java.net.URL;
import java.io.InputStreamReader;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rometools.rome.feed.synd.SyndEntry;
import java.util.*;

public class RomeParser {
	public static void main(String[] args) {
		
		Hashtable<String, String> hash1 = new Hashtable<String, String>();
		hash1.put("elp", "http://ep00.epimg.net/rss/tags/ultimas_noticias.xml");
		hash1.put("bbc", "http://feeds.bbci.co.uk/news/rss.xml");
		hash1.put("lav", "https://www.lavanguardia.com/mvc/feed/rss/home");
		hash1.put("cnn", "http://rss.cnn.com/rss/edition_europe.rss");
		hash1.put("abc", "https://sevilla.abc.es/rss/feeds/Sevilla_Sevilla.xml");
		hash1.put("elm", "https://e00-elmundo.uecdn.es/elmundo/rss/espana.xml");

        Hashtable<String, String> hash2 = new Hashtable<String, String>();
        hash2.put("elp", "El país");
        hash2.put("bbc", "BBC Headlines");
        hash2.put("lav", "La vanguardia");
        hash2.put("cnn", "CNN Headlines");
        hash2.put("abc", "ABC: Sevilla");
        hash2.put("elm", "El mundo");

        

        try {
        	SyndFeedInput input;
			SyndFeed feed; 
		    URL feedUrl;
		    List<SyndEntry> syndE = new ArrayList<SyndEntry>();
			Set<String> title = hash2.keySet();
 			Set<String> url = hash1.keySet();
  			Stream<String> stream = title.stream();
  			stream
  			.forEach(s -> System.out.println(hash2.get(s)));
			/*
  			Stream<String> stream1 = url.stream();
  			stream1
  			.forEach(t -> feedUrl = new URL(hash1.get(t)))
  			.forEach(e -> input= new SyndFeedInput())
  			.forEach(p -> feed = input.build(new XmlReader(feedUrl)))
  			.forEach(i -> System.out.println(feed.getTitle(i)))
  			.forEach(u -> syndE= feed.getEntries())
  			.limit(5)
  			.forEach(a -> System.out.println("\nTítulo: \n" + a.getTitle() + "\n Link: \n" + a.getLink() + "\n Descripción: \n" + a.getDescription().getValue() + "\n"));  */
  		}
  			catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ERROR: " + ex.getMessage());
		}



	}
}
