<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<!-- BEGIN PAGE -->

		<div class="page-content-model">


			<!-- BEGIN PAGE CONTAINER-->        

			<div class="container-fluid">


				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue" style="border: 0px;">
							<div class="portlet-title">
								<div class="caption"><i class="icon-edit"></i>代理账号管理</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="javascript:;" class="reload"></a>
								</div>
							</div>
							<div class="portlet-body">
								<div class="clearfix" style="margin-top: 30px;">
									<div class="btn-group">
										<span style="margin:20px 0px 5px 50px;font-size:18px;font-weight: 600;">账号:</span>
										<input type="text" class="form-control" id="userName" placeholder="请输入账号" style="height: 30px; font-size: 16px; margin: 0 10px 0 10px;" />
										<span style="margin:20px 0px 5px 20px;font-size:18px;font-weight: 600;">密码:</span>
										<input type="text" class="form-control" id="userpwd" placeholder="请输入密码" style="height: 30px; font-size: 16px; margin: 0 10px 0 10px;" />
										<span style="margin:20px 0px 5px 10px;font-size:18px;font-weight: 600;">代理姓名:</span>
										<input type="text" class="form-control" id="useragentName" placeholder="请代理姓名" style="height: 30px; font-size: 16px; margin: 0 10px 0 10px;" />
										<span style="margin:20px 0px 5px 20px;font-size:18px;font-weight: 600;">区域ID:</span>
										<input type="text" class="form-control" id="userQuYuID" placeholder="区域ID" style="height: 30px; font-size: 16px; margin: 0 10px 0 10px;" />
										
										<button  class="btn blue" style="margin-left: 10px; width: 80px; font-size: 16px;" onclick="insertServiceToService()">
										插入 <i class="icon-search"></i>
										</button>
										<button  class="btn blue" style="margin-left: 10px; width: 75px; font-size: 16px;" onclick="clearService()">
										重置
										</button>
									</div>
								</div>
								<table class="table" style="width: 85%; margin: 40px 0px 0px 35px;" id="">
									<thead>
										<tr style="height: 40px; background-color: #f5f7fb; color: #87898b;">
											<th style="width: 8%; font-size: 16px; font-weight: 300;">序号</th>
											<th style="width: 12%; font-size: 16px; font-weight: 300;">账号</th>
											<th style="width: 10%; font-size: 16px; font-weight: 300;">密码</th>
											<th style="width: 15%; font-size: 16px; font-weight: 300;">管理员姓名</th>
											<th style="width: 15%; font-size: 16px; font-weight: 300;">管理员ID(区域ID)</th>
											<th style="width: 20%; font-size: 16px; font-weight: 300;">操作时间</th>
											<th style="width: 20%; font-size: 16px; font-weight: 300;">操作</th>
										</tr>
									</thead>
									<tbody id="tb">
										
									</tbody>
								</table>
								<div class="tablepages-switch" style="margin: 40px 0px 20px 40%;">
									<button class="button-css1" id="prePage">上 一 页</button>
									<div class="pagenumber-css" id="rowPage">1/1</div>
									<button class="button-css1" id="nextPage">下 一 页</button>
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
<script src="js/UseManager.js" type="text/javascript"></script> 