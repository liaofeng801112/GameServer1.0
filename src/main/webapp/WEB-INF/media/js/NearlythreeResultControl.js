
//采用开奖结果
function getResultKaijiang(number,getRun){
	
	 $("#kaijaingjieguo").html(number);
	
}
//确认开奖结果
function insertValue(){  
	
	$.ajax({
		url:"historyPrize/insertOpenHistory",
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

function text(codown,cudsecond){
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
		setTimeout("insertValue()",1000);

		
		
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
			url : "selectKuaiSanList",
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
			url : "selectKuaiSanListYou",
			type : "POST",
			data : {
			 
			},
			dataType:'json',
			async:false,
			success : function(data) {
				console.log(data);
			    //推荐开奖
				$("#integralNumA").html(data.prize[0]+"-"+data.prize[1]+"-"+data.prize[2]);
				//展示盈利
				$("#getLIrun").html("获取利润"+(data.intoMoney-data.outMoney));
				
				//将选中开奖结果设置
				$("#kaijaingjieguo").html(data.prize[0]+""+data.prize[1]+""+data.prize[2]);
				
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
	var name=h.prize[0]+"-"+h.prize[1]+"-"+h.prize[2];
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
		    alert(h.prize[0]);
			  getResultKaijiang(h.prize[0]+""+h.prize[1]+""+h.prize[2],getLiRun);
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
		 "GameType":3,
		 "current_second":300,
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
	var minit=myDate.getMinutes()%5;     //获取当前分钟数(0-59)
	var second=myDate.getSeconds();     //获取当前秒数(0-59)
    
	//需要倒计时的分钟数
	var cudown=4*60+30-(minit*60+second);
	
	//剩下的分钟
	var codown=parseInt(cudown/60);
	//剩下的秒钟
	var cudsecond=cudown%60;
	
	$("#integralNumA-1").html(codown);
	$("#integralNumA-2").html(cudsecond);

	
	//倒计时运算
	text(codown,cudsecond);
	

	
}