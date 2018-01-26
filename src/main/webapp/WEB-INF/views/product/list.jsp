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
    <script type="text/javascript" src="/js/plugins/fancyBox/jquery.fancybox.pack.js"></script>
    <link href="/js/plugins/fancyBox/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <title>PSS-货品管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            //给新增按钮添加单击事件
            $(".btn_redirect").click(function () {
                //获取data-url的属性值
                window.location.href = $(this).data("url");
            });
            //图片弹出层
            $(".pic").fancybox();
        })

    </script>
</head>
<body>
<form id="searchForm" action="/product/list.do" method="post">

    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_bottom">
                            姓称/编码
                            <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}" />
                            所属品牌
                            <select id="brandSelect" name="brandId" class="ui_select01">
                                <option value="-1">所有品牌</option>
                                <c:forEach items="${brands}" var="b">
                                    <option value="${b.id}">${b.name}</option>
                                </c:forEach>
                            </select>
                            <script type="text/javascript">
                                //选中部门
                                $("#brandSelect option[value=${qo.brandId}]").prop("selected",true);
                            </script>
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_redirect"
                               data-url="/product/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>货品图片</th>
                        <th>货品名称</th>
                        <th>货品编码</th>
                        <th>货品品牌</th>
                        <th>成本价格</th>
                        <th>销售价格</th>
                        <th></th>
                    </tr>
                    <tbody>
                    <c:forEach items="${pageResult.result}" var="item">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                            <td>
                            <a class="pic" href="${item.imagePath}">
                            <img src=" ${item.smallImagePath}"class="list_img_min">
                            </a>
                            </td>
                            <td>${item.name}</td>
                            <td>${item.id}</td>
                            <td>${item.brandName}</td>
                            <td>${item.costPrice}</td>
                            <td>${item.salePrice}</td>
                            <td>
                                <a href="/product/input.do?id=${item.id}">编辑</a>
                                <a href="javascript:;" class="btn_delete" data-url="/product/delete.do?id=${item.id}&imagePath=${item.imagePath}">删除</a>
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
