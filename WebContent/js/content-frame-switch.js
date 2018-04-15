$(function(){
	$content_nav_tabs = [$("#result"),$("#graph"),$("#encyclopedia"),$("#history")];
	
	$content_frames = [$("#result_frame"),$("#graph_frame"),$("#encyclopedia_frame"),$("#history_frame")];
	
	switchContentFrame("result_frame");
});


function switchContentFrame(frame_id){
	
	for(let index in $content_frames){
		
		if($content_frames[index][0].id==frame_id){
			$content_frames[index].show();
			$content_nav_tabs[index].attr("class","active");
		}
		else{
			$content_frames[index].hide();
			$content_nav_tabs[index].removeAttr("class");
		}
	}
}

function resetContentFrames(){
	
	for(let index in $content_frames){
		
		$content_frames[index].empty();
	}
}

