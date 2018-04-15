$(function(){
	cur_user_infos = null;
	
	updateUser();
})

function getLoginUser(){
	$.ajaxSettings.async = false; 
	
	$.getJSON("user-getInfos",null,function(infos){
		if(infos!=-1){
			cur_user_infos = infos;
		}else{
			cur_user_infos = null;
		}
	})
}

function updateUser(){
	getLoginUser();
	
	if(cur_user_infos!=-1){
		$("#user-info a").text(cur_user_infos.userName);
	}else{
		$("#user-info a").text("游客");
	}
}