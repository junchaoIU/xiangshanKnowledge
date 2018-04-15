/**
 * 
 */

function createPopupFrame(title,popup_type,content_frame_id){
	type = '';
	switch(popup_type){
	case 'popup-graph':
		type = '知识图谱';
		break;
	case 'popup-encyclopedia':
		type = '知识百科';
		break;
	case 'popup-register':
		type='注册';
		break
	}
	$popup_frame = $("<div>").attr('id','popup-frame');
	$title = $("<div>").attr("id","title");
	$p_info = $("<p>").attr("id","info").text(title);
	$p_type = $("<p>").attr("id","type").text(type);
	$close_span = $("<span>").attr("class","glyphicon glyphicon-remove");
	$close_span.click(function(){
		disposePopup();
	});
	
	$title.append($p_info);
	$title.append($p_type);
	$title.append($close_span);
	$popup_content = $("<div>").attr("id",content_frame_id).attr('class',popup_type);
	$popup_frame.append($title);
	$popup_frame.append($popup_content);
	$('body').append($popup_frame);
}

function popupGraph(title){
	
	disposePopup();
	
	createPopupFrame(title,'popup-graph','popup-content');
	
	var datas = queryKnowledge(title);
	
	normalVisualize(datas,'popup-content');
		
}

function popupEncyclopedia(title){
	
	disposePopup();
	
	createPopupFrame(title,'popup-encyclopedia','popup-content');
	
	createEncyclopedia(title,'popup-content');
}

function disposePopup(){
	$("#popup-frame").remove();
}

function showRegister(){
	$("#register-frame").show();
}

function closeRegister(){
	$("#register-frame input:enabled").val("");
	$("#register-frame").hide();
}