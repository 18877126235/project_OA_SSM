<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="cn">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<meta charset="utf-8" />
	<title>维护类型管理</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<jsp:include page="/media/ui_css.jsp"></jsp:include>
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/media/css/select2_metro.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/media/css/DT_bootstrap.css" />
	<link href="${pageContext.request.contextPath}/media/css/jquery.fancybox.css" rel="stylesheet" />
<!-- END PAGE LEVEL STYLES -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/sweetalert.css" />
</head>
<!-- 引入jquery的库文件 -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.2.js"></script> 
<script type="text/javascript">
			
	$(function(){
			var button = $("#submit-button");
			$(".myModal2Update").click(function(){
					$("#myModalLabel2").html("维护类型修改");
					$(":input[name='maintainTypeName']").val("");
					$(":input[name='maintainTypeDescripti']").val("");
					$(":input[name='maintainTypeName']").removeAttr("readonly");
					$(":input[name='maintainTypeDescripti']").removeAttr("readonly");
					if($(this).attr("lang")==1){
							$("#myModalLabel2").html("维护类型");
							$(":input[name='maintainTypeName']").attr("readonly","readonly");
							$(":input[name='maintainTypeDescripti']").attr("readonly","readonly");
							$("#submit-button").remove();
							$(".gray").html("确定");
					}else if($(this).attr("lang")==2){
						$("#myModalLabel2").html("维护类型添加");
						$(".modal-footer").prepend(button);
						$(".gray").html("取消");
					}else{
						$(".modal-footer").prepend(button);
						$(".gray").html("取消");
					}
					 if($(this).attr("lang")!=2){
							//* /2.准备发送的异步请求
							var url = "${pageContext.request.contextPath}/fixedAsset/findByMaintainTypeId.action?maintainTypeId="+$(this).attr("title");
							//jquery实现  post   Ajax请求
							$.post(url, function(data) {
									$("#form").attr("action","${pageContext.request.contextPath}/fixedAsset/updateMaintainType.action");
									$(":input[name='maintainTypeId']").val(data.maintainTypeId);
									$(":input[name='maintainTypeName']").val(data.maintainTypeName);
									$(":input[name='maintainTypeDescripti']").val(data.maintainTypeDescripti);
							}, "json");// 替代  eval() 
					}
			});
	});
</script>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed">
<jsp:include page="/main_pages/top.jsp"></jsp:include>
<input type="hidden" name="result" value="${result}" />
<!-- BEGIN 容器 -->
<div class="page-container row-fluid">
	<jsp:include page="/main_pages/leftdel.jsp"></jsp:include>
	<!-- BEGIN 开始页面 -->
	<div class="page-content">
		<!-- ---------------------------------------------- -->
		<!-- BEGIN 页面容器-->
		<div class="container-fluid">
			<!-- BEGIN 页面标题-->
			<div class="row-fluid">
				<div class="span12">
					<!-- BEGIN 风格定制 -->
					<jsp:include page="/main_pages/styleset.jsp"></jsp:include>
					<!-- END 风格定制 -->
					<!-- BEGIN 网页的标题和面包屑-->
					<h3 class="page-title">
						维护类型管理 <small>维护类型管理页</small>
					</h3>
					<ul class="breadcrumb">
						<li>	<a href="${pageContext.request.contextPath}/index.jsp">系统管理</a>  <i class="icon-angle-right"></i>
								<a href="${pageContext.request.contextPath}/fixedAsset/findMaintainTypeByPage.action">维护类型管理</a> 
						</li>
					</ul>
					<!-- END 网页的标题和面包屑-->
				</div>
			</div>
			<!-- END 页面标题-->
			<!-- BEGIN EXAMPLE TABLE PORTLET-->
			<div class="portlet box blue">
				<div class="portlet-title">
					<div class="caption">
						<i class="icon-globe"></i>维护类型
					</div>
					<div class="actions">
						<div class="btn-group">
							<a id="new-fortifyType" class="btn red myModal2Update" href="#myModal2"  lang="2" data-toggle="modal">新建 <i class="icon-plus"></i></a>
							<a class="btn green" href="#" data-toggle="dropdown"> 改变显示列
								<i class="icon-angle-down"></i> </a>
							<div id="sample_2_column_toggler" class="dropdown-menu hold-on-click dropdown-checkboxes pull-right">
								<label><input type="checkbox" checked data-column="0">维护类型ID</label>
								<label><input type="checkbox" checked data-column="1">维护类型名称</label>
								<label><input type="checkbox" checked data-column="2">添加时间</label>
								<label><input type="checkbox" checked data-column="3">维护类型描述</label>
								<!--<label> <input type="checkbox" checked data-column="5">收款日期</label>
								<label><input type="checkbox" checked data-column="6">录入人</label> -->
							</div>
						</div>
					</div>
				</div>
				<div class="portlet-body">
					<table class="table table-striped table-bordered table-hover table-full-width"
						id="sample_2">
						<thead>
							<tr>
								<th>维护类型ID</th>
								<th>维护类型名称</th>
								<th>添加时间</th>
								<th>维护类型描述</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tablecusfollow">
						<c:forEach items="${pageBean}" var="obj">
								<tr>
									<td>
									${obj.maintainTypeId }
									</td>
									<td>
									<a class="myModal2Update"  href="#myModal2"  data-toggle="modal"  lang="1" title="${obj.maintainTypeId }">${obj.maintainTypeName}</a>
									</td>
									<td>
									<fmt:formatDate value="${obj.maintainTypeDate}"  pattern="yyyy-MM-dd"/>
									</td>
									<td>
									${obj.maintainTypeDescripti }
									</td>
									<td>
									<!-- begin操作按钮 -->
											<div class="btn-group" style="margin-bottom: 0px !important;">
												<a class="btn mini green" href="#" data-toggle="dropdown">
													<i class="icon-user"></i>操作<i class="icon-angle-down"></i>
												</a>
												<ul class="dropdown-menu" >
												<li ><a href="#myModal2" data-toggle="modal" class="role-edit btn mini purple myModal2Update" title="${obj.maintainTypeId }">编辑</a></li>
												<li><a href="#"  title="${obj.maintainTypeId }" class="btn mini red user_del">删除</a></li>
												<li><a href="#myModal2"  data-toggle="modal"  lang="1" title="${obj.maintainTypeId }" class="btn mini blue myModal2Update">查看</a></li>
											</ul>
											</div>
									</td>
								</tr>
								</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- END 页面 -->
	</div>
	<!-- END 容器 -->
</div>
<jsp:include page="/main_pages/foot.jsp"></jsp:include>
<jsp:include page="/media/ui_js.jsp"></jsp:include>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="${pageContext.request.contextPath}/media/js/select2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/media/js/DT_bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/media/js/jquery.fancybox.pack.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/media/js/app.js"></script>
<script src="${pageContext.request.contextPath}/media/js/form-samples.js"></script>
<script src="${pageContext.request.contextPath}/media/js/table-advanced.js"></script>
<script>
	jQuery(document).ready(function() {
		App.init();
		TableAdvanced.init();
		FormSamples.init();
	});
	
</script>
<!-- END JAVASCRIPTS -->
<script src="${pageContext.request.contextPath}/js/sweetalert.min.js"></script>
<script type="text/javascript">
	$(function(){
		function alerts() {
			$.DialogByZ.Close();
		}
		
		$('#submit-button').click(function(){
			var maintainTypeNameEle = $("input[name='maintainTypeName']");
			
			if($.trim(maintainTypeNameEle.val())==""){
				swal("请填写!", "维护类型名称不能为空！", "error");
				return false;
			}
			return true;
		});
	});
</script>

<!-- begin 消息提示框 -->
	<form action="${pageContext.request.contextPath}/fixedAsset/saveMaintainType.action" id="form" method="post">
	<input type="hidden"  name="maintainTypeId" value=""/>
	<div id="myModal2" class="modal hide  fade" style="width:600px;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
		<div class="modal-header ">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			<h3 id="myModalLabel2"  style="color:black;"> 维护类型添加</h3>
		</div>
		<div class="modal-body" >
			<div class="row-fluid">
				<div class="span10 ">
					<div class="control-group">
						<label class="control-label">维护类型名称：</label>
						<div class="controls">
							<input name="maintainTypeName" type="text"
								class="m-wrap span10" placeholder="必填" >
						</div>
					</div>
				</div>
				<!--/span-->
			</div>
			<div class="row-fluid">
				<div class="span10 ">
					<div class="control-group">
						<label class="control-label">维护类型描述：</label>
						<div class="controls">
							<input name="maintainTypeDescripti" type="text"
								class="m-wrap span10" placeholder="选填">
						</div>
					</div>
				</div>
				<!--/span-->
			</div>
		</div>
		<div class="modal-footer">
			<button type="submit" id="submit-button" class="btn green">提交</button>
			<button data-dismiss="modal" class="btn gray">取消</button>
		</div>
	</div>
	</form>
	<!-- end 消息提示框 -->
	<script type="text/javascript">
		var result = "";
		result = $(":input[name='result']").val();
		if (result != null && result != "") {
			swal({
				title : result,
				text : '<span  style="color:red;font-size: 20px;font-weight: 500;font-weight: 600; ">'+result+'</span><br/>3秒后自动关闭。',
				imageUrl : "${pageContext.request.contextPath}/images/thumbs-up.jpg",
				html : true,
				timer : 3000,
				showConfirmButton : false
			});
		}
		
		//判断是否删除
							$(".user_del").click(
											function() {
												//在当前超链接的祖先元素中查找tr
												var $tr = $(this).parents("tr");
												//在tr下查找第一个td
												//获取td的文本内容
												var adminName = $tr.children("td:eq(1)").text();
												var cId = $(this).attr("title");
												swal(
														{
															title : "您确定要删除"
																	+ [ adminName ]
																	+ "吗？",
															text : "您确定要删除这条数据？",
															type : "warning",
															showCancelButton : true,
															closeOnConfirm : false,
															confirmButtonText : "是的，我要删除",
															confirmButtonColor : "#ec6c62"
														},
														function() {
															window.location.href = "${pageContext.request.contextPath}/fixedAsset/deleteMaintainType.action?maintainTypeId="
																	+ cId;
														});

											});
	</script>
</body>
<!-- END BODY -->
</html>