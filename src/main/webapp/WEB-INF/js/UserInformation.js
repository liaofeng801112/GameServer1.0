
/**
 * 显示所有商品
 */
function showGoods(pageNum){
	var userinfo = {};
	if( $("#rolename").val() != "" ){
		userinfo.rolename = $("#rolename").val();
	}
	if($("#userName").val() != ""){
		userinfo.userName = $("#userName").val();
	}
	if($("#activity").val() != ""){
		userinfo.activity = $("#activity").val();
	}
	$.ajax({
		url : "userInfo/show",
		type : "POST",
		data:{
			"loginresult":JSON.stringify(userinfo),
			"pageNum":pageNum
		},
		success : function(mes) {
			var json = JSON.parse(mes);
			// 显示查询结果
			$("#tb").empty("");//删除之前的数据
            for (var i = 0; i < json.list.length; i++){
            	var grade = json.list[i].activity == 0 ? "正常":"冻结";
            	var status = json.list[i].activity == 0 ? "禁用":"启用";
            	var s = "";
            	s += "<tr><td>" + (i+1) + "</td><td id=\"mallname\">" + json.list[i].userName + "</td><td id=\"\">" + json.list[i].rolename + "</td><td id=\"grade\">" + grade + "</td>"
            	+ "<td><input type=\"button\" value = \""+status+"\" class=\"icon-edit\"  href=\"#portlet-config\" data-toggle=\"modal\" onclick='updateUser("+JSON.stringify(json.list[i])+")'/></td>";
            	$("#tb").append(s);
            }
            
			// 返回分页数
			$("#rowPage").empty("");//删除之前的数据
			$("#rowPage").append(json.pageNum + "/" +json.pages);

			// 下一页监听
			var nextPage = document.getElementById("nextPage");
			nextPage.onclick = function(){
				if( json.pageNum+1 > json.pages ){
					alert("已是最后一页");
				}else{
					showGoods(json.pageNum+1);
				}
			};
			// 上一页监听
			var nextPage = document.getElementById("prePage");
			nextPage.onclick = function(){
				if( json.pageNum-1 == 0 ){
					alert("已是第一页");
				}else{
					showGoods(json.pageNum-1);
				}
			};
		}
	});
}

function updateUser(userinfo){
	userinfo.activity = userinfo.activity == 0 ? 1:0;
	userinfo.username = userinfo.userName;
	$.ajax({
		url : "userInfo/control",
		type : "POST",
		data:{
			"usertable":JSON.stringify(userinfo)
		},
		success : function(json) {
			alert("修改成功");
			showGoods(1);
		}
	});
}
/**
 * 页面加载显示配置信息
 */
showGoods(1);