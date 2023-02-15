
//采用开奖结果
function getResultKaijiang(number,getRun){
	
	 $("#kaijaingjieguo").html(number);
	
}
//确认开奖结果
function insertValue(){  
	
	$.ajax({
		url:"historyPrize/insertOpenHistoryShiShi",
		type:"POST",
		async:false,
		data : {
 			"betsnum": $("#kuaisanqihao").text(),
 			"openresult":$("#kaijaingjieguo").text(),
 		},
		success : function(data){
			 if(data==1){
				 alert("开奖成功!!"); 
				//将数据恢复
				clearData();
				//新的一期
				setTimeout("sureQuickSan()",1000);

			 }else{
				 
				 alert("开奖失败,已经默认开奖！！");
			 }
			
			
		}
	});

	
	
	
}
//设置期数
sureQuickSan();
var intA ;
var intB;

function text(){
	 clearInterval(intA);
	 clearInterval(intB);
	intA= setInterval("CountDown()", 1000);
}
//时间倒计时
function CountDown(){
	var num1 = $("#integralNumA-1").html();
	var num2 = $("#integralNumA-2").html();
	minitue=num1;
	seconds=num2;
	//如果秒钟大于0
	  if((seconds-1)>=0){
		//设置秒钟
		  seconds=seconds-1;
		  $("#integralNumA-2").html(seconds);  
		  //设置分钟
		  $("#integralNumA-1").html(minitue);  
		
	  }else if(((seconds-1)<0)&&(minitue>0)){
		  
		  minitue=minitue-1;
		  //设置秒钟
		  seconds=60-1;
		  $("#integralNumA-2").html(seconds);  
		  //设置分钟
		  $("#integralNumA-1").html(minitue);     
	  }else{
		  clearInterval(intA);
		  //查询推荐结果
		  getTuiJian();
		  intB = setInterval("CountDownB()", 1000);  
	  }

}
function CountDownB(){
	var numB = $("#integralNumB").html();
	numB = Number(numB)-1;
	if(numB >= 0){
		if(numB<10){
			numB = "0"+numB;
		}
		$("#integralNumB").html(numB);
	}
	else{
		clearInterval(intB);
		//进行开奖
		insertValue();
	}
}
//重新开始将数据恢复成之前数据
function clearData(){
	$("#integralNumB").html(25);
	$("#integralNumA").html("---");
	//展示盈利
	$("#getLIrun").html("获取利润----");
	
	//将选中开奖结果设置
	$("#kaijaingjieguo").html("---");


	
	
}

//查询开奖推荐结果
function getTuiJian(){
	
	 $.ajax({
			url : "selectShiShiCaiList",
			type : "POST",
			data : {
			  "betsnum":$("#kuaisanqihao").text(),
			},
			dataType:'json',
			  async:false,
			success : function(data) {
				console.log(data);
			var tbody = document.getElementById('tbMain12'); 
//			   tbody.html();
			 $.each(data,function(i,item) {
				var row= getDataRow12(item,i+1);
				tbody.appendChild(row);
				 
			    });
					
			}
		});
	//展示最优开奖  selectKuaiSanListYou
		$.ajax({
			url : "selectShiShiCaiForOne",
			type : "POST",
			data : {
			 
			},
			dataType:'json',
			async:false,
			success : function(data) {
				console.log(data);
			    //推荐开奖
				$("#integralNumA").html(data.prize[0]+"-"+data.prize[1]+"-"+data.prize[2]+"-"+data.prize[3]+"-"+data.prize[4]);
				//展示盈利
				$("#getLIrun").html("获取利润"+(data.intoMoney-data.outMoney));
				
				//将选中开奖结果设置
				$("#kaijaingjieguo").html(data.prize[0]+""+data.prize[1]+""+data.prize[2]+""+data.prize[3]+""+data.prize[4]);
				
			}
		});
	
	
	
	
	
}

//进行页面端展示
function getDataRow12(h,i){ 
	
	var row = document.createElement('tr'); 
	/*序号*/
	var td1 = document.createElement('td');  
	td1.innerHTML =i;  
	row.appendChild(td1);  
	/*开奖结果*/
	var td2 = document.createElement('td');   
	var name=h.prize[0]+"-"+h.prize[1]+"-"+h.prize[2]+"-"+h.prize[3]+"-"+h.prize[4];
	td2.innerHTML =name;
	row.appendChild(td2); 
	/*投注金额*/
	var td3 = document.createElement('td');
	td3.innerHTML =h.intoMoney;
	row.appendChild(td3); 
	/*开奖金额*/
	var td4 = document.createElement('td'); 
	td4.innerHTML =h.outMoney;
	row.appendChild(td4); 
	
	/*获利*/
	var td5 = document.createElement('td');
	var getLiRun=h.intoMoney-h.outMoney;
	td5.innerHTML =getLiRun;
	row.appendChild(td5); 
	
	/*操作*/
	var button = document.createElement('td'); //创建第一列序号  
	  var btnSC = document.createElement('input'); // 创建一个input控件
		btnSC.setAttribute('type', 'button'); // type="button"
		btnSC.setAttribute('value', '选中');
		btnSC.setAttribute('class', 'button-css3');
		btnSC.onclick=function(){  
		   
			  getResultKaijiang(h.prize[0]+""+h.prize[1]+""+h.prize[2]+""+h.prize[3]+""+h.prize[4],getLiRun);
	      };
		button.appendChild(btnSC); 
		row.appendChild(button); //加入行  ，下面类似
		
	return row; 
}; 

//确定当前期号
function sureQuickSan(){
	   //获取当前期号
	//展示最优开奖
	$.ajax({
		url : "selectQiHaoForStyle",
		type : "POST",
		data : {
		 "GameType":1,
		 "current_second":90,
		},
		dataType:'json',
		async:false,
		success : function(data) {
		       //设置期号
		       $("#kuaisanqihao1").text(data);
		       $("#kuaisanqihao2").text(data);
		       $("#kuaisanqihao").text(data);
		      
		}
	});
	 sureMiniteSeconds();
      
}


//确定倒计时时间
function  sureMiniteSeconds(){
	var myDate = new Date();
	var minit=myDate.getMinutes();     //获取当前分钟数(0-59)
	var second=myDate.getSeconds();     //获取当前秒数(0-59)
    
	//需要倒计时的秒钟数
	var cudown=(minit*60+second)%90;
	

	var leaveSeconds;//剩余倒计时秒数
	//剩余的秒数
	var miaoshuLeave=90-cudown;

	if(miaoshuLeave>30){
		//还剩倒计时(还没到操作时间)
	 leaveSeconds=miaoshuLeave-30;	
		$("#integralNumA-1").html("00");
		$("#integralNumA-2").html(leaveSeconds);
		//倒计时运算
		text();

	}else if((miaoshuLeave>0)&&(miaoshuLeave<25)){
		//剩余开奖倒计时
		$("#integralNumA-1").html("00");
		$("#integralNumA-2").html("00");
		//倒计时开奖
		getTuiJian();//获取推荐结果
		//设置操作时间
		$("#integralNumB").html((30-miaoshuLeave));
		//进行倒计时
		intB = setInterval("CountDownB()", 1000);  
	}
    
}