package com.xiangshan.semantic.utils;

import java.io.*;
import java.util.*;

import com.xiangshan.semantic.models.Segments;
import com.xiangshan.utils.RequestUtil;

//n/名词 np/人名 ns/地名 ni/机构名 nz/其它专名
//m/数词 q/量词 mq/数量词 t/时间词 f/方位词 s/处所词
//v/动词 vm/能愿动词 vd/趋向动词 a/形容词 d/副词
//h/前接成分 k/后接成分 i/习语 j/简称
//r/代词 c/连词 p/介词 u/助词 y/语气助词
//e/叹词 o/拟声词 g/语素 w/标点 x/其它 

public class WordSegment {
	
	public static String[] getSegmentResult(String keyword) {
		String [] segments = null;
		try {
			String requestURL = "http://thulac.thunlp.org/getResult";
			Map<String,String> params = new HashMap<String,String>();
			params.put("context", keyword);
			BufferedReader data = RequestUtil.requestData(requestURL, params);
			segments = data.readLine().split(" ");   
		}catch(Exception e) {
			System.out.println("分词失败!");
		}
        return segments;
	}
	
	public static Segments segmentFilter(String[] segments) {
		boolean is_pre_segment_verb = false;
		
		if (segments!=null) {
			List<String> result = new ArrayList<String>();
			for(int i=0;i<segments.length;i++) {
				String segment = segments[i];
				if(checkLegal(segment)) {
					if(!is_pre_segment_verb) {
						result.add(segment.substring(0, segment.lastIndexOf("_")));
					}else {
						result.add(segments[i-1].substring(0, segments[i-1].lastIndexOf("_")) + segment.substring(0, segment.lastIndexOf("_")));
						is_pre_segment_verb = false;
					}
				}else if(segment.contains("_v")) {
					is_pre_segment_verb = true;
					if(i==segments.length-1) {
						result.add(segment.substring(0, segment.lastIndexOf("_")));
					}
				}else {
					is_pre_segment_verb = false;
				}
			}
			if(result.size()>=2) {
				return new Segments(result.get(0),result.get(1));
			}
		}
		
		return null;
	}

	private static boolean checkLegal(String word) {
		
		return word.contains("_n")|| word.contains("_t")|| word.contains("_f")|| word.contains("_s");
	}
}
