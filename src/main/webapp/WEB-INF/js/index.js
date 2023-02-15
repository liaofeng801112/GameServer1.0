var pagenum=1;//显示页码
var requestBean = {};//请求对象bean 
var serviceArea={};
/**
 * 页面加载显示区域
 */
showArea(1);
/**
 * 显示当前页面的区域代理信息
 */
function showArea(pageNum){
	
	//初始化serviceArea信息
	initService();
	//设置页码
	serviceArea.pageNum=pagenum;
	serviceArea.controlStyle=10005;
	
	//登录标识
	requestBean.requresHeader='10007';
	requestBean.style="request";
	requestBean.content=JSON.stringify(serviceArea);
	requestBean.returnDate=null;
	
	$.ajax({
		url : "manageService/control",
		type : "POST",
		data:{
			"Service":JSON.stringify(requestBean),
			
		},
		success : function(data) {
			
			var newdate=JSON.parse(data);
		     var content=JSON.parse(newdate.content);//获取的对象list
		     
		     $(".sub-menu").empty("");
		     for(var i=0;i<content.length;i++){
		    	  
		  
		    	  //遍历区域菜单
		    	//  var mesName="ServiceMoneyManager";
		    	  var lis='<li ><a href="javascript:void(0)" onclick="onloacdMes('+content[i].sid+')">'+content[i].sname+'</a></li>';
		    	  $(".sub-menu").append(lis);
		      }
		        
	
		},
		 error:function(request){
             alert("进入错误。。。");
          
         }
	});
}

//test
function onloacdMes(sid){
	$("#sid").val(sid);
	
	$("#dashboard").load('ServiceMoneyManager');
	
}
//初始化service对象
function initService(){
	
	//接口查询
	serviceArea.sid=null;
	serviceArea.sname=null;
	serviceArea.sdate=null;
	serviceArea.agents=null;
	serviceArea.dividedinto=null;
	serviceArea.controlStyle=null;
	serviceArea.manaeid=null;
	serviceArea.pageNum=null;
	
	
	
	
}