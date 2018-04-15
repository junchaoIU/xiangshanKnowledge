$(function(){
	cur_input_widget = null;
	
	$("html").click(function(){
		$("#proposal").removeAttr("class");
	});
})

function getProposals(the_input_widget){
	
	cur_input_widget = $(the_input_widget);
	keyword = cur_input_widget.val();
	
	var url = "indistinct-queryKnowledge";
	
	if(cur_input_widget[0].id=='predicate'){
		url = "indistinct-queryPredicate";
	}
	
	var args = {"keyword":keyword};
	
	var $proposal = $("#proposal");
	var $ul = $("#proposal ul");
	
	$ul.empty();
	$proposal.removeAttr("class");
	$.getJSON(url,args,function(datas){
		if(datas!=-1&&datas.length!=0){
			for(let i in datas){
				var $li = $("<li>");
				var $a = $("<a>").attr("href","#").text(datas[i]);
				$a.click(function(){
					cur_keyword=datas[i];
					cur_input_widget.val(datas[i]);
					if (cur_input_widget[0].id=='keyword'||cur_input_widget[0].id=='predicate'){
						queryByPage();
					}
				})
				$li.append($a);
				$ul.append($li);
			}
			
			if(cur_input_widget[0].id=='subject'){
				$proposal.attr("class","dropdown open subject");
			}else if(cur_input_widget[0].id=='object'){
				$proposal.attr("class","dropdown open object");
			}else{
				$proposal.attr("class","dropdown open");
			}
		}
	})	
}