/*
 * 目录事件脚本。
 * 
 * 作者：rosahen
 * 版本：1.0
 */

$(function(){
	
	//调用目录初始化函数
	initCatalog();
	
	$card_tabs = [$("#encyclopedia-tab"),$("#graph-tab")];
	
	$card_frames = [$("#encyclopedia_frame"),$("#graph_frame")];
	
	cur_keyword = null;
	
	$.ajaxSettings.async = false; 
});


function switchCardFrame(frame_id){
	
	for(let index in $card_tabs){
		
		if($card_frames[index][0].id==frame_id){
			$card_frames[index].show();
			$card_tabs[index].attr("class","active");
		}
		else{
			$card_frames[index].hide();
			$card_tabs[index].removeAttr("class");
		}
	}
}

/**
 * 获取某节点实例的函数。
 * 
 * @param event 标准的 js event 对象
 * @param treeId 对应 zTree 的 treeId，便于用户操控
 * @param treeNode 被点击的节点 JSON 数据对象
 */
function listInstances(event,treeId,treeNode){
	
	//如果该节点是父节点，证明它还不是最底层的类，肯定没有实例，只有最底层的类有实例
	if(!treeNode.isParent){
		
		//设置参数数据对象
		var args = {"date":new Date(),"name":treeNode.name};
		
		//设置实例目录参数
		var setting = {	
				
				//配置实例目录的回调函数
				callback: {
					onClick: generateKnowledgeCard //点击实例节点触发的函数
				}
			};
		
		//获取某节点的实例json数据
		$.getJSON("catalog-getInstances",args,function(zNodes){
			
			if(zNodes.length==0){
				$("#warning").empty();
				$("#warning").append('该类没有实例!');
				$("#instance-number").empty();
				$("#instances").empty();
			}else{
				$("#warning").empty();
				$("#instance-number").empty();
				$("#instance-number").append("("+zNodes.length+")");
				
				//初始化实例目录
				$.fn.zTree.init($("#instances"), setting, zNodes);
			}		
		})
	}else{
		$("#warning").empty();
		$("#instance-number").empty();
		$("#instances").empty();
		$("#warning").append('该类没有实例!');
	}
	
}

function generateKnowledgeCard(event,treeId,treeNode){
	
	$("#encyclopedia_frame").empty();
	
	$("#graph_frame").empty();

	cur_keyword = treeNode.name;
	
	createEncyclopedia(cur_keyword,'encyclopedia_frame');
	
	visualizeResult(cur_keyword);
	
	switchCardFrame("encyclopedia_frame");
	
}


function visualizeResult(keyword){
	
	datas = queryKnowledge(keyword);
	
	normalVisualize(datas,'graph_frame');
}

/**
 * 目录初始化函数。
 */
function initCatalog(){
	
	//配置目录参数
	var setting = {	
			async: {
				enable: true, //是否异步请求数据
				url:"catalog-getSubClasses", //请求数据的url
				autoParam:["name"] //上传的参数，即zNodes数组中的name值
			},
			callback: {
				onClick: listInstances //点击节点时触发的函数名
			}
		};
	
	//目录根节点初始数据
	var zNodes =[{ "name":"知识分类","iconOpen":"icon/foldericon_alter.gif","iconClose":"icon/unfoldericon_alter.gif","icon":"icon/unfoldericon_alter.gif","isParent":"true"}];
	
	//初始化目录
	$.fn.zTree.init($("#classes"), setting, zNodes);
}
