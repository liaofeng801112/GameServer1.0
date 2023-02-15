var pagenum=1;//显示页码
var requestBean = {};//请求对象bean 
var userManage={};
/**
 * 页面加载管理员信息
 */
showArea(1);



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

//发送消息
function send(){
	//登录标识
	requestBean.requresHeader='10006';
	requestBean.style="request";
	requestBean.content=$("#msg").val();
	requestBean.returnDate=null;
	
	//进行传送
	$.ajax({
		url : "send/msg",
		type : "POST",
		data:{
			"Service":JSON.stringify(requestBean),
		},
		success : function(data) {
				alert("发送完成!");
				showArea(1);
			
		},
		 error:function(request){
             alert("发送错误。。。");
          
         }
	});
	
}
