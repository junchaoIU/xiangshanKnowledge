package com.xiangshan.semantic.service.impls;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.ontology.models.Statement;
import com.xiangshan.semantic.models.Match;
import com.xiangshan.semantic.models.Segments;
import com.xiangshan.semantic.models.SemanticResult;
import com.xiangshan.semantic.service.interfaces.SemanticAnalyseService;
import com.xiangshan.semantic.utils.Disambiguation;
import com.xiangshan.semantic.utils.Query;
import com.xiangshan.semantic.utils.WordSegment;
import com.xiangshan.utils.JsonUtil;

@SuppressWarnings("rawtypes")
public class SemanticAnalyseServiceImpl extends BaseService implements SemanticAnalyseService{

	@Override
	public String analyse(String keyword) {
		// TODO Auto-generated method stub
		if(keyword!=null&&!keyword.trim().equals("")) {
			
			String[] results = WordSegment.getSegmentResult(keyword);
			
			Segments segments = WordSegment.segmentFilter(results);
			
			System.out.println(segments);
			
			if(segments!=null) {
				String subject = segments.getSubject();
				String predicate = segments.getPredicate();
				
				Collection<Statement> statements = Query.query(subject, predicate);
				
				Set<Match> matches = new HashSet<Match>();;
				
				if(statements.size()>0) {
					
					for(Statement stat : statements) {
						
						Match match = new Match();
						match.setRelation(stat.getPredicate());
						
						if(stat.getSubject().equals(subject)) {
							match.setTarget(stat.getObject());
						}else {
							match.setTarget(stat.getSubject());
						}
						
						matches.add(match);
					}
					
				}else {
					
					matches = Disambiguation.disambiguate(segments);
				}
				
				SemanticResult sr =  new SemanticResult(keyword, segments, matches);
				
				return JsonUtil.formatAsJson(sr);
			}
		}
		return null;
	}
	

}
