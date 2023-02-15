var pagenum=1;//显示页码
var requestBean = {};//请求对象bean 
var userManage={};
/**
 * 页面加载管理员信息
 */
showArea(1);
/**
 * 显示当前页面的区域代理信息
 */
function showArea(pageNum){
	
	//初始化serviceArea信息
	initService();
	//设置页码
	userManage.pageNumber=pagenum;
	userManage.controlStyle=10003;
	
	//登录标识
	requestBean.requresHeader='10006';
	requestBean.style="request";
	requestBean.content=JSON.stringify(userManage);
	requestBean.returnDate=null;
	
	$.ajax({
		url : "manageService/control",
		type : "POST",
		data:{
			"Service":JSON.stringify(requestBean),
			
		},
		success : function(data) {
			console.log(data);
			var newdate=JSON.parse(data);
			if(newdate.style=="10006"){
		     var content=JSON.parse(newdate.content);//获取的对象list
		     //清空之前的
		     $("#tb").empty("");
		     for(var i=0;i<content.length;i++){
		    	  
		    	  var tr=getDataRow(content[i],i);
		    	  
		    	  $("#tb").append(tr);
		    	  
		      }
		     
			}
			else{
				
				alert("请求数据失败");
			}
			
		},
		 error:function(request){
             alert("进入错误。。。");
          
         }
	});
}
//遍历行的数据
function getDataRow(h,i){  
	var row = document.createElement('tr'); //创建行  
	var idCell = document.createElement('th'); //创建第一列序号  
	idCell.innerHTML =i+1; //填充数据 
	row.appendChild(idCell); //加入行  ，下面类似  
	var idCell2 = document.createElement('th'); //创建第一列序号  
	idCell2.innerHTML =h.username; //填充数据  
	row.appendChild(idCell2); //加入行  ，下面类似  
	var idCell3 = document.createElement('th'); //创建第一列序号  
	idCell3.innerHTML =h.pwd; //填充数据  
	row.appendChild(idCell3); //加入行  ，下面类似  
	
	var idCell4 = document.createElement('th'); //创建第一列序号  
	idCell4.innerHTML =h.relname; //填充数据  
	row.appendChild(idCell4); //加入行  ，下面类似  
	
	var idCell6 = document.createElement('th'); //创建第一列序号  
	idCell6.innerHTML =h.manaeid; //填充数据  
	row.appendChild(idCell6); //加入行  ，下面类似  
	
	
	var idCell5 = document.createElement('th'); //创建第一列序号  
	idCell5.innerHTML =h.createtime; //填充数据  
	row.appendChild(idCell5); //加入行  ，下面类似  
	
/*	var idCell6 = document.createElement('th'); //创建第一列序号  
	idCell6.innerHTML =h.sdate; //填充数据  
	row.appendChild(idCell6); //加入行  ，下面类似  
*/	
	var idCell7 = document.createElement('th'); //创建第一列序号  
	
	
	var btnCc = document.createElement('input'); // 创建一个input控件
	btnCc.setAttribute('type', 'button'); // type="button"
	btnCc.setAttribute('value', '删  除');
	btnCc.setAttribute('class', 'button-css3');
	btnCc.onclick=function(){
		
		//初始化serviceArea信息
		initService();
		//设置页码
		userManage.controlStyle=10002;
		//区域ID
		userManage.manaeid=h.manaeid;

		//区域
		requestBean.requresHeader='10006';
		requestBean.style="request";
		requestBean.content=JSON.stringify(userManage);
		requestBean.returnDate=null;
		
		//进行传送
		$.ajax({
			url : "manageService/control",
			type : "POST",
			data:{
				"Service":JSON.stringify(requestBean),
				
			},
			success : function(data) {
			
				var newdate=JSON.parse(data);
				if(newdate.content=="删除成功"){
					alert("删除成功!");
					showArea(1);
				}else{
					
					alert("删除失败!");
					
				}
				
			},
			 error:function(request){
	             alert("进入错误。。。");
	          
	         }
		});
		
	}
	idCell7.appendChild(btnCc); 
	row.appendChild(idCell7); //加入行  ，下面类似  
	return row; //返回tr数据    
}  



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
//充值按钮将增加区域的信息为空
function clearService(){
	
	$("#userName").attr("value","");
	//区域ID
	$("#userpwd").attr("value","");
	//分成比例
  $("#useragentName").attr("value","");
	//代理者
	$("#userQuYuID").attr("value","");
	
	
}
//function updateUser(userinfo){
//	userinfo.activity = userinfo.activity == 0 ? 1:0;
//	userinfo.username = userinfo.userName;
//	$.ajax({
//		url : "userInfo/control",
//		type : "POST",
//		data:{
//			"usertable":JSON.stringify(userinfo)
//		},
//		success : function(json) {
//			alert("修改成功");
//			showGoods(1);
//		}
//	});
//}


//新增区域
function saveDB(){
	
	//进行传送
	$.ajax({
		url : "manageService/saveDB",
		type : "POST",
		success : function(data) {
			alert(data);
		},
		 error:function(request){
             alert("进入错误。。。");
          
         }
	});
	
	
	
	
	
}
