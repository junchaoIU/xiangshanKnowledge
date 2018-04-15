encyclopedia_details = null;
$encyclopedia_tabs = null;
$encyclopedia_tab_bar = null;

function createIntroduction(keyword){
	
	if(encyclopedia_details!=null&&encyclopedia_details!=-1){
		$introduction = $("<div>").attr("class", "item").attr("id", "introduction");
		$title = $("<div>").attr("id","title").text(keyword);
		comment = "暂无介绍！";
		for(let i in encyclopedia_details.links){
			if(encyclopedia_details.links[i].value=='comment'){
				comment = encyclopedia_details.links[i].target;
				break;
			}
		}
		comment_id = "comment_"+keyword;
		$comment = $("<div>").attr("class","comment").attr("id",comment_id);
		if(comment.length>150){
			$comment.css("height","105px");
		}else{
			$comment.css("height","");
		}
		$comment.text(comment);
		
		avatar = '';
		for(let j in encyclopedia_details.nodes){
			if(keyword==encyclopedia_details.nodes[j].name){
				symbol = encyclopedia_details.nodes[j].symbol;
				avatar= symbol.substring(symbol.lastIndexOf("image://") + 8);
				break;
			}
		}
		$avatar = $("<img>").attr("id","avatar").attr("src",avatar);
		$introduction.append($title);
		$introduction.append($comment);
		$introduction.append($avatar);
		
		if(comment.length>150){
			$a = $("<a>").attr("href","javascript:");
			$span = $("<span>").attr("class","glyphicon glyphicon-triangle-bottom show-more").text("展开").attr("onclick","showMore('comment','"+comment_id+"')");
			$a.append($span);
			$introduction.append($a);
		}
		
		return $introduction;
	}
	
	return null;
}

function createRelativePeople(keyword){
	people = queryKnowledge(keyword, "香山人物");
	
	if(people!=-1){
		relative_people_id = "relative_people_"+keyword;
		$relative_people = $("<div>").attr("class","item relative-people").attr("id",relative_people_id);
		$relative_people_tag = $("<div>").attr("class","tag").attr("id","relative-people-tag");
		$tag_left = $("<div>").attr("class","tag-left").text("相关人物");
		$tag_right = $("<div>").attr("class","tag-right")
		$relative_people_tag.append($tag_left);
		$relative_people_tag.append($tag_right);
		$relative_people_avatar = $("<div>").attr("id","relative-people-avatar");
		$relative_people_avatar.append($relative_people_tag);
		names = new Set();
		
		for(let i in people.links){
			name = people.links[i].source;
			relation = people.links[i].value;
			if(name==keyword){
				name = people.links[i].target;
			}

			avatar = null;
			
			if(names.has(name)||name.length>5){
				continue;
			}else{
				names.add(name);
			}
			
			for(let j in people.nodes){
				if(name==people.nodes[j].name){
					symbol = people.nodes[j].symbol;
					avatar = symbol.substring(symbol.lastIndexOf("image://") + 8);
					break;
				}
			}
			
			$a = $("<a>").attr("href","javascript:").attr("onclick","popupEncyclopedia('"+name+"')");
			$img = $("<img>").attr("src", avatar);
			$span = $("<span>").text(relation+" "+name);
			
			$a.append($img);
			$a.append($span);

			if(names.size>11){
				$a.attr("class","overflow");
				$a.hide();
			}
			
			$relative_people_avatar.append($a);
			
		}
		if(names.size>11){
			$a = $("<a>").attr("href","javascript:");
			$span=$("<span>").attr("class","glyphicon glyphicon-triangle-bottom show-more").text("展开").attr("onclick","showMore('relative-people','"+relative_people_id+"')");
			$a.append($span);
			$relative_people_avatar.append($a);
		}
		
		$relative_people.append($relative_people_avatar);
		
		return $relative_people;
	}
	
	return null;
}

function showMore(type,id){
	
	var $show_more = null;
	
	switch(type){
	
	case 'relative-people':
		$("#"+id+" .overflow").show();
		$show_more = $("#"+id+" .show-more");
		break
	case 'comment':
		$("#"+id).parent().css("height","auto");
		$("#"+id).css("height","auto");
		$show_more = $("#"+id).parent("#introduction").find(".show-more");
		break;
	}
	
	$show_more.text("收起").attr("class","glyphicon glyphicon-triangle-top hide-overflow").attr("onclick","hideOverflow('"+type+"','"+id+"')");
}

function hideOverflow(type,id){
	
	$hide_overflow = null;
	
	switch(type){
	case 'relative-people':
		$("#"+id+" .overflow").hide();
		$hide_overflow = $("#"+id+" .hide-overflow");
		break;
	case 'comment':
		$("#"+id).parent().css("height","180px");
		$("#"+id).css("height","105px");
		$hide_overflow = $("#"+id).parent("#introduction").find(".hide-overflow");
	}
	
	$hide_overflow.text("展开").attr("class","glyphicon glyphicon-triangle-bottom show-more").attr("onclick","showMore('"+type+"','"+id+"')");
}

function createRelativeEvents(keyword){
	events = queryKnowledge(keyword, "历史事件");
	
	if(events!=-1){
		$relative_events = $("<div>").attr("class","item").attr("id","relative-events");
		$relative_event_tag = $("<div>").attr("class","tag").attr("id","relative-event-tag");
		$tag_left = $("<div>").attr("class","tag-left").text("相关事件");
		$tag_right = $("<div>").attr("class","tag-right");
		$relative_event_tag.append($tag_left);
		$relative_event_tag.append($tag_right);
		$relative_event_infos = $("<div>").attr("id","relative-event-infos");
		$ul = $("<ul>").attr("class","nav nav-pills");
		for(let i in events.links){
			event_name = events.links[i].source;
			if(event_name==keyword){
				event_name = events.links[i].target;
			}
			$li = $("<li>");
			$a = $("<a>").attr("href","javascript:")
						.attr("onclick","popupEncyclopedia('"+event_name+"')")
						.attr("class","glyphicon glyphicon-tag").text(event_name);
			
			$li.append($a);
			$ul.append($li);
		}
		$relative_event_infos.append($ul);
		$relative_events.append($relative_event_tag);
		$relative_events.append($relative_event_infos);
		
		return $relative_events;
		
	}
	
	return null;
}

function createDetails(){
	
	if(encyclopedia_details!=null&&encyclopedia_details!=-1){
		
		$details = $("<div>").attr("class","item").attr("id","details");
		$details_tag = $("<div>").attr("class","tag").attr("id","details-tag");
		$tag_left = $("<div>").attr("class","tag-left").text("详细信息");
		$tag_right = $("<div>").attr("class","tag-right");
		$details_tag.append($tag_left);
		$details_tag.append($tag_right);
		$details_table = $("<div>").attr("id","details-table").attr("class","panel panel-default");
		$table = $("<table>").attr("class","table");
		$tr = $("<tr>");
		for(let i in encyclopedia_details.links){
			key = encyclopedia_details.links[i].value;
			value = encyclopedia_details.links[i].target;
			
			if(key=="comment"){
				if(encyclopedia_details.links.length>1) continue;
				else return null;
			}
			
			if(i % 2 == 0){
				$tr = $("<tr>");
			}
			$key_td = $("<td>").attr("class","key").text(key);
			$value_td = $("<td>").attr("class","value").text(value);
			$tr.append($key_td);
			$tr.append($value_td);
			
			if(i % 2 == 0){
				$table.append($tr);
			}
		}
		$details_table.append($table);
		$details.append($details_tag);
		$details.append($details_table);
		
		return $details;
	}
	
	return null;
}

function createEncyclopedia(keyword, encyclopedia_frame_id){
	
	if(keyword!=null&&$.trim(keyword)!=''){
		encyclopedia_details = queryDetails(keyword);
		
		$encyclopedia_frame = $("#"+encyclopedia_frame_id);
		
		$encyclopedia_frame.empty();
		
		$encyclopedia_main = $("<div>").attr("id","encyclopedia_main");
		
		$introduction = createIntroduction(keyword);
		
		$relative_people = createRelativePeople(keyword);
		
		$relative_events = createRelativeEvents(keyword);
		
		$details = createDetails();
		
		$encyclopedia_main.append($introduction);
		$encyclopedia_main.append($relative_people);
		$encyclopedia_main.append($relative_events);
		$encyclopedia_main.append($details);
		$encyclopedia_frame.append($encyclopedia_main);
	}
}

function generateMultiEncyclopedia(){
	switchEncyclopedia(arguments[0]);
	var $encyclopedia_frame = $("#encyclopedia_frame");
	$encyclopedia_tabs = [];
	if (	$encyclopedia_tab_bar!=null){
		$encyclopedia_tab_bar.remove();
	}
	$encyclopedia_tab_bar = $("<div>").attr("id","encyclopedia-tab-bar");

	var $ul = $("<ul>").attr("class","nav nav-tabs nav-justified");
	for(var i=0;i<arguments.length;i++){
		
		var $li = $("<li>");
		var $a = $("<a>").attr("href","#").attr("onclick","switchEncyclopedia('"+arguments[i]+"',this)").text(arguments[i]);
		if(i==0){
			$li.attr("class","active");
		}
		$li.append($a);
		$encyclopedia_tabs.push($li);
		$ul.append($li);
	}
	
	$encyclopedia_tab_bar.append($ul);
	$encyclopedia_frame.prepend($encyclopedia_tab_bar);
	
	switchContentFrame("encyclopedia_frame");
}

function switchEncyclopediaTab(keyword){
	if($encyclopedia_tabs!=null){
		for(let i in $encyclopedia_tabs){
			tab = $encyclopedia_tabs[i];
			if(tab.text()==keyword) tab.attr("class","active");
			else tab.removeAttr("class");
		}
	}
}

function switchEncyclopedia(keyword){
	
	switchEncyclopediaTab(keyword);
	
	if (keyword==null){
		keyword = cur_keyword;
	}
	
	createEncyclopedia(keyword, 'encyclopedia_frame');
	
	$('#encyclopedia_frame').prepend($encyclopedia_tab_bar);
	
}

function getEncyclopedia(keyword){
	
	if (keyword==null){
		keyword = cur_keyword;
	}
	
	createEncyclopedia(keyword,'encyclopedia_frame');
	
	switchContentFrame("encyclopedia_frame");
	
}