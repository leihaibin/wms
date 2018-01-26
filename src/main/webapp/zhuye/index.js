$(function() {
	var myTabs=$("#myTabs");
	
	$("#myTree").tree({
		url:'tree.json',
		onClick:function(node){
			//如果已经存在就选中,
			var flag=myTabs.tabs("exists",node.text);
			if(flag){
				//设置选中状态
			myTabs.tabs("select",node.text);
			}else {
				//给下面的tabs新增一个选项卡
				myTabs.tabs("add", {
					title: node.text,
					content:"<iframe src="+node.url+" width=100% height=100% ></iframe>",
					closable: true

				})
			}
		}
	});
	myTabs.tabs ({
		fit:true
	});
})