
function getHistories(history_type){
	$history_frame = $("#history_frame");
	
	if($history_frame.is(":hidden") && $history_frame.children().length==0){
		$history_frame.empty();
		
		histories = queryHistory(history_type);
		
		if(histories!=null){
			
			$history_table = $("<div>").attr("id","history-table").attr("class", "panel panel-default");
			$table = $("<table>").attr("class","table");
			$tr = $("<tr>");
			$time_td = $("<td>").attr("class","title").text("时间");
			$record_td = $("<td>").attr("class","title").text("记录");
			$operation_td = $("<td>").attr("class","title").text("操作");
			$clear_btn = $("<span>").attr("class","glyphicon glyphicon-trash").attr("id","clear_history").attr("onclick","emptyHistories('"+history_type+"')").text("清空");
			$operation_td.append($clear_btn);
			$tr.append($time_td);
			$tr.append($record_td);
			$tr.append($operation_td);
			$table.append($tr);
			
			for(var i=histories.length-1;i>-1;i--){
				
				var history_id = histories[i].id;
				var subject = histories[i].subject;
				var predicate = histories[i].predicate;
				var object = histories[i].object;
				var scope = histories[i].scope;
				var time_space_scope = histories[i].timeSpaceScope;
				var time = new Date(histories[i].createDate).toLocaleString();
				
				$tr = $("<tr>").attr("id",history_id).attr("class","history_item");
				$time_td = $("<td>").attr("class","time").text(time);
				
				var $a = null;
				var record = null;
				
				switch(history_type){
				case 'knowledgeSearch':
					record = subject+"("+scope+")";
					$a = $("<a>").attr("href","javascript:").attr("onclick","queryByPage('"+subject+"','"+scope+"')").text(record);
					break;
				case 'relationSearch':
					record = "("+subject+","+object+")";
					$a = $("<a>").attr("href","#").attr("onclick","queryByPage('"+subject+"','"+object+"')").text(record);
					break;
				case 'predicateSearch':
					record = predicate;
					$a = $("<a>").attr("href","javascript:").attr("onclick","queryByPage('"+predicate+"',1)").text(record);
					break;
				case 'timeSpaceSearch':
					record = subject+"("+time_space_scope+")";
					$a = $("<a>").attr("href","javascript:").attr("onclick","queryByPage('"+subject+"','"+time_space_scope+"')").text(record);
					break;
				}
				$record_td = $("<td>").attr("class","record");
				$record_td.append($a);
				$operation_td = $("<td>").attr("class","operation");
				$delete_btn = $("<button>").attr("class","btn btn-danger delete").attr("type","button").attr("onclick","deleteHistory('"+history_id+"')").text("删除");
				$operation_td.append($delete_btn);
				$tr.append($time_td);
				$tr.append($record_td);
				$tr.append($operation_td);
				$table.append($tr);
			}
			
			$history_table.append($table);
			$history_frame.append($history_table);
		}
	}
	
	switchContentFrame("history_frame");
}

function deleteHistory(history_id){
	console.log(history_id);
	var args={"historyId":history_id};
	
	$.getJSON("history-delete",args,function(result){
		
		if(result==true) $("#"+history_id).remove();
	})
}

function emptyHistories(history_type){
	var args={"historyType":history_type};
	
	$.getJSON("history-empty",args,function(result){
		
		if(result==true) $(".history_item").remove();
	})
}