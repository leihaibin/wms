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
    <title>PSS-出库销售管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            //日期处理
            $("input[name=biginDate],input[name=endDate]").addClass("Wdate").click(function () {
                WdatePicker({
                    readOnly: true
                });
            });
            //给新增按钮添加单击事件
            $(".btn_redirect").click(function () {
                //获取data-url的属性值
                window.location.href = $(this).data("url");
            })
        })

    </script>
</head>
<body>
<form id="searchForm" action="/stockOutcomeBill/list.do" method="post">

    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    开始时间:
                    <%--此处要使用嵌套变量,否则无法清空,导致高级查询无法使用报请写一个小于0的数--%>
                    <f:formatDate value="${qo.biginDate}" var="x" pattern="yyyy-MM-dd"/>
                    <input type="text" class="ui_input_txt02" name="biginDate" value="${x} "/>~
                    结束时间:
                    <f:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="q"/>
                    <input type="text" class="ui_input_txt02" name="endDate" value="${q}"/>
                    仓库:
                    <select id="depotSelect" name="depotId" class="ui_select01">
                        <option value="-1">所有仓库</option>
                        <c:forEach items="${depots}" var="d">
                            <option value="${d.id}">${d.name}</option>
                        </c:forEach>
                    </select>
                    客户:
                    <select id="clientSelect" name="clientId" class="ui_select01">
                        <option value="-1">所有客户</option>
                        <c:forEach items="${clients}" var="d">
                            <option value="${d.id}">${d.name}</option>
                        </c:forEach>
                    </select>
                    审核状态:

                    <select id="statusSelect" name="status" class="ui_select01">
                      <option value="-1">所有状态</option>
                      <option value="0">待审核</option>
                      <option value="1">已审核</option>

                        <%-- <c:forEach items="${qo.status}" var="d">
                             <option value="${d.id}">${d.name}</option>
                         </c:forEach>
--%>
                    </select>
                    <script type="text/javascript">
                        //选中部门
                        $("#depotSelect option[value=${qo.depotId}]").prop("selected", true);
                        $("#clientSelect option[value=${qo.clientId}]").prop("selected", true);

                        $("#statusSelect option[value=${qo.status}]").prop("selected", true);
                    </script>

                    <input type="submit" value="查询" class="ui_input_btn01"/>
                    <div id="box_bottom">
                        <input type="button" value="新增" class="ui_input_btn01 btn_redirect"
                               data-url="/stockOutcomeBill/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>订单编号</th>
                        <th>业务时间</th>
                        <th>仓库</th>
                        <th>客户</th>
                        <th>采购总金额</th>
                        <th>采购总数量</th>
                        <th>录入人</th>
                        <th>审核人</th>
                        <th>状态</th>
                        <th></th>
                    </tr>
                    <tbody>
                    <c:forEach items="${pageResult.result}" var="item">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb"/></td>
                            <td>${item.sn}</td>
                            <td><f:formatDate value="${item.vdate}" pattern="yyyy-MM-dd"/></td>
                            <td>${item.depot.name}</td>
                            <td>${item.client.name}</td>
                            <td>${item.totalAmount}</td>
                            <td>${item.totalNumber}</td>
                            <td>${item.inputUser.name}</td>
                            <td>${item.auditor.name}</td>
                            <td>
                                <span style='color:${item.status==0?"red":"green"}'>
                                        ${item.displayStatus}
                                </span>
                            </td>
                            <td>
                                <c:if test="${item.status==0}">

                                    <a href="/stockOutcomeBill/input.do?id=${item.id}">编辑</a>
                                    <a href="javascript:;" class="btn_delete"
                                       data-url="/stockOutcomeBill/delete.do?id=${item.id}">删除</a>
                                    <a href="javascript:;" class="btn_audit"
                                       data-url="/stockOutcomeBill/audit.do?id=${item.id}">审核</a>
                                </c:if>
                                <c:if test="${item.status==1}">
                                    <a href="/stockOutcomeBill/view.do?id=${item.id}">查看</a>

                                </c:if>
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
