<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<!-- BEGIN PAGE -->
<style>
.button-css1{ 
	margin: 0px 8px 0px 8px; 
	color:#FFF; 
	font-size: 16px; 
	font-weight: bold; 
	background-color: #1E90FF; 
	border-radius: 8px;
	-webkit-border-radius: 8px; 
	width: 90px; 
	text-align:center; 
	height: 32px; 
	border: 1px solid #598fab;
}
.pagenumber-css{ 
	display:inline; 
	font-size:22px;
	 margin-left: 5px; 
	 margin-right: 5px;
 }
.button-operation{
	margin-left:20px;
}
</style>	
		<div class="page-content-model">

			<!-- BEGIN PAGE CONTAINER-->

			<div class="container-fluid">


				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit"></i>物品记录</div>

								<div class="tools">

									<a href="javascript:;" class="collapse"></a>

									<a href="javascript:;" class="reload"></a>

									

								</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">
										<span style="margin:20px 0px 5px 20px;font-size:14px;font-weight: 600;">状态:</span>
										<select id="recordtype" class="form-control" style="width:80px;margin:20px;">
											<option value=""></option>
											<option value="0">商店或商城购买</option>
											<option value="1">摆摊购买</option>
											<option value="2">给与</option>
											<option value="3">礼包获得</option>
											<option value="4">其他获得</option>
											<option value="5">交易</option>
											<option value="6">合成消耗</option>
											<option value="7">合成符石</option>
											<option value="8">合成修改</option>
											<option value="9">使用</option>
											<option value="10">典当</option>
											<option value="11">取回典当</option>
											<option value="12">炼妖消耗</option>
										</select>
										<input type="text" class="form-control" id="rolename" placeholder="请输入角色名" style="margin:20px;"/>
										<input type="text" class="form-control" id="othername" placeholder="请输入对方角色名" style="margin:20px;"/>
										<input type="text" class="form-control" id="goods" placeholder="请输入物品名称" style="margin:20px;"/>
										<input type="date" class="form-control" id="recordtime" style="margin:20px;"/>
										
										<button  class="btn green" onclick="showGoods(1)">
										搜索 <i class="icon-search"></i>
										</button>
									</div>

								</div>

								<table class="table table-striped table-hover table-bordered" id="">

									<thead>

										<tr>

											<th>序号</th>

											<th>角色名</th>

											<th>对方角色名</th>

											<th>记录类型</th>
											<th>物品信息</th>
											<th>物品数量</th>
											<th>记录时间</th>

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
						
						</div>

						<!-- END EXAMPLE TABLE PORTLET-->

					</div>

				</div>

				<!-- END PAGE CONTENT -->

			</div>

			<!-- END PAGE CONTAINER-->

		</div>

		<!-- END PAGE -->
<script src="js/goodstable.js" type="text/javascript"></script> 