/*
 * 知识检索页面事件脚本。
 * 
 * 作者：rosahen
 * 版本：1.0
 */

$(function(){
	searchType = "timeSpaceSearch";
	cur_mode = 'time';
	cur_keyword = null;
	cur_time = null;
	cur_times = null;
	cur_position = null;
	cur_positions = null;
	
	$.ajaxSettings.async = false; 
	
	$("#timeSpace-list").on('hide.bs.select',function(e){  
	    queryByPage(cur_keyword,e.currentTarget.value)
	})
})


function generateTimeSpaceList(keyword){
	
	$timeSpace_list = $("#timeSpace-list");
	$timeSpace_list.empty();
	
	if(cur_mode=='time'){
		cur_times = queryTimes(keyword);
			
		for(var i=0;i<cur_times.length;i++){
			
			//创建<option>元素
			var $option = $("<option>");
			
			var $span = $("<span>").text(cur_times[i]);
			
			//将范围值赋给option元素
			$option.append($span);
			
			//将option添加到scope元素中
			$timeSpace_list.append($option);
		}
		
	}else{
		
		cur_positions = queryPositions(keyword);
		
		for(var i=0;i<cur_positions.length;i++){
			
			//创建<option>元素
			var $option = $("<option>");
			
			var $span = $("<span>").text(cur_positions[i]);
			
			//将范围值赋给option元素
			$option.append($span);
			
			//将option添加到scope元素中
			$timeSpace_list.append($option);
		}
	}
	$timeSpace_list.selectpicker('refresh');  
	$timeSpace_list.selectpicker('render');

}

function changeMode(mode_ratio){
	
	if(mode_ratio!=null) cur_mode = $(mode_ratio).val()
	
	if(cur_mode=='time'){
		$("#mode #time input").attr("checked","checked");
		$("#mode #position input").removeAttr("checked");
		$("#mode #time span").attr("class","checked");
		$("#mode #position span").removeAttr("class");
	}else{
		$("#mode #position input").attr("checked","checked");
		$("#mode #time input").removeAttr("checked");
		$("#mode #position span").attr("class","checked");
		$("#mode #time span").removeAttr("class");
	}
}

function queryByPage(){
	page_num = 1;
	switch(arguments.length){
	case 0:
		cur_keyword=$("#keyword").val();
		generateTimeSpaceList(cur_keyword);
		if(cur_mode=='time') cur_time = cur_times[0];
		else cur_position = cur_positions[0];
		break;
	case 1:
		page_num = arguments[0]
		break;
	case 2:
		cur_keyword = arguments[0];
		if (isNaN(arguments[1])){
			cur_mode = 'position';
			cur_position = arguments[1];
		}else{
			cur_mode = 'time';
			cur_time = arguments[1];
		}
		changeMode();
		break;
	}
	
	var args = {"keyword": cur_keyword, "mode":cur_mode, "time":cur_time, "position":cur_position, "pageNum":page_num, "pageSize":page_size, "historyType":searchType};
	
	var url = "timeSpace-queryByPage";
	
	var $result_frame = $("#result_frame");
	
	switchContentFrame("result_frame");
	
	resetContentFrames();
	
	$.getJSON(url, args, function(datas){
		
		console.log(datas)
		for(var i=0; i<datas.links.length; i++){
			
			title = datas.links[i].source;
			
			if(cur_keyword==title){
				title = datas.links[i].target;
			}
			
			comment = "暂无详情！"
			details = queryDetails(title);
			for(var j=0;details!=-1&&j<details.links.length;j++){
				
				if(details.links[j].value=='comment'){
					comment = details.links[j].target;
					break;
				}
			}
			relation =  datas.links[i].value;
			
			$item = createResultItem(title, relation, comment);
			
			$result_frame.append($item);
			
		}
		if(datas!=-1){
			var page_count = -1;
			if(cur_mode=='time') page_count = Math.ceil(queryKnowledgeByTime(cur_keyword,cur_time).data.links.length / page_size);
			else page_count = Math.ceil(queryKnowledgeByPosition(cur_keyword,cur_position).data.links.length / page_size);
			
			var $pagination = createPagination(page_num, page_count);
			
			$result_frame.append($pagination);
		}

	});
}

function visualizeResult(){
	
	if(cur_keyword!=null){
		$graph_frame = $("#graph_frame");
		
		if($graph_frame.is(":hidden") && $graph_frame.children().length==0){
			datas = queryTimeSpaceDatas(cur_keyword, cur_mode);
			
			timeSpaceVisualize(datas, 'graph_frame');
		}
	}
	
	switchContentFrame("graph_frame");
}


function createResultItem(name, relation, comment){
	
	var $item = $("<div>").attr("class", "result-item");
	
	var $title = $("<a>").attr("href","#")
					.attr("class", "result-item-name")
					.text(name);
	
	var $relation = $("<span>").attr("class","result-item-relation")
								.text("("+relation+")");
	
	$item.append($title);
	$item.append($relation);
	$item.append($("<br>"));
	
	var $comment = $("<p>").attr("class","result-item-comment")
					.text(comment);

	$item.append($comment);
	
	$button1 = $("<button>").text("查看图谱").attr("class", "btn btn-default").css("position","absolute").css("left","530px");
	$button1.click(function(){
		popupGraph(name);
	});
	$button2 = $("<button>").text("查看百科").attr("class", "btn btn-default").css("position","absolute").css("left","630px");
	$button2.click(function(){
		popupEncyclopedia(name);
	});
	$item.append($button1);
	$item.append($button2);
	
	return $item;
}
