$(function () {
    $("#selectAll").click(function () {
        $(".all_permissions option").appendTo(".select_permissions")
    })
    $("#select").click(function () {
        $(".all_permissions option:selected").appendTo(".select_permissions")
    })
    $("#deselectAll").click(function () {
        $(".select_permissions option").appendTo(".all_permissions")
    })
    $("#deselect").click(function () {
        $(".select_permissions option:selected").appendTo(".all_permissions")
    })
//删除已经分配的权限选项
    var ids = [];//已经分配权限的id
    $.each($(".select_permissions option"), function (index, item) {
        ids[index] = item.value;

    });
    //迭代所有权限的选项
    $.each($(".all_permissions option"), function (index, item) {
        //判断权限的id是否在ids数组中
        if ($.inArray(item.value,ids)>= 0){
        $(item).remove();//删除自己
    }
    });
    //在表单提交之前选中已经选择的所有权限设置为选中状态
    $("#editForm").submit(function(){
        $(".select_permissions option").prop("selected",true);
    })
});
$(function () {
    $("#mselectAll").click(function () {
        $(".all_menus option").appendTo(".select_menus")
    })
    $("#mselect").click(function () {
        $(".all_menus option:selected").appendTo(".select_menus")
    })
    $("#mdeselectAll").click(function () {
        $(".select_menus option").appendTo(".all_menus")
    })
    $("#mdeselect").click(function () {
        $(".select_menus option:selected").appendTo(".all_menus")
    })
//删除已经分配的权限选项
    var ids = [];//已经分配权限的id
    $.each($(".select_menus option"), function (index, item) {
        ids[index] = item.value;

    });
    //迭代所有权限的选项
    $.each($(".all_menus option"), function (index, item) {
        //判断权限的id是否在ids数组中
        if ($.inArray(item.value,ids)>= 0){
        $(item).remove();//删除自己
    }
    });
    //在表单提交之前选中已经选择的所有权限设置为选中状态
    $("#editForm").submit(function(){
        $(".select_menus option").prop("selected",true);
    })
});