$(function () {
    $("#emp_datagird").datagrid({
        url:"http://localhost/employee/easy.do",
        fit:true,
        fitColumns:true,
        toolbar:'#emp_toolbar',
        pagination:true,
        rownumbers:true,
        singleSelect:true,
        columns: [[{
            field: 'id',
            hidden: true
        }, {
            field: 'name',
            title: '姓名',
            width: 100
        },
        {
            field: 'email',
            title: '邮箱',
            width: 100
        },
        {
            field: 'age',
            title: '年龄',
            width: 100
        }, {
            field: 'admin',
            title: '超级管理员',
            width: 100
            },{
            field: 'dept',
            title: '部门',
            width: 100,
                formatter:function(value,row,index){
                    return value?value.name:"";
                }


        }]]
    });
    //表单弹出框
    $("#emp_dialog").dialog({
        width:270,
        height:250,
        buttons:"#butts",
        closed:true
    })
    //设置搜索框
    $("#keyword").searchbox({
    	searcher:function(value,name){
    		//使用load方法,重新加载第一页的数据
    		$("#emp_datagrid").datagrid("load",{
    			"keyword":value
    		});
    	}
    })

})
//新增按钮
function add(){
    $("#emp_dialog").dialog("open");
    $("#emp_dialog").dialog("setTitle","新增员工")
}
//编辑按钮
function edit(){
    //判断是否选中数据
    var row =$("#emp_datagird").datagrid("getSelected");
    if(!row){
        //弹出提示框
        $.messager.alert('温馨提示', "请选中一条数据", "warning");
        return;
    }
    //回显操作,读取记录填充到表单中
    //基于同名匹配
    $("#emp_form").form('load',row);
    //给员工添加dept.id属性
    row["dept.id"]=row.dept.id;

    //设置标题
    $("#emp_dialog").dialog("setTitle","编辑员工")
    //打开弹窗
    $("#emp_dialog").dialog("open");
}
//删除按钮
function remove(){
	//判断是否选中数据
	 var row =$("#emp_datagird").datagrid("getSelected");
	    if(!row){
	        //弹出提示框
	        $.messager.alert('温馨提示', "请选中一条数据", "warning");
	        return;
	    }
	    //发送删除数据
	    $.get("http://localhost/employee/delete.do",{id:row.id},function(data){
	    	if(data.success){
	    		//弹出提示
	    		  $.messager.alert('温馨提示',data.msg,"info",function(){
	                  //重新加载页面
	                  $("#emp_datagrid").datagrid("reload");

	    		  })
	    	}
	    })
}
function reload(){
    $("#emp_datagrid").datagrid("reload");
}
function cancel(){
    //关闭弹窗
    $("#emp_dialog").dialog("close");
}
//保存按钮
function save(){
    var url ='http://localhost/employee/saveOrUpdate.do';

    //判断是否有id
    if($("[name=id]").val()){
    var url ='http://localhost/employee/saveOrUpdate.do';

    }
    //提交表单
    $("#emp_form").form("submit",{
        url:url,
        success:function(data){
            //转换为json对象
            data= $.parseJSON(data);
            if(data.success){
                //弹出一个提示,
                $.messager.alert('温馨提示',data.msg,"info",function(){
                //关闭弹窗
                $("#emp_dialog").dialog("close");
                //重新加载页面
                $("#emp_datagrid").datagrid("reload");

                });

            }else {
                $.messager.alert('温馨提示', data.msg, "errorr");
            }
        }
    })
}