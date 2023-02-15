var requestBean = {};//请求对象bean 
var userManage={};
//初始化service对象
function initService(){
	
	//接口查询
	userManage.manaeid=null;
	userManage.username=null;
	userManage.pwd=null;
	userManage.relname=null;
	userManage.createtime=null;
	userManage.controlStyle=null;
	userManage.pageNumber=null;
	userManage.flag=null;	
}
var Login = function () {
    
    return {
        //main function to initiate the module
        init: function () {
        	
           $('.login-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                }
	            },

	            messages: {
	                username: {
	                    required: "账号不能为空"
	                },
	                password: {
	                    required: "密码不能为空"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	            	$("#errorText").text("请输入账号密码！");
	                $('.alert-error', $('.login-form')).show();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            },
				
				// 登入按钮提交
	            submitHandler: function (form) {
	            	selectManager();
	            }
	        });

			// 回车键登入
	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                	selectManager();
	                }
	                return false;
	            }
	        });

        }

    };

}();

//查询数据库
function selectManager(){
	var account=document.getElementById("username").value;
	var password=document.getElementById("password").value;
	if((account==null)||(account=="")){
		alert("用户名不能为空");
		return;
	}if((password==null)||(password=="")){
		alert("密码不能为空");
		return;
	}
	//初始化信息
	initService();
	userManage.username=account;
	userManage.pwd=password;
	userManage.controlStyle=6666;
	//登录标识
	requestBean.requresHeader='10006';
	requestBean.style="request";
	requestBean.content=JSON.stringify(userManage);
	requestBean.returnDate=null;
	$.ajax({
		url : "manageService/control",
		type : "POST",
		data : {
			"Service":JSON.stringify(requestBean),
		},
		success : function(data) {
			console.log(data);
			var newdate=JSON.parse(data);
            sessionStorage.setItem("mskey", newdate.mkey);
			if(newdate.requresHeader=="1"){
				window.location.href="index";
			}else if(newdate.requresHeader=="0"){
				window.location.href="index";
			}else{
				alert("登录失败！");
			}
		},
		error:function(data){
			alert("系统繁忙请稍后再试！");
		}

	});
	
//	
//	
//	
//	$.ajax({
//		url : "management/selectUserAndPwd",
//		type : "POST",
//		data : {
//			"account" : account,
//			"managerpwd": password,
//		},
//		success : function(data) {
//			if(data==1){
//				setCookie('account',account,2);
//				window.location.href="index";
//				
//			}
//			else{
//				
//				$("#errorText").text("账号密码错误，请重新输入！");
//				$('.alert-error', $('.login-form')).show();
//			}
//				
//		},
//		error:function(data){
//			alert("系统繁忙请稍后再试！");
//		}
//
//	});
}




//写入到Cookie
//创建一个可在 cookie 变量中存储访问者姓名的函数
function setCookie(c_name,value,expiredays)
{
var exdate=new Date();
exdate.setDate(exdate.getDate()+expiredays);
document.cookie=c_name+ "=" +escape(value)+((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}
