<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table cellpadding="5" style="margin-left: 30px" id="itemParamAddTable" class="itemParam">
	<tr>
		<td>商品类目:</td>
		<td><a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a> 
			<input type="hidden" name="cid" style="width: 280px;"></input>
		</td>
	</tr>
	<tr class="hide addGroupTr">
		<td>规格参数:</td>
		<td>
			<ul>
				<li><a href="javascript:void(0)" class="easyui-linkbutton addGroup">添加分组</a></li>
				<!-- (1) 新添加的规格组和规格项都会放到这里-->
			</ul>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<a href="javascript:void(0)" class="easyui-linkbutton submit">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton close">关闭</a>
		</td>
	</tr>
</table>
<!-- 包含了一个规格组，一个规格项，一个添加按钮，一个del按钮。默认情况下这个是隐藏掉的，作为模板被copy到(1)中 -->
<div  class="itemParamAddTemplate" style="display: none;">
	<li class="param">  <!-- eq(0)-->
		<ul>
			<li>  <!-- eq(1) -->
				<input class="easyui-textbox" style="width: 150px;" name="group"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton addParam"  title="添加参数" data-options="plain:true,iconCls:'icon-add'"></a>
			</li>
			<li>   <!-- eq(2) -->
				<span>|-------</span><input  style="width: 150px;" class="easyui-textbox" name="param"/>&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton delParam" title="删除" data-options="plain:true,iconCls:'icon-cancel'"></a>						
			</li>
		</ul>
	</li>
</div>
<script style="text/javascript">
	$(function(){
		//大括号中的值作为data传递给initItemCat方法
		TAOTAO.initItemCat({
			fun:function(node){
			$(".addGroupTr").hide().find(".param").remove();
				//  判断选择的目录是否已经添加过规格
			  $.getJSON("/item/param/query/itemcatid/" + node.id,function(data){//这里的data是发送完请求后接收到的数据
				  if(data.status == 200 && data.data){
					  $.messager.alert("提示", "该类目已经添加，请选择其他类目。", undefined, function(){
						 $("#itemParamAddTable .selectItemCat").click();//弹出一个alert框，有个确认按钮，点击这个按钮就会关闭alert框，并自动点击selectItemCat，即实现了返回商品类目的效果
					  });
					  return ;
				  }
				  $(".addGroupTr").show();
			  });
			}
		});

		//点击“添加分组”按钮，在下方自动添加一个分组和一个规格项，以及一个+和一个x号
		$(".addGroup").click(function(){
			  var temple = $(".itemParamAddTemplate li").eq(0).clone();//index = 0 的标签为li的元素
			  $(this).parent().parent().append(temple);//$(this).parent().parent() = <ul>
			  temple.find(".addParam").click(function(){ //给"+"按钮绑定一个函数，这个函数的功能是添加一个规格项
				  var li = $(".itemParamAddTemplate li").eq(2).clone();//index = 2的标签为li的元素，是一个规格项
				  li.find(".delParam").click(function(){ //给"x"按钮绑定一个函数，即删除该选项
					  $(this).parent().remove();
				  });
				  li.appendTo($(this).parentsUntil("ul").parent());
			  });
			  temple.find(".delParam").click(function(){
				  $(this).parent().remove();
			  });
		 });
		
		$("#itemParamAddTable .close").click(function(){
			$(".panel-tool-close").click();
		});

		//点击submit按钮，将数据转换成json格式提交给后端
		$("#itemParamAddTable .submit").click(function(){
			var params = [];
			var groups = $("#itemParamAddTable [name=group]");
			groups.each(function(i,e){
				var p = $(e).parentsUntil("ul").parent().find("[name=param]");
				var _ps = [];
				p.each(function(_i,_e){
					var _val = $(_e).siblings("input").val();//siblings：兄弟节点
					if($.trim(_val).length>0){
						_ps.push(_val);						
					}
				});
				var _val = $(e).siblings("input").val();
				if($.trim(_val).length>0 && _ps.length > 0){
					params.push({
						"group":_val,
						"params":_ps
					});					
				}
			});
			var url = "/item/param/save/"+$("#itemParamAddTable [name=cid]").val();
			$.post(url,{"paramData":JSON.stringify(params)},function(data){//将json转为字符串保存在paramData中，传
				if(data.status == 200){
					$.messager.alert('提示','新增商品规格成功!',undefined,function(){
						$(".panel-tool-close").click();
    					$("#itemParamList").datagrid("reload");
    				});
				}
			});
		});
	});
</script>