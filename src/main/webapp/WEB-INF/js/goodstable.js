


/**
 * 显示所有商品
 */
function showGoods(pageNum){
	var goodsrecord = {};
	if( $("#rolename").val() != "" ){
		goodsrecord.rolename = $("#rolename").val();
	}
	if($("#othername").val() != ""){
		goodsrecord.othername = $("#othername").val();
	}
	if($("#goods").val() != ""){
		goodsrecord.goods = $("#goods").val();
	}
	if($("#recordtype").val() != ""){
		goodsrecord.recordtype = $("#recordtype").val();
	}
	if($("#recordtime").val() != ""){
		goodsrecord.recordtime = $("#recordtime").val();
	}
	$.ajax({
		url : "goodsRecord/show",
		type : "POST",
		data:{
			"goodsrecord":JSON.stringify(goodsrecord),
			"pageNum":pageNum
		},
		success : function(mes) {
			console.log(mes);
			var json=JSON.parse(mes);
			// 显示查询结果
			$("#tb").empty("");//删除之前的数据
            for (var i = 0; i < json.list.length; i++){
            	//0：商店或商城购买、1:摆摊购买、2、给与、3、礼包获得、4、其他获得、5:交易,
            	//6、合成消耗,7、合成符石，8、合成修改，9、使用,10:典当，11：取回典当,12:炼妖消耗
            	var grade = "";
            	switch (json.list[i].recordtype) {
				case 0:
					grade = "商店或商城购买";
					break;
				case 1:
					grade = "摆摊购买";
					break;
				case 2:
					grade = "给与";
					break;
				case 3:
					grade = "礼包获得";
					break;
				case 4:
					grade = "其他获得";
					break;
				case 5:
					grade = "交易";
					break;
				case 6:
					grade = "合成消耗";
					break;
				case 7:
					grade = "合成符石";
					break;
				case 8:
					grade = "合成修改";
					break;
				case 9:
					grade = "使用";
					break;
				case 10:
					grade = "典当";
					break;
				case 11:
					grade = "取回典当";
					break;
				default:
					grade = "炼妖消耗";
					break;
				}
            	var othername = json.list[i].othername == null ? "":json.list[i].othername;
            	var s = "";
            	s += "<tr><td>" + (i+1) + "</td><td id=\"mallname\">" + json.list[i].rolename + "</td><td id=\"\">" + othername + "</td><td id=\"grade\">" + grade + "</td>"
                + "<td id=\"\">" + json.list[i].goods + "</td><td id=\"\">" + json.list[i].goodsnum + "</td><td id=\"\">" + Todate(json.list[i].recordtime) + "</td>";
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
function Todate(num) { //Sep 14, 2018 5:45:07 PM
    num = num + "";
    var date = "";
    var month = new Array();
    month["Jan"] = 1; month["Feb"] = 2; month["Mar"] = 3; month["Apr"] = 4; month["May"] = 5; month["Jan"] = 6;
    month["Jul"] = 7; month["Aug"] = 8; month["Sep"] = 9; month["Oct"] = 10; month["Nov"] = 11; month["Dec"] = 12;
    str = num.split(" ");
    var time = str[3].split(":");
    if( str[4] == "PM" ){
    	time[0] = parseInt(time[0])+12;
    }
    date = str[2] + "-";
    date = date + month[str[0]] + "-" + str[1].replace(',',"")+" "+ time[0]+":"+time[1]+":"+time[2];
    return date;
}
/**
 * 页面加载显示配置信息
 */
showGoods(1);