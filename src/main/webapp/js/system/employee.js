
$(function () {
        $("#selectAll").click(function () {
            $(".all_roles option").appendTo(".selected_roles")
        })
        $("#select").click(function () {
            $(".all_roles option:selected").appendTo(".selected_roles")
        })
        $("#deselectAll").click(function () {
            $(".selected_roles option").appendTo(".all_roles")
        })
        $("#deselect").click(function () {
            $(".selected_roles option:selected").appendTo(".all_roles")
        })
//删除已经分配的权限选项
        var ids = [];//已经分配权限的id
        $.each($(".selected_roles option"), function (index, item) {
            ids[index] = item.value;

        });
        //迭代所有权限的选项
        $.each($(".all_roles option"), function (index, item) {
            //判断权限的id是否在ids数组中
            if ($.inArray(item.value,ids)>= 0){
                $(item).remove();//删除自己
            }
        });
        //在表单提交之前选中已经选择的所有权限设置为选中状态
        $("#editForm").submit(function(){
            $(".selected_roles option").prop("selected",true);
        })
    });
