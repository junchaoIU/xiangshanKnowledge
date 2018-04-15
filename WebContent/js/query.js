function queryKnowledge(keyword, scope){
	if(!scope) scope='所有类';
	var args = {"keyword": keyword, "scope":scope};
	var url = "knowledge-query";
	
	return query(url,args);
}

function queryDetails(keyword){
	var url = "knowledge-getDetails";
	var args = {"keyword": keyword};
	
	return query(url,args);
}

function queryPredicate(predicate){
	var args = {"predicate": predicate};
	var url = "predicate-query";
	
	return query(url,args);
}

function queryRelation(subject,object){
	var args = {"subject": subject,"object": object};
	var url = "relation-query";
	
	return query(url,args);
}

function queryStatement(subject,predicate,object){
	var args = {"subject":subject,"predicate":predicate,"object":object};
	var url = "statement-visualize";
	
	return query(url,args);
}

function queryTimes(keyword){
	var args={"keyword": cur_keyword};
	var url = "timeSpace-getTimeline";
	
	return query(url,args);
}

function queryPositions(keyword){
	var args={"keyword": cur_keyword};
	var url = "timeSpace-getPositionLine";
	
	return query(url,args);
}

function queryTimeSpaceDatas(keyword, mode){
	
	var args={"keyword": cur_keyword, "mode":mode};
	var url = "timeSpace-query";
	
	return query(url,args);
}

function queryKnowledgeByTime(keyword, time){
	var args={"keyword": cur_keyword,"time": time};
	var url = "timeSpace-queryByTime";
	
	return query(url,args);
}

function queryKnowledgeByPosition(keyword, position){
	var args={"keyword": cur_keyword,"position": position};
	var url = "timeSpace-queryByPosition";
	
	return query(url,args);
}

function queryHistory(history_type){
	var args={"historyType":history_type};
	var url = "history-get";
	
	return query(url,args);
}

function semanticAnalyse(keyword){
	var args={"keyword":keyword};
	var url = "semantic-analyse";
	
	return query(url, args);
}

function query(url,args){
	var datas = null;
	
	$.getJSON(url, args, function(results){
		datas = results;
	})
	
	return datas;
}