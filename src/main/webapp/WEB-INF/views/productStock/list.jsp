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
        $(function(){

        });
    </script>
</head>
<body>
<form id="searchForm" action="/productStock/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        货品名/编号:
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}" />
                        所在仓库:
                        <select id="depotSelect" name="depotId" class="ui_select01">
                                <option value="-1">所有仓库</option>
                            <c:forEach items="${depots}" var="d">
                                <option value="${d.id}">${d.name}</option>
                            </c:forEach>
                        </select>
                        所在品牌:
                        <select id="brandSelect" name="brandId" class="ui_select01">
                                <option value="-1">所有品牌</option>
                            <c:forEach items="${brands}" var="d">
                                <option value="${d.id}">${d.name}</option>
                            </c:forEach>
                        </select>
                        阈值:
                        <input type="text" class="ui_input_txt02" name="limitNumber" value="${qo.limitNumber}" />
                        <script type="text/javascript">
                            //选中部门
                            $("#depotSelect option[value=${qo.depotId}]").prop("selected",true);
                            $("#brandSelect option[value=${qo.brandId}]").prop("selected",true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="submit" value="查询" class="ui_input_btn01"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>仓库</th>
                        <th>货品编码</th>
                        <th>货品名称</th>
                        <th>品牌</th>
                        <th>库存数量</th>
                        <th>库存价格</th>
                        <th>库存汇总</th>

                        <th></th>
                    </tr>
                    <tbody>
                    <c:forEach items="${pageResult.result}" var="item">
                    <tr>
                        <td><input type="checkbox" name="IDCheck" class="acb" data-eid="${item.id}" /></td>
                        <td>${item.depot.name}</td>
                        <td>${item.product.sn}</td>
                        <td>${item.product.name}</td>
                        <td>${item.product.brandName}</td>
                        <td>${item.storeNumber}</td>
                        <td>${item.price}</td>
                        <td>${item.amount}</td>


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

