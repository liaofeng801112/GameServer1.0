var chart;
var pagenum=1;//显示页码
var requestBean = {};//请求对象bean 
var serviceArea={};
//月年统计
var areaServiceMonthBean;
//月日统计
var dayForOneAreaServiceMonthBean;

var serviceId;
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
	serviceArea.controlStyle=10003;
	//设置区域ID
//	serviceArea.sid=7080;
	serviceId=$("#sid").val();
	serviceArea.sid=serviceId;
	
	
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
			console.log(data);
			var newdate=JSON.parse(data);
		     var content=JSON.parse(newdate.content);//获取的对象list
		    //月年统计
		     areaServiceMonthBean=content.areaServiceMonthBean;
		     //月日统计
		     dayForOneAreaServiceMonthBean=content.dayForOneAreaServiceMonthBean;
		    //显示月统计
		     monthYears();
		     //月日统计
		     monthForDay();
		     //进行充值记录遍历
		     insertToMoney(content.expensesReceiptsList);
			
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
	idCell2.innerHTML =h.playeracc; //填充数据  
	row.appendChild(idCell2); //加入行  ，下面类似  
	var idCell3 = document.createElement('th'); //创建第一列序号  
	idCell3.innerHTML =h.recharge; //填充数据  
	row.appendChild(idCell3); //加入行  ，下面类似  
	
	var idCell4 = document.createElement('th'); //创建第一列序号  
	idCell4.innerHTML =h.playerpay; //填充数据  
	row.appendChild(idCell4); //加入行  ，下面类似  
	
	var idCell5 = document.createElement('th'); //创建第一列序号  
	idCell5.innerHTML =h.yuanbao; //填充数据  
	row.appendChild(idCell5); //加入行  ，下面类似  
	
	var idCell51 = document.createElement('th'); //创建第一列序号  
	idCell51.innerHTML =h.paytime; //填充数据  
	row.appendChild(idCell51); //加入行  ，下面类似  
	
	
	
	return row; //返回tr数据    
}  

//月日统计表
function monthForDay(){
//	alert(dayForOneAreaServiceMonthBean.one);
	//天统计报表折线图
	  chart = new Highcharts.Chart({
	        chart: {
	            renderTo: 'container3',          //放置图表的容器
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            defaultSeriesType: 'line'   
	        },
	        title: {
	            text: '日统计报表'
	        },
	        subtitle: {
	            text: '一区'
	        },
	        xAxis: {//X轴数据
	            categories: ['6.1', '6.2', '6.3', '6.4', '6.5', '6.6', '6.7', '6.8', '6.9', '6.10', '6.11', '6.12','6.13', '6.14', '6.15', '6.16', '6.17', '6.18', '6.19', '6.20', '6.21', '6.22', '6.23', '6.24','6.25', '6.26', '6.27', '6.28', '6.29', '6.30'],
	            labels: {
	                rotation: -45, //字体倾斜
	                align: 'right',
	                style: { font: 'normal 13px 宋体' }
	            }
	        },
	        yAxis: {//Y轴显示文字
	            title: {
	                text: '金额/元'
	            }
	        },
	        tooltip: {
	            enabled: true,
	            formatter: function() {
	                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 1);
	            }
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true
	                },
	                enableMouseTracking: true//是否显示title
	            }
	        },
	        series: [{
	            name: '一区',
	            data: [dayForOneAreaServiceMonthBean.one,dayForOneAreaServiceMonthBean.two,dayForOneAreaServiceMonthBean.three,dayForOneAreaServiceMonthBean.four,dayForOneAreaServiceMonthBean.five,dayForOneAreaServiceMonthBean.six,dayForOneAreaServiceMonthBean.seven,dayForOneAreaServiceMonthBean.eight,dayForOneAreaServiceMonthBean.nine,dayForOneAreaServiceMonthBean.ten,dayForOneAreaServiceMonthBean.elev,dayForOneAreaServiceMonthBean.tween,dayForOneAreaServiceMonthBean.thirteen,dayForOneAreaServiceMonthBean.fourteen,dayForOneAreaServiceMonthBean.fivteen,dayForOneAreaServiceMonthBean.sixteen,dayForOneAreaServiceMonthBean.seventeen,dayForOneAreaServiceMonthBean.eightteen,dayForOneAreaServiceMonthBean.nineteen,dayForOneAreaServiceMonthBean.tweity,dayForOneAreaServiceMonthBean.twyone,dayForOneAreaServiceMonthBean.twytwo,dayForOneAreaServiceMonthBean.twythree,dayForOneAreaServiceMonthBean.twyfour,dayForOneAreaServiceMonthBean.twyfive,dayForOneAreaServiceMonthBean.twysix,dayForOneAreaServiceMonthBean.twyseven,dayForOneAreaServiceMonthBean.twyeight,dayForOneAreaServiceMonthBean.twynine,dayForOneAreaServiceMonthBean.thirty,dayForOneAreaServiceMonthBean.thyone]
	        }]
	    });
		
	
	
	
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

//月年统计
function monthYears(){
	 //年统计报表
	//折线图示例
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container1',          //放置图表的容器
            plotBackgroundColor: null,
            plotBorderWidth: null,
            defaultSeriesType: 'line'   
        },
        title: {
            text: '年月统计报表'
        },
        subtitle: {
            text: '一区'
        },
        xAxis: {//X轴数据
            categories: ['一月份', '二月份', '三月份', '四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份'],
            labels: {
                rotation: -45, //字体倾斜
                align: 'right',
                style: { font: 'normal 13px 宋体' }
            }
        },
        yAxis: {//Y轴显示文字
            title: {
                text: '单位/元'
            }
        },
        tooltip: {
            enabled: true,
            formatter: function() {
                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 1);
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true//是否显示title
            }
        },
        series: [ {
            name: '四区',
            data: [areaServiceMonthBean.one,areaServiceMonthBean.two,areaServiceMonthBean.three,areaServiceMonthBean.four,areaServiceMonthBean.five,areaServiceMonthBean.six,areaServiceMonthBean.seven,areaServiceMonthBean.eight,areaServiceMonthBean.nine,areaServiceMonthBean.ten,areaServiceMonthBean.eleven,areaServiceMonthBean.tweer]
		}]
    });
	
}

//遍历当前也的充值查询列表
function insertToMoney(listExpen){
	console.log("展示");
	//清空列表
	$("#tb").empty("");

    for (var int = 0; int < listExpen.length; int++) {
    	
		var tr=getDataRow(listExpen[int],int);
		
		$("#tb").append(tr);
	}
	
}

//下一月查询
function nextPage(){

	
	//初始化serviceArea信息
	initService();
	//设置页码
	pagenum+=1;
	serviceArea.pageNum=pagenum+1;
	serviceArea.controlStyle=10004;
	//设置区域ID
	serviceArea.sid=serviceId;
	
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
			console.log(data);
			var newdate=JSON.parse(data);
			var content=JSON.parse(newdate.content);//获取的对象list
			if((content.expensesReceiptsList==null)||(content.expensesReceiptsList=="")||(content.expensesReceiptsList=="[]")){
		     
				alert("最后一页"); 
		    	pagenum-=1; 
		     }else{
		    	 //进行充值记录遍历
			     insertToMoney(content.expensesReceiptsList);
			     $("#rowPage").html(pagenum);
		     }
			
		},
		 error:function(request){
             alert("进入错误。。。");
          
         }
	});

	
	
	
	
}

//上一页查询
function frontPage(){

	
	//初始化serviceArea信息
	initService();
	//设置页码
	pagenum-=1;
	serviceArea.pageNum=pagenum;
	serviceArea.controlStyle=10004;
	//设置区域ID
	serviceArea.sid=serviceId;
	
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
			console.log(data);
			var newdate=JSON.parse(data);
			 var content=JSON.parse(newdate.content);//获取的对象list
			 if((content.expensesReceiptsList==null)||(content.expensesReceiptsList=="")||(content.expensesReceiptsList=="[]")){
			     
					alert("已经是第一页"); 
			    	pagenum+=1; 
			    	
			     }else{
			    	 $("#rowPage").html(pagenum);
			    	 //进行充值记录遍历
				     insertToMoney(content.expensesReceiptsList);
			    	
			     }
			
		},
		 error:function(request){
             alert("进入错误。。。");
          
         }
	});

	
	
	
	
}
//按钮重置
function refresh(){
	
	$("#searchdate").attr("value","");
	
	$("#searchuserName").attr("value","");
}

$(document).ready(function() {
	
//	//天统计报表折线图
//	  chart = new Highcharts.Chart({
//	        chart: {
//	            renderTo: 'container3',          //放置图表的容器
//	            plotBackgroundColor: null,
//	            plotBorderWidth: null,
//	            defaultSeriesType: 'line'   
//	        },
//	        title: {
//	            text: '日统计报表'
//	        },
//	        subtitle: {
//	            text: '一区'
//	        },
//	        xAxis: {//X轴数据
//	            categories: ['6.1', '6.2', '6.3', '6.4', '6.5', '6.6', '6.7', '6.8', '6.9', '6.10', '6.11', '6.12','6.13', '6.14', '6.15', '6.16', '6.17', '6.18', '6.19', '6.20', '6.21', '6.22', '6.23', '6.24','6.25', '6.26', '6.27', '6.28', '6.29', '6.30'],
//	            labels: {
//	                rotation: -45, //字体倾斜
//	                align: 'right',
//	                style: { font: 'normal 13px 宋体' }
//	            }
//	        },
//	        yAxis: {//Y轴显示文字
//	            title: {
//	                text: '金额/元'
//	            }
//	        },
//	        tooltip: {
//	            enabled: true,
//	            formatter: function() {
//	                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 1);
//	            }
//	        },
//	        plotOptions: {
//	            line: {
//	                dataLabels: {
//	                    enabled: true
//	                },
//	                enableMouseTracking: true//是否显示title
//	            }
//	        },
//	        series: [{
//	            name: '一区',
//	            data: [1,2,3,4,5,6,7,8,9,10,11,12,12,13,14,15,15,14,14,14,1,4,14,14,141,41,14,3,2,1]
//	        }]
//	    });
//		


	
//	//柱状图图示例
//	chart = new Highcharts.Chart({
//	                chart: {
//	                    renderTo: 'container2',          //放置图表的容器
//	                    plotBackgroundColor: null,
//	                    plotBorderWidth: null,
//	                    defaultSeriesType: 'column'   //图表类型line, spline, area, areaspline, column, bar, pie , scatter 
//	                },
//	                title: {
//	                    text: '总报表统计'
//	                }, 
//	                xAxis: {//X轴数据
//	                    categories:  ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
//	                    labels: {
//	                        rotation: -45, //字体倾斜
//	                        align: 'right',
//	                        style: { font: 'normal 13px 宋体' }
//	                    }
//	                },
//	                yAxis: {//Y轴显示文字
//	                    title: {
//	                        text: '单位/元'
//	                    }
//	                },
//	                tooltip: {
//	                    enabled: true,
//	                    formatter: function() {
//	                        return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 1) + "元";
//	                    }
//	                },
//	                plotOptions: {
//	                    column: {
//	                        dataLabels: {
//	                            enabled: true
//	                        },
//	                        enableMouseTracking: true//是否显示title
//	                    }
//	                },
//	                series: [{
//	                    name: '杭州',
//	                    data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2]
//	                }, {
//	                    name: '江西',
//	                    data: [4.0, 2.9, 5.5, 24.5, 18.4, 11.5, 35.2]
//	                }, {
//	                    name: '湖南',
//	                    data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0]
//	                }]
//	              });
//	//折线图示例
//    chart = new Highcharts.Chart({
//        chart: {
//            renderTo: 'container',          //放置图表的容器
//            plotBackgroundColor: null,
//            plotBorderWidth: null,
//            defaultSeriesType: 'line'   
//        },
//        title: {
//            text: '一区周统计报表'
//        },
//        subtitle: {
//            text: '一区'
//        },
//        xAxis: {//X轴数据
//            categories: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'],
//            labels: {
//                rotation: -45, //字体倾斜
//                align: 'right',
//                style: { font: 'normal 13px 宋体' }
//            }
//        },
//        yAxis: {//Y轴显示文字
//            title: {
//                text: '金额/元'
//            }
//        },
//        tooltip: {
//            enabled: true,
//            formatter: function() {
//                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 1);
//            }
//        },
//        plotOptions: {
//            line: {
//                dataLabels: {
//                    enabled: true
//                },
//                enableMouseTracking: true//是否显示title
//            }
//        },
//        series: [{
//            name: '一区',
//            data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2]
//        }, {
//            name: '二区',
//            data: [4.0, 2.9, 5.5, 24.5, 18.4, 11.5, 35.2]
//        }, {
//            name: '三区',
//            data: [14.0, 12.9, 15.5, 14.5, 28.4, 21.5, 15.2]
//        }, {
//            name: '四区',
//            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0]
//		}
//        ]
//    });
//    
//    //年统计报表
//	//折线图示例
//    chart = new Highcharts.Chart({
//        chart: {
//            renderTo: 'container1',          //放置图表的容器
//            plotBackgroundColor: null,
//            plotBorderWidth: null,
//            defaultSeriesType: 'line'   
//        },
//        title: {
//            text: '一区周统计报表'
//        },
//        subtitle: {
//            text: '一区'
//        },
//        xAxis: {//X轴数据
//            categories: ['一月份', '二月份', '三月份', '四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份'],
//            labels: {
//                rotation: -45, //字体倾斜
//                align: 'right',
//                style: { font: 'normal 13px 宋体' }
//            }
//        },
//        yAxis: {//Y轴显示文字
//            title: {
//                text: '产量/百万'
//            }
//        },
//        tooltip: {
//            enabled: true,
//            formatter: function() {
//                return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 1);
//            }
//        },
//        plotOptions: {
//            line: {
//                dataLabels: {
//                    enabled: true
//                },
//                enableMouseTracking: true//是否显示title
//            }
//        },
//        series: [{
//            name: '一区',
//            data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
//        }, {
//            name: '二区',
//            data: [4.0, 2.9, 5.5, 24.5, 18.4, 11.5, 35.2, 36.5, 23.3, 38.3, 23.9, 3.6]
//        }, {
//            name: '三区',
//            data: [14.0, 12.9, 15.5, 14.5, 28.4, 21.5, 15.2, 16.5, 13.3, 28.3, 13.9, 13.6]
//        }, {
//            name: '四区',
//            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
//		}]
//    });
	//柱状图
 
});

//页面跳转
function changePage(page){
	
	$("#dashboard").load(page);
	
}