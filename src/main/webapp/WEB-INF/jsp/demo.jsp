<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<!-- BEGIN PAGE -->
	
		<div class="page-content-model">
			<!-- BEGIN 模态框-->
			<div id="portlet-config" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>portlet Settings</h3>
				</div>
				<div class="modal-body">
					<p>Here will be a configuration form</p>
				</div>
			</div>
			<!-- END 模态框-->

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
										<!-- 显示模态框 -->
										<button id=""  href="#portlet-config" data-toggle="modal" class="btn green">

										添加 <i class="icon-plus"></i>

										</button>

									</div>


								</div>

								<table class="table table-striped table-hover table-bordered" id="">

									<thead>

										<tr>

											<th>用户名</th>

											<th>姓名</th>

											<th>住址</th>

											<th>备注</th>

											<th>操作</th>

										</tr>

									</thead>

									<tbody>

										<tr class="">

											<td>alex</td>

											<td>Alex Nilson</td>

											<td>1234</td>

											<td class="center">power user</td>

											<td>
											<a href="#" class="btn mini purple"><i class="icon-edit"></i> 修改</a>
											<a href="#" class="btn mini black"><i class="icon-trash"></i> 删除</a>
											</td>

										</tr>

									</tbody>

								</table>

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
<script src="js/demo.js" type="text/javascript"></script> 