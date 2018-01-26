   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>PSS-采购订单管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            //日期处理
            $("input[name=biginDate],input[name=endDate]").addClass("Wdate").click(function(){
                WdatePicker({
                    readOnly:true
                });
            });
        });
    </script>
</head>
<body>
<form id="searchForm" action="/chart/order.do" method="post">

    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    货品:<input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}" />
                            开始时间:
                    <%--此处要使用嵌套变量,否则无法清空,导致高级查询无法使用报请写一个小于0的数--%>
                    <f:formatDate value="${qo.biginDate}" var="x" pattern="yyyy-MM-dd"/>
                            <input type="text" class="ui_input_txt02" name="biginDate" value="${x} " />~
                            结束时间:
                    <f:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="q"/>
                            <input type="text" class="ui_input_txt02" name="endDate" value="${q}" />
                            供应商:
                            <select id="supplierSelect" name="supplierId" class="ui_select01">
                                <option value="-1">所有供应商</option>
                                <c:forEach items="${suppliers}" var="d">
                                    <option value="${d.id}">${d.name}</option>
                                </c:forEach>
                            </select>
                            品牌:
                            <select id="brandSelect" name="brandId" class="ui_select01">
                                <option value="-1">所有品牌</option>
                                <c:forEach items="${brands}" var="d">
                                    <option value="${d.id}">${d.name}</option>
                                </c:forEach>
                            </select>
                            分组:
                            <select id="groupSelect" name="groupBy" class="ui_select01">
                                <c:forEach items="${groupByMap}" var="entry">
                                    <option value="${entry.key}">${entry.value}</option>
                                </c:forEach>
                            </select>

                            <script type="text/javascript">
                                //选中部门
                               $("#supplierSelect option[value=${qo.supplierId}]").prop("selected",true);
                               $("#brandSelect option[value=${qo.brandId}]").prop("selected",true);
                               $("#groupSelect option[value='${qo.groupBy}']").prop("selected",true);
                            </script>

                        <input type="submit" value="查询" class="ui_input_btn01"/>
                    <div id="box_bottom">
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th>分组类型</th>
                        <th>采购总数量</th>
                        <th>采购总金额</th>
                    </tr>
                    <tbody>
                    <c:forEach items="${dataList}" var="item">
                        <tr>
                            <td>${item.groupType}</td>
                            <td>${item.totalNumber}</td>
                            <td>${item.totalAmount}</td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</form>
</body>
</html>
