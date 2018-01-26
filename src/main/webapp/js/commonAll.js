jQuery.ajaxSettings.traditional=true;
/** table鼠标悬停换色* */
$(function () {
	//禁用序列化
	jQuery.ajaxSettings.traditional = true;
    // 如果鼠标移到行上时，执行函数
    $(".table tr").mouseover(function () {
        $(this).css({
            background: "#CDDAEB"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#1D1E21"
            });
        });
    }).mouseout(function () {
        $(this).css({
            background: "#FFF"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#909090"
            });
        });
    });
});
$(function(){
    $(function(){
        //跳到input页面
        $(".btn_redirect").click(function(){
            window.location.href=$(this).data("url");
        });
        //查看下级目录
        $(".btn_child").click(function(){
            window.location.href=$(this).data("url");
        });
        //删除操作
        $(".btn_delete").click(function(){
            var deleteUrl=$(this).data("url");
            $.dialog({
                title:"温馨提示",
                icon:"face-smile",
                content:"你确定删除吗?",
                cancel:true,
                ok:function(){
                    //创建删除成功提示
                    var tg = $.dialog({
                        title:"温馨提示",
                        icon:"face-smile"
                    });
                    //发送ajax请求
                    $.get(deleteUrl,{},function(data){
                        if(data.success){
                            tg.content("删除成功!").button({
                                name:"确定",
                                callback:function(){
                                    window.location.reload();
                                }
                            })
                        }
                    });
                }
            });
        });
        //审核操作
        $(".btn_audit").click(function(){
            var auditUrl=$(this).data("url");
            $.dialog({
                title:"温馨提示",
                icon:"face-smile",
                content:"你确定审核吗?",
                cancel:true,
                ok:function() {
                    //创建删除成功提示
                    var tg = $.dialog({
                        title: "温馨提示",
                        icon: "face-smile"
                    });
                    //发送ajax请求
                    $.get(auditUrl, {}, function (data) {
                        if (data.success) {
                            tg.content("审核成功!").button({
                                name: "确定",
                                callback: function () {
                                    window.location.reload();
                                }
                            });
                        } else {
                            tg.content(data.msg).button({
                                name: "知道了",
                                callback: function () {
                                    window.location.reload();
                                }
                            });
                        }
                    },"JSON");

                }
        });

    });
});
})