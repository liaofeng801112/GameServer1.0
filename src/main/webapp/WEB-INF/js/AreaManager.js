var pagenum = 1;// 显示页码
var requestBean = {};// 请求对象bean
var serviceArea = {};
/**
 * 页面加载显示区域
 */
showArea(1);
/**
 * 显示当前页面的区域代理信息
 */
function showArea(pageNum) {

	// 初始化serviceArea信息
	initService();
	requestBean = {};
	serviceArea = {};
	// 设置页码
	serviceArea.pageNum = pagenum;
	serviceArea.controlStyle = 10005;

	// 登录标识
	requestBean.requresHeader = '10007';
	requestBean.style = "request";
	requestBean.content = JSON.stringify(serviceArea);
	requestBean.returnDate = null;
	$.ajax({
		url : "manageService/control",
		type : "POST",
		data : {
			"Service" : JSON.stringify(requestBean),

		},
		success : function(data) {
			var newdate = JSON.parse(data);
			var content = JSON.parse(newdate.content);// 获取的对象list
			// 清空之前的
			$("#tb").empty("");

			for (var i = 0; i < content.length; i++) {
				var tr = getDataRow(content[i], i);
				// 遍历区域列表
				$("#tb").append(tr);
				// //遍历区域菜单
				// var mesName="ServiceMoneyManager";
				// var lis='<li ><a href="javascript:void(0)"
				// onclick="aClick('+mesName+')">'+content[i].sname+'</a></li>';
				// $(".sub-menu").append(lis);
			}

		},
		error : function(request) {
			alert("进入错误。。。");

		}
	});
}

// 遍历行的数据
function getDataRow(h, i) {
	var row = document.createElement('tr'); // 创建行
	var idCell = document.createElement('th'); // 创建第一列序号
	idCell.innerHTML = i + 1; // 填充数据
	row.appendChild(idCell); // 加入行 ，下面类似
	var idCell2 = document.createElement('th'); // 创建第一列序号
	idCell2.innerHTML = h.sname; // 填充数据
	row.appendChild(idCell2); // 加入行 ，下面类似
	var idCell3 = document.createElement('th'); // 创建第一列序号
	idCell3.innerHTML = h.sid; // 填充数据
	row.appendChild(idCell3); // 加入行 ，下面类似

	var idCell4 = document.createElement('th'); // 创建第一列序号
	idCell4.innerHTML = h.dividedinto; // 填充数据
	row.appendChild(idCell4); // 加入行 ，下面类似

	var idCell5 = document.createElement('th'); // 创建第一列序号
	idCell5.innerHTML = h.agents; // 填充数据
	row.appendChild(idCell5); // 加入行 ，下面类似

	var idCell51 = document.createElement('th'); // 创建第一列序号
	idCell51.innerHTML = h.manaeid; // 填充数据
	row.appendChild(idCell51); // 加入行 ，下面类似

	var idCell6 = document.createElement('th'); // 创建第一列序号
	idCell6.innerHTML = h.sdate; // 填充数据
	row.appendChild(idCell6); // 加入行 ，下面类似

	var idCell7 = document.createElement('th'); // 创建第一列序号
	

	var btnCc = document.createElement('input'); // 创建一个input控件
	btnCc.setAttribute('type', 'button'); // type="button"
	btnCc.setAttribute('value', '删  除');
	btnCc.setAttribute('class', 'button-css3');
	btnCc.onclick = function() {

		// 初始化serviceArea信息
		initService();
		// 设置页码
		serviceArea.controlStyle = 10002;
		// 区域ID
		serviceArea.sid = h.sid;

		// 区域
		requestBean.requresHeader = '10007';
		requestBean.style = "request";
		requestBean.content = JSON.stringify(serviceArea);
		requestBean.returnDate = null;

		// 进行传送
		$.ajax({
			url : "manageService/control",
			type : "POST",
			data : {
				"Service" : JSON.stringify(requestBean),

			},
			success : function(data) {
				var newdate = JSON.parse(data);
				if (newdate.content == "删除成功") {
					alert("删除成功!");
					showArea(1);
				} else {

					alert("删除失败!");

				}

			},
			error : function(request) {
				alert("进入错误。。。");

			}
		});

	}
	idCell7.appendChild(btnCc);
	row.appendChild(idCell7); // 加入行 ，下面类似
	return row; // 返回tr数据
}

// 初始化service对象
function initService() {

	// 接口查询
	serviceArea.sid = null;
	serviceArea.sname = null;
	serviceArea.sdate = null;
	serviceArea.agents = null;
	serviceArea.dividedinto = null;
	serviceArea.controlStyle = null;
	serviceArea.manaeid = null;
	serviceArea.pageNum = null;

}
// 充值按钮将增加区域的信息为空
function clearService() {

	$("#serviceName").attr("value", "");
	// 区域ID
	$("#serviceId").attr("value", "");
	// 分成比例
	$("#devideInto").attr("value", "");
	// 代理者 ManageId
	$("#agentName").attr("value", "");
	// 管理员ID
	$("#ManageId").attr("value", "");

}
// function updateUser(userinfo){
// userinfo.activity = userinfo.activity == 0 ? 1:0;
// userinfo.username = userinfo.userName;
// $.ajax({
// url : "userInfo/control",
// type : "POST",
// data:{
// "usertable":JSON.stringify(userinfo)
// },
// success : function(json) {
// alert("修改成功");
// showGoods(1);
// }
// });
// }

// 新增区域
function insertServiceToService() {

	if ($("#serviceName").val() == null)// 区域名
	{
		alert("区域名不能为空");
		return;
	}
	if ($("#serviceId").val() == null)// 区域Id
	{
		alert("区域ID");
		return;
	}
	if ($("#agentName").val() == null)// 分成比例
	{
		alert("代理者姓名不为空");
		return;
	}
	if ($("#devideInto").val() == null)// 代理者
	{
		alert("分成比例不能为空");
		return;
	}
	if ($("#ManageId").val() == null)// 代理者
	{
		alert("管理员ID");
		return;
	}
	// 插入代理区域
	// 初始化serviceArea信息
	initService();
	// 设置页码
	serviceArea.controlStyle = 10000;
	// 区域名
	serviceArea.sname = $("#serviceName").val();
	// 区域ID
	serviceArea.sid = $("#serviceId").val();
	// 分成比例
	serviceArea.dividedinto = $("#devideInto").val();
	// 代理者
	serviceArea.agents = $("#agentName").val();
	// 管理员ID
	serviceArea.manaeid = $("#ManageId").val();

	// 登录标识
	requestBean.requresHeader = '10007';
	requestBean.style = "request";
	requestBean.content = JSON.stringify(serviceArea);
	requestBean.returnDate = null;
	// 进行传送
	$.ajax({
		url : "manageService/control",
		type : "POST",
		data : {
			"Service" : JSON.stringify(requestBean),

		},
		success : function(data) {
			var newdate = JSON.parse(data);
			if (newdate.content == "添加成功") {
				alert("添加成功!");
				showArea(1);
			} else {

				alert("添加失败!");

			}

		},
		error : function(request) {
			alert("进入错误。。。");

		}
	});

}
