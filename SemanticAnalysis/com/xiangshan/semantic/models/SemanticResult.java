package com.xiangshan.semantic.models;

import java.util.Set;

public class SemanticResult {

	private String keyword;
	
	private Segments segments;
	
	private Set<Match> matches;
	
	public SemanticResult() {
		super();
	}
	
	public SemanticResult(String keyword, Segments segments, Set<Match> matches) {
		super();
		this.keyword = keyword;
		this.segments = segments;
		this.matches = matches;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Segments getSegments() {
		return segments;
	}

	public void setSegments(Segments segments) {
		this.segments = segments;
	}

	public Set<Match> getMatches() {
		return matches;
	}

	public void setMatches(Set<Match> matches) {
		this.matches = matches;
	}
	
	
}
