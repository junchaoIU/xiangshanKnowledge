package com.xiangshan.semantic.utils;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.xiangshan.ontology.models.Statement;
import com.xiangshan.semantic.models.Match;
import com.xiangshan.semantic.models.Segments;
import com.xiangshan.utils.RequestUtil;

public class Disambiguation {

	// 词林API
	private static Set<String> getSynonymsFromCiLin(String keyword) {
		try {
			String requestURL = "https://www.cilin.org/jyc/w_" + keyword + ".html";

			BufferedReader data = RequestUtil.requestData(requestURL, null);
			Set<String> synonyms = new HashSet<String>();

			while (data.read() != -1) {
				String line = data.readLine();

				if (line.contains("aboutwords")) {
					String[] parts = line.split("<p class=\"aboutwords\">");

					for (String part : parts) {
						if (part.contains("<b>近义词</b><br>汉语:")) {
							part = part.substring(part.indexOf("汉语:") + 3, part.length());
							if (part.contains("<br>")) {
								part = part.substring(0, part.indexOf("<br>"));
							}

							String[] words = part.split(",");

							for (String word : words) {
								if (!word.contains("keywordsred") && word.trim().length() >= 2) {
									synonyms.add(word.trim());
								}
							}
						}
					}
				}
			}

			return synonyms;

		} catch (Exception e) {
			System.out.println("找不到同义词!");
		}

		return null;
	}

	// 百度API
	private static Set<String> getSynonymsFromBaidu(String keyword) {
		try {
			String requestURL = "http://hanyu.baidu.com/s?wd=" + keyword + "&from=zici";
			BufferedReader data = RequestUtil.requestData(requestURL, null);
			Set<String> synonyms = new HashSet<String>();
			boolean flag = false;
			while (data.read() != -1) {
				String line = data.readLine();

				if (line != null) {
					if (line.contains("synonym")) {
						flag = true;
						continue;
					}
					if (flag) {
						if (line.contains("cf=synant&ptype=zici")) {
							String synonym = line.substring(line.indexOf(">") + 1, line.indexOf("</a>"));
							synonyms.add(synonym);

						} else if (line.contains("antonym"))
							break;
					}

				}
			}

			return synonyms;

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("找不到同义词!");
		}

		return null;
	}

	public static Set<Match> disambiguate(Segments segments) {

		Set<Match> matches = new HashSet<Match>();

		Set<String> synonyms = getSynonymsFromBaidu(segments.getPredicate());

		Iterator<String> iter = synonyms.iterator();

		while (iter.hasNext()) {

			String subject = segments.getSubject();
			String predicate = iter.next();

			Collection<Statement> statements = Query.query(subject, predicate);

			for (Statement stat : statements) {

				Match match = new Match();
				match.setRelation(predicate);

				if (stat.getSubject().equals(subject)) {
					match.setTarget(stat.getObject());
				} else {
					match.setTarget(stat.getSubject());
				}

				matches.add(match);
			}
		}

		if (matches.size() <= 0) {

			synonyms = getSynonymsFromCiLin(segments.getPredicate());

			iter = synonyms.iterator();

			while (iter.hasNext()) {

				String subject = segments.getSubject();
				String predicate = iter.next();

				Collection<Statement> statements = Query.query(subject, predicate);

				for (Statement stat : statements) {

					Match match = new Match();
					match.setRelation(predicate);

					if (stat.getSubject().equals(subject)) {
						match.setTarget(stat.getObject());
					} else {
						match.setTarget(stat.getSubject());
					}

					matches.add(match);
				}
			}
		}

		return matches;
	}
}
