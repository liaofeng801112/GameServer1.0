<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<!-- BEGIN PAGE -->
		<div class="page-content-model">


			<!-- BEGIN PAGE CONTAINER-->        

			<div class="container-fluid">


				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div style="width: 96%; margin-left: 2%;">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div style="line-height: 28px; font-size: 28px; margin-bottom: 30px;">当前总收益(元): <span style="margin-left: 15px; font-weight: bold;" id="getSum">0</span></div>
						<div class="portlet box blue" style="border: 0px;">
							<div class="portlet-title">
								<div class="caption"><i class="icon-edit"></i>日收支报表</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="javascript:;" class="reload"></a>
								</div>
							</div>
							<div class="portlet-body">
								<div id="container3" style="width:100%; height:492px;">
                    			</div>
							</div>
							<div class="portlet-title">
								<div class="caption"><i class="icon-edit"></i>年统计报表</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="javascript:;" class="reload"></a>
								</div>
							</div>
							<div class="portlet-body">
								<div id="container1" style="width:100%; height:492px;">
                    			</div>
							</div>
							<div class="portlet-title">
								<div class="caption"><i class="icon-edit"></i>区域充值记录信息</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="javascript:;" class="reload"></a>
								</div>
							</div>
							<div class="portlet-body">
								<div class="clearfix" style="margin-top: 30px;">
									<div class="btn-group">
										<span style="margin:20px 0px 5px 50px;font-size:18px;font-weight: 600;">日期:</span>
										<input type="date" class="form-control" id="searchdate" placeholder="请输入要查询的日期" style="height: 30px; font-size: 16px; margin: 0 10px 0 10px;" />
										<span style="margin:20px 0px 5px 20px;font-size:18px;font-weight: 600;">用户名:</span>
										<input type="text" class="form-control" id="searchuserName" placeholder="请输入要查询的用户名" style="height: 30px; font-size: 16px; margin: 0 10px 0 10px;" />
										<button  class="btn blue" style="margin-left: 40px; width: 90px; font-size: 16px;" onclick="refresh()">
										查询 <i class="icon-search"></i>
										</button>
										<button  class="btn blue" style="margin-left: 15px; width: 80px; font-size: 16px;" onclick="refresh()">
										重置
										</button>
									</div>
								</div>
								<table class="table" style="width: 85%; margin: 40px 0px 0px 35px;" id="" border="1px">
									<thead>
										<tr style="height: 40px; background-color: #f5f7fb; color: #87898b;">
											<th style="width: 8%; font-size: 16px; font-weight: 300;">序号</th>
											<th style="width: 16%; font-size: 16px; font-weight: 300;">玩家账号</th>
											<th style="width: 16%; font-size: 16px; font-weight: 300;">支付金额</th>
											<th style="width: 25%; font-size: 16px; font-weight: 300;">实际到账金额(充满送)</th>
											<th style="width: 15%; font-size: 16px; font-weight: 300;">获得元宝</th>
											<th style="width: 20%; font-size: 16px; font-weight: 300;">充值时间</th>
										</tr>
									</thead>
									<tbody id="tb">
									
									</tbody>
								</table>
								<div class="tablepages-switch" style="margin: 40px 0px 20px 40%;">
									<button class="button-css1" id="prePage" onclick="frontPage()">上 一 页</button>
									<div class="pagenumber-css" id="rowPage">1</div>
									<button class="button-css1" id="nextPage" onclick="nextPage()">下 一 页</button>
								</div>
							</div>
						<!-- END EXAMPLE TABLE PORTLET-->
					</div>
				</div>
				<!-- END PAGE CONTENT -->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
		<!-- END PAGE -->
	</div>
<script type="text/javascript" src="js/jquery-1.5.2.min.js"></script>

<script type="text/javascript" src="js/highcharts.js"></script>
<script src="js/likerecord.js" type="text/javascript"></script>
<!-- 加载js 区域ID信息 -->
<script src="media/js/index.js" type="text/javascript"></script>     