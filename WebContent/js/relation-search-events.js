/*
 * 关系检索页面事件脚本。
 * 
 * 作者：rosahen
 * 版本：1.0
 */

$(function(){
	
	searchType = "relationSearch";
	cur_subject = null;
	cur_object = null;
	
	$.ajaxSettings.async = false; 
})


function queryByPage(){
	page_num = 1;
	
	switch(arguments.length){
	
	case 0:
		cur_subject = $("#subject").val();
		
		cur_object = $("#object").val();
		break;
	case 1:
		page_num = arguments[0]
		break;
	case 2:
		cur_subject = arguments[0];
		cur_object = arguments[1];
		break;
	}
	
	var args = {"subject": cur_subject, "object":cur_object, "pageNum":page_num, "pageSize":page_size, "historyType":searchType};
	
	var url = "relation-queryByPage";
	
	var $result_frame = $("#result_frame");
	
	switchContentFrame("result_frame");
	
	resetContentFrames();
	
	relations = new Set();
	
	$.getJSON(url, args, function(datas){
		
		for(var i=0; i<datas.length; i++){
			
			
			relation =  datas[i].predicate;
			
			if(relations.has(relation)){
				continue;
			}
			
			relations.add(relation);
			
			$item = createResultItem(datas[i].subject, relation, datas[i].object);
			
			$result_frame.append($item);
			
		}
		if(datas!=-1){
			
			var page_count = Math.ceil(queryRelation(cur_subject,cur_object).length / page_size);
			
			var $pagination = createPagination(page_num, page_count);
			
			$result_frame.append($pagination);
		}

	});
}

function createResultItem(subject, relation, object){
	
	var $item = $("<div>").attr("class", "result-item");
	
	var $subject = $("<a>").attr("href",'javascript:window.open("access-knowledgeSearch?'+subject+'")')
							.attr("class", "result-item-name")
							.text(subject);
	
	var $relation = $("<span>").attr("class","result-item-relation")
								.text(relation);
	
	var $object = $("<a>").attr("href",'javascript:window.open("access-knowledgeSearch?'+object+'")')
							.attr("class", "result-item-name")
							.text(object);
	$item.append($subject);
	$item.append($relation);
	$item.append($object);
	
	$button1 = $("<button>").text("查看图谱")
							.attr("class", "btn btn-default")
							.attr("onclick","visualizeStatement('"+subject+"','"+relation+"','"+object+"')")
							.css("position","absolute")
							.css("left","530px");
	$button2 = $("<button>").text("查看百科")
							.attr("class", "btn btn-default")
							.attr("onclick","generateMultiEncyclopedia('"+subject+"','"+object+"')")
							.css("position","absolute")
							.css("left","630px");
	
	$item.append($button1);
	$item.append($button2);
	
	return $item;
}


/**
 * 可视化三元组函数。
 * 
 * @param subject 三元组的主语。
 * @param predicate 三元组的谓语。
 * @param object 三元组的宾语。
 */
function visualizeStatement(subject,predicate,object){
	datas = queryStatement(subject,predicate,object);
	
	normalVisualize(datas,'graph_frame');
	
	switchContentFrame("graph_frame");
}
