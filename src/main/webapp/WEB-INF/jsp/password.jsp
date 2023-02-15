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
								<div class="caption"><i class="icon-edit"></i>修改账号和密码</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="javascript:;" class="reload"></a>
								</div>
							</div>
							<div class="portlet-body">
								<div class="clearfix" style="margin-top: 30px;">
									<div class="btn-group">
										<span style="margin:20px 0px 5px 50px;font-size:18px;font-weight: 600;">账号:</span>
										<input type="text" class="form-control" id="name" placeholder="请输入账号" style="height: 30px; font-size: 16px; margin: 0 10px 0 10px;" />
										<span style="margin:20px 0px 5px 20px;font-size:18px;font-weight: 600;">密码:</span>
										<input type="text" class="form-control" id="password" placeholder="请输入密码" style="height: 30px; font-size: 16px; margin: 0 10px 0 10px;" />
										
										<button  class="btn blue" style="margin-left: 10px; width: 140px; font-size: 16px;" onclick="password()">
										确定修改
										</button>
									</div>
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
<script src="js/password.js" type="text/javascript"></script> 