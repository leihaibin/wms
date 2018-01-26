
//菜单配置
var setting = {
    data: {
        //启用简单json格式
        simpleData: {
            enable: true
        }
    },
    callback: {
        //节点单击事件
        onClick: function (event, treeId, treeNode) {
            if (treeNode.action) {
                //打开菜单对应的URL
                $("#rightMain").prop("src", treeNode.action + ".do");
                //设置菜单导航
                $("#here_area").html("当前位置:" + treeNode.getParentNode().name + "&nbsp;>&nbsp;" + treeNode.name);
            }
        }
    },
    async: {
        enable: true,
        url:"systemMenu/loadMenusByParentSn.do",
        autoParam: ["sn=parentSn"]
    }
};
var zNode1 = [
    {id: 1, pId: 0, name: "业务管理", isParent: true, sn: "business"}
];
var zNode2 = [
    {id: 2, pId: 0, name: "系统管理", isParent: true, sn: "system"}
];
var zNode3 = [
    {id: 3, pId: 0, name: "报表管理", isParent: true, sn: "chart"}
];
var zNodes = {
    business:zNode1,
    system:zNode2,
    chart:zNode3
}

//加载菜单树
function loadMenu(sn) {
    $.fn.zTree.init($("#dleft_tab1"), setting, zNodes[sn]);
}
//渲染菜单
$(function () {
    loadMenu("business");
});
//----------------------------------------------------
//菜单面包切换
$(function () {
    $("#TabPage2 li").click(function () {
        //把所有li的样式删除,图片恢复到未选中
        $.each($("#TabPage2 li"), function (index, item) {
            $(item).removeClass("selected");
            $(item).children("img").prop("src", "/images/common/" + (index + 1) + ".jpg");
        });
        //增加selected样式
        $(this).addClass("selected");
        //修改被选中的图片
        var currentIndex = $(this).index();//当前元素在选择器中的索引
        $(this).children("img").prop("src", "/images/common/" + (currentIndex + 1) + "_hover.jpg")
        //修改模块图片(标题)
        $("#nav_module").children("img").prop("src", "/images/common/module_" + (currentIndex + 1) + ".png");
        //切换菜单面板,重新加载该菜单对应的菜单节点
        loadMenu($(this).data("rootmenu"));

    });
});


//加载当前日期
function loadDate() {
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "."
        + myDay;
}

/**
 * 隐藏或者显示侧边栏
 *
 */
function switchSysBar(flag) {
    var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
    if (flag == true) { // flag==true
        left_menu_cnt.show(500, 'linear');
        side.css({
            width: '280px'
        });
        $('#top_nav').css({
            width: '77%',
            left: '304px'
        });
        $('#main').css({
            left: '280px'
        });
    } else {
        if (left_menu_cnt.is(":visible")) {
            left_menu_cnt.hide(10, 'linear');
            side.css({
                width: '60px'
            });
            $('#top_nav').css({
                width: '100%',
                left: '60px',
                'padding-left': '28px'
            });
            $('#main').css({
                left: '60px'
            });
            $("#show_hide_btn").find('img').attr('src',
                '/images/common/nav_show.png');
        } else {
            left_menu_cnt.show(500, 'linear');
            side.css({
                width: '280px'
            });
            $('#top_nav').css({
                width: '77%',
                left: '304px',
                'padding-left': '0px'
            });
            $('#main').css({
                left: '280px'
            });
            $("#show_hide_btn").find('img').attr('src',
                '/images/common/nav_hide.png');
        }
    }
}

// =====================================
$(function () {
    // 加载日期
    loadDate();
    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function () {
        switchSysBar();
    });
});
