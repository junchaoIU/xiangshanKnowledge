/*
 * 知识检索页面事件脚本。
 * 
 * 作者：rosahen
 * 版本：1.0
 */

$(function(){
	
	
	//调用获取检索范围的函数
	getScopes();
	
	searchType = "knowledgeSearch";
	cur_keyword = null;
	cur_scope = null;
	
	$.ajaxSettings.async = false; 
	
	if (window.location.search!=''){
		keyword =  decodeURI(window.location.search.substr(1));
		queryByPage(keyword,'所有类');
	}
	
	$("#knowledge-scope").on('hide.bs.select',function(e){  
	    queryByPage(cur_keyword,e.currentTarget.value);
	})
})

/**
 * 获取检索范围函数。
 * 
 */
function getScopes(){
	
	//获取范围数据
	$.getJSON("catalog-getTopClasses",null,function(datas){
		
		//获取id为scope的元素
		var $scope = $("#knowledge-scope");
		
		for(var i=0;i<datas.length;i++){
			
			//创建<option>元素
			var $option = $("<option>");
			
			//将范围值赋给option元素
			$option.text(datas[i].name);
			
			//将option添加到scope元素中
			$scope.append($option);
		}
		$scope.selectpicker('refresh');  
		$scope.selectpicker('render');
	});
}

function queryByPage(){
	page_num = 1;
	switch(arguments.length){
	
	case 0:
		cur_keyword=$("#keyword").val();
		
		cur_scope = $("#knowledge-scope option:selected").val();
		break;
	case 1:
		page_num = arguments[0];
		break;
	case 2:
		cur_keyword = arguments[0];
		cur_scope = arguments[1];
		break;
	}
	if(cur_keyword!=null&&$.trim(cur_keyword)!=''){
		var args = {"keyword": cur_keyword, "scope":cur_scope, "pageNum":page_num, "pageSize":page_size, "historyType":searchType};
		
		var url = "knowledge-queryByPage";
		
		var $result_frame = $("#result_frame");
		
		switchContentFrame("result_frame");
		
		resetContentFrames();
	
		$.getJSON(url, args, function(datas){
			
			if(datas!=-1){
				for(var i=0;i<datas.links.length; i++){
					
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
				var page_count = Math.ceil(queryKnowledge(cur_keyword,cur_scope).links.length / page_size);
				var $pagination = createPagination(page_num, page_count);
				
				$result_frame.append($pagination);
				
			}else{
				semantic = semanticAnalyse(cur_keyword);
				if(semantic!=-1){
					if(semantic.matches.length>0){
						$warning = $("<p>").html('找不到和<span>“'+cur_keyword+'”</span>相关的结果,您可能想找？').attr("id","warning");
						$result_frame.append($warning);
					}
					for(let i in semantic.matches){
						title = semantic.matches[i].target;
						comment = "暂无详情！"
						details = queryDetails(title);
						
						for(var j=0;details!=-1&&j<details.links.length;j++){
							
							if(details.links[j].value=='comment'){
								comment = details.links[j].target;
								break;
							}
						}
						relation =  semantic.matches[i].relation;
						
						$item = createResultItem(title, relation, comment);
						
						$result_frame.append($item);
					}
				}
			}
	
		});
	}
}

function visualizeResult(){
	
	if(cur_keyword!=null){
		$graph_frame = $("#graph_frame");
		
		if($graph_frame.is(":hidden") && $graph_frame.children().length==0){
			datas = queryKnowledge(cur_keyword, cur_scope);
			normalVisualize(datas,'graph_frame');
		}
	}
	
	switchContentFrame("graph_frame");
}


function createResultItem(name, relation, comment){
	
	var $item = $("<div>").attr("class", "result-item");
	
	var $title = $("<a>").attr("class", "result-item-name")
					.attr("onclick", "queryByPage('"+title+"','所有类')")
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
	})
	$button2 = $("<button>").text("查看百科").attr("class", "btn btn-default").css("position","absolute").css("left","630px");
	$button2.click(function(){
		popupEncyclopedia(name);
	})
	$item.append($button1);
	$item.append($button2);
	
	return $item;
}
