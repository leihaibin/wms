<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>PSS-账户管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
    <script type="text/javascript">
            //全选和全不选复选框
            $("#all").click(function(){
                $(".acb").prop("checked",this.checked);
            });
            //批量删除
            $(".btn_batchDelete").click(function(){
                //如果没有复选框选中
                var deleteUrl =$(this).data("url");
                if($(".acb:checked").size()==0) {
                    $.dialog({
                        title: "溫馨提示",
                        content: "亲,请选择被删除的数据！",
                        icon: "face-smile",
                        ok: true
                    });
                    return;
                }
                var deleteIds=[];
            //弹出对话框,确认是否真的删除
                $.each($(".acb:checked"),function(index,item){
                    //把选中复选框对应的id保存到数组中
                    deleteIds[index]=$(item).data("eid");
                })
            $.dialog({
                title:"溫馨提示",
                content:"你确定要批量删除吗?",
                icon:"face-smile",
                cancel:true,
                ok:function(){
                    var dg= $.dialog({
                    title:"溫馨提示",
                    icon:"face-smile"
                });
                    //发送ajax请求去删除
                    $.post(deleteUrl,{ids:deleteIds},function(data){
                        if(data.success){
                            dg.content("批量删除成功!").button({
                                name:"知道了",
                                callback:function(){
                                    window.location.reload();
                                }
                            })
                        }
                    },"json")
                }

            });
        });
    </script>
</head>
<body>
<form id="searchForm" action="/employee/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        姓名/邮箱
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}" />
                        所属部门
                        <select id="deptSelect" name="deptId" class="ui_select01">
                                <option value="-1">所有部门</option>
                            <c:forEach items="${depts}" var="d">
                                <option value="${d.id}">${d.name}</option>
                            </c:forEach>
                        </select>
                        <script type="text/javascript">
                            //选中部门
                            $("#deptSelect option[value=${qo.deptId}]").prop("selected",true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="批量删除" class="ui_input_btn01 btn_batchDelete" data-url="/employee/batchDelete.do"/>
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_redirect" data-url="/employee/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>编号</th>
                        <th>用户名</th>
                        <th>EMAIL</th>
                        <th>年龄</th>
                        <th>所属部门</th>
                        <th>角色</th>
                        <th></th>
                    </tr>
                    <tbody>
                    <c:forEach items="${pageResult.result}" var="item">
                    <tr>
                        <td><input type="checkbox" name="IDCheck" class="acb" data-eid="${item.id}" /></td>
                        <td>${item.id}</td>
                        <td>${item.name}</td>
                        <td>${item.email}</td>
                        <td>${item.age}</td>
                        <td>${item.dept.name}</td>
                        <td>${item.roleNames}</td>
                        <td>
                            <a href="/employee/input.do?id=${item.id}">编辑</a>
                            <a href="javascript:;" class="btn_delete" data-url="/employee/delete.do?id=${item.id}">删除</a>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
           <%@include file="/WEB-INF/views/commons/common_page.jsp"%>
        </div>
    </div>
</form>
</body>
</html>

