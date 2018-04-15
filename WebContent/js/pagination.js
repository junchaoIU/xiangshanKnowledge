$(function(){
	page_size = 6;
	max_page = 5;
	page_show = 5;
});

function createPagination(page_now,page_count){
	if (page_now==1){
		max_page = 5;
	}
	var $ul = $("<ul>").attr("class","pagination");
	if($(".pagination").length>0){
		$(".pagination").children().remove();
		$ul = $(".pagination");
	}
	var $li;
	var $a;
	var $span;
	
	if (page_now>1){
		$span = $("<span>").html("&laquo;")
		$a = $("<a>").attr("href","javascript:")
					.append($span);
		
		$a.click(function(){
			queryByPage(page_now-1);
		});
		
		$li = $("<li>").append($a);
		$ul.append($li);
	}
	
	if(page_now>max_page){
		max_page += page_show;
	}else if(page_now<max_page&&page_now>0&&(page_now%max_page)%page_show==0){
		max_page -= page_show;
	}
	
	page_index = max_page - (page_show - 1);
	
	for(var i=page_index;i<page_index+page_show&&i<=page_count;i++){
		$li = $("<li>");
		
		if(i==page_now) $li.attr("class","active");
		$a = $("<a>").text(i)
					.attr("href","javascript:")
					.attr("onclick","queryByPage("+i+")");
		
		$li.append($a);
		$ul.append($li);
		
	}
	
	if(page_now<page_count){
		$li = $("<li>");
		$a = $("<a>").attr("href","javascript:")
		$a.click(function(){
			queryByPage(page_now+1);
		});
		$span = $("<span>").html("&raquo;")
		$a.append($span);
		$li.append($a);
		$ul.append($li);
	}
	
	return $ul;
}