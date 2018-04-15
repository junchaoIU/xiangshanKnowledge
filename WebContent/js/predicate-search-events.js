/*
 * 属性检索页面事件脚本。
 * 
 * 作者：rosahen
 * 版本：1.0
 */

$(function(){
	
	//获取所有本体属性
	getPredicates();
	searchType = "predicateSearch";
	cur_predicate = null;
	$.ajaxSettings.async = false; 

	$("#predicate-list").on('hide.bs.select',function(e){  
	    queryByPage(e.currentTarget.value,1)
	})
})


/**
 * 获取所有本体属性的方法。
 * 
 */
function getPredicates(){
	
	//获取范围数据
	$.getJSON("predicate-getPredicates",null,function(datas){
		
		//获取id为scope的元素
		var $list = $("#predicate-list");
		
		for(var i=0;i<datas.length;i++){
			
			//创建<option>元素
			var $option = $("<option>");
			
			var $span = $("<span>").text(datas[i]);
			
			//将范围值赋给option元素
			$option.append($span);
			
			//将option添加到scope元素中
			$list.append($option);
		}
		
		$list.selectpicker('refresh');  
        $list.selectpicker('render');
	});
	
}

function queryByPage(){
	page_num = 1;
	
	switch(arguments.length){
	
	case 0:
		cur_predicate=$("#predicate").val();
		break;
	case 1:
		page_num = arguments[0]
		break;
	case 2:
		cur_predicate = arguments[0];
		page_num = arguments[1];
		break;
	}
	
	var args = {"predicate": cur_predicate, "pageNum":page_num, "pageSize":page_size, "historyType":searchType};
	
	var url = "predicate-queryByPage";
	
	var $result_frame = $("#result_frame");
	
	switchContentFrame("result_frame");
	
	resetContentFrames();
	
	$.getJSON(url, args, function(datas){
		
		for(var i=0; i<datas.length; i++){
			
			$item = createResultItem(datas[i].subject, datas[i].predicate, datas[i].object);
			
			$result_frame.append($item);
			
		}
		if(datas!=-1){
			
			var page_count = Math.ceil(queryPredicate(cur_predicate).length / page_size);
			
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
	
	var $object = $("<a>").attr("href", 'javascript:window.open("access-knowledgeSearch?'+object+'")')
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
