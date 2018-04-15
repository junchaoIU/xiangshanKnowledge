function login(){
	
	var user_name = $("#userName").val();
	
	var password = $("#password").val();
	
	var args = {"userName":user_name, "password":password};
	
	$.getJSON("user-login", args, function(result){
		if(result==true){
			window.location.reload();
		}else{
			alert("登陆失败!");
		}
	})
}

function logout(){
	
	$.getJSON("user-logout", null, function(result){
		if(result==true){
			alert("注销成功！");
			window.location.reload();
		}else{
			alert("注销失败！")
		}
	})
}

function register(){
	
	var user_name = $("#register_userName").val();
	var password = $("#register_password").val();
	if(user_name.trim()==''||password.trim()==''){
		alert("用户名和密码不能为空!");
		return;
	}
	var old = $("#register_old").val();
	var gender = $("#gender option:selected").text();
	var role = $("#role").val();
	
	var args={"userName": user_name,"password": password,"old": old,"gender": gender,"role": role};
	
	$.getJSON("user-register",args,function(result){
		if(result==true){
			alert("注册成功!")
			closeRegister();
		}else{
			alert("注册失败!");
		}
	})
}