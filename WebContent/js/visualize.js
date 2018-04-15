//全局 echarts 对象，在 script 标签引入 echarts.js 文件后获得，或者在 AMD 环境中通过 require('echarts') 获得。
var myChart;

//力向图的节点对象数组
var _nodes;

//力向图的边对象数组
var _links;

var _categories;

var _line_datas;

var _nodes_map;

var _links_map;

var _categories_map;

var option;

function generateGraph(nodes,links,line_datas,categories,options,graph_layout,frame_id){
	
	echarts.dispose(document.getElementById(frame_id));
	
	myChart = echarts.init(document.getElementById(frame_id));
	
	option = generateOption(nodes,links,line_datas,categories,options,graph_layout);
	
    myChart.setOption(option);
    
    myChart.on('timelinechanged',function(params){
    		
    		node = _line_datas[params.currentIndex];
    		$("#line-hint").remove();
    		$line_hint = $("<span>").attr("id","line-hint")
    		if(cur_mode=='time'){
    			$line_hint.text(node+"年");
    		}else{
    			if(node.length>4){
    				$line_hint.css("font-size","23px")
    			}
    			$line_hint.text(node);
    		}
    		$("#"+frame_id).append($line_hint);
    });
    
    myChart.on("dblclick",function(params){
    		if(_nodes_map.get(params.name)){
    			closeNode(params.name);
    		}else{
    			openNode(params.name);
    		}
    		
    		refresh();
    });
    
    myChart.on('mousedown',function(params){
    		
    		if(params.dataType=='node'){
    			if(params.event.event.altKey){
    				freeNode(params);
    			}else{
    				fixNode(params);
    			}
    		}
    });
    
    myChart.on('mouseup',function(params){
 		refresh();
    })

}

function openNode(name){
	
	if(searchType!='knowledgeSearch') cur_scope=null;
	data = queryKnowledge(name, cur_scope);
	
	var temp_nodes = new Array();
	var temp_links = new Array();
	var temp_categories = new Array();
	
	data.nodes.forEach(function(node){
		
		if(!hasNode(node)){
			 node.symbolSize = node.value;
		        node.symbol="circle";
		        //该节点标签的样式。
		        node.label = {
		            normal: {
		            	//是否显示
		                show: true,
		                position: "inside",
		                color: "#FFF"
		            }
		     };
			_nodes.push(node);
			temp_nodes.push(node);
		}
		
		if(!hasCategory(node.category)){
			
			var category = {
	            name: node.category
	        };
			
			_categories.push(category)
			temp_categories.push(category)
		}
	});
	
	_nodes_map.set(name,temp_nodes);
	_categories_map.set(name,temp_categories);
	
	data.links.forEach(function(link){
		
		if(!hasLink(link)){
			link.lineStyle={
		        normal: {
		        		type: 'dashed',
		            color: 'orange',
		            //边的曲度，支持从 0 到 1 的值，值越大曲度越大。
		            curveness: 0.05
		        	}
			  };
			_links.push(link);
			temp_links.push(link);
		}
	});
	_links_map.set(name,temp_links);
}

function closeNode(name){
	
	var temp_nodes = _nodes_map.get(name);
	
	var temp_links = _links_map.get(name);
	
	var temp_categories = _categories_map.get(name);
	
	temp_nodes.forEach(function(node){
		
		deleteNode(node);
	})
	
	_nodes_map.delete(name);
	
	temp_categories.forEach(function(category){
		deleteCategory(category.name);
	})
	
	_categories_map.delete(name);
	
	temp_links.forEach(function(link){
	
		deleteLink(link);
	})
	
	_links_map.delete(name);
	
}

function deleteNode(theNode){
	
	_nodes.forEach(function(node,index){
		if(node.name==theNode.name){
			_nodes.splice(index,1);
			
			if(_nodes_map.get(theNode.name)){
				closeNode(theNode.name)
			}
			return true;
		}
	})
	
	return false;
}

function deleteLink(theLink){
	
	_links.forEach(function(link,index){
		
		if(link.source==theLink.source&&link.target==theLink.target
				&&link.value==theLink.value){
			_links.splice(index,1);
			return true;
		}
	});
	
	return false;
}

function hasNode(theNode){
	
	_nodes.forEach(function(node){
		
		if(node.name==theNode.name) return true;
	})
	
	return false;
}

function hasLink(theLink){

	_links.forEach(function(link){
		
		if(link.source==theLink.source&&link.target==theLink.target
				&&link.value==theLink.value){
			return true;
		}
	});
	
	return false;
}

function hasCategory(categoryName){
	
	for(index in _categories){
		if(_categories[index].name==categoryName){
			return true;
		}
	}
	return false;
}

function deleteCategory(categoryName){
	
	for(index in _categories){
		if(_categories[index].name==categoryName){
			_categories.splice(index,1);
			return true;
		}
	}
	return false;
}

function fixNode(currentNode){

	_nodes.forEach(function (node) {

	    if(node.name==currentNode.name) node.fixed = true;
	});
}

function freeNode(currentNode){

	_nodes.forEach(function (node) {

	    if(node.name==currentNode.name) node.fixed = false;
	});
}


function generateMetaOption(nodes,links,times,categories,_options,graph_layout){
	option={
			
		//提示框组件
		tooltip: generateToolTip(),
	    
	    //工具栏。内置有导出图片，数据视图，动态类型切换，数据区域缩放，重置五个工具。
	    toolbox: generateToolBox(),
	    
	    legend:generateLegend(categories),
	        
	    //echarts图形的动画持续时间，支持回调函数
	    animationDuration: 1500,
	    
	    //数据更新动画的缓动效果。
	    animationEasingUpdate: 'quinticInOut',
	    
	    //数据更新动画的延迟，支持回调函数
	    animationDelayUpdate: 1000,
	    
	    //系列列表。每个系列通过 type 决定自己的图表类型
	    series : generateSeries(nodes,links,categories,graph_layout)
    
    };
	
	return option;
}


function generateTimeline(line_datas){
	if(line_datas==null) return null;
		
	timeline={
		
		data: line_datas,
		axisType: "category",
        label: {
            formatter : function(s) {
            		return "";
            }
        },
        
        lineStyle: {
        		color: "white"
        }
	}
	return timeline;
}


function generateToolBox(){
	
	toolbox={
	    	show: true,
	        
	    	/*各工具配置项。
	    	除了各个内置的工具按钮外，还可以自定义工具按钮。
	    	注意，自定义的工具名字，只能以 my 开头，例如下例中的 myTool1，myTool2：*/
	    feature: {
		    	//数据视图按钮
		    	dataView: {readOnly: false},
		        
		    	//重置视图按钮
		    	restore: {},
		    	
		    	//另存为图片按钮
		    saveAsImage: {}
	    }
    }
	
	return toolbox;
}


function generateToolTip(){
	tooltip={
    	
		//是否显示
		show: true,
		
		//触发类型('item','axis','none')
		//数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
	    	trigger: 'item',
	    	
	    	//显示延迟
	    	showDelay: 200,
	    	
	    	//鼠标是否可进入提示框浮层中，默认为false，如需详情内交互，如添加链接，按钮，可设置为 true。
	    	enterable: true,
	    	
	    	//提示框动画的持续时间
	    	transitionDuration: 1,
	    	
	    	//提示框浮层内容格式器，支持字符串模板和回调函数两种形式。
	    	formatter: function (params) {
	    	    return 'name:'+params.name+"<br>value:"+params.value;
	    	}
    }
	
	return tooltip;
}


function generateLegend(categories){
	
	if (categories==null) return null;
	
	legend = [{
        // selectedMode: 'single',
        data: categories.map(function (a) {
            return a.name;
        }),
        textStyle: {
            color: 'white'
        }
    }]
	
	return legend;
}


function normalVisualize(datas,frame_id){
	_nodes = new Array();
	_links = new Array();
	_nodes_map = new Map();
	_links_map = new Map();
	_categories_map = new Map();

    if(datas==-1) return null;
    
    _categories = new Array();
    
    //遍历服务器返回的datas对象的节点数组
    datas.nodes.forEach(function (node) {
    		//该节点的大小，可以设置成诸如 10 这样单一的数字，也可以用数组分开表示宽和高，例如 [20, 10] 表示标记宽为20，高为10。
        node.symbolSize = node.value;
        node.symbol="circle";
        //该节点标签的样式。
        node.label = {
            normal: {
            	//是否显示
                show: true,
                position: "inside",
                color: "#FFF"
            }
        };
        //将该节点添加到_nodes
       _nodes.push(node);
       
       if(!hasCategory(node.category)){
	       _categories.push({
	            name: node.category
	        });
       }
       
    });
    
    //遍历服务器返回的datas对象的边数组
    datas.links.forEach(function(link){
	    	//将边添加到_links
    		link.lineStyle={
			        normal: {
			            color: '#FFF',
			            //边的曲度，支持从 0 到 1 的值，值越大曲度越大。
			            curveness: 0.05
			        	}
				  };
	    	_links.push(link);
    });
    
    generateGraph(_nodes, _links, null, _categories, null, 'force', frame_id);
    
}


function timeSpaceVisualize(datas,frame_id){
	
	_line_datas = new Array();
	var _options = new Array();
	var baseNodes = new Array();
	var baseLinks = new Array();
	var baseCategories = new Array();
	
    if(datas==-1) return null;
    
    datas.forEach(function(element,index){
    	
	    	var _nodes = new Array();
	    	var _links = new Array();
	    	var _categories = new Array();
    	
	    	_line_datas.push(element.key);
    	
	    	element.data.nodes.forEach(function(node){
			//该节点的大小，可以设置成诸如 10 这样单一的数字，也可以用数组分开表示宽和高，例如 [20, 10] 表示标记宽为20，高为10。
	        node.symbolSize = node.value;
	        node.symbol = "circle";
        
	        //该节点标签的样式。
	        node.label = {
	            normal: {
	            	//是否显示
	                show: true,
	                position: "inside",
	                color: "#FFF"
	            }
	        };
        
	        //将该节点添加到_nodes
		    _nodes.push(node);
	       
	        _categories.push({
	            name: node.category
	        });
	    	});
  
	    	element.data.links.forEach(function(link){
	    		link.lineStyle = {
			            normal: {
			                color: '#FFF',
			                //边的曲度，支持从 0 到 1 的值，值越大曲度越大。
			                curveness: 0.3
			            }
			        };
	    		//将边添加到_links
	    		_links.push(link);
	    	});
	    	 _option = generateMetaOption(_nodes,_links,null,_categories,null,'circular');
    	
	    	 _options.push(_option);
	    	 
	    	 if(index==0){
	    		 baseNodes = _nodes;
	    		 baseLinks = _links;
	    		 baseCategories = _categories;
	    	 }
    });
    
    generateGraph(baseNodes, baseLinks, _line_datas, baseCategories, _options,'circular', frame_id);
	
}


function generateOption(nodes,links,line_datas,categories,_options, graph_layout){

	option={
			
		baseOption:{
			timeline: generateTimeline(line_datas),
			//提示框组件
			tooltip: generateToolTip(),
		    
		    //工具栏。内置有导出图片，数据视图，动态类型切换，数据区域缩放，重置五个工具。
		    toolbox: generateToolBox(),
		    
		    legend:generateLegend(categories),
		        
		    //echarts图形的动画持续时间，支持回调函数
		    animationDuration: 1500,
		    
		    //数据更新动画的缓动效果。
		    animationEasingUpdate: 'quinticInOut',
		    
		    //数据更新动画的延迟，支持回调函数
		    animationDelayUpdate: 1000,
		    
		    //系列列表。每个系列通过 type 决定自己的图表类型
		    series : generateSeries(nodes,links,categories,graph_layout)
		},
		
		options: _options
    
    };
	
	return option;
}


function generateSeries(nodes, links, categories, _layout){
	
	if(nodes==null) return [];
	
	_force = null;
	
	if(_layout=='force'){
		_force = {
					//边的两个节点之间的距离，这个距离也会受 repulsion。
			        	edgeLength: [0,250],
			        	//节点之间的斥力因子。
			        repulsion: 80,  
			        layoutAnimation : true,
			        gravity: 0.001
			    };	
	}
	
	series = [
	    	//配置type为"graph"的图表，用于展现节点以及节点之间的关系数据。
	    {
		    //图表类型
		    	type: 'graph',
		    	//图表的布局类型，设置为力向图
		    layout: _layout,
		    //指定力向图的节点数据集合
		    data: nodes,
		    //指定力向图的边数据集合
		    links: links,
		    categories: categories,
		    //节点是否可拖动
		    draggable:true,
		    //边两端的图形样式，[边的起点样式，边的终点样式]
		    edgeSymbol: ['none', 'arrow'],
		    //是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。
		    focusNodeAdjacency:true,
		    //如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
		    roam: true,
		    //图形上的文本标签，可用于说明图形的一些数据信息，比如值，名称等
		    label: {
		        normal: {
		            position: 'top',
		            formatter: function(params){
		                return params.name;
		              }
		        }
		    },
		    
		    force: _force,
	    }
    ]
	
	return series;
}


function fixNode(currentNode){
	
	_nodes.forEach(function (node) {
    	
		if(node.name==currentNode.name) node.fixed = true;
    });
	
	refresh();
}


function refresh(){
	
	option.baseOption.series[0].data = _nodes;
	option.baseOption.series[0].links = _links;
	option.baseOption.legend = generateLegend(_categories),

	myChart.setOption(option);
}


/**
 * 检查节点数组中是否存在该节点对象的方法。
 * 
 * @param node 节点对象。
 */
function hasNode(node){
	
	//遍历_nodes节点对象数组
	for(var i=0;i<_nodes.length;i++){
		
		//如果节点的名字相同，则认为两个节点相同
		if(_nodes[i].name==node.name) return true;
	}
	
	return false;
}

/**
 * 检查边数组中是否存在该边对象的方法。
 * 
 * @param link 边对象。
 */
function hasLink(link){
	
	//遍历_links边对象数组
	for(var i=0;i<_links.length;i++){
		
		alink = _links[i];
		
		//如果的边的起点和终点相同，则认为两条边相同
		if(alink.source==link.source&&alink.target==link.target) return true;
	}
	
	return false;
}
