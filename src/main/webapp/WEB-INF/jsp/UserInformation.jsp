<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="js/UserInformation.js" type="text/javascript"></script> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<!-- BEGIN PAGE -->

		<div class="page-content-model">


			<!-- BEGIN PAGE CONTAINER-->        

			<div class="container-fluid">


				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit"></i>用户信息</div>

								<div class="tools">

									<a href="javascript:;" class="collapse"></a>

									<a href="javascript:;" class="reload"></a>


								</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">
										<span style="margin:20px 0px 5px 20px;font-size:14px;font-weight: 600;">状态:</span>
										<select id="activity" class="form-control" style="width:80px;margin:20px;">
											<option value=""></option>
											<option value="0">正常</option>
											<option value="1">冻结</option>
										</select>
										<input type="text" class="form-control" id="userName" placeholder="请输入账号" style="margin:20px;"/>
										<input type="text" class="form-control" id="rolename" placeholder="请输入角色名" style="margin:20px;"/>
										
										<button  class="btn green" onclick="showGoods(1)">
										搜索 <i class="icon-search"></i>
										</button>

									</div>


								</div>

								<table class="table table-striped table-hover table-bordered" id="">

									<thead>

										<tr>

											<th>序号</th>

											<th>账号</th>

											<th>角色名</th>
											
											<th>状态</th>
											
											<th>操作</th>

										</tr>

									</thead>

									<tbody id="tb">


									</tbody>

								</table>
							<div class="tablepages-switch" style="margin-left: 33%;">
						<button class="button-css1" id="prePage">上 一 页</button>
						<div class="pagenumber-css" id="rowPage"></div>
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
