<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/from/jquery.form.min.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function () {
            //日期处理
            $("input[name=vdate]").addClass("Wdate").click(function(){
                WdatePicker({
                    readOnly:true
                });
            })
            $("#edit_table_body")
            //点击放大镜,弹出对话框
                   .on("click", ".searchproduct", function () {
                        var currentTr = $(this).closest("tr");//找到放大镜所在的行(tr)
                        var url = "/product/selectProductList.do";
                        $.dialog.open(url, {
                            id: 'ajxxList',
                            title: '选择货品列表',
                            width: 1000,
                            height: 700,
                            close: function () {//当对话框关闭时
                                var json = $.dialog.data("json");
                                if (json) {
                                    //将数据回显到当前行中
                                    currentTr.find("[tag=name]").val(json.name);
                                    currentTr.find("[tag=pid]").val(json.id);
                                    currentTr.find("[tag=salePrice]").val(json.salePrice);
                                    currentTr.find("[tag=brand]").text(json.brandName);
                                }
                            }
                        });
                    })
                    //給采購價格和草狗数量文本框设置失去焦点事件,计算金额小计
                    .on("blur", "[tag=salePrice],[tag=number]", function () {
                        var currentTr = $(this).closest("tr");//找到放大镜所在的行(tr)
                        var salePrice = parseFloat(currentTr.find("[tag=salePrice]").val()) || 0;
                        var number = parseFloat(currentTr.find("[tag=number]").val()) || 0;
                        var amount = (salePrice * number).toFixed(2);
                        currentTr.find("[tag=amount]").text(amount);

                    })
                    //删除明细
                    .on("click",".removeItem", function () {
                        var currentTr = $(this).closest("tr");//找到放大镜所在的行(tr)
                        if ($("#edit_table_body tr").size() > 1) {
                            currentTr.remove();
                        } else {
                            //清空數據
                            currentTr.find("[tag=name]").val("");
                            currentTr.find("[tag=number").val("");
                            currentTr.find("[tag=salePrice]").val("");
                            currentTr.find("[tag=remark]").val("");
                            currentTr.find("[tag=brand]").text("");
                            currentTr.find("[tag=amount]").text("");

                        }
                    });
            ;


            //添加明细操作
            $(".appendRow").click(function () {
                //拷贝tbody中第一个tr,并追加到tbody最后
                var copy = $("#edit_table_body tr:first").clone();
                //copy前清空数据
                copy.find("[tag=name]").val("");
                copy.find("[tag=number").val("");
                copy.find("[tag=salePrice]").val("");
                copy.find("[tag=remark]").val("");
                copy.find("[tag=brand]").text("");
                copy.find("[tag=amount]").text("");
                copy.appendTo("#edit_table_body");
            });
            //提交表单前,迭代 edit_table_body 的每一行.带索引index,和存储数据的变量item
            $(".btn_submit").click(function () {
                $.each($("#edit_table_body tr"), function (index, item) {
                    $(item).find("[tag=pid]").prop("name", "items[" + index + "].product.id");
                    $(item).find("[tag=salePrice]").prop("name", "items[" + index + "].salePrice");
                    $(item).find("[tag=number]").prop("name", "items[" + index + "].number");
                    $(item).find("[tag=remark]").prop("name", "items[" + index + "].remark");

                });
            //=====================]
            //使用ajax提交表单
                $("#editForm").ajaxSubmit(function (data) {
                    if (data.success) {
                        $.dialog({
                            title: "溫馨提示",
                            content: "操作成功!",
                            icon: "face-smile",
                            ok: function () {
                                window.location.href = "/stockOutcomeBill/list.do";
                            }
                        });
                    }

                });
            });

        });
    </script>


</head>
<body>
<form name="editForm" action="/stockOutcomeBill/saveOrUpdate.do" method="post" id="editForm">
    <input type="hidden" name="id" value="${stockOutcomeBill.id}"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">订单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">订单编号</td>
                    <td class="ui_text_lt">
                        <input name="sn" class="ui_input_txt02" value="${stockOutcomeBill.sn}"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">仓库</td>
                    <td class="ui_text_lt">
                        <select id="depotSelect" name="depot.id" class="ui_select03">
                            <c:forEach items="${depots}" var="d">
                                <option value="${d.id}">${d.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">客户</td>
                    <td class="ui_text_lt">
                        <select id="clientSelect" name="client.id" class="ui_select03">
                            <c:forEach items="${clients}" var="d">
                                <option value="${d.id}">${d.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <script type="text/javascript">
                    //选中部门
                    $("#depotSelect option[value=${stockOutcomeBill.depot.id}]").prop("selected", true);
                    $("#clientSelect option[value=${stockOutcomeBill.client.id}]").prop("selected", true);
                </script>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">

                        <input name="vdate" class="ui_input_txt02" value="<f:formatDate value="${stockOutcomeBill.vdate}" pattern="yyyy-MM-dd"/>"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <c:if test="${empty stockOutcomeBill}">

                            <tr>
                                <td></td>
                                <td>
                                    <input disabled="true" readonly="true" class="ui_input_txt02" tag="name"/>
                                    <img src="/images/common/search.png" class="searchproduct"/>
                                    <input type="hidden" name="items[0].product.id" tag="pid"/>
                                </td>
                                <td><span tag="brand"></span></td>
                                <td><input tag="salePrice" name="items[0].salePrice"
                                           class="ui_input_txt00"/></td>
                                <td><input tag="number" name="items[0].number"
                                           class="ui_input_txt00"/></td>
                                <td><span tag="amount"></span></td>
                                <td><input tag="remark" name="items[0].remark"
                                           class="ui_input_txt02"/></td>
                                <td>
                                    <a href="javascript:;" class="removeItem">删除明细</a>
                                </td>
                            </tr>
                            </c:if>
                            <c:if test="${not empty stockOutcomeBill}">
                                <c:forEach items="${stockOutcomeBill.items}" var="item">

                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true" class="ui_input_txt02" tag="name" value="${item.product.name}"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <input type="hidden" name="items[0].product.id" tag="pid"  value="${item.product.id}"/>
                                    </td>
                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input tag="salePrice" name="items[0].salePrice"  value="${item.salePrice}"
                                               class="ui_input_txt00"/></td>
                                    <td><input tag="number" name="items[0].number" value="${item.number}"
                                               class="ui_input_txt00"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input tag="remark" name="items[0].remark" value="${item.remark}"
                                               class="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <td>&nbsp;</td>
                <td class="ui_text_lt">
                    &nbsp;<input type="button" value="确定保存" class="ui_input_btn01 btn_submit"/>
                    &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                </td>
                </tr>
            </table>
        </div>

    </div>
</form>
</body>
</html>