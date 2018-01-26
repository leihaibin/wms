<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-菜单管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/systemMenu/list.do" method="post">

    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_bottom">
                        <input type="button" value="新增" class="ui_input_btn01 btn_redirect"
                               data-url="/systemMenu/input.do?parentId=${qo.parentId}"/>
                    </div>
                </div>
            </div>
        </div>
        当前菜单:<a href="/systemMenu/list.do">根目录</a>
        <c:forEach items="${menus}" var="item">
            ><a href="/systemMenu/list.do?parentId=${item.id }">${item.name}</a>
        </c:forEach>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>编码</th>
                        <th>菜单名称</th>
                        <th>菜单编码</th>
                        <th>父级菜单</th>
                        <th>URL</th>
                        <th></th>
                    </tr>
                    <tbody>
                    <c:forEach items="${pageResult.result}" var="item">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.sn}</td>
                            <td>${item.parent.name}</td>
                            <td>${item.url}</td>
                            <td>
                                <a href="javascript:;" class="btn_redirect"
                                   data-url="/systemMenu/input.do?id=${item.id}&parentId=${qo.parentId}">编辑</a>
                                <a href="javascript:;" class="btn_delete"
                                   data-url="/systemMenu/delete.do?id=${item.id}">删除</a>
                                <a href="javascript:;" class="btn_child"
                                   data-url="/systemMenu/list.do?parentId=${item.id }">查看下级</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%@include file="/WEB-INF/views/commons/common_page.jsp" %>
        </div>
    </div>
</form>
</body>
</html>
